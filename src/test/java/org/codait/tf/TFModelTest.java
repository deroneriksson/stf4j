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

package org.codait.tf;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.codait.tf.simple.AddInt64Test;
import org.junit.Assert;
import org.junit.Test;

public class TFModelTest {

	protected static Logger log = LogManager.getLogger(TFModelTest.class);

	@Test(expected = TFException.class)
	public void modelDirNull() {
		@SuppressWarnings("unused")
		TFModel model = new TFModel(null);
	}

	@Test(expected = TFException.class)
	public void modelDirNotExist() {
		@SuppressWarnings("unused")
		TFModel model = new TFModel("./model_dir_not_exist");
	}

	@Test
	public void modelExists() {
		TFModel model = new TFModel(AddInt64Test.ADD_INT64_MODEL_DIR);
		Assert.assertNotNull(model);
	}

	@Test(expected = TFException.class)
	public void inputsNull() {
		TFModel model = new TFModel(AddInt64Test.ADD_INT64_MODEL_DIR).sig("serving_default");
		model.in("input1", null).in("input2", null).out("output").run();
	}

	@Test(expected = TFException.class)
	public void inputsNullNoSigDef() {
		TFModel model = new TFModel(AddInt64Test.ADD_INT64_MODEL_DIR);
		model.in("input1", null).in("input2", null).out("output").run();
	}

	@Test(expected = TFException.class)
	public void missingInputs() {
		TFModel model = new TFModel(AddInt64Test.ADD_INT64_MODEL_DIR).sig("serving_default");
		model.out("output").run();
	}

	@Test(expected = TFException.class)
	public void missingInputNoSigDef() {
		TFModel model = new TFModel(AddInt64Test.ADD_INT64_MODEL_DIR);
		model.out("output").run();
	}

	@Test(expected = TFException.class)
	public void missingOutput() {
		TFModel model = new TFModel(AddInt64Test.ADD_INT64_MODEL_DIR).sig("serving_default");
		model.in("input1", 1L).in("input2", 2L).run();
	}

	@Test(expected = TFException.class)
	public void missingOutputNoSigDef() {
		TFModel model = new TFModel(AddInt64Test.ADD_INT64_MODEL_DIR);
		model.in("input1", 1L).in("input2", 2L).run();
	}

	@Test(expected = TFException.class)
	public void badInputKey() {
		TFModel model = new TFModel(AddInt64Test.ADD_INT64_MODEL_DIR).sig("serving_default");
		model.in("bad_input", 1L).in("input2", 2L).out("output").run();
	}

	@Test(expected = TFException.class)
	public void badInputKeyNoSigDef() {
		TFModel model = new TFModel(AddInt64Test.ADD_INT64_MODEL_DIR);
		model.in("bad_input", 1L).in("input2", 2L).out("output").run();
	}

	@Test(expected = TFException.class)
	public void badOutputKey() {
		TFModel model = new TFModel(AddInt64Test.ADD_INT64_MODEL_DIR).sig("serving_default");
		model.in("input1", 1L).in("input2", 2L).out("bad_output").run();
	}

	@Test(expected = TFException.class)
	public void badOutputKeyNoSigDef() {
		TFModel model = new TFModel(AddInt64Test.ADD_INT64_MODEL_DIR);
		model.in("input1", 1L).in("input2", 2L).out("bad_output").run();
	}

	@Test
	public void inputLongsOutputLongNullSignatureDefKey() {
		TFModel model = new TFModel(AddInt64Test.ADD_INT64_MODEL_DIR);
		long result = model.sig(null).in("input1", 1L).in("input2", 2L).out("output").run().getLong("output");
		Assert.assertTrue(3L == result);
	}

	@Test(expected = TFException.class)
	public void badSignatureDefKey() {
		TFModel model = new TFModel(AddInt64Test.ADD_INT64_MODEL_DIR);
		model.sig("bad_sig_def_key");
	}
}
