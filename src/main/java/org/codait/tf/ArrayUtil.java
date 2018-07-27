package org.codait.tf;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class ArrayUtil {

	/**
	 * Logger for ArrayUtil
	 */
	protected static Logger log = LogManager.getLogger(ArrayUtil.class);

	/**
	 * Obtain a list of array dimensions based on an input array.
	 * 
	 * @param array
	 *            Input array as as object
	 * @return List of array dimensions
	 */
	public static List<Integer> getArrayDimensionsList(Object array) {
		if (array.getClass().isArray()) {
			List<Integer> dim = new ArrayList<Integer>();
			int length = Array.getLength(array);
			dim.add(length);
			Object v = Array.get(array, 0); // assume all have expected lengths
			List<Integer> dims = getArrayDimensionsList(v);
			if (dims != null) {
				dim.addAll(dims);
			}
			return dim;
		} else {
			return null;
		}

	}

	/**
	 * Obtain dimensions of an array.
	 * 
	 * @param array
	 *            Input array as an object
	 * @return Array dimensions as an array
	 */
	public static int[] getArrayDimensions(Object array) {
		List<Integer> dimList = getArrayDimensionsList(array);
		int[] dimensions = new int[dimList.size()];
		for (int i = 0; i < dimList.size(); i++) {
			dimensions[i] = dimList.get(i);
		}
		return dimensions;
	}

	/**
	 * Convert an array from one type to another, where the destination type is specified by the destType parameter.
	 * 
	 * @param orig
	 *            The original array
	 * @param destType
	 *            The type (class) that the original array should be converted to
	 * @return The resulting array
	 */
	public static Object convertArrayType(Object orig, Class<?> destType) {
		int[] dimensions = getArrayDimensions(orig);
		Object dest = Array.newInstance(destType, dimensions);
		copyArrayVals(orig, dest);
		return dest;
	}

	/**
	 * Copy values from one array to another array with the same shape and perform needed type conversions.
	 * 
	 * @param orig
	 *            The original array
	 * @param dest
	 *            The destination array
	 */
	public static void copyArrayVals(Object orig, Object dest) {
		String o = orig.getClass().getComponentType().getSimpleName();
		String d = dest.getClass().getComponentType().getSimpleName();
		for (int i = 0; i < Array.getLength(orig); i++) {
			Object v = Array.get(orig, i);
			Object vd = Array.get(dest, i);
			if (v.getClass().isArray()) {
				copyArrayVals(v, vd);
			} else {
				if ("int".equals(o) && "Long".equals(d)) {
					Array.set(dest, i, Long.valueOf((int) v));
				} else if ("int".equals(o) && "Float".equals(d)) {
					Array.set(dest, i, Float.valueOf((int) v));
				} else if ("int".equals(o) && "double".equalsIgnoreCase(d)) {
					Array.set(dest, i, Double.valueOf((int) v));
				} else if ("long".equals(o) && "int".equals(d)) {
					Array.set(dest, i, ((Long) v).intValue());
				} else if ("double".equalsIgnoreCase(o) && "float".equals(d)) {
					Array.set(dest, i, Float.valueOf((float) (double) v));
				} else if ("Byte".equals(o) && "byte".equals(d)) {
					Array.set(dest, i, v);
				} else {
					Array.set(dest, i, v);
				}
			}
		}
	}

	/**
	 * Convert individual 2d int arrays with same dimensions to a single 3d int array.
	 * 
	 * @param arrays
	 *            2d int arrays of the same dimensions
	 * @return 3d int array that combines the individual 2d int arrays
	 */
	public static int[][][] convert2dIntArraysTo3dIntArray(int[][]... arrays) {
		int[][][] array = new int[arrays.length][arrays[0].length][arrays[0][0].length];
		for (int i = 0; i < arrays.length; i++) {
			int[][] ar = arrays[i];
			for (int r = 0; r < ar.length; r++) {
				for (int c = 0; c < ar[0].length; c++) {
					array[i][r][c] = ar[r][c];
				}
			}
		}
		return array;
	}

	/**
	 * Convert 1d long array to int array.
	 * 
	 * @param lArray
	 *            Primitive long array
	 * @return Primitive int array
	 */
	public static int[] lToI(long[] lArray) {
		int[] iArray = new int[lArray.length];
		for (int i = 0; i < lArray.length; i++) {
			iArray[i] = (int) lArray[i];
		}
		return iArray;
	}

	/**
	 * Obtain the index at which the maximum value occurs in an array.
	 * 
	 * @param f
	 *            The float array
	 * @return The index at which the maximum value occurs
	 */
	public static int maxIndex(float[] f) {
		int maxIndex = 0;
		float maxValue = Float.MIN_VALUE;
		for (int i = 0; i < f.length; i++) {
			if (f[i] > maxValue) {
				maxIndex = i;
				maxValue = f[i];
			}
		}
		return maxIndex;
	}

	/**
	 * Obtain the indices at which the maximum values occur in the rows of a 2d array.
	 * 
	 * @param f
	 *            The float array
	 * @return The indices at which the maximum row values occur
	 */
	public static int[] maxIndices(float[][] f) {
		int[] maxIndices = new int[f.length];
		for (int i = 0; i < f.length; i++) {
			maxIndices[i] = maxIndex(f[i]);
		}
		return maxIndices;
	}

	/**
	 * Obtain the index at which the maximum value occurs in an array.
	 * 
	 * @param d
	 *            The double array
	 * @return The index at which the maximum value occurs
	 */
	public static int maxIndex(double[] d) {
		int maxIndex = 0;
		double maxValue = Double.MIN_VALUE;
		for (int i = 0; i < d.length; i++) {
			if (d[i] > maxValue) {
				maxIndex = i;
				maxValue = d[i];
			}
		}
		return maxIndex;
	}

	/**
	 * Obtain the indices at which the maximum values occur in the rows of a 2d array.
	 * 
	 * @param d
	 *            The double array
	 * @return The indices at which the maximum row values occur
	 */
	public static int[] maxIndices(double[][] d) {
		int[] maxIndices = new int[d.length];
		for (int i = 0; i < d.length; i++) {
			maxIndices[i] = maxIndex(d[i]);
		}
		return maxIndices;
	}

	/**
	 * Convert a multidimensional (dim) String array to a multidimensional (dim+1) byte array. The multidimensional byte
	 * array will have 1 more dimension than the String array since a String is converted to a byte array.
	 * 
	 * @param s
	 *            The multidimensional String array
	 * @return The multidimensional (dim+1) byte array equivalent of the multidimensional (dim) String array
	 */
	public static Object multidimStringsToMultidimBytes(Object s) {
		int[] sDim = getArrayDimensions(s);
		int[] bDim = Arrays.copyOf(sDim, sDim.length + 1);
		Object b = Array.newInstance(byte.class, bDim);
		copyStringArrayToByteArrayVals(s, b);
		return b;
	}

	/**
	 * Convert String values in a multidimensional (dim) array to byte values in a multidimensional (dim+1) array. The
	 * multidimensional byte array has 1 more dimension than the multidimensional String array since a String is
	 * converted to a byte array.
	 * 
	 * @param orig
	 *            The multidimensional (dim) String array
	 * @param dest
	 *            The multidimensional (dim+1) byte array
	 */
	public static void copyStringArrayToByteArrayVals(Object orig, Object dest) {
		for (int i = 0; i < Array.getLength(orig); i++) {
			Object v = Array.get(orig, i);
			Object vd = Array.get(dest, i);
			if (v.getClass().isArray()) {
				copyStringArrayToByteArrayVals(v, vd);
			} else {
				String s = (String) v;
				byte[] bytes = s.getBytes(StandardCharsets.UTF_8);
				Array.set(dest, i, bytes);
			}
		}
	}
}
