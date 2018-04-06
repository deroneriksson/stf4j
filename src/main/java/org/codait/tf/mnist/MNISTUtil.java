package org.codait.tf.mnist;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class MNISTUtil {

	public static final String TEST_IMAGES = "./mnist_data/t10k-images-idx3-ubyte";
	public static final String TEST_LABELS = "./mnist_data/t10k-labels-idx1-ubyte";

	public static void main(String[] args) throws IOException {
		int[] labels = getLabels(TEST_LABELS);
		System.out.println("LABEL:" + labels[0]);
		int[][][] images = getImages(TEST_IMAGES);
		displayImage(images[0]);
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

	public static void displayImageAsText(float[][] image) {
		int[][] iImage = fToI(image);
		displayImageAsText(iImage);
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

	public static int[][] fToI(float[][] image) {
		int[][] iImage = new int[image.length][image[0].length];
		for (int r = 0; r < image.length; r++) {
			for (int c = 0; c < image[0].length; c++) {
				iImage[r][c] = (int) image[r][c];
			}
		}
		return iImage;
	}

	public static float[][][] f2ToF3(float[][] image) {
		float[][][] fImage = new float[1][image.length][image[0].length];
		for (int r = 0; r < image.length; r++) {
			for (int c = 0; c < image[0].length; c++) {
				fImage[0][r][c] = image[r][c];
			}
		}
		return fImage;
	}

	public static void displayImage(int[][] image) {
		BufferedImage bi = iToBuff(image);
		displayBufferedImage(bi);
	}

	public static void displayImage(float[][] image) {
		int[][] iImage = fToI(image);
		displayImage(iImage);
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
