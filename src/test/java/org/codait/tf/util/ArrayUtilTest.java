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
	public void booleanObjectArrayToByteObjectArray() {
		Byte[] b = (Byte[]) ArrayUtil.convertArrayType(new Boolean[] { true }, Byte.class);
		Assert.assertTrue((byte) 1 == b[0]);
	}

	@Test
	public void booleanArrayToDoubleArray() {
		double[] d = (double[]) ArrayUtil.convertArrayType(new boolean[] { true }, double.class);
		Assert.assertTrue(1.0d == d[0]);
	}

	@Test
	public void booleanObjectArrayToDoubleObjectArray() {
		Double[] d = (Double[]) ArrayUtil.convertArrayType(new Boolean[] { true }, Double.class);
		Assert.assertTrue(1.0d == d[0]);
	}

	@Test
	public void booleanArrayToFloatArray() {
		float[] f = (float[]) ArrayUtil.convertArrayType(new boolean[] { true }, float.class);
		Assert.assertTrue(1.0f == f[0]);
	}

	@Test
	public void booleanObjectArrayToFloatObjectArray() {
		Float[] f = (Float[]) ArrayUtil.convertArrayType(new Boolean[] { true }, Float.class);
		Assert.assertTrue(1.0f == f[0]);
	}

	@Test
	public void booleanArrayToIntArray() {
		int[] i = (int[]) ArrayUtil.convertArrayType(new boolean[] { true }, int.class);
		Assert.assertTrue(1 == i[0]);
	}

	@Test
	public void booleanObjectArrayToIntegerObjectArray() {
		Integer[] i = (Integer[]) ArrayUtil.convertArrayType(new Boolean[] { true }, Integer.class);
		Assert.assertTrue(1 == i[0]);
	}

	@Test
	public void booleanArrayToLongArray() {
		long[] l = (long[]) ArrayUtil.convertArrayType(new boolean[] { true }, long.class);
		Assert.assertTrue(1L == l[0]);
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
}
