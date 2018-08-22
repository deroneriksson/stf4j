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

package org.codait.stf4j.graph;

import java.util.Arrays;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.codait.stf4j.TFModel;
import org.codait.stf4j.simple.AddInt64Test;
import org.codait.stf4j.util.ArrayUtil;
import org.junit.Assert;
import org.junit.Test;
import org.tensorflow.Tensor;

public class TFGraphTest {

	protected static Logger log = LogManager.getLogger(TFGraphTest.class);

	@Test
	public void graphAddScalarLongs() {
		TFModel model = new TFModel(AddInt64Test.ADD_INT64_MODEL_DIR);
		long modelOutput = model.in("input1", 1L).in("input2", 2L).out("output").run().getLong("output");
		log.debug("Model output: " + modelOutput);
		TFGraph graph = model.tfGraph();
		@SuppressWarnings("unchecked")
		Tensor<Long> t = (Tensor<Long>) graph.input("input1", Tensor.create(1L, Long.class))
				.input("input2", Tensor.create(2L, Long.class)).output("add").run().getTensor("add");
		long graphOutput = t.longValue();
		log.debug("Graph output: " + graphOutput);
		Assert.assertEquals(3L, modelOutput);
		Assert.assertEquals(3L, graphOutput);
	}

	@Test
	public void graphAddLongArrays() {
		long[] l1 = new long[] { 1L, 3L, 5L };
		long[] l2 = new long[] { 2L, 4L, 6L };

		TFModel model = new TFModel(AddInt64Test.ADD_INT64_MODEL_DIR);
		long[] modelOutput = model.in("input1", l1).in("input2", l2).out("output").run().getLongArray("output");
		log.debug("Model output: " + Arrays.toString(modelOutput));
		TFGraph graph = model.tfGraph();
		@SuppressWarnings("unchecked")
		Tensor<Long> t = (Tensor<Long>) graph.input("input1", Tensor.create(l1, Long.class))
				.input("input2", Tensor.create(l2, Long.class)).output("add").run().getTensor("add");
		long[] graphOutput = ArrayUtil.longTensorToLongArray(t);
		log.debug("Graph output: " + Arrays.toString(graphOutput));
		long[] expected = new long[] { 3L, 7L, 11L };
		Assert.assertArrayEquals(expected, modelOutput);
		Assert.assertArrayEquals(expected, graphOutput);
	}

	@Test
	public void graphAdd2dLongArrays() {
		long[][] l1 = new long[][] { { 1L, 3L }, { 5L, 7L } };
		long[][] l2 = new long[][] { { 2L, 4L }, { 6L, 8L } };

		TFModel model = new TFModel(AddInt64Test.ADD_INT64_MODEL_DIR);
		long[][] modelOutput = (long[][]) model.in("input1", l1).in("input2", l2).out("output").run()
				.getLongArrayMultidimensional("output");
		log.debug("Model output: " + Arrays.deepToString(modelOutput));
		TFGraph graph = model.tfGraph();
		@SuppressWarnings("unchecked")
		Tensor<Long> t = (Tensor<Long>) graph.input("input1", Tensor.create(l1, Long.class))
				.input("input2", Tensor.create(l2, Long.class)).output("add").run().getTensor("add");
		long[][] graphOutput = (long[][]) ArrayUtil.longTensorToMultidimensionalLongArray(t);
		log.debug("Graph output: " + Arrays.deepToString(graphOutput));
		long[][] expected = new long[][] { { 3L, 7L }, { 11L, 15L } };
		Assert.assertArrayEquals(expected, modelOutput);
		Assert.assertArrayEquals(expected, graphOutput);
	}

}
