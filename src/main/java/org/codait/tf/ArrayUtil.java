package org.codait.tf;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class ArrayUtil {

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
	 * Convert an array from one type to another, where the destination type is
	 * specified by the destType parameter.
	 * 
	 * @param orig
	 *            the original array
	 * @param destType
	 *            the type (class) that the original array should be converted
	 *            to
	 * @return the resulting array
	 */
	public static Object convertArrayType(Object orig, Class<?> destType) {
		int[] dimensions = getArrayDimensions(orig);
		Object dest = Array.newInstance(destType, dimensions);
		copyArrayVals(orig, dest);
		return dest;
	}

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
				} else {
					Array.set(dest, i, v);
				}
			}
		}
	}

	/**
	 * Convert individual 2d int arrays with same dimensions to a single 3d int
	 * array.
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
	 *            long array
	 * @return int array
	 */
	public static int[] lToI(long[] lArray) {
		int[] iArray = new int[lArray.length];
		for (int i = 0; i < lArray.length; i++) {
			iArray[i] = (int) lArray[i];
		}
		return iArray;
	}

	public static int maxIndex(float[] f) {
		int maxIndex = 0;
		float maxValue = 0.0f;
		for (int i = 0; i < f.length; i++) {
			if (f[i] > maxValue) {
				maxIndex = i;
				maxValue = f[i];
			}
		}
		return maxIndex;
	}

	public static int[] maxIndices(float[][] f) {
		int[] maxIndices = new int[f.length];
		for (int i = 0; i < f.length; i++) {
			maxIndices[i] = maxIndex(f[i]);
		}
		return maxIndices;
	}

	public static int maxIndex(double[] d) {
		int maxIndex = 0;
		double maxValue = 0.0d;
		for (int i = 0; i < d.length; i++) {
			if (d[i] > maxValue) {
				maxIndex = i;
				maxValue = d[i];
			}
		}
		return maxIndex;
	}

	public static int[] maxIndices(double[][] d) {
		int[] maxIndices = new int[d.length];
		for (int i = 0; i < d.length; i++) {
			maxIndices[i] = maxIndex(d[i]);
		}
		return maxIndices;
	}
}
