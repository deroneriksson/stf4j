package org.codait.tf;

import java.lang.reflect.Array;
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
		if (ti.getDtype() == DataType.DT_INT64) {
			@SuppressWarnings("unchecked")
			Tensor<Long> tensor = (Tensor<Long>) keyToOutput(key);
			long l = tensor.copyTo(new long[1])[0];
			return l;
		} else {
			throw new TFException("getLong not implemented for '" + key + "' data type: " + ti.getDtype());
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
		if (ti.getDtype() == DataType.DT_INT64) {
			@SuppressWarnings("unchecked")
			Tensor<Long> tensor = (Tensor<Long>) keyToOutput(key);
			return ArrayUtil.longTensorToLongArray(tensor);
		} else {
			throw new TFException("getLongArray not implemented for '" + key + "' data type: " + ti.getDtype());
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
		if (ti.getDtype() == DataType.DT_INT64) {
			@SuppressWarnings("unchecked")
			Tensor<Long> tensor = (Tensor<Long>) keyToOutput(key);
			Object l = ArrayUtil.longTensorToMultidimensionalLongArray(tensor);
			return l;
		} else {
			throw new TFException(
					"getLongArrayMultidimensional not implemented for '" + key + "' data type: " + ti.getDtype());
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
		if (ti.getDtype() == DataType.DT_INT64) {
			@SuppressWarnings("unchecked")
			Tensor<Long> tensor = (Tensor<Long>) keyToOutput(key);
			float f = (float) tensor.copyTo(new long[1])[0];
			return f;
		} else {
			throw new TFException("getFloat not implemented for '" + key + "' data type: " + ti.getDtype());
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
		} else if (dtype == DataType.DT_INT64) {
			@SuppressWarnings("unchecked")
			Tensor<Long> tensor = (Tensor<Long>) keyToOutput(key);
			long[] lArray = ArrayUtil.longTensorToLongArray(tensor);
			return ArrayUtil.lToF(lArray);
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
		if (ti.getDtype() == DataType.DT_FLOAT) {
			@SuppressWarnings("unchecked")
			Tensor<Float> tensor = (Tensor<Float>) keyToOutput(key);
			Object f = ArrayUtil.floatTensorToMultidimensionalFloatArray(tensor);
			return f;
		} else {
			throw new TFException(
					"getFloatArrayMultidimensional not implemented for '" + key + "' data type: " + ti.getDtype());
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
		if (ti.getDtype() == DataType.DT_INT64) {
			@SuppressWarnings("unchecked")
			Tensor<Long> tensor = (Tensor<Long>) keyToOutput(key);
			int i = (int) tensor.copyTo(new long[1])[0];
			return i;
		} else {
			throw new TFException("getInt not implemented for '" + key + "' data type: " + ti.getDtype());
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
		if (ti.getDtype() == DataType.DT_INT64) {
			@SuppressWarnings("unchecked")
			Tensor<Long> tensor = (Tensor<Long>) keyToOutput(key);
			long[] lArray = ArrayUtil.longTensorToLongArray(tensor);
			return ArrayUtil.lToI(lArray);
		} else {
			throw new TFException("getIntArray not implemented for '" + key + "' data type: " + ti.getDtype());
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
		if (ti.getDtype() == DataType.DT_INT64) {
			@SuppressWarnings("unchecked")
			Tensor<Long> tensor = (Tensor<Long>) keyToOutput(key);
			Object l = ArrayUtil.longTensorToMultidimensionalLongArray(tensor);
			Object i = ArrayUtil.convertArrayType(l, int.class);
			return i;
		} else {
			throw new TFException(
					"getIntArrayMultidimensional not implemented for '" + key + "' data type: " + ti.getDtype());
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
		if (ti.getDtype() == DataType.DT_INT64) {
			@SuppressWarnings("unchecked")
			Tensor<Long> tensor = (Tensor<Long>) keyToOutput(key);
			double d = (double) tensor.copyTo(new long[1])[0];
			return d;
		} else {
			throw new TFException("getDouble not implemented for '" + key + "' data type: " + ti.getDtype());
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
			float[] fArray = ArrayUtil.floatTensorToFloatArray(tensor);
			double[] d = (double[]) ArrayUtil.convertArrayType(fArray, double.class);
			return d;
		} else if (dtype == DataType.DT_INT64) {
			@SuppressWarnings("unchecked")
			Tensor<Long> tensor = (Tensor<Long>) keyToOutput(key);
			long[] lArray = ArrayUtil.longTensorToLongArray(tensor);
			return ArrayUtil.lToD(lArray);
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
		if (ti.getDtype() == DataType.DT_FLOAT) {
			@SuppressWarnings("unchecked")
			Tensor<Float> tensor = (Tensor<Float>) keyToOutput(key);
			Object f = ArrayUtil.floatTensorToMultidimensionalFloatArray(tensor);
			Object d = ArrayUtil.convertArrayType(f, double.class);
			return d;
		} else {
			throw new TFException(
					"getDoubleArrayMultidimensional not implemented for '" + key + "' data type: " + ti.getDtype());
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
		String[][] multi = (String[][]) getStringArrayMultidimensional(key);
		String[] result = Arrays.stream(multi).flatMap(x -> Arrays.stream(x)).toArray(String[]::new);
		return result;
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
		if (ti.getDtype() == DataType.DT_STRING) {
			@SuppressWarnings("unchecked")
			Tensor<String> tensor = (Tensor<String>) keyToOutput(key);
			int[] sDim = ArrayUtil.lToI(tensor.shape());
			int[] bDim = Arrays.copyOf(sDim, sDim.length + 1);
			Object bDest = Array.newInstance(byte.class, bDim);
			tensor.copyTo(bDest);
			Object sDest = ArrayUtil.multidimBytesToMultidimStrings(bDest);
			return sDest;
		} else {
			throw new TFException(
					"getStringArrayMultidimensional not implemented for '" + key + "' data type: " + ti.getDtype());
		}
	}
}
