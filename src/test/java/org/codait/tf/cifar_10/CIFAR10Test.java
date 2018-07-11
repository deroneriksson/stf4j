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
		int imageNum = 0;
		float[][][] image = images[imageNum];
		int label = labels[imageNum];

		int prediction = model.in("input", image).out("classes").run().getInt("classes");

		Assert.assertTrue(isClassificationCorrect(label, prediction, imageNum));
	}

	@Test
	public void cifarMultiImageInputClassesOutput() {
		log.debug("CIFAR10 - input images as 4d primitive float array, output classes");
		int index = 0;
		int size = 128;
		log.debug(String.format("Image batch index: %d, size: %d", index, size));
		float[][][][] imageBatch = getImageBatch(index, size);
		int[] labels = getLabelBatch(index, size);
		int[] imageNums = getImageNumBatch(index, size);

		int[] predictions = model.in("input", imageBatch).out("classes").run().getIntArray("classes");

		float accuracy = computeAccuracy(labels, predictions, imageNums);
		evaluateAccuracy(accuracy, 0.80f);

	}

	private void evaluateAccuracy(float accuracy, float acceptableAccuracy) {
		if (accuracy < acceptableAccuracy) {
			String message = String.format(
					"Failure, accuracy (%5.2f%%) must be greater or equal to acceptable accuracy (%5.2f%%)",
					accuracy * 100, acceptableAccuracy * 100);
			log.debug(message);
			Assert.fail(message);
		} else {
			String message = String.format(
					"Success, accuracy (%5.2f%%) great than or equal to acceptable accuracy (%5.2f%%)", accuracy * 100,
					acceptableAccuracy * 100);
			log.debug(message);
		}
	}

	private boolean isClassificationCorrect(long label, long prediction, int imageNum) {
		if (label == prediction) {
			log.debug(String.format("Success, image #%d label %d (%s) equals prediction %d (%s)", imageNum, label,
					CIFAR10Util.classes[(int) label], prediction, CIFAR10Util.classes[(int) prediction]));
			return true;
		} else {
			log.debug(String.format("Failure, image #%d label %d (%s) does not equal prediction %d (%s)", imageNum,
					label, CIFAR10Util.classes[(int) label], prediction, CIFAR10Util.classes[(int) prediction]));
			return false;
		}
	}

	private float computeAccuracy(int[] labels, int[] predictions, int[] imageNums) {
		int numCorrectPredictions = 0;
		for (int i = 0; i < labels.length; i++) {
			long label = labels[i];
			long prediction = predictions[i];
			int imageNum = imageNums[i];
			if (isClassificationCorrect(label, prediction, imageNum)) {
				numCorrectPredictions++;
			}
		}
		return (float) numCorrectPredictions / predictions.length;
	}

	private float[][][][] getImageBatch(int startingIndex, int batchSize) {
		float[][][][] imageBatch = new float[batchSize][32][32][3];
		for (int i = startingIndex; i < startingIndex + batchSize; i++) {
			imageBatch[i - startingIndex] = images[i];
		}
		return imageBatch;
	}

	private int[] getLabelBatch(int startingIndex, int batchSize) {
		int[] labelBatch = new int[batchSize];
		for (int i = startingIndex; i < startingIndex + batchSize; i++) {
			labelBatch[i - startingIndex] = labels[i];
		}
		return labelBatch;
	}

	private int[] getImageNumBatch(int startingIndex, int batchSize) {
		int[] imageNumBatch = new int[batchSize];
		for (int i = startingIndex; i < startingIndex + batchSize; i++) {
			imageNumBatch[i - startingIndex] = i;
		}
		return imageNumBatch;
	}
}
