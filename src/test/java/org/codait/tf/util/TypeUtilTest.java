package org.codait.tf.util;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

public class TypeUtilTest {

	protected static Logger log = LogManager.getLogger(TypeUtilTest.class);

	@Test
	public void boolean_to_byte_true() {
		Assert.assertTrue(TypeUtil.boolean_to_byte(true) == (byte) 1);
	}

	@Test
	public void boolean_to_byte_false() {
		Assert.assertTrue(TypeUtil.boolean_to_byte(false) == (byte) 0);
	}

	@SuppressWarnings("null")
	@Test(expected = NullPointerException.class)
	public void boolean_to_byte_null() {
		TypeUtil.boolean_to_byte((Boolean) null);
	}

	@Test
	public void boolean_to_double_true() {
		Assert.assertTrue(TypeUtil.boolean_to_double(true) == 1.0d);
	}

	@Test
	public void boolean_to_double_false() {
		Assert.assertTrue(TypeUtil.boolean_to_double(false) == 0.0d);
	}

	@Test
	public void boolean_to_float_true() {
		Assert.assertTrue(TypeUtil.boolean_to_float(true) == 1.0f);
	}

	@Test
	public void boolean_to_float_false() {
		Assert.assertTrue(TypeUtil.boolean_to_float(false) == 0.0f);
	}

	@Test
	public void boolean_to_int_true() {
		Assert.assertTrue(TypeUtil.boolean_to_int(true) == 1);
	}

	@Test
	public void boolean_to_int_false() {
		Assert.assertTrue(TypeUtil.boolean_to_int(false) == 0);
	}

	@Test
	public void boolean_to_long_true() {
		Assert.assertTrue(TypeUtil.boolean_to_long(true) == 1L);
	}

	@Test
	public void boolean_to_long_false() {
		Assert.assertTrue(TypeUtil.boolean_to_long(false) == 0L);
	}

	@Test
	public void boolean_to_String_true() {
		Assert.assertTrue(TypeUtil.boolean_to_String(true).equals("true"));
	}

	@Test
	public void boolean_to_String_false() {
		Assert.assertTrue(TypeUtil.boolean_to_String(false).equals("false"));
	}

	@Test
	public void boolean_to_String_TRUE() {
		Assert.assertFalse(TypeUtil.boolean_to_String(true).equals("TRUE"));
	}

	@Test
	public void boolean_to_String_FALSE() {
		Assert.assertFalse(TypeUtil.boolean_to_String(false).equals("FALSE"));
	}

	@Test
	public void byte_to_boolean_minus1() {
		Assert.assertTrue(TypeUtil.byte_to_boolean((byte) -1) == true);
	}

	@Test
	public void byte_to_boolean_0() {
		Assert.assertTrue(TypeUtil.byte_to_boolean((byte) 0) == false);
	}

	@Test
	public void byte_to_boolean_1() {
		Assert.assertTrue(TypeUtil.byte_to_boolean((byte) 1) == true);
	}

	@Test
	public void byte_to_boolean_2() {
		Assert.assertTrue(TypeUtil.byte_to_boolean((byte) 2) == true);
	}

	@Test
	public void byte_unsigned_to_double_minus1() {
		Assert.assertTrue(TypeUtil.byte_unsigned_to_double((byte) -1) == 255.0d);
	}

	@Test
	public void byte_unsigned_to_double_0() {
		Assert.assertTrue(TypeUtil.byte_unsigned_to_double((byte) 0) == 0.0d);
	}

	@Test
	public void byte_unsigned_to_double_1() {
		Assert.assertTrue(TypeUtil.byte_unsigned_to_double((byte) 1) == 1.0d);
	}

	@Test
	public void byte_unsigned_to_double_2() {
		Assert.assertTrue(TypeUtil.byte_unsigned_to_double((byte) 2) == 2.0d);
	}

	@Test
	public void byte_unsigned_to_double_127() {
		Assert.assertTrue(TypeUtil.byte_unsigned_to_double((byte) 127) == 127.0d);
	}

