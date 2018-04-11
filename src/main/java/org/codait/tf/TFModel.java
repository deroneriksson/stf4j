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

	Map<String, Object> inputNameToValue = new LinkedHashMap<>();
	Map<String, Object> outputNameToValue = new LinkedHashMap<>();
	Map<String, String> inputKeyToName = new LinkedHashMap<>();
	Map<String, String> outputKeyToName = new LinkedHashMap<>();

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

	public TFModel in(String key, Object value) {
		if (value == null) {
			throw new TFException("Input value cannot be null");
		}
		log.debug("Register input key '" + key + "' with object type " + value.getClass().getName());
		if (value instanceof Tensor) {
			String name = TFUtil.inputKeyToName(key, metaGraphDef());
			inputNameToValue.put(name, value);
			inputKeyToName.put(key, name);
		} else {
			TensorInfo ti = TFUtil.inputKeyToTensorInfo(key, metaGraphDef());
			String name = ti.getName();
			Tensor<?> tensor = TFUtil.convertToTensor(key, name, value, ti);
			inputNameToValue.put(name, tensor);
			inputKeyToName.put(key, name);
		}
		return this;
	}

	public TFModel out(String key) {
		log.debug("Register output key '" + key + "'");
		String name = TFUtil.outputKeyToName(key, metaGraphDef());
		outputKeyToName.put(key, name);
		outputNameToValue.put(name, null);
		return this;
	}

	public TFModel out(String... keys) {
		for (String key : keys) {
			out(key);
		}
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
		TFResults results = new TFResults(this);
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

	public void displayMetaGraphDefInfo() {
		try {
			TFUtil.displaySignatureDefInfo(metaGraphDef());
		} catch (InvalidProtocolBufferException e) {
			throw new TFException("Exception displaying MetaGraphDef", e);
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("Model directory: ");
		sb.append(modelDir());

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

}
