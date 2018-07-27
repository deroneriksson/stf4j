package org.codait.tf.higgs;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.codait.tf.TFModel;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class HiggsBoostedTreesTest {

	protected static Logger log = LogManager.getLogger(HiggsBoostedTreesTest.class);

	public static final String HIGGS_SAVED_MODEL_DIR = "./higgs_boosted_trees_saved_model/";

	private TFModel model = null;

	static String[] s = new String[] {
			"0.869293,-0.635082,0.225690,0.327470,-0.689993,0.754202,-0.248573,-1.092064,0.0,1.374992,-0.653674,0.930349,1.107436,1.138904,-1.578198,-1.046985,0.0,0.657930,-0.010455,-0.045767,3.101961,1.353760,0.979563,0.978076,0.920005,0.721657,0.988751,0.876678",
			"1.595839,-0.607811,0.007075,1.818450,-0.111906,0.847550,-0.566437,1.581239,2.173076,0.755421,0.643110,1.426367,0.0,0.921661,-1.190432,-1.615589,0.0,0.651114,-0.654227,-1.274345,3.101961,0.823761,0.938191,0.971758,0.789176,0.430553,0.961357,0.957818" };
	static byte[][] b = new byte[2][];
	static {
		try {
			b[0] = s[0].getBytes("UTF-8");
			b[1] = s[1].getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			log.error("Exception encoding String to byte array", e);
		}
	}

	@Before
	public void init() throws IOException {
		model = new TFModel(HIGGS_SAVED_MODEL_DIR);
	}

	@After
	public void after() {
	}

	@Test
	public void higgsInputStrings() {
		log.debug(
				"Higgs Boosted Trees - input data as strings, output class_ids, classes, logistic, logits, and probabilities");

		model.in("inputs", s).out("class_ids", "classes", "logistic", "logits", "probabilities").run();
	}

	@Test
	public void higgsInputStringBytes() throws UnsupportedEncodingException {
		log.debug(
				"Higgs Boosted Trees - input data as string bytes, output class_ids, classes, logistic, logits, and probabilities");

		model.in("inputs", b).out("class_ids", "classes", "logistic", "logits", "probabilities").run();
	}

}
