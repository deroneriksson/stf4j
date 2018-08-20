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

package org.codait.stf4j.util;

import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.codait.stf4j.simple.BooleanLogicTest;
import org.junit.Assert;
import org.junit.Test;
import org.tensorflow.Tensor;
import org.tensorflow.types.UInt8;

public class ArrayUtilTest {

	protected static Logger log = LogManager.getLogger(ArrayUtilTest.class);

	@Test
	public void booleanArrayToByteArray() {
		byte[] b = (byte[]) ArrayUtil.convertArrayType(new boolean[] { true }, byte.class);
		Assert.assertTrue((byte) 1 == b[0]);
	}

	@Test
	public void booleanArrayToDoubleArray() {
		double[] d = (double[]) ArrayUtil.convertArrayType(new boolean[] { true }, double.class);
		Assert.assertTrue(1.0d == d[0]);
	}

	@Test
	public void booleanArrayToFloatArray() {
		float[] f = (float[]) ArrayUtil.convertArrayType(new boolean[] { true }, float.class);
		Assert.assertTrue(1.0f == f[0]);
	}

	@Test
	public void booleanArrayToIntArray() {
		int[] i = (int[]) ArrayUtil.convertArrayType(new boolean[] { true }, int.class);
		Assert.assertTrue(1 == i[0]);
	}

	@Test
	public void booleanArrayToLongArray() {
		long[] l = (long[]) ArrayUtil.convertArrayType(new boolean[] { true }, long.class);
		Assert.assertTrue(1L == l[0]);
	}

	@Test
	public void booleanObjectArrayToByteObjectArray() {
		Byte[] b = (Byte[]) ArrayUtil.convertArrayType(new Boolean[] { true }, Byte.class);
		Assert.assertTrue((byte) 1 == b[0]);
	}

	@Test
	public void booleanObjectArrayToDoubleObjectArray() {
		Double[] d = (Double[]) ArrayUtil.convertArrayType(new Boolean[] { true }, Double.class);
		Assert.assertTrue(1.0d == d[0]);
	}

	@Test
	public void booleanObjectArrayToFloatObjectArray() {
		Float[] f = (Float[]) ArrayUtil.convertArrayType(new Boolean[] { true }, Float.class);
		Assert.assertTrue(1.0f == f[0]);
	}

	@Test
	public void booleanObjectArrayToIntegerObjectArray() {
		Integer[] i = (Integer[]) ArrayUtil.convertArrayType(new Boolean[] { true }, Integer.class);
		Assert.assertTrue(1 == i[0]);
	}

	@Test
	public void booleanObjectArrayToLongObjectArray() {
		Long[] l = (Long[]) ArrayUtil.convertArrayType(new Boolean[] { true }, Long.class);
		Assert.assertTrue(1L == l[0]);
	}

	@Test
	public void booleanObjectArrayToStringArray() {
		String[] s = (String[]) ArrayUtil.convertArrayType(new Boolean[] { true }, String.class);
		Assert.assertTrue("true".equals(s[0]));
	}

	@Test
	public void byteArrayToBooleanArray() {
		boolean[] b = (boolean[]) ArrayUtil.convertArrayType(new byte[] { 1 }, boolean.class);
		Assert.assertTrue(true == b[0]);
	}

	@Test
	public void byteObjectArrayToBooleanObjectArray() {
		Boolean[] b = (Boolean[]) ArrayUtil.convertArrayType(new Byte[] { 1 }, Boolean.class);
		Assert.assertTrue(true == b[0]);
	}

	@Test
	public void doubleArrayToBooleanArray() {
		boolean[] b = (boolean[]) ArrayUtil.convertArrayType(new double[] { 1.0d }, boolean.class);
		Assert.assertTrue(true == b[0]);
	}

	@Test
	public void doubleArrayToByteArray() {
		byte[] b = (byte[]) ArrayUtil.convertArrayType(new double[] { 1.0d }, byte.class);
		Assert.assertTrue((byte) 1 == b[0]);
	}

	@Test
	public void doubleArrayToFloatArray() {
		float[] f = (float[]) ArrayUtil.convertArrayType(new double[] { 1.0d }, float.class);
		Assert.assertTrue(1.0f == f[0]);
	}

