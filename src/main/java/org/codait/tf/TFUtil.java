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

/**
 * Utility class for various TF API functionality.
 *
 */
public class TFUtil {

	/**
	 * Logger for TFUtil
	 */
	protected static Logger log = LogManager.getLogger(TFUtil.class);

	/**
	 * Obtain SignatureDef information from SavedModelBundle.
	 * 
	 * @param savedModelBundle
	 *            The SavedModelBundle object
	 * @return SignatureDef information as a String
	 * @throws InvalidProtocolBufferException
	 *             If problem occurred reading protobuf object
	 */
	public static String signatureDefInfo(SavedModelBundle savedModelBundle) throws InvalidProtocolBufferException {
		return signatureDefInfo(savedModelBundle.metaGraphDef());
	}

	/**
	 * Obtain SignatureDef information from MetaGraphDef bytes.
	 * 
	 * @param metaGraphDefBytes
	 *            Byte array representing MetaGraphDef object
	 * @return SignatureDef information as a String
	 * @throws InvalidProtocolBufferException
	 *             If problem occurred reading protobuf object
	 */
	public static String signatureDefInfo(byte[] metaGraphDefBytes) throws InvalidProtocolBufferException {
		MetaGraphDef mgd = MetaGraphDef.parseFrom(metaGraphDefBytes);
		return signatureDefInfo(mgd);
	}

	/**
	 * Obtain SignatureDef information from MetaGraphDef object.
	 * 
	 * @param mgd
	 *            The MetaGraphDef object
	 * @return SignatureDef information as a String
	 * @throws InvalidProtocolBufferException
	 *             If problem occurred reading protobuf object
	 */
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

