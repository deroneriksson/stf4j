package org.codait.tf;

import java.lang.reflect.Array;
import java.nio.FloatBuffer;
import java.nio.LongBuffer;
import java.util.Map;
import java.util.Map.Entry;

import org.tensorflow.Tensor;
import org.tensorflow.framework.DataType;
import org.tensorflow.framework.TensorInfo;

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

	protected void checkKey(String key) {
		if (!outputKeyToName.containsKey(key)) {
			throw new TFException("Output '" + key + "' not found in results");
		}
	}

	public Tensor<?> getTensor(String key) {
		checkKey(key);
		return (Tensor<?>) outputNameToValue.get(outputKeyToName.get(key));
	}

	public long getLong(String key) {
		checkKey(key);
		TensorInfo ti = TFUtil.outputKeyToTensorInfo(key, model);
		if (ti.getDtype() == DataType.DT_INT64) {
			@SuppressWarnings("unchecked")
			Tensor<Long> tensor = (Tensor<Long>) outputNameToValue.get(outputKeyToName.get(key));
			long l = tensor.copyTo(new long[1])[0];
			return l;
		} else {
			throw new TFException("getLong not implemented for '" + key + "' data type: " + ti.getDtype());
		}
	}

	public long[] getLongArray(String key) {
		checkKey(key);
		TensorInfo ti = TFUtil.outputKeyToTensorInfo(key, model);
		if (ti.getDtype() == DataType.DT_INT64) {
			@SuppressWarnings("unchecked")
			Tensor<Long> tensor = (Tensor<Long>) outputNameToValue.get(outputKeyToName.get(key));
			LongBuffer lb = LongBuffer.allocate(tensor.numElements());
			tensor.writeTo(lb);
			return lb.array();
		} else {
			throw new TFException("getLongArray not implemented for '" + key + "' data type: " + ti.getDtype());
		}
	}

	public Object getLongArrayMultidimensional(String key) {
		checkKey(key);
		TensorInfo ti = TFUtil.outputKeyToTensorInfo(key, model);
		if (ti.getDtype() == DataType.DT_INT64) {
			@SuppressWarnings("unchecked")
			Tensor<Long> tensor = (Tensor<Long>) outputNameToValue.get(outputKeyToName.get(key));
			int[] shape = ArrayUtil.lToI(tensor.shape());
			Object dest = Array.newInstance(long.class, shape);
			tensor.copyTo(dest);
			return dest;
		} else {
			throw new TFException(
					"getLongArrayMultidimensional not implemented for '" + key + "' data type: " + ti.getDtype());
		}
	}

	public float getFloat(String key) {
		checkKey(key);
		TensorInfo ti = TFUtil.outputKeyToTensorInfo(key, model);
		if (ti.getDtype() == DataType.DT_INT64) {
			@SuppressWarnings("unchecked")
			Tensor<Long> tensor = (Tensor<Long>) outputNameToValue.get(outputKeyToName.get(key));
			float f = (float) tensor.copyTo(new long[1])[0];
			return f;
		} else {
			throw new TFException("getFloat not implemented for '" + key + "' data type: " + ti.getDtype());
		}
	}

	public float[] getFloatArray(String key) {
		checkKey(key);
		TensorInfo ti = TFUtil.outputKeyToTensorInfo(key, model);
		if (ti.getDtype() == DataType.DT_FLOAT) {
			@SuppressWarnings("unchecked")
			Tensor<Float> tensor = (Tensor<Float>) outputNameToValue.get(outputKeyToName.get(key));
			FloatBuffer fb = FloatBuffer.allocate(tensor.numElements());
			tensor.writeTo(fb);
			return fb.array();
		} else {
			throw new TFException("getFloatArray not implemented for '" + key + "' data type: " + ti.getDtype());
		}
	}

	public Object getFloatArrayMultidimensional(String key) {
		checkKey(key);
		TensorInfo ti = TFUtil.outputKeyToTensorInfo(key, model);
		if (ti.getDtype() == DataType.DT_FLOAT) {
			@SuppressWarnings("unchecked")
			Tensor<Float> tensor = (Tensor<Float>) outputNameToValue.get(outputKeyToName.get(key));
			int[] shape = ArrayUtil.lToI(tensor.shape());
			Object dest = Array.newInstance(float.class, shape);
			tensor.copyTo(dest);
			return dest;
		} else {
			throw new TFException(
					"getFloatArrayMultidimensional not implemented for '" + key + "' data type: " + ti.getDtype());
		}
	}

	public int getInt(String key) {
		checkKey(key);
		TensorInfo ti = TFUtil.outputKeyToTensorInfo(key, model);
		if (ti.getDtype() == DataType.DT_INT64) {
			@SuppressWarnings("unchecked")
			Tensor<Long> tensor = (Tensor<Long>) outputNameToValue.get(outputKeyToName.get(key));
			int i = (int) tensor.copyTo(new long[1])[0];
			return i;
		} else {
			throw new TFException("getInt not implemented for '" + key + "' data type: " + ti.getDtype());
		}
	}

	public int[] getIntArray(String key) {
		checkKey(key);
		TensorInfo ti = TFUtil.outputKeyToTensorInfo(key, model);
		if (ti.getDtype() == DataType.DT_INT64) {
			@SuppressWarnings("unchecked")
			Tensor<Long> tensor = (Tensor<Long>) outputNameToValue.get(outputKeyToName.get(key));
			LongBuffer lb = LongBuffer.allocate(tensor.numElements());
			tensor.writeTo(lb);
			return ArrayUtil.lToI(lb.array());
		} else {
			throw new TFException("getIntArray not implemented for '" + key + "' data type: " + ti.getDtype());
		}
	}

	public Object getIntArrayMultidimensional(String key) {
		checkKey(key);
		TensorInfo ti = TFUtil.outputKeyToTensorInfo(key, model.metaGraphDef());
		if (ti.getDtype() == DataType.DT_INT64) {
			@SuppressWarnings("unchecked")
			Tensor<Long> tensor = (Tensor<Long>) outputNameToValue.get(outputKeyToName.get(key));
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

	public double getDouble(String key) {
		checkKey(key);
		TensorInfo ti = TFUtil.outputKeyToTensorInfo(key, model);
		if (ti.getDtype() == DataType.DT_INT64) {
			@SuppressWarnings("unchecked")
			Tensor<Long> tensor = (Tensor<Long>) outputNameToValue.get(outputKeyToName.get(key));
			double d = (double) tensor.copyTo(new long[1])[0];
			return d;
		} else {
			throw new TFException("getDouble not implemented for '" + key + "' data type: " + ti.getDtype());
		}
	}

	public double[] getDoubleArray(String key) {
		checkKey(key);
		TensorInfo ti = TFUtil.outputKeyToTensorInfo(key, model);
		if (ti.getDtype() == DataType.DT_FLOAT) {
			@SuppressWarnings("unchecked")
			Tensor<Float> tensor = (Tensor<Float>) outputNameToValue.get(outputKeyToName.get(key));
			FloatBuffer fb = FloatBuffer.allocate(tensor.numElements());
			tensor.writeTo(fb);
			double[] d = (double[]) ArrayUtil.convertArrayType(fb.array(), double.class);
			return d;
		} else {
			throw new TFException("getFloatArray not implemented for '" + key + "' data type: " + ti.getDtype());
		}
	}
}