	@Test
	public void doubleArrayToIntArray() {
		int[] i = (int[]) ArrayUtil.convertArrayType(new double[] { 1.0d }, int.class);
		Assert.assertTrue(1 == i[0]);
	}

	@Test
	public void doubleArrayToLongArray() {
		long[] l = (long[]) ArrayUtil.convertArrayType(new double[] { 1.0d }, long.class);
		Assert.assertTrue(1L == l[0]);
	}

	@Test
	public void doubleObjectArrayToBooleanObjectArray() {
		Boolean[] b = (Boolean[]) ArrayUtil.convertArrayType(new Double[] { 1.0d }, Boolean.class);
		Assert.assertTrue(true == b[0]);
	}

	@Test
	public void doubleObjectArrayToByteObjectArray() {
		Byte[] b = (Byte[]) ArrayUtil.convertArrayType(new Double[] { 1.0d }, Byte.class);
		Assert.assertTrue((byte) 1 == b[0]);
	}

	@Test
	public void doubleObjectArrayToFloatObjectArray() {
		Float[] f = (Float[]) ArrayUtil.convertArrayType(new Double[] { 1.0d }, Float.class);
		Assert.assertTrue(1.0f == f[0]);
	}

	@Test
	public void doubleObjectArrayToIntegerObjectArray() {
		Integer[] i = (Integer[]) ArrayUtil.convertArrayType(new Double[] { 1.0d }, Integer.class);
		Assert.assertTrue(1 == i[0]);
	}

	@Test
	public void doubleObjectArrayToLongObjectArray() {
		Long[] l = (Long[]) ArrayUtil.convertArrayType(new Double[] { 1.0d }, Long.class);
		Assert.assertTrue(1L == l[0]);
	}

	@Test
	public void doubleObjectArrayToStringArray() {
		String[] s = (String[]) ArrayUtil.convertArrayType(new Double[] { 1.0d }, String.class);
		Assert.assertTrue("1.0".equals(s[0]));
	}

	@Test
	public void floatArrayToBooleanArray() {
		boolean[] b = (boolean[]) ArrayUtil.convertArrayType(new float[] { 1.0f }, boolean.class);
		Assert.assertTrue(true == b[0]);
	}

	@Test
	public void floatArrayToByteArray() {
		byte[] b = (byte[]) ArrayUtil.convertArrayType(new float[] { 1.0f }, byte.class);
		Assert.assertTrue((byte) 1 == b[0]);
	}

	@Test
	public void floatArrayToDoubleArray() {
		double[] d = (double[]) ArrayUtil.convertArrayType(new float[] { 1.0f }, double.class);
		Assert.assertTrue(1.0d == d[0]);
	}

	@Test
	public void floatArrayToIntArray() {
		int[] i = (int[]) ArrayUtil.convertArrayType(new float[] { 1.0f }, int.class);
		Assert.assertTrue(1 == i[0]);
	}

	@Test
	public void floatArrayToLongArray() {
		long[] l = (long[]) ArrayUtil.convertArrayType(new float[] { 1.0f }, long.class);
		Assert.assertTrue(1L == l[0]);
	}

	@Test
	public void floatObjectArrayToBooleanObjectArray() {
		Boolean[] b = (Boolean[]) ArrayUtil.convertArrayType(new Float[] { 1.0f }, Boolean.class);
		Assert.assertTrue(true == b[0]);
	}

	@Test
	public void floatObjectArrayToByteObjectArray() {
		Byte[] b = (Byte[]) ArrayUtil.convertArrayType(new Float[] { 1.0f }, Byte.class);
		Assert.assertTrue((byte) 1 == b[0]);
	}

	@Test
	public void floatObjectArrayToDoubleObjectArray() {
		Double[] d = (Double[]) ArrayUtil.convertArrayType(new Float[] { 1.0f }, Double.class);
		Assert.assertTrue(1.0d == d[0]);
	}

	@Test
	public void floatObjectArrayToIntegerObjectArray() {
		Integer[] i = (Integer[]) ArrayUtil.convertArrayType(new Float[] { 1.0f }, Integer.class);
		Assert.assertTrue(1 == i[0]);
	}

