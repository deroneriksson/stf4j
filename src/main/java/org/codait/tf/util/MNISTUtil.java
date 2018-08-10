package org.codait.tf.util;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Utility class for dealing with MNIST data in Java.
 *
 */
public class MNISTUtil {

	public static final String TEST_IMAGES = "./mnist_data/t10k-images-idx3-ubyte";
	public static final String TEST_LABELS = "./mnist_data/t10k-labels-idx1-ubyte";

	public static void main(String[] args) throws IOException {
		int[] labels = getLabels(TEST_LABELS);
		System.out.println("LABEL:" + labels[0]);
		int[][][] images = getImages(TEST_IMAGES);
		displayMNISTImage(images[0]);
		displayMNISTImageAsText(images[0]);
	}

	/**
	 * Obtain labels from MNIST label data file.
	 * 
	 * @param labelFile
	 *            MNIST label data file
	 * @return Labels (valued 0 through 9) as an int array
	 * @throws IOException
	 *             if problem occurs reading data file
	 */
	public static int[] getLabels(String labelFile) throws IOException {
		byte[] b = Files.readAllBytes(Paths.get(labelFile));
		int[] labels = new int[b.length - 8];
		for (int i = 0; i < labels.length; i++) {
			labels[i] = b[i + 8];
		}
		return labels;
	}

	/**
	 * Obtain images from MNIST image data file. The images are returned as a 3-dimensional int array, where dimension 1
	 * is the image number, dimension 2 is the rows, and dimension 3 is the columns. The pixel represents a grayscale
	 * int value.
	 * 
	 * @param imageFile
	 *            MNIST image data file
	 * @return Images as a 3-dimensional int array
	 * @throws IOException
	 *             if problem occurs reading data file
	 */
	public static int[][][] getImages(String imageFile) throws IOException {
		byte[] b = Files.readAllBytes(Paths.get(imageFile));
		int[][][] images = new int[(b.length - 16) / 784][28][28];
		for (int i = 0; i < b.length - 16; i++) {
			images[i / 784][i % 784 / 28][i % 784 % 28] = b[i + 16] & 0xFF;
		}
		return images;
	}

	/**
	 * Obtain an MNIST image as text int values from 0 to 255, where 0 represents white and 255 represents black.
	 * 
	 * @param image
	 *            MNIST image as a 2-dimensional int array
	 */
	public static String mnistImageAsText(int[][] image) {
		StringBuilder sb = new StringBuilder();
		for (int r = 0; r < image.length; r++) {
			for (int c = 0; c < image[0].length; c++) {
				sb.append(String.format("%3d ", image[r][c]));
			}
			sb.append("\n");
		}
		return sb.toString();
	}

	/**
	 * Display an MNIST image as text int values from 0 to 255, where 0 represents white and 255 represents black.
	 * 
	 * @param image
	 *            MNIST image as a 2-dimensional int array
	 */
	public static void displayMNISTImageAsText(int[][] image) {
		System.out.println(mnistImageAsText(image));
	}

	/**
	 * Display an MNIST image to the screen as an image.
	 * 
	 * @param image
	 *            MNIST image as a 2-dimensional int array
	 */
	public static void displayMNISTImage(int[][] image) {
		BufferedImage bi = mnistToBuff(image);
		ImageUtil.displayBufferedImage(bi);
	}

	/**
	 * Convert an MNIST image represented as a 2-dimensional int array to a BufferedImage.
	 * 
	 * @param image
	 *            MNIST image as a 2-dimensional int array
	 * @return the BufferedImage object
	 */
	public static BufferedImage mnistToBuff(int[][] image) {
		int cols = image[0].length;
		int rows = image.length;

		int[][] j = new int[rows][cols];

		// convert pixels to rgb colorspace values
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < cols; c++) {
				j[r][c] = 255 - image[r][c]; // invert colors
				j[r][c] = 256 * 256 * j[r][c] + 256 * j[r][c] + j[r][c];
			}
		}

		BufferedImage bi = new BufferedImage(cols, rows, BufferedImage.TYPE_INT_RGB);
		for (int y = 0; y < rows; y++) {
			int[] row = j[y];
			bi.setRGB(0, y, cols, 1, row, 0, 1);
		}
		return bi;
	}

}