	@Test
	public void byte_unsigned_to_double_128() {
		Assert.assertTrue(TypeUtil.byte_unsigned_to_double((byte) 128) == 128.0d);
	}

	@Test
	public void byte_unsigned_to_double_255() {
		Assert.assertTrue(TypeUtil.byte_unsigned_to_double((byte) 255) == 255.0d);
	}

	@Test
	public void byte_unsigned_to_double_256() {
		Assert.assertTrue(TypeUtil.byte_unsigned_to_double((byte) 256) == 0.0d);
	}

	@Test
	public void byte_unsigned_to_float_minus1() {
		Assert.assertTrue(TypeUtil.byte_unsigned_to_float((byte) -1) == 255.0f);
	}

	@Test
	public void byte_unsigned_to_float_0() {
		Assert.assertTrue(TypeUtil.byte_unsigned_to_float((byte) 0) == 0.0f);
	}

	@Test
	public void byte_unsigned_to_float_1() {
		Assert.assertTrue(TypeUtil.byte_unsigned_to_float((byte) 1) == 1.0f);
	}

	@Test
	public void byte_unsigned_to_float_2() {
		Assert.assertTrue(TypeUtil.byte_unsigned_to_float((byte) 2) == 2.0f);
	}

	@Test
	public void byte_unsigned_to_float_127() {
		Assert.assertTrue(TypeUtil.byte_unsigned_to_float((byte) 127) == 127.0f);
	}

	@Test
	public void byte_unsigned_to_float_128() {
		Assert.assertTrue(TypeUtil.byte_unsigned_to_float((byte) 128) == 128.0f);
	}

	@Test
	public void byte_unsigned_to_float_255() {
		Assert.assertTrue(TypeUtil.byte_unsigned_to_float((byte) 255) == 255.0f);
	}

	@Test
	public void byte_unsigned_to_float_256() {
		Assert.assertTrue(TypeUtil.byte_unsigned_to_float((byte) 256) == 0.0f);
	}

	@Test
	public void byte_unsigned_to_int_minus1() {
		Assert.assertTrue(TypeUtil.byte_unsigned_to_int((byte) -1) == 255);
	}

	@Test
	public void byte_unsigned_to_int_0() {
		Assert.assertTrue(TypeUtil.byte_unsigned_to_int((byte) 0) == 0);
	}

	@Test
	public void byte_unsigned_to_int_1() {
		Assert.assertTrue(TypeUtil.byte_unsigned_to_int((byte) 1) == 1);
	}

	@Test
	public void byte_unsigned_to_int_2() {
		Assert.assertTrue(TypeUtil.byte_unsigned_to_int((byte) 2) == 2);
	}

	@Test
	public void byte_unsigned_to_int_127() {
		Assert.assertTrue(TypeUtil.byte_unsigned_to_int((byte) 127) == 127);
	}

	@Test
	public void byte_unsigned_to_int_128() {
		Assert.assertTrue(TypeUtil.byte_unsigned_to_int((byte) 128) == 128);
	}

	@Test
	public void byte_unsigned_to_int_255() {
		Assert.assertTrue(TypeUtil.byte_unsigned_to_int((byte) 255) == 255);
	}

	@Test
	public void byte_unsigned_to_int_256() {
		Assert.assertTrue(TypeUtil.byte_unsigned_to_int((byte) 256) == 0);
	}

	@Test
	public void byte_unsigned_to_long_minus1() {
		Assert.assertTrue(TypeUtil.byte_unsigned_to_long((byte) -1) == 255L);
	}

	@Test
	public void byte_unsigned_to_long_0() {
		Assert.assertTrue(TypeUtil.byte_unsigned_to_long((byte) 0) == 0L);
	}

	@Test
	public void byte_unsigned_to_long_1() {
		Assert.assertTrue(TypeUtil.byte_unsigned_to_long((byte) 1) == 1L);
	}

