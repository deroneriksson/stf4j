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

package org.codait.stf4j.higgs;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.codait.stf4j.TFBaseTest;
import org.codait.stf4j.TFModel;
import org.junit.After;
import org.junit.Assert;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;

public class HiggsBoostedTreesTest extends TFBaseTest {

	protected static Logger log = LogManager.getLogger(HiggsBoostedTreesTest.class);

	public static final String HIGGS_SAVED_MODEL_DIR = "../stf4j-test-models/higgs_boosted_trees_saved_model/";

	private TFModel model = null;

	static String[] s = new String[] {
			"0.869293,-0.635082,0.225690,0.327470,-0.689993,0.754202,-0.248573,-1.092064,0.0,1.374992,-0.653674,0.930349,1.107436,1.138904,-1.578198,-1.046985,0.0,0.657930,-0.010455,-0.045767,3.101961,1.353760,0.979563,0.978076,0.920005,0.721657,0.988751,0.876678",
			"1.595839,-0.607811,0.007075,1.818450,-0.111906,0.847550,-0.566437,1.581239,2.173076,0.755421,0.643110,1.426367,0.0,0.921661,-1.190432,-1.615589,0.0,0.651114,-0.654227,-1.274345,3.101961,0.823761,0.938191,0.971758,0.789176,0.430553,0.961357,0.957818" };
	static byte[][] b = new byte[2][];
	static Byte[][] bObj = new Byte[2][];
	static {
		b[0] = s[0].getBytes(StandardCharsets.UTF_8);
		b[1] = s[1].getBytes(StandardCharsets.UTF_8);
		bObj[0] = new Byte[b[0].length];
		bObj[1] = new Byte[b[1].length];
		for (int i = 0; i < b[0].length; i++) {
			bObj[0][i] = b[0][i];
		}
		for (int i = 0; i < b[1].length; i++) {
			bObj[1][i] = b[1][i];
		}
	}

	@Before
	public void init() throws IOException {
		boolean savedModelExists = new File(HIGGS_SAVED_MODEL_DIR).exists();
		Assume.assumeTrue("SavedModel directory (" + HIGGS_SAVED_MODEL_DIR
				+ ") can't be found, so skipping Higgs Boosted Trees tests", savedModelExists);

		model = new TFModel(HIGGS_SAVED_MODEL_DIR).sig("predict");
	}

	@After
	public void after() {
	}

	@Test
	public void higgsInputStrings() {
		log.debug(
				"Higgs Boosted Trees - input data as csv strings, output class_ids, classes, logistic, logits, and probabilities");

		model.in("inputs", s).out("class_ids", "classes", "logistic", "logits", "probabilities").run();
	}

	@Test
	public void higgsInputStringBytes() {
		log.debug(
				"Higgs Boosted Trees - input data as csv string bytes, output class_ids, classes, logistic, logits, and probabilities");

		model.in("inputs", b).out("class_ids", "classes", "logistic", "logits", "probabilities").run();
	}

	@Test
	public void higgsInputStringByteObjects() {
		log.debug(
				"Higgs Boosted Trees - input data as csv string byte objects, output class_ids, classes, logistic, logits, and probabilities");

		model.in("inputs", bObj).out("class_ids", "classes", "logistic", "logits", "probabilities").run();
	}

	@Test
	public void higgsInputStringsOutputClassIdsLongs() {
		log.debug("Higgs Boosted Trees - input data as csv strings, output class_ids as long array");
		long[] expected = new long[] { 1, 0 };
		long[] predictions = model.in("inputs", s).out("class_ids").run().getLongArray("class_ids");
		displayDebug(expected, predictions);
		Assert.assertArrayEquals(expected, predictions);
	}

	@Test
	public void higgsInputStringsOutputClassIdsInts() {
		log.debug("Higgs Boosted Trees - input data as csv strings, output class_ids as int array");
		int[] expected = new int[] { 1, 0 };
		int[] predictions = model.in("inputs", s).out("class_ids").run().getIntArray("class_ids");
		displayDebug(expected, predictions);
		Assert.assertArrayEquals(expected, predictions);
	}

