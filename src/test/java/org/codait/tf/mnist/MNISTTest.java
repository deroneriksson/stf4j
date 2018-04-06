package org.codait.tf.mnist;

import java.io.IOException;

import org.codait.tf.TFModel;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

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
	public void testClassesPredictionImage0() {
		int label = labels[0];
		int prediction = model.in("image", images[0]).out("classes").run().getInt("classes");
		Assert.assertEquals(label, prediction);
	}

}
