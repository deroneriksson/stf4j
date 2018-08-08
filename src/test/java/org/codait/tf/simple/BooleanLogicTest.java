package org.codait.tf.simple;

import java.io.IOException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.codait.tf.TFModel;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BooleanLogicTest {

	protected static Logger log = LogManager.getLogger(BooleanLogicTest.class);

	public static final String BOOLEAN_LOGIC_MODEL_DIR = "./simple/boolean_logic";

	private TFModel model = null;

	@Before
	public void init() throws IOException {
		model = new TFModel(BOOLEAN_LOGIC_MODEL_DIR).sig("serving_default");
	}

	@After
	public void after() {
	}

	@Test
	public void trueAndFalse() {
		boolean result = model.in("input1", true).in("input2", false).out("and").run().getBoolean("and");
		Assert.assertTrue(false == result);
	}

	@Test
	public void trueOrFalse() {
		boolean result = model.in("input1", true).in("input2", false).out("or").run().getBoolean("or");
		Assert.assertTrue(true == result);
	}

	@Test
	public void notTrueAndFalse() {
		boolean result = model.in("input1", true).in("input2", false).out("not_and").run().getBoolean("not_and");
		Assert.assertTrue(true == result);
	}

	@Test
	public void notTrueOrFalse() {
		boolean result = model.in("input1", true).in("input2", false).out("not_or").run().getBoolean("not_or");
		Assert.assertTrue(false == result);
	}

	@Test
	public void trueXorFalse() {
		boolean result = model.in("input1", true).in("input2", false).out("xor").run().getBoolean("xor");
		Assert.assertTrue(true == result);
	}

	@Test
	public void booleanArrayAndBooleanArray() {
		boolean[] result = model.in("input1", new boolean[] { true, false }).in("input2", new boolean[] { true, true })
				.out("and").run().getBooleanArray("and");
		Assert.assertTrue(result[0] == true);
		Assert.assertTrue(result[1] == false);
	}

	@Test
	public void booleanArrayOrBooleanArray() {
		boolean[] result = model.in("input1", new boolean[] { true, false }).in("input2", new boolean[] { true, true })
				.out("or").run().getBooleanArray("or");
		Assert.assertTrue(result[0] == true);
		Assert.assertTrue(result[1] == true);
	}

	@Test
	public void booleanArrayAndBooleanArrayOutputByteArray() {
		byte[] result = model.in("input1", new boolean[] { true, false }).in("input2", new boolean[] { true, true })
				.out("and").run().getByteArray("and");
		Assert.assertArrayEquals(new byte[] { 1, 0 }, result);
	}

	@Test
	public void multiBooleanArrayAndMultiBooleanArrayOutputMultiBooleanArray() {
		boolean[][] input1 = new boolean[][] { { true, true }, { false, false } };
		boolean[][] input2 = new boolean[][] { { true, false }, { true, false } };
		boolean[][] expected = new boolean[][] { { true, false }, { false, false } };
		boolean[][] result = (boolean[][]) model.in("input1", input1).in("input2", input2).out("and").run()
				.getBooleanArrayMultidimensional("and");
		assertArrayEquals(expected, result);
	}

	@Test
	public void multiBooleanArrayOrMultiBooleanArrayOutputMultiBooleanArray() {
		boolean[][] input1 = new boolean[][] { { true, true }, { false, false } };
		boolean[][] input2 = new boolean[][] { { true, false }, { true, false } };
		boolean[][] expected = new boolean[][] { { true, true }, { true, false } };
		boolean[][] result = (boolean[][]) model.in("input1", input1).in("input2", input2).out("or").run()
				.getBooleanArrayMultidimensional("or");
		assertArrayEquals(expected, result);
	}

	@Test
	public void multiBooleanArrayXorMultiBooleanArrayOutputMultiBooleanArray() {
		boolean[][] input1 = new boolean[][] { { true, true }, { false, false } };
		boolean[][] input2 = new boolean[][] { { true, false }, { true, false } };
		boolean[][] expected = new boolean[][] { { false, true }, { true, false } };
		boolean[][] result = (boolean[][]) model.in("input1", input1).in("input2", input2).out("xor").run()
				.getBooleanArrayMultidimensional("xor");
		assertArrayEquals(expected, result);
	}

	@Test
	public void notMultiBooleanArrayAndMultiBooleanArrayOutputMultiBooleanArray() {
		boolean[][] input1 = new boolean[][] { { true, true }, { false, false } };
		boolean[][] input2 = new boolean[][] { { true, false }, { true, false } };
		boolean[][] expected = new boolean[][] { { false, true }, { true, true } };
		boolean[][] result = (boolean[][]) model.in("input1", input1).in("input2", input2).out("not_and").run()
				.getBooleanArrayMultidimensional("not_and");
		assertArrayEquals(expected, result);
	}

	@Test
	public void notMultiBooleanArrayOrMultiBooleanArrayOutputMultiBooleanArray() {
		boolean[][] input1 = new boolean[][] { { true, true }, { false, false } };
		boolean[][] input2 = new boolean[][] { { true, false }, { true, false } };
		boolean[][] expected = new boolean[][] { { false, false }, { false, true } };
		boolean[][] result = (boolean[][]) model.in("input1", input1).in("input2", input2).out("not_or").run()
				.getBooleanArrayMultidimensional("not_or");
		assertArrayEquals(expected, result);
	}

	@Test
	public void multiBooleanArrayAndMultiBooleanArrayOutputMultiByteArray() {
		boolean[][] input1 = new boolean[][] { { true, true }, { false, false } };
		boolean[][] input2 = new boolean[][] { { true, false }, { true, false } };
		byte[][] expected = new byte[][] { { 1, 0 }, { 0, 0 } };
		byte[][] result = (byte[][]) model.in("input1", input1).in("input2", input2).out("and").run()
				.getByteArrayMultidimensional("and");
		Assert.assertTrue(expected.length == result.length);
		for (int i = 0; i < expected.length; i++) {
			Assert.assertArrayEquals(expected[i], result[i]);
		}
	}

	@Test
	public void multiBooleanArrayOrMultiBooleanArrayOutputMultiByteArray() {
		boolean[][] input1 = new boolean[][] { { true, true }, { false, false } };
		boolean[][] input2 = new boolean[][] { { true, false }, { true, false } };
		byte[][] expected = new byte[][] { { 1, 1 }, { 1, 0 } };
		byte[][] result = (byte[][]) model.in("input1", input1).in("input2", input2).out("or").run()
				.getByteArrayMultidimensional("or");
		Assert.assertTrue(expected.length == result.length);
		for (int i = 0; i < expected.length; i++) {
			Assert.assertArrayEquals(expected[i], result[i]);
		}
	}

	protected void assertArrayEquals(boolean[][] expecteds, boolean[][] actuals) {
		Assert.assertTrue(expecteds.length == actuals.length);
		for (int i = 0; i < expecteds.length; i++) {
			assertArrayEquals(expecteds[i], actuals[i]);
		}
	}

	protected void assertArrayEquals(boolean[] expecteds, boolean[] actuals) {
		Assert.assertTrue(expecteds.length == actuals.length);
		for (int i = 0; i < expecteds.length; i++) {
			Assert.assertTrue(expecteds[i] == actuals[i]);
		}
	}

	@Test
	public void trueAndFalseOutputByte() {
		byte result = model.in("input1", true).in("input2", false).out("and").run().getByte("and");
		Assert.assertTrue(0 == result);
	}

	@Test
	public void trueOrFalseOutputByte() {
		byte result = model.in("input1", true).in("input2", false).out("or").run().getByte("or");
		Assert.assertTrue(1 == result);
	}

	@Test
	public void trueByteAndFalseByteOutputByte() {
		byte result = model.in("input1", (byte) 1).in("input2", (byte) 0).out("and").run().getByte("and");
		Assert.assertTrue(0 == result);
	}

	@Test
	public void trueByteOrFalseByteOutputByte() {
		byte result = model.in("input1", (byte) 1).in("input2", (byte) 0).out("or").run().getByte("or");
		Assert.assertTrue(1 == result);
	}

	@Test
	public void byteArrayAndByteArrayOutputByteArray() {
		byte[] result = model.in("input1", new byte[] { 1, 0 }).in("input2", new byte[] { 1, 1 }).out("and").run()
				.getByteArray("and");
		Assert.assertArrayEquals(new byte[] { 1, 0 }, result);
	}

	@Test
	public void byteArrayOrByteArrayOutputByteArray() {
		byte[] result = model.in("input1", new byte[] { 1, 0 }).in("input2", new byte[] { 1, 1 }).out("or").run()
				.getByteArray("or");
		Assert.assertArrayEquals(new byte[] { 1, 1 }, result);
	}

	@Test
	public void multiByteArrayAndMultiByteArrayOutputMultiByteArray() {
		byte[][] input1 = new byte[][] { { 1, 1 }, { 0, 0 } };
		byte[][] input2 = new byte[][] { { 1, 0 }, { 1, 0 } };
		byte[][] expected = new byte[][] { { 1, 0 }, { 0, 0 } };
		byte[][] result = (byte[][]) model.in("input1", input1).in("input2", input2).out("and").run()
				.getByteArrayMultidimensional("and");
		Assert.assertTrue(expected.length == result.length);
		for (int i = 0; i < expected.length; i++) {
			Assert.assertArrayEquals(expected[i], result[i]);
		}
	}

	@Test
	public void multiByteArrayOrMultiByteArrayOutputMultiByteArray() {
		byte[][] input1 = new byte[][] { { 1, 1 }, { 0, 0 } };
		byte[][] input2 = new byte[][] { { 1, 0 }, { 1, 0 } };
		byte[][] expected = new byte[][] { { 1, 1 }, { 1, 0 } };
		byte[][] result = (byte[][]) model.in("input1", input1).in("input2", input2).out("or").run()
				.getByteArrayMultidimensional("or");
		Assert.assertTrue(expected.length == result.length);
		for (int i = 0; i < expected.length; i++) {
			Assert.assertArrayEquals(expected[i], result[i]);
		}
	}

	@Test
	public void intTrueAndIntFalseOutputInt() {
		int result = model.in("input1", 1).in("input2", 0).out("and").run().getInt("and");
		Assert.assertTrue(0 == result);
	}

	@Test
	public void intTrueOrIntFalseOutputInt() {
		int result = model.in("input1", 1).in("input2", 0).out("or").run().getInt("or");
		Assert.assertTrue(1 == result);
	}

	@Test
	public void intArrayAndIntArrayOutputIntArray() {
		int[] result = model.in("input1", new int[] { 1, 0 }).in("input2", new int[] { 1, 1 }).out("and").run()
				.getIntArray("and");
		Assert.assertArrayEquals(new int[] { 1, 0 }, result);
	}

	@Test
	public void intArrayOrIntArrayOutputIntArray() {
		int[] result = model.in("input1", new int[] { 1, 0 }).in("input2", new int[] { 1, 1 }).out("or").run()
				.getIntArray("or");
		Assert.assertArrayEquals(new int[] { 1, 1 }, result);
	}

	@Test
	public void multiIntArrayAndMultiIntArrayOutputMultiIntArray() {
		int[][] input1 = new int[][] { { 1, 1 }, { 0, 0 } };
		int[][] input2 = new int[][] { { 1, 0 }, { 1, 0 } };
		int[][] expected = new int[][] { { 1, 0 }, { 0, 0 } };
		int[][] result = (int[][]) model.in("input1", input1).in("input2", input2).out("and").run()
				.getIntArrayMultidimensional("and");
		Assert.assertTrue(expected.length == result.length);
		for (int i = 0; i < expected.length; i++) {
			Assert.assertArrayEquals(expected[i], result[i]);
		}
	}

	@Test
	public void multiIntArrayOrMultiIntArrayOutputMultiIntArray() {
		int[][] input1 = new int[][] { { 1, 1 }, { 0, 0 } };
		int[][] input2 = new int[][] { { 1, 0 }, { 1, 0 } };
		int[][] expected = new int[][] { { 1, 1 }, { 1, 0 } };
		int[][] result = (int[][]) model.in("input1", input1).in("input2", input2).out("or").run()
				.getIntArrayMultidimensional("or");
		Assert.assertTrue(expected.length == result.length);
		for (int i = 0; i < expected.length; i++) {
			Assert.assertArrayEquals(expected[i], result[i]);
		}
	}

	@Test
	public void longTrueAndLongFalseOutputLong() {
		long result = model.in("input1", 1L).in("input2", 0L).out("and").run().getLong("and");
		Assert.assertTrue(0 == result);
	}

	@Test
	public void longTrueOrLongFalseOutputLong() {
		long result = model.in("input1", 1L).in("input2", 0L).out("or").run().getLong("or");
		Assert.assertTrue(1 == result);
	}

	@Test
	public void longArrayAndLongArrayOutputLongArray() {
		long[] result = model.in("input1", new long[] { 1L, 0L }).in("input2", new long[] { 1L, 1L }).out("and").run()
				.getLongArray("and");
		Assert.assertArrayEquals(new long[] { 1L, 0L }, result);
	}

	@Test
	public void longArrayOrLongArrayOutputLongArray() {
		long[] result = model.in("input1", new long[] { 1L, 0L }).in("input2", new long[] { 1L, 1L }).out("or").run()
				.getLongArray("or");
		Assert.assertArrayEquals(new long[] { 1L, 1L }, result);
	}

	@Test
	public void multiLongArrayAndMultiLongArrayOutputMultiLongArray() {
		long[][] input1 = new long[][] { { 1L, 1L }, { 0L, 0L } };
		long[][] input2 = new long[][] { { 1L, 0L }, { 1L, 0L } };
		long[][] expected = new long[][] { { 1L, 0L }, { 0L, 0L } };
		long[][] result = (long[][]) model.in("input1", input1).in("input2", input2).out("and").run()
				.getLongArrayMultidimensional("and");
		Assert.assertTrue(expected.length == result.length);
		for (int i = 0; i < expected.length; i++) {
			Assert.assertArrayEquals(expected[i], result[i]);
		}
	}

	@Test
	public void multiLongArrayOrMultiLongArrayOutputMultiLongArray() {
		long[][] input1 = new long[][] { { 1L, 1L }, { 0L, 0L } };
		long[][] input2 = new long[][] { { 1L, 0L }, { 1L, 0L } };
		long[][] expected = new long[][] { { 1L, 1L }, { 1L, 0L } };
		long[][] result = (long[][]) model.in("input1", input1).in("input2", input2).out("or").run()
				.getLongArrayMultidimensional("or");
		Assert.assertTrue(expected.length == result.length);
		for (int i = 0; i < expected.length; i++) {
			Assert.assertArrayEquals(expected[i], result[i]);
		}
	}

	@Test
	public void floatTrueAndFloatFalseOutputFloat() {
		float result = model.in("input1", 1.0f).in("input2", 0.0f).out("and").run().getFloat("and");
		Assert.assertTrue(0 == result);
	}

	@Test
	public void floatTrueOrFloatFalseOutputFloat() {
		float result = model.in("input1", 1.0f).in("input2", 0.0f).out("or").run().getFloat("or");
		Assert.assertTrue(1 == result);
	}

	@Test
	public void floatArrayAndFloatArrayOutputFloatArray() {
		float[] result = model.in("input1", new float[] { 1.0f, 0.0f }).in("input2", new float[] { 1.0f, 1.0f })
				.out("and").run().getFloatArray("and");
		Assert.assertArrayEquals(new float[] { 1.0f, 0.0f }, result, 0.0f);
	}

	@Test
	public void floatArrayOrFloatArrayOutputFloatArray() {
		float[] result = model.in("input1", new float[] { 1.0f, 0.0f }).in("input2", new float[] { 1.0f, 1.0f })
				.out("or").run().getFloatArray("or");
		Assert.assertArrayEquals(new float[] { 1.0f, 1.0f }, result, 0.0f);
	}

	@Test
	public void multiFloatArrayAndMultiFloatArrayOutputMultiFloatArray() {
		float[][] input1 = new float[][] { { 1.0f, 1.0f }, { 0.0f, 0.0f } };
		float[][] input2 = new float[][] { { 1.0f, 0.0f }, { 1.0f, 0.0f } };
		float[][] expected = new float[][] { { 1.0f, 0.0f }, { 0.0f, 0.0f } };
		float[][] result = (float[][]) model.in("input1", input1).in("input2", input2).out("and").run()
				.getFloatArrayMultidimensional("and");
		Assert.assertTrue(expected.length == result.length);
		for (int i = 0; i < expected.length; i++) {
			Assert.assertArrayEquals(expected[i], result[i], 0.0f);
		}
	}

	@Test
	public void multiFloatArrayOrMultiFloatArrayOutputMultiFloatArray() {
		float[][] input1 = new float[][] { { 1.0f, 1.0f }, { 0.0f, 0.0f } };
		float[][] input2 = new float[][] { { 1.0f, 0.0f }, { 1.0f, 0.0f } };
		float[][] expected = new float[][] { { 1.0f, 1.0f }, { 1.0f, 0.0f } };
		float[][] result = (float[][]) model.in("input1", input1).in("input2", input2).out("or").run()
				.getFloatArrayMultidimensional("or");
		Assert.assertTrue(expected.length == result.length);
		for (int i = 0; i < expected.length; i++) {
			Assert.assertArrayEquals(expected[i], result[i], 0.0f);
		}
	}

}
