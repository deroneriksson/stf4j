package org.codait.tf;

import java.util.LinkedHashMap;
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
import org.tensorflow.framework.TensorInfo;

import com.google.protobuf.InvalidProtocolBufferException;

public class TFModel {

	/**
	 * Logger for TFModel
	 */
	protected static Logger log = LogManager.getLogger(TFModel.class);

	SavedModelBundle savedModel;
	MetaGraphDef metaGraphDef;
	String savedModelDir;

	String signatureDefKey;
	Map<String, Object> inputNameToValue = new LinkedHashMap<>();
	Map<String, Object> outputNameToValue = new LinkedHashMap<>();
	Map<String, String> inputKeyToName = new LinkedHashMap<>();
	Map<String, String> outputKeyToName = new LinkedHashMap<>();
	TFResults results;

	public TFModel(String modelDir, String... metaGraphDefTags) {
		log.debug("Creating TFModel object");
		savedModelDir = modelDir;
		savedModel = SavedModelBundle.load(modelDir, metaGraphDefTags);
	}

	public TFModel(String modelDir) {
		this(modelDir, "serve");
	}

	public SavedModelBundle model() {
		return savedModel;
	}

	public String modelDir() {
		return savedModelDir;
	}

	public Session session() {
		return savedModel.session();
	}

	public Runner runner() {
		return session().runner();
	}

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

	public TFModel out(String outputKey) {
		log.debug("Register output key '" + outputKey + "'");
		String outputName = TFUtil.outputKeyToName(signatureDefKey, outputKey, metaGraphDef());
		outputKeyToName.put(outputKey, outputName);
		outputNameToValue.put(outputName, null);
		return this;
	}

	public TFModel out(String... keys) {
		for (String key : keys) {
			out(key);
		}
		return this;
	}

	public TFModel sig(String signatureDefKey) {
		this.signatureDefKey = signatureDefKey;
		return this;
	}

	public TFResults run() {
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

	public String signatureDefInfo() {
		try {
			return TFUtil.signatureDefInfo(metaGraphDef());
		} catch (InvalidProtocolBufferException e) {
			throw new TFException("Exception displaying MetaGraphDef", e);
		}
	}

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

	public void clear() {
		inputKeyToName.clear();
		inputNameToValue.clear();
		outputKeyToName.clear();
		outputNameToValue.clear();
		if (results != null) {
			results.outputKeyToName.clear();
			results.outputNameToValue.clear();
		}
		signatureDefKey = null;
	}
}