	/**
	 * Convert a Java object to its corresponding Tensor object.
	 * 
	 * @param key
	 *            The input key
	 * @param name
	 *            The input name
	 * @param value
	 *            The Java object to convert
	 * @param ti
	 *            The TensorInfo object
	 * @return Data stored in a Tensor object
	 */
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
			//////////////////////////////////////////////////////////////////////////////
		} else if (DataType.DT_INT64 == dtype && isLongType(value)) {
			if (value instanceof Long) {
				tensor = Tensor.create(value, Long.class);
			} else {
				tensor = Tensor.create(value, Long.class);
			}
		} else if (DataType.DT_INT64 == dtype && isIntType(value)) {
			if (value instanceof Integer) {
				long val = Long.valueOf((int) value);
				tensor = Tensor.create(val, Long.class);
			} else {
				log.warn("Implicitly converting int array to long array");
				Object longArray = ArrayUtil.convertArrayType(value, long.class);
				tensor = Tensor.create(longArray, Long.class);
			}
		} else if (DataType.DT_INT64 == dtype && isFloatType(value)) {
			if (value instanceof Float) {
				long val = ((Float) value).longValue();
				tensor = Tensor.create(val, Long.class);
			} else {
				log.warn("Implicitly converting float array to long array");
				Object longArray = ArrayUtil.convertArrayType(value, long.class);
				tensor = Tensor.create(longArray, Long.class);
			}
		} else if (DataType.DT_INT64 == dtype && isDoubleType(value)) {
			if (value instanceof Double) {
				long val = ((Double) value).longValue();
				tensor = Tensor.create(val, Long.class);
			} else {
				log.warn("Implicitly converting double array to long array");
				Object longArray = ArrayUtil.convertArrayType(value, long.class);
				tensor = Tensor.create(longArray, Long.class);
			}
		} else if (DataType.DT_INT64 == dtype && isStringType(value)) {
			if (value instanceof String) {
				long val = Long.parseLong((String) value);
				tensor = Tensor.create(val, Long.class);
			} else {
				log.warn("Implicitly converting String array to long array");
				Object longArray = ArrayUtil.convertArrayType(value, long.class);
				tensor = Tensor.create(longArray, Long.class);
			}
			//////////////////////////////////////////////////////////////////////////////
		} else if (DataType.DT_INT32 == dtype && isLongType(value)) {
			if (value instanceof Long) {
				int val = ((Long) value).intValue();
				tensor = Tensor.create(val, Integer.class);
			} else {
				log.warn("Implicitly converting long array to int array");
				Object intArray = ArrayUtil.convertArrayType(value, int.class);
				tensor = Tensor.create(intArray, Integer.class);
			}
		} else if (DataType.DT_INT32 == dtype && isIntType(value)) {
			if (value instanceof Integer) {
				tensor = Tensor.create(value, Integer.class);
			} else {
				tensor = Tensor.create(value, Integer.class);
			}
		} else if (DataType.DT_INT32 == dtype && isFloatType(value)) {
			if (value instanceof Float) {
				int val = ((Float) value).intValue();
				tensor = Tensor.create(val, Integer.class);
			} else {
				log.warn("Implicitly converting float array to int array");
				Object intArray = ArrayUtil.convertArrayType(value, int.class);
				tensor = Tensor.create(intArray, Integer.class);
			}
		} else if (DataType.DT_INT32 == dtype && isDoubleType(value)) {
			if (value instanceof Double) {
				int val = ((Double) value).intValue();
				tensor = Tensor.create(val, Integer.class);
			} else {
				log.warn("Implicitly converting double array to int array");
				Object intArray = ArrayUtil.convertArrayType(value, int.class);
				tensor = Tensor.create(intArray, Integer.class);
			}
		} else if (DataType.DT_INT32 == dtype && isStringType(value)) {
			if (value instanceof String) {
				int val = Integer.parseInt((String) value);
				tensor = Tensor.create(val, Integer.class);
			} else {
				log.warn("Implicitly converting String array to int array");
				Object intArray = ArrayUtil.convertArrayType(value, int.class);
				tensor = Tensor.create(intArray, Integer.class);
			}
			//////////////////////////////////////////////////////////////////////////////
		} else if (DataType.DT_STRING == dtype && isByteArray(value)) {
			if (isByteObjectArray(value)) {
				log.warn("Implicitly converting Byte object array to primitive byte array");
				Object byteArray = ArrayUtil.convertArrayType(value, byte.class);
				tensor = Tensor.create(byteArray, String.class);
			} else { // primitive byte array
				tensor = Tensor.create(value, String.class);
			}
		} else if (DataType.DT_STRING == dtype && isStringType(value)) {
			if (value instanceof String) {
				throw new TFException("Could not convert input key '" + key + "' (name: '" + name
						+ "') to Tensor - conversion not implemented yet.");
			} else {
				log.warn("Implicitly converting String array to byte array");
				Object byteArray = ArrayUtil.multidimStringsToMultidimBytes(value);
				tensor = Tensor.create(byteArray, String.class);
			}
		}
		if (tensor == null) {
			throw new TFException("Could not convert input key '" + key + "' (name: '" + name + "') to Tensor");
		}
		return tensor;
	}

	/**
	 * Return true if the object is a Long instance or a long/Long array.
	 * 
	 * @param value
	 *            The object to evaluate
	 * @return True if object is a Long type, false otherwise
	 */
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

	/**
	 * Return true if the object is an Integer instance or an int/Integer array.
	 * 
	 * @param value
	 *            The object to evaluate
	 * @return True if object is an Integer type, false otherwise
	 */
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

	/**
	 * Return true if the object is a String or a String array.
	 * 
	 * @param value
	 *            The object to evaluate
	 * @return True if object is a String type, false otherwise
	 */
	public static boolean isStringType(Object value) {
		if (value instanceof String) {
			return true;
		}
		String typeName = value.getClass().getTypeName();
		if (typeName.startsWith("java.lang.String[")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Return true if the object is a Float array.
	 * 
	 * @param value
	 *            The object to evaluate
	 * @return True if object is a Float Object array, false otherwise
	 */
	public static boolean isFloatObjectArray(Object value) {
		String typeName = value.getClass().getTypeName();
		return typeName.startsWith("java.lang.Float[");
	}

	/**
	 * Return true if the object is a Byte array.
	 * 
	 * @param value
	 *            The object to evaluate
	 * @return True if object is a Byte Object array, false otherwise
	 */
	public static boolean isByteObjectArray(Object value) {
		String typeName = value.getClass().getTypeName();
		return typeName.startsWith("java.lang.Byte[");
	}

	/**
	 * Return true if the object is a Float instance or a float/Float array.
	 * 
	 * @param value
	 *            The object to evaluate
	 * @return True if object is a Float type, false otherwise
	 */
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

	/**
	 * Return true if the object is a Double instance or a double/Double array.
	 * 
	 * @param value
	 *            The object to evaluate
	 * @return True if object is a Double type, false otherwise
	 */
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

	/**
	 * Return true if the object is a byte/Byte array.
	 * 
	 * @param value
	 *            The object to evaluate
	 * @return True if object is a byte/Byte array, false otherwise
	 */
	public static boolean isByteArray(Object value) {
		String typeName = value.getClass().getTypeName();
		if (typeName.startsWith("byte[") || typeName.startsWith("java.lang.Byte[")) {
			return true;
		}
		return false;
	}

	/**
	 * Obtain the input name corresponding to an input key. If no SignatureDef key is specified, an input key can
	 * potentially return an unexpected name since an input key is not necessarily uniquely paired with an input name.
	 * 
	 * @param signatureDefKey
	 *            The SignatureDef key
	 * @param inputKey
	 *            The input key
	 * @param metaGraphDef
	 *            The MetaGraphDef object
	 * @return The input name corresponding to the input key
	 */
	public static String inputKeyToName(String signatureDefKey, String inputKey, TFModel model) {
		return inputKeyToName(signatureDefKey, inputKey, model.metaGraphDef());
	}

	/**
	 * Obtain the input name corresponding to an input key. If no SignatureDef key is specified, an input key can
	 * potentially return an unexpected name since an input key is not necessarily uniquely paired with an input name.
	 * 
	 * @param signatureDefKey
	 *            The SignatureDef key
	 * @param inputKey
	 *            The input key
	 * @param metaGraphDef
	 *            The MetaGraphDef object
	 * @return The input name corresponding to the input key
	 */
	public static String inputKeyToName(String signatureDefKey, String inputKey, MetaGraphDef metaGraphDef) {
		Map<String, SignatureDef> sdm = metaGraphDef.getSignatureDefMap();
		if (signatureDefKey == null) {
			Set<Entry<String, SignatureDef>> sdmEntries = sdm.entrySet();
			for (Entry<String, SignatureDef> sdmEntry : sdmEntries) {
				SignatureDef sigDef = sdmEntry.getValue();
				Map<String, TensorInfo> inputsMap = sigDef.getInputsMap();
				if (inputsMap.containsKey(inputKey)) {
					TensorInfo tensorInfo = inputsMap.get(inputKey);
					String inputName = tensorInfo.getName();
					return inputName;
				}
			}
		} else {
			SignatureDef sigDef = sdm.get(signatureDefKey);
			Map<String, TensorInfo> inputsMap = sigDef.getInputsMap();
			if (inputsMap.containsKey(inputKey)) {
				TensorInfo tensorInfo = inputsMap.get(inputKey);
				String inputName = tensorInfo.getName();
				return inputName;
			}
		}
		throw new TFException("Input key '" + inputKey + "' not found in MetaGraphDef");
	}

	/**
	 * Obtain the TensorInfo object corresponding to an input key.
	 * 
	 * @param signatureDefKey
	 *            The SignatureDef key
	 * @param inputKey
	 *            The input key
	 * @param model
	 *            The TFModel object
	 * @return The TensorInfo object corresponding to the input key
	 */
	public static TensorInfo inputKeyToTensorInfo(String signatureDefKey, String inputKey, TFModel model) {
		return inputKeyToTensorInfo(signatureDefKey, inputKey, model.metaGraphDef());
	}

	/**
	 * Obtain the TensorInfo object corresponding to an input key.
	 *
	 * @param signatureDefKey
	 *            The SignatureDef key
	 * @param inputKey
	 *            The input key
	 * @param metaGraphDef
	 *            The MetaGraphDef object
	 * @return The TensorInfo object corresponding to the input key
	 */
	public static TensorInfo inputKeyToTensorInfo(String signatureDefKey, String inputKey, MetaGraphDef metaGraphDef) {
		Map<String, SignatureDef> sdm = metaGraphDef.getSignatureDefMap();
		if (signatureDefKey == null) {
			Set<Entry<String, SignatureDef>> sdmEntries = sdm.entrySet();
			for (Entry<String, SignatureDef> sdmEntry : sdmEntries) {
				SignatureDef sigDef = sdmEntry.getValue();
				Map<String, TensorInfo> inputsMap = sigDef.getInputsMap();
				if (inputsMap.containsKey(inputKey)) {
					TensorInfo tensorInfo = inputsMap.get(inputKey);
					log.debug("Retrieved TensorInfo '" + tensorInfo.getName() + "' for key '" + inputKey + "'");
					return tensorInfo;
				}
			}
			throw new TFException("Input key '" + inputKey + "' not found in MetaGraphDef");
		} else {
			SignatureDef sigDef = sdm.get(signatureDefKey);
			Map<String, TensorInfo> inputsMap = sigDef.getInputsMap();
			if (inputsMap.containsKey(inputKey)) {
				TensorInfo tensorInfo = inputsMap.get(inputKey);
				log.debug("Retrieved TensorInfo '" + tensorInfo.getName() + "' for key '" + inputKey + "'");
				return tensorInfo;
			}
			throw new TFException("Input key '" + inputKey + "' for SignatureDef '" + signatureDefKey + "' not found");
		}
	}

	/**
	 * Obtain the output name corresponding to an output key.
	 * 
	 * @param signatureDefKey
	 *            The SignatureDef key
	 * @param outputKey
	 *            The output key
	 * @param model
	 *            The TFModel object
	 * @return The output name corresponding to the output key
	 */
	public static String outputKeyToName(String signatureDefKey, String outputKey, TFModel model) {
		return outputKeyToName(signatureDefKey, outputKey, model.metaGraphDef());
	}

	/**
	 * Obtain the output name corresponding to an output key.
	 * 
	 * @param signatureDefKey
	 *            The SignatureDef key
	 * @param outputKey
	 *            The output key
	 * @param metaGraphDef
	 *            The MetaGraphDef object
	 * @return The output name corresponding to the output key
	 */
	public static String outputKeyToName(String signatureDefKey, String outputKey, MetaGraphDef metaGraphDef) {
		Map<String, SignatureDef> sdm = metaGraphDef.getSignatureDefMap();
		if (signatureDefKey == null) {
			Set<Entry<String, SignatureDef>> sdmEntries = sdm.entrySet();
			for (Entry<String, SignatureDef> sdmEntry : sdmEntries) {
				SignatureDef sigDef = sdmEntry.getValue();
				Map<String, TensorInfo> outputsMap = sigDef.getOutputsMap();
				if (outputsMap.containsKey(outputKey)) {
					TensorInfo tensorInfo = outputsMap.get(outputKey);
					String outputName = tensorInfo.getName();
					return outputName;
				}
			}
			throw new TFException("Output key '" + outputKey + "' not found in MetaGraphDef");
		} else {
			SignatureDef sigDef = sdm.get(signatureDefKey);
			Map<String, TensorInfo> outputsMap = sigDef.getOutputsMap();
			if (outputsMap.containsKey(outputKey)) {
				TensorInfo tensorInfo = outputsMap.get(outputKey);
				String outputName = tensorInfo.getName();
				return outputName;
			}
			throw new TFException(
					"Output key '" + outputKey + "' for SignatureDef '" + signatureDefKey + "' not found");
		}
	}

	/**
	 * Obtain the TensorInfo object corresponding to an output key.
	 * 
	 * @param key
	 *            The output key
	 * @param model
	 *            The TFModel object
	 * @return The TensorInfo object corresponding to the output key
	 */
	public static TensorInfo outputKeyToTensorInfo(String key, TFModel model) {
		return outputKeyToTensorInfo(key, model.metaGraphDef());
	}

	/**
	 * Obtain the TensorInfo object corresponding to an output key.
	 * 
	 * @param key
	 *            The output key
	 * @param metaGraphDef
	 *            The MetaGraphDef object
	 * @return The TensorInfo object corresponding to the output key
	 */
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
