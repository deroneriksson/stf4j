package org.codait.tf;

import static org.codait.tf.util.TypeUtil.byte_to_boolean;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.codait.tf.simple.BooleanLogicTest;
import org.codait.tf.util.ArrayUtil;
import org.junit.Assert;
import org.junit.Test;
import org.tensorflow.Tensor;

public class TensorTest {

	protected static Logger log = LogManager.getLogger(TensorTest.class);

	@Test
	public void createFloatTensorWithFloat() {
		Tensor<Float> tensor = Tensor.create(1.0f, Float.class);
		float val = tensor.floatValue();
		Assert.assertEquals(1.0f, val, 0.0f);
	}

	// float objects cannot be used as elements in a TensorFlow Tensor
	@Test(expected = IllegalArgumentException.class)
	public void createFloatTensorWithFloat_PrimitiveParameter() {
		Tensor.create(1.0f, float.class);
	}

	@Test
	public void createFloatTensorWithFloatObject() {
		Tensor<Float> tensor = Tensor.create(new Float(1.0f), Float.class);
		float val = tensor.floatValue();
		Assert.assertEquals(1.0f, val, 0.0f);
	}

	@Test(expected = NullPointerException.class)
	public void createFloatTensorWithNull() {
		Tensor.create(null, Float.class);
	}

	@Test
	public void createFloatTensorWithFloatArray() {
		float[] f = new float[] { 1.0f, 2.0f };
		Tensor<Float> tensor = Tensor.create(f, Float.class);
		FloatBuffer fb = FloatBuffer.allocate(tensor.numElements());
		tensor.writeTo(fb);
		float[] result = fb.array();
		Assert.assertArrayEquals(f, result, 0.0f);
	}

	// cannot create non-scalar Tensors from arrays of boxed values
	@Test(expected = IllegalArgumentException.class)
	public void createFloatTensorWithFloatObjectArray() {
		Float[] f = new Float[] { new Float(1.0f), new Float(2.0f) };
		Tensor.create(f, Float.class);
	}

	@Test
	public void createDoubleTensorWithDouble() {
		Tensor<Double> tensor = Tensor.create(1.0d, Double.class);
		double val = tensor.doubleValue();
		Assert.assertEquals(1.0d, val, 0.0d);
	}

	// double objects cannot be used as elements in a TensorFlow Tensor
	@Test(expected = IllegalArgumentException.class)
	public void createDoubleTensorWithDouble_PrimitiveParameter() {
		Tensor.create(1.0d, double.class);
	}

	@Test
	public void createDoubleTensorWithDoubleObject() {
		Tensor<Double> tensor = Tensor.create(new Double(1.0d), Double.class);
		double val = tensor.doubleValue();
		Assert.assertEquals(1.0d, val, 0.0d);
	}

	@Test(expected = NullPointerException.class)
	public void createDoubleTensorWithNull() {
		Tensor.create(null, Double.class);
	}

	@Test
	public void createDoubleTensorWithDoubleArray() {
		double[] d = new double[] { 1.0d, 2.0d };
		Tensor<Double> tensor = Tensor.create(d, Double.class);
		DoubleBuffer db = DoubleBuffer.allocate(tensor.numElements());
		tensor.writeTo(db);
		double[] result = db.array();
		Assert.assertArrayEquals(d, result, 0.0d);
	}

	// cannot create non-scalar Tensors from arrays of boxed values
	@Test(expected = IllegalArgumentException.class)
	public void createDoubleTensorWithDoubleObjectArray() {
		Double[] d = new Double[] { new Double(1.0d), new Double(2.0d) };
		Tensor.create(d, Double.class);
	}

	@Test
	public void createIntegerTensorWithInt() {
		Tensor<Integer> tensor = Tensor.create(1, Integer.class);
		int val = tensor.intValue();
		Assert.assertEquals(1, val);
	}

	// int objects cannot be used as elements in a TensorFlow Tensor
	@Test(expected = IllegalArgumentException.class)
	public void createIntegerTensorWithInt_PrimitiveParameter() {
		Tensor.create(1, int.class);
	}

	@Test
	public void createIntegerTensorWithIntegerObject() {
		Tensor<Integer> tensor = Tensor.create(new Integer(1), Integer.class);
		int val = tensor.intValue();
		Assert.assertEquals(1, val);
	}

	@Test(expected = NullPointerException.class)
	public void createIntegerTensorWithNull() {
		Tensor.create(null, Integer.class);
	}

	@Test
	public void createIntegerTensorWithIntArray() {
		int[] i = new int[] { 1, 2 };
		Tensor<Integer> tensor = Tensor.create(i, Integer.class);
		IntBuffer ib = IntBuffer.allocate(tensor.numElements());
		tensor.writeTo(ib);
		int[] result = ib.array();
		Assert.assertArrayEquals(i, result);
	}

