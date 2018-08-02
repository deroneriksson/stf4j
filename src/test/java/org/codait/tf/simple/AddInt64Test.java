package org.codait.tf.simple;

import java.io.IOException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.codait.tf.TFModel;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AddInt64Test {

	protected static Logger log = LogManager.getLogger(AddInt64Test.class);

	public static final String ADD_INT64_MODEL_DIR = "./simple/add_int64";

	private TFModel model = null;

	@Before
	public void init() throws IOException {
		model = new TFModel(ADD_INT64_MODEL_DIR).sig("serving_default");
	}

	@After
	public void after() {
	}

	@Test
	public void inputLongsOutputLong() {
		long result = model.in("input1", 1L).in("input2", 2L).out("output").run().getLong("output");
		Assert.assertTrue(3L == result);
	}

	@Test
	public void inputIntsOutputLong() {
		long result = model.in("input1", 1).in("input2", 2).out("output").run().getLong("output");
		Assert.assertTrue(3L == result);
	}

	@Test
	public void inputFloatsOutputLong() {
		long result = model.in("input1", 1.0f).in("input2", 2.0f).out("output").run().getLong("output");
		Assert.assertTrue(3L == result);
	}

	@Test
	public void inputDoublesOutputLong() {
		long result = model.in("input1", 1.0d).in("input2", 2.0d).out("output").run().getLong("output");
		Assert.assertTrue(3L == result);
	}

	@Test
	public void inputStringsOutputLong() {
		long result = model.in("input1", "1").in("input2", "2").out("output").run().getLong("output");
		Assert.assertTrue(3L == result);
	}

	@Test
	public void inputLongsOutputInt() {
		int result = model.in("input1", 1L).in("input2", 2L).out("output").run().getInt("output");
		Assert.assertTrue(3 == result);
	}

	@Test
	public void inputLongsOutputFloat() {
		float result = model.in("input1", 1L).in("input2", 2L).out("output").run().getFloat("output");
		Assert.assertTrue(3.0f == result);
	}

	@Test
	public void inputLongsOutputDouble() {
		double result = model.in("input1", 1L).in("input2", 2L).out("output").run().getDouble("output");
		Assert.assertTrue(3.0d == result);
	}

}
