package org.codait.tf;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.tensorflow.SavedModelBundle;
import org.tensorflow.Tensor;
import org.tensorflow.framework.DataType;
import org.tensorflow.framework.MetaGraphDef;
import org.tensorflow.framework.SignatureDef;
import org.tensorflow.framework.TensorInfo;
import org.tensorflow.framework.TensorShapeProto;
import org.tensorflow.framework.TensorShapeProto.Dim;

import com.google.protobuf.InvalidProtocolBufferException;

public class TFUtil {

	/**
	 * Logger for TFUtil
	 */
	protected static Logger log = LogManager.getLogger(TFUtil.class);

	public static String signatureDefInfo(SavedModelBundle savedModelBundle) throws InvalidProtocolBufferException {
		return signatureDefInfo(savedModelBundle.metaGraphDef());
	}

	public static String signatureDefInfo(byte[] metaGraphDefBytes) throws InvalidProtocolBufferException {
		MetaGraphDef mgd = MetaGraphDef.parseFrom(metaGraphDefBytes);
		return signatureDefInfo(mgd);
	}

	public static String signatureDefInfo(MetaGraphDef mgd) throws InvalidProtocolBufferException {
		StringBuilder sb = new StringBuilder();
		Map<String, SignatureDef> sdm = mgd.getSignatureDefMap();
		Set<Entry<String, SignatureDef>> sdmEntries = sdm.entrySet();
		for (Entry<String, SignatureDef> sdmEntry : sdmEntries) {
			sb.append("\nSignatureDef key: " + sdmEntry.getKey());
			SignatureDef sigDef = sdmEntry.getValue();
			String methodName = sigDef.getMethodName();
			sb.append("\nmethod name: " + methodName);

			sb.append("\ninputs:");
			Map<String, TensorInfo> inputsMap = sigDef.getInputsMap();
			Set<Entry<String, TensorInfo>> inputEntries = inputsMap.entrySet();
			for (Entry<String, TensorInfo> inputEntry : inputEntries) {
				sb.append("\n  input key: " + inputEntry.getKey());
				TensorInfo inputTensorInfo = inputEntry.getValue();
				DataType inputTensorDtype = inputTensorInfo.getDtype();
				sb.append("\n    dtype: " + inputTensorDtype);
				sb.append("\n    shape: (");
				TensorShapeProto inputTensorShape = inputTensorInfo.getTensorShape();
				int dimCount = inputTensorShape.getDimCount();
				for (int i = 0; i < dimCount; i++) {
					Dim dim = inputTensorShape.getDim(i);
					long dimSize = dim.getSize();
					if (i > 0) {
						sb.append(", ");
					}
					sb.append(dimSize);
				}
				sb.append(")");
				String inputTensorName = inputTensorInfo.getName();
				sb.append("\n    name: " + inputTensorName);
			}

			sb.append("\noutputs:");
			Map<String, TensorInfo> outputsMap = sigDef.getOutputsMap();
			Set<Entry<String, TensorInfo>> outputEntries = outputsMap.entrySet();
			for (Entry<String, TensorInfo> outputEntry : outputEntries) {
				sb.append("\n  output key: " + outputEntry.getKey());
				TensorInfo outputTensorInfo = outputEntry.getValue();
				DataType outputTensorDtype = outputTensorInfo.getDtype();
				sb.append("\n    dtype: " + outputTensorDtype);
				sb.append("\n    shape: (");
				TensorShapeProto outputTensorShape = outputTensorInfo.getTensorShape();
				int dimCount = outputTensorShape.getDimCount();
				for (int i = 0; i < dimCount; i++) {
					Dim dim = outputTensorShape.getDim(i);
					long dimSize = dim.getSize();
					if (i > 0) {
						sb.append(", ");
					}
					sb.append(dimSize);
				}
				sb.append(")");
				String inputTensorName = outputTensorInfo.getName();
				sb.append("\n    name: " + inputTensorName);
			}
		}
		return sb.toString();
	}

	public static Tensor<?> convertToTensor(String key, String name, Object value, TensorInfo ti) {
		DataType dtype = ti.getDtype();
		Tensor<?> tensor = null;
		if (DataType.DT_FLOAT == dtype && isFloatType(value)) {
			if (value instanceof Float) {
				tensor = Tensor.create(value, Float.class);
			} else if (isFloatObjectArray(value)) {
				// to avoid: "cannot create non-scalar Tensors from arrays of
				// boxed values"
				log.warn("Implicitly converting Float object array to primitive float array");
				Object floatArray = ArrayUtil.convertArrayType(value, float.class);
				tensor = Tensor.create(floatArray, Float.class);
			} else { // primitive float array
				tensor = Tensor.create(value, Float.class);
			}
		} else if (DataType.DT_FLOAT == dtype && isIntType(value)) {
			if (value instanceof Integer) {
				float val = (float) value;
				tensor = Tensor.create(val, Float.class);
			} else {
				log.warn("Implicitly converting integer array to float array");
				Object floatArray = ArrayUtil.convertArrayType(value, float.class);
				tensor = Tensor.create(floatArray, Float.class);
			}
		} else if (DataType.DT_FLOAT == dtype && isLongType(value)) {
			if (value instanceof Long) {
				float val = (float) value;
				tensor = Tensor.create(val, Float.class);
			} else {
				log.warn("Implicitly converting long array to float array");
				Object floatArray = ArrayUtil.convertArrayType(value, float.class);
				tensor = Tensor.create(floatArray, Float.class);
			}
		} else if (DataType.DT_FLOAT == dtype && isDoubleType(value)) {
			if (value instanceof Double) {
				double val = (double) value;
				tensor = Tensor.create(val, Double.class);
			} else {
				log.warn("Implicitly converting double array to float array");
				Object floatArray = ArrayUtil.convertArrayType(value, float.class);
				tensor = Tensor.create(floatArray, Float.class);
			}
		}
		if (tensor == null) {
			throw new TFException("Could not convert input key '" + key + "' (name: '" + name + "') to Tensor");
		}
		return tensor;
	}

