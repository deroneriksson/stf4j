---
layout: default
---
<!--
{% comment %}
# ------------------------------------------------------------------------
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#      http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#
# ------------------------------------------------------------------------
{% endcomment %}
-->

<h1>STF4J (Simplified TensorFlow for Java)</h1>

* Table of contents.
{:toc}


# Overview

STF4J is a lightweight Java API that simplifies the running of pretrained
TensorFlow SavedModels from Java.

Design goals:

1. REPL friendly (see useful output when objects are retrieved).
2. Easy access to SavedModel signature information.
3. Input type coercion from Java scalars, arrays, and multidimensional arrays to Tensors.
4. Output type coercion from Tensors to Java scalars, arrays, and multidimensional arrays.
5. Use friendly keys (e.g., `probabilities`, `classes`, `image`, `input`)
   rather than variable names (e.g., `Placeholder:0`, `ArgMax:0`, `Softmax:0`, `input_tensor:0`,
   `softmax_tensor:0`) for model inputs and outputs.
6. Retrieve values by output key.


# Examples

## Hello World

The `stf4j-test-models` project contains several prebuilt SavedModels that are used for testing.
Here, we use the `add_string` model to concatenate two input strings, `hello` and `world`. The output,
`helloworld`, is output to the console.

```
package org.codait.example;

import org.codait.tf.TFModel;

public class Example {

	public static void main(String[] args) {
		TFModel model = new TFModel("../stf4j-test-models/simple_saved_models/add_string").sig("serving_default");
		String result = model.in("input1", "hello").in("input2", "world").out("output").run().getString("output");
		System.out.println(result);
	}

}
```