	@Test
	public void byte_unsigned_to_long_2() {
		Assert.assertTrue(TypeUtil.byte_unsigned_to_long((byte) 2) == 2L);
	}

	@Test
	public void byte_unsigned_to_long_127() {
		Assert.assertTrue(TypeUtil.byte_unsigned_to_long((byte) 127) == 127L);
	}

	@Test
	public void byte_unsigned_to_long_128() {
		Assert.assertTrue(TypeUtil.byte_unsigned_to_long((byte) 128) == 128L);
	}

	@Test
	public void byte_unsigned_to_long_255() {
		Assert.assertTrue(TypeUtil.byte_unsigned_to_long((byte) 255) == 255L);
	}

	@Test
	public void byte_unsigned_to_long_256() {
		Assert.assertTrue(TypeUtil.byte_unsigned_to_long((byte) 256) == 0L);
	}

	@Test
	public void byte_unsigned_to_String_minus1() {
		Assert.assertTrue(TypeUtil.byte_unsigned_to_String((byte) -1).equals("255"));
	}

	@Test
	public void byte_unsigned_to_String_0() {
		Assert.assertTrue(TypeUtil.byte_unsigned_to_String((byte) 0).equals("0"));
	}

	@Test
	public void byte_unsigned_to_String_1() {
		Assert.assertTrue(TypeUtil.byte_unsigned_to_String((byte) 1).equals("1"));
	}

	@Test
	public void byte_unsigned_to_String_2() {
		Assert.assertTrue(TypeUtil.byte_unsigned_to_String((byte) 2).equals("2"));
	}

	@Test
	public void byte_unsigned_to_String_127() {
		Assert.assertTrue(TypeUtil.byte_unsigned_to_String((byte) 127).equals("127"));
	}

	@Test
	public void byte_unsigned_to_String_128() {
		Assert.assertTrue(TypeUtil.byte_unsigned_to_String((byte) 128).equals("128"));
	}

	@Test
	public void byte_unsigned_to_String_255() {
		Assert.assertTrue(TypeUtil.byte_unsigned_to_String((byte) 255).equals("255"));
	}

	@Test
	public void byte_unsigned_to_String_256() {
		Assert.assertTrue(TypeUtil.byte_unsigned_to_String((byte) 256).equals("0"));
	}

	@Test
	public void double_to_boolean_minus1() {
		Assert.assertTrue(TypeUtil.double_to_boolean(-1.0d) == true);
	}

	@Test
	public void double_to_boolean_0() {
		Assert.assertTrue(TypeUtil.double_to_boolean(0.0d) == false);
	}

	@Test
	public void double_to_boolean_1() {
		Assert.assertTrue(TypeUtil.double_to_boolean(1.0d) == true);
	}

	@Test
	public void double_to_boolean_1point1() {
		Assert.assertTrue(TypeUtil.double_to_boolean(1.1d) == true);
	}

	@Test
	public void double_to_boolean_MAX_VALUE() {
		Assert.assertTrue(TypeUtil.double_to_boolean(Double.MAX_VALUE) == true);
	}

	@Test
	public void double_to_boolean_MIN_VALUE() {
		Assert.assertTrue(TypeUtil.double_to_boolean(Double.MIN_VALUE) == true);
	}

	@Test
	public void double_to_boolean_NaN() {
		Assert.assertTrue(TypeUtil.double_to_boolean(Double.NaN) == true);
	}

	@Test
	public void double_to_float_minus1() {
		Assert.assertTrue(TypeUtil.double_to_float(-1.0d) == -1.0f);
	}

	@Test
	public void double_to_float_0() {
		Assert.assertTrue(TypeUtil.double_to_float(0.0d) == 0.0f);
	}

	@Test
	public void double_to_float_1() {
		Assert.assertTrue(TypeUtil.double_to_float(1.0d) == 1.0f);
	}

	@Test
	public void double_to_float_1point1() {
		Assert.assertTrue(TypeUtil.double_to_float(1.1d) == 1.1f);
	}