	@Test
	public void floatObjectArrayToLongObjectArray() {
		Long[] l = (Long[]) ArrayUtil.convertArrayType(new Float[] { 1.0f }, Long.class);
		Assert.assertTrue(1L == l[0]);
	}

	@Test
	public void floatObjectArrayToStringArray() {
		String[] s = (String[]) ArrayUtil.convertArrayType(new Float[] { 1.0f }, String.class);
		Assert.assertTrue("1.0".equals(s[0]));
	}

	@Test
	public void intArrayToBooleanArray() {
		boolean[] b = (boolean[]) ArrayUtil.convertArrayType(new int[] { 1 }, boolean.class);
		Assert.assertTrue(true == b[0]);
	}

	@Test
	public void intArrayToByteArray() {
		byte[] b = (byte[]) ArrayUtil.convertArrayType(new int[] { 1 }, byte.class);
		Assert.assertTrue((byte) 1 == b[0]);
	}

	@Test
	public void intArrayToDoubleArray() {
		double[] d = (double[]) ArrayUtil.convertArrayType(new int[] { 1 }, double.class);
		Assert.assertTrue(1.0d == d[0]);
	}

	@Test
	public void intArrayToFloatArray() {
		float[] f = (float[]) ArrayUtil.convertArrayType(new int[] { 1 }, float.class);
		Assert.assertTrue(1.0f == f[0]);
	}

	@Test
	public void intArrayToLongArray() {
		long[] l = (long[]) ArrayUtil.convertArrayType(new int[] { 1 }, long.class);
		Assert.assertTrue(1L == l[0]);
	}

	@Test
	public void integerObjectArrayToBooleanObjectArray() {
		Boolean[] b = (Boolean[]) ArrayUtil.convertArrayType(new Integer[] { 1 }, Boolean.class);
		Assert.assertTrue(true == b[0]);
	}

	@Test
	public void integerObjectArrayToByteObjectArray() {
		Byte[] b = (Byte[]) ArrayUtil.convertArrayType(new Integer[] { 1 }, Byte.class);
		Assert.assertTrue((byte) 1 == b[0]);
	}

	@Test
	public void integerObjectArrayToDoubleObjectArray() {
		Double[] d = (Double[]) ArrayUtil.convertArrayType(new Integer[] { 1 }, Double.class);
		Assert.assertTrue(1.0d == d[0]);
	}

	@Test
	public void integerObjectArrayToFloatObjectArray() {
		Float[] f = (Float[]) ArrayUtil.convertArrayType(new Integer[] { 1 }, Float.class);
		Assert.assertTrue(1.0f == f[0]);
	}

	@Test
	public void integerObjectArrayToLongObjectArray() {
		Long[] l = (Long[]) ArrayUtil.convertArrayType(new Integer[] { 1 }, Long.class);
		Assert.assertTrue(1L == l[0]);
	}

	@Test
	public void integerObjectArrayToStringArray() {
		String[] s = (String[]) ArrayUtil.convertArrayType(new Integer[] { 1 }, String.class);
		Assert.assertTrue("1".equals(s[0]));
	}

	@Test
	public void longArrayToBooleanArray() {
		boolean[] b = (boolean[]) ArrayUtil.convertArrayType(new long[] { 1L }, boolean.class);
		Assert.assertTrue(true == b[0]);
	}

	@Test
	public void longArrayToByteArray() {
		byte[] b = (byte[]) ArrayUtil.convertArrayType(new long[] { 1L }, byte.class);
		Assert.assertTrue((byte) 1 == b[0]);
	}

	@Test
	public void longArrayToDoubleArray() {
		double[] d = (double[]) ArrayUtil.convertArrayType(new long[] { 1L }, double.class);
		Assert.assertTrue(1.0d == d[0]);
	}

	@Test
	public void longArrayToFloatArray() {
		float[] f = (float[]) ArrayUtil.convertArrayType(new long[] { 1L }, float.class);
		Assert.assertTrue(1.0f == f[0]);
	}

	@Test
	public void longArrayToIntArray() {
		int[] i = (int[]) ArrayUtil.convertArrayType(new long[] { 1L }, int.class);
		Assert.assertTrue(1 == i[0]);
	}

