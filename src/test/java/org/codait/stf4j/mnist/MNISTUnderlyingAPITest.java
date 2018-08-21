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

package org.codait.stf4j.mnist;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.codait.stf4j.util.ArrayUtil;
import org.codait.stf4j.util.MNISTUtil;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;
import org.tensorflow.SavedModelBundle;
import org.tensorflow.Session;
import org.tensorflow.Session.Runner;
import org.tensorflow.Tensor;

public class MNISTUnderlyingAPITest {

	protected static Logger log = LogManager.getLogger(MNISTUnderlyingAPITest.class);

	private static SavedModelBundle model = null;
	private static String mDir = null;

	private static int[] labels = null;
	private static int[][][] images = null;

	@Before
	public void init() throws IOException {
		boolean savedModelExists = new File(MNISTTest.MNIST_SAVED_MODEL_DIR).exists();
		boolean testLabelsExist = new File(MNISTTest.MNIST_DATA_DIR + MNISTTest.TEST_LABELS).exists();
		boolean testImagesExist = new File(MNISTTest.MNIST_DATA_DIR + MNISTTest.TEST_IMAGES).exists();
		Assume.assumeTrue("SavedModel directory (" + MNISTTest.MNIST_SAVED_MODEL_DIR
				+ ") can't be found, so skipping MNIST Underlying API tests", savedModelExists);
		Assume.assumeTrue("Test labels (" + MNISTTest.MNIST_DATA_DIR + MNISTTest.TEST_LABELS
				+ ") can't be found, so skipping MNIST Underlying API tests", testLabelsExist);
		Assume.assumeTrue("Test images (" + MNISTTest.MNIST_DATA_DIR + MNISTTest.TEST_IMAGES
				+ ") can't be found, so skipping MNIST Underlying API tests", testImagesExist);

		if (labels == null) {
			log.debug("Loading MNIST labels");
			labels = MNISTUtil.getLabels(MNISTTest.MNIST_DATA_DIR + MNISTTest.TEST_LABELS);
		}
		if (images == null) {
			log.debug("Loading MNIST images");
			images = MNISTUtil.getImages(MNISTTest.MNIST_DATA_DIR + MNISTTest.TEST_IMAGES);
		}

		tfModel(MNISTTest.MNIST_SAVED_MODEL_DIR);
	}

	@AfterClass
	public static void after() {
		labels = null;
		images = null;
	}

	@Test
	public void singlePredictionClassesTest0() {
		singlePredictionClasses(0);
	}

	@Test
	public void singlePredictionClassesTest1() {
		singlePredictionClasses(1);
	}

	@Test
	public void singlePredictionClassesTest2() {
		singlePredictionClasses(2);
	}

	@Test
	public void singlePredictionClassesTest3() {
		singlePredictionClasses(3);
	}

	@Test
	public void singlePredictionClassesTest4() {
		singlePredictionClasses(4);
	}

	@Test
	public void singlePredictionProbabilitiesTest5() {
		singlePredictionProbabilities(5);
	}

	@Test
	public void singlePredictionProbabilitiesTest6() {
		singlePredictionProbabilities(6);
	}

	@Test
	public void singlePredictionProbabilitiesTest7() {
		singlePredictionProbabilities(7);
	}

	@Test
	public void singlePredictionProbabilitiesTest8() {
		singlePredictionProbabilities(8);
	}

	@Test
	public void singlePredictionProbabilitiesTest9() {
		singlePredictionProbabilities(9);
	}

	@Test
	public void singlePredictionClassesProbabilitiesTest10() {
		singlePredictionClassesProbabilities(10);
	}

	@Test
	public void singlePredictionClassesProbabilitiesTest11() {
		singlePredictionClassesProbabilities(11);
	}

	@Test
	public void singlePredictionClassesProbabilitiesTest12() {
		singlePredictionClassesProbabilities(12);
	}

	@Test
	public void singlePredictionClassesProbabilitiesTest13() {
		singlePredictionClassesProbabilities(13);
	}

	@Test
	public void singlePredictionClassesProbabilitiesTest14() {
		singlePredictionClassesProbabilities(14);
	}

	@Test
	public void multiplePredictionClassesTest() {
		multiplePredictionClasses(15, 16, 17, 18, 19);
	}

	@Test
	public void multiplePredictionProbabilitiesTest() {
		multiplePredictionProbabilities(20, 21, 22, 23, 24);
	}

	@Test
	public void multiplePredictionClassesProbabilitiesTest() {
		multiplePredictionClassesProbabilities(25, 26, 27, 28, 29);
	}

