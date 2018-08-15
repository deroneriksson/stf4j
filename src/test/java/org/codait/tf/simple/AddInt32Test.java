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

	@Test
	public void inputLongArraysOutputLongArray() {
		long[] result = model.in("input1", new long[] { 1L, 2L }).in("input2", new long[] { 3L, 4L }).out("output")
				.run().getLongArray("output");
		Assert.assertArrayEquals(new long[] { 4L, 6L }, result);
	}

	@Test
	public void inputFloatArraysOutputIntArray() {
		int[] result = model.in("input1", new float[] { 1.0f, 2.0f }).in("input2", new float[] { 3.0f, 4.0f })
				.out("output").run().getIntArray("output");
		Assert.assertArrayEquals(new int[] { 4, 6 }, result);
	}

	@Test
	public void inputDoubleArraysOutputIntArray() {
		int[] result = model.in("input1", new double[] { 1.0d, 2.0d }).in("input2", new double[] { 3.0d, 4.0d })
				.out("output").run().getIntArray("output");
		Assert.assertArrayEquals(new int[] { 4, 6 }, result);
	}

	@Test
	public void inputStringArraysOutputIntArray() {
		int[] result = model.in("input1", new String[] { "1", "2" }).in("input2", new String[] { "3", "4" })
				.out("output").run().getIntArray("output");
		Assert.assertArrayEquals(new int[] { 4, 6 }, result);
	}

	@Test
	public void inputIntArraysOutputFloatArray() {
		float[] result = model.in("input1", new int[] { 1, 2 }).in("input2", new int[] { 3, 4 }).out("output").run()
				.getFloatArray("output");
		Assert.assertArrayEquals(new float[] { 4.0f, 6.0f }, result, 0.0f);
	}

	@Test
	public void inputIntArraysOutputDoubleArray() {
		double[] result = model.in("input1", new int[] { 1, 2 }).in("input2", new int[] { 3, 4 }).out("output").run()
				.getDoubleArray("output");
		Assert.assertArrayEquals(new double[] { 4.0d, 6.0d }, result, 0.0d);
	}

	@Test
	public void inputIntArraysOutputStringArray() {
		String[] result = model.in("input1", new int[] { 1, 2 }).in("input2", new int[] { 3, 4 }).out("output").run()
				.getStringArray("output");
		Assert.assertArrayEquals(new String[] { "4", "6" }, result);
	}

	@Test
	public void inputStringArraysOutputStringArray() {
		String[] result = model.in("input1", new String[] { "1", "2" }).in("input2", new String[] { "3", "4" })
				.out("output").run().getStringArray("output");
		Assert.assertArrayEquals(new String[] { "4", "6" }, result);
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

	@Test
	public void inputIntArrayIntScalarOutputIntArray() {
		int[] result = model.in("input1", new int[] { 1, 2 }).in("input2", 3).out("output").run().getIntArray("output");
		Assert.assertArrayEquals(new int[] { 4, 5 }, result);
	}

	@Test
	public void inputLongArrayIntScalarOutputIntArray() {
		int[] result = model.in("input1", new long[] { 1L, 2L }).in("input2", 3).out("output").run()
				.getIntArray("output");
		Assert.assertArrayEquals(new int[] { 4, 5 }, result);
	}

	@Test
	public void inputFloatArrayIntScalarOutputIntArray() {
		int[] result = model.in("input1", new float[] { 1.0f, 2.0f }).in("input2", 3).out("output").run()
				.getIntArray("output");
		Assert.assertArrayEquals(new int[] { 4, 5 }, result);
	}

	@Test
	public void inputDoubleArrayIntScalarOutputIntArray() {
		int[] result = model.in("input1", new double[] { 1.0d, 2.0d }).in("input2", 3).out("output").run()
				.getIntArray("output");
		Assert.assertArrayEquals(new int[] { 4, 5 }, result);
	}

	@Test
	public void inputStringArrayIntScalarOutputIntArray() {
		int[] result = model.in("input1", new String[] { "1", "2" }).in("input2", 3).out("output").run()
				.getIntArray("output");
		Assert.assertArrayEquals(new int[] { 4, 5 }, result);
	}

	@Test
	public void inputIntScalarIntArrayOutputIntArray() {
		int[] result = model.in("input1", 1).in("input2", new int[] { 2, 3 }).out("output").run().getIntArray("output");
		Assert.assertArrayEquals(new int[] { 3, 4 }, result);
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
	public void inputMultiLongArrayIntScalarOutputMultiIntArray() {
		long[][] input1 = new long[][] { { 1L, 2L }, { 3L, 4L } };
		int input2 = 5;
		int[][] expected = new int[][] { { 6, 7 }, { 8, 9 } };
		int[][] result = (int[][]) model.in("input1", input1).in("input2", input2).out("output").run()
				.getIntArrayMultidimensional("output");
		for (int i = 0; i < expected.length; i++) {
			Assert.assertArrayEquals(expected[i], result[i]);
		}
	}

	@Test
	public void inputMultiFloatArrayIntScalarOutputMultiIntArray() {
		float[][] input1 = new float[][] { { 1.0f, 2.0f }, { 3.0f, 4.0f } };
		int input2 = 5;
		int[][] expected = new int[][] { { 6, 7 }, { 8, 9 } };
		int[][] result = (int[][]) model.in("input1", input1).in("input2", input2).out("output").run()
				.getIntArrayMultidimensional("output");
		for (int i = 0; i < expected.length; i++) {
			Assert.assertArrayEquals(expected[i], result[i]);
		}
	}

	@Test
	public void inputMultiDoubleArrayIntScalarOutputMultiIntArray() {
		double[][] input1 = new double[][] { { 1.0d, 2.0d }, { 3.0d, 4.0d } };
		int input2 = 5;
		int[][] expected = new int[][] { { 6, 7 }, { 8, 9 } };
		int[][] result = (int[][]) model.in("input1", input1).in("input2", input2).out("output").run()
				.getIntArrayMultidimensional("output");
		for (int i = 0; i < expected.length; i++) {
			Assert.assertArrayEquals(expected[i], result[i]);
		}
	}

	@Test
	public void inputMultiStringArrayIntScalarOutputMultiIntArray() {
		String[][] input1 = new String[][] { { "1", "2" }, { "3", "4" } };
		int input2 = 5;
		int[][] expected = new int[][] { { 6, 7 }, { 8, 9 } };
		int[][] result = (int[][]) model.in("input1", input1).in("input2", input2).out("output").run()
				.getIntArrayMultidimensional("output");
		for (int i = 0; i < expected.length; i++) {
			Assert.assertArrayEquals(expected[i], result[i]);
		}
	}

	@Test
	public void inputIntScalarMultiIntArrayOutputMultiIntArray() {
		int input1 = 5;
		int[][] input2 = new int[][] { { 1, 2 }, { 3, 4 } };
		int[][] expected = new int[][] { { 6, 7 }, { 8, 9 } };
		int[][] result = (int[][]) model.in("input1", input1).in("input2", input2).out("output").run()
				.getIntArrayMultidimensional("output");
		for (int i = 0; i < expected.length; i++) {
			Assert.assertArrayEquals(expected[i], result[i]);
		}
	}

	@Test
	public void inputIntArrayIntScalarOutputLongArray() {
		long[] result = model.in("input1", new int[] { 1, 2 }).in("input2", 3).out("output").run()
				.getLongArray("output");
		Assert.assertArrayEquals(new long[] { 4L, 5L }, result);
	}

	@Test
	public void inputIntArrayIntScalarOutputFloatArray() {
		float[] result = model.in("input1", new int[] { 1, 2 }).in("input2", 3).out("output").run()
				.getFloatArray("output");
		Assert.assertArrayEquals(new float[] { 4.0f, 5.0f }, result, 0.0f);
	}

	@Test
	public void inputIntArrayIntScalarOutputDoubleArray() {
		double[] result = model.in("input1", new int[] { 1, 2 }).in("input2", 3).out("output").run()
				.getDoubleArray("output");
		Assert.assertArrayEquals(new double[] { 4.0d, 5.0d }, result, 0.0d);
	}

	@Test
	public void inputIntArrayIntScalarOutputStringArray() {
		String[] result = model.in("input1", new int[] { 1, 2 }).in("input2", 3).out("output").run()
				.getStringArray("output");
		Assert.assertArrayEquals(new String[] { "4", "5" }, result);
	}

	@Test
	public void inputMultiIntArrayIntScalarOutputMultiLongArray() {
		int[][] input1 = new int[][] { { 1, 2 }, { 3, 4 } };
		int input2 = 5;
		long[][] expected = new long[][] { { 6L, 7L }, { 8L, 9L } };
		long[][] result = (long[][]) model.in("input1", input1).in("input2", input2).out("output").run()
				.getLongArrayMultidimensional("output");
		for (int i = 0; i < expected.length; i++) {
			Assert.assertArrayEquals(expected[i], result[i]);
		}
	}

	@Test
	public void inputMultiIntArrayIntScalarOutputMultiFloatArray() {
		int[][] input1 = new int[][] { { 1, 2 }, { 3, 4 } };
		int input2 = 5;
		float[][] expected = new float[][] { { 6.0f, 7.0f }, { 8.0f, 9.0f } };
		float[][] result = (float[][]) model.in("input1", input1).in("input2", input2).out("output").run()
				.getFloatArrayMultidimensional("output");
		for (int i = 0; i < expected.length; i++) {
			Assert.assertArrayEquals(expected[i], result[i], 0.0f);
		}
	}

	@Test
	public void inputMultiIntArrayIntScalarOutputMultiDoubleArray() {
		int[][] input1 = new int[][] { { 1, 2 }, { 3, 4 } };
		int input2 = 5;
		double[][] expected = new double[][] { { 6.0d, 7.0d }, { 8.0d, 9.0d } };
		double[][] result = (double[][]) model.in("input1", input1).in("input2", input2).out("output").run()
				.getDoubleArrayMultidimensional("output");
		for (int i = 0; i < expected.length; i++) {
			Assert.assertArrayEquals(expected[i], result[i], 0.0d);
		}
	}

	@Test
	public void inputMultiIntArrayIntScalarOutputMultiStringArray() {
		int[][] input1 = new int[][] { { 1, 2 }, { 3, 4 } };
		int input2 = 5;
		String[][] expected = new String[][] { { "6", "7" }, { "8", "9" } };
		String[][] result = (String[][]) model.in("input1", input1).in("input2", input2).out("output").run()
				.getStringArrayMultidimensional("output");
		for (int i = 0; i < expected.length; i++) {
			Assert.assertArrayEquals(expected[i], result[i]);
		}
	}

	@Test
	public void inputIntArrayLongScalarOutputIntArray() {
		int[] result = model.in("input1", new int[] { 1, 2 }).in("input2", 3L).out("output").run()
				.getIntArray("output");
		Assert.assertArrayEquals(new int[] { 4, 5 }, result);
	}

	@Test
	public void inputIntArrayFloatScalarOutputIntArray() {
		int[] result = model.in("input1", new int[] { 1, 2 }).in("input2", 3.0f).out("output").run()
				.getIntArray("output");
		Assert.assertArrayEquals(new int[] { 4, 5 }, result);
	}

	@Test
	public void inputIntArrayDoubleScalarOutputIntArray() {
		int[] result = model.in("input1", new int[] { 1, 2 }).in("input2", 3.0d).out("output").run()
				.getIntArray("output");
		Assert.assertArrayEquals(new int[] { 4, 5 }, result);
	}

	@Test
	public void inputIntArrayStringScalarOutputIntArray() {
		int[] result = model.in("input1", new int[] { 1, 2 }).in("input2", "3").out("output").run()
				.getIntArray("output");
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
	public void inputIntArraysOutputInt() {
		int result = model.in("input1", new int[] { 1, 2 }).in("input2", new int[] { 3, 4 }).out("output").run()
				.getInt("output");
		Assert.assertTrue(4 == result);
	}

	@Test
	public void inputIntArraysOutputLong() {
		long result = model.in("input1", new int[] { 1, 2 }).in("input2", new int[] { 3, 4 }).out("output").run()
				.getLong("output");
		Assert.assertTrue(4L == result);
	}

	@Test
	public void inputIntArraysOutputFloat() {
		float result = model.in("input1", new int[] { 1, 2 }).in("input2", new int[] { 3, 4 }).out("output").run()
				.getFloat("output");
		Assert.assertTrue(4.0f == result);
	}

	@Test
	public void inputIntArraysOutputDouble() {
		double result = model.in("input1", new int[] { 1, 2 }).in("input2", new int[] { 3, 4 }).out("output").run()
				.getDouble("output");
		Assert.assertTrue(4.0d == result);
	}

	@Test
	public void inputIntArraysOutputString() {
		String result = model.in("input1", new int[] { 1, 2 }).in("input2", new int[] { 3, 4 }).out("output").run()
				.getString("output");
		Assert.assertTrue("4".equals(result));
	}

	@Test
	public void inputMultiIntArraysOutputInt() {
		int[][] input1 = new int[][] { { 1, 2 }, { 3, 4 } };
		int[][] input2 = new int[][] { { 1, 2 }, { 3, 4 } };
		int result = model.in("input1", input1).in("input2", input2).out("output").run().getInt("output");
		Assert.assertTrue(2 == result);
	}

	@Test
	public void inputMultiIntArraysOutputLong() {
		int[][] input1 = new int[][] { { 1, 2 }, { 3, 4 } };
		int[][] input2 = new int[][] { { 1, 2 }, { 3, 4 } };
		long result = model.in("input1", input1).in("input2", input2).out("output").run().getLong("output");
		Assert.assertTrue(2L == result);
	}

	@Test
	public void inputMultiIntArraysOutputFloat() {
		int[][] input1 = new int[][] { { 1, 2 }, { 3, 4 } };
		int[][] input2 = new int[][] { { 1, 2 }, { 3, 4 } };
		float result = model.in("input1", input1).in("input2", input2).out("output").run().getFloat("output");
		Assert.assertTrue(2.0f == result);
	}

	@Test
	public void inputMultiIntArraysOutputDouble() {
		int[][] input1 = new int[][] { { 1, 2 }, { 3, 4 } };
		int[][] input2 = new int[][] { { 1, 2 }, { 3, 4 } };
		double result = model.in("input1", input1).in("input2", input2).out("output").run().getDouble("output");
		Assert.assertTrue(2.0d == result);
	}

	@Test
	public void inputMultiIntArraysOutputString() {
		int[][] input1 = new int[][] { { 1, 2 }, { 3, 4 } };
		int[][] input2 = new int[][] { { 1, 2 }, { 3, 4 } };
		String result = model.in("input1", input1).in("input2", input2).out("output").run().getString("output");
		Assert.assertTrue("2".equals(result));
	}

	@Test
	public void inputBytesOutputByte() {
		byte result = model.in("input1", (byte) 1).in("input2", (byte) 2).out("output").run().getByte("output");
		Assert.assertTrue((byte) 3 == result);
	}

	@Test
	public void inputByteArraysOutputByteArray() {
		byte[] result = model.in("input1", new byte[] { (byte) 1, (byte) 2 })
				.in("input2", new byte[] { (byte) 3, (byte) 4 }).out("output").run().getByteArray("output");
		Assert.assertArrayEquals(new byte[] { (byte) 4, (byte) 6 }, result);
	}

	@Test
	public void inputMultiByteArraysOutputMultiByteArray() {
		byte[][] input1 = new byte[][] { { (byte) 1, (byte) 2 }, { (byte) 3, (byte) 4 } };
		byte[][] input2 = new byte[][] { { (byte) 1, (byte) 2 }, { (byte) 3, (byte) 4 } };
		byte[][] expected = new byte[][] { { (byte) 2, (byte) 4 }, { (byte) 6, (byte) 8 } };
		byte[][] result = (byte[][]) model.in("input1", input1).in("input2", input2).out("output").run()
				.getByteArrayMultidimensional("output");
		for (int i = 0; i < expected.length; i++) {
			Assert.assertArrayEquals(expected[i], result[i]);
		}
	}

	@Test
	public void inputByteArraysOutputByte() {
		byte result = model.in("input1", new byte[] { (byte) 1, (byte) 2 })
				.in("input2", new byte[] { (byte) 3, (byte) 4 }).out("output").run().getByte("output");
		Assert.assertEquals((byte) 4, result);
	}

	@Test
	public void inputMultiByteArraysOutputMultiByte() {
		byte[][] input1 = new byte[][] { { (byte) 1, (byte) 2 }, { (byte) 3, (byte) 4 } };
		byte[][] input2 = new byte[][] { { (byte) 1, (byte) 2 }, { (byte) 3, (byte) 4 } };
		byte expected = (byte) 2;
		byte result = model.in("input1", input1).in("input2", input2).out("output").run().getByte("output");
		Assert.assertTrue(expected == result);
	}

	// 1 + 0 = 1
	@Test
	public void inputBooleanTrueBooleanFalseOutputBooleanTrue() {
		boolean result = model.in("input1", true).in("input2", false).out("output").run().getBoolean("output");
		Assert.assertEquals(true, result);
	}

	// 0 + 0 = 0
	@Test
	public void inputBooleanFalseBooleanFalseOutputBooleanFalse() {
		boolean result = model.in("input1", false).in("input2", false).out("output").run().getBoolean("output");
		Assert.assertEquals(false, result);
	}

	// 1 + 0 = 1, 0 + 1 = 1
	@Test
	public void inputBooleanArraysOutputBooleanArray() {
		boolean[] result = model.in("input1", new boolean[] { true, false }).in("input2", new boolean[] { false, true })
				.out("output").run().getBooleanArray("output");
		BooleanLogicTest.assertArrayEquals(new boolean[] { true, true }, result);
	}

	// 1 + 0 = 1, 1 + 0 = 1, 0 + 1 = 1, 0 + 0 = 0
	@Test
	public void inputMultiBooleanArraysOutputMultiBooleanArray() {
		boolean[][] input1 = new boolean[][] { { true, true }, { false, false } };
		boolean[][] input2 = new boolean[][] { { false, false }, { true, false } };
		boolean[][] expected = new boolean[][] { { true, true }, { true, false } };
		boolean[][] result = (boolean[][]) model.in("input1", input1).in("input2", input2).out("output").run()
				.getBooleanArrayMultidimensional("output");
		BooleanLogicTest.assertArrayEquals(expected, result);
	}

	// 1 + 0 = 1
	@Test
	public void inputBooleanArraysOutputBoolean() {
		boolean result = model.in("input1", new boolean[] { true, false }).in("input2", new boolean[] { false, true })
				.out("output").run().getBoolean("output");
		Assert.assertEquals(true, result);
	}

	// 1 + 0 = 1
	@Test
	public void inputMultiBooleanArraysOutputMultiBoolean() {
		boolean[][] input1 = new boolean[][] { { true, true }, { false, false } };
		boolean[][] input2 = new boolean[][] { { false, false }, { true, false } };
		boolean expected = true;
		boolean result = model.in("input1", input1).in("input2", input2).out("output").run().getBoolean("output");
		Assert.assertEquals(expected, result);
	}

	@Test
	public void inputMultiIntegerObjectArraysOutputMultiIntArray() {
		Integer[][] input1 = new Integer[][] { { 1, 2 }, { 3, 4 } };
		Integer[][] input2 = new Integer[][] { { 1, 2 }, { 3, 4 } };
		int[][] expected = new int[][] { { 2, 4 }, { 6, 8 } };
		int[][] result = (int[][]) model.in("input1", input1).in("input2", input2).out("output").run()
				.getIntArrayMultidimensional("output");
		for (int i = 0; i < expected.length; i++) {
			Assert.assertArrayEquals(expected[i], result[i]);
		}
	}
}
