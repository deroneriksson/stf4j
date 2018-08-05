package org.codait.tf.simple;

import java.io.IOException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.codait.tf.TFModel;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AddFloat64Test {

	protected static Logger log = LogManager.getLogger(AddFloat64Test.class);

	public static final String ADD_FLOAT64_MODEL_DIR = "./simple/add_float64";

	private TFModel model = null;

	@Before
	public void init() throws IOException {
		model = new TFModel(ADD_FLOAT64_MODEL_DIR).sig("serving_default");
	}

	@After
	public void after() {
	}

	@Test
	public void inputDoublesOutputDouble() {
		double result = model.in("input1", 1.0d).in("input2", 2.0d).out("output").run().getDouble("output");
		Assert.assertTrue(3.0d == result);
	}

	@Test
	public void inputFloatsOutputDouble() {
		double result = model.in("input1", 1.0f).in("input2", 2.0f).out("output").run().getDouble("output");
		Assert.assertTrue(3.0d == result);
	}

	@Test
	public void inputIntsOutputDouble() {
		double result = model.in("input1", 1).in("input2", 2).out("output").run().getDouble("output");
		Assert.assertTrue(3.0d == result);
	}

	@Test
	public void inputLongsOutputDouble() {
		double result = model.in("input1", 1L).in("input2", 2L).out("output").run().getDouble("output");
		Assert.assertTrue(3.0d == result);
	}

	@Test
	public void inputStringsOutputDouble() {
		double result = model.in("input1", "1").in("input2", "2").out("output").run().getDouble("output");
		Assert.assertTrue(3.0d == result);
	}

	@Test
	public void inputDoublesOutputInt() {
		int result = model.in("input1", 1.0d).in("input2", 2.0d).out("output").run().getInt("output");
		Assert.assertTrue(3 == result);
	}

	@Test
	public void inputDoublesOutputLong() {
		long result = model.in("input1", 1.0d).in("input2", 2.0d).out("output").run().getLong("output");
		Assert.assertTrue(3L == result);
	}

	@Test
	public void inputDoublesOutputFloat() {
		float result = model.in("input1", 1.0d).in("input2", 2.0d).out("output").run().getFloat("output");
		Assert.assertTrue(3.0f == result);
	}

	@Test
	public void inputDoublesOutputString() {
		String result = model.in("input1", 1.0d).in("input2", 2.0d).out("output").run().getString("output");
		Assert.assertTrue("3.0".equals(result));
	}

	@Test
	public void inputDoubleArraysOutputDoubleArray() {
		double[] result = model.in("input1", new double[] { 1.0d, 2.0d }).in("input2", new double[] { 3.0d, 4.0d })
				.out("output").run().getDoubleArray("output");
		Assert.assertArrayEquals(new double[] { 4.0d, 6.0d }, result, 0.0d);
	}

	@Test
	public void inputIntArraysOutputDoubleArray() {
		double[] result = model.in("input1", new int[] { 1, 2 }).in("input2", new int[] { 3, 4 }).out("output").run()
				.getDoubleArray("output");
		Assert.assertArrayEquals(new double[] { 4.0d, 6.0d }, result, 0.0d);
	}

	@Test
	public void inputLongArraysOutputDoubleArray() {
		double[] result = model.in("input1", new long[] { 1L, 2L }).in("input2", new long[] { 3L, 4L }).out("output")
				.run().getDoubleArray("output");
		Assert.assertArrayEquals(new double[] { 4.0d, 6.0d }, result, 0.0d);
	}

	@Test
	public void inputFloatArraysOutputDoubleArray() {
		double[] result = model.in("input1", new float[] { 1.0f, 2.0f }).in("input2", new float[] { 3.0f, 4.0f })
				.out("output").run().getDoubleArray("output");
		Assert.assertArrayEquals(new double[] { 4.0d, 6.0d }, result, 0.0d);
	}

	@Test
	public void inputStringArraysOutputDoubleArray() {
		double[] result = model.in("input1", new String[] { "1", "2" }).in("input2", new String[] { "3", "4" })
				.out("output").run().getDoubleArray("output");
		Assert.assertArrayEquals(new double[] { 4.0d, 6.0d }, result, 0.0d);
	}

	@Test
	public void inputDoubleArraysOutputFloatArray() {
		float[] result = model.in("input1", new double[] { 1.0d, 2.0d }).in("input2", new double[] { 3.0d, 4.0d })
				.out("output").run().getFloatArray("output");
		Assert.assertArrayEquals(new float[] { 4.0f, 6.0f }, result, 0.0f);
	}
}
