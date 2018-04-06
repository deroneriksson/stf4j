package org.codait.tf;

import java.util.Map;
import java.util.Map.Entry;

import org.tensorflow.Tensor;

public class TFResults {

	Map<String, Object> outputs;

	public TFResults(TFModel model) {
		outputs = model.outputs;
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

	public Long getLong(String outputName) {
		checkKey(outputName);
		@SuppressWarnings("unchecked")
		Tensor<Long> tensor = (Tensor<Long>) outputs.get(outputName);
		long l = tensor.copyTo(new long[1])[0];
		return l;
	}
}
