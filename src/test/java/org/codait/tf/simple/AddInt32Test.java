package org.codait.tf.simple;

import java.io.IOException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.codait.tf.TFModel;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AddInt32Test {

	protected static Logger log = LogManager.getLogger(AddInt32Test.class);

	public static final String ADD_INT32_MODEL_DIR = "./simple/add_int32";

	private TFModel model = null;

	@Before
	public void init() throws IOException {
		model = new TFModel(ADD_INT32_MODEL_DIR).sig("serving_default");
	}

	@After
	public void after() {
	}

	@Test
	public void inputIntsOutputInt() {
		int result = model.in("input1", 1).in("input2", 2).out("output").run().getInt("output");
		Assert.assertTrue(3 == result);
	}

	@Test
	public void inputLongsOutputInt() {
		int result = model.in("input1", 1L).in("input2", 2L).out("output").run().getInt("output");
		Assert.assertTrue(3 == result);
	}

	@Test
	public void inputIntsOutputLong() {
		long result = model.in("input1", 1).in("input2", 2).out("output").run().getLong("output");
		Assert.assertTrue(3L == result);
	}

	@Test
	public void inputFloatsOutputInt() {
		int result = model.in("input1", 1.0f).in("input2", 2.0f).out("output").run().getInt("output");
		Assert.assertTrue(3 == result);
	}

	@Test
	public void inputDoublesOutputInt() {
		int result = model.in("input1", 1.0d).in("input2", 2.0d).out("output").run().getInt("output");
		Assert.assertTrue(3 == result);
	}

	@Test
	public void inputStringsOutputInt() {
		int result = model.in("input1", "1").in("input2", "2").out("output").run().getInt("output");
		Assert.assertTrue(3 == result);
	}

	@Test
	public void inputIntsOutputFloat() {
		float result = model.in("input1", 1).in("input2", 2).out("output").run().getFloat("output");
		Assert.assertTrue(3.0f == result);
	}

	@Test
	public void inputIntsOutputDouble() {
		double result = model.in("input1", 1).in("input2", 2).out("output").run().getDouble("output");
		Assert.assertTrue(3.0d == result);
	}

	@Test
	public void inputIntsOutputString() {
		String result = model.in("input1", 1).in("input2", 2).out("output").run().getString("output");
		Assert.assertTrue("3".equals(result));
	}


	@Test
	public void inputIntArraysOutputIntArray() {
		int[] result = model.in("input1", new int[] { 1, 2 }).in("input2", new int[] { 3, 4 }).out("output").run()
				.getIntArray("output");
		Assert.assertArrayEquals(new int[] { 4, 6 }, result);
	}

	@Test
	public void inputIntArraysOutputLongArray() {
		long[] result = model.in("input1", new int[] { 1, 2 }).in("input2", new int[] { 3, 4 }).out("output").run()
				.getLongArray("output");
		Assert.assertArrayEquals(new long[] { 4L, 6L }, result);
	}

	@Test
	public void inputLongArraysOutputIntArray() {
		int[] result = model.in("input1", new long[] { 1L, 2L }).in("input2", new long[] { 3L, 4L }).out("output").run()
				.getIntArray("output");
		Assert.assertArrayEquals(new int[] { 4, 6 }, result);
	}

}
