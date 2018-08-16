package org.codait.tf.util;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

/**
 * Utility class for dealing with CIFAR-10 data in Java. See https://www.cs.toronto.edu/~kriz/cifar.html for format
 * information.
 * 
 * Examples:
 * 
 * <pre>
 * int[] labels = CIFAR10Util.getLabels(TEST_BATCH_BIN);
 * System.out.println("class: " + classes[labels[0]]);
 * System.out.println("class: " + classes[labels[1]]);
 * System.out.println("class: " + classes[labels[2]]);
 * System.out.println("class: " + classes[labels[3]]);
 * System.out.println("class: " + classes[labels[4]]);
 * 
 * float[][][][] images = CIFAR10Util.getImages(TEST_BATCH_BIN, DimOrder.ROWS_COLS_CHANNELS);
 * ImageUtil.displayImage(images[0]);
 * ImageUtil.displayImage(images[1]);
 * ImageUtil.displayImage(images[2]);
 * ImageUtil.displayImage(images[3]);
 * ImageUtil.displayImage(images[4]);
 * 
 * float[][][][] preprocessedImages = CIFAR10Util.getPreprocessedImages(TEST_BATCH_BIN, DimOrder.ROWS_COLS_CHANNELS);
 * float[][][] preprocessedImage = preprocessedImages[0];
 * System.out.println(Arrays.deepToString(preprocessedImage));
 * 
 * float[][][] cat = CIFAR10Util.getScaledDownImage("images/cat.jpg", DimOrder.ROWS_COLS_CHANNELS);
 * ImageUtil.displayImage(cat);
 * float[][][] dog = CIFAR10Util.getScaledDownImage("images/dog.png", DimOrder.ROWS_COLS_CHANNELS);
 * ImageUtil.displayImage(dog);
 * </pre>
 */
public class CIFAR10Util {

	public static enum DimOrder {
		ROWS_COLS_CHANNELS, CHANNELS_ROWS_COLS
	}

	public static final String TEST_BATCH_BIN = "./cifar10_data/cifar-10-batches-bin/test_batch.bin";

	public static final String[] classes = new String[] { "airplane", "automobile", "bird", "cat", "deer", "dog",
			"frog", "horse", "ship", "truck" };;

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
}