	@Test
	public void higgsInputStringsOutputClassIdsFloats() {
		log.debug("Higgs Boosted Trees - input data as csv strings, output class_ids as float array");
		float[] expected = new float[] { 1f, 0f };
		float[] predictions = model.in("inputs", s).out("class_ids").run().getFloatArray("class_ids");
		displayDebug(expected, predictions);
		Assert.assertArrayEquals(expected, predictions, 0f);
	}

	@Test
	public void higgsInputStringsOutputClassIdsDoubles() {
		log.debug("Higgs Boosted Trees - input data as csv strings, output class_ids as double array");
		double[] expected = new double[] { 1, 0 };
		double[] predictions = model.in("inputs", s).out("class_ids").run().getDoubleArray("class_ids");
		displayDebug(expected, predictions);
		Assert.assertArrayEquals(expected, predictions, 0);
	}

	@Test
	public void higgsInputStringsOutputClassIdsDoublesMultidimensional() {
		log.debug(
				"Higgs Boosted Trees - input data as csv strings, output class_ids as double (multidimensional) array");
		double[] expected = new double[] { 1, 0 };
		double[][] predictions = (double[][]) model.in("inputs", s).out("class_ids").run()
				.getDoubleArrayMultidimensional("class_ids");
		Assert.assertEquals("Predictions array length not equal to expected array length.", expected.length,
				predictions.length);
		for (int i = 0; i < predictions.length; i++) {
			double pred = predictions[i][0];
			double exp = expected[i];
			displayDebug(exp, pred);
			Assert.assertEquals(exp, pred, 0);
		}
	}

	@Test
	public void higgsInputStringsOutputClassIdsFloatsMultidimensional() {
		log.debug(
				"Higgs Boosted Trees - input data as csv strings, output class_ids as float (multidimensional) array");
		float[] expected = new float[] { 1f, 0f };
		float[][] predictions = (float[][]) model.in("inputs", s).out("class_ids").run()
				.getFloatArrayMultidimensional("class_ids");
		Assert.assertEquals("Predictions array length not equal to expected array length.", expected.length,
				predictions.length);
		for (int i = 0; i < predictions.length; i++) {
			double pred = predictions[i][0];
			double exp = expected[i];
			displayDebug(exp, pred);
			Assert.assertEquals(exp, pred, 0f);
		}
	}

	@Test
	public void higgsInputStringsOutputClassIdsIntsMultidimensional() {
		log.debug("Higgs Boosted Trees - input data as csv strings, output class_ids as int (multidimensional) array");
		int[] expected = new int[] { 1, 0 };
		int[][] predictions = (int[][]) model.in("inputs", s).out("class_ids").run()
				.getIntArrayMultidimensional("class_ids");
		Assert.assertEquals("Predictions array length not equal to expected array length.", expected.length,
				predictions.length);
		for (int i = 0; i < predictions.length; i++) {
			int pred = predictions[i][0];
			int exp = expected[i];
			displayDebug(exp, pred);
			Assert.assertEquals(exp, pred, 0);
		}
	}

	@Test
	public void higgsInputStringsOutputClassIdsLongsMultidimensional() {
		log.debug("Higgs Boosted Trees - input data as csv strings, output class_ids as long (multidimensional) array");
		long[] expected = new long[] { 1, 0 };
		long[][] predictions = (long[][]) model.in("inputs", s).out("class_ids").run()
				.getLongArrayMultidimensional("class_ids");
		Assert.assertEquals("Predictions array length not equal to expected array length.", expected.length,
				predictions.length);
		for (int i = 0; i < predictions.length; i++) {
			long pred = predictions[i][0];
			long exp = expected[i];
			displayDebug(exp, pred);
			Assert.assertEquals(exp, pred, 0);
		}
	}

	@Test
	public void higgsInputStringsOutputClassIdsStrings() {
		log.debug("Higgs Boosted Trees - input data as csv strings, output class_ids as String array");
		String[] expected = new String[] { "1", "0" };
		String[] predictions = model.in("inputs", s).out("class_ids").run().getStringArray("class_ids");
		Assert.assertEquals("Predictions array length not equal to expected array length.", expected.length,
				predictions.length);
		displayDebug(expected, predictions);
		Assert.assertArrayEquals(expected, predictions);
	}

