package com.ibm.stc.tf.mnist;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.deeplearning4j.datasets.mnist.MnistManager;
import org.tensorflow.DataType;
import org.tensorflow.Graph;
import org.tensorflow.Operation;
import org.tensorflow.Output;
import org.tensorflow.SavedModelBundle;
import org.tensorflow.Session;
import org.tensorflow.Tensor;
import org.tensorflow.framework.MetaGraphDef;
import org.tensorflow.framework.SignatureDef;

/**
 * Try an MNIST saved model created in Tensorflow from Java.
 * 
 * For more Python and model information, see info.txt.
 * 
 */
public class MNISTExample {

	public static final String MNIST_DATA_DIR = "/tmp/mnist_data/";
	public static final String MNIST_SAVED_MODEL_DIR = "./model/";

	public static final String TRAIN_IMAGES = "train-images-idx3-ubyte";
	public static final String TRAIN_LABELS = "train-labels-idx1-ubyte";
	public static final String TEST_IMAGES = "t10k-images-idx3-ubyte";
	public static final String TEST_LABELS = "t10k-labels-idx1-ubyte";

	public static void main(String[] args) {
		try {

			String savedModelDir = MNIST_SAVED_MODEL_DIR; // + "1521072365";
			SavedModelBundle savedModel = SavedModelBundle.load(savedModelDir, "serve");

			// MnistManager trainingManager = getTrainingManager();
			// trainingManager.setCurrent(0);
			// int label = trainingManager.readLabel();
			// System.out.println("Label: " + label);
			// int[][] image = trainingManager.readImage();
			// displayImageAsText(image);
			// displayImage(image);

			MnistManager testManager = getTestManager();
			testManager.setCurrent(0);
			int label = testManager.readLabel();
			System.out.println("Label: " + label);
			int[][] iImage = testManager.readImage();
			displayImageAsText(iImage);
			displayImage(iImage);

			float[][] image = iToF(iImage);

			Graph g = savedModel.graph();
			Iterator<Operation> operations = g.operations();
			while (operations.hasNext()) {
				Operation op = operations.next();
				System.out.println("OP:" + op);
			}

			byte[] metaGraphDefBytes = savedModel.metaGraphDef();
			MetaGraphDef mgd = MetaGraphDef.parseFrom(metaGraphDefBytes);
			Map<String, SignatureDef> signatureDefMap = mgd.getSignatureDefMap();
			Set<Entry<String, SignatureDef>> entries = signatureDefMap.entrySet();
			for (Entry<String, SignatureDef> entry : entries) {
				System.out.println("ENTRY:" + entry);
			}

		} catch (Throwable t) {
			System.out.println(t);
		}
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

	public static float[][] iToF(int[][] image) {
		float[][] fImage = new float[image.length][image[0].length];
		for (int r = 0; r < image.length; r++) {
			for (int c = 0; c < image[0].length; c++) {
				fImage[r][c] = image[r][c];
			}
		}
		return fImage;
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
