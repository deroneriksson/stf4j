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
	public void inputStringOutputString_a_b_ab() {
		String result = model.in("input1", "a").in("input2", "b").out("output").run().getString("output");
		Assert.assertTrue("ab".equals(result));
	}

	@Test
	public void inputStringOutputString_aa_bb_aabb() {
		String result = model.in("input1", "aa").in("input2", "bb").out("output").run().getString("output");
		Assert.assertTrue("aabb".equals(result));
	}

	@Test
	public void inputStringOutputString_aaa_b_aaab() {
		String result = model.in("input1", "aaa").in("input2", "b").out("output").run().getString("output");
		Assert.assertTrue("aaab".equals(result));
	}

	@Test
	public void inputStringOutputString_a_bbb_abbb() {
		String result = model.in("input1", "a").in("input2", "bbb").out("output").run().getString("output");
		Assert.assertTrue("abbb".equals(result));
	}

	@Test
	public void inputStringOutputString_blank_b_b() {
		String result = model.in("input1", "").in("input2", "b").out("output").run().getString("output");
		Assert.assertTrue("b".equals(result));
	}

	@Test
	public void inputStringOutputString_a_blank_a() {
		String result = model.in("input1", "a").in("input2", "").out("output").run().getString("output");
		Assert.assertTrue("a".equals(result));
	}

	@Test
	public void inputStringOutputString_blank_blank_blank() {
		String result = model.in("input1", "").in("input2", "").out("output").run().getString("output");
		Assert.assertTrue("".equals(result));
	}

	@Test
	public void inputStringOutputString_a_b__c_d__ad_bd() {
		String[] result = model.in("input1", new String[] { "a", "b" }).in("input2", new String[] { "c", "d" })
				.out("output").run().getStringArray("output");
		Assert.assertArrayEquals(new String[] { "ac", "bd" }, result);
	}
}