	@Test
	public void higgsInputStringsOutputClassIdsStringsMultidimensional() {
		log.debug(
				"Higgs Boosted Trees - input data as csv strings, output class_ids as String (multidimensional) array");
		String[] expected = new String[] { "1", "0" };
		String[][] predictions = (String[][]) model.in("inputs", s).out("class_ids").run()
				.getStringArrayMultidimensional("class_ids");
		Assert.assertEquals("Predictions array length not equal to expected array length.", expected.length,
				predictions.length);
		for (int i = 0; i < predictions.length; i++) {
			String pred = predictions[i][0];
			String exp = expected[i];
			displayDebug(exp, pred);
			Assert.assertEquals(exp, pred);
		}
	}

	@Test
	public void higgsInputStringsOutputLogisticStrings() {
		log.debug("Higgs Boosted Trees - input data as csv strings, output logistic as String array");
		String[] expected = new String[] { "0.6440273", "0.10902369" };
		String[] predictions = model.in("inputs", s).out("logistic").run().getStringArray("logistic");
		for (int i = 0; i < expected.length; i++) {
			String pred = predictions[i];
			String exp = expected[i];
			float p = Float.parseFloat(pred);
			float e = Float.parseFloat(exp);
			displayDebug(p, e);
			Assert.assertEquals(p, e, 0.00001f);
		}
	}

	@Test
	public void higgsInputStringsOutputLogisticStringsMultidimensional() {
		log.debug(
				"Higgs Boosted Trees - input data as csv strings, output logistic as String (multidimensional) array");
		String[] expected = new String[] { "0.6440273", "0.10902369" };
		String[][] predictions = (String[][]) model.in("inputs", s).out("logistic").run()
				.getStringArrayMultidimensional("logistic");
		for (int i = 0; i < expected.length; i++) {
			String pred = predictions[i][0];
			String exp = expected[i];
			float p = Float.parseFloat(pred);
			float e = Float.parseFloat(exp);
			displayDebug(p, e);
			Assert.assertEquals(p, e, 0.00001f);
		}
	}

	@Test
	public void higgsInputStringsOutputClassesStrings() {
		log.debug("Higgs Boosted Trees - input data as csv strings, output classes as String array");
		String[] expected = new String[] { "1", "0" };
		String[] predictions = model.in("inputs", s).out("classes").run().getStringArray("classes");
		Assert.assertEquals("Predictions array length not equal to expected array length.", expected.length,
				predictions.length);
		displayDebug(expected, predictions);
		Assert.assertArrayEquals(expected, predictions);
	}

	@Test
	public void higgsInputStringsOutputClassesInts() {
		log.debug("Higgs Boosted Trees - input data as csv strings, output classes as int array");
		int[] expected = new int[] { 1, 0 };
		int[] predictions = model.in("inputs", s).out("classes").run().getIntArray("classes");
		Assert.assertEquals("Predictions array length not equal to expected array length.", expected.length,
				predictions.length);
		displayDebug(expected, predictions);
		Assert.assertArrayEquals(expected, predictions);
	}

	@Test
	public void higgsInputStringsOutputClassesIntsMultidimensional() {
		log.debug("Higgs Boosted Trees - input data as csv strings, output classes as int (multidimensional) array");
		int[] expected = new int[] { 1, 0 };
		int[][] predictions = (int[][]) model.in("inputs", s).out("classes").run()
				.getIntArrayMultidimensional("classes");
		Assert.assertEquals("Predictions array length not equal to expected array length.", expected.length,
				predictions.length);
		for (int i = 0; i < predictions.length; i++) {
			int pred = predictions[i][0];
			int exp = expected[i];
			displayDebug(exp, pred);
			Assert.assertEquals(exp, pred);
		}
	}

