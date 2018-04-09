package org.codait.tf.mnist;

import java.io.IOException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.codait.tf.ArrayUtil;
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
		log.debug(String.format("Label: %d, Prediction: %d", label, prediction));
		Assert.assertEquals(label, prediction);
	}

	@Test
	public void testClassesPredictionInputFloatArray() {
		log.debug("MNIST classes prediction - input image as 2d primitive float array");
		int label = labels[1];
		float[][] image = MNISTUtil.iToF(images[1]);
		int prediction = model.in("image", image).out("classes").run().getInt("classes");
		log.debug(String.format("Label: %d, Prediction: %d", label, prediction));
		Assert.assertEquals(label, prediction);
	}

	@Test
	public void testClassesPredictionInputTensor() {
		log.debug("MNIST classes prediction - input image as Tensor (from 2d primitive float array)");
		int label = labels[2];
		float[][] image = MNISTUtil.iToF(images[2]);
		Tensor<Float> tensor = Tensor.create(image, Float.class);
		int prediction = model.in("image", tensor).out("classes").run().getInt("classes");
		log.debug(String.format("Label: %d, Prediction: %d", label, prediction));
		Assert.assertEquals(label, prediction);
	}

	@Test
	public void testClassesPredictionInputIntegerObjectArray() {
		log.debug("MNIST classes prediction - input image as 2d Integer object array");
		int label = labels[3];
		Integer[][] image = (Integer[][]) ArrayUtil.convertArrayType(images[3], Integer.class);
		int prediction = model.in("image", image).out("classes").run().getInt("classes");
		log.debug(String.format("Label: %d, Prediction: %d", label, prediction));
		Assert.assertEquals(label, prediction);
	}

	@Test
	public void testClassesPredictionInputFloatObjectArray() {
		log.debug("MNIST classes prediction - input image as 2d Float object array");
		int label = labels[4];
		Float[][] image = (Float[][]) ArrayUtil.convertArrayType(images[4], Float.class);
		int prediction = model.in("image", image).out("classes").run().getInt("classes");
		log.debug(String.format("Label: %d, Prediction: %d", label, prediction));
		Assert.assertEquals(label, prediction);
	}

	@Test
	public void testClassesPredictionInputLongArray() {
		log.debug("MNIST classes prediction - input image as 2d primitive long array");
		int label = labels[5];
		long[][] image = (long[][]) ArrayUtil.convertArrayType(images[5], long.class);
		int prediction = model.in("image", image).out("classes").run().getInt("classes");
		log.debug(String.format("Label: %d, Prediction: %d", label, prediction));
		Assert.assertEquals(label, prediction);
	}

	@Test
	public void testClassesPredictionInputLongObjectArray() {
		log.debug("MNIST classes prediction - input image as 2d Long object array");
		int label = labels[6];
		Long[][] image = (Long[][]) ArrayUtil.convertArrayType(images[6], Long.class);
		int prediction = model.in("image", image).out("classes").run().getInt("classes");
		log.debug(String.format("Label: %d, Prediction: %d", label, prediction));
		Assert.assertEquals(label, prediction);
	}

}
