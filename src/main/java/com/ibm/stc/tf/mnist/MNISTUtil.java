package com.ibm.stc.tf.mnist;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class MNISTUtil {

	public static final String TEST_IMAGES = "/tmp/mnist_data/t10k-images-idx3-ubyte";
	public static final String TEST_LABELS = "/tmp/mnist_data/t10k-labels-idx1-ubyte";

	public static void main(String[] args) throws IOException {
		int[] labels = getLabels(TEST_LABELS);
		System.out.println("LABEL:" + labels[0]);
		int[][][] images = getImages(TEST_IMAGES);
		MNISTExample.displayImage(images[0]);
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
	 * Obtain images from MNIST image data file. The images are returned as a
	 * 3-dimensional int array, where dimension 1 is the image number, dimension
	 * 2 is the rows, and dimension 3 is the columns. The pixel represents a
	 * grayscale int value.
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

}
