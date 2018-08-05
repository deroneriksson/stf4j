package org.codait.tf.simple;

import java.io.IOException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.codait.tf.TFModel;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AddStringTest {

	protected static Logger log = LogManager.getLogger(AddStringTest.class);

	public static final String ADD_STRING_MODEL_DIR = "./simple/add_string";

	private TFModel model = null;

	@Before
	public void init() throws IOException {
		model = new TFModel(ADD_STRING_MODEL_DIR).sig("serving_default");
	}

	@After
	public void after() {
	}

	@Test
	public void inputStringOutputString() {
		String result = model.in("input1", "a").in("input2", "b").out("output").run().getString("output");
		Assert.assertTrue("ab".equals(result));
	}

}
