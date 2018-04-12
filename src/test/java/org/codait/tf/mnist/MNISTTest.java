package org.codait.tf.mnist;

import java.io.IOException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.codait.tf.ArrayUtil;
import org.codait.tf.TFException;
import org.codait.tf.TFModel;
import org.codait.tf.TFResults;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.tensorflow.Tensor;

public class MNISTTest {

	protected static Logger log = LogManager.getLogger(MNISTTest.class);

	public static final String MNIST_DATA_DIR = "./mnist_data/";
	public static final String TRAIN_IMAGES = "train-images-idx3-ubyte";
	public static final String TRAIN_LABELS = "train-labels-idx1-ubyte";
	public static final String TEST_IMAGES = "t10k-images-idx3-ubyte";
	public static final String TEST_LABELS = "t10k-labels-idx1-ubyte";

	public static final String MNIST_SAVED_MODEL_DIR = "./mnist_model/";

	private int[] labels = null;
	private int[][][] images = null;
	private TFModel model = null;

	@Before
	public void init() throws IOException {
		labels = MNISTUtil.getLabels(MNIST_DATA_DIR + TEST_LABELS);
		images = MNISTUtil.getImages(MNIST_DATA_DIR + TEST_IMAGES);
		model = new TFModel(MNIST_SAVED_MODEL_DIR);
	}

	@After
	public void after() {
	}

	@Test
	public void testClassesPredictionInputIntArray() {
		log.debug("MNIST classes prediction - input image as 2d primitive int array");
		int label = labels[0];
		int prediction = model.in("image", images[0]).out("classes").run().getInt("classes");
		displayDebug(label, prediction);
		Assert.assertEquals(label, prediction);
	}

	@Test
	public void testClassesPredictionInputFloatArray() {
		log.debug("MNIST classes prediction - input image as 2d primitive float array");
		int label = labels[1];
		float[][] image = (float[][]) ArrayUtil.convertArrayType(images[1], float.class);
		int prediction = model.in("image", image).out("classes").run().getInt("classes");
		displayDebug(label, prediction);
		Assert.assertEquals(label, prediction);
	}

	@Test
	public void testClassesPredictionInputTensor() {
		log.debug("MNIST classes prediction - input image as Tensor (from 2d primitive float array)");
		int label = labels[2];
		float[][] image = (float[][]) ArrayUtil.convertArrayType(images[2], float.class);
		Tensor<Float> tensor = Tensor.create(image, Float.class);
		int prediction = model.in("image", tensor).out("classes").run().getInt("classes");
		displayDebug(label, prediction);
		Assert.assertEquals(label, prediction);
	}

	@Test
	public void testClassesPredictionInputIntegerObjectArray() {
		log.debug("MNIST classes prediction - input image as 2d Integer object array");
		int label = labels[3];
		Integer[][] image = (Integer[][]) ArrayUtil.convertArrayType(images[3], Integer.class);
		int prediction = model.in("image", image).out("classes").run().getInt("classes");
		displayDebug(label, prediction);
		Assert.assertEquals(label, prediction);
	}

	@Test
	public void testClassesPredictionInputFloatObjectArray() {
		log.debug("MNIST classes prediction - input image as 2d Float object array");
		int label = labels[4];
		Float[][] image = (Float[][]) ArrayUtil.convertArrayType(images[4], Float.class);
		int prediction = model.in("image", image).out("classes").run().getInt("classes");
		displayDebug(label, prediction);
		Assert.assertEquals(label, prediction);
	}

	@Test
	public void testClassesPredictionInputLongArray() {
		log.debug("MNIST classes prediction - input image as 2d primitive long array");
		int label = labels[5];
		long[][] image = (long[][]) ArrayUtil.convertArrayType(images[5], long.class);
		int prediction = model.in("image", image).out("classes").run().getInt("classes");
		displayDebug(label, prediction);
		Assert.assertEquals(label, prediction);
	}

