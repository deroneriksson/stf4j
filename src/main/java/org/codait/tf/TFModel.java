package org.codait.tf;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.tensorflow.SavedModelBundle;
import org.tensorflow.Session;
import org.tensorflow.Session.Runner;
import org.tensorflow.Tensor;
import org.tensorflow.framework.MetaGraphDef;
import org.tensorflow.framework.SignatureDef;
import org.tensorflow.framework.TensorInfo;

import com.google.protobuf.InvalidProtocolBufferException;

/**
 * Encapsulation of a TensorFlow model for simplified TensorFlow execution from Java using SavedModels.
 *
 */
public class TFModel {

	/**
	 * Logger for TFModel
	 */
	protected static Logger log = LogManager.getLogger(TFModel.class);

	/**
	 * SavedModel loaded from a file system
	 */
	SavedModelBundle savedModel;
	/**
	 * model metadata description
	 */
	MetaGraphDef metaGraphDef;
	/**
	 * SavedModel directory
	 */
	String savedModelDir;

	/**
	 * SignatureDef key that allows a particular set of input keys and output keys to be mapped to the desired input
	 * names and output names.
	 */
	String signatureDefKey;
	/**
	 * Mapping of input keys to names.
	 */
	Map<String, String> inputKeyToName = new LinkedHashMap<String, String>();
	/**
	 * Mapping of output keys to names.
	 */
	Map<String, String> outputKeyToName = new LinkedHashMap<String, String>();
	/**
	 * Mapping of input names to values.
	 */
	Map<String, Object> inputNameToValue = new LinkedHashMap<String, Object>();
	/**
	 * Mapping of output names to values.
	 */
	Map<String, Object> outputNameToValue = new LinkedHashMap<String, Object>();
	/**
	 * The results obtained from executing the model.
	 */
	TFResults results;
	/**
	 * The required input keys.
	 */
	Set<String> requiredInputKeys = new LinkedHashSet<String>();
	/**
	 * The possible output keys.
	 */
	Set<String> possibleOutputKeys = new LinkedHashSet<String>();

	/**
	 * Load TensorFlow model located at modelDir with specified MetaGraphDef tags.
	 * 
	 * @param modelDir
	 *            SavedModel directory
	 * @param metaGraphDefTags
	 *            The MetaGraphDef tags
	 */
	public TFModel(String modelDir, String... metaGraphDefTags) {
		log.debug("Creating TFModel object");
		savedModelDir = modelDir;
		savedModel = SavedModelBundle.load(modelDir, metaGraphDefTags);
	}

	/**
	 * Load TensorFlow model located at modelDir with tag "serve".
	 * 
	 * @param modelDir
	 *            SavedModel directory
	 */
	public TFModel(String modelDir) {
		this(modelDir, "serve");
	}

	/**
	 * Obtain the SavedModel.
	 * 
	 * @return The SavedModelBundle
	 */
	public SavedModelBundle model() {
		return savedModel;
	}

	/**
	 * Obtain the SavedModel directory.
	 * 
	 * @return SavedModel directory
	 */
	public String modelDir() {
		return savedModelDir;
	}

	/**
	 * Obtain a Session to allow computations to be performed on the SavedModel.
	 * 
	 * @return A Session to the SavedModel.
	 */
	public Session session() {
		return savedModel.session();
	}

	/**
	 * Obtain a Runner to run the TensorFlow model graph operations and retrieve the results.
	 * 
	 * @return A Runner to the SavedModel.
	 */
	public Runner runner() {
		return session().runner();
	}

	/**
	 * Add an input to the model by specifying an input key and the corresponding value. If a SignatureDef key has been
	 * specified using the TFModel sig() method, the input key will be specific to the SignatureDef key.
	 * 
	 * @param inputKey
	 *            The input key
	 * @param inputValue
	 *            The input value
	 * @return {@code this} TFModel object to allow chaining of methods
	 */
	public TFModel in(String inputKey, Object inputValue) {
		if (inputValue == null) {
			throw new TFException("Input value cannot be null");
		}
		log.debug("Register input key '" + inputKey + "' with object type " + inputValue.getClass().getName());
		if (inputValue instanceof Tensor) {
			String inputName = TFUtil.inputKeyToName(signatureDefKey, inputKey, metaGraphDef());
			inputNameToValue.put(inputName, inputValue);
			inputKeyToName.put(inputKey, inputName);
		} else {
			TensorInfo ti = TFUtil.inputKeyToTensorInfo(signatureDefKey, inputKey, metaGraphDef());
			String inputName = ti.getName();
			Tensor<?> tensor = TFUtil.convertToTensor(inputKey, inputName, inputValue, ti);
			inputNameToValue.put(inputName, tensor);
			inputKeyToName.put(inputKey, inputName);
		}
		return this;
	}

