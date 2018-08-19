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
7. Minimize external dependencies (only relevant TensorFlow libraries and log4j).


# Hello World

The `stf4j-test-models` project contains several prebuilt SavedModels that are used for testing.
Here, we use the `add_string` model to concatenate two input strings, `hello` and `world`.

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

Output:

```
helloworld
```


# Build

STF4J is a standard maven Java project. The project can be cloned from GitHub and built using maven.
Here, we skip testing. After building, the STF4J jar file is located in the target directory.

```
git clone https://github.com/deroneriksson/stf4j.git
cd stf4j
mvn clean package -DskipTests
```


For convenience when trying out STF4J, the shade profile builds 1) an `uber` STF4J jar that contains the
TensorFlow dependencies and log4j and 2) a `tf` STF4J jar that contains the TensorFlow dependencies with no log4j.

```
mvn clean package -DskipTests -Pshade
```


# Testing

The `stf4j-test-models` project contains several prebuilt SavedModels for testing. The `stf4j-test-models` project contains
information about how to rebuild the models if necessary.

For full testing, MNIST and CIFAR-10 data should be added to the `stf4j-test-models` project. Instructions to do this
are located in the `stf4j-test-models` project. If the data is not present, the relevant tests will be skipped.

```
git clone https://github.com/deroneriksson/stf4j-test-models.git
git clone https://github.com/deroneriksson/stf4j.git
cd stf4j
mvn clean test
```


# Examples

## Java


## Scala

To use STF4J with the Scala REPL, we can build the STF4J uberjar and start the Scala REPL
with the uberjar on the classpath.

```
mvn clean package -DskipTests -Pshade
scala -cp target/stf4j-uber-1.10.0-SNAPSHOT.jar
```

First, we'll import `the org.codait.tf` package, which contains the `TFModel` and `TFResults` classes.
Next, we create a `TFModel` object based on the `add_float32` model, which adds two float
`Tensors`.
We set the signature definition to be "serving_default".

```
import org.codait.tf._
val model = new TFModel("../stf4j-test-models/simple_saved_models/add_float32")
model.sig("serving_default")
val result = model.in("input1", 1.0f).in("input2", 2.0f).out("output").run()
val sum = result.getFloat("output")
```

Here we see the REPL console output. Notice that when we create our `model` object, the contained signature
definitions are displayed. We see a single signature definition which has the "serving_default" key.
The input and output keys are displayed, along with their types, shapes, and underlying variable names.

Inputs are set by calls to the model's `in` method, and outputs are set by calls to the `out` method.
The model is run by the `run()` method, which returns a `TFResults` object, which maps keys to output
`Tensors`. Notice that the returned `TFResults` object's `toString()` displays information about the
contained outputs. We see in the console output that the result contains an `output` key which maps
to the `output:0` variable and that the result is a `FLOAT` tensor containing a scalar float value.
We obtain the float scalar result by calling the `TFResults` `getFloat("output")` method with the
desired output key specified.

```
scala> import org.codait.tf._
import org.codait.tf._

scala> val model = new TFModel("../stf4j-test-models/simple_saved_models/add_float32")
2018-08-19 12:59:09.927801: I tensorflow/cc/saved_model/reader.cc:31] Reading SavedModel from: ../stf4j-test-models/simple_saved_models/add_float32
2018-08-19 12:59:09.927952: I tensorflow/cc/saved_model/reader.cc:54] Reading meta graph with tags { serve }
2018-08-19 12:59:09.928248: I tensorflow/cc/saved_model/loader.cc:113] Restoring SavedModel bundle.
2018-08-19 12:59:09.928271: I tensorflow/cc/saved_model/loader.cc:123] The specified SavedModel has no variables; no checkpoints were restored.
2018-08-19 12:59:09.928278: I tensorflow/cc/saved_model/loader.cc:148] Running LegacyInitOp on SavedModel bundle.
2018-08-19 12:59:09.928285: I tensorflow/cc/saved_model/loader.cc:233] SavedModel load for tags { serve }; Status: success. Took 487 microseconds.
model: org.codait.tf.TFModel =
Model directory: ../stf4j-test-models/simple_saved_models/add_float32

SignatureDef key: serving_default
method name: tensorflow/serving/predict
inputs:
  input key: input2
    dtype: DT_FLOAT
    shape: ()
    name: input2:0
  input key: input1
    dtype: DT_FLOAT
    shape: ()
    name: input1:0
outputs:
  output key: output
    dtype: DT_FLOAT
    shape: ()
    name: output:0
Note: SignatureDef info can be obtained by calling TFModel's signatureDefInfo() method.

scala> model.sig("serving_default")
res50: org.codait.tf.TFModel =
Model directory: ../stf4j-test-models/simple_saved_models/add_float32

SignatureDef key: serving_default
method name: tensorflow/serving/predict
inputs:
  input key: input2
    dtype: DT_FLOAT
    shape: ()
    name: input2:0
  input key: input1
    dtype: DT_FLOAT
    shape: ()
    name: input1:0
outputs:
  output key: output
    dtype: DT_FLOAT
    shape: ()
    name: output:0
Note: SignatureDef info can be obtained by calling TFModel's signatureDefInfo() method.

scala> val result = model.in("input1", 1.0f).in("input2", 2.0f).out("output").run()
result: org.codait.tf.TFResults =
SignatureDef Key: serving_default
Outputs:
  [1] output (output:0): FLOAT tensor with shape []

scala> val sum = result.getFloat("output")
sum: Float = 3.0

```