	@Test
	public void testClassesPredictionInputLongObjectArray() {
		log.debug("MNIST classes prediction - input image as 2d Long object array");
		int label = labels[6];
		Long[][] image = (Long[][]) ArrayUtil.convertArrayType(images[6], Long.class);
		int prediction = model.in("image", image).out("classes").run().getInt("classes");
		displayDebug(label, prediction);
		Assert.assertEquals(label, prediction);
	}

	@Test
	public void testClassesPredictionInputDoubleArray() {
		log.debug("MNIST classes prediction - input image as 2d primitive double array");
		int label = labels[7];
		double[][] image = (double[][]) ArrayUtil.convertArrayType(images[7], double.class);
		int prediction = model.in("image", image).out("classes").run().getInt("classes");
		displayDebug(label, prediction);
		Assert.assertEquals(label, prediction);
	}

	@Test
	public void testClassesPredictionInputDoubleObjectArray() {
		log.debug("MNIST classes prediction - input image as 2d Double object array");
		int label = labels[8];
		Double[][] image = (Double[][]) ArrayUtil.convertArrayType(images[8], Double.class);
		int prediction = model.in("image", image).out("classes").run().getInt("classes");
		displayDebug(label, prediction);
		Assert.assertEquals(label, prediction);
	}

	@Test(expected = TFException.class)
	public void testBadInputKey() {
		log.debug("MNIST classes prediction - bad input key");
		model.in("badInputKey", null);
	}

	@Test(expected = TFException.class)
	public void testNullInputValue() {
		log.debug("MNIST classes prediction - null input value");
		model.in("image", null);
	}

	@Test
	public void testClassesPredictionInputTensorMultipleImages() {
		log.debug("MNIST classes prediction - input images as Tensor, output long array");

		long[] lbls = new long[] { labels[9], labels[10] };

		int[][][] iImages = ArrayUtil.convert2dIntArraysTo3dIntArray(images[9], images[10]);
		float[][][] fImages = (float[][][]) ArrayUtil.convertArrayType(iImages, float.class);
		Tensor<Float> tensor = Tensor.create(fImages, Float.class);
		long[] predictions = model.in("image", tensor).out("classes").run().getLongArray("classes");
		displayDebug(lbls, predictions);
		Assert.assertArrayEquals(lbls, predictions);
	}

	@Test
	public void testClassesPredictionInputTensorOutputMultidimensionalLongArray() {
		log.debug("MNIST classes prediction - input images as Tensor, output multidimensional long array");

		long[] lbls = new long[] { labels[11], labels[12] };

		int[][][] iImages = ArrayUtil.convert2dIntArraysTo3dIntArray(images[11], images[12]);
		float[][][] fImages = (float[][][]) ArrayUtil.convertArrayType(iImages, float.class);
		Tensor<Float> tensor = Tensor.create(fImages, Float.class);
		long[] predictions = (long[]) model.in("image", tensor).out("classes").run()
				.getLongArrayMultidimensional("classes");
		displayDebug(lbls, predictions);
		Assert.assertArrayEquals(lbls, predictions);
	}

	@Test
	public void testClassesPredictionInputIntArrayMultipleImages() {
		log.debug("MNIST classes prediction - input images as 3d primitive int array, output long array");
		long[] lbls = new long[] { labels[13], labels[14] };

		int[][][] iImages = ArrayUtil.convert2dIntArraysTo3dIntArray(images[13], images[14]);
		long[] predictions = model.in("image", iImages).out("classes").run().getLongArray("classes");
		displayDebug(lbls, predictions);
		Assert.assertArrayEquals(lbls, predictions);
	}

	@Test
	public void testClassesPredictionInputFloatArrayMultipleImages() {
		log.debug("MNIST classes prediction - input images as 3d primitive float array, output long array");
		long[] lbls = new long[] { labels[15], labels[16] };

		int[][][] iImages = ArrayUtil.convert2dIntArraysTo3dIntArray(images[15], images[16]);
		float[][][] fImages = (float[][][]) ArrayUtil.convertArrayType(iImages, float.class);
		long[] predictions = model.in("image", fImages).out("classes").run().getLongArray("classes");
		displayDebug(lbls, predictions);
		Assert.assertArrayEquals(lbls, predictions);
	}

