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

package org.codait.stf4j.util;

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
	public void boolean_to_String_bytes_true() {
		Assert.assertArrayEquals(TypeUtil.boolean_to_String_bytes(true), "true".getBytes());
	}

	@Test
	public void boolean_to_String_bytes_false() {
		Assert.assertArrayEquals(TypeUtil.boolean_to_String_bytes(false), "false".getBytes());
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
	public void double_to_byte_minus1() {
		Assert.assertTrue(TypeUtil.double_to_byte(-1.0d) == (byte) -1);
	}

	@Test
	public void double_to_byte_0() {
		Assert.assertTrue(TypeUtil.double_to_byte(0.0d) == (byte) 0);
	}

	@Test
	public void double_to_byte_1() {
		Assert.assertTrue(TypeUtil.double_to_byte(1.0d) == (byte) 1);
	}

	@Test
	public void double_to_byte_1point1() {
		Assert.assertTrue(TypeUtil.double_to_byte(1.1d) == (byte) 1);
	}

	@Test
	public void double_to_byte_MAX_VALUE() {
		// byteValue() of Double.MAX_VALUE gives -1
		Assert.assertTrue(TypeUtil.double_to_byte(Double.MAX_VALUE) == (byte) -1);
	}

	@Test
	public void double_to_byte_MIN_VALUE() {
		Assert.assertTrue(TypeUtil.double_to_byte(Double.MIN_VALUE) == (byte) 0);
	}

	@Test
	public void double_to_byte_NaN() {
		Assert.assertTrue(TypeUtil.double_to_byte(Double.NaN) == 0);
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

	@Test
	public void double_to_String_bytes_0() {
		Assert.assertArrayEquals(TypeUtil.double_to_String_bytes(0.0d), "0.0".getBytes());
	}

	@Test
	public void double_to_String_bytes_1() {
		Assert.assertArrayEquals(TypeUtil.double_to_String_bytes(1.0d), "1.0".getBytes());
	}

	@Test
	public void float_to_boolean_minus1() {
		Assert.assertTrue(TypeUtil.float_to_boolean(-1.0f) == true);
	}

	@Test
	public void float_to_boolean_0() {
		Assert.assertTrue(TypeUtil.float_to_boolean(0.0f) == false);
	}

	@Test
	public void float_to_boolean_1() {
		Assert.assertTrue(TypeUtil.float_to_boolean(1.0f) == true);
	}

	@Test
	public void float_to_boolean_1point1() {
		Assert.assertTrue(TypeUtil.float_to_boolean(1.1f) == true);
	}

	@Test
	public void float_to_boolean_MAX_VALUE() {
		Assert.assertTrue(TypeUtil.float_to_boolean(Float.MAX_VALUE) == true);
	}

	@Test
	public void float_to_boolean_MIN_VALUE() {
		Assert.assertTrue(TypeUtil.float_to_boolean(Float.MIN_VALUE) == true);
	}

	@Test
	public void float_to_boolean_NaN() {
		Assert.assertTrue(TypeUtil.float_to_boolean(Float.NaN) == true);
	}

	@Test
	public void float_to_byte_minus1() {
		Assert.assertTrue(TypeUtil.float_to_byte(-1.0f) == (byte) -1);
	}

	@Test
	public void float_to_byte_0() {
		Assert.assertTrue(TypeUtil.float_to_byte(0.0f) == (byte) 0);
	}

	@Test
	public void float_to_byte_1() {
		Assert.assertTrue(TypeUtil.float_to_byte(1.0f) == (byte) 1);
	}

	@Test
	public void float_to_byte_1point1() {
		Assert.assertTrue(TypeUtil.float_to_byte(1.1f) == (byte) 1);
	}

	@Test
	public void float_to_byte_MAX_VALUE() {
		// byteValue() of Float.MAX_VALUE gives -1
		Assert.assertTrue(TypeUtil.float_to_byte(Float.MAX_VALUE) == (byte) -1);
	}

	@Test
	public void float_to_byte_MIN_VALUE() {
		Assert.assertTrue(TypeUtil.float_to_byte(Float.MIN_VALUE) == (byte) 0);
	}

	@Test
	public void float_to_byte_NaN() {
		Assert.assertTrue(TypeUtil.float_to_byte(Float.NaN) == 0);
	}

	@Test
	public void float_to_double_minus1() {
		Assert.assertTrue(TypeUtil.float_to_double(-1.0f) == -1.0d);
	}

	@Test
	public void float_to_double_0() {
		Assert.assertTrue(TypeUtil.float_to_double(0.0f) == 0.0d);
	}

	@Test
	public void float_to_double_1() {
		Assert.assertTrue(TypeUtil.float_to_double(1.0f) == 1.0d);
	}

	@Test
	public void float_to_double_1point1() {
		// Round answer since float 1.1f does not exactly equal value converted to double
		Assert.assertTrue(Math.round(TypeUtil.float_to_double(1.1f) * 10000d) / 10000d == 1.1d);
	}

	@Test
	public void float_to_double_MAX_VALUE() {
		String max = Double.toString(TypeUtil.float_to_double(Float.MAX_VALUE));
		Assert.assertTrue(max.startsWith("3.402") && max.endsWith("E38"));
	}

	@Test
	public void float_to_double_MIN_VALUE() {
		String min = Double.toString(TypeUtil.float_to_double(Float.MIN_VALUE));
		Assert.assertTrue(min.startsWith("1.401") && min.endsWith("E-45"));
	}

	@Test
	public void float_to_double_NaN() {
		Assert.assertTrue(Double.isNaN(TypeUtil.float_to_double(Float.NaN)));
	}

	@Test
	public void float_to_int_minus1() {
		Assert.assertTrue(TypeUtil.float_to_int(-1.0f) == -1);
	}

	@Test
	public void float_to_int_0() {
		Assert.assertTrue(TypeUtil.float_to_int(0.0f) == 0);
	}

	@Test
	public void float_to_int_1() {
		Assert.assertTrue(TypeUtil.float_to_int(1.0f) == 1);
	}

	@Test
	public void float_to_int_1point1() {
		Assert.assertTrue(TypeUtil.float_to_int(1.1f) == 1);
	}

	@Test
	public void float_to_int_MAX_VALUE() {
		Assert.assertTrue(TypeUtil.float_to_int(Float.MAX_VALUE) == Integer.MAX_VALUE);
	}

	@Test
	public void float_to_int_MIN_VALUE() {
		Assert.assertTrue(TypeUtil.float_to_int(Float.MIN_VALUE) == 0);
	}

	@Test
	public void float_to_int_NaN() {
		Assert.assertTrue(TypeUtil.float_to_int(Float.NaN) == 0);
	}

	@Test
	public void float_to_long_minus1() {
		Assert.assertTrue(TypeUtil.float_to_long(-1.0f) == -1L);
	}

	@Test
	public void float_to_long_0() {
		Assert.assertTrue(TypeUtil.float_to_long(0.0f) == 0L);
	}

	@Test
	public void float_to_long_1() {
		Assert.assertTrue(TypeUtil.float_to_long(1.0f) == 1L);
	}

	@Test
	public void float_to_long_1point1() {
		Assert.assertTrue(TypeUtil.float_to_long(1.1f) == 1L);
	}

	@Test
	public void float_to_long_MAX_VALUE() {
		Assert.assertTrue(TypeUtil.float_to_long(Float.MAX_VALUE) == Long.MAX_VALUE);
	}

	@Test
	public void float_to_long_MIN_VALUE() {
		Assert.assertTrue(TypeUtil.float_to_long(Float.MIN_VALUE) == 0L);
	}

	@Test
	public void float_to_long_NaN() {
		Assert.assertTrue(TypeUtil.float_to_long(Float.NaN) == 0L);
	}

	@Test
	public void float_to_String_minus1() {
		Assert.assertTrue(TypeUtil.float_to_String(-1.0f).equals("-1.0"));
	}

	@Test
	public void float_to_String_0() {
		Assert.assertTrue(TypeUtil.float_to_String(0.0f).equals("0.0"));
	}

	@Test
	public void float_to_String_1() {
		Assert.assertTrue(TypeUtil.float_to_String(1.0f).equals("1.0"));
	}

	@Test
	public void float_to_String_1point1() {
		Assert.assertTrue(TypeUtil.float_to_String(1.1f).equals("1.1"));
	}

	@Test
	public void float_to_String_MAX_VALUE() {
		// See Float.class for MAX_VALUE
		Assert.assertTrue(TypeUtil.float_to_String(Float.MAX_VALUE).equals("3.4028235E38"));
	}

	@Test
	public void float_to_String_MIN_VALUE() {
		// See Float.class for MIN_VALUE
		Assert.assertTrue(TypeUtil.float_to_String(Float.MIN_VALUE).equals("1.4E-45"));
	}

	@Test
	public void float_to_String_NaN() {
		Assert.assertTrue(TypeUtil.float_to_String(Float.NaN).equals("NaN"));
	}

	@Test
	public void float_to_String_bytes_0() {
		Assert.assertArrayEquals(TypeUtil.float_to_String_bytes(0.0f), "0.0".getBytes());
	}

	@Test
	public void float_to_String_bytes_1() {
		Assert.assertArrayEquals(TypeUtil.float_to_String_bytes(1.0f), "1.0".getBytes());
	}

	@Test
	public void int_to_boolean_minus1() {
		Assert.assertTrue(TypeUtil.int_to_boolean(-1) == true);
	}

	@Test
	public void int_to_boolean_0() {
		Assert.assertTrue(TypeUtil.int_to_boolean(0) == false);
	}

	@Test
	public void int_to_boolean_1() {
		Assert.assertTrue(TypeUtil.int_to_boolean(1) == true);
	}

	@Test
	public void int_to_boolean_MAX_VALUE() {
		Assert.assertTrue(TypeUtil.int_to_boolean(Integer.MAX_VALUE) == true);
	}

	@Test
	public void int_to_boolean_MIN_VALUE() {
		Assert.assertTrue(TypeUtil.int_to_boolean(Integer.MIN_VALUE) == true);
	}

	@Test
	public void int_to_byte_minus1() {
		Assert.assertTrue(TypeUtil.int_to_byte(-1) == (byte) -1);
	}

	@Test
	public void int_to_byte_0() {
		Assert.assertTrue(TypeUtil.int_to_byte(0) == (byte) 0);
	}

	@Test
	public void int_to_byte_1() {
		Assert.assertTrue(TypeUtil.int_to_byte(1) == (byte) 1);
	}

	@Test
	public void int_to_byte_MAX_VALUE() {
		// byteValue() of Integer.MAX_VALUE gives -1
		Assert.assertTrue(TypeUtil.int_to_byte(Integer.MAX_VALUE) == (byte) -1);
	}

	@Test
	public void int_to_byte_MIN_VALUE() {
		Assert.assertTrue(TypeUtil.int_to_byte(Integer.MIN_VALUE) == (byte) 0);
	}

	@Test
	public void int_to_float_minus1() {
		Assert.assertTrue(TypeUtil.int_to_float(-1) == -1.0f);
	}

	@Test
	public void int_to_float_0() {
		Assert.assertTrue(TypeUtil.int_to_float(0) == 0.0f);
	}

	@Test
	public void int_to_float_1() {
		Assert.assertTrue(TypeUtil.int_to_float(1) == 1.0f);
	}

	@Test
	public void int_to_float_MAX_VALUE() {
		Assert.assertTrue(TypeUtil.int_to_float(Integer.MAX_VALUE) == Integer.MAX_VALUE);
	}

	@Test
	public void int_to_float_MIN_VALUE() {
		Assert.assertTrue(TypeUtil.int_to_float(Integer.MIN_VALUE) == Integer.MIN_VALUE);
	}

	@Test
	public void int_to_double_minus1() {
		Assert.assertTrue(TypeUtil.int_to_double(-1) == -1.0d);
	}

	@Test
	public void int_to_double_0() {
		Assert.assertTrue(TypeUtil.int_to_double(0) == 0.0d);
	}

	@Test
	public void int_to_double_1() {
		Assert.assertTrue(TypeUtil.int_to_double(1) == 1.0d);
	}

	@Test
	public void int_to_double_MAX_VALUE() {
		Assert.assertTrue(TypeUtil.int_to_double(Integer.MAX_VALUE) == Integer.MAX_VALUE);
	}

	@Test
	public void int_to_double_MIN_VALUE() {
		Assert.assertTrue(TypeUtil.int_to_double(Integer.MIN_VALUE) == Integer.MIN_VALUE);
	}

	@Test
	public void int_to_long_minus1() {
		Assert.assertTrue(TypeUtil.int_to_long(-1) == -1L);
	}

	@Test
	public void int_to_long_0() {
		Assert.assertTrue(TypeUtil.int_to_long(0) == 0L);
	}

	@Test
	public void int_to_long_1() {
		Assert.assertTrue(TypeUtil.int_to_long(1) == 1L);
	}

	@Test
	public void int_to_long_MAX_VALUE() {
		Assert.assertTrue(TypeUtil.int_to_long(Integer.MAX_VALUE) == Integer.MAX_VALUE);
	}

	@Test
	public void int_to_long_MIN_VALUE() {
		Assert.assertTrue(TypeUtil.int_to_long(Integer.MIN_VALUE) == Integer.MIN_VALUE);
	}

	@Test
	public void int_to_String_minus1() {
		Assert.assertTrue(TypeUtil.int_to_String(-1).equals("-1"));
	}

	@Test
	public void int_to_String_0() {
		Assert.assertTrue(TypeUtil.int_to_String(0).equals("0"));
	}

	@Test
	public void int_to_String_1() {
		Assert.assertTrue(TypeUtil.int_to_String(1).equals("1"));
	}

	@Test
	public void int_to_String_bytes_0() {
		Assert.assertArrayEquals(TypeUtil.int_to_String_bytes(0), "0".getBytes());
	}

	@Test
	public void int_to_String_bytes_1() {
		Assert.assertArrayEquals(TypeUtil.int_to_String_bytes(1), "1".getBytes());
	}

	@Test
	public void int_to_String_MAX_VALUE() {
		// See Integer.class for MAX_VALUE
		Assert.assertTrue(TypeUtil.int_to_String(Integer.MAX_VALUE).equals("2147483647"));
	}

	@Test
	public void long_to_boolean_minus1() {
		Assert.assertTrue(TypeUtil.long_to_boolean(-1L) == true);
	}

	@Test
	public void long_to_boolean_0() {
		Assert.assertTrue(TypeUtil.long_to_boolean(0L) == false);
	}

	@Test
	public void long_to_boolean_1() {
		Assert.assertTrue(TypeUtil.long_to_boolean(1L) == true);
	}

	@Test
	public void long_to_boolean_MAX_VALUE() {
		Assert.assertTrue(TypeUtil.long_to_boolean(Long.MAX_VALUE) == true);
	}

	@Test
	public void long_to_boolean_MIN_VALUE() {
		Assert.assertTrue(TypeUtil.long_to_boolean(Long.MIN_VALUE) == true);
	}

	@Test
	public void long_to_byte_minus1() {
		Assert.assertTrue(TypeUtil.long_to_byte(-1L) == (byte) -1);
	}

	@Test
	public void long_to_byte_0() {
		Assert.assertTrue(TypeUtil.long_to_byte(0L) == (byte) 0);
	}

	@Test
	public void long_to_byte_1() {
		Assert.assertTrue(TypeUtil.long_to_byte(1L) == (byte) 1);
	}

	@Test
	public void long_to_byte_MAX_VALUE() {
		// byteValue() of Long.MAX_VALUE gives -1
		Assert.assertTrue(TypeUtil.long_to_byte(Long.MAX_VALUE) == (byte) -1);
	}

	@Test
	public void long_to_byte_MIN_VALUE() {
		Assert.assertTrue(TypeUtil.long_to_byte(Long.MIN_VALUE) == (byte) 0);
	}

	@Test
	public void long_to_float_minus1() {
		Assert.assertTrue(TypeUtil.long_to_float(-1) == -1.0f);
	}

	@Test
	public void long_to_float_0() {
		Assert.assertTrue(TypeUtil.long_to_float(0) == 0.0f);
	}

	@Test
	public void long_to_float_1() {
		Assert.assertTrue(TypeUtil.long_to_float(1) == 1.0f);
	}

	@Test
	public void long_to_float_MAX_VALUE() {
		Assert.assertTrue(TypeUtil.long_to_float(Long.MAX_VALUE) == Long.MAX_VALUE);
	}

	@Test
	public void long_to_float_MIN_VALUE() {
		Assert.assertTrue(TypeUtil.long_to_float(Long.MIN_VALUE) == Long.MIN_VALUE);
	}

	@Test
	public void long_to_double_minus1() {
		Assert.assertTrue(TypeUtil.long_to_double(-1) == -1.0d);
	}

	@Test
	public void long_to_double_0() {
		Assert.assertTrue(TypeUtil.long_to_double(0) == 0.0d);
	}

	@Test
	public void long_to_double_1() {
		Assert.assertTrue(TypeUtil.long_to_double(1) == 1.0d);
	}

	@Test
	public void long_to_double_MAX_VALUE() {
		Assert.assertTrue(TypeUtil.long_to_double(Long.MAX_VALUE) == Long.MAX_VALUE);
	}

	@Test
	public void long_to_double_MIN_VALUE() {
		Assert.assertTrue(TypeUtil.long_to_double(Long.MIN_VALUE) == Long.MIN_VALUE);
	}

	@Test
	public void long_to_int_minus1() {
		Assert.assertTrue(TypeUtil.long_to_int(-1) == -1L);
	}

	@Test
	public void long_to_int_0() {
		Assert.assertTrue(TypeUtil.long_to_int(0) == 0L);
	}

	@Test
	public void long_to_int_1() {
		Assert.assertTrue(TypeUtil.long_to_int(1) == 1L);
	}

	@Test
	public void long_to_int_MAX_VALUE() {
		Assert.assertTrue(TypeUtil.long_to_int(Long.MAX_VALUE) == -1);
	}

	@Test
	public void long_to_int_MIN_VALUE() {
		Assert.assertTrue(TypeUtil.long_to_int(Long.MIN_VALUE) == 0);
	}

	@Test
	public void long_to_String_minus1() {
		Assert.assertTrue(TypeUtil.long_to_String(-1).equals("-1"));
	}

	@Test
	public void long_to_String_0() {
		Assert.assertTrue(TypeUtil.long_to_String(0).equals("0"));
	}

	@Test
	public void long_to_String_1() {
		Assert.assertTrue(TypeUtil.long_to_String(1).equals("1"));
	}

	@Test
	public void long_to_String_MAX_VALUE() {
		// See Long.class for MAX_VALUE
		Assert.assertTrue(TypeUtil.long_to_String(Long.MAX_VALUE).equals("9223372036854775807"));
	}

	@Test
	public void long_to_String_bytes_0() {
		Assert.assertArrayEquals(TypeUtil.long_to_String_bytes(0L), "0".getBytes());
	}

	@Test
	public void long_to_String_bytes_1() {
		Assert.assertArrayEquals(TypeUtil.long_to_String_bytes(1L), "1".getBytes());
	}

	@Test
	public void String_to_boolean_false() {
		Assert.assertTrue(TypeUtil.String_to_boolean("false") == false);
	}

	@Test
	public void String_to_boolean_true() {
		Assert.assertTrue(TypeUtil.String_to_boolean("true") == true);
	}

	@Test
	public void String_to_boolean_FALSE() {
		Assert.assertTrue(TypeUtil.String_to_boolean("FALSE") == false);
	}

	@Test
	public void String_to_boolean_TRUE() {
		Assert.assertTrue(TypeUtil.String_to_boolean("TRUE") == false);
	}

	@Test
	public void String_to_byte_minus1() {
		Assert.assertTrue(TypeUtil.String_to_byte("-1") == (byte) -1);
	}

	@Test
	public void String_to_byte_0() {
		Assert.assertTrue(TypeUtil.String_to_byte("0") == (byte) 0);
	}

	@Test
	public void String_to_byte_1() {
		Assert.assertTrue(TypeUtil.String_to_byte("1") == (byte) 1);
	}

	@Test
	public void String_to_double_minus1() {
		Assert.assertTrue(TypeUtil.String_to_double("-1.0") == -1.0d);
	}

	@Test
	public void String_to_double_0() {
		Assert.assertTrue(TypeUtil.String_to_double("0.0") == 0.0d);
	}

	@Test
	public void String_to_double_1() {
		Assert.assertTrue(TypeUtil.String_to_double("1.0") == 1.0d);
	}

	@Test
	public void String_to_double_1point1() {
		Assert.assertTrue(TypeUtil.String_to_double("1.1") == 1.1d);
	}

	@Test
	public void String_to_float_minus1() {
		Assert.assertTrue(TypeUtil.String_to_float("-1.0") == -1.0f);
	}

	@Test
	public void String_to_float_0() {
		Assert.assertTrue(TypeUtil.String_to_float("0.0") == 0.0f);
	}

	@Test
	public void String_to_float_1() {
		Assert.assertTrue(TypeUtil.String_to_float("1.0") == 1.0f);
	}

	@Test
	public void String_to_float_1point1() {
		Assert.assertTrue(TypeUtil.String_to_float("1.1") == 1.1f);
	}

	@Test
	public void String_bytes_to_boolean_false() {
		Assert.assertTrue(TypeUtil.String_bytes_to_boolean("false".getBytes()) == false);
	}

	@Test
	public void String_bytes_to_boolean_true() {
		Assert.assertTrue(TypeUtil.String_bytes_to_boolean("true".getBytes()) == true);
	}

	@Test
	public void String_bytes_to_boolean_FALSE() {
		Assert.assertTrue(TypeUtil.String_bytes_to_boolean("FALSE".getBytes()) == false);
	}

	@Test
	public void String_bytes_to_boolean_TRUE() {
		Assert.assertTrue(TypeUtil.String_bytes_to_boolean("TRUE".getBytes()) == false);
	}

	@Test
	public void String_bytes_to_byte_minus1() {
		Assert.assertTrue(TypeUtil.String_bytes_to_byte("-1".getBytes()) == (byte) -1);
	}

	@Test
	public void String_bytes_to_byte_0() {
		Assert.assertTrue(TypeUtil.String_bytes_to_byte("0".getBytes()) == (byte) 0);
	}

	@Test
	public void String_bytes_to_byte_1() {
		Assert.assertTrue(TypeUtil.String_bytes_to_byte("1".getBytes()) == (byte) 1);
	}

	@Test
	public void String_bytes_to_double_minus1() {
		Assert.assertTrue(TypeUtil.String_bytes_to_double("-1.0".getBytes()) == -1.0d);
	}

	@Test
	public void String_bytes_to_double_0() {
		Assert.assertTrue(TypeUtil.String_bytes_to_double("0.0".getBytes()) == 0.0d);
	}

	@Test
	public void String_bytes_to_double_1() {
		Assert.assertTrue(TypeUtil.String_bytes_to_double("1.0".getBytes()) == 1.0d);
	}

	@Test
	public void String_bytes_to_double_1point1() {
		Assert.assertTrue(TypeUtil.String_bytes_to_double("1.1".getBytes()) == 1.1d);
	}

	@Test
	public void String_bytes_to_float_minus1() {
		Assert.assertTrue(TypeUtil.String_bytes_to_float("-1.0".getBytes()) == -1.0f);
	}

	@Test
	public void String_bytes_to_float_0() {
		Assert.assertTrue(TypeUtil.String_bytes_to_float("0.0".getBytes()) == 0.0f);
	}

	@Test
	public void String_bytes_to_float_1() {
		Assert.assertTrue(TypeUtil.String_bytes_to_float("1.0".getBytes()) == 1.0f);
	}

	@Test
	public void String_bytes_to_float_1point1() {
		Assert.assertTrue(TypeUtil.String_bytes_to_float("1.1".getBytes()) == 1.1f);
	}
}
