package org.codait.tf.cifar_10;

import java.io.IOException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.codait.tf.TFModel;
import org.codait.tf.cifar_10.CIFAR10Util.DimOrder;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CIFAR10Test {

	protected static Logger log = LogManager.getLogger(CIFAR10Test.class);

	public static final String CIFAR10_TEST_BATCH_BIN = "./cifar10_data/cifar-10-batches-bin/test_batch.bin";
	public static final String CIFAR10_SAVED_MODEL_DIR = "./cifar10_saved_model/";

	private int[] labels = null;
	private float[][][][] images = null;
	private TFModel model = null;

	@Before
	public void init() throws IOException {
		labels = CIFAR10Util.getLabels(CIFAR10_TEST_BATCH_BIN);
		images = CIFAR10Util.getPreprocessedImages(CIFAR10_TEST_BATCH_BIN, DimOrder.ROWS_COLS_CHANNELS);
		model = new TFModel(CIFAR10_SAVED_MODEL_DIR);
	}

	@After
	public void after() {
	}

	@Test
	public void cifarSingleImageInputClassesOutput() {
		log.debug("CIFAR10 - input image as 3d primitive float array, output classes");
		float[][][] image = images[0];
		int label = labels[0];
		int prediction = model.in("input", image).out("classes").run().getInt("classes");
		displayDebug(label, prediction);
		Assert.assertEquals(label, prediction);
	}

	private void displayDebug(long label, long prediction) {
		log.debug(String.format("Label: %d (%s), Prediction: %d (%s)", label, CIFAR10Util.classes[(int) label],
				prediction, CIFAR10Util.classes[(int) prediction]));
	}

}