	@Test
	public void longObjectArrayToBooleanObjectArray() {
		Boolean[] b = (Boolean[]) ArrayUtil.convertArrayType(new Long[] { 1L }, Boolean.class);
		Assert.assertTrue(true == b[0]);
	}

	@Test
	public void longObjectArrayToByteObjectArray() {
		Byte[] b = (Byte[]) ArrayUtil.convertArrayType(new Long[] { 1L }, Byte.class);
		Assert.assertTrue((byte) 1 == b[0]);
	}

	@Test
	public void longObjectArrayToDoubleObjectArray() {
		Double[] d = (Double[]) ArrayUtil.convertArrayType(new Long[] { 1L }, Double.class);
		Assert.assertTrue(1.0d == d[0]);
	}

	@Test
	public void longObjectArrayToFloatObjectArray() {
		Float[] f = (Float[]) ArrayUtil.convertArrayType(new Long[] { 1L }, Float.class);
		Assert.assertTrue(1.0f == f[0]);
	}

	@Test
	public void longObjectArrayToIntegerObjectArray() {
		Integer[] i = (Integer[]) ArrayUtil.convertArrayType(new Long[] { 1L }, Integer.class);
		Assert.assertTrue(1 == i[0]);
	}

	@Test
	public void longObjectArrayToStringArray() {
		String[] s = (String[]) ArrayUtil.convertArrayType(new Long[] { 1L }, String.class);
		Assert.assertTrue("1".equals(s[0]));
	}

	@Test
	public void stringArrayToBooleanArray() {
		boolean[] b = (boolean[]) ArrayUtil.convertArrayType(new String[] { "true" }, boolean.class);
		Assert.assertTrue(true == b[0]);
	}

	@Test
	public void stringArrayToBooleanObjectArray() {
		Boolean[] b = (Boolean[]) ArrayUtil.convertArrayType(new String[] { "true" }, Boolean.class);
		Assert.assertTrue(true == b[0]);
	}

	@Test
	public void stringArrayToByteArray() {
		byte[] b = (byte[]) ArrayUtil.convertArrayType(new String[] { "1" }, byte.class);
		Assert.assertTrue((byte) 1 == b[0]);
	}

	@Test
	public void stringArrayToByteObjectArray() {
		Byte[] b = (Byte[]) ArrayUtil.convertArrayType(new String[] { "1" }, Byte.class);
		Assert.assertTrue((byte) 1 == b[0]);
	}

	@Test
	public void stringArrayToDoubleArray() {
		double[] d = (double[]) ArrayUtil.convertArrayType(new String[] { "1.0" }, double.class);
		Assert.assertTrue(1.0d == d[0]);
	}

	@Test
	public void stringArrayToDoubleObjectArray() {
		Double[] d = (Double[]) ArrayUtil.convertArrayType(new String[] { "1.0" }, Double.class);
		Assert.assertTrue(1.0d == d[0]);
	}

	@Test
	public void stringArrayToFloatArray() {
		float[] f = (float[]) ArrayUtil.convertArrayType(new String[] { "1.0" }, float.class);
		Assert.assertTrue(1.0f == f[0]);
	}

	@Test
	public void stringArrayToFloatObjectArray() {
		Float[] f = (Float[]) ArrayUtil.convertArrayType(new String[] { "1.0" }, Float.class);
		Assert.assertTrue(1.0f == f[0]);
	}

	@Test
	public void stringArrayToIntArray() {
		int[] i = (int[]) ArrayUtil.convertArrayType(new String[] { "1" }, int.class);
		Assert.assertTrue(1 == i[0]);
	}

	@Test
	public void stringArrayToIntegerObjectArray() {
		Integer[] i = (Integer[]) ArrayUtil.convertArrayType(new String[] { "1" }, Integer.class);
		Assert.assertTrue(1 == i[0]);
	}

	@Test
	public void stringArrayToLongArray() {
		long[] l = (long[]) ArrayUtil.convertArrayType(new String[] { "1" }, long.class);
		Assert.assertTrue(1L == l[0]);
	}

	@Test
	public void stringArrayToLongObjectArray() {
		Long[] l = (Long[]) ArrayUtil.convertArrayType(new String[] { "1" }, Long.class);
		Assert.assertTrue(1L == l[0]);
	}

