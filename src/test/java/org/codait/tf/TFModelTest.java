package org.codait.tf;

import java.io.IOException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TFModelTest {

	protected static Logger log = LogManager.getLogger(TFModelTest.class);

	public static final String BOOLEAN_LOGIC_MODEL_DIR = "./simple/boolean_logic";

	private TFModel model = null;

	@Before
	public void init() throws IOException {

	}

	@After
	public void after() {
	}

	@Test(expected = TFException.class)
	public void modelDirNull() {
		model = new TFModel(null);
	}

	@Test(expected = TFException.class)
	public void modelDirNotExist() {
		model = new TFModel("./model_dir_not_exist");
	}

	@Test
	public void modelExists() {
		model = new TFModel(BOOLEAN_LOGIC_MODEL_DIR);
		Assert.assertNotNull(model);
	}
}
