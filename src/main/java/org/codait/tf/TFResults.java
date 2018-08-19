// ------------------------------------------------------------------------
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
//
// ------------------------------------------------------------------------

package org.codait.tf;

import static org.codait.tf.util.TypeUtil.*;

import java.util.Map;
import java.util.Map.Entry;

import org.codait.tf.util.ArrayUtil;
import org.codait.tf.util.TFUtil;
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
	 * If output key does not exist, throw TFException.
	 * 
	 * @param key
	 *            The output key
	 */
	protected void checkKey(String key) {
		if (!keyExists(key)) {
			throw new TFException("Output '" + key + "' not found in results");
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
		} else if (dtype == DataType.DT_UINT8) {
			@SuppressWarnings("unchecked")
			Tensor<UInt8> tensor = (Tensor<UInt8>) keyToOutput(key);
			int shapeLength = tensor.shape().length;
			if (shapeLength == 0) {
				byte b = TFUtil.byteScalarFromUInt8Tensor(tensor);
				return byte_to_boolean(b);
			} else {
				Object bArray = getByteArrayMultidimensional(key);
				byte b = (byte) ArrayUtil.firstElementValueOfMultidimArray(bArray);
				return byte_to_boolean(b);
			}
		} else if (dtype == DataType.DT_INT32) {
			@SuppressWarnings("unchecked")
			Tensor<Integer> tensor = (Tensor<Integer>) keyToOutput(key);
			int shapeLength = tensor.shape().length;
			if (shapeLength == 0) {
				int i = ((Integer) tensor.intValue());
				return int_to_boolean(i);
			} else {
				Object iArray = getIntArrayMultidimensional(key);
				int i = (int) ArrayUtil.firstElementValueOfMultidimArray(iArray);
				return int_to_boolean(i);
			}
		} else if (dtype == DataType.DT_INT64) {
			@SuppressWarnings("unchecked")
			Tensor<Long> tensor = (Tensor<Long>) keyToOutput(key);
			int shapeLength = tensor.shape().length;
			if (shapeLength == 0) {
				long l = ((Long) tensor.longValue());
				return long_to_boolean(l);
			} else {
				Object lArray = getLongArrayMultidimensional(key);
				long l = (long) ArrayUtil.firstElementValueOfMultidimArray(lArray);
				return long_to_boolean(l);
			}
		} else if (dtype == DataType.DT_FLOAT) {
			@SuppressWarnings("unchecked")
			Tensor<Float> tensor = (Tensor<Float>) keyToOutput(key);
			int shapeLength = tensor.shape().length;
			if (shapeLength == 0) {
				float f = ((Float) tensor.floatValue());
				return float_to_boolean(f);
			} else {
				Object fArray = getFloatArrayMultidimensional(key);
				float f = (float) ArrayUtil.firstElementValueOfMultidimArray(fArray);
				return float_to_boolean(f);
			}
		} else if (dtype == DataType.DT_DOUBLE) {
			@SuppressWarnings("unchecked")
			Tensor<Double> tensor = (Tensor<Double>) keyToOutput(key);
			int shapeLength = tensor.shape().length;
			if (shapeLength == 0) {
				double d = ((Double) tensor.doubleValue());
				return double_to_boolean(d);
			} else {
				Object dArray = getDoubleArrayMultidimensional(key);
				double d = (double) ArrayUtil.firstElementValueOfMultidimArray(dArray);
				return double_to_boolean(d);
			}
		} else if (dtype == DataType.DT_STRING) {
			@SuppressWarnings("unchecked")
			Tensor<String> tensor = (Tensor<String>) keyToOutput(key);
			if (tensor.shape().length == 0) {
				String s = new String(tensor.bytesValue());
				return String_to_boolean(s);
			} else {
				Object sArray = getStringArrayMultidimensional(key);
				String s = (String) ArrayUtil.firstElementValueOfMultidimArray(sArray);
				return String_to_boolean(s);
			}

		} else {
			throw new TFException("getBoolean not implemented for '" + key + "' data type: " + dtype);
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
		} else if (dtype == DataType.DT_UINT8) {
			@SuppressWarnings("unchecked")
			Tensor<UInt8> tensor = (Tensor<UInt8>) keyToOutput(key);
			byte[] byteArray = ArrayUtil.uint8TensorToByteArray(tensor);
			boolean[] boolArray = (boolean[]) ArrayUtil.convertArrayType(byteArray, boolean.class);
			return boolArray;
		} else if (dtype == DataType.DT_INT32) {
			@SuppressWarnings("unchecked")
			Tensor<Integer> tensor = (Tensor<Integer>) keyToOutput(key);
			int[] i = ArrayUtil.intTensorToIntArray(tensor);
			boolean[] b = (boolean[]) ArrayUtil.convertArrayType(i, boolean.class);
			return b;
		} else if (dtype == DataType.DT_INT64) {
			@SuppressWarnings("unchecked")
			Tensor<Long> tensor = (Tensor<Long>) keyToOutput(key);
			long[] l = ArrayUtil.longTensorToLongArray(tensor);
			boolean[] b = (boolean[]) ArrayUtil.convertArrayType(l, boolean.class);
			return b;
		} else if (dtype == DataType.DT_FLOAT) {
			@SuppressWarnings("unchecked")
			Tensor<Float> tensor = (Tensor<Float>) keyToOutput(key);
			float[] f = ArrayUtil.floatTensorToFloatArray(tensor);
			boolean[] b = (boolean[]) ArrayUtil.convertArrayType(f, boolean.class);
			return b;
		} else if (dtype == DataType.DT_DOUBLE) {
			@SuppressWarnings("unchecked")
			Tensor<Double> tensor = (Tensor<Double>) keyToOutput(key);
			double[] d = ArrayUtil.doubleTensorToDoubleArray(tensor);
			boolean[] b = (boolean[]) ArrayUtil.convertArrayType(d, boolean.class);
			return b;
		} else if (dtype == DataType.DT_STRING) {
			String[] s = getStringArray(key);
			boolean[] b = (boolean[]) ArrayUtil.convertArrayType(s, boolean.class);
			return b;
		} else {
			throw new TFException("getBooleanArray not implemented for '" + key + "' data type: " + dtype);
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
		} else if (dtype == DataType.DT_UINT8) {
			@SuppressWarnings("unchecked")
			Tensor<UInt8> tensor = (Tensor<UInt8>) keyToOutput(key);
			Object byteArray = ArrayUtil.uint8TensorToMultidimensionalByteArray(tensor);
			Object booleanArray = ArrayUtil.convertArrayType(byteArray, boolean.class);
			return booleanArray;
		} else if (dtype == DataType.DT_INT32) {
			@SuppressWarnings("unchecked")
			Tensor<Integer> tensor = (Tensor<Integer>) keyToOutput(key);
			Object i = ArrayUtil.intTensorToMultidimensionalIntArray(tensor);
			Object b = ArrayUtil.convertArrayType(i, boolean.class);
			return b;
		} else if (dtype == DataType.DT_INT64) {
			@SuppressWarnings("unchecked")
			Tensor<Long> tensor = (Tensor<Long>) keyToOutput(key);
			Object l = ArrayUtil.longTensorToMultidimensionalLongArray(tensor);
			Object b = ArrayUtil.convertArrayType(l, boolean.class);
			return b;
		} else if (dtype == DataType.DT_FLOAT) {
			@SuppressWarnings("unchecked")
			Tensor<Float> tensor = (Tensor<Float>) keyToOutput(key);
			Object f = ArrayUtil.floatTensorToMultidimensionalFloatArray(tensor);
			Object b = ArrayUtil.convertArrayType(f, boolean.class);
			return b;
		} else if (dtype == DataType.DT_DOUBLE) {
			@SuppressWarnings("unchecked")
			Tensor<Double> tensor = (Tensor<Double>) keyToOutput(key);
			Object d = ArrayUtil.doubleTensorToMultidimensionalDoubleArray(tensor);
			Object b = ArrayUtil.convertArrayType(d, boolean.class);
			return b;
		} else if (dtype == DataType.DT_STRING) {
			@SuppressWarnings("unchecked")
			Tensor<String> tensor = (Tensor<String>) keyToOutput(key);
			Object s = ArrayUtil.stringTensorToMultidimensionalStringArray(tensor);
			Object b = ArrayUtil.convertArrayType(s, boolean.class);
			return b;
		} else {
			throw new TFException(
					"getBooleanArrayMultidimensional not implemented for '" + key + "' data type: " + dtype);
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
				return boolean_to_byte(b);
			} else {
				Object booleanArray = getBooleanArrayMultidimensional(key);
				boolean b = (boolean) ArrayUtil.firstElementValueOfMultidimArray(booleanArray);
				return boolean_to_byte(b);
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
				return int_to_byte(tensor.intValue());
			} else {
				Object iArray = getIntArrayMultidimensional(key);
				int i = (int) ArrayUtil.firstElementValueOfMultidimArray(iArray);
				return int_to_byte(i);
			}
		} else if (dtype == DataType.DT_INT64) {
			@SuppressWarnings("unchecked")
			Tensor<Integer> tensor = (Tensor<Integer>) keyToOutput(key);
			int shapeLength = tensor.shape().length;
			if (shapeLength == 0) {
				return long_to_byte(tensor.longValue());
			} else {
				Object lArray = getLongArrayMultidimensional(key);
				long l = (long) ArrayUtil.firstElementValueOfMultidimArray(lArray);
				return long_to_byte(l);
			}
		} else if (dtype == DataType.DT_FLOAT) {
			@SuppressWarnings("unchecked")
			Tensor<Float> tensor = (Tensor<Float>) keyToOutput(key);
			int shapeLength = tensor.shape().length;
			if (shapeLength == 0) {
				return float_to_byte(tensor.floatValue());
			} else {
				Object fArray = getFloatArrayMultidimensional(key);
				float f = (float) ArrayUtil.firstElementValueOfMultidimArray(fArray);
				return float_to_byte(f);
			}
		} else if (dtype == DataType.DT_DOUBLE) {
			@SuppressWarnings("unchecked")
			Tensor<Double> tensor = (Tensor<Double>) keyToOutput(key);
			int shapeLength = tensor.shape().length;
			if (shapeLength == 0) {
				return double_to_byte(tensor.doubleValue());
			} else {
				Object dArray = getDoubleArrayMultidimensional(key);
				double d = (double) ArrayUtil.firstElementValueOfMultidimArray(dArray);
				return double_to_byte(d);
			}
		} else {
			throw new TFException("getByte not implemented for '" + key + "' data type: " + dtype);
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
				return float_to_double(tensor.floatValue());
			} else {
				Object fArray = getFloatArrayMultidimensional(key);
				float f = (float) ArrayUtil.firstElementValueOfMultidimArray(fArray);
				return float_to_double(f);
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
				return long_to_double(tensor.longValue());
			} else {
				Object lArray = getLongArrayMultidimensional(key);
				long l = (long) ArrayUtil.firstElementValueOfMultidimArray(lArray);
				return long_to_double(l);
			}
		} else if (dtype == DataType.DT_INT32) {
			@SuppressWarnings("unchecked")
			Tensor<Integer> tensor = (Tensor<Integer>) keyToOutput(key);
			int shapeLength = tensor.shape().length;
			if (shapeLength == 0) {
				return int_to_double(tensor.intValue());
			} else {
				Object iArray = getIntArrayMultidimensional(key);
				int i = (int) ArrayUtil.firstElementValueOfMultidimArray(iArray);
				return int_to_double(i);
			}
		} else if (dtype == DataType.DT_UINT8) {
			@SuppressWarnings("unchecked")
			Tensor<UInt8> tensor = (Tensor<UInt8>) keyToOutput(key);
			int shapeLength = tensor.shape().length;
			if (shapeLength == 0) {
				byte b = TFUtil.byteScalarFromUInt8Tensor(tensor);
				return byte_unsigned_to_double(b);
			} else {
				Object bArray = getByteArrayMultidimensional(key);
				byte b = (byte) ArrayUtil.firstElementValueOfMultidimArray(bArray);
				return byte_unsigned_to_double(b);
			}
		} else if (dtype == DataType.DT_STRING) {
			@SuppressWarnings("unchecked")
			Tensor<String> tensor = (Tensor<String>) keyToOutput(key);
			int shapeLength = tensor.shape().length;
			if (shapeLength == 0) {
				return String_bytes_to_double(tensor.bytesValue());
			} else {
				Object sArray = getStringArrayMultidimensional(key);
				String s = (String) ArrayUtil.firstElementValueOfMultidimArray(sArray);
				return String_to_double(s);
			}
		} else if (dtype == DataType.DT_BOOL) {
			@SuppressWarnings("unchecked")
			Tensor<Boolean> tensor = (Tensor<Boolean>) keyToOutput(key);
			int shapeLength = tensor.shape().length;
			if (shapeLength == 0) {
				return boolean_to_double(tensor.booleanValue());
			} else {
				Object bArray = getBooleanArrayMultidimensional(key);
				boolean b = (boolean) ArrayUtil.firstElementValueOfMultidimArray(bArray);
				return boolean_to_double(b);
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
			return ArrayUtil.fToD(f);
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
				return double_to_float(tensor.doubleValue());
			} else {
				Object dArray = getDoubleArrayMultidimensional(key);
				double d = (double) ArrayUtil.firstElementValueOfMultidimArray(dArray);
				return double_to_float(d);
			}
		} else if (dtype == DataType.DT_INT64) {
			@SuppressWarnings("unchecked")
			Tensor<Long> tensor = (Tensor<Long>) keyToOutput(key);
			int shapeLength = tensor.shape().length;
			if (shapeLength == 0) {
				return long_to_float(tensor.longValue());
			} else {
				Object lArray = getLongArrayMultidimensional(key);
				long l = (long) ArrayUtil.firstElementValueOfMultidimArray(lArray);
				return long_to_float(l);
			}
		} else if (dtype == DataType.DT_INT32) {
			@SuppressWarnings("unchecked")
			Tensor<Integer> tensor = (Tensor<Integer>) keyToOutput(key);
			int shapeLength = tensor.shape().length;
			if (shapeLength == 0) {
				return int_to_float(tensor.intValue());
			} else {
				Object iArray = getIntArrayMultidimensional(key);
				int i = (int) ArrayUtil.firstElementValueOfMultidimArray(iArray);
				return int_to_float(i);
			}
		} else if (dtype == DataType.DT_UINT8) {
			@SuppressWarnings("unchecked")
			Tensor<UInt8> tensor = (Tensor<UInt8>) keyToOutput(key);
			int shapeLength = tensor.shape().length;
			if (shapeLength == 0) {
				byte b = TFUtil.byteScalarFromUInt8Tensor(tensor);
				return byte_unsigned_to_float(b);
			} else {
				Object bArray = getByteArrayMultidimensional(key);
				byte b = (byte) ArrayUtil.firstElementValueOfMultidimArray(bArray);
				return byte_unsigned_to_float(b);
			}
		} else if (dtype == DataType.DT_STRING) {
			@SuppressWarnings("unchecked")
			Tensor<String> tensor = (Tensor<String>) keyToOutput(key);
			int shapeLength = tensor.shape().length;
			if (shapeLength == 0) {
				return String_bytes_to_float(tensor.bytesValue());
			} else {
				Object sArray = getStringArrayMultidimensional(key);
				String s = (String) ArrayUtil.firstElementValueOfMultidimArray(sArray);
				return String_to_float(s);
			}
		} else if (dtype == DataType.DT_BOOL) {
			@SuppressWarnings("unchecked")
			Tensor<Boolean> tensor = (Tensor<Boolean>) keyToOutput(key);
			int shapeLength = tensor.shape().length;
			if (shapeLength == 0) {
				return boolean_to_float(tensor.booleanValue());
			} else {
				Object bArray = getBooleanArrayMultidimensional(key);
				boolean b = (boolean) ArrayUtil.firstElementValueOfMultidimArray(bArray);
				return boolean_to_float(b);
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
				return float_to_int(tensor.floatValue());
			} else {
				Object fArray = getFloatArrayMultidimensional(key);
				float f = (float) ArrayUtil.firstElementValueOfMultidimArray(fArray);
				return float_to_int(f);
			}
		} else if (dtype == DataType.DT_DOUBLE) {
			@SuppressWarnings("unchecked")
			Tensor<Double> tensor = (Tensor<Double>) keyToOutput(key);
			int shapeLength = tensor.shape().length;
			if (shapeLength == 0) {
				return double_to_int(tensor.doubleValue());
			} else {
				Object dArray = getDoubleArrayMultidimensional(key);
				double d = (double) ArrayUtil.firstElementValueOfMultidimArray(dArray);
				return double_to_int(d);
			}
		} else if (dtype == DataType.DT_INT64) {
			@SuppressWarnings("unchecked")
			Tensor<Long> tensor = (Tensor<Long>) keyToOutput(key);
			int shapeLength = tensor.shape().length;
			if (shapeLength == 0) {
				return long_to_int(tensor.longValue());
			} else {
				Object lArray = getLongArrayMultidimensional(key);
				long l = (long) ArrayUtil.firstElementValueOfMultidimArray(lArray);
				return long_to_int(l);
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
				return byte_unsigned_to_int(b);
			} else {
				Object bArray = getByteArrayMultidimensional(key);
				byte b = (byte) ArrayUtil.firstElementValueOfMultidimArray(bArray);
				return byte_unsigned_to_int(b);
			}
		} else if (dtype == DataType.DT_STRING) {
			@SuppressWarnings("unchecked")
			Tensor<String> tensor = (Tensor<String>) keyToOutput(key);
			int shapeLength = tensor.shape().length;
			if (shapeLength == 0) {
				return String_bytes_to_int(tensor.bytesValue());
			} else {
				Object sArray = getStringArrayMultidimensional(key);
				String s = (String) ArrayUtil.firstElementValueOfMultidimArray(sArray);
				return String_to_int(s);
			}
		} else if (dtype == DataType.DT_BOOL) {
			@SuppressWarnings("unchecked")
			Tensor<Boolean> tensor = (Tensor<Boolean>) keyToOutput(key);
			int shapeLength = tensor.shape().length;
			if (shapeLength == 0) {
				return boolean_to_int(tensor.booleanValue());
			} else {
				Object bArray = getBooleanArrayMultidimensional(key);
				boolean b = (boolean) ArrayUtil.firstElementValueOfMultidimArray(bArray);
				return boolean_to_int(b);
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
				return float_to_long(tensor.floatValue());
			} else {
				Object fArray = getFloatArrayMultidimensional(key);
				float f = (float) ArrayUtil.firstElementValueOfMultidimArray(fArray);
				return float_to_long(f);
			}
		} else if (dtype == DataType.DT_DOUBLE) {
			@SuppressWarnings("unchecked")
			Tensor<Double> tensor = (Tensor<Double>) keyToOutput(key);
			int shapeLength = tensor.shape().length;
			if (shapeLength == 0) {
				return double_to_long(tensor.doubleValue());
			} else {
				Object dArray = getDoubleArrayMultidimensional(key);
				double d = (double) ArrayUtil.firstElementValueOfMultidimArray(dArray);
				return double_to_long(d);
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
				return int_to_long(tensor.intValue());
			} else {
				Object iArray = getIntArrayMultidimensional(key);
				int i = (int) ArrayUtil.firstElementValueOfMultidimArray(iArray);
				return int_to_long(i);
			}
		} else if (dtype == DataType.DT_UINT8) {
			@SuppressWarnings("unchecked")
			Tensor<UInt8> tensor = (Tensor<UInt8>) keyToOutput(key);
			int shapeLength = tensor.shape().length;
			if (shapeLength == 0) {
				byte b = TFUtil.byteScalarFromUInt8Tensor(tensor);
				return byte_unsigned_to_long(b);
			} else {
				Object bArray = getByteArrayMultidimensional(key);
				byte b = (byte) ArrayUtil.firstElementValueOfMultidimArray(bArray);
				return byte_unsigned_to_long(b);
			}
		} else if (dtype == DataType.DT_STRING) {
			@SuppressWarnings("unchecked")
			Tensor<String> tensor = (Tensor<String>) keyToOutput(key);
			int shapeLength = tensor.shape().length;
			if (shapeLength == 0) {
				return String_bytes_to_long(tensor.bytesValue());
			} else {
				Object sArray = getStringArrayMultidimensional(key);
				String s = (String) ArrayUtil.firstElementValueOfMultidimArray(sArray);
				return String_to_long(s);
			}
		} else if (dtype == DataType.DT_BOOL) {
			@SuppressWarnings("unchecked")
			Tensor<Boolean> tensor = (Tensor<Boolean>) keyToOutput(key);
			int shapeLength = tensor.shape().length;
			if (shapeLength == 0) {
				return boolean_to_long(tensor.booleanValue());
			} else {
				Object bArray = getBooleanArrayMultidimensional(key);
				boolean b = (boolean) ArrayUtil.firstElementValueOfMultidimArray(bArray);
				return boolean_to_long(b);
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
				return float_to_String(tensor.floatValue());
			} else {
				Object fArray = getFloatArrayMultidimensional(key);
				float f = (float) ArrayUtil.firstElementValueOfMultidimArray(fArray);
				return float_to_String(f);
			}
		} else if (dtype == DataType.DT_DOUBLE) {
			@SuppressWarnings("unchecked")
			Tensor<Double> tensor = (Tensor<Double>) keyToOutput(key);
			if (tensor.shape().length == 0) {
				return double_to_String(tensor.doubleValue());
			} else {
				Object dArray = getDoubleArrayMultidimensional(key);
				double d = (double) ArrayUtil.firstElementValueOfMultidimArray(dArray);
				return double_to_String(d);
			}
		} else if (dtype == DataType.DT_INT64) {
			@SuppressWarnings("unchecked")
			Tensor<Long> tensor = (Tensor<Long>) keyToOutput(key);
			if (tensor.shape().length == 0) {
				return long_to_String(tensor.longValue());
			} else {
				Object lArray = getLongArrayMultidimensional(key);
				long l = (long) ArrayUtil.firstElementValueOfMultidimArray(lArray);
				return long_to_String(l);
			}
		} else if (dtype == DataType.DT_INT32) {
			@SuppressWarnings("unchecked")
			Tensor<Integer> tensor = (Tensor<Integer>) keyToOutput(key);
			if (tensor.shape().length == 0) {
				return int_to_String(tensor.intValue());
			} else {
				Object iArray = getIntArrayMultidimensional(key);
				int i = (int) ArrayUtil.firstElementValueOfMultidimArray(iArray);
				return int_to_String(i);
			}
		} else if (dtype == DataType.DT_UINT8) {
			@SuppressWarnings("unchecked")
			Tensor<UInt8> tensor = (Tensor<UInt8>) keyToOutput(key);
			int shapeLength = tensor.shape().length;
			if (shapeLength == 0) {
				byte b = TFUtil.byteScalarFromUInt8Tensor(tensor);
				return byte_unsigned_to_String(b);
			} else {
				Object bArray = getByteArrayMultidimensional(key);
				byte b = (byte) ArrayUtil.firstElementValueOfMultidimArray(bArray);
				return byte_unsigned_to_String(b);
			}
		} else if (dtype == DataType.DT_STRING) {
			@SuppressWarnings("unchecked")
			Tensor<String> tensor = (Tensor<String>) keyToOutput(key);
			if (tensor.shape().length == 0) {
				return String_bytes_to_String(tensor.bytesValue());
			} else {
				Object sArray = getStringArrayMultidimensional(key);
				String s = (String) ArrayUtil.firstElementValueOfMultidimArray(sArray);
				return s;
			}
		} else if (dtype == DataType.DT_BOOL) {
			@SuppressWarnings("unchecked")
			Tensor<Boolean> tensor = (Tensor<Boolean>) keyToOutput(key);
			if (tensor.shape().length == 0) {
				return boolean_to_String(tensor.booleanValue());
			} else {
				Object bArray = getBooleanArrayMultidimensional(key);
				boolean b = (boolean) ArrayUtil.firstElementValueOfMultidimArray(bArray);
				return boolean_to_String(b);
			}
		} else {
			throw new TFException("getString not implemented for '" + key + "' data type: " + dtype);
		}
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
				Object obj = getStringArrayMultidimensional(key);
				String[] s = (String[]) ArrayUtil.firstDimensionValuesOfMultidimArray(obj);
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
	 * Return true if the output key exists in the result map, false otherwise.
	 * 
	 * @param key
	 *            The output key
	 * @return true if the output key exists in the result map, false otherwise.
	 */
	public boolean keyExists(String key) {
		return outputKeyToName.containsKey(key);
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
}