	private void displayDebug(long[] labels, long[] predictions) {
		for (int i = 0; i < labels.length; i++) {
			long label = labels[i];
			long prediction = predictions[i];
			displayDebug(label, prediction);
		}
	}

	private void displayDebug(int[] labels, int[] predictions) {
		for (int i = 0; i < labels.length; i++) {
			long label = labels[i];
			long prediction = predictions[i];
			displayDebug(label, prediction);
		}
	}

	private void displayDebug(long label, long prediction) {
		log.debug(String.format("Label: %d, Prediction: %d", label, prediction));
	}

	private void displayDebug(float label, float prediction) {
		log.debug(String.format("Label: %f, Prediction: %f", label, prediction));
	}

	private void displayDebug(double label, double prediction) {
		log.debug(String.format("Label: %f, Prediction: %f", label, prediction));
	}

	private void displayDebug(long label, long cPrediction, long pPrediction) {
		log.debug(String.format("Label: %d, Classes Prediction: %d, Probabilities Prediction: %d", label, cPrediction,
				pPrediction));
	}

	@Test
	public void testClassesPredictionInputIntegerObjectArrayMultipleImages() {
		log.debug("MNIST classes prediction - input images as 3d Integer object array, output long array");
		long[] lbls = new long[] { labels[17], labels[18] };

		int[][][] iImages = ArrayUtil.convert2dIntArraysTo3dIntArray(images[17], images[18]);
		Integer[][][] integerImages = (Integer[][][]) ArrayUtil.convertArrayType(iImages, Integer.class);
		long[] predictions = model.in("image", integerImages).out("classes").run().getLongArray("classes");
		displayDebug(lbls, predictions);
		Assert.assertArrayEquals(lbls, predictions);
	}

	@Test
	public void testClassesPredictionInputFloatObjectArrayMultipleImages() {
		log.debug("MNIST classes prediction - input images as 3d Float object array, output long array");
		long[] lbls = new long[] { labels[19], labels[20] };

		int[][][] iImages = ArrayUtil.convert2dIntArraysTo3dIntArray(images[19], images[20]);
		Float[][][] fImages = (Float[][][]) ArrayUtil.convertArrayType(iImages, Float.class);
		long[] predictions = model.in("image", fImages).out("classes").run().getLongArray("classes");
		displayDebug(lbls, predictions);
		Assert.assertArrayEquals(lbls, predictions);
	}

	@Test
	public void testClassesPredictionInputLongObjectArrayMultipleImages() {
		log.debug("MNIST classes prediction - input images as 3d Long object array, output long array");
		long[] lbls = new long[] { labels[21], labels[22] };

		int[][][] iImages = ArrayUtil.convert2dIntArraysTo3dIntArray(images[21], images[22]);
		Long[][][] lImages = (Long[][][]) ArrayUtil.convertArrayType(iImages, Long.class);
		long[] predictions = model.in("image", lImages).out("classes").run().getLongArray("classes");
		displayDebug(lbls, predictions);
		Assert.assertArrayEquals(lbls, predictions);
	}

	@Test
	public void testClassesPredictionInputLongArrayMultipleImages() {
		log.debug("MNIST classes prediction - input images as 3d primitive long array, output long array");
		long[] lbls = new long[] { labels[23], labels[24] };

		int[][][] iImages = ArrayUtil.convert2dIntArraysTo3dIntArray(images[23], images[24]);
		long[][][] lImages = (long[][][]) ArrayUtil.convertArrayType(iImages, long.class);
		long[] predictions = model.in("image", lImages).out("classes").run().getLongArray("classes");
		displayDebug(lbls, predictions);
		Assert.assertArrayEquals(lbls, predictions);
	}

	@Test
	public void testClassesPredictionInputDoubleArrayMultipleImages() {
		log.debug("MNIST classes prediction - input images as 3d primitive double array, output long array");
		long[] lbls = new long[] { labels[25], labels[26] };

		int[][][] iImages = ArrayUtil.convert2dIntArraysTo3dIntArray(images[25], images[26]);
		double[][][] dImages = (double[][][]) ArrayUtil.convertArrayType(iImages, double.class);
		long[] predictions = model.in("image", dImages).out("classes").run().getLongArray("classes");
		displayDebug(lbls, predictions);
		Assert.assertArrayEquals(lbls, predictions);
	}