	@Test
	public void double_to_float_MAX_VALUE() {
		Assert.assertTrue(TypeUtil.double_to_float(Double.MAX_VALUE) == Float.POSITIVE_INFINITY);
	}

	@Test
	public void double_to_float_MIN_VALUE() {
		Assert.assertTrue(TypeUtil.double_to_float(Double.MIN_VALUE) == 0.0f);
	}

	@Test
	public void double_to_float_NaN() {
		Assert.assertTrue(Float.isNaN(TypeUtil.double_to_float(Double.NaN)));
	}

	@Test
	public void double_to_int_minus1() {
		Assert.assertTrue(TypeUtil.double_to_int(-1.0d) == -1);
	}

	@Test
	public void double_to_int_0() {
		Assert.assertTrue(TypeUtil.double_to_int(0.0d) == 0);
	}

	@Test
	public void double_to_int_1() {
		Assert.assertTrue(TypeUtil.double_to_int(1.0d) == 1);
	}

	@Test
	public void double_to_int_1point1() {
		Assert.assertTrue(TypeUtil.double_to_int(1.1d) == 1);
	}

	@Test
	public void double_to_int_MAX_VALUE() {
		Assert.assertTrue(TypeUtil.double_to_int(Double.MAX_VALUE) == Integer.MAX_VALUE);
	}

	@Test
	public void double_to_int_MIN_VALUE() {
		Assert.assertTrue(TypeUtil.double_to_int(Double.MIN_VALUE) == 0);
	}

	@Test
	public void double_to_int_NaN() {
		Assert.assertTrue(TypeUtil.double_to_int(Double.NaN) == 0);
	}

	@Test
	public void double_to_long_minus1() {
		Assert.assertTrue(TypeUtil.double_to_long(-1.0d) == -1L);
	}

	@Test
	public void double_to_long_0() {
		Assert.assertTrue(TypeUtil.double_to_long(0.0d) == 0L);
	}

	@Test
	public void double_to_long_1() {
		Assert.assertTrue(TypeUtil.double_to_long(1.0d) == 1L);
	}

	@Test
	public void double_to_long_1point1() {
		Assert.assertTrue(TypeUtil.double_to_long(1.1d) == 1L);
	}

	@Test
	public void double_to_long_MAX_VALUE() {
		Assert.assertTrue(TypeUtil.double_to_long(Double.MAX_VALUE) == Long.MAX_VALUE);
	}

	@Test
	public void double_to_long_MIN_VALUE() {
		Assert.assertTrue(TypeUtil.double_to_long(Double.MIN_VALUE) == 0L);
	}

	@Test
	public void double_to_long_NaN() {
		Assert.assertTrue(TypeUtil.double_to_long(Double.NaN) == 0L);
	}

	@Test
	public void double_to_String_minus1() {
		Assert.assertTrue(TypeUtil.double_to_String(-1.0d).equals("-1.0"));
	}

	@Test
	public void double_to_String_0() {
		Assert.assertTrue(TypeUtil.double_to_String(0.0d).equals("0.0"));
	}

	@Test
	public void double_to_String_1() {
		Assert.assertTrue(TypeUtil.double_to_String(1.0d).equals("1.0"));
	}

	@Test
	public void double_to_String_1point1() {
		Assert.assertTrue(TypeUtil.double_to_String(1.1d).equals("1.1"));
	}

	@Test
	public void double_to_String_MAX_VALUE() {
		// See Double.class for MAX_VALUE
		Assert.assertTrue(TypeUtil.double_to_String(Double.MAX_VALUE).equals("1.7976931348623157E308"));
	}

	@Test
	public void double_to_String_MIN_VALUE() {
		// See Double.class for MIN_VALUE
		Assert.assertTrue(TypeUtil.double_to_String(Double.MIN_VALUE).equals("4.9E-324"));
	}

	@Test
	public void double_to_String_NaN() {
		Assert.assertTrue(TypeUtil.double_to_String(Double.NaN).equals("NaN"));
	}

}
