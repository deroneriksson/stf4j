package org.codait.tf;

import java.util.Arrays;
import java.util.Map;
import java.util.Map.Entry;

import org.tensorflow.Tensor;
import org.tensorflow.framework.DataType;
import org.tensorflow.framework.TensorInfo;
import org.tensorflow.types.UInt8;

/**
 * Representation of the results of running a TensorFlow model, which primarily consists of a map of Tensor values. An
 * individual result is obtained by specifying the output key.
 *
 */
public class TFResults {

	/**
	 * The TensorFlow model.
	 */
	TFModel model;
	/**
	 * Mapping of output keys to names.
	 */
	Map<String, String> outputKeyToName;
	/**
	 * Mapping of output names to values.
	 */
	Map<String, Object> outputNameToValue;

	/**
	 * Create TFResults object with TFModel. Obtain the output key-to-name and name-to-value mappings from the TFModel
	 * object.
	 * 
	 * @param model
	 *            The TensorFlow model
	 */
	public TFResults(TFModel model) {
		this.model = model;
		this.outputKeyToName = model.outputKeyToName;
		this.outputNameToValue = model.outputNameToValue;
	}

	/**
	 * Display the output results. This includes the output keys, the output names, and information about the tensors
	 * such as the tensor types and shapes. This information is very useful in a REPL environment.
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("SignatureDef Key: ");
		sb.append(model.signatureDefKey == null ? "None" : model.signatureDefKey);
		sb.append("\nOutputs:\n");
		if (outputKeyToName == null || outputKeyToName.isEmpty()) {
			sb.append("None\n");
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
	 * If output key does not exist, throw TFException.
	 * 
	 * @param key
	 *            The output key
	 */
	protected void checkKey(String key) {
		if (!outputKeyToName.containsKey(key)) {
			throw new TFException("Output '" + key + "' not found in results");
		}
	}

	/**
	 * Obtain the output Tensor corresponding to the output key.
	 * 
	 * @param key
	 *            The output key
	 * @return The output Tensor
	 */
	public Tensor<?> getTensor(String key) {
		checkKey(key);
		return (Tensor<?>) keyToOutput(key);
	}

	/**
	 * Obtain the long value corresponding to the output key.
	 * 
	 * @param key
	 *            The output key
	 * @return The long value
	 */
	public long getLong(String key) {
		checkKey(key);
		TensorInfo ti = TFUtil.outputKeyToTensorInfo(key, model);
		DataType dtype = ti.getDtype();
		if (dtype == DataType.DT_FLOAT) {
			@SuppressWarnings("unchecked")
			Tensor<Float> tensor = (Tensor<Float>) keyToOutput(key);
			int shapeLength = tensor.shape().length;
			if (shapeLength == 0) {
				long l = (long) tensor.floatValue();
				return l;
			} else {
				Object fArray = getFloatArrayMultidimensional(key);
				long l = (long) (float) ArrayUtil.firstElementValueOfMultidimArray(fArray);
				return l;
			}
		} else if (dtype == DataType.DT_DOUBLE) {
			@SuppressWarnings("unchecked")
			Tensor<Double> tensor = (Tensor<Double>) keyToOutput(key);
			int shapeLength = tensor.shape().length;
			if (shapeLength == 0) {
				long l = (long) tensor.doubleValue();
				return l;
			} else {
				Object dArray = getDoubleArrayMultidimensional(key);
				long l = (long) (double) ArrayUtil.firstElementValueOfMultidimArray(dArray);
				return l;
			}
		} else if (dtype == DataType.DT_INT64) {
			@SuppressWarnings("unchecked")
			Tensor<Long> tensor = (Tensor<Long>) keyToOutput(key);
			int shapeLength = tensor.shape().length;
			if (shapeLength == 0) {
				long l = tensor.longValue();
				return l;
			} else {
				Object lArray = getLongArrayMultidimensional(key);
				long l = (long) ArrayUtil.firstElementValueOfMultidimArray(lArray);
				return l;
			}
		} else if (dtype == DataType.DT_INT32) {
			@SuppressWarnings("unchecked")
			Tensor<Integer> tensor = (Tensor<Integer>) keyToOutput(key);
			int shapeLength = tensor.shape().length;
			if (shapeLength == 0) {
				long l = (long) tensor.intValue();
				return l;
			} else {
				Object iArray = getIntArrayMultidimensional(key);
				long l = (long) (int) ArrayUtil.firstElementValueOfMultidimArray(iArray);
				return l;
			}
		} else if (dtype == DataType.DT_UINT8) {
			@SuppressWarnings("unchecked")
			Tensor<UInt8> tensor = (Tensor<UInt8>) keyToOutput(key);
			int shapeLength = tensor.shape().length;
			if (shapeLength == 0) {
				byte b = TFUtil.byteScalarFromUInt8Tensor(tensor);
				return (long) b & 0xFF; // unsigned
			} else {
				Object bArray = getByteArrayMultidimensional(key);
				long l = (long) ((byte) ArrayUtil.firstElementValueOfMultidimArray(bArray) & 0xFF);
				return l;
			}
		} else if (dtype == DataType.DT_STRING) {
			@SuppressWarnings("unchecked")
			Tensor<String> tensor = (Tensor<String>) keyToOutput(key);
			int shapeLength = tensor.shape().length;
			if (shapeLength == 0) {
				long l = Long.parseLong(new String(tensor.bytesValue()));
				return l;
			} else {
				Object sArray = getStringArrayMultidimensional(key);
				String s = (String) ArrayUtil.firstElementValueOfMultidimArray(sArray);
				long l = Long.parseLong(s);
				return l;
			}
		} else if (dtype == DataType.DT_BOOL) {
			@SuppressWarnings("unchecked")
			Tensor<Boolean> tensor = (Tensor<Boolean>) keyToOutput(key);
			int shapeLength = tensor.shape().length;
			if (shapeLength == 0) {
				boolean b = tensor.booleanValue();
				return b ? 1L : 0L;
			} else {
				Object bArray = getBooleanArrayMultidimensional(key);
				boolean b = (boolean) ArrayUtil.firstElementValueOfMultidimArray(bArray);
				return b ? 1L : 0L;
			}
		} else {
			throw new TFException("getLong not implemented for '" + key + "' data type: " + dtype);
		}
	}

	/**
	 * Obtain the long array corresponding to the output key.
	 * 
	 * @param key
	 *            The output key
	 * @return The long array
	 */
	public long[] getLongArray(String key) {
		checkKey(key);
		TensorInfo ti = TFUtil.outputKeyToTensorInfo(key, model);
		DataType dtype = ti.getDtype();
		if (dtype == DataType.DT_FLOAT) {
			@SuppressWarnings("unchecked")
			Tensor<Float> tensor = (Tensor<Float>) keyToOutput(key);
			float[] f = ArrayUtil.floatTensorToFloatArray(tensor);
			return ArrayUtil.fToL(f);
		} else if (dtype == DataType.DT_DOUBLE) {
			@SuppressWarnings("unchecked")
			Tensor<Double> tensor = (Tensor<Double>) keyToOutput(key);
			double[] d = ArrayUtil.doubleTensorToDoubleArray(tensor);
			return ArrayUtil.dToL(d);
		} else if (dtype == DataType.DT_INT64) {
			@SuppressWarnings("unchecked")
			Tensor<Long> tensor = (Tensor<Long>) keyToOutput(key);
			return ArrayUtil.longTensorToLongArray(tensor);
		} else if (dtype == DataType.DT_INT32) {
			@SuppressWarnings("unchecked")
			Tensor<Integer> tensor = (Tensor<Integer>) keyToOutput(key);
			int[] i = ArrayUtil.intTensorToIntArray(tensor);
			long[] l = ArrayUtil.iToL(i);
			// alternative option
			// long[] l = (long[]) ArrayUtil.convertArrayType(i, long.class);
			return l;
		} else if (dtype == DataType.DT_UINT8) {
			@SuppressWarnings("unchecked")
			Tensor<UInt8> tensor = (Tensor<UInt8>) keyToOutput(key);
			byte[] b = ArrayUtil.uint8TensorToByteArray(tensor);
			long[] l = (long[]) ArrayUtil.convertUnsignedArrayType(b, long.class);
			return l;
		} else if (dtype == DataType.DT_STRING) {
			String[] s = getStringArray(key);
			long[] l = (long[]) ArrayUtil.convertArrayType(s, long.class);
			return l;
		} else if (dtype == DataType.DT_BOOL) {
			@SuppressWarnings("unchecked")
			Tensor<Boolean> tensor = (Tensor<Boolean>) keyToOutput(key);
			boolean[] b = ArrayUtil.booleanTensorToBooleanArray(tensor);
			long[] l = (long[]) ArrayUtil.convertArrayType(b, long.class);
			return l;
		} else {
			throw new TFException("getLongArray not implemented for '" + key + "' data type: " + dtype);
		}
	}

