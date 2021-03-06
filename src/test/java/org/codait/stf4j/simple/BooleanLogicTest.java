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

package org.codait.stf4j.simple;

import java.io.IOException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.codait.stf4j.TFModel;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BooleanLogicTest {

	protected static Logger log = LogManager.getLogger(BooleanLogicTest.class);

	public static final String BOOLEAN_LOGIC_MODEL_DIR = "../stf4j-test-models/simple_saved_models/boolean_logic";

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

	public static void assertArrayEquals(boolean[][] expecteds, boolean[][] actuals) {
		Assert.assertTrue(expecteds.length == actuals.length);
		for (int i = 0; i < expecteds.length; i++) {
			assertArrayEquals(expecteds[i], actuals[i]);
		}
	}

	public static void assertArrayEquals(boolean[] expecteds, boolean[] actuals) {
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

	@Test
	public void doubleTrueAndDoubleFalseOutputDouble() {
		double result = model.in("input1", 1.0d).in("input2", 0.0d).out("and").run().getDouble("and");
		Assert.assertTrue(0 == result);
	}

	@Test
	public void doubleTrueOrDoubleFalseOutputDouble() {
		double result = model.in("input1", 1.0d).in("input2", 0.0d).out("or").run().getDouble("or");
		Assert.assertTrue(1 == result);
	}

	@Test
	public void doubleArrayAndDoubleArrayOutputDoubleArray() {
		double[] result = model.in("input1", new double[] { 1.0d, 0.0d }).in("input2", new double[] { 1.0d, 1.0d })
				.out("and").run().getDoubleArray("and");
		Assert.assertArrayEquals(new double[] { 1.0d, 0.0d }, result, 0.0d);
	}

	@Test
	public void doubleArrayOrDoubleArrayOutputDoubleArray() {
		double[] result = model.in("input1", new double[] { 1.0d, 0.0d }).in("input2", new double[] { 1.0d, 1.0d })
				.out("or").run().getDoubleArray("or");
		Assert.assertArrayEquals(new double[] { 1.0d, 1.0d }, result, 0.0d);
	}

	@Test
	public void multiDoubleArrayAndMultiDoubleArrayOutputMultiDoubleArray() {
		double[][] input1 = new double[][] { { 1.0d, 1.0d }, { 0.0d, 0.0d } };
		double[][] input2 = new double[][] { { 1.0d, 0.0d }, { 1.0d, 0.0d } };
		double[][] expected = new double[][] { { 1.0d, 0.0d }, { 0.0d, 0.0d } };
		double[][] result = (double[][]) model.in("input1", input1).in("input2", input2).out("and").run()
				.getDoubleArrayMultidimensional("and");
		Assert.assertTrue(expected.length == result.length);
		for (int i = 0; i < expected.length; i++) {
			Assert.assertArrayEquals(expected[i], result[i], 0.0d);
		}
	}

	@Test
	public void multiDoubleArrayOrMultiDoubleArrayOutputMultiDoubleArray() {
		double[][] input1 = new double[][] { { 1.0d, 1.0d }, { 0.0d, 0.0d } };
		double[][] input2 = new double[][] { { 1.0d, 0.0d }, { 1.0d, 0.0d } };
		double[][] expected = new double[][] { { 1.0d, 1.0d }, { 1.0d, 0.0d } };
		double[][] result = (double[][]) model.in("input1", input1).in("input2", input2).out("or").run()
				.getDoubleArrayMultidimensional("or");
		Assert.assertTrue(expected.length == result.length);
		for (int i = 0; i < expected.length; i++) {
			Assert.assertArrayEquals(expected[i], result[i], 0.0d);
		}
	}

	@Test
	public void stringTrueAndStringFalseOutputString() {
		String result = model.in("input1", "true").in("input2", "false").out("and").run().getString("and");
		Assert.assertTrue("false".equals(result));
	}

	@Test
	public void stringTrueOrStringFalseOutputString() {
		String result = model.in("input1", "true").in("input2", "false").out("or").run().getString("or");
		Assert.assertTrue("true".equals(result));
	}

	@Test
	public void stringArrayAndStringArrayOutputStringArray() {
		String[] result = model.in("input1", new String[] { "true", "false" })
				.in("input2", new String[] { "true", "true" }).out("and").run().getStringArray("and");
		Assert.assertArrayEquals(new String[] { "true", "false" }, result);
	}

	@Test
	public void stringArrayOrStringArrayOutputStringArray() {
		String[] result = model.in("input1", new String[] { "true", "false" })
				.in("input2", new String[] { "true", "true" }).out("or").run().getStringArray("or");
		Assert.assertArrayEquals(new String[] { "true", "true" }, result);
	}

	@Test
	public void multiStringArrayAndMultiStringArrayOutputMultiStringArray() {
		String[][] input1 = new String[][] { { "true", "true" }, { "false", "false" } };
		String[][] input2 = new String[][] { { "true", "false" }, { "true", "false" } };
		String[][] expected = new String[][] { { "true", "false" }, { "false", "false" } };
		String[][] result = (String[][]) model.in("input1", input1).in("input2", input2).out("and").run()
				.getStringArrayMultidimensional("and");
		Assert.assertTrue(expected.length == result.length);
		for (int i = 0; i < expected.length; i++) {
			Assert.assertArrayEquals(expected[i], result[i]);
		}
	}

	@Test
	public void multiStringArrayOrMultiStringArrayOutputMultiStringArray() {
		String[][] input1 = new String[][] { { "true", "true" }, { "false", "false" } };
		String[][] input2 = new String[][] { { "true", "false" }, { "true", "false" } };
		String[][] expected = new String[][] { { "true", "true" }, { "true", "false" } };
		String[][] result = (String[][]) model.in("input1", input1).in("input2", input2).out("or").run()
				.getStringArrayMultidimensional("or");
		Assert.assertTrue(expected.length == result.length);
		for (int i = 0; i < expected.length; i++) {
			Assert.assertArrayEquals(expected[i], result[i]);
		}
	}

	@Test
	public void booleanArrayAndBooleanArrayOutputBoolean() {
		boolean result = model.in("input1", new boolean[] { true, false }).in("input2", new boolean[] { true, true })
				.out("and").run().getBoolean("and");
		Assert.assertEquals(true, result);
	}

	@Test
	public void booleanArrayOrBooleanArrayOutputBoolean() {
		boolean result = model.in("input1", new boolean[] { true, false }).in("input2", new boolean[] { true, true })
				.out("or").run().getBoolean("or");
		Assert.assertEquals(true, result);
	}

	@Test
	public void byteArrayAndByteArrayOutputByte() {
		byte result = model.in("input1", new byte[] { 1, 0 }).in("input2", new byte[] { 1, 1 }).out("and").run()
				.getByte("and");
		Assert.assertEquals((byte) 1, result);
	}

	@Test
	public void byteArrayOrByteArrayOutputByte() {
		byte result = model.in("input1", new byte[] { 1, 0 }).in("input2", new byte[] { 1, 1 }).out("or").run()
				.getByte("or");
		Assert.assertEquals((byte) 1, result);
	}

	@Test
	public void intArrayAndIntArrayOutputInt() {
		int result = model.in("input1", new int[] { 1, 0 }).in("input2", new int[] { 1, 1 }).out("and").run()
				.getInt("and");
		Assert.assertEquals(1, result);
	}

	@Test
	public void intArrayOrIntArrayOutputInt() {
		int result = model.in("input1", new int[] { 1, 0 }).in("input2", new int[] { 1, 1 }).out("or").run()
				.getInt("or");
		Assert.assertEquals(1, result);
	}

	@Test
	public void longArrayAndLongArrayOutputLong() {
		long result = model.in("input1", new long[] { 1L, 0L }).in("input2", new long[] { 1L, 1L }).out("and").run()
				.getLong("and");
		Assert.assertEquals(1L, result);
	}

	@Test
	public void longArrayOrLongArrayOutputLong() {
		long result = model.in("input1", new long[] { 1L, 0L }).in("input2", new long[] { 1L, 1L }).out("or").run()
				.getLong("or");
		Assert.assertEquals(1L, result);
	}

	@Test
	public void floatArrayAndFloatArrayOutputFloat() {
		float result = model.in("input1", new float[] { 1.0f, 0.0f }).in("input2", new float[] { 1.0f, 1.0f })
				.out("and").run().getFloat("and");
		Assert.assertEquals(1.0f, result, 0.0f);
	}

	@Test
	public void floatArrayOrFloatArrayOutputFloat() {
		float result = model.in("input1", new float[] { 1.0f, 0.0f }).in("input2", new float[] { 1.0f, 1.0f }).out("or")
				.run().getFloat("or");
		Assert.assertEquals(1.0f, result, 0.0f);
	}

	@Test
	public void doubleArrayAndDoubleArrayOutputDouble() {
		double result = model.in("input1", new double[] { 1.0d, 0.0d }).in("input2", new double[] { 1.0d, 1.0d })
				.out("and").run().getDouble("and");
		Assert.assertEquals(1.0d, result, 0.0d);
	}

	@Test
	public void doubleArrayOrDoubleArrayOutputDouble() {
		double result = model.in("input1", new double[] { 1.0d, 0.0d }).in("input2", new double[] { 1.0d, 1.0d })
				.out("or").run().getDouble("or");
		Assert.assertEquals(1.0d, result, 0.0d);
	}

	@Test
	public void stringArrayAndStringArrayOutputString() {
		String result = model.in("input1", new String[] { "true", "false" })
				.in("input2", new String[] { "true", "true" }).out("and").run().getString("and");
		Assert.assertTrue("true".equals(result));
	}

	@Test
	public void stringArrayOrStringArrayOutputString() {
		String result = model.in("input1", new String[] { "true", "false" })
				.in("input2", new String[] { "true", "true" }).out("or").run().getString("or");
		Assert.assertTrue("true".equals(result));
	}

	@Test
	public void multiBooleanObjectArrayAndMultiBooleanObjectArrayOutputMultiBooleanArray() {
		Boolean[][] input1 = new Boolean[][] { { true, true }, { false, false } };
		Boolean[][] input2 = new Boolean[][] { { true, false }, { true, false } };
		boolean[][] expected = new boolean[][] { { true, false }, { false, false } };
		boolean[][] result = (boolean[][]) model.in("input1", input1).in("input2", input2).out("and").run()
				.getBooleanArrayMultidimensional("and");
		assertArrayEquals(expected, result);
	}
}