	@Test
	public void higgsInputStringsOutputClassesLongMultidimensional() {
		log.debug("Higgs Boosted Trees - input data as csv strings, output classes as long (multidimensional) array");
		long[] expected = new long[] { 1, 0 };
		long[][] predictions = (long[][]) model.in("inputs", s).out("classes").run()
				.getLongArrayMultidimensional("classes");
		Assert.assertEquals("Predictions array length not equal to expected array length.", expected.length,
				predictions.length);
		for (int i = 0; i < predictions.length; i++) {
			long pred = predictions[i][0];
			long exp = expected[i];
			displayDebug(exp, pred);
			Assert.assertEquals(exp, pred);
		}
	}

	@Test
	public void higgsInputStringsOutputClassesFloatsMultidimensional() {
		log.debug("Higgs Boosted Trees - input data as csv strings, output classes as float (multidimensional) array");
		float[] expected = new float[] { 1, 0 };
		float[][] predictions = (float[][]) model.in("inputs", s).out("classes").run()
				.getFloatArrayMultidimensional("classes");
		Assert.assertEquals("Predictions array length not equal to expected array length.", expected.length,
				predictions.length);
		for (int i = 0; i < predictions.length; i++) {
			float pred = predictions[i][0];
			float exp = expected[i];
			displayDebug(exp, pred);
			Assert.assertEquals(exp, pred, 0f);
		}
	}

	@Test
	public void higgsInputStringsOutputClassesDoublesMultidimensional() {
		log.debug("Higgs Boosted Trees - input data as csv strings, output classes as double (multidimensional) array");
		double[] expected = new double[] { 1, 0 };
		double[][] predictions = (double[][]) model.in("inputs", s).out("classes").run()
				.getDoubleArrayMultidimensional("classes");
		Assert.assertEquals("Predictions array length not equal to expected array length.", expected.length,
				predictions.length);
		for (int i = 0; i < predictions.length; i++) {
			double pred = predictions[i][0];
			double exp = expected[i];
			displayDebug(exp, pred);
			Assert.assertEquals(exp, pred, 0);
		}
	}

	@Test
	public void higgsInputStringsOutputClassesStringsMultidimensional() {
		log.debug("Higgs Boosted Trees - input data as csv strings, output classes as String (multidimensional) array");
		String[] expected = new String[] { "1", "0" };
		String[][] predictions = (String[][]) model.in("inputs", s).out("classes").run()
				.getStringArrayMultidimensional("classes");
		Assert.assertEquals("Predictions array length not equal to expected array length.", expected.length,
				predictions.length);
		for (int i = 0; i < predictions.length; i++) {
			String pred = predictions[i][0];
			String exp = expected[i];
			displayDebug(exp, pred);
			Assert.assertEquals(exp, pred);
		}
	}

	@Test
	public void higgsInputStringsOutputClassesLongs() {
		log.debug("Higgs Boosted Trees - input data as csv strings, output classes as long array");
		long[] expected = new long[] { 1, 0 };
		long[] predictions = model.in("inputs", s).out("classes").run().getLongArray("classes");
		Assert.assertEquals("Predictions array length not equal to expected array length.", expected.length,
				predictions.length);
		displayDebug(expected, predictions);
		Assert.assertArrayEquals(expected, predictions);
	}

	@Test
	public void higgsInputStringsOutputClassesFloats() {
		log.debug("Higgs Boosted Trees - input data as csv strings, output classes as float array");
		float[] expected = new float[] { 1f, 0f };
		float[] predictions = model.in("inputs", s).out("classes").run().getFloatArray("classes");
		Assert.assertEquals("Predictions array length not equal to expected array length.", expected.length,
				predictions.length);
		displayDebug(expected, predictions);
		Assert.assertArrayEquals(expected, predictions, 0f);
	}

	@Test
	public void higgsInputStringsOutputClassesDoubles() {
		log.debug("Higgs Boosted Trees - input data as csv strings, output classes as double array");
		double[] expected = new double[] { 1f, 0f };
		double[] predictions = model.in("inputs", s).out("classes").run().getDoubleArray("classes");
		Assert.assertEquals("Predictions array length not equal to expected array length.", expected.length,
				predictions.length);
		displayDebug(expected, predictions);
		Assert.assertArrayEquals(expected, predictions, 0);
	}