	/**
	 * Obtain the multidimensional long array corresponding to the output key.
	 * 
	 * @param key
	 *            The output key
	 * @return The multidimensional long array
	 */
	public Object getLongArrayMultidimensional(String key) {
		checkKey(key);
		TensorInfo ti = TFUtil.outputKeyToTensorInfo(key, model);
		DataType dtype = ti.getDtype();
		if (dtype == DataType.DT_FLOAT) {
			@SuppressWarnings("unchecked")
			Tensor<Float> tensor = (Tensor<Float>) keyToOutput(key);
			Object f = ArrayUtil.floatTensorToMultidimensionalFloatArray(tensor);
			Object l = ArrayUtil.convertArrayType(f, long.class);
			return l;
		} else if (dtype == DataType.DT_DOUBLE) {
			@SuppressWarnings("unchecked")
			Tensor<Double> tensor = (Tensor<Double>) keyToOutput(key);
			Object d = ArrayUtil.doubleTensorToMultidimensionalDoubleArray(tensor);
			Object l = ArrayUtil.convertArrayType(d, long.class);
			return l;
		} else if (dtype == DataType.DT_INT64) {
			@SuppressWarnings("unchecked")
			Tensor<Long> tensor = (Tensor<Long>) keyToOutput(key);
			Object l = ArrayUtil.longTensorToMultidimensionalLongArray(tensor);
			return l;
		} else if (dtype == DataType.DT_INT32) {
			@SuppressWarnings("unchecked")
			Tensor<Integer> tensor = (Tensor<Integer>) keyToOutput(key);
			Object i = ArrayUtil.intTensorToMultidimensionalIntArray(tensor);
			Object l = ArrayUtil.convertArrayType(i, long.class);
			return l;
		} else if (dtype == DataType.DT_UINT8) {
			@SuppressWarnings("unchecked")
			Tensor<UInt8> tensor = (Tensor<UInt8>) keyToOutput(key);
			Object b = ArrayUtil.uint8TensorToMultidimensionalByteArray(tensor);
			Object l = ArrayUtil.convertUnsignedArrayType(b, long.class);
			return l;
		} else if (dtype == DataType.DT_STRING) {
			Object s = getStringArrayMultidimensional(key);
			Object l = ArrayUtil.convertArrayType(s, long.class);
			return l;
		} else if (dtype == DataType.DT_BOOL) {
			@SuppressWarnings("unchecked")
			Tensor<Boolean> tensor = (Tensor<Boolean>) keyToOutput(key);
			Object b = ArrayUtil.booleanTensorToMultidimensionalBooleanArray(tensor);
			Object l = ArrayUtil.convertArrayType(b, long.class);
			return l;
		} else {
			throw new TFException("getLongArrayMultidimensional not implemented for '" + key + "' data type: " + dtype);
		}
	}

	/**
	 * Obtain the float value corresponding to the output key.
	 * 
	 * @param key
	 *            The output key
	 * @return The float value
	 */
	public float getFloat(String key) {
		checkKey(key);
		TensorInfo ti = TFUtil.outputKeyToTensorInfo(key, model);
		DataType dtype = ti.getDtype();
		if (dtype == DataType.DT_FLOAT) {
			@SuppressWarnings("unchecked")
			Tensor<Float> tensor = (Tensor<Float>) keyToOutput(key);
			int shapeLength = tensor.shape().length;
			if (shapeLength == 0) {
				float f = tensor.floatValue();
				return f;
			} else {
				Object fArray = getFloatArrayMultidimensional(key);
				float f = (float) ArrayUtil.firstElementValueOfMultidimArray(fArray);
				return f;
			}
		} else if (dtype == DataType.DT_DOUBLE) {
			@SuppressWarnings("unchecked")
			Tensor<Double> tensor = (Tensor<Double>) keyToOutput(key);
			int shapeLength = tensor.shape().length;
			if (shapeLength == 0) {
				float f = (float) tensor.doubleValue();
				return f;
			} else {
				Object dArray = getDoubleArrayMultidimensional(key);
				float f = (float) (double) ArrayUtil.firstElementValueOfMultidimArray(dArray);
				return f;
			}
		} else if (dtype == DataType.DT_INT64) {
			@SuppressWarnings("unchecked")
			Tensor<Long> tensor = (Tensor<Long>) keyToOutput(key);
			int shapeLength = tensor.shape().length;
			if (shapeLength == 0) {
				float f = (float) tensor.longValue();
				return f;
			} else {
				Object lArray = getLongArrayMultidimensional(key);
				float f = (float) (long) ArrayUtil.firstElementValueOfMultidimArray(lArray);
				return f;
			}
		} else if (dtype == DataType.DT_INT32) {
			@SuppressWarnings("unchecked")
			Tensor<Integer> tensor = (Tensor<Integer>) keyToOutput(key);
			int shapeLength = tensor.shape().length;
			if (shapeLength == 0) {
				float f = (float) tensor.intValue();
				return f;
			} else {
				Object iArray = getIntArrayMultidimensional(key);
				float f = (float) (int) ArrayUtil.firstElementValueOfMultidimArray(iArray);
				return f;
			}
		} else if (dtype == DataType.DT_UINT8) {
			@SuppressWarnings("unchecked")
			Tensor<UInt8> tensor = (Tensor<UInt8>) keyToOutput(key);
			int shapeLength = tensor.shape().length;
			if (shapeLength == 0) {
				byte b = TFUtil.byteScalarFromUInt8Tensor(tensor);
				int i = b & 0xFF; // unsigned
				return (float) i;
			} else {
				Object bArray = getByteArrayMultidimensional(key);
				float f = (float) ((byte) ArrayUtil.firstElementValueOfMultidimArray(bArray) & 0xFF);
				return f;
			}
		} else if (dtype == DataType.DT_STRING) {
			@SuppressWarnings("unchecked")
			Tensor<String> tensor = (Tensor<String>) keyToOutput(key);
			int shapeLength = tensor.shape().length;
			if (shapeLength == 0) {
				float f = Float.parseFloat(new String(tensor.bytesValue()));
				return f;
			} else {
				Object sArray = getStringArrayMultidimensional(key);
				String s = (String) ArrayUtil.firstElementValueOfMultidimArray(sArray);
				float f = Float.parseFloat(s);
				return f;
			}
		} else if (dtype == DataType.DT_BOOL) {
			@SuppressWarnings("unchecked")
			Tensor<Boolean> tensor = (Tensor<Boolean>) keyToOutput(key);
			int shapeLength = tensor.shape().length;
			if (shapeLength == 0) {
				boolean b = tensor.booleanValue();
				return b ? 1.0f : 0.0f;
			} else {
				Object bArray = getBooleanArrayMultidimensional(key);
				boolean b = (boolean) ArrayUtil.firstElementValueOfMultidimArray(bArray);
				return b ? 1.0f : 0.0f;
			}
		} else {
			throw new TFException("getFloat not implemented for '" + key + "' data type: " + dtype);
		}
	}

