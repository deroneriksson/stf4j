package com.ibm.stc.tf.mnist;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.deeplearning4j.datasets.mnist.MnistManager;

/**
 * Create an MNIST model in TensorFlow and try it from Java.
 * 
 * TensorFlow Python script location:
 * https://github.com/tensorflow/models/blob/master/official/mnist/mnist.py
 * 
 * Model created using: mnist.py --export_dir /tmp/mnist_saved_model
 * 
 * Model information: cd tensorflow/bazel-bin/tensorflow/python/tools
 * ./saved_model_cli show --dir /tmp/mnist_saved_model/1521072365/
 * 
 * The given SavedModel contains the following tag-sets: serve
 * 
 */
public class MNISTExample {

	public static final String MNIST_DATA_DIR = "/tmp/mnist_data/";

	public static final String TRAIN_IMAGES = "train-images-idx3-ubyte";
	public static final String TRAIN_LABELS = "train-labels-idx1-ubyte";
	public static final String TEST_IMAGES = "t10k-images-idx3-ubyte";
	public static final String TEST_LABELS = "t10k-labels-idx1-ubyte";

	public static void main(String[] args) throws IOException {

		MnistManager trainingManager = getTrainingManager();
		trainingManager.setCurrent(0);
		int label = trainingManager.readLabel();
		System.out.println("Label: " + label);
		int[][] image = trainingManager.readImage();
		displayImageAsText(image);
		displayImage(image);

		MnistManager testManager = getTestManager();
		testManager.setCurrent(0);
		label = trainingManager.readLabel();
		System.out.println("Label: " + label);
		image = trainingManager.readImage();
		displayImageAsText(image);
		displayImage(image);

	}

	public static MnistManager getTrainingManager() throws IOException {
		String trainingImages = MNIST_DATA_DIR + TRAIN_IMAGES;
		String trainingLabels = MNIST_DATA_DIR + TRAIN_LABELS;
		if (!new File(trainingImages).exists()) {
			System.out.println("'" + trainingImages + "' can't be found");
			System.exit(-1);
		}
		if (!new File(trainingLabels).exists()) {
			System.out.println("'" + trainingLabels + "' can't be found");
			System.exit(-1);
		}
		return new MnistManager(trainingImages, trainingLabels);
	}

	public static MnistManager getTestManager() throws IOException {
		String testImages = MNIST_DATA_DIR + TEST_IMAGES;
		String testLabels = MNIST_DATA_DIR + TEST_LABELS;
		if (!new File(testImages).exists()) {
			System.out.println("'" + testImages + "' can't be found");
			System.exit(-1);
		}
		if (!new File(testLabels).exists()) {
			System.out.println("'" + testLabels + "' can't be found");
			System.exit(-1);
		}
		return new MnistManager(testImages, testLabels, 10000);
	}

	public static void displayImageAsText(int[][] image) {
		StringBuilder sb = new StringBuilder();
		for (int r = 0; r < image.length; r++) {
			for (int c = 0; c < image[0].length; c++) {
				sb.append(String.format("%3d ", image[r][c]));
			}
			sb.append("\n");
		}
		System.out.println(sb.toString());
	}

	public static void displayImage(int[][] image) {
		BufferedImage bi = iToBuff(image);
		displayBufferedImage(bi);
	}

	public static void displayBufferedImage(BufferedImage bi) {
		JFrame jframe = new JFrame();
		JLabel jlabel = new JLabel(new ImageIcon(bi));
		jframe.getContentPane().add(jlabel);
		jframe.setSize(bi.getWidth() + 50, bi.getHeight() + 50);
		jframe.setVisible(true);
		jframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	public static BufferedImage iToBuff(int[][] i) {
		int cols = i[0].length;
		int rows = i.length;

		// convert pixels to rgb colorspace values
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < cols; c++) {
				i[r][c] = 255 - i[r][c]; // invert colors
				i[r][c] = 256 * 256 * i[r][c] + 256 * i[r][c] + i[r][c];
			}
		}

		BufferedImage bi = new BufferedImage(cols, rows, BufferedImage.TYPE_INT_RGB);
		for (int y = 0; y < rows; y++) {
			int[] row = i[y];
			bi.setRGB(0, y, cols, 1, row, 0, 1);
		}
		return bi;
	}

}