	@Test
	public void unsignedByteArrayToDoubleArray() {
		double[] d = (double[]) ArrayUtil.convertUnsignedArrayType(new byte[] { 1 }, double.class);
		Assert.assertTrue(1.0d == d[0]);
	}

	@Test
	public void unsignedByteArrayToDoubleObjectArray() {
		Double[] d = (Double[]) ArrayUtil.convertUnsignedArrayType(new byte[] { 1 }, Double.class);
		Assert.assertTrue(1.0d == d[0]);
	}

	@Test
	public void unsignedByteArrayToFloatArray() {
		float[] f = (float[]) ArrayUtil.convertUnsignedArrayType(new byte[] { 1 }, float.class);
		Assert.assertTrue(1.0d == f[0]);
	}

	@Test
	public void unsignedByteArrayToFloatObjectArray() {
		Float[] f = (Float[]) ArrayUtil.convertUnsignedArrayType(new byte[] { 1 }, Float.class);
		Assert.assertTrue(1.0d == f[0]);
	}

	@Test
	public void unsignedByteArrayToIntArray() {
		int[] i = (int[]) ArrayUtil.convertUnsignedArrayType(new byte[] { 1 }, int.class);
		Assert.assertTrue(1 == i[0]);
	}

	@Test
	public void unsignedByteArrayToIntegerObjectArray() {
		Integer[] i = (Integer[]) ArrayUtil.convertUnsignedArrayType(new byte[] { 1 }, Integer.class);
		Assert.assertTrue(1 == i[0]);
	}

	@Test
	public void unsignedByteArrayToLongArray() {
		long[] l = (long[]) ArrayUtil.convertUnsignedArrayType(new byte[] { 1 }, long.class);
		Assert.assertTrue(1 == l[0]);
	}

	@Test
	public void unsignedByteArrayToLongObjectArray() {
		Long[] l = (Long[]) ArrayUtil.convertUnsignedArrayType(new byte[] { 1 }, Long.class);
		Assert.assertTrue(1 == l[0]);
	}

	@Test
	public void unsignedByteArrayToStringArray() {
		String[] s = (String[]) ArrayUtil.convertUnsignedArrayType(new byte[] { 1 }, String.class);
		Assert.assertTrue("1".equals(s[0]));
	}

	@Test
	public void booleanTensorToBooleanArray() {
		boolean[] b = new boolean[] { true, false };
		Tensor<Boolean> tensor = Tensor.create(b, Boolean.class);
		boolean[] result = ArrayUtil.booleanTensorToBooleanArray(tensor);
		BooleanLogicTest.assertArrayEquals(b, result);
	}

	@Test
	public void booleanTensorToByteArray() {
		boolean[] b = new boolean[] { true, false };
		byte[] expected = new byte[] { 1, 0 };
		Tensor<Boolean> tensor = Tensor.create(b, Boolean.class);
		byte[] result = ArrayUtil.booleanTensorToByteArray(tensor);
		Assert.assertArrayEquals(expected, result);
	}

	@Test
	public void booleanTensorToMultidimensionalBooleanArray() {
		boolean[][] b = new boolean[][] { { true, false }, { false, true } };
		Tensor<Boolean> tensor = Tensor.create(b, Boolean.class);
		boolean[][] result = (boolean[][]) ArrayUtil.booleanTensorToMultidimensionalBooleanArray(tensor);
		BooleanLogicTest.assertArrayEquals(b, result);
	}

	@Test
	public void convert2dIntArraysTo3dIntArray() {
		int[][] i1 = new int[][] { { 1, 2 }, { 3, 4 } };
		int[][] i2 = new int[][] { { 5, 6 }, { 7, 8 } };
		int[][][] i3 = ArrayUtil.convert2dIntArraysTo3dIntArray(i1, i2);
		Assert.assertArrayEquals(i3[0][0], i1[0]);
		Assert.assertArrayEquals(i3[0][1], i1[1]);
		Assert.assertArrayEquals(i3[1][0], i2[0]);
		Assert.assertArrayEquals(i3[1][1], i2[1]);
	}

