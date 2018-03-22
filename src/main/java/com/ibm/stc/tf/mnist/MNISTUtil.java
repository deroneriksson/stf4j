package com.ibm.stc.tf.mnist;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class MNISTUtil {

	public static final String TEST_IMAGES = "/tmp/mnist_data/t10k-images-idx3-ubyte";
	public static final String TEST_LABELS = "/tmp/mnist_data/t10k-labels-idx1-ubyte";

	public static void main(String[] args) throws IOException {
		int[] labels = getTestLabels();
		System.out.println("LABEL:" + labels[0]);
		int[][][] images = getTestImages();
		MNISTExample.displayImage(images[0]);
	}

	public static int[] getTestLabels() throws IOException {
		byte[] b = Files.readAllBytes(Paths.get(TEST_LABELS));
		int[] labels = new int[b.length - 8];
		for (int i = 0; i < labels.length; i++) {
			labels[i] = b[i + 8];
		}
		return labels;
	}

	public static int[][][] getTestImages() throws IOException {
		byte[] b = Files.readAllBytes(Paths.get(TEST_IMAGES));
		int[][][] images = new int[(b.length - 16) / 784][28][28];
		for (int i = 0; i < b.length - 16; i++) {
			images[i / 784][i % 784 / 28][i % 784 % 28] = b[i + 16];
		}
		return images;
	}

}
