package com.ibm.stc.tf.cifar_10;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class CIFAR10Util {

	public static final String TEST_BATCH_BIN = "/tmp/cifar10_data/cifar-10-batches-bin/test_batch.bin";

	// see https://www.cs.toronto.edu/~kriz/cifar.html for format info
	public static void main(String[] args) throws IOException {
		int[] labels = getLabels(TEST_BATCH_BIN);
		System.out.println("label: " + labels[6]);
		System.out.println("label: " + labels[7]);
		System.out.println("label: " + labels[8]);
		System.out.println("label: " + labels[9]);
		System.out.println("label: " + labels[10]);
		int[][][][] images = getImages(TEST_BATCH_BIN);
		displayImage(images[6]);
		displayImage(images[7]);
		displayImage(images[8]);
		displayImage(images[9]);
		displayImage(images[10]);
	}

	public static int[] getLabels(String batchBinFile) throws IOException {
		byte[] b = Files.readAllBytes(Paths.get(batchBinFile));
		int[] labels = new int[b.length / 3073];
		for (int i = 0; i < labels.length; i++) {
			labels[i] = b[i * 3073];
		}
		return labels;
	}

	public static int[][][][] getImages(String batchBinFile) throws IOException {
		byte[] b = Files.readAllBytes(Paths.get(batchBinFile));
		int[][][][] images = new int[b.length / 3073][3][32][32];
		for (int i = 0; i < images.length; i++) {
			for (int j = 0; j < 3072; j++) {
				images[i][j / 1024][j % 1024 / 32][j % 1024 % 32] = b[i * 3073 + j] & 0xFF;
			}
		}
		return images;
	}

	public static BufferedImage i3ToBuff(int[][][] i) {
		int cols = i[0][0].length;
		int rows = i[0].length;

		int[][] img = new int[rows][cols];

		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < cols; c++) {
				img[r][c] = i[0][r][c] * 256 * 256 + i[1][r][c] * 256 + i[2][r][c];
			}
		}

		BufferedImage bi = new BufferedImage(cols, rows, BufferedImage.TYPE_INT_RGB);
		for (int y = 0; y < rows; y++) {
			int[] row = img[y];
			bi.setRGB(0, y, cols, 1, row, 0, 1);
		}
		return bi;
	}

	public static void displayImage(int[][][] image) {
		BufferedImage bi = i3ToBuff(image);
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

}