	/**
	 * Obtain the float array corresponding to the output key.
	 * 
	 * @param key
	 *            The output key
	 * @return The float array
	 */
	public float[] getFloatArray(String key) {
		checkKey(key);
		TensorInfo ti = TFUtil.outputKeyToTensorInfo(key, model);
		DataType dtype = ti.getDtype();
		if (dtype == DataType.DT_FLOAT) {
			@SuppressWarnings("unchecked")
			Tensor<Float> tensor = (Tensor<Float>) keyToOutput(key);
			return ArrayUtil.floatTensorToFloatArray(tensor);
		} else if (dtype == DataType.DT_DOUBLE) {
			@SuppressWarnings("unchecked")
			Tensor<Double> tensor = (Tensor<Double>) keyToOutput(key);
			double[] d = ArrayUtil.doubleTensorToDoubleArray(tensor);
			return ArrayUtil.dToF(d);
		} else if (dtype == DataType.DT_INT64) {
			@SuppressWarnings("unchecked")
			Tensor<Long> tensor = (Tensor<Long>) keyToOutput(key);
			long[] l = ArrayUtil.longTensorToLongArray(tensor);
			return ArrayUtil.lToF(l);
		} else if (dtype == DataType.DT_INT32) {
			@SuppressWarnings("unchecked")
			Tensor<Integer> tensor = (Tensor<Integer>) keyToOutput(key);
			int[] i = ArrayUtil.intTensorToIntArray(tensor);
			return ArrayUtil.iToF(i);
		} else if (dtype == DataType.DT_UINT8) {
			@SuppressWarnings("unchecked")
			Tensor<UInt8> tensor = (Tensor<UInt8>) keyToOutput(key);
			byte[] b = ArrayUtil.uint8TensorToByteArray(tensor);
			float[] f = (float[]) ArrayUtil.convertUnsignedArrayType(b, float.class);
			return f;
		} else if (dtype == DataType.DT_STRING) {
			String[] s = getStringArray(key);
			float[] f = (float[]) ArrayUtil.convertArrayType(s, float.class);
			return f;
		} else if (dtype == DataType.DT_BOOL) {
			@SuppressWarnings("unchecked")
			Tensor<Boolean> tensor = (Tensor<Boolean>) keyToOutput(key);
			boolean[] b = ArrayUtil.booleanTensorToBooleanArray(tensor);
			float[] f = (float[]) ArrayUtil.convertArrayType(b, float.class);
			return f;
		} else {
			throw new TFException("getFloatArray not implemented for '" + key + "' data type: " + dtype);
		}
	}

	/**
	 * Obtain the multidimensional float array corresponding to the output key.
	 * 
	 * @param key
	 *            The output key
	 * @return The multidimensional float array
	 */
	public Object getFloatArrayMultidimensional(String key) {
		checkKey(key);
		TensorInfo ti = TFUtil.outputKeyToTensorInfo(key, model);
		DataType dtype = ti.getDtype();
		if (dtype == DataType.DT_FLOAT) {
			@SuppressWarnings("unchecked")
			Tensor<Float> tensor = (Tensor<Float>) keyToOutput(key);
			Object f = ArrayUtil.floatTensorToMultidimensionalFloatArray(tensor);
			return f;
		} else if (dtype == DataType.DT_DOUBLE) {
			@SuppressWarnings("unchecked")
			Tensor<Double> tensor = (Tensor<Double>) keyToOutput(key);
			Object d = ArrayUtil.doubleTensorToMultidimensionalDoubleArray(tensor);
			Object f = ArrayUtil.convertArrayType(d, float.class);
			return f;
		} else if (dtype == DataType.DT_INT64) {
			@SuppressWarnings("unchecked")
			Tensor<Long> tensor = (Tensor<Long>) keyToOutput(key);
			Object l = ArrayUtil.longTensorToMultidimensionalLongArray(tensor);
			Object f = ArrayUtil.convertArrayType(l, float.class);
			return f;
		} else if (dtype == DataType.DT_INT32) {
			@SuppressWarnings("unchecked")
			Tensor<Integer> tensor = (Tensor<Integer>) keyToOutput(key);
			Object i = ArrayUtil.intTensorToMultidimensionalIntArray(tensor);
			Object f = ArrayUtil.convertArrayType(i, float.class);
			return f;
		} else if (dtype == DataType.DT_UINT8) {
			@SuppressWarnings("unchecked")
			Tensor<UInt8> tensor = (Tensor<UInt8>) keyToOutput(key);
			Object b = ArrayUtil.uint8TensorToMultidimensionalByteArray(tensor);
			Object f = ArrayUtil.convertUnsignedArrayType(b, float.class);
			return f;
		} else if (dtype == DataType.DT_STRING) {
			Object s = getStringArrayMultidimensional(key);
			Object f = ArrayUtil.convertArrayType(s, float.class);
			return f;
		} else if (dtype == DataType.DT_BOOL) {
			@SuppressWarnings("unchecked")
			Tensor<Boolean> tensor = (Tensor<Boolean>) keyToOutput(key);
			Object b = ArrayUtil.booleanTensorToMultidimensionalBooleanArray(tensor);
			Object f = ArrayUtil.convertArrayType(b, float.class);
			return f;
		} else {
			throw new TFException(
					"getFloatArrayMultidimensional not implemented for '" + key + "' data type: " + dtype);
		}
	}

