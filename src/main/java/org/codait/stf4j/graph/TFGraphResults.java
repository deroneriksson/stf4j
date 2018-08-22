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

import java.util.Map;
import java.util.Map.Entry;

import org.tensorflow.Tensor;

/**
 * Representation of the results of running a TensorFlow graph, which primarily consists of a map of Tensor values. An
 * individual result is obtained by specifying the output name.
 *
 */
public class TFGraphResults {

	/**
	 * The TensorFlow graph.
	 */
	TFGraph graph;
	/**
	 * Mapping of output names to values.
	 */
	Map<String, Object> outputNameToValue;

	/**
	 * Create TFGraphResults object with TFGraph. Obtain the output name-to-value mappings from the TFGraph object.
	 * 
	 * @param graph
	 *            The TensorFlow graph
	 */
	public TFGraphResults(TFGraph graph) {
		this.graph = graph;
		this.outputNameToValue = graph.outputNameToValue;
	}

	/**
	 * Obtain the output Tensor corresponding to the output name.
	 * 
	 * @param outputName
	 *            The output name
	 * @return The output Tensor
	 */
	public Tensor<?> getTensor(String outputName) {
		return (Tensor<?>) outputNameToValue.get(outputName);
	}

	/**
	 * Obtain the output Tensor corresponding to the output name.
	 * 
	 * @param <T>
	 *            The type of the returned Tensor
	 * 
	 * @param outputName
	 *            The output name
	 * @param type
	 *            The tensor type
	 * @return The output Tensor
	 */
	@SuppressWarnings("unchecked")
	public <T> Tensor<T> getTensor(String outputName, Class<T> type) {
		return (Tensor<T>) outputNameToValue.get(outputName);
	}

	/**
	 * Display the output results. This includes the output names and information about the tensors such as the tensor
	 * types and shapes. This information is very useful in a REPL environment.
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("\nGraph Outputs:\n");
		if (outputNameToValue == null || outputNameToValue.isEmpty()) {
			sb.append("None\n");
		} else {
			int count = 0;
			for (Entry<String, Object> entry : outputNameToValue.entrySet()) {
				sb.append("  [");
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