	@Test
	public void testClassesPredictionInputDoubleObjectArrayMultipleImages() {
		log.debug("MNIST classes prediction - input images as 3d Double object array, output long array");
		long[] lbls = new long[] { labels[27], labels[28] };

		int[][][] iImages = ArrayUtil.convert2dIntArraysTo3dIntArray(images[27], images[28]);
		Double[][][] dImages = (Double[][][]) ArrayUtil.convertArrayType(iImages, Double.class);
		long[] predictions = model.in("image", dImages).out("classes").run().getLongArray("classes");
		displayDebug(lbls, predictions);
		Assert.assertArrayEquals(lbls, predictions);
	}

	@Test
	public void testClassesPredictionInputIntArrayOutputLong() {
		log.debug("MNIST classes prediction - input image as 2d primitive int array, output long");
		long label = labels[29];
		long prediction = model.in("image", images[29]).out("classes").run().getLong("classes");
		displayDebug(label, prediction);
		Assert.assertEquals(label, prediction);
	}

	@Test
	public void testClassesPredictionInputIntArrayThreeImages() {
		log.debug("MNIST classes prediction - input images as 3d primitive int array, output long array");
		long[] lbls = new long[] { labels[30], labels[31], labels[32] };

		int[][][] iImages = ArrayUtil.convert2dIntArraysTo3dIntArray(images[30], images[31], images[32]);
		long[] predictions = model.in("image", iImages).out("classes").run().getLongArray("classes");
		displayDebug(lbls, predictions);
		Assert.assertArrayEquals(lbls, predictions);
	}

	@Test
	public void testClassesPredictionInputIntArrayOneImage() {
		log.debug("MNIST classes prediction - input image as 3d primitive int array, output long array");
		long[] lbls = new long[] { labels[33] };

		int[][][] iImages = ArrayUtil.convert2dIntArraysTo3dIntArray(images[33]);
		long[] predictions = model.in("image", iImages).out("classes").run().getLongArray("classes");
		displayDebug(lbls, predictions);
		Assert.assertArrayEquals(lbls, predictions);
	}

	@Test
	public void testProbabilitiesPredictionInputIntArray() {
		log.debug("MNIST probabilities prediction - input image as 2d primitive int array");
		int label = labels[34];
		float[] probabilities = model.in("image", images[34]).out("probabilities").run().getFloatArray("probabilities");
		int prediction = ArrayUtil.maxIndex(probabilities);
		displayDebug(label, prediction);
		Assert.assertEquals(label, prediction);
	}

	@Test
	public void testProbabilitiesPredictionInputIntArrayMultipleImages() {
		log.debug("MNIST probabilities prediction - input images as 3d primitive int array");
		int[] lbls = new int[] { labels[35], labels[36] };
		int[][][] iImages = ArrayUtil.convert2dIntArraysTo3dIntArray(images[35], images[36]);
		float[][] probabilities = (float[][]) model.in("image", iImages).out("probabilities").run()
				.getFloatArrayMultidimensional("probabilities");
		int[] predictions = ArrayUtil.maxIndices(probabilities);
		displayDebug(lbls, predictions);
		Assert.assertArrayEquals(lbls, predictions);
	}

	@Test
	public void testClassesPredictionInputIntArrayMultipleImagesOutputIntArray() {
		log.debug("MNIST classes prediction - input images as 3d primitive int array, output int array");
		int[] lbls = new int[] { labels[37], labels[38] };

		int[][][] iImages = ArrayUtil.convert2dIntArraysTo3dIntArray(images[37], images[38]);
		int[] predictions = model.in("image", iImages).out("classes").run().getIntArray("classes");
		displayDebug(lbls, predictions);
		Assert.assertArrayEquals(lbls, predictions);
	}