	/**
	 * Obtain the int value corresponding to the output key.
	 * 
	 * @param key
	 *            The output key
	 * @return The int value
	 */
	public int getInt(String key) {
		checkKey(key);
		TensorInfo ti = TFUtil.outputKeyToTensorInfo(key, model);
		DataType dtype = ti.getDtype();
		if (dtype == DataType.DT_FLOAT) {
			@SuppressWarnings("unchecked")
			Tensor<Float> tensor = (Tensor<Float>) keyToOutput(key);
			int shapeLength = tensor.shape().length;
			if (shapeLength == 0) {
				int i = (int) tensor.floatValue();
				return i;
			} else {
				Object fArray = getFloatArrayMultidimensional(key);
				int i = (int) (float) ArrayUtil.firstElementValueOfMultidimArray(fArray);
				return i;
			}
		} else if (dtype == DataType.DT_DOUBLE) {
			@SuppressWarnings("unchecked")
			Tensor<Double> tensor = (Tensor<Double>) keyToOutput(key);
			int shapeLength = tensor.shape().length;
			if (shapeLength == 0) {
				int i = (int) tensor.doubleValue();
				return i;
			} else {
				Object dArray = getDoubleArrayMultidimensional(key);
				int i = (int) (double) ArrayUtil.firstElementValueOfMultidimArray(dArray);
				return i;
			}
		} else if (dtype == DataType.DT_INT64) {
			@SuppressWarnings("unchecked")
			Tensor<Long> tensor = (Tensor<Long>) keyToOutput(key);
			int shapeLength = tensor.shape().length;
			if (shapeLength == 0) {
				int i = (int) tensor.longValue();
				return i;
			} else {
				Object lArray = getLongArrayMultidimensional(key);
				int i = (int) (long) ArrayUtil.firstElementValueOfMultidimArray(lArray);
				return i;
			}
		} else if (dtype == DataType.DT_INT32) {
			@SuppressWarnings("unchecked")
			Tensor<Integer> tensor = (Tensor<Integer>) keyToOutput(key);
			int shapeLength = tensor.shape().length;
			if (shapeLength == 0) {
				int i = tensor.intValue();
				return i;
			} else {
				Object iArray = getIntArrayMultidimensional(key);
				int i = (int) ArrayUtil.firstElementValueOfMultidimArray(iArray);
				return i;
			}
		} else if (dtype == DataType.DT_UINT8) {
			@SuppressWarnings("unchecked")
			Tensor<UInt8> tensor = (Tensor<UInt8>) keyToOutput(key);
			int shapeLength = tensor.shape().length;
			if (shapeLength == 0) {
				byte b = TFUtil.byteScalarFromUInt8Tensor(tensor);
				return (int) b & 0xFF; // unsigned
			} else {
				Object bArray = getByteArrayMultidimensional(key);
				int i = (int) ((byte) ArrayUtil.firstElementValueOfMultidimArray(bArray) & 0xFF);
				return i;
			}
		} else if (dtype == DataType.DT_STRING) {
			@SuppressWarnings("unchecked")
			Tensor<String> tensor = (Tensor<String>) keyToOutput(key);
			int shapeLength = tensor.shape().length;
			if (shapeLength == 0) {
				int i = Integer.parseInt(new String(tensor.bytesValue()));
				return i;
			} else {
				Object sArray = getStringArrayMultidimensional(key);
				String s = (String) ArrayUtil.firstElementValueOfMultidimArray(sArray);
				int i = Integer.parseInt(s);
				return i;
			}
		} else if (dtype == DataType.DT_BOOL) {
			@SuppressWarnings("unchecked")
			Tensor<Boolean> tensor = (Tensor<Boolean>) keyToOutput(key);
			int shapeLength = tensor.shape().length;
			if (shapeLength == 0) {
				boolean b = tensor.booleanValue();
				return b ? 1 : 0;
			} else {
				Object bArray = getBooleanArrayMultidimensional(key);
				boolean b = (boolean) ArrayUtil.firstElementValueOfMultidimArray(bArray);
				return b ? 1 : 0;
			}
		} else {
			throw new TFException("getInt not implemented for '" + key + "' data type: " + dtype);
		}
	}

	/**
	 * Obtain the int array corresponding to the output key.
	 * 
	 * @param key
	 *            The output key
	 * @return The int array
	 */
	public int[] getIntArray(String key) {
		checkKey(key);
		TensorInfo ti = TFUtil.outputKeyToTensorInfo(key, model);
		DataType dtype = ti.getDtype();
		if (dtype == DataType.DT_FLOAT) {
			@SuppressWarnings("unchecked")
			Tensor<Float> tensor = (Tensor<Float>) keyToOutput(key);
			float[] f = ArrayUtil.floatTensorToFloatArray(tensor);
			return ArrayUtil.fToI(f);
		} else if (dtype == DataType.DT_DOUBLE) {
			@SuppressWarnings("unchecked")
			Tensor<Double> tensor = (Tensor<Double>) keyToOutput(key);
			double[] d = ArrayUtil.doubleTensorToDoubleArray(tensor);
			return ArrayUtil.dToI(d);
		} else if (dtype == DataType.DT_INT64) {
			@SuppressWarnings("unchecked")
			Tensor<Long> tensor = (Tensor<Long>) keyToOutput(key);
			long[] l = ArrayUtil.longTensorToLongArray(tensor);
			return ArrayUtil.lToI(l);
		} else if (dtype == DataType.DT_INT32) {
			@SuppressWarnings("unchecked")
			Tensor<Integer> tensor = (Tensor<Integer>) keyToOutput(key);
			int[] i = ArrayUtil.intTensorToIntArray(tensor);
			return i;
		} else if (dtype == DataType.DT_UINT8) {
			@SuppressWarnings("unchecked")
			Tensor<UInt8> tensor = (Tensor<UInt8>) keyToOutput(key);
			byte[] b = ArrayUtil.uint8TensorToByteArray(tensor);
			int[] i = (int[]) ArrayUtil.convertUnsignedArrayType(b, int.class);
			return i;
		} else if (dtype == DataType.DT_STRING) {
			String[] s = getStringArray(key);
			int[] i = (int[]) ArrayUtil.convertArrayType(s, int.class);
			return i;
		} else if (dtype == DataType.DT_BOOL) {
			@SuppressWarnings("unchecked")
			Tensor<Boolean> tensor = (Tensor<Boolean>) keyToOutput(key);
			boolean[] b = ArrayUtil.booleanTensorToBooleanArray(tensor);
			int[] i = (int[]) ArrayUtil.convertArrayType(b, int.class);
			return i;
		} else {
			throw new TFException("getIntArray not implemented for '" + key + "' data type: " + dtype);
		}
	}

	/**
	 * Obtain the multidimensional int array corresponding to the output key.
	 * 
	 * @param key
	 *            The output key
	 * @return The multidimensional int array
	 */
	public Object getIntArrayMultidimensional(String key) {
		checkKey(key);
		TensorInfo ti = TFUtil.outputKeyToTensorInfo(key, model.metaGraphDef());
		DataType dtype = ti.getDtype();
		if (dtype == DataType.DT_FLOAT) {
			@SuppressWarnings("unchecked")
			Tensor<Float> tensor = (Tensor<Float>) keyToOutput(key);
			Object f = ArrayUtil.floatTensorToMultidimensionalFloatArray(tensor);
			Object i = ArrayUtil.convertArrayType(f, int.class);
			return i;
		} else if (dtype == DataType.DT_DOUBLE) {
			@SuppressWarnings("unchecked")
			Tensor<Double> tensor = (Tensor<Double>) keyToOutput(key);
			Object d = ArrayUtil.doubleTensorToMultidimensionalDoubleArray(tensor);
			Object i = ArrayUtil.convertArrayType(d, int.class);
			return i;
		} else if (dtype == DataType.DT_INT64) {
			@SuppressWarnings("unchecked")
			Tensor<Long> tensor = (Tensor<Long>) keyToOutput(key);
			Object l = ArrayUtil.longTensorToMultidimensionalLongArray(tensor);
			Object i = ArrayUtil.convertArrayType(l, int.class);
			return i;
		} else if (dtype == DataType.DT_INT32) {
			@SuppressWarnings("unchecked")
			Tensor<Integer> tensor = (Tensor<Integer>) keyToOutput(key);
			Object i = ArrayUtil.intTensorToMultidimensionalIntArray(tensor);
			return i;
		} else if (dtype == DataType.DT_UINT8) {
			@SuppressWarnings("unchecked")
			Tensor<UInt8> tensor = (Tensor<UInt8>) keyToOutput(key);
			Object b = ArrayUtil.uint8TensorToMultidimensionalByteArray(tensor);
			Object i = ArrayUtil.convertUnsignedArrayType(b, int.class);
			return i;
		} else if (dtype == DataType.DT_STRING) {
			Object s = getStringArrayMultidimensional(key);
			Object i = ArrayUtil.convertArrayType(s, int.class);
			return i;
		} else if (dtype == DataType.DT_BOOL) {
			@SuppressWarnings("unchecked")
			Tensor<Boolean> tensor = (Tensor<Boolean>) keyToOutput(key);
			Object b = ArrayUtil.booleanTensorToMultidimensionalBooleanArray(tensor);
			Object i = ArrayUtil.convertArrayType(b, int.class);
			return i;
		} else {
			throw new TFException("getIntArrayMultidimensional not implemented for '" + key + "' data type: " + dtype);
		}
	}

