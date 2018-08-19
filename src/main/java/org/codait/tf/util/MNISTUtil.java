// ------------------------------------------------------------------------
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
//
// ------------------------------------------------------------------------

package org.codait.tf.util;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Utility class for dealing with MNIST data in Java.
 *
 * Examples:
 * 
 * <pre>
 * final String TEST_IMAGES = "../stf4j-test-models/mnist_data/t10k-images-idx3-ubyte";
 * final String TEST_LABELS = "../stf4j-test-models/mnist_data/t10k-labels-idx1-ubyte";
 * 
 * int[] labels = MNISTUtil.getLabels(TEST_LABELS);
 * System.out.println("LABEL:" + labels[0]);
 * int[][][] images = MNISTUtil.getImages(TEST_IMAGES);
 * MNISTUtil.displayMNISTImage(images[0]);
 * MNISTUtil.displayMNISTImageAsText(images[0]);
 * </pre>
 */
public class MNISTUtil {

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
	 * Display an MNIST image as text int values from 0 to 255, where 0 represents white and 255 represents black.
	 * 
	 * @param image
	 *            MNIST image as a 2-dimensional int array
	 */
	public static void displayMNISTImageAsText(int[][] image) {
		System.out.println(mnistImageAsText(image));
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
	 * Obtain an MNIST image as text int values from 0 to 255, where 0 represents white and 255 represents black.
	 * 
	 * @param image
	 *            MNIST image as a 2-dimensional int array
	 * @return Image represented as text
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