	protected SavedModelBundle tfModel() {
		return model;
	}

	protected SavedModelBundle tfModel(String modelDir) {
		return tfModel(modelDir, "serve");
	}

	protected SavedModelBundle tfModel(String modelDir, String... metaGraphDefTags) {
		if (modelDir == null) {
			return null;
		}
		if (!modelDir.equals(mDir)) {
			model = SavedModelBundle.load(modelDir, metaGraphDefTags);
			mDir = modelDir;
		}
		return model;
	}

	protected Session tfSession() {
		return model.session();
	}

	protected Runner tfRunner() {
		return tfSession().runner();
	}

	protected float[][][] getTestImages(int... imageNums) {
		float[][][] images = new float[imageNums.length][][];
		for (int i = 0; i < imageNums.length; i++) {
			images[i] = getTestImage(imageNums[i]);
		}
		return images;
	}

	protected float[][] getTestImage(int imageNum) {
		int[][] iImage = getTestImageAsInts(imageNum);
		float[][] fImage = (float[][]) ArrayUtil.convertArrayType(iImage, float.class);
		return fImage;
	}

	protected int[][] getTestImageAsInts(int imageNum) {
		return images[imageNum];
	}

	protected int getTestImageLabel(int imageNum) {
		return labels[imageNum];
	}

	protected int[] getTestImageLabels(int... imageNums) {
		int[] testLabels = new int[imageNums.length];
		for (int i = 0; i < imageNums.length; i++) {
			testLabels[i] = labels[imageNums[i]];
		}
		return testLabels;
	}

	protected void singlePredictionClasses(int testImageNum) {
		float[][] image = getTestImage(testImageNum);
		int label = getTestImageLabel(testImageNum);

		Tensor<Float> imageTensor = Tensor.create(image, Float.class);
		int prediction = (int) tfRunner().feed("Placeholder", imageTensor).fetch("ArgMax").run().get(0)
				.expect(Long.class).copyTo(new long[1])[0];

		if (label == prediction) {
			log.debug(String.format("Success, image (#%d) classes prediction (%d) matches label (%d)", testImageNum,
					prediction, label));
		} else {
			log.debug(String.format("Failure, image (#%d) classes prediction (%d) does not match label (%d)",
					testImageNum, prediction, label));
		}
		Assert.assertEquals(label, prediction);
	}

	protected void singlePredictionProbabilities(int testImageNum) {
		float[][] image = getTestImage(testImageNum);
		int label = getTestImageLabel(testImageNum);

		Tensor<Float> imageTensor = Tensor.create(image, Float.class);
		Tensor<Float> tResult = tfRunner().feed("Placeholder", imageTensor).fetch("Softmax").run().get(0)
				.expect(Float.class);
		float[][] fResult = tResult.copyTo(new float[1][10]);
		int prediction = 0;
		float maxValue = 0.0f;
		for (int i = 0; i < fResult[0].length; i++) {
			if (fResult[0][i] > maxValue) {
				prediction = i;
				maxValue = fResult[0][1];
			}
		}

		if (label == prediction) {
			log.debug(String.format("Success, image (#%d) probabilities prediction (%d) matches label (%d)",
					testImageNum, prediction, label));
		} else {
			log.debug(String.format("Failure, image (#%d) probabilities prediction (%d) does not match label (%d)",
					testImageNum, prediction, label));
		}
		Assert.assertEquals(label, prediction);
	}

	protected void singlePredictionClassesProbabilities(int testImageNum) {
		float[][] image = getTestImage(testImageNum);
		int label = getTestImageLabel(testImageNum);

		Tensor<Float> imageTensor = Tensor.create(image, Float.class);
		List<Tensor<?>> result = tfRunner().feed("Placeholder", imageTensor).fetch("ArgMax").fetch("Softmax").run();
		int argMaxPrediction = (int) result.get(0).expect(Long.class).copyTo(new long[1])[0];

		float[][] fResult = result.get(1).expect(Float.class).copyTo(new float[1][10]);
		int softmaxPrediction = 0;
		float maxValue = 0.0f;
		for (int i = 0; i < fResult[0].length; i++) {
			if (fResult[0][i] > maxValue) {
				softmaxPrediction = i;
				maxValue = fResult[0][1];
			}
		}

		if ((label == argMaxPrediction) && (label == softmaxPrediction)) {
			log.debug(String.format("Success, image (#%d) ArgMax (%d) and Softmax (%d) match label (%d)", testImageNum,
					argMaxPrediction, softmaxPrediction, label));
		} else {
			log.debug(String.format("Failure, image (#%d) ArgMax (%d), Softmax (%d) and label (%d) do not all match",
					testImageNum, argMaxPrediction, softmaxPrediction, label));
		}
		Assert.assertTrue((label == argMaxPrediction) && (label == softmaxPrediction));
	}