	/**
	 * Obtain the double value corresponding to the output key.
	 * 
	 * @param key
	 *            The output key
	 * @return The double value
	 */
	public double getDouble(String key) {
		checkKey(key);
		TensorInfo ti = TFUtil.outputKeyToTensorInfo(key, model);
		DataType dtype = ti.getDtype();
		if (dtype == DataType.DT_FLOAT) {
			@SuppressWarnings("unchecked")
			Tensor<Float> tensor = (Tensor<Float>) keyToOutput(key);
			int shapeLength = tensor.shape().length;
			if (shapeLength == 0) {
				double d = (double) tensor.floatValue();
				return d;
			} else {
				Object fArray = getFloatArrayMultidimensional(key);
				double d = (double) (float) ArrayUtil.firstElementValueOfMultidimArray(fArray);
				return d;
			}
		} else if (dtype == DataType.DT_DOUBLE) {
			@SuppressWarnings("unchecked")
			Tensor<Double> tensor = (Tensor<Double>) keyToOutput(key);
			int shapeLength = tensor.shape().length;
			if (shapeLength == 0) {
				double d = tensor.doubleValue();
				return d;
			} else {
				Object dArray = getDoubleArrayMultidimensional(key);
				double d = (double) ArrayUtil.firstElementValueOfMultidimArray(dArray);
				return d;
			}
		} else if (dtype == DataType.DT_INT64) {
			@SuppressWarnings("unchecked")
			Tensor<Long> tensor = (Tensor<Long>) keyToOutput(key);
			int shapeLength = tensor.shape().length;
			if (shapeLength == 0) {
				double d = (double) tensor.longValue();
				return d;
			} else {
				Object lArray = getLongArrayMultidimensional(key);
				double d = (double) (long) ArrayUtil.firstElementValueOfMultidimArray(lArray);
				return d;
			}
		} else if (dtype == DataType.DT_INT32) {
			@SuppressWarnings("unchecked")
			Tensor<Integer> tensor = (Tensor<Integer>) keyToOutput(key);
			int shapeLength = tensor.shape().length;
			if (shapeLength == 0) {
				double d = (double) tensor.intValue();
				return d;
			} else {
				Object iArray = getIntArrayMultidimensional(key);
				double d = (double) (int) ArrayUtil.firstElementValueOfMultidimArray(iArray);
				return d;
			}
		} else if (dtype == DataType.DT_UINT8) {
			@SuppressWarnings("unchecked")
			Tensor<UInt8> tensor = (Tensor<UInt8>) keyToOutput(key);
			int shapeLength = tensor.shape().length;
			if (shapeLength == 0) {
				byte b = TFUtil.byteScalarFromUInt8Tensor(tensor);
				int i = b & 0xFF; // unsigned
				return (double) i;
			} else {
				Object bArray = getByteArrayMultidimensional(key);
				double d = (double) ((byte) ArrayUtil.firstElementValueOfMultidimArray(bArray) & 0xFF);
				return d;
			}
		} else if (dtype == DataType.DT_STRING) {
			@SuppressWarnings("unchecked")
			Tensor<String> tensor = (Tensor<String>) keyToOutput(key);
			int shapeLength = tensor.shape().length;
			if (shapeLength == 0) {
				double d = Double.parseDouble(new String(tensor.bytesValue()));
				return d;
			} else {
				Object sArray = getStringArrayMultidimensional(key);
				String s = (String) ArrayUtil.firstElementValueOfMultidimArray(sArray);
				double d = Double.parseDouble(s);
				return d;
			}
		} else if (dtype == DataType.DT_BOOL) {
			@SuppressWarnings("unchecked")
			Tensor<Boolean> tensor = (Tensor<Boolean>) keyToOutput(key);
			int shapeLength = tensor.shape().length;
			if (shapeLength == 0) {
				boolean b = tensor.booleanValue();
				return b ? 1.0d : 0.0d;
			} else {
				Object bArray = getBooleanArrayMultidimensional(key);
				boolean b = (boolean) ArrayUtil.firstElementValueOfMultidimArray(bArray);
				return b ? 1.0d : 0.0d;
			}
		} else {
			throw new TFException("getDouble not implemented for '" + key + "' data type: " + dtype);
		}
	}

	/**
	 * Obtain the double array corresponding to the output key.
	 * 
	 * @param key
	 *            The output key
	 * @return The double array
	 */
	public double[] getDoubleArray(String key) {
		checkKey(key);
		TensorInfo ti = TFUtil.outputKeyToTensorInfo(key, model);
		DataType dtype = ti.getDtype();
		if (dtype == DataType.DT_FLOAT) {
			@SuppressWarnings("unchecked")
			Tensor<Float> tensor = (Tensor<Float>) keyToOutput(key);
			float[] f = ArrayUtil.floatTensorToFloatArray(tensor);
			double[] d = (double[]) ArrayUtil.convertArrayType(f, double.class);
			return d;
		} else if (dtype == DataType.DT_DOUBLE) {
			@SuppressWarnings("unchecked")
			Tensor<Double> tensor = (Tensor<Double>) keyToOutput(key);
			return ArrayUtil.doubleTensorToDoubleArray(tensor);
		} else if (dtype == DataType.DT_INT64) {
			@SuppressWarnings("unchecked")
			Tensor<Long> tensor = (Tensor<Long>) keyToOutput(key);
			long[] l = ArrayUtil.longTensorToLongArray(tensor);
			return ArrayUtil.lToD(l);
		} else if (dtype == DataType.DT_INT32) {
			@SuppressWarnings("unchecked")
			Tensor<Integer> tensor = (Tensor<Integer>) keyToOutput(key);
			int[] i = ArrayUtil.intTensorToIntArray(tensor);
			return ArrayUtil.iToD(i);
		} else if (dtype == DataType.DT_UINT8) {
			@SuppressWarnings("unchecked")
			Tensor<UInt8> tensor = (Tensor<UInt8>) keyToOutput(key);
			byte[] b = ArrayUtil.uint8TensorToByteArray(tensor);
			double[] d = (double[]) ArrayUtil.convertUnsignedArrayType(b, double.class);
			return d;
		} else if (dtype == DataType.DT_STRING) {
			String[] s = getStringArray(key);
			double[] d = (double[]) ArrayUtil.convertArrayType(s, double.class);
			return d;
		} else if (dtype == DataType.DT_BOOL) {
			@SuppressWarnings("unchecked")
			Tensor<Boolean> tensor = (Tensor<Boolean>) keyToOutput(key);
			boolean[] b = ArrayUtil.booleanTensorToBooleanArray(tensor);
			double[] d = (double[]) ArrayUtil.convertArrayType(b, double.class);
			return d;
		} else {
			throw new TFException("getDoubleArray not implemented for '" + key + "' data type: " + dtype);
		}
	}

