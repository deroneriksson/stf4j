package org.codait.tf.util;

public class TypeUtil {

	public static boolean byte_to_boolean(byte b) {
		return b == 0 ? false : true;
	}

	public static boolean int_to_boolean(int i) {
		return i == 0 ? false : true;
	}

	public static boolean long_to_boolean(long l) {
		return l == 0L ? false : true;
	}

	public static boolean float_to_boolean(float f) {
		return f == 0.0f ? false : true;
	}

	public static boolean double_to_boolean(double d) {
		return d == 0.0d ? false : true;
	}

	public static boolean string_to_boolean(String s) {
		if ("true".equals(s)) {
			return true;
		} else {
			return false;
		}
	}

	public static byte boolean_to_byte(boolean b) {
		return b ? (byte) 1 : (byte) 0;
	}

	public static byte int_to_byte(int i) {
		return ((Integer) i).byteValue();
	}

	public static byte long_to_byte(long l) {
		return ((Long) l).byteValue();
	}

	public static byte float_to_byte(float f) {
		return ((Float) f).byteValue();
	}

	public static byte double_to_byte(double d) {
		return ((Double) d).byteValue();
	}

	public static double float_to_double(float f) {
		return (double) f;
	}

	public static double long_to_double(long l) {
		return (double) l;
	}

	public static double int_to_double(int i) {
		return (double) i;
	}

	public static double byte_unsigned_to_double(byte b) {
		int i = b & 0xFF; // unsigned
		return (double) i;
	}

	public static double string_bytes_to_double(byte[] b) {
		return Double.parseDouble(new String(b));
	}

	public static double string_to_double(String s) {
		return Double.parseDouble(s);
	}

	public static double boolean_to_double(boolean b) {
		return b ? 1.0d : 0.0d;
	}

	public static float double_to_float(double d) {
		return (float) d;
	}

	public static float long_to_float(long l) {
		return (float) l;
	}

	public static float int_to_float(int i) {
		return (float) i;
	}

	public static float byte_unsigned_to_float(byte b) {
		int i = b & 0xFF; // unsigned
		return (float) i;
	}

	public static float string_bytes_to_float(byte[] b) {
		return Float.parseFloat(new String(b));
	}

	public static float string_to_float(String s) {
		return Float.parseFloat(s);
	}

	public static float boolean_to_float(boolean b) {
		return b ? 1.0f : 0.0f;
	}

	public static int float_to_int(float f) {
		return (int) f;
	}

	public static int double_to_int(double d) {
		return (int) d;
	}

	public static int long_to_int(long l) {
		return (int) l;
	}

	public static int byte_unsigned_to_int(byte b) {
		return b & 0xFF; // unsigned
	}

	public static int string_bytes_to_int(byte[] b) {
		return Integer.parseInt(new String(b));
	}

	public static int string_to_int(String s) {
		return Integer.parseInt(s);
	}

	public static int boolean_to_int(boolean b) {
		return b ? 1 : 0;
	}

	public static long float_to_long(float f) {
		return (long) f;
	}

	public static long double_to_long(double d) {
		return (long) d;
	}

	public static long int_to_long(int i) {
		return (long) i;
	}

	public static long byte_unsigned_to_long(byte b) {
		return (long) b & 0xFF; // unsigned
	}

	public static long string_bytes_to_long(byte[] b) {
		return Long.parseLong(new String(b));
	}

	public static long string_to_long(String s) {
		return Long.parseLong(s);
	}

	public static long boolean_to_long(boolean b) {
		return b ? 1L : 0L;
	}

	public static String float_to_string(float f) {
		return Float.toString(f);
	}

	public static String double_to_string(double d) {
		return Double.toString(d);
	}

	public static String long_to_string(long l) {
		return Long.toString(l);
	}

	public static String int_to_string(int i) {
		return Integer.toString(i);
	}

	public static String byte_unsigned_to_string(byte b) {
		return Integer.toString((int) b & 0xFF); // unsigned
	}

	public static String string_bytes_to_string(byte[] b) {
		return new String(b);
	}

	public static String boolean_to_string(boolean b) {
		return Boolean.toString(b);
	}
}