	protected void multiplePredictionClasses(int... testImageNums) {
		float[][][] images = getTestImages(testImageNums);
		int[] labels = getTestImageLabels(testImageNums);

		Tensor<Float> imageTensor = Tensor.create(images, Float.class);
		Tensor<Long> result = tfRunner().feed("Placeholder", imageTensor).fetch("ArgMax").run().get(0)
				.expect(Long.class);
		long[] predictions = result.copyTo(new long[testImageNums.length]);

		for (int i = 0; i < labels.length; i++) {
			if (labels[i] == predictions[i]) {
				log.debug(String.format("Success, multiple image (#%d) classes prediction (%d) matches label (%d)",
						testImageNums[i], predictions[i], labels[i]));
			} else {
				log.debug(
						String.format("Failure, multiple image (#%d) classes prediction (%d) does not match label (%d)",
								testImageNums[i], predictions[i], labels[i]));
			}
			Assert.assertEquals(labels[i], predictions[i]);
		}
	}

	protected void multiplePredictionProbabilities(int... testImageNums) {
		float[][][] images = getTestImages(testImageNums);
		int[] labels = getTestImageLabels(testImageNums);

		Tensor<Float> imageTensor = Tensor.create(images, Float.class);
		Tensor<Float> tResult = tfRunner().feed("Placeholder", imageTensor).fetch("Softmax").run().get(0)
				.expect(Float.class);
		float[][] fResult = tResult.copyTo(new float[testImageNums.length][10]);
		int[] predictions = new int[testImageNums.length];
		float[] maxValues = new float[testImageNums.length];
		for (int i = 0; i < fResult.length; i++) {
			for (int j = 0; j < fResult[i].length; j++) {
				if (fResult[i][j] > maxValues[i]) {
					predictions[i] = j;
					maxValues[i] = fResult[i][j];
				}
			}
		}

		for (int i = 0; i < labels.length; i++) {
			if (labels[i] == predictions[i]) {
				log.debug(
						String.format("Success, multiple image (#%d) probabilities prediction (%d) matches label (%d)",
								testImageNums[i], predictions[i], labels[i]));
			} else {
				log.debug(String.format(
						"Failure, multiple image (#%d) probabilities prediction (%d) does not match label (%d)",
						testImageNums[i], predictions[i], labels[i]));
			}
			Assert.assertEquals(labels[i], predictions[i]);
		}
	}

	protected void multiplePredictionClassesProbabilities(int... testImageNums) {
		float[][][] images = getTestImages(testImageNums);
		int[] labels = getTestImageLabels(testImageNums);

		Tensor<Float> imageTensor = Tensor.create(images, Float.class);
		List<Tensor<?>> result = tfRunner().feed("Placeholder", imageTensor).fetch("ArgMax").fetch("Softmax").run();
		long[] argMaxPredictions = result.get(0).expect(Long.class).copyTo(new long[testImageNums.length]);

		float[][] softmaxProbabilities = result.get(1).expect(Float.class).copyTo(new float[testImageNums.length][10]);
		int[] softmaxPredictions = new int[testImageNums.length];
		float[] maxValues = new float[testImageNums.length];
		for (int i = 0; i < softmaxProbabilities.length; i++) {
			for (int j = 0; j < softmaxProbabilities[i].length; j++) {
				if (softmaxProbabilities[i][j] > maxValues[i]) {
					softmaxPredictions[i] = j;
					maxValues[i] = softmaxProbabilities[i][j];
				}
			}
		}

		for (int i = 0; i < labels.length; i++) {
			int testImageNum = testImageNums[i];
			int label = labels[i];
			int argMaxPrediction = (int) argMaxPredictions[i];
			int softmaxPrediction = softmaxPredictions[i];
			if ((label == argMaxPrediction) && (label == softmaxPrediction)) {
				log.debug(String.format("Success, multiple image (#%d) ArgMax (%d) and Softmax (%d) match label (%d)",
						testImageNum, argMaxPrediction, softmaxPrediction, label));
			} else {
				log.debug(String.format(
						"Failure, multiple image (#%d) ArgMax (%d), Softmax (%d) and label (%d) do not all match",
						testImageNum, argMaxPrediction, softmaxPrediction, label));
			}
			Assert.assertTrue((label == argMaxPrediction) && (label == softmaxPrediction));
		}
	}

}