	@Test
	public void doubleTensorToDoubleArray() {
		double[] d = new double[] { 1.0d, 2.0d };
		Tensor<Double> tensor = Tensor.create(d, Double.class);
		double[] result = ArrayUtil.doubleTensorToDoubleArray(tensor);
		Assert.assertArrayEquals(d, result, 0.0d);
	}

	@Test
	public void doubleTensorToMultidimensionalDoubleArray() {
		double[][] d = new double[][] { { 1.0d, 2.0d }, { 3.0d, 4.0d } };
		Tensor<Double> tensor = Tensor.create(d, Double.class);
		double[][] result = (double[][]) ArrayUtil.doubleTensorToMultidimensionalDoubleArray(tensor);
		Assert.assertArrayEquals(d, result);
	}

	@Test
	public void floatTensorToFloatArray() {
		float[] f = new float[] { 1.0f, 2.0f };
		Tensor<Float> tensor = Tensor.create(f, Float.class);
		float[] result = ArrayUtil.floatTensorToFloatArray(tensor);
		Assert.assertArrayEquals(f, result, 0.0f);
	}

	@Test
	public void floatTensorToMultidimensionalFloatArray() {
		float[][] f = new float[][] { { 1.0f, 2.0f }, { 3.0f, 4.0f } };
		Tensor<Float> tensor = Tensor.create(f, Float.class);
		float[][] result = (float[][]) ArrayUtil.floatTensorToMultidimensionalFloatArray(tensor);
		Assert.assertArrayEquals(f, result);
	}

	@Test
	public void longTensorToLongArray() {
		long[] l = new long[] { 1L, 2L };
		Tensor<Long> tensor = Tensor.create(l, Long.class);
		long[] result = ArrayUtil.longTensorToLongArray(tensor);
		Assert.assertArrayEquals(l, result);
	}

	@Test
	public void longTensorToMultidimensionalLongArray() {
		long[][] l = new long[][] { { 1L, 2L }, { 3L, 4L } };
		Tensor<Long> tensor = Tensor.create(l, Long.class);
		long[][] result = (long[][]) ArrayUtil.longTensorToMultidimensionalLongArray(tensor);
		Assert.assertArrayEquals(l, result);
	}

	@Test
	public void intTensorToIntArray() {
		int[] i = new int[] { 1, 2 };
		Tensor<Integer> tensor = Tensor.create(i, Integer.class);
		int[] result = ArrayUtil.intTensorToIntArray(tensor);
		Assert.assertArrayEquals(i, result);
	}

	@Test
	public void intTensorToMultidimensionalIntArray() {
		int[][] i = new int[][] { { 1, 2 }, { 3, 4 } };
		Tensor<Integer> tensor = Tensor.create(i, Integer.class);
		int[][] result = (int[][]) ArrayUtil.intTensorToMultidimensionalIntArray(tensor);
		Assert.assertArrayEquals(i, result);
	}

	@Test
	public void dToF() {
		double[] d = new double[] { 1.0d, 2.0d };
		float[] f = new float[] { 1.0f, 2.0f };
		float[] result = ArrayUtil.dToF(d);
		Assert.assertArrayEquals(f, result, 0.0f);
	}

	@Test
	public void dToI() {
		double[] d = new double[] { 1.0d, 2.0d };
		int[] i = new int[] { 1, 2 };
		int[] result = ArrayUtil.dToI(d);
		Assert.assertArrayEquals(i, result);
	}

	@Test
	public void dToL() {
		double[] d = new double[] { 1.0d, 2.0d };
		long[] l = new long[] { 1L, 2L };
		long[] result = ArrayUtil.dToL(d);
		Assert.assertArrayEquals(l, result);
	}

	@Test
	public void fToD() {
		float[] f = new float[] { 1.0f, 2.0f };
		double[] d = new double[] { 1.0d, 2.0d };
		double[] result = ArrayUtil.fToD(f);
		Assert.assertArrayEquals(d, result, 0.0d);
	}

	@Test
	public void fToI() {
		float[] f = new float[] { 1.0f, 2.0f };
		int[] i = new int[] { 1, 2 };
		int[] result = ArrayUtil.fToI(f);
		Assert.assertArrayEquals(i, result);
	}

	@Test
	public void fToL() {
		float[] f = new float[] { 1.0f, 2.0f };
		long[] l = new long[] { 1L, 2L };
		long[] result = ArrayUtil.fToL(f);
		Assert.assertArrayEquals(l, result);
	}

