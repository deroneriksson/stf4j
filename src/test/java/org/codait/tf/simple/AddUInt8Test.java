package org.codait.tf.simple;

import java.io.IOException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.codait.tf.TFModel;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AddUInt8Test {

	protected static Logger log = LogManager.getLogger(AddUInt8Test.class);

	public static final String ADD_UINT8_MODEL_DIR = "./simple/add_uint8";

	private TFModel model = null;

	@Before
	public void init() throws IOException {
		model = new TFModel(ADD_UINT8_MODEL_DIR).sig("serving_default");
		System.out.println("MODEL:" + model);
	}

	@After
	public void after() {
	}

	@Test
	public void inputByte1Byte2OutputInt() {
		int result = model.in("input1", (byte) 1).in("input2", (byte) 2).out("output").run().getInt("output");
		Assert.assertTrue(3 == result);
	}

	@Test
	public void inputByte127Byte1OutputInt() {
		int result = model.in("input1", (byte) 127).in("input2", (byte) 1).out("output").run().getInt("output");
		Assert.assertTrue(128 == result);
	}

	@Test
	public void inputByte254Byte1OutputInt() {
		int result = model.in("input1", (byte) 254).in("input2", (byte) 1).out("output").run().getInt("output");
		Assert.assertTrue(255 == result);
	}

	@Test
	public void inputByte255Byte1OutputInt() {
		int result = model.in("input1", (byte) 255).in("input2", (byte) 1).out("output").run().getInt("output");
		Assert.assertTrue(0 == result);
	}

	@Test
	public void inputByte1Byte2OutputByte() {
		byte result = model.in("input1", (byte) 1).in("input2", (byte) 2).out("output").run().getByte("output");
		Assert.assertTrue(3 == result);
	}

	@Test
	public void inputByte127Byte1OutputByte() {
		byte result = model.in("input1", (byte) 127).in("input2", (byte) 1).out("output").run().getByte("output");
		// adding 1 to Byte.MAX_VALUE should give -128 since java byte is signed
		Assert.assertTrue(-128 == result);
	}

	@Test
	public void inputInt1Int2OutputInt() {
		int result = model.in("input1", 1).in("input2", 2).out("output").run().getInt("output");
		Assert.assertTrue(3 == result);
	}

	@Test
	public void inputInt127Int1OutputInt() {
		int result = model.in("input1", 127).in("input2", 1).out("output").run().getInt("output");
		Assert.assertTrue(128 == result);
	}

	@Test
	public void inputInt254Int1OutputInt() {
		int result = model.in("input1", 254).in("input2", 1).out("output").run().getInt("output");
		Assert.assertTrue(255 == result);
	}

	@Test
	public void inputInt255Int1OutputInt() {
		int result = model.in("input1", 255).in("input2", 1).out("output").run().getInt("output");
		Assert.assertTrue(0 == result);
	}

	@Test
	public void inputLong1Long2OutputLong() {
		long result = model.in("input1", 1L).in("input2", 2L).out("output").run().getLong("output");
		Assert.assertTrue(3L == result);
	}

	@Test
	public void inputLong127Long1OutputLong() {
		long result = model.in("input1", 127L).in("input2", 1L).out("output").run().getLong("output");
		Assert.assertTrue(128L == result);
	}

	@Test
	public void inputLong254Long1OutputLong() {
		long result = model.in("input1", 254L).in("input2", 1L).out("output").run().getLong("output");
		Assert.assertTrue(255L == result);
	}

	@Test
	public void inputLong255Long1OutputLong() {
		long result = model.in("input1", 255L).in("input2", 1L).out("output").run().getLong("output");
		Assert.assertTrue(0L == result);
	}

	@Test
	public void inputFloat1Float2OutputFloat() {
		float result = model.in("input1", 1.0f).in("input2", 2.0f).out("output").run().getFloat("output");
		Assert.assertTrue(3.0f == result);
	}

	@Test
	public void inputFloat127Float1OutputFloat() {
		float result = model.in("input1", 127.0f).in("input2", 1.0f).out("output").run().getFloat("output");
		Assert.assertTrue(128.0f == result);
	}

	@Test
	public void inputFloat254Float1OutputFloat() {
		float result = model.in("input1", 254.0f).in("input2", 1.0f).out("output").run().getFloat("output");
		Assert.assertTrue(255.0f == result);
	}

	@Test
	public void inputFloat255Float1OutputFloat() {
		float result = model.in("input1", 255.0f).in("input2", 1.0f).out("output").run().getFloat("output");
		Assert.assertTrue(0.0f == result);
	}
}