	/**
	 * Add an output from the model by specifying an output key. If a SignatureDef key has been specified using the
	 * TFModel sig() method, the output key will be specific to the SignatureDef key.
	 * 
	 * @param outputKey
	 *            The output key
	 * @return {@code this} TFModel object to allow chaining of methods
	 */
	public TFModel out(String outputKey) {
		log.debug("Register output key '" + outputKey + "'");
		String outputName = TFUtil.outputKeyToName(signatureDefKey, outputKey, metaGraphDef());
		outputKeyToName.put(outputKey, outputName);
		outputNameToValue.put(outputName, null);
		return this;
	}

	/**
	 * Add outputs from the model by specifying output keys. If a SignatureDef key has been specified using the TFModel
	 * sig() method, the output key will be specific to the SignatureDef key.
	 * 
	 * @param outputKeys
	 *            The output keys
	 * @return {@code this} TFModel object to allow chaining of methods
	 */
	public TFModel out(String... outputKeys) {
		for (String outputKey : outputKeys) {
			out(outputKey);
		}
		return this;
	}

	/**
	 * Specify a particular SignatureDef key which allows for specificity in terms of inputs and outputs.
	 * 
	 * @param signatureDefKey
	 *            The SignatureDef key
	 * @return {@code this} TFModel object to allow chaining of methods
	 */
	public TFModel sig(String signatureDefKey) {
		this.signatureDefKey = signatureDefKey;
		requiredInputKeys.clear();
		if (signatureDefKey == null) {
			return this;
		}

		MetaGraphDef mgd = metaGraphDef();
		Map<String, SignatureDef> sdm = mgd.getSignatureDefMap();
		SignatureDef signatureDef = sdm.get(signatureDefKey);

		if (signatureDef == null) {
			Set<String> signatureDefKeys = sdm.keySet();
			throw new TFException("SignatureDef key '" + signatureDefKey + "' not found. Possible keys: "
					+ signatureDefKeys.toString());
		}
		Map<String, TensorInfo> inputsMap = signatureDef.getInputsMap();
		Set<Entry<String, TensorInfo>> inputEntries = inputsMap.entrySet();
		for (Entry<String, TensorInfo> inputEntry : inputEntries) {
			String requiredInputKey = inputEntry.getKey();
			requiredInputKeys.add(requiredInputKey);
		}

		Map<String, TensorInfo> outputsMap = signatureDef.getOutputsMap();
		Set<Entry<String, TensorInfo>> outputEntries = outputsMap.entrySet();
		for (Entry<String, TensorInfo> outputEntry : outputEntries) {
			String possibleOutputKey = outputEntry.getKey();
			possibleOutputKeys.add(possibleOutputKey);
		}

		return this;
	}

	/**
	 * Execute the model graph operations. The results will be returned as a TFResults object, which is a mapping of
	 * output keys to output names to output values. Specific outputs are retrieved by output keys.
	 * 
	 * @return The results as a TFResults object.
	 */
	public TFResults run() {
		if (signatureDefKey == null) {
			log.warn(
					"No SignatureDef key is specified. It is highly recommended that you specify the SignatureDef key using the sig() method");
		}
		checkInputKeys();
		checkOutputKeys();

		log.debug("Running model");
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
		List<Tensor<?>> res = runner.run();
		int i = 0;
		for (String oName : oNames) {
			outputNameToValue.put(oName, res.get(i++));
		}
		results = new TFResults(this);
		log.debug("Model results:\n" + results);
		return results;
	}

