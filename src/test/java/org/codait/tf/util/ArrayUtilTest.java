package org.codait.tf.util;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

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
}
