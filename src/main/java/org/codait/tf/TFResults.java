package org.codait.tf;

import java.util.Arrays;
import java.util.Map;
import java.util.Map.Entry;

import org.tensorflow.Tensor;
import org.tensorflow.framework.DataType;
import org.tensorflow.framework.TensorInfo;

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
				long l = (long) tensor.copyTo(new float[1])[0];
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
				long l = (long) tensor.copyTo(new double[1])[0];
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
				long l = tensor.copyTo(new long[1])[0];
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
				long i = (long) tensor.copyTo(new int[1])[0];
				return i;
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
		} else if (dtype == DataType.DT_STRING) {
			String[] s = getStringArray(key);
			long[] l = (long[]) ArrayUtil.convertArrayType(s, long.class);
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
		} else if (dtype == DataType.DT_STRING) {
			Object s = getStringArrayMultidimensional(key);
			Object l = ArrayUtil.convertArrayType(s, long.class);
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
				float f = tensor.copyTo(new float[1])[0];
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
				float f = (float) tensor.copyTo(new double[1])[0];
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
				float f = (float) tensor.copyTo(new long[1])[0];
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
				float f = (float) tensor.copyTo(new int[1])[0];
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
		} else if (dtype == DataType.DT_STRING) {
			String[] s = getStringArray(key);
			float[] f = (float[]) ArrayUtil.convertArrayType(s, float.class);
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
		} else if (dtype == DataType.DT_STRING) {
			Object s = getStringArrayMultidimensional(key);
			Object f = ArrayUtil.convertArrayType(s, float.class);
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
				int i = (int) tensor.copyTo(new float[1])[0];
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
				int i = (int) tensor.copyTo(new double[1])[0];
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
				int i = (int) tensor.copyTo(new long[1])[0];
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
				int i = tensor.copyTo(new int[1])[0];
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
		} else if (dtype == DataType.DT_STRING) {
			String[] s = getStringArray(key);
			int[] i = (int[]) ArrayUtil.convertArrayType(s, int.class);
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
		} else if (dtype == DataType.DT_STRING) {
			Object s = getStringArrayMultidimensional(key);
			Object i = ArrayUtil.convertArrayType(s, int.class);
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
				double d = (double) tensor.copyTo(new float[1])[0];
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
				double d = tensor.copyTo(new double[1])[0];
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
				double d = (double) tensor.copyTo(new long[1])[0];
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
				double d = (double) tensor.copyTo(new int[1])[0];
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
		} else if (dtype == DataType.DT_STRING) {
			String[] s = getStringArray(key);
			double[] d = (double[]) ArrayUtil.convertArrayType(s, double.class);
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
		} else if (dtype == DataType.DT_STRING) {
			Object s = getStringArrayMultidimensional(key);
			Object d = ArrayUtil.convertArrayType(s, double.class);
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
			return Float.toString(tensor.floatValue());
		} else if (dtype == DataType.DT_DOUBLE) {
			@SuppressWarnings("unchecked")
			Tensor<Double> tensor = (Tensor<Double>) keyToOutput(key);
			return Double.toString(tensor.doubleValue());
		} else if (dtype == DataType.DT_INT64) {
			@SuppressWarnings("unchecked")
			Tensor<Long> tensor = (Tensor<Long>) keyToOutput(key);
			return Long.toString(tensor.longValue());
		} else if (dtype == DataType.DT_INT32) {
			@SuppressWarnings("unchecked")
			Tensor<Integer> tensor = (Tensor<Integer>) keyToOutput(key);
			return Integer.toString(tensor.intValue());
		} else if (dtype == DataType.DT_STRING) {
			@SuppressWarnings("unchecked")
			Tensor<String> tensor = (Tensor<String>) keyToOutput(key);
			return new String(tensor.bytesValue());
		} else {
			throw new TFException("getString not implemented for '" + key + "' data type: " + dtype);
		}
	}
}