	/**
	 * Obtain the multidimensional double array corresponding to the output key.
	 * 
	 * @param key
	 *            The output key
	 * @return The multidimensional double array
	 */
	public Object getDoubleArrayMultidimensional(String key) {
		checkKey(key);
		TensorInfo ti = TFUtil.outputKeyToTensorInfo(key, model);
		DataType dtype = ti.getDtype();
		if (dtype == DataType.DT_FLOAT) {
			@SuppressWarnings("unchecked")
			Tensor<Float> tensor = (Tensor<Float>) keyToOutput(key);
			Object f = ArrayUtil.floatTensorToMultidimensionalFloatArray(tensor);
			Object d = ArrayUtil.convertArrayType(f, double.class);
			return d;
		} else if (dtype == DataType.DT_DOUBLE) {
			@SuppressWarnings("unchecked")
			Tensor<Double> tensor = (Tensor<Double>) keyToOutput(key);
			Object d = ArrayUtil.doubleTensorToMultidimensionalDoubleArray(tensor);
			return d;
		} else if (dtype == DataType.DT_INT64) {
			@SuppressWarnings("unchecked")
			Tensor<Long> tensor = (Tensor<Long>) keyToOutput(key);
			Object l = ArrayUtil.longTensorToMultidimensionalLongArray(tensor);
			Object d = ArrayUtil.convertArrayType(l, double.class);
			return d;
		} else if (dtype == DataType.DT_INT32) {
			@SuppressWarnings("unchecked")
			Tensor<Integer> tensor = (Tensor<Integer>) keyToOutput(key);
			Object i = ArrayUtil.intTensorToMultidimensionalIntArray(tensor);
			Object d = ArrayUtil.convertArrayType(i, double.class);
			return d;
		} else if (dtype == DataType.DT_UINT8) {
			@SuppressWarnings("unchecked")
			Tensor<UInt8> tensor = (Tensor<UInt8>) keyToOutput(key);
			Object b = ArrayUtil.uint8TensorToMultidimensionalByteArray(tensor);
			Object d = ArrayUtil.convertUnsignedArrayType(b, double.class);
			return d;
		} else if (dtype == DataType.DT_STRING) {
			Object s = getStringArrayMultidimensional(key);
			Object d = ArrayUtil.convertArrayType(s, double.class);
			return d;
		} else if (dtype == DataType.DT_BOOL) {
			@SuppressWarnings("unchecked")
			Tensor<Boolean> tensor = (Tensor<Boolean>) keyToOutput(key);
			Object b = ArrayUtil.booleanTensorToMultidimensionalBooleanArray(tensor);
			Object d = ArrayUtil.convertArrayType(b, double.class);
			return d;
		} else {
			throw new TFException(
					"getDoubleArrayMultidimensional not implemented for '" + key + "' data type: " + dtype);
		}
	}

	/**
	 * Obtain the corresponding output value (Tensor) for an output key.
	 * 
	 * @param key
	 *            The output key.
	 * @return The output value (Tensor) corresponding to an output key.
	 */
	private Object keyToOutput(String key) {
		return outputNameToValue.get(outputKeyToName.get(key));
	}

	/**
	 * Obtain the String array corresponding to the output key.
	 * 
	 * @param key
	 *            The output key
	 * @return The String array
	 */
	public String[] getStringArray(String key) {
		checkKey(key);
		TensorInfo ti = TFUtil.outputKeyToTensorInfo(key, model);
		DataType dtype = ti.getDtype();
		if (dtype == DataType.DT_STRING) {
			@SuppressWarnings("unchecked")
			Tensor<String> tensor = (Tensor<String>) keyToOutput(key);
			int length = tensor.shape().length;
			if (length == 1) {
				String[] s = (String[]) getStringArrayMultidimensional(key);
				return s;
			} else {
				String[][] multi = (String[][]) getStringArrayMultidimensional(key);
				String[] s = Arrays.stream(multi).flatMap(x -> Arrays.stream(x)).toArray(String[]::new);
				return s;
			}
		} else if (dtype == DataType.DT_INT64) {
			@SuppressWarnings("unchecked")
			Tensor<Long> tensor = (Tensor<Long>) keyToOutput(key);
			long[] l = ArrayUtil.longTensorToLongArray(tensor);
			String[] s = (String[]) ArrayUtil.convertArrayType(l, String.class);
			return s;
		} else if (dtype == DataType.DT_INT32) {
			@SuppressWarnings("unchecked")
			Tensor<Integer> tensor = (Tensor<Integer>) keyToOutput(key);
			int[] i = ArrayUtil.intTensorToIntArray(tensor);
			String[] s = (String[]) ArrayUtil.convertArrayType(i, String.class);
			return s;
		} else if (dtype == DataType.DT_UINT8) {
			@SuppressWarnings("unchecked")
			Tensor<UInt8> tensor = (Tensor<UInt8>) keyToOutput(key);
			byte[] b = ArrayUtil.uint8TensorToByteArray(tensor);
			String[] s = (String[]) ArrayUtil.convertUnsignedArrayType(b, String.class);
			return s;
		} else if (dtype == DataType.DT_FLOAT) {
			@SuppressWarnings("unchecked")
			Tensor<Float> tensor = (Tensor<Float>) keyToOutput(key);
			float[] f = ArrayUtil.floatTensorToFloatArray(tensor);
			String[] s = (String[]) ArrayUtil.convertArrayType(f, String.class);
			return s;
		} else if (dtype == DataType.DT_DOUBLE) {
			@SuppressWarnings("unchecked")
			Tensor<Double> tensor = (Tensor<Double>) keyToOutput(key);
			double[] d = ArrayUtil.doubleTensorToDoubleArray(tensor);
			String[] s = (String[]) ArrayUtil.convertArrayType(d, String.class);
			return s;
		} else if (dtype == DataType.DT_BOOL) {
			@SuppressWarnings("unchecked")
			Tensor<Boolean> tensor = (Tensor<Boolean>) keyToOutput(key);
			boolean[] b = ArrayUtil.booleanTensorToBooleanArray(tensor);
			String[] s = (String[]) ArrayUtil.convertArrayType(b, String.class);
			return s;
		} else {
			throw new TFException("getStringArray not implemented for '" + key + "' data type: " + dtype);
		}
	}

	/**
	 * Obtain the multidimensional String array corresponding to the output key.
	 * 
	 * @param key
	 *            The output key
	 * @return The multidimensional String array
	 */
	public Object getStringArrayMultidimensional(String key) {
		checkKey(key);
		TensorInfo ti = TFUtil.outputKeyToTensorInfo(key, model);
		DataType dtype = ti.getDtype();
		if (dtype == DataType.DT_STRING) {
			@SuppressWarnings("unchecked")
			Tensor<String> tensor = (Tensor<String>) keyToOutput(key);
			Object s = ArrayUtil.stringTensorToMultidimensionalStringArray(tensor);
			return s;
		} else if (dtype == DataType.DT_INT64) {
			@SuppressWarnings("unchecked")
			Tensor<Long> tensor = (Tensor<Long>) keyToOutput(key);
			Object l = ArrayUtil.longTensorToMultidimensionalLongArray(tensor);
			Object s = ArrayUtil.convertArrayType(l, String.class);
			return s;
		} else if (dtype == DataType.DT_INT32) {
			@SuppressWarnings("unchecked")
			Tensor<Integer> tensor = (Tensor<Integer>) keyToOutput(key);
			Object i = ArrayUtil.intTensorToMultidimensionalIntArray(tensor);
			Object s = ArrayUtil.convertArrayType(i, String.class);
			return s;
		} else if (dtype == DataType.DT_UINT8) {
			@SuppressWarnings("unchecked")
			Tensor<UInt8> tensor = (Tensor<UInt8>) keyToOutput(key);
			Object b = ArrayUtil.uint8TensorToMultidimensionalByteArray(tensor);
			Object s = ArrayUtil.convertUnsignedArrayType(b, String.class);
			return s;
		} else if (dtype == DataType.DT_FLOAT) {
			@SuppressWarnings("unchecked")
			Tensor<Float> tensor = (Tensor<Float>) keyToOutput(key);
			Object f = ArrayUtil.floatTensorToMultidimensionalFloatArray(tensor);
			Object s = ArrayUtil.convertArrayType(f, String.class);
			return s;
		} else if (dtype == DataType.DT_DOUBLE) {
			@SuppressWarnings("unchecked")
			Tensor<Double> tensor = (Tensor<Double>) keyToOutput(key);
			Object d = ArrayUtil.doubleTensorToMultidimensionalDoubleArray(tensor);
			Object s = ArrayUtil.convertArrayType(d, String.class);
			return s;
		} else if (dtype == DataType.DT_BOOL) {
			@SuppressWarnings("unchecked")
			Tensor<Boolean> tensor = (Tensor<Boolean>) keyToOutput(key);
			Object b = ArrayUtil.booleanTensorToMultidimensionalBooleanArray(tensor);
			Object s = ArrayUtil.convertArrayType(b, String.class);
			return s;
		} else {
			throw new TFException(
					"getStringArrayMultidimensional not implemented for '" + key + "' data type: " + dtype);
		}
	}

