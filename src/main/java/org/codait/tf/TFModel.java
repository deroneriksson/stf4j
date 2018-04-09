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
	String savedModelDir;

	Map<String, Object> inputs = new LinkedHashMap<>();
	Map<String, Object> outputs = new LinkedHashMap<>();

	public TFModel(String modelDir, String... metaGraphDefTags) {
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
		if (value instanceof Tensor) {
			String name = TFUtil.inputKeyToName(key, metaGraphDef());
			inputs.put(name, value);
		} else {
			TensorInfo ti = TFUtil.inputKeyToTensorInfo(key, metaGraphDef());
			String name = ti.getName();
			Tensor<?> tensor = TFUtil.convertToTensor(name, value, ti);
			inputs.put(name, tensor);
		}
		return this;
	}

	public TFModel out(String key) {
		outputs.put(key, null);
		return this;
	}

	public TFModel out(String... keys) {
		for (String key : keys) {
			outputs.put(key, null);
		}
		return this;
	}

	public TFResults run() {
		Runner runner = runner();
		Set<Entry<String, Object>> iEntries = inputs.entrySet();
		for (Entry<String, Object> iEntry : iEntries) {
			String key = iEntry.getKey();
			Object value = iEntry.getValue();
			runner.feed(key, (Tensor<?>) value);
		}
		Set<String> oKeys = outputs.keySet();
		for (String oKey : oKeys) {
			String oName = TFUtil.outputKeyToName(oKey, metaGraphDef());
			runner.fetch(oName);
		}
		List<Tensor<?>> res = runner.run();
		int i = 0;
		for (String oKey : oKeys) {
			outputs.put(oKey, res.get(i++));
		}
		TFResults results = new TFResults(this);
		return results;
	}

	public MetaGraphDef metaGraphDef() {
		try {
			byte[] b = savedModel.metaGraphDef();
			MetaGraphDef metaGraphDef = MetaGraphDef.parseFrom(b);
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

}
