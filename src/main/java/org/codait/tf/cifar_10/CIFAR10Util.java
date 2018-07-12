package org.codait.tf.cifar_10;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class CIFAR10Util {

	public static final String TEST_BATCH_BIN = "./cifar10_data/cifar-10-batches-bin/test_batch.bin";

	public static final String[] classes = new String[] { "airplane", "automobile", "bird", "cat", "deer", "dog",
			"frog", "horse", "ship", "truck" };

	public static enum DimOrder {
		ROWS_COLS_CHANNELS, CHANNELS_ROWS_COLS
	};

	// see https://www.cs.toronto.edu/~kriz/cifar.html for format info
	public static void main(String[] args) throws IOException {
		int[] labels = getLabels(TEST_BATCH_BIN);
		System.out.println("class: " + classes[labels[6]]);
		System.out.println("class: " + classes[labels[7]]);
		System.out.println("class: " + classes[labels[8]]);
		System.out.println("class: " + classes[labels[9]]);
		System.out.println("class: " + classes[labels[10]]);

		float[][][][] images = getImages(TEST_BATCH_BIN, DimOrder.ROWS_COLS_CHANNELS);
		displayImage(images[6]);
		displayImage(images[7]);
		displayImage(images[8]);
		displayImage(images[9]);
		displayImage(images[10]);

		float[][][][] preprocessedImages = getPreprocessedImages(TEST_BATCH_BIN, DimOrder.ROWS_COLS_CHANNELS);
		float[][][] preprocessedImage = preprocessedImages[0];
		System.out.println(Arrays.deepToString(preprocessedImage));

		float[][][] cat = getScaledDownImage("images/cat.jpg", DimOrder.ROWS_COLS_CHANNELS);
		displayImage(cat);
		float[][][] dog = getScaledDownImage("images/dog.png", DimOrder.ROWS_COLS_CHANNELS);
		displayImage(dog);
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
	 * Obtain images from CIFAR-10 binary data file. If dimOrder is CHANNELS_ROWS_COLS, the images are returned as a
	 * 4-dimensional float array, where dimension 1 is the image number, dimension 2 is the channel (0=R, 1=G, 2=B),
	 * dimension 3 is the rows, and dimension 4 is the columns. If dimOrder is ROWS_COLS_CHANNELS, the images are
	 * returned as a 4-dimensional float array, where dimension 1 is the image number, dimension 2 is the rows,
	 * dimension 3 is the columns, and dimension 4 is the channel (0=R, 1=G, 2=B).
	 * 
	 * @param batchBinFile
	 *            CIFAR-10 binary data file
	 * @param dimOrder
	 *            if CHANNELS_ROWS_COLS, return images[numImages][channel][rows][cols]. if ROWS_COLS_CHANNELS, return
	 *            images[numImages][rows][cols][channel].
	 * @return Images as a 4-dimensional float array
	 * @throws IOException
	 *             if problem occurs reading binary data file
	 */
	public static float[][][][] getImages(String batchBinFile, DimOrder dimOrder) throws IOException {
		byte[] b = Files.readAllBytes(Paths.get(batchBinFile));
		float[][][][] images = null;
		if (dimOrder == DimOrder.CHANNELS_ROWS_COLS) {
			images = new float[b.length / 3073][3][32][32];
			for (int i = 0; i < images.length; i++) {
				for (int j = 0; j < 3072; j++) {
					images[i][j / 1024][j % 1024 / 32][j % 1024 % 32] = b[i * 3073 + j + 1] & 0xFF;
				}
			}
		} else if (dimOrder == DimOrder.ROWS_COLS_CHANNELS) {
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
	 * Convert a 3-dimension float array image to a BufferedImage. For the input array, the dimension 1 is the channel
	 * (0=R, 1=G, 2=B), dimension 2 is the rows, and dimension 3 is the columns.
	 * 
	 * @param i
	 *            Image as a 3-dimensional float array
	 * @return BufferedImage representation of the image
	 */
	public static BufferedImage f3ChannelRowsColsToBuff(float[][][] i) {
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
	 * Display an image to the screen. If the first dimension has a size of 3, the image is assumed to be
	 * [channel][rows][cols]. Otherwise it is assumed to be [rows][cols][channel].
	 * 
	 * @param image
	 *            Image as a 3-dimensional float array
	 */
	public static void displayImage(float[][][] image) {
		BufferedImage bi = null;
		if (image.length == 3) {
			bi = f3ChannelRowsColsToBuff(image);
		} else {
			bi = f3RowsColsChannelToBuff(image);
		}
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

	/**
	 * Preprocess the image, as in the preprocess_image function in cifar10_main.py, which calls
	 * tf.image.per_image_standardization(image). The average is subtracted and this is divided by the adjusted standard
	 * deviation.
	 * 
	 * @param f
	 *            Image as a 3-dimensional float array
	 * @return Preprocessed image as a 3-dimensional float array
	 */
	public static float[][][] preprocessImage(float[][][] f) {
		int x = f.length;
		int y = f[0].length;
		int z = f[0][0].length;
		int numElements = x * y * z;

		// find average
		double sum = 0;
		for (int a = 0; a < x; a++) {
			for (int b = 0; b < y; b++) {
				for (int c = 0; c < z; c++) {
					sum += f[a][b][c];
				}
			}
		}
		double avg = sum / numElements;

		// find std deviation and adjusted std deviation
		double tmp = 0;
		for (int a = 0; a < x; a++) {
			for (int b = 0; b < y; b++) {
				for (int c = 0; c < z; c++) {
					tmp += (Math.pow(f[a][b][c] - avg, 2));
				}
			}
		}
		double stdDev = Math.sqrt(tmp / numElements);
		double adjStdDev = Math.max(stdDev, 1.0 / Math.sqrt(numElements));

		float[][][] f2 = new float[f.length][f[0].length][f[0][0].length];
		for (int a = 0; a < x; a++) {
			for (int b = 0; b < y; b++) {
				for (int c = 0; c < z; c++) {
					f2[a][b][c] = (float) ((f[a][b][c] - avg) / adjStdDev);
				}
			}
		}
		return f2;
	}

	/**
	 * Preprocess a group of images.
	 * 
	 * @param images
	 *            Images as a 4-dimensional float array, where the first dimension is the image number
	 * 
	 * @return The preprocessed images as a 4-dimensional float array
	 */
	public static float[][][][] preprocessImages(float[][][][] images) {
		float[][][][] preprocessedImages = new float[images.length][images[0].length][images[0][0].length][images[0][0][0].length];
		for (int i = 0; i < images.length; i++) {
			preprocessedImages[i] = preprocessImage(images[i]);
		}
		return preprocessedImages;
	}

	/**
	 * Obtain preprocessed images from CIFAR-10 binary data file. If dimOrder is CHANNELS_ROWS_COLS, the images are
	 * returned as a 4-dimensional float array, where dimension 1 is the image number, dimension 2 is the channel (0=R,
	 * 1=G, 2=B), dimension 3 is the rows, and dimension 4 is the columns. If dimOrder is ROWS_COLS_CHANNELS, the images
	 * are returned as a 4-dimensional float array, where dimension 1 is the image number, dimension 2 is the rows,
	 * dimension 3 is the columns, and dimension 4 is the channel (0=R, 1=G, 2=B).
	 * 
	 * @param batchBinFile
	 *            CIFAR-10 binary data file
	 * @param dimOrder
	 *            if CHANNELS_ROWS_COLS, return images[numImages][channel][rows][cols]. if ROWS_COLS_CHANNELS, return
	 *            images[numImages][rows][cols][channel].
	 * @return The preprocessed images as a 4-dimensional float array
	 * @throws IOException
	 *             if problem occurs reading binary data file
	 */
	public static float[][][][] getPreprocessedImages(String batchBinFile, DimOrder dimOrder) throws IOException {
		float[][][][] images = getImages(batchBinFile, dimOrder);
		return preprocessImages(images);
	}

	/**
	 * Obtain a scaled-down image as a 3-dimensional float array.
	 * 
	 * @param imagePath
	 *            Path to an image file
	 * @param dimOrder
	 *            if CHANNELS_ROWS_COLS, return [3][32][32] array. if ROWS_COLS_CHANNELS, return [32][32][3] array.
	 * @return Image as a 3-dimensional float array
	 * @throws IOException
	 *             if problem occurs reading image file
	 */
	public static float[][][] getScaledDownImage(String imagePath, DimOrder dimOrder) throws IOException {
		BufferedImage bi = ImageIO.read(new File(imagePath));
		BufferedImage scaledBi = new BufferedImage(32, 32, bi.getType());

		Graphics2D g = scaledBi.createGraphics();
		g.drawImage(bi, 0, 0, 32, 32, null);
		g.dispose();

		float[][][] im = null;
		if (dimOrder == DimOrder.CHANNELS_ROWS_COLS) {
			im = new float[3][32][32];
			for (int row = 0; row < 32; row++) {
				for (int col = 0; col < 32; col++) {
					int val = scaledBi.getRGB(col, row);
					Color color = new Color(val);
					im[0][row][col] = color.getRed();
					im[1][row][col] = color.getGreen();
					im[2][row][col] = color.getBlue();
				}
			}
		} else if (dimOrder == DimOrder.ROWS_COLS_CHANNELS) {
			im = new float[32][32][3];
			for (int row = 0; row < 32; row++) {
				for (int col = 0; col < 32; col++) {
					int val = scaledBi.getRGB(col, row);
					Color color = new Color(val);
					im[row][col][0] = color.getRed();
					im[row][col][1] = color.getGreen();
					im[row][col][2] = color.getBlue();
				}
			}
		}
		return im;
	}
}