	@Test
	public void iToD() {
		int[] i = new int[] { 1, 2 };
		double[] d = new double[] { 1.0d, 2.0d };
		double[] result = ArrayUtil.iToD(i);
		Assert.assertArrayEquals(d, result, 0.0d);
	}

	@Test
	public void iToF() {
		int[] i = new int[] { 1, 2 };
		float[] f = new float[] { 1.0f, 2.0f };
		float[] result = ArrayUtil.iToF(i);
		Assert.assertArrayEquals(f, result, 0.0f);
	}

	@Test
	public void iToL() {
		int[] i = new int[] { 1, 2 };
		long[] l = new long[] { 1L, 2L };
		long[] result = ArrayUtil.iToL(i);
		Assert.assertArrayEquals(l, result);
	}

	@Test
	public void lToD() {
		long[] l = new long[] { 1L, 2L };
		double[] d = new double[] { 1.0d, 2.0d };
		double[] result = ArrayUtil.lToD(l);
		Assert.assertArrayEquals(d, result, 0.0d);
	}

	@Test
	public void lToF() {
		long[] l = new long[] { 1L, 2L };
		float[] f = new float[] { 1.0f, 2.0f };
		float[] result = ArrayUtil.lToF(l);
		Assert.assertArrayEquals(f, result, 0.0f);
	}

	@Test
	public void lToI() {
		long[] l = new long[] { 1L, 2L };
		int[] i = new int[] { 1, 2 };
		int[] result = ArrayUtil.lToI(l);
		Assert.assertArrayEquals(i, result);
	}

	@Test
	public void firstElementValueOfMultidimArray_float() {
		float[][] f = new float[][] { { 1.0f, 2.0f }, { 3.0f, 4.0f } };
		float first = (float) ArrayUtil.firstElementValueOfMultidimArray(f);
		Assert.assertEquals(f[0][0], first, 0.0f);
	}

	@Test
	public void getArrayDimensions() {
		float[][] f = new float[][] { { 1.0f, 2.0f }, { 3.0f, 4.0f } };
		int[] dimensions = ArrayUtil.getArrayDimensions(f);
		Assert.assertArrayEquals(new int[] { 2, 2 }, dimensions);
	}

	@Test
	public void getArrayDimensionsList() {
		float[][] f = new float[][] { { 1.0f, 2.0f }, { 3.0f, 4.0f } };
		List<Integer> dimensions = ArrayUtil.getArrayDimensionsList(f);
		Assert.assertTrue(2 == dimensions.size());
		Assert.assertTrue(2 == dimensions.get(0));
		Assert.assertTrue(2 == dimensions.get(1));
	}

	@Test
	public void maxIndexDouble() {
		double[] d = new double[] { 1.0d, 2.0d, 3.0d, 2.0d, 1.0d };
		int i = ArrayUtil.maxIndex(d);
		Assert.assertEquals(2, i);
	}

	@Test
	public void maxIndexFloat() {
		float[] f = new float[] { 1.0f, 2.0f, 3.0f, 2.0f, 1.0f };
		int i = ArrayUtil.maxIndex(f);
		Assert.assertEquals(2, i);
	}

	@Test
	public void maxIndicesDouble() {
		double[] d1 = new double[] { 1.0d, 2.0d, 3.0d, 2.0d, 1.0d };
		double[] d2 = new double[] { 1.0d, 2.0d, 3.0d, 4.0d, 1.0d };
		double[][] d3 = new double[][] { d1, d2 };
		int[] i = ArrayUtil.maxIndices(d3);
		Assert.assertArrayEquals(new int[] { 2, 3 }, i);
	}

	@Test
	public void maxIndicesFloat() {
		float[] f1 = new float[] { 1.0f, 2.0f, 3.0f, 2.0f, 1.0f };
		float[] f2 = new float[] { 1.0f, 2.0f, 3.0f, 4.0f, 1.0f };
		float[][] f3 = new float[][] { f1, f2 };
		int[] i = ArrayUtil.maxIndices(f3);
		Assert.assertArrayEquals(new int[] { 2, 3 }, i);
	}

