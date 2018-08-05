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

	@Test
	public void inputStringOutputString_aaa_bbb__c_d__aaac_bbbd() {
		String[] result = model.in("input1", new String[] { "aaa", "bbb" }).in("input2", new String[] { "c", "d" })
				.out("output").run().getStringArray("output");
		Assert.assertArrayEquals(new String[] { "aaac", "bbbd" }, result);
	}

	@Test
	public void inputStringOutputString_a_bbb__c_ddd__ac_bbbddd() {
		String[] result = model.in("input1", new String[] { "a", "bbb" }).in("input2", new String[] { "c", "ddd" })
				.out("output").run().getStringArray("output");
		Assert.assertArrayEquals(new String[] { "ac", "bbbddd" }, result);
	}

	@Test
	public void inputStringOutputString_aaa_b__ccc_d__aaaccc_bd() {
		String[] result = model.in("input1", new String[] { "aaa", "b" }).in("input2", new String[] { "ccc", "d" })
				.out("output").run().getStringArray("output");
		Assert.assertArrayEquals(new String[] { "aaaccc", "bd" }, result);
	}

	@Test
	public void inputStringOutputString_a_bbb__ccc_d__accc_bbbd() {
		String[] result = model.in("input1", new String[] { "a", "bbb" }).in("input2", new String[] { "ccc", "d" })
				.out("output").run().getStringArray("output");
		Assert.assertArrayEquals(new String[] { "accc", "bbbd" }, result);
	}

	@Test
	public void inputStringOutputString_a_b__c_d__aaac_bddd() {
		String[] result = model.in("input1", new String[] { "aaa", "b" }).in("input2", new String[] { "c", "ddd" })
				.out("output").run().getStringArray("output");
		Assert.assertArrayEquals(new String[] { "aaac", "bddd" }, result);
	}

	// try with a 2-byte UTF-8 character
	@Test
	public void inputStringOutputString_aring_b_aringb() {
		String result = model.in("input1", "å").in("input2", "b").out("output").run().getString("output");
		Assert.assertTrue("åb".equals(result));
	}

	// try with a 2-byte UTF-8 character
	@Test
	public void inputStringOutputString_aaring_bb_aaringbb() {
		String result = model.in("input1", "åå").in("input2", "bb").out("output").run().getString("output");
		Assert.assertTrue("ååbb".equals(result));
	}

	// try with a 2-byte UTF-8 character
	@Test
	public void inputStringOutputString_aaaring_b_aaaringb() {
		String result = model.in("input1", "ååå").in("input2", "b").out("output").run().getString("output");
		Assert.assertTrue("åååb".equals(result));
	}

	// try with a 2-byte UTF-8 character
	@Test
	public void inputStringOutputString_aring_bbb_aringbbb() {
		String result = model.in("input1", "å").in("input2", "bbb").out("output").run().getString("output");
		Assert.assertTrue("åbbb".equals(result));
	}

	// try with a 2-byte UTF-8 character
	@Test
	public void inputStringOutputString_aring_b__c_d__ad_bd() {
		String[] result = model.in("input1", new String[] { "å", "b" }).in("input2", new String[] { "c", "d" })
				.out("output").run().getStringArray("output");
		Assert.assertArrayEquals(new String[] { "åc", "bd" }, result);
	}

	// try with a 2-byte UTF-8 character
	@Test
	public void inputStringOutputString_aaaring_bbb__c_d__aaac_bbbd() {
		String[] result = model.in("input1", new String[] { "ååå", "bbb" }).in("input2", new String[] { "c", "d" })
				.out("output").run().getStringArray("output");
		Assert.assertArrayEquals(new String[] { "åååc", "bbbd" }, result);
	}

	// try with a 2-byte UTF-8 character
	@Test
	public void inputStringOutputString_aring_bbb__c_ddd__aringc_bbbddd() {
		String[] result = model.in("input1", new String[] { "å", "bbb" }).in("input2", new String[] { "c", "ddd" })
				.out("output").run().getStringArray("output");
		Assert.assertArrayEquals(new String[] { "åc", "bbbddd" }, result);
	}

	// try with a 2-byte UTF-8 character
	@Test
	public void inputStringOutputString_aaring_bbb__c_dddaaaring__aaringc_bbbdddaaaring() {
		String[] result = model.in("input1", new String[] { "åå", "bbb" }).in("input2", new String[] { "c", "dddååå" })
				.out("output").run().getStringArray("output");
		Assert.assertArrayEquals(new String[] { "ååc", "bbbdddååå" }, result);
	}

	@Test
	public void inputStringOutputString_a_b_c_d__e_f_g_h__ae_bf_cg_dh() {
		String[][] input1 = new String[][] { { "a", "b" }, { "c", "d" } };
		String[][] input2 = new String[][] { { "e", "f" }, { "g", "h" } };
		String[][] expected = new String[][] { { "ae", "bf" }, { "cg", "dh" } };
		String[][] result = (String[][]) model.in("input1", input1).in("input2", input2).out("output").run()
				.getStringArrayMultidimensional("output");
		for (int i = 0; i < expected.length; i++) {
			Assert.assertArrayEquals(expected[i], result[i]);
		}
	}

	@Test
	public void inputStringOutputString_aaa_b_c_d__eee_f_g_h__aaaeee_bf_cg_dh() {
		String[][] input1 = new String[][] { { "aaa", "b" }, { "c", "d" } };
		String[][] input2 = new String[][] { { "eee", "f" }, { "g", "h" } };
		String[][] expected = new String[][] { { "aaaeee", "bf" }, { "cg", "dh" } };
		String[][] result = (String[][]) model.in("input1", input1).in("input2", input2).out("output").run()
				.getStringArrayMultidimensional("output");
		for (int i = 0; i < expected.length; i++) {
			Assert.assertArrayEquals(expected[i], result[i]);
		}
	}

	@Test
	public void inputStringOutputString_aaa_bbb_c_d__e_f_g_h__aaae_bbbf_cg_dh() {
		String[][] input1 = new String[][] { { "aaa", "bbb" }, { "c", "d" } };
		String[][] input2 = new String[][] { { "e", "f" }, { "g", "h" } };
		String[][] expected = new String[][] { { "aaae", "bbbf" }, { "cg", "dh" } };
		String[][] result = (String[][]) model.in("input1", input1).in("input2", input2).out("output").run()
				.getStringArrayMultidimensional("output");
		for (int i = 0; i < expected.length; i++) {
			Assert.assertArrayEquals(expected[i], result[i]);
		}
	}

	@Test
	public void inputStringOutputString_aaa_b_c_ddd__e_fff_gg_h__aaae_bfff_cgg_ddh() {
		String[][] input1 = new String[][] { { "aaa", "b" }, { "c", "dd" } };
		String[][] input2 = new String[][] { { "e", "fff" }, { "gg", "h" } };
		String[][] expected = new String[][] { { "aaae", "bfff" }, { "cgg", "ddh" } };
		String[][] result = (String[][]) model.in("input1", input1).in("input2", input2).out("output").run()
				.getStringArrayMultidimensional("output");
		for (int i = 0; i < expected.length; i++) {
			Assert.assertArrayEquals(expected[i], result[i]);
		}
	}

	// try with a 2-byte UTF-8 character
	@Test
	public void inputStringOutputString_aaaring_b_c_daring__e_faaring_g_h__ae_bf_cg_daringh() {
		String[][] input1 = new String[][] { { "ååå", "b" }, { "c", "då" } };
		String[][] input2 = new String[][] { { "e", "fåå" }, { "g", "h" } };
		String[][] expected = new String[][] { { "åååe", "bfåå" }, { "cg", "dåh" } };
		String[][] result = (String[][]) model.in("input1", input1).in("input2", input2).out("output").run()
				.getStringArrayMultidimensional("output");
		for (int i = 0; i < expected.length; i++) {
			Assert.assertArrayEquals(expected[i], result[i]);
		}
	}
}
