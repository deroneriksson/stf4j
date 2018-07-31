package org.codait.tf;

import java.lang.reflect.Array;
import java.nio.FloatBuffer;
import java.nio.LongBuffer;
import java.util.Arrays;
import java.util.Map;
import java.util.Map.Entry;

import org.tensorflow.Tensor;
import org.tensorflow.framework.DataType;
import org.tensorflow.framework.TensorInfo;

/**
 * Representation of the results of running a TensorFlow model, which primarily consists of a map of Tensor values.
 *
 */
public class TFResults {

	TFModel model;
	Map<String, String> outputKeyToName;
	Map<String, Object> outputNameToValue;

	public TFResults(TFModel model) {
		this.model = model;
		this.outputKeyToName = model.outputKeyToName;
		this.outputNameToValue = model.outputNameToValue;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

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
			LongBuffer lb = LongBuffer.allocate(tensor.numElements());
			tensor.writeTo(lb);
			return lb.array();
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
			int[] shape = ArrayUtil.lToI(tensor.shape());
			Object dest = Array.newInstance(long.class, shape);
			tensor.copyTo(dest);
			return dest;
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
		if (ti.getDtype() == DataType.DT_FLOAT) {
			@SuppressWarnings("unchecked")
			Tensor<Float> tensor = (Tensor<Float>) keyToOutput(key);
			FloatBuffer fb = FloatBuffer.allocate(tensor.numElements());
			tensor.writeTo(fb);
			return fb.array();
		} else {
			throw new TFException("getFloatArray not implemented for '" + key + "' data type: " + ti.getDtype());
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
			int[] shape = ArrayUtil.lToI(tensor.shape());
			Object dest = Array.newInstance(float.class, shape);
			tensor.copyTo(dest);
			return dest;
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
			LongBuffer lb = LongBuffer.allocate(tensor.numElements());
			tensor.writeTo(lb);
			return ArrayUtil.lToI(lb.array());
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
			int[] shape = ArrayUtil.lToI(tensor.shape());
			Object l = Array.newInstance(long.class, shape);
			tensor.copyTo(l);
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
		if (ti.getDtype() == DataType.DT_FLOAT) {
			@SuppressWarnings("unchecked")
			Tensor<Float> tensor = (Tensor<Float>) keyToOutput(key);
			FloatBuffer fb = FloatBuffer.allocate(tensor.numElements());
			tensor.writeTo(fb);
			double[] d = (double[]) ArrayUtil.convertArrayType(fb.array(), double.class);
			return d;
		} else {
			throw new TFException("getDoubleArray not implemented for '" + key + "' data type: " + ti.getDtype());
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
			int[] shape = ArrayUtil.lToI(tensor.shape());
			Object f = Array.newInstance(float.class, shape);
			tensor.copyTo(f);
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
