package org.codait.tf.mnist;

import java.io.IOException;

import org.codait.tf.TFModel;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.tensorflow.Tensor;

public class MNISTTest {

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
		int label = labels[0];
		int prediction = model.in("image", images[0]).out("classes").run().getInt("classes");
		Assert.assertEquals(label, prediction);
	}

	@Test
	public void testClassesPredictionInputFloatArray() {
		int label = labels[1];
		float[][] image = MNISTUtil.iToF(images[1]);
		int prediction = model.in("image", image).out("classes").run().getInt("classes");
		Assert.assertEquals(label, prediction);
	}

	@Test
	public void testClassesPredictionInputTensor() {
		int label = labels[2];
		float[][] image = MNISTUtil.iToF(images[2]);
		Tensor<Float> tensor = Tensor.create(image, Float.class);
		int prediction = model.in("image", tensor).out("classes").run().getInt("classes");
		Assert.assertEquals(label, prediction);
	}

	@Test
	public void testClassesPredictionInputIntegerObjectArray() {
		int label = labels[3];
		Integer[][] image = MNISTUtil.iToIO(images[3]);
		int prediction = model.in("image", image).out("classes").run().getInt("classes");
		Assert.assertEquals(label, prediction);
	}

	// java.lang.IllegalArgumentException: cannot create non-scalar Tensors from
	// arrays of boxed values
	// @Test(expected = IllegalArgumentException.class)
	@Test
	public void testClassesPredictionInputFloatObjectArray() {
		int label = labels[4];
		Float[][] image = MNISTUtil.iToFO(images[4]);
		int prediction = model.in("image", image).out("classes").run().getInt("classes");
		Assert.assertEquals(label, prediction);
	}

}
