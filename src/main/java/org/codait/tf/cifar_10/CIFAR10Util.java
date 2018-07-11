package org.codait.tf.cifar_10;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class CIFAR10Util {

	public static final String TEST_BATCH_BIN = "./cifar10_data/cifar-10-batches-bin/test_batch.bin";

	public static final String[] classes = new String[] { "airplane", "automobile", "bird", "cat", "deer", "dog",
			"frog", "horse", "ship", "truck" };

	// see https://www.cs.toronto.edu/~kriz/cifar.html for format info
	public static void main(String[] args) throws IOException {
		int[] labels = getLabels(TEST_BATCH_BIN);
		System.out.println("class: " + classes[labels[6]]);
		System.out.println("class: " + classes[labels[7]]);
		System.out.println("class: " + classes[labels[8]]);
		System.out.println("class: " + classes[labels[9]]);
		System.out.println("class: " + classes[labels[10]]);
		float[][][][] images = getImages(TEST_BATCH_BIN, true);
		displayImage(images[6]);
		displayImage(images[7]);
		displayImage(images[8]);
		displayImage(images[9]);
		displayImage(images[10]);

	}

	/**
	 * Obtain labels from CIFAR-10 binary data file.
	 * 
	 * @param batchBinFile
	 *            CIFAR-10 binary data file
	 * @return Labels (valued 0 through 9) as an int array
	 * @throws IOException
	 *             if problem occurs reading binary data file
	 */
	public static int[] getLabels(String batchBinFile) throws IOException {
		byte[] b = Files.readAllBytes(Paths.get(batchBinFile));
		int[] labels = new int[b.length / 3073];
		for (int i = 0; i < labels.length; i++) {
			labels[i] = b[i * 3073];
		}
		return labels;
	}

	/**
	 * Obtain images from CIFAR-10 binary data file. If channelRowsCols is true, the images are returned as a
	 * 4-dimensional float array, where dimension 1 is the image number, dimension 2 is the channel (0=R, 1=G, 2=B),
	 * dimension 3 is the rows, and dimension 4 is the columns. If channelRowsCols is false the images are returned as a
	 * 4-dimensional float array, where dimension 1 is the image number, dimension 2 is the rows, dimension 3 is the
	 * columns, and dimension 4 is the channel (0=R, 1=G, 2=B).
	 * 
	 * @param batchBinFile
	 *            CIFAR-10 binary data file
	 * @param channelRowsCols
	 *            if true, return images[numImages][channel][rows][cols]. if false, return
	 *            images[numImages][rows][cols][channel].
	 * @return Images as a 4-dimensional float array
	 * @throws IOException
	 *             if problem occurs reading binary data file
	 */
	public static float[][][][] getImages(String batchBinFile, boolean channelRowsCols) throws IOException {
		byte[] b = Files.readAllBytes(Paths.get(batchBinFile));
		float[][][][] images = null;
		if (channelRowsCols) {
			images = new float[b.length / 3073][3][32][32];
			for (int i = 0; i < images.length; i++) {
				for (int j = 0; j < 3072; j++) {
					images[i][j / 1024][j % 1024 / 32][j % 1024 % 32] = b[i * 3073 + j + 1] & 0xFF;
				}
			}
		} else {
			images = new float[b.length / 3073][32][32][3];
			for (int i = 0; i < images.length; i++) {
				for (int j = 0; j < 3072; j++) {
					images[i][j % 1024 / 32][j % 1024 % 32][j / 1024] = b[i * 3073 + j + 1] & 0xFF;
				}
			}
		}
		return images;
	}

	/**
	 * Obtain images from CIFAR-10 binary data file. The images are returned as a 4-dimensional float array, where
	 * dimension 1 is the image number, dimension 2 is the channel (0=R, 1=G, 2=B), dimension 3 is the rows, and
	 * dimension 4 is the columns.
	 * 
	 * @param batchBinFile
	 *            CIFAR-10 binary data file
	 * @return Images as a 4-dimensional float array
	 * @throws IOException
	 *             if problem occurs reading binary data file
	 */
	public static float[][][][] getImages(String batchBinFile) throws IOException {
		return getImages(batchBinFile, true);
	}

	/**
	 * Convert a 3-dimension float array image to a BufferedImage. For the input array, the dimension 1 is the channel
	 * (0=R, 1=G, 2=B), dimension 2 is the rows, and dimension 3 is the columns.
	 * 
	 * @param i
	 *            Image as a 3-dimensional float array
	 * @return BufferedImage representation of the image
	 */
	public static BufferedImage f3ToBuff(float[][][] i) {
		int cols = i[0][0].length;
		int rows = i[0].length;

		int[][] img = new int[rows][cols];

		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < cols; c++) {
				img[r][c] = (int) (i[0][r][c] * 256 * 256 + i[1][r][c] * 256 + i[2][r][c]);
			}
		}

		BufferedImage bi = new BufferedImage(cols, rows, BufferedImage.TYPE_INT_RGB);
		for (int y = 0; y < rows; y++) {
			int[] row = img[y];
			bi.setRGB(0, y, cols, 1, row, 0, 1);
		}
		return bi;
	}

	/**
	 * Convert a 3-dimension float array image to a BufferedImage. For the input array, the dimension 1 is the rows,
	 * dimension 2 is the columns, and dimension 3 is the channel (0=R, 1=G, 2=B).
	 * 
	 * @param i
	 *            Image as a 3-dimensional float array
	 * @return BufferedImage representation of the image
	 */
	public static BufferedImage f3RowsColsChannelToBuff(float[][][] i) {
		int cols = i[0].length;
		int rows = i.length;

		int[][] img = new int[rows][cols];

		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < cols; c++) {
				img[r][c] = (int) (i[r][c][0] * 256 * 256 + i[r][c][1] * 256 + i[r][c][2]);
			}
		}

		BufferedImage bi = new BufferedImage(cols, rows, BufferedImage.TYPE_INT_RGB);
		for (int y = 0; y < rows; y++) {
			int[] row = img[y];
			bi.setRGB(0, y, cols, 1, row, 0, 1);
		}
		return bi;
	}

	/**
	 * Display an image to the screen. The input image has 3 dimensions, where dimension 1 is the channel (0=R, 1=G,
	 * 2=B), dimension 2 is the rows, and dimension 3 is the columns.
	 * 
	 * @param image
	 *            Image as a 3-dimensional float array
	 */
	public static void displayImage(float[][][] image) {
		BufferedImage bi = f3ToBuff(image);
		displayBufferedImage(bi);
	}

	/**
	 * Display an image to the screen. The input image has 3 dimensions, where dimension 1 is the rows, dimension 2 is
	 * the columns, and dimension 3 is the channel (0=R, 1=G, 2=B).
	 * 
	 * @param image
	 *            Image as a 3-dimensional float array
	 */
	public static void displayImageRowsColsChannel(float[][][] image) {
		BufferedImage bi = f3RowsColsChannelToBuff(image);
		displayBufferedImage(bi);
	}

	/**
	 * Display a BufferedImage to the screen.
	 * 
	 * @param bi
	 *            BufferedImage representation of the image
	 */
	public static void displayBufferedImage(BufferedImage bi) {
		JFrame jframe = new JFrame();
		JLabel jlabel = new JLabel(new ImageIcon(bi));
		jframe.getContentPane().add(jlabel);
		jframe.setSize(bi.getWidth() + 50, bi.getHeight() + 50);
		jframe.setVisible(true);
		jframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

}
