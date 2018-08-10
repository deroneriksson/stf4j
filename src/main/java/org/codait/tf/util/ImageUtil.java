package org.codait.tf.util;

import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * Utility class related to images.
 *
 */
public class ImageUtil {

	/**
	 * Display a BufferedImage to the screen.
	 * 
	 * @param bi
	 *            the BufferedImage object
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

}
