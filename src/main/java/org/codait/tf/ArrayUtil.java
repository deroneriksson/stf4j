package org.codait.tf;

import java.lang.reflect.Array;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.tensorflow.Tensor;

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
				} else if ("int".equals(o) && "String".equals(d)) {
					Array.set(dest, i, Integer.toString((int) v));
				} else if ("long".equals(o) && "int".equals(d)) {
					Array.set(dest, i, ((Long) v).intValue());
				} else if ("double".equalsIgnoreCase(o) && "float".equals(d)) {
					Array.set(dest, i, Float.valueOf((float) (double) v));
				} else if ("Byte".equals(o) && "byte".equals(d)) {
					Array.set(dest, i, v);
				} else if ("String".equals(o) && "int".equals(d)) {
					Array.set(dest, i, Integer.valueOf((String) v));
				} else if ("String".equals(o) && "long".equals(d)) {
					Array.set(dest, i, Long.valueOf((String) v));
				} else if ("String".equals(o) && "float".equals(d)) {
					Array.set(dest, i, Float.valueOf((String) v));
				} else if ("String".equals(o) && "double".equals(d)) {
					Array.set(dest, i, Double.valueOf((String) v));
				} else if ("long".equals(o) && "String".equals(d)) {
					Array.set(dest, i, Long.toString((long) v));
				} else if ("float".equals(o) && "String".equals(d)) {
					Array.set(dest, i, Float.toString((float) v));
				} else if ("float".equals(o) && "long".equals(d)) {
					Array.set(dest, i, ((Float) v).longValue());
				} else if ("double".equals(o) && "long".equals(d)) {
					Array.set(dest, i, ((Double) v).longValue());
				} else if ("float".equals(o) && "int".equals(d)) {
					Array.set(dest, i, ((Float) v).intValue());
				} else if ("double".equals(o) && "int".equals(d)) {
					Array.set(dest, i, ((Double) v).intValue());
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
	 * Convert 1d int array to long array.
	 * 
	 * @param iArray
	 *            Primitive int array
	 * @return Primitive long array
	 */
	public static long[] iToL(int[] iArray) {
		long[] lArray = new long[iArray.length];
		for (int i = 0; i < iArray.length; i++) {
			lArray[i] = (long) iArray[i];
		}
		return lArray;
	}

	/**
	 * Convert 1d int array to float array.
	 * 
	 * @param iArray
	 *            Primitive int array
	 * @return Primitive float array
	 */
	public static float[] iToF(int[] iArray) {
		float[] fArray = new float[iArray.length];
		for (int i = 0; i < iArray.length; i++) {
			fArray[i] = (float) iArray[i];
		}
		return fArray;
	}

	/**
	 * Convert 1d long array to float array.
	 * 
	 * @param lArray
	 *            Primitive long array
	 * @return Primitive float array
	 */
	public static float[] lToF(long[] lArray) {
		float[] fArray = new float[lArray.length];
		for (int i = 0; i < lArray.length; i++) {
			fArray[i] = (float) lArray[i];
		}
		return fArray;
	}

	/**
	 * Convert 1d long array to double array.
	 * 
	 * @param lArray
	 *            Primitive long array
	 * @return Primitive double array
	 */
	public static double[] lToD(long[] lArray) {
		double[] dArray = new double[lArray.length];
		for (int i = 0; i < lArray.length; i++) {
			dArray[i] = (double) lArray[i];
		}
		return dArray;
	}

	/**
	 * Convert 1d int array to double array.
	 * 
	 * @param iArray
	 *            Primitive int array
	 * @return Primitive double array
	 */
	public static double[] iToD(int[] iArray) {
		double[] dArray = new double[iArray.length];
		for (int i = 0; i < iArray.length; i++) {
			dArray[i] = (double) iArray[i];
		}
		return dArray;
	}

	/**
	 * Convert {@code Tensor<Long>} to long array.
	 * 
	 * @param tensor
	 *            The Tensor of Long values
	 * @return Primitive long array
	 */
	public static long[] longTensorToLongArray(Tensor<Long> tensor) {
		LongBuffer lb = LongBuffer.allocate(tensor.numElements());
		tensor.writeTo(lb);
		return lb.array();
	}

	/**
	 * Convert {@code Tensor<Integer>} to int array.
	 * 
	 * @param tensor
	 *            The Tensor of Integer values
	 * @return Primitive int array
	 */
	public static int[] intTensorToIntArray(Tensor<Integer> tensor) {
		IntBuffer ib = IntBuffer.allocate(tensor.numElements());
		tensor.writeTo(ib);
		return ib.array();
	}

	/**
	 * Convert {@code Tensor<Float>} to float array.
	 * 
	 * @param tensor
	 *            The Tensor of Float values
	 * @return Primitive float array
	 */
	public static float[] floatTensorToFloatArray(Tensor<Float> tensor) {
		FloatBuffer fb = FloatBuffer.allocate(tensor.numElements());
		tensor.writeTo(fb);
		return fb.array();
	}

	/**
	 * Convert {@code Tensor<Float>} to multidimensional float array.
	 * 
	 * @param tensor
	 *            The Tensor of Float values
	 * @return Multidimensional primitive float array as an Object
	 */
	public static Object floatTensorToMultidimensionalFloatArray(Tensor<Float> tensor) {
		int[] shape = lToI(tensor.shape());
		Object f = Array.newInstance(float.class, shape);
		tensor.copyTo(f);
		return f;
	}

	/**
	 * Convert {@code Tensor<Long>} to multidimensional long array.
	 * 
	 * @param tensor
	 *            The Tensor of Long values
	 * @return Multidimensional primitive long array as an Object
	 */
	public static Object longTensorToMultidimensionalLongArray(Tensor<Long> tensor) {
		int[] shape = lToI(tensor.shape());
		Object l = Array.newInstance(long.class, shape);
		tensor.copyTo(l);
		return l;
	}

	/**
	 * Convert {@code Tensor<String>} to multidimensional String array.
	 * 
	 * @param tensor
	 *            The Tensor of String values
	 * @return Multidimensional String array as an Object
	 */
	public static Object stringTensorToMultidimensionalStringArray(Tensor<String> tensor) {
		int[] sShape = lToI(tensor.shape());
		int[] bShape = Arrays.copyOf(sShape, sShape.length + 1);
		Object b = Array.newInstance(byte.class, bShape);
		tensor.copyTo(b);
		Object s = multidimBytesToMultidimStrings(b);
		return s;
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
	protected static void copyStringArrayToByteArrayVals(Object orig, Object dest) {
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

	/**
	 * Convert a multidimensional (dim) byte array to a multidimensional (dim-1) String array. The multidimensional
	 * String array will have 1 less dimension than the multidimensional byte array since a 1D byte array is converted
	 * to a String.
	 * 
	 * @param b
	 *            The multidimensional byte array
	 * @return The multidimensional (dim-1) String array equivalent of the multidimensional (dim) byte array
	 */
	public static Object multidimBytesToMultidimStrings(Object b) {
		int[] bDim = getArrayDimensions(b);
		int[] sDim = Arrays.copyOf(bDim, bDim.length - 1);
		Object s = Array.newInstance(String.class, sDim);
		copyByteArrayToStringArrayVals(b, s, sDim.length);
		return s;
	}

	/**
	 * Convert byte values in a multidimensional (dim) array to String values in a multidimensional (dim-1) array. The
	 * multidimensional byte array has 1 more dimension than the multidimensional String array since a 1D byte array is
	 * converted to a String.
	 * 
	 * @param orig
	 *            The multidimensional (dim) byte array
	 * @param dest
	 *            The multidimensional (dim-1) String array
	 * @param dimCount
	 *            String dimension count for recursion. If greater than one, recurse through multidimensional byte
	 *            array. Otherwise, copy 1D byte array to corresponding String and place in multidimensional String
	 *            array.
	 */
	protected static void copyByteArrayToStringArrayVals(Object orig, Object dest, int dimCount) {
		for (int i = 0; i < Array.getLength(orig); i++) {
			Object v = Array.get(orig, i);
			Object vd = Array.get(dest, i);
			if (dimCount > 1) {
				int newDim = dimCount - 1;
				copyByteArrayToStringArrayVals(v, vd, newDim);
			} else {
				String s = new String((byte[]) v, StandardCharsets.UTF_8);
				Array.set(dest, i, s);
			}
		}
	}
}
