package org.codait.tf;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Experiment with arrays and reflection.
 *
 */
public class ArrayExample {

	public static void main(String[] args) {
		try {
			System.out.println("Hello world");

			// create array with reflection api and set values standard way
			int[] dimensions = new int[] { 2, 3, 4 };
			Object array = Array.newInstance(int.class, dimensions);
			int val = 0;
			for (int i = 0; i < 2; i++) {
				for (int j = 0; j < 3; j++) {
					for (int k = 0; k < 4; k++) {
						int[][][] ar = (int[][][]) array;
						ar[i][j][k] = val;
						val++;
					}
				}
			}
			System.out.println(Arrays.deepToString((Object[]) array));

			// create array with reflection and set values with reflection
			Object a = Array.newInstance(int.class, dimensions);
			setArrayValues(a, dimensions, new AtomicInteger(0));
			System.out.println(Arrays.deepToString((Object[]) a));

			// convert int array to float array using reflection
			Object fArray = convertArrayType(array, float.class);
			System.out.println(Arrays.deepToString((Object[]) fArray));
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	public static void setArrayValues(Object a, int[] dimensions, AtomicInteger val) {
		for (int i = 0; i < Array.getLength(a); i++) {
			Object v = Array.get(a, i);
			if (v.getClass().isArray()) {
				setArrayValues(v, dimensions, val);
			} else {
				int inc = val.incrementAndGet();
				Array.set(a, i, inc - 1);
			}
		}
	}

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

	public static int[] getArrayDimensions(Object array) {
		List<Integer> dimList = getArrayDimensionsList(array);
		int[] dimensions = new int[dimList.size()];
		for (int i = 0; i < dimList.size(); i++) {
			dimensions[i] = dimList.get(i);
		}
		return dimensions;
	}

	public static Object convertArrayType(Object orig, Class<?> destType) {
		int[] dimensions = getArrayDimensions(orig);
		Object dest = Array.newInstance(destType, dimensions);
		copyArrayVals(orig, dest);
		return dest;
	}

	public static void copyArrayVals(Object orig, Object dest) {
		for (int i = 0; i < Array.getLength(orig); i++) {
			Object v = Array.get(orig, i);
			Object vd = Array.get(dest, i);
			if (v.getClass().isArray()) {
				copyArrayVals(v, vd);
			} else {
				Array.set(dest, i, v);
			}
		}
	}

}
