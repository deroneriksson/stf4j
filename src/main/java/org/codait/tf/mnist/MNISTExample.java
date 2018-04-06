package org.codait.tf.mnist;

import java.io.IOException;
import java.util.List;

import org.tensorflow.SavedModelBundle;
import org.tensorflow.Session;
import org.tensorflow.Session.Runner;
import org.tensorflow.Tensor;

/**
 * Try an MNIST saved model created in Tensorflow from Java.
 * 
 * For more Python and model information, see info.txt.
 * 
 */
public class MNISTExample {

	public static final String MNIST_DATA_DIR = "./mnist_data/";
	public static final String MNIST_SAVED_MODEL_DIR = "./mnist_model/";

	public static final String TRAIN_IMAGES = "train-images-idx3-ubyte";
	public static final String TRAIN_LABELS = "train-labels-idx1-ubyte";
	public static final String TEST_IMAGES = "t10k-images-idx3-ubyte";
	public static final String TEST_LABELS = "t10k-labels-idx1-ubyte";

	private static SavedModelBundle model = null;
	private static String mDir = null;

	private static int[] labels = null;
	private static int[][][] images = null;

	public static void main(String[] args) throws IOException {
		labels = MNISTUtil.getLabels(MNIST_DATA_DIR + TEST_LABELS);
		images = MNISTUtil.getImages(MNIST_DATA_DIR + TEST_IMAGES);

		tfModel(MNIST_SAVED_MODEL_DIR);

		singlePredictionClasses(0);
		singlePredictionClasses(1);
		singlePredictionClasses(2);
		singlePredictionClasses(3);
		singlePredictionClasses(4);

		singlePredictionProbabilities(5);
		singlePredictionProbabilities(6);
		singlePredictionProbabilities(7);
		singlePredictionProbabilities(8);
		singlePredictionProbabilities(9);

		singlePredictionClassesProbabilities(10);
		singlePredictionClassesProbabilities(11);
		singlePredictionClassesProbabilities(12);
		singlePredictionClassesProbabilities(13);
		singlePredictionClassesProbabilities(14);

		multiplePredictionClasses(15, 16, 17, 18, 19);
		multiplePredictionProbabilities(20, 21, 22, 23, 24);
		multiplePredictionClassesProbabilities(25, 26, 27, 28, 29);
	}

	public static void singlePredictionClasses(int testImageNum) throws IOException {
		float[][] image = getTestImage(testImageNum);
		int label = getTestImageLabel(testImageNum);

		Tensor<Float> imageTensor = Tensor.create(image, Float.class);
		int prediction = (int) tfRunner().feed("Placeholder", imageTensor).fetch("ArgMax").run().get(0)
				.expect(Long.class).copyTo(new long[1])[0];

		if (label == prediction) {
			System.out.println(String.format("Success, image (#%d) classes prediction (%d) matches label (%d)",
					testImageNum, prediction, label));
		} else {
			System.out.println(String.format("Failure, image (#%d) classes prediction (%d) does not match label (%d)",
					testImageNum, prediction, label));
		}
	}

	public static void singlePredictionProbabilities(int testImageNum) throws IOException {
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
			System.out.println(String.format("Success, image (#%d) probabilities prediction (%d) matches label (%d)",
					testImageNum, prediction, label));
		} else {
			System.out.println(
					String.format("Failure, image (#%d) probabilities prediction (%d) does not match label (%d)",
							testImageNum, prediction, label));
		}
	}

	public static void singlePredictionClassesProbabilities(int testImageNum) throws IOException {
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
			System.out.println(String.format("Success, image (#%d) ArgMax (%d) and Softmax (%d) match label (%d)",
					testImageNum, argMaxPrediction, softmaxPrediction, label));
		} else {
			System.out.println(
					String.format("Failure, image (#%d) ArgMax (%d), Softmax (%d) and label (%d) do not all match",
							testImageNum, argMaxPrediction, softmaxPrediction, label));
		}
	}

	public static void multiplePredictionClasses(int... testImageNums) throws IOException {

		float[][][] images = getTestImages(testImageNums);
		int[] labels = getTestImageLabels(testImageNums);

		Tensor<Float> imageTensor = Tensor.create(images, Float.class);
		Tensor<Long> result = tfRunner().feed("Placeholder", imageTensor).fetch("ArgMax").run().get(0)
				.expect(Long.class);
		long[] predictions = result.copyTo(new long[testImageNums.length]);

		for (int i = 0; i < labels.length; i++) {
			if (labels[i] == predictions[i]) {
				System.out.println(
						String.format("Success, multiple image (#%d) classes prediction (%d) matches label (%d)",
								testImageNums[i], predictions[i], labels[i]));
			} else {
				System.out.println(
						String.format("Failure, multiple image (#%d) classes prediction (%d) does not match label (%d)",
								testImageNums[i], predictions[i], labels[i]));
			}
		}
	}

	public static void multiplePredictionProbabilities(int... testImageNums) throws IOException {

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
				System.out.println(
						String.format("Success, multiple image (#%d) probabilities prediction (%d) matches label (%d)",
								testImageNums[i], predictions[i], labels[i]));
			} else {
				System.out.println(String.format(
						"Failure, multiple image (#%d) probabilities prediction (%d) does not match label (%d)",
						testImageNums[i], predictions[i], labels[i]));
			}
		}
	}

	public static void multiplePredictionClassesProbabilities(int... testImageNums) throws IOException {

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
				System.out.println(
						String.format("Success, multiple image (#%d) ArgMax (%d) and Softmax (%d) match label (%d)",
								testImageNum, argMaxPrediction, softmaxPrediction, label));
			} else {
				System.out.println(String.format(
						"Failure, multiple image (#%d) ArgMax (%d), Softmax (%d) and label (%d) do not all match",
						testImageNum, argMaxPrediction, softmaxPrediction, label));
			}
		}
	}

	public static SavedModelBundle tfModel() {
		return model;
	}

	public static SavedModelBundle tfModel(String modelDir) {
		return tfModel(modelDir, "serve");
	}

	public static SavedModelBundle tfModel(String modelDir, String... metaGraphDefTags) {
		if (modelDir == null) {
			return null;
		}
		if (!modelDir.equals(mDir)) {
			model = SavedModelBundle.load(modelDir, metaGraphDefTags);
			mDir = modelDir;
		}
		return model;
	}

	public static Session tfSession() {
		return model.session();
	}

	public static Runner tfRunner() {
		return tfSession().runner();
	}

	public static float[][][] getTestImages(int... imageNums) throws IOException {
		float[][][] images = new float[imageNums.length][][];
		for (int i = 0; i < imageNums.length; i++) {
			images[i] = getTestImage(imageNums[i]);
		}
		return images;
	}

	public static float[][] getTestImage(int imageNum) throws IOException {
		int[][] iImage = getTestImageAsInts(imageNum);
		float[][] fImage = MNISTUtil.iToF(iImage);
		return fImage;
	}

	public static int[][] getTestImageAsInts(int imageNum) throws IOException {
		return images[imageNum];
	}

	public static int getTestImageLabel(int imageNum) throws IOException {
		return labels[imageNum];
	}

	public static int[] getTestImageLabels(int... imageNums) throws IOException {
		int[] testLabels = new int[imageNums.length];
		for (int i = 0; i < imageNums.length; i++) {
			testLabels[i] = labels[imageNums[i]];
		}
		return testLabels;
	}
}