	@Test
	public void higgsInputStringsOutputLogisticFloats() {
		log.debug("Higgs Boosted Trees - input data as csv strings, output logistic as float array");
		float[] expected = new float[] { 0.6440273f, 0.10902369f };
		float[] predictions = model.in("inputs", s).out("logistic").run().getFloatArray("logistic");
		displayDebug(expected, predictions);
		Assert.assertArrayEquals(expected, predictions, 0.00001f);
	}

	@Test
	public void higgsInputStringsOutputLogisticDoubles() {
		log.debug("Higgs Boosted Trees - input data as csv strings, output logistic as double array");
		double[] expected = new double[] { 0.6440273, 0.10902369 };
		double[] predictions = model.in("inputs", s).out("logistic").run().getDoubleArray("logistic");
		displayDebug(expected, predictions);
		Assert.assertArrayEquals(expected, predictions, 0.00001);
	}

	@Test
	public void higgsInputStringsOutputLogitsFloats() {
		log.debug("Higgs Boosted Trees - input data as csv strings, output logits as float array");
		float[] expected = new float[] { 0.59288704f, -2.1007526f };
		float[] predictions = model.in("inputs", s).out("logits").run().getFloatArray("logits");
		displayDebug(expected, predictions);
		Assert.assertArrayEquals(expected, predictions, 0.00001f);
	}

	@Test
	public void higgsInputStringsOutputLogitsDoubles() {
		log.debug("Higgs Boosted Trees - input data as csv strings, output logits as double array");
		double[] expected = new double[] { 0.59288704, -2.1007526 };
		double[] predictions = model.in("inputs", s).out("logits").run().getDoubleArray("logits");
		displayDebug(expected, predictions);
		Assert.assertArrayEquals(expected, predictions, 0.00001);
	}

	@Test
	public void higgsInputStringsOutputProbabilitiesFloatsMultidimensional() {
		log.debug("Higgs Boosted Trees - input data as csv strings, output probabilities as 2D float array");
		float[][] expected = new float[][] { { 0.3559727f, 0.6440273f }, { 0.8909763f, 0.1090237f } };
		float[][] predictions = (float[][]) model.in("inputs", s).out("probabilities").run()
				.getFloatArrayMultidimensional("probabilities");
		for (int i = 0; i < expected.length; i++) {
			float[] expProbs = expected[i];
			float[] preProbs = predictions[i];
			displayDebug(expProbs, preProbs);
			Assert.assertArrayEquals(expProbs, preProbs, 0.00001f);
		}
	}

	@Test
	public void higgsInputStringsOutputProbabilitiesDoublesMultidimensional() {
		log.debug("Higgs Boosted Trees - input data as csv strings, output probabilities as 2D double array");
		double[][] expected = new double[][] { { 0.3559727, 0.6440273 }, { 0.8909763, 0.1090237 } };
		double[][] predictions = (double[][]) model.in("inputs", s).out("probabilities").run()
				.getDoubleArrayMultidimensional("probabilities");
		for (int i = 0; i < expected.length; i++) {
			double[] expProbs = expected[i];
			double[] preProbs = predictions[i];
			displayDebug(expProbs, preProbs);
			Assert.assertArrayEquals(expProbs, preProbs, 0.00001);
		}
	}

	@Test
	public void higgsInputStringsOutputProbabilitiesStringsMultidimensional() {
		log.debug(
				"Higgs Boosted Trees - input data as csv strings, output probabilities as String (multidimensional) array");
		String[][] expected = new String[][] { { "0.3559727", "0.6440273" }, { "0.8909763", "0.1090237" } };
		String[][] predictions = (String[][]) model.in("inputs", s).out("probabilities").run()
				.getStringArrayMultidimensional("probabilities");
		for (int i = 0; i < expected.length; i++) {
			for (int j = 0; j < expected[0].length; j++) {
				String pred = predictions[i][j];
				String exp = expected[i][j];
				float p = Float.parseFloat(pred);
				float e = Float.parseFloat(exp);
				displayDebug(p, e);
				Assert.assertEquals(p, e, 0.00001f);
			}
		}
	}

}