	public static boolean isLongType(Object value) {
		if (value instanceof Long) {
			return true;
		}
		String typeName = value.getClass().getTypeName();
		if (typeName.startsWith("long[") || typeName.startsWith("java.lang.Long[")) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isIntType(Object value) {
		if (value instanceof Integer) {
			return true;
		}
		String typeName = value.getClass().getTypeName();
		if (typeName.startsWith("int[") || typeName.startsWith("java.lang.Integer[")) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isFloatObjectArray(Object value) {
		String typeName = value.getClass().getTypeName();
		return typeName.startsWith("java.lang.Float[");
	}

	public static boolean isFloatType(Object value) {
		if (value instanceof Float) {
			return true;
		}
		String typeName = value.getClass().getTypeName();
		if (typeName.startsWith("float[") || typeName.startsWith("java.lang.Float[")) {
			return true;
		}
		return false;
	}

	public static boolean isDoubleType(Object value) {
		if (value instanceof Double) {
			return true;
		}
		String typeName = value.getClass().getTypeName();
		if (typeName.startsWith("double[") || typeName.startsWith("java.lang.Double[")) {
			return true;
		}
		return false;
	}

	public static String inputKeyToName(String key, MetaGraphDef metaGraphDef) {
		Map<String, SignatureDef> sdm = metaGraphDef.getSignatureDefMap();
		Set<Entry<String, SignatureDef>> sdmEntries = sdm.entrySet();
		for (Entry<String, SignatureDef> sdmEntry : sdmEntries) {
			SignatureDef sigDef = sdmEntry.getValue();
			Map<String, TensorInfo> inputsMap = sigDef.getInputsMap();
			if (inputsMap.containsKey(key)) {
				TensorInfo tensorInfo = inputsMap.get(key);
				String inputName = tensorInfo.getName();
				return inputName;
			}
		}
		throw new TFException("Input key '" + key + "' not found in MetaGraphDef");
	}

	public static TensorInfo inputKeyToTensorInfo(String key, MetaGraphDef metaGraphDef) {
		Map<String, SignatureDef> sdm = metaGraphDef.getSignatureDefMap();
		Set<Entry<String, SignatureDef>> sdmEntries = sdm.entrySet();
		for (Entry<String, SignatureDef> sdmEntry : sdmEntries) {
			SignatureDef sigDef = sdmEntry.getValue();
			Map<String, TensorInfo> inputsMap = sigDef.getInputsMap();
			if (inputsMap.containsKey(key)) {
				TensorInfo tensorInfo = inputsMap.get(key);
				log.debug("Retrieved TensorInfo '" + tensorInfo.getName() + "' for key '" + key + "'");
				return tensorInfo;
			}
		}
		throw new TFException("Input key '" + key + "' not found in MetaGraphDef");
	}

	public static String outputKeyToName(String key, MetaGraphDef metaGraphDef) {
		Map<String, SignatureDef> sdm = metaGraphDef.getSignatureDefMap();
		Set<Entry<String, SignatureDef>> sdmEntries = sdm.entrySet();
		for (Entry<String, SignatureDef> sdmEntry : sdmEntries) {
			SignatureDef sigDef = sdmEntry.getValue();
			Map<String, TensorInfo> outputsMap = sigDef.getOutputsMap();
			if (outputsMap.containsKey(key)) {
				TensorInfo tensorInfo = outputsMap.get(key);
				String outputName = tensorInfo.getName();
				return outputName;
			}
		}
		throw new TFException("Output key '" + key + "' not found in MetaGraphDef");
	}

	public static TensorInfo outputKeyToTensorInfo(String key, MetaGraphDef metaGraphDef) {
		Map<String, SignatureDef> sdm = metaGraphDef.getSignatureDefMap();
		Set<Entry<String, SignatureDef>> sdmEntries = sdm.entrySet();
		for (Entry<String, SignatureDef> sdmEntry : sdmEntries) {
			SignatureDef sigDef = sdmEntry.getValue();
			Map<String, TensorInfo> outputsMap = sigDef.getOutputsMap();
			if (outputsMap.containsKey(key)) {
				TensorInfo tensorInfo = outputsMap.get(key);
				return tensorInfo;
			}
		}
		throw new TFException("Output key '" + key + "' not found in MetaGraphDef");
	}

}