	/**
	 * Obtain the String value corresponding to the output key.
	 * 
	 * @param key
	 *            The output key
	 * @return The String value
	 */
	public String getString(String key) {
		checkKey(key);
		TensorInfo ti = TFUtil.outputKeyToTensorInfo(key, model);
		DataType dtype = ti.getDtype();
		if (dtype == DataType.DT_FLOAT) {
			@SuppressWarnings("unchecked")
			Tensor<Float> tensor = (Tensor<Float>) keyToOutput(key);
			if (tensor.shape().length == 0) {
				return Float.toString(tensor.floatValue());
			} else {
				Object fArray = getFloatArrayMultidimensional(key);
				float f = (float) ArrayUtil.firstElementValueOfMultidimArray(fArray);
				return Float.toString(f);
			}
		} else if (dtype == DataType.DT_DOUBLE) {
			@SuppressWarnings("unchecked")
			Tensor<Double> tensor = (Tensor<Double>) keyToOutput(key);
			if (tensor.shape().length == 0) {
				return Double.toString(tensor.doubleValue());
			} else {
				Object dArray = getDoubleArrayMultidimensional(key);
				double d = (double) ArrayUtil.firstElementValueOfMultidimArray(dArray);
				return Double.toString(d);
			}
		} else if (dtype == DataType.DT_INT64) {
			@SuppressWarnings("unchecked")
			Tensor<Long> tensor = (Tensor<Long>) keyToOutput(key);
			if (tensor.shape().length == 0) {
				return Long.toString(tensor.longValue());
			} else {
				Object lArray = getLongArrayMultidimensional(key);
				long l = (long) ArrayUtil.firstElementValueOfMultidimArray(lArray);
				return Long.toString(l);
			}
		} else if (dtype == DataType.DT_INT32) {
			@SuppressWarnings("unchecked")
			Tensor<Integer> tensor = (Tensor<Integer>) keyToOutput(key);
			if (tensor.shape().length == 0) {
				return Integer.toString(tensor.intValue());
			} else {
				Object iArray = getIntArrayMultidimensional(key);
				int i = (int) ArrayUtil.firstElementValueOfMultidimArray(iArray);
				return Integer.toString(i);
			}
		} else if (dtype == DataType.DT_UINT8) {
			@SuppressWarnings("unchecked")
			Tensor<UInt8> tensor = (Tensor<UInt8>) keyToOutput(key);
			int shapeLength = tensor.shape().length;
			if (shapeLength == 0) {
				byte b = TFUtil.byteScalarFromUInt8Tensor(tensor);
				int i = (int) b & 0xFF; // unsigned
				return Integer.toString(i);
			} else {
				Object bArray = getByteArrayMultidimensional(key);
				int i = (int) ((byte) ArrayUtil.firstElementValueOfMultidimArray(bArray) & 0xFF);
				String s = Integer.toString(i);
				return s;
			}
		} else if (dtype == DataType.DT_STRING) {
			@SuppressWarnings("unchecked")
			Tensor<String> tensor = (Tensor<String>) keyToOutput(key);
			if (tensor.shape().length == 0) {
				return new String(tensor.bytesValue());
			} else {
				Object sArray = getStringArrayMultidimensional(key);
				String s = (String) ArrayUtil.firstElementValueOfMultidimArray(sArray);
				return s;
			}
		} else if (dtype == DataType.DT_BOOL) {
			@SuppressWarnings("unchecked")
			Tensor<Boolean> tensor = (Tensor<Boolean>) keyToOutput(key);
			if (tensor.shape().length == 0) {
				return Boolean.toString(tensor.booleanValue());
			} else {
				Object bArray = getBooleanArrayMultidimensional(key);
				boolean b = (boolean) ArrayUtil.firstElementValueOfMultidimArray(bArray);
				return Boolean.toString(b);
			}
		} else {
			throw new TFException("getString not implemented for '" + key + "' data type: " + dtype);
		}
	}

	/**
	 * Obtain the boolean value corresponding to the output key.
	 * 
	 * @param key
	 *            The output key
	 * @return The boolean value
	 */
	public boolean getBoolean(String key) {
		checkKey(key);
		TensorInfo ti = TFUtil.outputKeyToTensorInfo(key, model);
		DataType dtype = ti.getDtype();
		if (dtype == DataType.DT_BOOL) {
			@SuppressWarnings("unchecked")
			Tensor<Boolean> tensor = (Tensor<Boolean>) keyToOutput(key);
			int shapeLength = tensor.shape().length;
			if (shapeLength == 0) {
				boolean b = tensor.booleanValue();
				return b;
			} else {
				Object bArray = getBooleanArrayMultidimensional(key);
				boolean b = (boolean) ArrayUtil.firstElementValueOfMultidimArray(bArray);
				return b;
			}
		} else {
			throw new TFException("getBoolean not implemented for '" + key + "' data type: " + dtype);
		}
	}

	/**
	 * Obtain the byte value corresponding to the output key.
	 * 
	 * @param key
	 *            The output key
	 * @return The byte value
	 */
	public byte getByte(String key) {
		checkKey(key);
		TensorInfo ti = TFUtil.outputKeyToTensorInfo(key, model);
		DataType dtype = ti.getDtype();
		if (dtype == DataType.DT_BOOL) {
			@SuppressWarnings("unchecked")
			Tensor<Boolean> tensor = (Tensor<Boolean>) keyToOutput(key);
			int shapeLength = tensor.shape().length;
			if (shapeLength == 0) {
				boolean b = tensor.booleanValue();
				return b ? (byte) 1 : (byte) 0;
			} else {
				Object booleanArray = getBooleanArrayMultidimensional(key);
				boolean b = (boolean) ArrayUtil.firstElementValueOfMultidimArray(booleanArray);
				return b ? (byte) 1 : (byte) 0;
			}
		} else if (dtype == DataType.DT_UINT8) {
			@SuppressWarnings("unchecked")
			Tensor<UInt8> tensor = (Tensor<UInt8>) keyToOutput(key);
			int shapeLength = tensor.shape().length;
			if (shapeLength == 0) {
				byte b = TFUtil.byteScalarFromUInt8Tensor(tensor);
				return b;
			} else {
				Object bArray = getByteArrayMultidimensional(key);
				byte b = (byte) ArrayUtil.firstElementValueOfMultidimArray(bArray);
				return b;
			}
		} else if (dtype == DataType.DT_INT32) {
			@SuppressWarnings("unchecked")
			Tensor<Integer> tensor = (Tensor<Integer>) keyToOutput(key);
			int shapeLength = tensor.shape().length;
			if (shapeLength == 0) {
				byte b = ((Integer) tensor.intValue()).byteValue();
				return b;
			} else {
				Object iArray = getIntArrayMultidimensional(key);
				int i = (int) ArrayUtil.firstElementValueOfMultidimArray(iArray);
				return ((Integer) i).byteValue();
			}
		} else if (dtype == DataType.DT_INT64) {
			@SuppressWarnings("unchecked")
			Tensor<Integer> tensor = (Tensor<Integer>) keyToOutput(key);
			int shapeLength = tensor.shape().length;
			if (shapeLength == 0) {
				byte b = ((Long) tensor.longValue()).byteValue();
				return b;
			} else {
				Object lArray = getLongArrayMultidimensional(key);
				long l = (long) ArrayUtil.firstElementValueOfMultidimArray(lArray);
				return ((Long) l).byteValue();
			}
		} else if (dtype == DataType.DT_FLOAT) {
			@SuppressWarnings("unchecked")
			Tensor<Float> tensor = (Tensor<Float>) keyToOutput(key);
			int shapeLength = tensor.shape().length;
			if (shapeLength == 0) {
				byte b = ((Float) tensor.floatValue()).byteValue();
				return b;
			} else {
				Object fArray = getFloatArrayMultidimensional(key);
				float f = (float) ArrayUtil.firstElementValueOfMultidimArray(fArray);
				return ((Float) f).byteValue();
			}
		} else if (dtype == DataType.DT_DOUBLE) {
			@SuppressWarnings("unchecked")
			Tensor<Double> tensor = (Tensor<Double>) keyToOutput(key);
			int shapeLength = tensor.shape().length;
			if (shapeLength == 0) {
				byte b = ((Double) tensor.doubleValue()).byteValue();
				return b;
			} else {
				Object dArray = getDoubleArrayMultidimensional(key);
				double d = (double) ArrayUtil.firstElementValueOfMultidimArray(dArray);
				return ((Double) d).byteValue();
			}
		} else {
			throw new TFException("getByte not implemented for '" + key + "' data type: " + dtype);
		}
	}