	@Test
	public void testClassesPredictionInputIntArrayMultipleImagesOutputMultidimensionalIntArray() {
		log.debug(
				"MNIST classes prediction - input images as 3d primitive int array, output multidimensional int array");
		int[] lbls = new int[] { labels[39], labels[40] };

		int[][][] iImages = ArrayUtil.convert2dIntArraysTo3dIntArray(images[39], images[40]);
		int[] predictions = (int[]) model.in("image", iImages).out("classes").run()
				.getIntArrayMultidimensional("classes");
		displayDebug(lbls, predictions);
		Assert.assertArrayEquals(lbls, predictions);
	}

	@Test
	public void testProbabilitiesPredictionInputIntegerObjectArray() {
		log.debug("MNIST probabilities prediction - input image as 2d Integer object array");
		int label = labels[41];
		Integer[][] image = (Integer[][]) ArrayUtil.convertArrayType(images[41], Integer.class);
		float[] probabilities = model.in("image", image).out("probabilities").run().getFloatArray("probabilities");
		int prediction = ArrayUtil.maxIndex(probabilities);
		displayDebug(label, prediction);
		Assert.assertEquals(label, prediction);
	}

	@Test
	public void testProbabilitiesPredictionInputIntegerObjectArrayMultipleImages() {
		log.debug("MNIST probabilities prediction - input images as 3d Integer object array");
		int[] lbls = new int[] { labels[42], labels[43] };
		int[][][] iImages = ArrayUtil.convert2dIntArraysTo3dIntArray(images[42], images[43]);
		Integer[][][] imgs = (Integer[][][]) ArrayUtil.convertArrayType(iImages, Integer.class);
		float[][] probabilities = (float[][]) model.in("image", imgs).out("probabilities").run()
				.getFloatArrayMultidimensional("probabilities");
		int[] predictions = ArrayUtil.maxIndices(probabilities);
		displayDebug(lbls, predictions);
		Assert.assertArrayEquals(lbls, predictions);
	}

	@Test
	public void testProbabilitiesPredictionInputFloatArray() {
		log.debug("MNIST probabilities prediction - input image as primitive 2d float array");
		int label = labels[44];
		float[][] image = (float[][]) ArrayUtil.convertArrayType(images[44], float.class);
		float[] probabilities = model.in("image", image).out("probabilities").run().getFloatArray("probabilities");
		int prediction = ArrayUtil.maxIndex(probabilities);
		displayDebug(label, prediction);
		Assert.assertEquals(label, prediction);
	}

	@Test
	public void testProbabilitiesPredictionInputFloatArrayMultipleImages() {
		log.debug("MNIST probabilities prediction - input images as 3d primitive float array");
		int[] lbls = new int[] { labels[45], labels[46] };
		int[][][] iImages = ArrayUtil.convert2dIntArraysTo3dIntArray(images[45], images[46]);
		float[][][] imgs = (float[][][]) ArrayUtil.convertArrayType(iImages, float.class);
		float[][] probabilities = (float[][]) model.in("image", imgs).out("probabilities").run()
				.getFloatArrayMultidimensional("probabilities");
		int[] predictions = ArrayUtil.maxIndices(probabilities);
		displayDebug(lbls, predictions);
		Assert.assertArrayEquals(lbls, predictions);
	}

	@Test
	public void testProbabilitiesPredictionInputFloatObjectArray() {
		log.debug("MNIST probabilities prediction - input image as 2d Float object array");
		int label = labels[47];
		Float[][] image = (Float[][]) ArrayUtil.convertArrayType(images[47], Float.class);
		float[] probabilities = model.in("image", image).out("probabilities").run().getFloatArray("probabilities");
		int prediction = ArrayUtil.maxIndex(probabilities);
		displayDebug(label, prediction);
		Assert.assertEquals(label, prediction);
	}

	@Test
	public void testProbabilitiesPredictionInputFloatObjectArrayMultipleImages() {
		log.debug("MNIST probabilities prediction - input images as 3d Float object array");
		int[] lbls = new int[] { labels[48], labels[49] };
		int[][][] iImages = ArrayUtil.convert2dIntArraysTo3dIntArray(images[48], images[49]);
		Float[][][] imgs = (Float[][][]) ArrayUtil.convertArrayType(iImages, Float.class);
		float[][] probabilities = (float[][]) model.in("image", imgs).out("probabilities").run()
				.getFloatArrayMultidimensional("probabilities");
		int[] predictions = ArrayUtil.maxIndices(probabilities);
		displayDebug(lbls, predictions);
		Assert.assertArrayEquals(lbls, predictions);
	}

