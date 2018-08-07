package org.codait.tf.simple;

import java.io.IOException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.codait.tf.TFException;
import org.codait.tf.TFModel;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AddInt64Test {

	protected static Logger log = LogManager.getLogger(AddInt64Test.class);

	public static final String ADD_INT64_MODEL_DIR = "./simple/add_int64";

	private TFModel model = null;

	@Before
	public void init() throws IOException {
		model = new TFModel(ADD_INT64_MODEL_DIR).sig("serving_default");
	}

	@After
	public void after() {
	}

	@Test
	public void inputLongsOutputLong() {
		long result = model.in("input1", 1L).in("input2", 2L).out("output").run().getLong("output");
		Assert.assertTrue(3L == result);
	}

	@Test
	public void inputIntsOutputLong() {
		long result = model.in("input1", 1).in("input2", 2).out("output").run().getLong("output");
		Assert.assertTrue(3L == result);
	}

	@Test
	public void inputFloatsOutputLong() {
		long result = model.in("input1", 1.0f).in("input2", 2.0f).out("output").run().getLong("output");
		Assert.assertTrue(3L == result);
	}

	@Test
	public void inputDoublesOutputLong() {
		long result = model.in("input1", 1.0d).in("input2", 2.0d).out("output").run().getLong("output");
		Assert.assertTrue(3L == result);
	}

	@Test
	public void inputStringsOutputLong() {
		long result = model.in("input1", "1").in("input2", "2").out("output").run().getLong("output");
		Assert.assertTrue(3L == result);
	}

	@Test
	public void inputLongsOutputInt() {
		int result = model.in("input1", 1L).in("input2", 2L).out("output").run().getInt("output");
		Assert.assertTrue(3 == result);
	}

	@Test
	public void inputLongsOutputFloat() {
		float result = model.in("input1", 1L).in("input2", 2L).out("output").run().getFloat("output");
		Assert.assertTrue(3.0f == result);
	}

	@Test
	public void inputLongsOutputDouble() {
		double result = model.in("input1", 1L).in("input2", 2L).out("output").run().getDouble("output");
		Assert.assertTrue(3.0d == result);
	}

	@Test
	public void inputLongsOutputString() {
		String result = model.in("input1", 1L).in("input2", 2L).out("output").run().getString("output");
		Assert.assertTrue("3".equals(result));
	}

	@Test
	public void inputLongArraysOutputLongArray() {
		long[] result = model.in("input1", new long[] { 1L, 2L }).in("input2", new long[] { 3L, 4L }).out("output")
				.run().getLongArray("output");
		Assert.assertArrayEquals(new long[] { 4L, 6L }, result);
	}

	@Test
	public void inputIntArraysOutputLongArray() {
		long[] result = model.in("input1", new int[] { 1, 2 }).in("input2", new int[] { 3, 4 }).out("output").run()
				.getLongArray("output");
		Assert.assertArrayEquals(new long[] { 4, 6 }, result);
	}

	@Test
	public void inputFloatArraysOutputLongArray() {
		long[] result = model.in("input1", new float[] { 1.0f, 2.0f }).in("input2", new float[] { 3.0f, 4.0f })
				.out("output").run().getLongArray("output");
		Assert.assertArrayEquals(new long[] { 4L, 6L }, result);
	}

	@Test
	public void inputDoubleArraysOutputLongArray() {
		long[] result = model.in("input1", new double[] { 1.0d, 2.0d }).in("input2", new double[] { 3.0d, 4.0d })
				.out("output").run().getLongArray("output");
		Assert.assertArrayEquals(new long[] { 4L, 6L }, result);
	}

	@Test
	public void inputStringArraysOutputLongArray() {
		long[] result = model.in("input1", new String[] { "1", "2" }).in("input2", new String[] { "3", "4" })
				.out("output").run().getLongArray("output");
		Assert.assertArrayEquals(new long[] { 4L, 6L }, result);
	}

	@Test
	public void inputLongArraysOutputIntArray() {
		int[] result = model.in("input1", new long[] { 1L, 2L }).in("input2", new long[] { 3L, 4L }).out("output").run()
				.getIntArray("output");
		Assert.assertArrayEquals(new int[] { 4, 6 }, result);
	}

	@Test
	public void inputLongArraysOutputFloatArray() {
		float[] result = model.in("input1", new long[] { 1L, 2L }).in("input2", new long[] { 3L, 4L }).out("output")
				.run().getFloatArray("output");
		Assert.assertArrayEquals(new float[] { 4.0f, 6.0f }, result, 0.0f);
	}

	@Test
	public void inputLongArraysOutputDoubleArray() {
		double[] result = model.in("input1", new long[] { 1L, 2L }).in("input2", new long[] { 3L, 4L }).out("output")
				.run().getDoubleArray("output");
		Assert.assertArrayEquals(new double[] { 4.0d, 6.0d }, result, 0.0d);
	}

	@Test
	public void inputLongArraysOutputStringArray() {
		String[] result = model.in("input1", new long[] { 1L, 2L }).in("input2", new long[] { 3L, 4L }).out("output")
				.run().getStringArray("output");
		Assert.assertArrayEquals(new String[] { "4", "6" }, result);
	}

	@Test
	public void inputStringArraysOutputStringArray() {
		String[] result = model.in("input1", new String[] { "1", "2" }).in("input2", new String[] { "3", "4" })
				.out("output").run().getStringArray("output");
		Assert.assertArrayEquals(new String[] { "4", "6" }, result);
	}

	@Test
	public void inputMultiLongArraysOutputMultiLongArray() {
		long[][] input1 = new long[][] { { 1L, 2L }, { 3L, 4L } };
		long[][] input2 = new long[][] { { 1L, 2L }, { 3L, 4L } };
		long[][] expected = new long[][] { { 2L, 4L }, { 6L, 8L } };
		long[][] result = (long[][]) model.in("input1", input1).in("input2", input2).out("output").run()
				.getLongArrayMultidimensional("output");
		for (int i = 0; i < expected.length; i++) {
			Assert.assertArrayEquals(expected[i], result[i]);
		}
	}

	@Test
	public void inputMultiIntArraysOutputMultiIntArray() {
		int[][] input1 = new int[][] { { 1, 2 }, { 3, 4 } };
		int[][] input2 = new int[][] { { 1, 2 }, { 3, 4 } };
		int[][] expected = new int[][] { { 2, 4 }, { 6, 8 } };
		int[][] result = (int[][]) model.in("input1", input1).in("input2", input2).out("output").run()
				.getIntArrayMultidimensional("output");
		for (int i = 0; i < expected.length; i++) {
			Assert.assertArrayEquals(expected[i], result[i]);
		}
	}

	@Test
	public void inputMultiFloatArraysOutputMultiFloatArray() {
		float[][] input1 = new float[][] { { 1.0f, 2.0f }, { 3.0f, 4.0f } };
		float[][] input2 = new float[][] { { 1.0f, 2.0f }, { 3.0f, 4.0f } };
		float[][] expected = new float[][] { { 2.0f, 4.0f }, { 6.0f, 8.0f } };
		float[][] result = (float[][]) model.in("input1", input1).in("input2", input2).out("output").run()
				.getFloatArrayMultidimensional("output");
		for (int i = 0; i < expected.length; i++) {
			Assert.assertArrayEquals(expected[i], result[i], 0.0f);
		}
	}

	@Test
	public void inputMultiDoubleArraysOutputMultiDoubleArray() {
		double[][] input1 = new double[][] { { 1.0d, 2.0d }, { 3.0d, 4.0d } };
		double[][] input2 = new double[][] { { 1.0d, 2.0d }, { 3.0d, 4.0d } };
		double[][] expected = new double[][] { { 2.0d, 4.0d }, { 6.0d, 8.0d } };
		double[][] result = (double[][]) model.in("input1", input1).in("input2", input2).out("output").run()
				.getDoubleArrayMultidimensional("output");
		for (int i = 0; i < expected.length; i++) {
			Assert.assertArrayEquals(expected[i], result[i], 0.0d);
		}
	}

	@Test
	public void inputMultiStringArraysOutputMultiStringArray() {
		String[][] input1 = new String[][] { { "1", "2" }, { "3", "4" } };
		String[][] input2 = new String[][] { { "1", "2" }, { "3", "4" } };
		String[][] expected = new String[][] { { "2", "4" }, { "6", "8" } };
		String[][] result = (String[][]) model.in("input1", input1).in("input2", input2).out("output").run()
				.getStringArrayMultidimensional("output");
		for (int i = 0; i < expected.length; i++) {
			Assert.assertArrayEquals(expected[i], result[i]);
		}
	}

	@Test(expected = TFException.class)
	public void inputsNull() {
		model.in("input1", null).in("input2", null).out("output").run();
	}

	@Test(expected = TFException.class)
	public void missingInputs() {
		model.out("output").run();
	}

	@Test(expected = TFException.class)
	public void missingOutput() {
		model.in("input1", 1L).in("input2", 2L).run();
	}

	@Test(expected = TFException.class)
	public void badInputKey() {
		model.in("bad_input", 1L).in("input2", 2L).out("output").run();
	}

	@Test(expected = TFException.class)
	public void badOutputKey() {
		model.in("input1", 1L).in("input2", 2L).out("bad_output").run();
	}

	@Test
	public void inputLongsOutputLongNoSignatureDefKey() {
		long result = model.sig(null).in("input1", 1L).in("input2", 2L).out("output").run().getLong("output");
		Assert.assertTrue(3L == result);
	}

	@Test(expected = TFException.class)
	public void badInputNoSignatureDefKey() {
		model.sig(null).in("bad_input", 1L).in("input2", 2L).out("output").run();
	}

	@Test(expected = TFException.class)
	public void badOutputNoSignatureDefKey() {
		model.sig(null).in("input1", 1L).in("input2", 2L).out("bad_output").run();
	}

	@Test(expected = TFException.class)
	public void badSignatureDefKey() {
		model.sig("bad_sig_def_key");
	}

	@Test
	public void inputMultiIntArrayIntScalarOutputMultiIntArray() {
		int[][] input1 = new int[][] { { 1, 2 }, { 3, 4 } };
		int input2 = 5;
		int[][] expected = new int[][] { { 6, 7 }, { 8, 9 } };
		int[][] result = (int[][]) model.in("input1", input1).in("input2", input2).out("output").run()
				.getIntArrayMultidimensional("output");
		for (int i = 0; i < expected.length; i++) {
			Assert.assertArrayEquals(expected[i], result[i]);
		}
	}

	@Test
	public void inputMultiLongArrayLongScalarOutputMultiLongArray() {
		long[][] input1 = new long[][] { { 1L, 2L }, { 3L, 4L } };
		long input2 = 5L;
		long[][] expected = new long[][] { { 6L, 7L }, { 8L, 9L } };
		long[][] result = (long[][]) model.in("input1", input1).in("input2", input2).out("output").run()
				.getLongArrayMultidimensional("output");
		for (int i = 0; i < expected.length; i++) {
			Assert.assertArrayEquals(expected[i], result[i]);
		}
	}

	@Test
	public void inputMultiFloatArrayFloatScalarOutputMultiFloatArray() {
		float[][] input1 = new float[][] { { 1.0f, 2.0f }, { 3.0f, 4.0f } };
		float input2 = 5.0f;
		float[][] expected = new float[][] { { 6.0f, 7.0f }, { 8.0f, 9.0f } };
		float[][] result = (float[][]) model.in("input1", input1).in("input2", input2).out("output").run()
				.getFloatArrayMultidimensional("output");
		for (int i = 0; i < expected.length; i++) {
			Assert.assertArrayEquals(expected[i], result[i], 0.0f);
		}
	}

	@Test
	public void inputMultiDoubleArrayDoubleScalarOutputMultiDoubleArray() {
		double[][] input1 = new double[][] { { 1.0d, 2.0d }, { 3.0d, 4.0d } };
		double input2 = 5.0d;
		double[][] expected = new double[][] { { 6.0d, 7.0d }, { 8.0d, 9.0d } };
		double[][] result = (double[][]) model.in("input1", input1).in("input2", input2).out("output").run()
				.getDoubleArrayMultidimensional("output");
		for (int i = 0; i < expected.length; i++) {
			Assert.assertArrayEquals(expected[i], result[i], 0.0d);
		}
	}

	@Test
	public void inputMultiStringArrayStringScalarOutputMultiStringArray() {
		String[][] input1 = new String[][] { { "1", "2" }, { "3", "4" } };
		String input2 = "5";
		String[][] expected = new String[][] { { "6", "7" }, { "8", "9" } };
		String[][] result = (String[][]) model.in("input1", input1).in("input2", input2).out("output").run()
				.getStringArrayMultidimensional("output");
		for (int i = 0; i < expected.length; i++) {
			Assert.assertArrayEquals(expected[i], result[i]);
		}
	}

	@Test
	public void inputIntArrayIntScalarOutputIntArray() {
		int[] result = model.in("input1", new int[] { 1, 2 }).in("input2", 3).out("output").run().getIntArray("output");
		Assert.assertArrayEquals(new int[] { 4, 5 }, result);
	}

	@Test
	public void inputLongArrayLongScalarOutputLongArray() {
		long[] result = model.in("input1", new long[] { 1L, 2L }).in("input2", 3L).out("output").run()
				.getLongArray("output");
		Assert.assertArrayEquals(new long[] { 4, 5 }, result);
	}

	@Test
	public void inputFloatArrayFloatScalarOutputFloatArray() {
		float[] result = model.in("input1", new float[] { 1.0f, 2.0f }).in("input2", 3.0f).out("output").run()
				.getFloatArray("output");
		Assert.assertArrayEquals(new float[] { 4.0f, 5.0f }, result, 0.0f);
	}

	@Test
	public void inputDoubleArrayDoubleScalarOutputDoubleArray() {
		double[] result = model.in("input1", new double[] { 1.0d, 2.0d }).in("input2", 3.0d).out("output").run()
				.getDoubleArray("output");
		Assert.assertArrayEquals(new double[] { 4.0d, 5.0d }, result, 0.0d);
	}

	@Test
	public void inputStringArrayStringScalarOutputStringArray() {
		String[] result = model.in("input1", new String[] { "1", "2" }).in("input2", "3").out("output").run()
				.getStringArray("output");
		Assert.assertArrayEquals(new String[] { "4", "5" }, result);
	}

	@Test
	public void inputLongArraysOutputInt() {
		int result = model.in("input1", new long[] { 1L, 2L }).in("input2", new long[] { 3L, 4L }).out("output").run()
				.getInt("output");
		Assert.assertTrue(4 == result);
	}

	@Test
	public void inputLongArraysOutputLong() {
		long result = model.in("input1", new long[] { 1L, 2L }).in("input2", new long[] { 3L, 4L }).out("output").run()
				.getLong("output");
		Assert.assertTrue(4L == result);
	}

	@Test
	public void inputLongArraysOutputFloat() {
		float result = model.in("input1", new long[] { 1L, 2L }).in("input2", new long[] { 3L, 4L }).out("output").run()
				.getFloat("output");
		Assert.assertTrue(4.0f == result);
	}

	@Test
	public void inputLongArraysOutputDouble() {
		double result = model.in("input1", new long[] { 1L, 2L }).in("input2", new long[] { 3L, 4L }).out("output")
				.run().getDouble("output");
		Assert.assertTrue(4.0d == result);
	}

	@Test
	public void inputLongArraysOutputString() {
		String result = model.in("input1", new long[] { 1L, 2L }).in("input2", new long[] { 3L, 4L }).out("output")
				.run().getString("output");
		Assert.assertTrue("4".equals(result));
	}

	@Test
	public void inputMultiLongArraysOutputInt() {
		long[][] input1 = new long[][] { { 1L, 2L }, { 3L, 4L } };
		long[][] input2 = new long[][] { { 1L, 2L }, { 3L, 4L } };
		int result = model.in("input1", input1).in("input2", input2).out("output").run().getInt("output");
		Assert.assertTrue(2 == result);
	}

	@Test
	public void inputMultiLongArraysOutputLong() {
		long[][] input1 = new long[][] { { 1L, 2L }, { 3L, 4L } };
		long[][] input2 = new long[][] { { 1L, 2L }, { 3L, 4L } };
		long result = model.in("input1", input1).in("input2", input2).out("output").run().getLong("output");
		Assert.assertTrue(2L == result);
	}

	@Test
	public void inputMultiLongArraysOutputFloat() {
		long[][] input1 = new long[][] { { 1L, 2L }, { 3L, 4L } };
		long[][] input2 = new long[][] { { 1L, 2L }, { 3L, 4L } };
		float result = model.in("input1", input1).in("input2", input2).out("output").run().getFloat("output");
		Assert.assertTrue(2.0f == result);
	}

	@Test
	public void inputMultiLongArraysOutputDouble() {
		long[][] input1 = new long[][] { { 1L, 2L }, { 3L, 4L } };
		long[][] input2 = new long[][] { { 1L, 2L }, { 3L, 4L } };
		double result = model.in("input1", input1).in("input2", input2).out("output").run().getDouble("output");
		Assert.assertTrue(2.0d == result);
	}

	@Test
	public void inputMultiLongArraysOutputString() {
		long[][] input1 = new long[][] { { 1L, 2L }, { 3L, 4L } };
		long[][] input2 = new long[][] { { 1L, 2L }, { 3L, 4L } };
		String result = model.in("input1", input1).in("input2", input2).out("output").run().getString("output");
		Assert.assertTrue("2".equals(result));
	}
}
