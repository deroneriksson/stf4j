package org.codait.tf;

import java.lang.reflect.Array;
import java.util.Map;
import java.util.Map.Entry;

import org.tensorflow.Tensor;
import org.tensorflow.framework.DataType;
import org.tensorflow.framework.TensorInfo;

public class TFResults {

	TFModel model;
	Map<String, Object> outputs;

	public TFResults(TFModel model) {
		this.model = model;
		this.outputs = model.outputs;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		if (outputs == null || outputs.isEmpty()) {
			sb.append("None\n");
		} else {
			int count = 0;
			for (Entry<String, Object> entry : outputs.entrySet()) {
				sb.append("  [");
				sb.append(++count);
				sb.append("] ");

				String name = entry.getKey();
				Object value = entry.getValue();
				sb.append(name);
				sb.append(": ");
				sb.append(value);

				sb.append("\n");
			}
		}

		return sb.toString();
	}

	protected void checkKey(String outputName) {
		if (!outputs.containsKey(outputName)) {
			throw new TFException("Output '" + outputName + "' not found in results");
		}
	}

	public Tensor<?> getTensor(String outputName) {
		checkKey(outputName);
		return (Tensor<?>) outputs.get(outputName);
	}

	public long getLong(String outputName) {
		checkKey(outputName);
		@SuppressWarnings("unchecked")
		Tensor<Long> tensor = (Tensor<Long>) outputs.get(outputName);
		long l = tensor.copyTo(new long[1])[0];
		return l;
	}

	public long[] getLongArray(String outputName) {
		checkKey(outputName);
		@SuppressWarnings("unchecked")
		Tensor<Long> tensor = (Tensor<Long>) outputs.get(outputName);
		long[] l = tensor.copyTo(new long[(int) tensor.shape()[0]]);
		return l;
	}

	public Object getLongArrayMultidimensional(String outputName) {
		checkKey(outputName);
		@SuppressWarnings("unchecked")
		Tensor<Long> tensor = (Tensor<Long>) outputs.get(outputName);
		int[] shape = ArrayUtil.lToI(tensor.shape());
		Object dest = Array.newInstance(long.class, shape);
		tensor.copyTo(dest);
		return dest;
	}

	public int getInt(String outputName) {
		checkKey(outputName);
		TensorInfo ti = TFUtil.outputKeyToTensorInfo(outputName, model.metaGraphDef());
		if (ti.getDtype() == DataType.DT_INT64) {
			@SuppressWarnings("unchecked")
			Tensor<Long> tensor = (Tensor<Long>) outputs.get(outputName);
			int i = (int) tensor.copyTo(new long[1])[0];
			return i;
		} else {
			throw new TFException("getInt not implemented for '" + outputName + "' data type: " + ti.getDtype());
		}

	}
}