	// cannot create non-scalar Tensors from arrays of boxed values
	@Test(expected = IllegalArgumentException.class)
	public void createIntegerTensorWithIntegerObjectArray() {
		Integer[] i = new Integer[] { new Integer(1), new Integer(2) };
		Tensor.create(i, Float.class);
	}

	@Test
	public void createLongTensorWithLong() {
		Tensor<Long> tensor = Tensor.create(1L, Long.class);
		long val = tensor.longValue();
		Assert.assertEquals(1L, val);
	}

	// long objects cannot be used as elements in a TensorFlow Tensor
	@Test(expected = IllegalArgumentException.class)
	public void createLongTensorWithLong_PrimitiveParameter() {
		Tensor.create(1L, long.class);
	}

	@Test
	public void createLongTensorWithLongObject() {
		Tensor<Long> tensor = Tensor.create(new Long(1L), Long.class);
		long val = tensor.longValue();
		Assert.assertEquals(1L, val);
	}

	@Test(expected = NullPointerException.class)
	public void createLongTensorWithNull() {
		Tensor.create(null, Long.class);
	}

	@Test
	public void createLongTensorWithLongArray() {
		long[] l = new long[] { 1L, 2L };
		Tensor<Long> tensor = Tensor.create(l, Long.class);
		LongBuffer lb = LongBuffer.allocate(tensor.numElements());
		tensor.writeTo(lb);
		long[] result = lb.array();
		Assert.assertArrayEquals(l, result);
	}

	// cannot create non-scalar Tensors from arrays of boxed values
	@Test(expected = IllegalArgumentException.class)
	public void createLongTensorWithLongObjectArray() {
		Long[] l = new Long[] { new Long(1L), new Long(2L) };
		Tensor.create(l, Long.class);
	}

	@Test
	public void createBooleanTensorWithBoolean() {
		Tensor<Boolean> tensor = Tensor.create(true, Boolean.class);
		boolean val = tensor.booleanValue();
		Assert.assertEquals(true, val);
	}

	// boolean objects cannot be used as elements in a TensorFlow Tensor
	@Test(expected = IllegalArgumentException.class)
	public void createBooleanTensorWithBoolean_PrimitiveParameter() {
		Tensor.create(true, boolean.class);
	}

	@Test
	public void createBooleanTensorWithBooleanObject() {
		Tensor<Boolean> tensor = Tensor.create(new Boolean(true), Boolean.class);
		boolean val = tensor.booleanValue();
		Assert.assertEquals(true, val);
	}

	@Test(expected = NullPointerException.class)
	public void createBooleanTensorWithNull() {
		Tensor.create(null, Boolean.class);
	}

	@Test
	public void createBooleanTensorWithBooleanArray() {
		boolean[] b = new boolean[] { true, false };
		Tensor<Boolean> tensor = Tensor.create(b, Boolean.class);
		ByteBuffer bb = ByteBuffer.allocate(tensor.numElements());
		tensor.writeTo(bb);
		byte[] byteArray = bb.array();
		boolean[] booleanArray = new boolean[byteArray.length];
		for (int i = 0; i < byteArray.length; i++) {
			booleanArray[i] = byte_to_boolean(byteArray[i]);
		}
		BooleanLogicTest.assertArrayEquals(b, booleanArray);
	}

	// cannot create non-scalar Tensors from arrays of boxed values
	@Test(expected = IllegalArgumentException.class)
	public void createBooleanTensorWithBooleanObjectArray() {
		Boolean[] b = new Boolean[] { new Boolean(true), new Boolean(false) };
		Tensor.create(b, Boolean.class);
	}

	@Test
	public void createStringTensorWithStringBytes() {
		Tensor<String> tensor = Tensor.create("test".getBytes(), String.class);
		String val = new String(tensor.bytesValue());
		Assert.assertEquals("test", val);
	}

	// cannot create Tensors of type java.lang.String
	@Test(expected = IllegalArgumentException.class)
	public void createStringTensorWithStringObject() {
		Tensor.create("test", String.class);
	}

	@Test(expected = NullPointerException.class)
	public void createStringTensorWithNull() {
		Tensor.create(null, String.class);
	}

	@Test
	public void createStringTensorWithStringByteArray() {
		byte[][] b = new byte[][] { "hello".getBytes(), "world".getBytes() };
		Tensor<String> tensor = Tensor.create(b, String.class);
		String[] result = (String[]) ArrayUtil.stringTensorToMultidimensionalStringArray(tensor);
		Assert.assertArrayEquals(new String[] { "hello", "world" }, result);
	}

	// cannot create non-scalar Tensors from arrays of boxed values
	@Test(expected = IllegalArgumentException.class)
	public void createStringTensorWithByteObjectArray() {
		Byte[] b1 = new Byte[] { 65, 66 };
		Byte[] b2 = new Byte[] { 65, 66 };
		Byte[][] b = new Byte[][] { b1, b2 };
		Tensor.create(b, String.class);
	}

}
