package org.codait.tf.mnist;

import java.io.IOException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.codait.tf.ArrayUtil;
import org.codait.tf.TFException;
import org.codait.tf.TFModel;
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

	private void displayDebug(long label, long prediction) {
		log.debug(String.format("Label: %d, Prediction: %d", label, prediction));
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
}