	/**
	 * Obtain the boolean array corresponding to the output key.
	 * 
	 * @param key
	 *            The output key
	 * @return The boolean array
	 */
	public boolean[] getBooleanArray(String key) {
		checkKey(key);
		TensorInfo ti = TFUtil.outputKeyToTensorInfo(key, model);
		DataType dtype = ti.getDtype();
		if (dtype == DataType.DT_BOOL) {
			@SuppressWarnings("unchecked")
			Tensor<Boolean> tensor = (Tensor<Boolean>) keyToOutput(key);
			boolean[] b = ArrayUtil.booleanTensorToBooleanArray(tensor);
			return b;
		} else {
			throw new TFException("getBooleanArray not implemented for '" + key + "' data type: " + dtype);
		}
	}

	/**
	 * Obtain the byte array corresponding to the output key.
	 * 
	 * @param key
	 *            The output key
	 * @return The byte array
	 */
	public byte[] getByteArray(String key) {
		checkKey(key);
		TensorInfo ti = TFUtil.outputKeyToTensorInfo(key, model);
		DataType dtype = ti.getDtype();
		if (dtype == DataType.DT_BOOL) {
			@SuppressWarnings("unchecked")
			Tensor<Boolean> tensor = (Tensor<Boolean>) keyToOutput(key);
			byte[] b = ArrayUtil.booleanTensorToByteArray(tensor);
			return b;
		} else if (dtype == DataType.DT_UINT8) {
			@SuppressWarnings("unchecked")
			Tensor<UInt8> tensor = (Tensor<UInt8>) keyToOutput(key);
			byte[] b = ArrayUtil.uint8TensorToByteArray(tensor);
			return b;
		} else if (dtype == DataType.DT_INT32) {
			@SuppressWarnings("unchecked")
			Tensor<Integer> tensor = (Tensor<Integer>) keyToOutput(key);
			int[] i = ArrayUtil.intTensorToIntArray(tensor);
			byte[] b = (byte[]) ArrayUtil.convertArrayType(i, byte.class);
			return b;
		} else if (dtype == DataType.DT_INT64) {
			@SuppressWarnings("unchecked")
			Tensor<Long> tensor = (Tensor<Long>) keyToOutput(key);
			long[] l = ArrayUtil.longTensorToLongArray(tensor);
			byte[] b = (byte[]) ArrayUtil.convertArrayType(l, byte.class);
			return b;
		} else if (dtype == DataType.DT_FLOAT) {
			@SuppressWarnings("unchecked")
			Tensor<Float> tensor = (Tensor<Float>) keyToOutput(key);
			float[] f = ArrayUtil.floatTensorToFloatArray(tensor);
			byte[] b = (byte[]) ArrayUtil.convertArrayType(f, byte.class);
			return b;
		} else if (dtype == DataType.DT_DOUBLE) {
			@SuppressWarnings("unchecked")
			Tensor<Double> tensor = (Tensor<Double>) keyToOutput(key);
			double[] d = ArrayUtil.doubleTensorToDoubleArray(tensor);
			byte[] b = (byte[]) ArrayUtil.convertArrayType(d, byte.class);
			return b;
		} else {
			throw new TFException("getByteArray not implemented for '" + key + "' data type: " + dtype);
		}
	}

	/**
	 * Obtain the multidimensional boolean array corresponding to the output key.
	 * 
	 * @param key
	 *            The output key
	 * @return The multidimensional boolean array
	 */
	public Object getBooleanArrayMultidimensional(String key) {
		checkKey(key);
		TensorInfo ti = TFUtil.outputKeyToTensorInfo(key, model.metaGraphDef());
		DataType dtype = ti.getDtype();
		if (dtype == DataType.DT_BOOL) {
			@SuppressWarnings("unchecked")
			Tensor<Boolean> tensor = (Tensor<Boolean>) keyToOutput(key);
			Object b = ArrayUtil.booleanTensorToMultidimensionalBooleanArray(tensor);
			return b;
		} else {
			throw new TFException(
					"getBooleanArrayMultidimensional not implemented for '" + key + "' data type: " + dtype);
		}
	}

	/**
	 * Obtain the multidimensional byte array corresponding to the output key.
	 * 
	 * @param key
	 *            The output key
	 * @return The multidimensional byte array
	 */
	public Object getByteArrayMultidimensional(String key) {
		checkKey(key);
		TensorInfo ti = TFUtil.outputKeyToTensorInfo(key, model.metaGraphDef());
		DataType dtype = ti.getDtype();
		if (dtype == DataType.DT_BOOL) {
			@SuppressWarnings("unchecked")
			Tensor<Boolean> tensor = (Tensor<Boolean>) keyToOutput(key);
			Object booleanArray = ArrayUtil.booleanTensorToMultidimensionalBooleanArray(tensor);
			Object byteArray = ArrayUtil.convertArrayType(booleanArray, byte.class);
			return byteArray;
		} else if (dtype == DataType.DT_UINT8) {
			@SuppressWarnings("unchecked")
			Tensor<UInt8> tensor = (Tensor<UInt8>) keyToOutput(key);
			Object byteArray = ArrayUtil.uint8TensorToMultidimensionalByteArray(tensor);
			return byteArray;
		} else if (dtype == DataType.DT_INT32) {
			@SuppressWarnings("unchecked")
			Tensor<Integer> tensor = (Tensor<Integer>) keyToOutput(key);
			Object i = ArrayUtil.intTensorToMultidimensionalIntArray(tensor);
			Object b = ArrayUtil.convertArrayType(i, byte.class);
			return b;
		} else if (dtype == DataType.DT_INT64) {
			@SuppressWarnings("unchecked")
			Tensor<Long> tensor = (Tensor<Long>) keyToOutput(key);
			Object l = ArrayUtil.longTensorToMultidimensionalLongArray(tensor);
			Object b = ArrayUtil.convertArrayType(l, byte.class);
			return b;
		} else if (dtype == DataType.DT_FLOAT) {
			@SuppressWarnings("unchecked")
			Tensor<Float> tensor = (Tensor<Float>) keyToOutput(key);
			Object f = ArrayUtil.floatTensorToMultidimensionalFloatArray(tensor);
			Object b = ArrayUtil.convertArrayType(f, byte.class);
			return b;
		} else if (dtype == DataType.DT_DOUBLE) {
			@SuppressWarnings("unchecked")
			Tensor<Double> tensor = (Tensor<Double>) keyToOutput(key);
			Object d = ArrayUtil.doubleTensorToMultidimensionalDoubleArray(tensor);
			Object b = ArrayUtil.convertArrayType(d, byte.class);
			return b;
		} else {
			throw new TFException("getByteArrayMultidimensional not implemented for '" + key + "' data type: " + dtype);
		}
	}
}