	@Test
	public void testProbabilitiesPredictionInputLongArray() {
		log.debug("MNIST probabilities prediction - input image as primitive 2d long array");
		int label = labels[50];
		long[][] image = (long[][]) ArrayUtil.convertArrayType(images[50], long.class);
		float[] probabilities = model.in("image", image).out("probabilities").run().getFloatArray("probabilities");
		int prediction = ArrayUtil.maxIndex(probabilities);
		displayDebug(label, prediction);
		Assert.assertEquals(label, prediction);
	}

	@Test
	public void testProbabilitiesPredictionInputLongArrayMultipleImages() {
		log.debug("MNIST probabilities prediction - input images as 3d primitive long array");
		int[] lbls = new int[] { labels[51], labels[52] };
		int[][][] iImages = ArrayUtil.convert2dIntArraysTo3dIntArray(images[51], images[52]);
		long[][][] imgs = (long[][][]) ArrayUtil.convertArrayType(iImages, long.class);
		float[][] probabilities = (float[][]) model.in("image", imgs).out("probabilities").run()
				.getFloatArrayMultidimensional("probabilities");
		int[] predictions = ArrayUtil.maxIndices(probabilities);
		displayDebug(lbls, predictions);
		Assert.assertArrayEquals(lbls, predictions);
	}

	@Test
	public void testProbabilitiesPredictionInputLongObjectArray() {
		log.debug("MNIST probabilities prediction - input image as 2d Long object array");
		int label = labels[53];
		Long[][] image = (Long[][]) ArrayUtil.convertArrayType(images[53], Long.class);
		float[] probabilities = model.in("image", image).out("probabilities").run().getFloatArray("probabilities");
		int prediction = ArrayUtil.maxIndex(probabilities);
		displayDebug(label, prediction);
		Assert.assertEquals(label, prediction);
	}

	@Test
	public void testProbabilitiesPredictionInputLongObjectArrayMultipleImages() {
		log.debug("MNIST probabilities prediction - input images as 3d Long object array");
		int[] lbls = new int[] { labels[54], labels[55] };
		int[][][] iImages = ArrayUtil.convert2dIntArraysTo3dIntArray(images[54], images[55]);
		Long[][][] imgs = (Long[][][]) ArrayUtil.convertArrayType(iImages, Long.class);
		float[][] probabilities = (float[][]) model.in("image", imgs).out("probabilities").run()
				.getFloatArrayMultidimensional("probabilities");
		int[] predictions = ArrayUtil.maxIndices(probabilities);
		displayDebug(lbls, predictions);
		Assert.assertArrayEquals(lbls, predictions);
	}

	@Test
	public void testProbabilitiesPredictionInputDoubleArray() {
		log.debug("MNIST probabilities prediction - input image as primitive 2d double array");
		int label = labels[50];
		double[][] image = (double[][]) ArrayUtil.convertArrayType(images[50], double.class);
		float[] probabilities = model.in("image", image).out("probabilities").run().getFloatArray("probabilities");
		int prediction = ArrayUtil.maxIndex(probabilities);
		displayDebug(label, prediction);
		Assert.assertEquals(label, prediction);
	}

	@Test
	public void testProbabilitiesPredictionInputDoubleArrayMultipleImages() {
		log.debug("MNIST probabilities prediction - input images as 3d primitive double array");
		int[] lbls = new int[] { labels[51], labels[52] };
		int[][][] iImages = ArrayUtil.convert2dIntArraysTo3dIntArray(images[51], images[52]);
		double[][][] imgs = (double[][][]) ArrayUtil.convertArrayType(iImages, double.class);
		float[][] probabilities = (float[][]) model.in("image", imgs).out("probabilities").run()
				.getFloatArrayMultidimensional("probabilities");
		int[] predictions = ArrayUtil.maxIndices(probabilities);
		displayDebug(lbls, predictions);
		Assert.assertArrayEquals(lbls, predictions);
	}

