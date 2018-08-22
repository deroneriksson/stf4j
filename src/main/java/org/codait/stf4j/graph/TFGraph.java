// ------------------------------------------------------------------------
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
//
// ------------------------------------------------------------------------

package org.codait.stf4j.graph;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.codait.stf4j.TFException;
import org.tensorflow.Graph;
import org.tensorflow.Session;
import org.tensorflow.Session.Runner;
import org.tensorflow.Tensor;

/**
 * Encapsulation of a TensorFlow graph.
 *
 */
public class TFGraph {

	/**
	 * Logger for TFGraph
	 */
	protected static Logger log = LogManager.getLogger(TFGraph.class);

	/**
	 * TensorFlow Graph object
	 */
	Graph graph;

	/**
	 * Mapping of input names to values.
	 */
	Map<String, Object> inputNameToValue = new LinkedHashMap<String, Object>();
	/**
	 * Mapping of output names to values.
	 */
	Map<String, Object> outputNameToValue = new LinkedHashMap<String, Object>();
	/**
	 * The results obtained from executing the graph.
	 */
	TFGraphResults results;

	/**
	 * Load TensorFlow graph located at graphPath.
	 * 
	 * @param graphPath
	 *            path to TensorFlow graph
	 */
	public TFGraph(String graphPath) {
		if (graphPath == null) {
			throw new TFException("Graph path is null");
		}
		File f = new File(graphPath);
		if (!f.exists()) {
			throw new TFException("Graph file '" + graphPath + "' could not be found");
		}
		log.debug("Creating TFGraph object");
		long start = new Date().getTime();

		byte[] graphDef;
		try {
			graphDef = Files.readAllBytes(Paths.get(graphPath));
		} catch (IOException e) {
			throw new TFException("Exception reading graph from: " + graphPath, e);
		}
		graph = new Graph();
		graph.importGraphDef(graphDef);

		long end = new Date().getTime();
		log.debug("Graph loaded from '" + graphPath + "' in " + (end - start) + " milliseconds");
	}

	/**
	 * Create TFGraph object based on TensorFlow Graph object.
	 * 
	 * @param graph
	 *            TensorFlow graph object
	 */
	public TFGraph(Graph graph) {
		this.graph = graph;
	}

	/**
	 * Clear the TFGraph and the results.
	 */
	public void clear() {
		inputNameToValue.clear();
		outputNameToValue.clear();
		if (results != null) {
			results.outputNameToValue.clear();
		}
	}

	/**
	 * Add an input to the graph by specifying an input name and the corresponding value.
	 * 
	 * @param inputName
	 *            The input key
	 * @param inputValue
	 *            The input value (Tensor)
	 * @return {@code this} TFGraph object to allow chaining of methods
	 */
	public TFGraph input(String inputName, Tensor<?> inputValue) {
		if (inputValue == null) {
			throw new TFException("Input value cannot be null");
		}
		inputNameToValue.put(inputName, inputValue);
		return this;
	}

	/**
	 * Obtain the graph.
	 * 
	 * @return The TensorFlow graph
	 */
	public Graph graph() {
		return graph;
	}

	/**
	 * Add an output from the graph by specifying an output name.
	 * 
	 * @param outputName
	 *            The output name
	 * @return {@code this} TFModel object to allow chaining of methods
	 */
	public TFGraph output(String outputName) {
		log.debug("Register output name '" + outputName + "'");
		outputNameToValue.put(outputName, null);
		return this;
	}

	/**
	 * Add outputs from the graph by specifying output names.
	 * 
	 * @param outputNames
	 *            The output names
	 * @return {@code this} TFModel object to allow chaining of methods
	 */
	public TFGraph output(String... outputNames) {
		for (String outputName : outputNames) {
			output(outputName);
		}
		return this;
	}

	/**
	 * Execute the graph operations. The results will be returned as a TFGraphResults object, which is a mapping of
	 * output names to output values. Specific outputs are retrieved by output names.
	 * 
	 * @return The results as a TFGraphResults object.
	 */
	public TFGraphResults run() {
		log.debug("Running graph");
		Runner runner = runner();
		Set<Entry<String, Object>> iEntries = inputNameToValue.entrySet();
		for (Entry<String, Object> iEntry : iEntries) {
			String name = iEntry.getKey();
			Object value = iEntry.getValue();
			runner.feed(name, (Tensor<?>) value);
		}
		Set<String> oNames = outputNameToValue.keySet();
		for (String oName : oNames) {
			runner.fetch(oName);
		}
		List<Tensor<?>> res = null;
		try {
			res = runner.run();
		} catch (Exception e) {
			throw new TFException("Problem executing TensorFlow graph: " + e.getMessage(), e);
		}
		int i = 0;
		for (String oName : oNames) {
			outputNameToValue.put(oName, res.get(i++));
		}
		results = new TFGraphResults(this);
		log.debug("Graph results:\n" + results);
		return results;
	}

	/**
	 * Obtain a Runner to run the TensorFlow graph operations and retrieve the results.
	 * 
	 * @return A Runner to the Graph.
	 */
	public Runner runner() {
		return session().runner();
	}

	/**
	 * Obtain a Session to allow computations to be performed on the graph.
	 * 
	 * @return A Session to the graph.
	 */
	public Session session() {
		return new Session(graph);
	}

	/**
	 * Display information about the model, such as the model location in the file system, the inputs, and the outputs.
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("Graph Inputs:\n");
		if (inputNameToValue == null || inputNameToValue.isEmpty()) {
			sb.append(" None\n");
		} else {
			int count = 0;
			for (Entry<String, Object> entry : inputNameToValue.entrySet()) {
				sb.append(" [");
				sb.append(++count);
				sb.append("] ");

				String name = entry.getKey();
				Object tensor = entry.getValue();
				sb.append("(");
				sb.append(name);
				sb.append(")");
				sb.append(": ");
				sb.append(tensor);

				sb.append("\n");
			}
		}

		sb.append("Graph Outputs:\n");
		if (outputNameToValue == null || outputNameToValue.isEmpty()) {
			sb.append(" None\n");
		} else {
			int count = 0;
			for (Entry<String, Object> entry : outputNameToValue.entrySet()) {
				sb.append(" [");
				sb.append(++count);
				sb.append("] ");

				String name = entry.getKey();
				Object tensor = entry.getValue();
				sb.append("(");
				sb.append(name);
				sb.append(")");
				sb.append(": ");
				sb.append(tensor);

				sb.append("\n");
			}
		}

		return sb.toString();
	}
}
