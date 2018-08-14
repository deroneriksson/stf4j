package org.codait.tf;

import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
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

}