	@Test
	public void testProbabilitiesPredictionInputDoubleObjectArray() {
		log.debug("MNIST probabilities prediction - input image as 2d Double object array");
		int label = labels[53];
		Double[][] image = (Double[][]) ArrayUtil.convertArrayType(images[53], Double.class);
		float[] probabilities = model.in("image", image).out("probabilities").run().getFloatArray("probabilities");
		int prediction = ArrayUtil.maxIndex(probabilities);
		displayDebug(label, prediction);
		Assert.assertEquals(label, prediction);
	}

	@Test
	public void testProbabilitiesPredictionInputDoubleObjectArrayMultipleImages() {
		log.debug("MNIST probabilities prediction - input images as 3d Double object array");
		int[] lbls = new int[] { labels[54], labels[55] };
		int[][][] iImages = ArrayUtil.convert2dIntArraysTo3dIntArray(images[54], images[55]);
		Double[][][] imgs = (Double[][][]) ArrayUtil.convertArrayType(iImages, Double.class);
		float[][] probabilities = (float[][]) model.in("image", imgs).out("probabilities").run()
				.getFloatArrayMultidimensional("probabilities");
		int[] predictions = ArrayUtil.maxIndices(probabilities);
		displayDebug(lbls, predictions);
		Assert.assertArrayEquals(lbls, predictions);
	}

	@Test
	public void testClassesPredictionInputIntArrayOutputFloat() {
		log.debug("MNIST classes prediction - input image as 2d primitive int array, output float");
		float label = labels[0];
		float prediction = model.in("image", images[0]).out("classes").run().getFloat("classes");
		displayDebug(label, prediction);
		Assert.assertEquals(label, prediction, 0f);
	}

	@Test
	public void testClassesPredictionInputIntArrayOutputDouble() {
		log.debug("MNIST classes prediction - input image as 2d primitive int array, output double");
		double label = labels[0];
		double prediction = model.in("image", images[0]).out("classes").run().getDouble("classes");
		displayDebug(label, prediction);
		Assert.assertEquals(label, prediction, 0d);
	}

	@Test
	public void testProbabilitiesPredictionInputIntArrayOutputDoubleArray() {
		log.debug("MNIST probabilities prediction - input image as 2d primitive int array, output double array");
		int label = labels[34];
		double[] probabilities = model.in("image", images[34]).out("probabilities").run()
				.getDoubleArray("probabilities");
		int prediction = ArrayUtil.maxIndex(probabilities);
		displayDebug(label, prediction);
		Assert.assertEquals(label, prediction);
	}

	@Test
	public void testProbabilitiesPredictionInputIntArrayMultipleImagesOutputDoubleArrayMultidimensional() {
		log.debug(
				"MNIST probabilities prediction - input images as 3d primitive int array, output double array multidimensional");
		int[] lbls = new int[] { labels[35], labels[36] };
		int[][][] iImages = ArrayUtil.convert2dIntArraysTo3dIntArray(images[35], images[36]);
		double[][] probabilities = (double[][]) model.in("image", iImages).out("probabilities").run()
				.getDoubleArrayMultidimensional("probabilities");
		int[] predictions = ArrayUtil.maxIndices(probabilities);
		displayDebug(lbls, predictions);
		Assert.assertArrayEquals(lbls, predictions);
	}

	@Test
	public void testClassesProbabilityPredictionInputIntArray() {
		log.debug("MNIST classes prediction - input image as 2d primitive int array");
		int label = labels[0];
		int[][] image = images[0];
		TFResults results = model.in("image", image).out("classes", "probabilities").run();
		int cPrediction = results.getInt("classes");
		float[] probabilities = results.getFloatArray("probabilities");
		int pPrediction = ArrayUtil.maxIndex(probabilities);
		displayDebug(label, cPrediction, pPrediction);
		Assert.assertEquals(label, cPrediction);
		Assert.assertEquals(label, pPrediction);
	}

}