	@Test
	public void multidimStringsToMultidimBytes() {
		String[][] s = new String[][] { { "AB", "CD" }, { "EF", "GH" } };
		byte[][][] expected = new byte[][][] { { { 65, 66 }, { 67, 68 } }, { { 69, 70 }, { 71, 72 } } };
		byte[][][] b = (byte[][][]) ArrayUtil.multidimStringsToMultidimBytes(s);
		Assert.assertArrayEquals(expected, b);
	}

	@Test
	public void multidimBytesToMultidimStrings() {
		byte[][][] b = new byte[][][] { { { 65, 66 }, { 67, 68 } }, { { 69, 70 }, { 71, 72 } } };
		String[][] expected = new String[][] { { "AB", "CD" }, { "EF", "GH" } };
		String[][] s = (String[][]) ArrayUtil.multidimBytesToMultidimStrings(b);
		Assert.assertArrayEquals(expected, s);
	}

	@Test
	public void stringTensorToMultidimensionalStringArray() {
		String[][] s = new String[][] { { "AB", "CD" }, { "EF", "GH" } };
		byte[][][] b = (byte[][][]) ArrayUtil.multidimStringsToMultidimBytes(s);
		Tensor<String> tensor = Tensor.create(b, String.class);
		String[][] result = (String[][]) ArrayUtil.stringTensorToMultidimensionalStringArray(tensor);
		Assert.assertArrayEquals(s, result);
	}

	@Test
	public void uint8TensorToByteArray() {
		byte[] b = new byte[] { (byte) 1, (byte) 2 };
		Tensor<UInt8> tensor = Tensor.create(b, UInt8.class);
		byte[] result = ArrayUtil.uint8TensorToByteArray(tensor);
		Assert.assertArrayEquals(b, result);
	}

	@Test
	public void uint8TensorToMultidimensionalByteArray() {
		byte[][] b = new byte[][] { { (byte) 1, (byte) 2 }, { (byte) 3, (byte) 4 } };
		Tensor<UInt8> tensor = Tensor.create(b, UInt8.class);
		byte[][] result = (byte[][]) ArrayUtil.uint8TensorToMultidimensionalByteArray(tensor);
		Assert.assertArrayEquals(b, result);
	}

	@Test
	public void firstDimensionValuesOfMultidimArray_2dInt() {
		int[][] i = new int[][] { { 1, 2 }, { 3, 4 } };
		int[] result = (int[]) ArrayUtil.firstDimensionValuesOfMultidimArray(i);
		Assert.assertArrayEquals(new int[] { 1, 3 }, result);
	}

	@Test
	public void firstDimensionValuesOfMultidimArray_3dInt() {
		int[][][] i = new int[][][] { { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 } },
				{ { 10, 11, 12 }, { 13, 14, 15 }, { 16, 17, 18 } },
				{ { 19, 20, 21 }, { 22, 23, 24 }, { 25, 26, 27 } } };
		int[] result = (int[]) ArrayUtil.firstDimensionValuesOfMultidimArray(i);
		Assert.assertArrayEquals(new int[] { 1, 10, 19 }, result);
	}

	@Test
	public void firstDimensionValuesOfMultidimArray_2dString() {
		String[][] s = new String[][] { { "one", "two" }, { "three", "four" } };
		String[] result = (String[]) ArrayUtil.firstDimensionValuesOfMultidimArray(s);
		Assert.assertArrayEquals(new String[] { "one", "three" }, result);
	}

	@Test
	public void firstDimensionValuesOfMultidimArray_3dString() {
		String[][][] s = new String[][][] {
				{ { "one", "two", "three" }, { "four", "five", "six" }, { "seven", "eight", "nine" } },
				{ { "ten", "eleven", "twelve" }, { "thirteen", "fourteen", "fifteen" },
						{ "sixteen", "seventeen", "eighteen" } },
				{ { "nineteen", "twenty", "twenty one" }, { "twenty two", "twenty three", "twenty four" },
						{ "twenty five", "twenty six", "twenty seven" } } };
		String[] result = (String[]) ArrayUtil.firstDimensionValuesOfMultidimArray(s);
		Assert.assertArrayEquals(new String[] { "one", "ten", "nineteen" }, result);
	}
}