	/**
	 * Check that the required input keys have been provided. If not, throw a TFException specifying the missing input
	 * keys.
	 */
	protected void checkInputKeys() {
		if (signatureDefKey == null) {
			log.debug("No SignatureDef key specified, so won't validate input keys for SignatureDef");
			return;
		}

		List<String> missingReqInputKeys = new ArrayList<String>();
		Set<String> inputKeys = inputKeyToName.keySet();
		for (String reqInputKey : requiredInputKeys) {
			if (!inputKeys.contains(reqInputKey)) {
				missingReqInputKeys.add(reqInputKey);
			}
		}
		if (missingReqInputKeys.size() > 0) {
			String missingInputKeys = missingReqInputKeys.toString();
			throw new TFException(
					"The following '" + signatureDefKey + "' required input keys are missing: " + missingInputKeys);
		}
	}

	/**
	 * Check that at least one output key has been provided. If not, throw a TFException specifying the possible output
	 * keys.
	 */
	protected void checkOutputKeys() {
		Set<String> oNames = outputNameToValue.keySet();
		if (oNames == null || oNames.isEmpty()) {
			String possibleOutputs = possibleOutputKeys.toString();
			throw new TFException(
					"At least one output key needs to be specified. Possible output keys: " + possibleOutputs);
		}
	}

	/**
	 * Obtain the model metadata description.
	 * 
	 * @return The model metadata description as a MetaGraphDef object
	 */
	public MetaGraphDef metaGraphDef() {
		if (metaGraphDef != null) {
			return metaGraphDef;
		}
		try {
			byte[] b = savedModel.metaGraphDef();
			metaGraphDef = MetaGraphDef.parseFrom(b);
			return metaGraphDef;
		} catch (InvalidProtocolBufferException e) {
			throw new TFException("Exception obtaining MetaGraphDef from saved model", e);
		}
	}

	/**
	 * Obtain SignatureDef metadata as a String.
	 * 
	 * @return The SignatureDef metadata as a String
	 */
	public String signatureDefInfo() {
		try {
			return TFUtil.signatureDefInfo(metaGraphDef());
		} catch (InvalidProtocolBufferException e) {
			throw new TFException("Exception displaying MetaGraphDef", e);
		}
	}

	/**
	 * Display information about the model, such as the model location in the file system, the SignatureDef metadata,
	 * the inputs, and the outputs.
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("Model directory: ");
		sb.append(modelDir());

		if ((inputKeyToName == null || inputKeyToName.isEmpty())
				&& (outputKeyToName == null || outputKeyToName.isEmpty())) {
			sb.append("\n");
			sb.append(signatureDefInfo());
			sb.append("\nNote: SignatureDef info can be obtained by calling TFModel's signatureDefInfo() method.\n");
			return sb.toString();
		}

		sb.append("\nSignatureDef Key: ");
		sb.append(signatureDefKey == null ? "None" : signatureDefKey);
		sb.append("\nInputs:\n");
		if (inputKeyToName == null || inputKeyToName.isEmpty()) {
			sb.append("  None\n");
		} else {
			int count = 0;
			for (Entry<String, String> entry : inputKeyToName.entrySet()) {
				sb.append("  [");
				sb.append(++count);
				sb.append("] ");

				String key = entry.getKey();
				String name = entry.getValue();
				sb.append(key);
				sb.append(" (");
				sb.append(name);
				sb.append(")");
				sb.append(": ");
				Object value = inputNameToValue.get(name);
				sb.append(value);

				sb.append("\n");
			}
		}

		sb.append("Outputs:\n");
		if (outputKeyToName == null || outputKeyToName.isEmpty()) {
			sb.append("  None\n");
		} else {
			int count = 0;
			for (Entry<String, String> entry : outputKeyToName.entrySet()) {
				sb.append("  [");
				sb.append(++count);
				sb.append("] ");

				String key = entry.getKey();
				String name = entry.getValue();
				sb.append(key);
				sb.append(" (");
				sb.append(name);
				sb.append(")");
				sb.append(": ");
				Object value = outputNameToValue.get(name);
				sb.append(value);

				sb.append("\n");
			}
		}

		return sb.toString();
	}

	/**
	 * Clear the SignatureDef key, the input and output key-to-name-to-value mappings, and the results.
	 */
	public void clear() {
		inputKeyToName.clear();
		inputNameToValue.clear();
		outputKeyToName.clear();
		outputNameToValue.clear();
		requiredInputKeys.clear();
		if (results != null) {
			results.outputKeyToName.clear();
			results.outputNameToValue.clear();
		}
		signatureDefKey = null;
	}
}
