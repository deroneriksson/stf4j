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
7. Minimize external dependencies (only TensorFlow/Protocol Buffer libraries and log4j).


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

Currently STF4J only has the following library dependencies:
tensorflow, libtensorflow, libtensorflow_jni, protobuf-java, and log4j.


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


Using the existing model, let's input two Float arrays and obtain the resulting Float array.

```
val input1 = Array(1.0f, 2.0f)
val input2 = Array(0.1f, 0.2f)
val sum = model.in("input1", input1).in("input2", input2).out("output").run().getFloatArray("output")
```


In the console output, we see the elements in the two Float arrays have been added, and the resulting
Float array is returned by the call to the `TFResults` `getFloatArray("output")` method.

```
scala> val input1 = Array(1.0f, 2.0f)
input1: Array[Float] = Array(1.0, 2.0)

scala> val input2 = Array(0.1f, 0.2f)
input2: Array[Float] = Array(0.1, 0.2)

scala> val sum = model.in("input1", input1).in("input2", input2).out("output").run().getFloatArray("output")
sum: Array[Float] = Array(1.1, 2.2)
```


Next, let's input two multidimensional Float arrays and obtain the resulting multidimensional Float array.
Here, we input two 3d arrays and obtain the resulting 3d array. We print the values of the individual
array elements.

```
val input1 = Array(Array(Array(1.0f, 2.0f), Array(3.0f, 4.0f)), Array(Array(5.0f, 6.0f), Array(7.0f, 8.0f)))
val input2 = Array(Array(Array(0.1f, 0.2f), Array(0.3f, 0.4f)), Array(Array(0.5f, 0.6f), Array(0.7f, 0.8f)))
val result = model.in("input1", input1).in("input2", input2).out("output").run().getFloatArrayMultidimensional("output")
val sum = result.asInstanceOf[Array[Array[Array[Float]]]]
print(sum(0)(0)(0))
print(sum(0)(0)(1))
print(sum(0)(1)(0))
print(sum(0)(1)(1))
print(sum(1)(0)(0))
print(sum(1)(0)(1))
print(sum(1)(1)(0))
print(sum(1)(1)(1))
```


Output:

```
scala> val input1 = Array(Array(Array(1.0f, 2.0f), Array(3.0f, 4.0f)), Array(Array(5.0f, 6.0f), Array(7.0f, 8.0f)))
input1: Array[Array[Array[Float]]] = Array(Array(Array(1.0, 2.0), Array(3.0, 4.0)), Array(Array(5.0, 6.0), Array(7.0, 8.0)))

scala> val input2 = Array(Array(Array(0.1f, 0.2f), Array(0.3f, 0.4f)), Array(Array(0.5f, 0.6f), Array(0.7f, 0.8f)))
input2: Array[Array[Array[Float]]] = Array(Array(Array(0.1, 0.2), Array(0.3, 0.4)), Array(Array(0.5, 0.6), Array(0.7, 0.8)))

scala> val result = model.in("input1", input1).in("input2", input2).out("output").run().getFloatArrayMultidimensional("output")
result: Object = Array(Array(Array(1.1, 2.2), Array(3.3, 4.4)), Array(Array(5.5, 6.6), Array(7.7, 8.8)))

scala> val sum = result.asInstanceOf[Array[Array[Array[Float]]]]
sum: Array[Array[Array[Float]]] = Array(Array(Array(1.1, 2.2), Array(3.3, 4.4)), Array(Array(5.5, 6.6), Array(7.7, 8.8)))

scala> print(sum(0)(0)(0))
1.1
scala> print(sum(0)(0)(1))
2.2
scala> print(sum(0)(1)(0))
3.3
scala> print(sum(0)(1)(1))
4.4
scala> print(sum(1)(0)(0))
5.5
scala> print(sum(1)(0)(1))
6.6
scala> print(sum(1)(1)(0))
7.7
scala> print(sum(1)(1)(1))
8.8
```


STF4J implicitly performs type coercion where possible. In the example below, two multidimensional Int arrays
are input into the `add_float32` model. Since the inputs need to be FLOAT Tensors, STF4J converts the
multidimensional Int arrays to FLOAT Tensors.

```
val input1 = Array(Array(Array(1, 2), Array(3, 4)), Array(Array(5, 6), Array(7, 8)))
val input2 = Array(Array(Array(1, 2), Array(3, 4)), Array(Array(5, 6), Array(7, 8)))
val result = model.in("input1", input1).in("input2", input2).out("output").run().getFloatArrayMultidimensional("output")
val sum = result.asInstanceOf[Array[Array[Array[Float]]]]
print(sum(0)(0)(0))
print(sum(0)(0)(1))
print(sum(0)(1)(0))
print(sum(0)(1)(1))
print(sum(1)(0)(0))
print(sum(1)(0)(1))
print(sum(1)(1)(0))
print(sum(1)(1)(1))
```

We see that the 3d Int arrays have been converted to FLOAT Tensors and added together by the model,
and the resulting 3d Float array is obtained by the call to `getFloatArrayMultidimensional("output")`.

```
scala> val input1 = Array(Array(Array(1, 2), Array(3, 4)), Array(Array(5, 6), Array(7, 8)))
input1: Array[Array[Array[Int]]] = Array(Array(Array(1, 2), Array(3, 4)), Array(Array(5, 6), Array(7, 8)))

scala> val input2 = Array(Array(Array(1, 2), Array(3, 4)), Array(Array(5, 6), Array(7, 8)))
input2: Array[Array[Array[Int]]] = Array(Array(Array(1, 2), Array(3, 4)), Array(Array(5, 6), Array(7, 8)))

scala> val result = model.in("input1", input1).in("input2", input2).out("output").run().getFloatArrayMultidimensional("output")
result: Object = Array(Array(Array(2.0, 4.0), Array(6.0, 8.0)), Array(Array(10.0, 12.0), Array(14.0, 16.0)))

scala> val sum = result.asInstanceOf[Array[Array[Array[Float]]]]
sum: Array[Array[Array[Float]]] = Array(Array(Array(2.0, 4.0), Array(6.0, 8.0)), Array(Array(10.0, 12.0), Array(14.0, 16.0)))

scala> print(sum(0)(0)(0))
2.0
scala> print(sum(0)(0)(1))
4.0
scala> print(sum(0)(1)(0))
6.0
scala> print(sum(0)(1)(1))
8.0
scala> print(sum(1)(0)(0))
10.0
scala> print(sum(1)(0)(1))
12.0
scala> print(sum(1)(1)(0))
14.0
scala> print(sum(1)(1)(1))
16.0
```


In the previous example, the resulting 3d Float array could also be retrieved as a 3d Int array if desired, since STF4J
can perform the type coercion automatically.

```
val result = model.in("input1", input1).in("input2", input2).out("output").run().getIntArrayMultidimensional("output")
val sum = result.asInstanceOf[Array[Array[Array[Int]]]]
```


Output:

```
scala> val result = model.in("input1", input1).in("input2", input2).out("output").run().getIntArrayMultidimensional("output")
result: Object = Array(Array(Array(2, 4), Array(6, 8)), Array(Array(10, 12), Array(14, 16)))

scala> val sum = result.asInstanceOf[Array[Array[Array[Int]]]]
sum: Array[Array[Array[Int]]] = Array(Array(Array(2, 4), Array(6, 8)), Array(Array(10, 12), Array(14, 16)))
```


Next, let's have a look at a model that returns multiple outputs. The `boolean_logic` model in the `stf4j-test-models`
project has 2 boolean inputs and 5 boolean outputs. The model takes the 2 boolean inputs and performs the following
boolean logic operations on the inputs and provides them as outputs: "and", "or", "xor", "not_and", "not_or".

Here, we input `true` for `input1` and `false` for `input2`. We retrieve the values of the inputs ANDed and ORed
together.

```
val model = new TFModel("../stf4j-test-models/simple_saved_models/boolean_logic").sig("serving_default")
val result = model.in("input1", true).in("input2", false).out("and", "or").run()
val true_and_false = result.getBoolean("and")
val true_or_false = result.getBoolean("or")
```


Output:

```
scala> val model = new TFModel("../stf4j-test-models/simple_saved_models/boolean_logic").sig("serving_default")
2018-08-19 14:25:23.109043: I tensorflow/cc/saved_model/reader.cc:31] Reading SavedModel from: ../stf4j-test-models/simple_saved_models/boolean_logic
2018-08-19 14:25:23.109690: I tensorflow/cc/saved_model/reader.cc:54] Reading meta graph with tags { serve }
2018-08-19 14:25:23.110120: I tensorflow/cc/saved_model/loader.cc:113] Restoring SavedModel bundle.
2018-08-19 14:25:23.110689: I tensorflow/cc/saved_model/loader.cc:123] The specified SavedModel has no variables; no checkpoints were restored.
2018-08-19 14:25:23.110704: I tensorflow/cc/saved_model/loader.cc:148] Running LegacyInitOp on SavedModel bundle.
2018-08-19 14:25:23.110723: I tensorflow/cc/saved_model/loader.cc:233] SavedModel load for tags { serve }; Status: success. Took 1683 microseconds.
model: org.codait.tf.TFModel =
Model directory: ../stf4j-test-models/simple_saved_models/boolean_logic

SignatureDef key: serving_default
method name: tensorflow/serving/predict
inputs:
  input key: input1
    dtype: DT_BOOL
    shape: ()
    name: input1:0
  input key: input2
    dtype: DT_BOOL
    shape: ()
    name: input2:0
outputs:
  output key: not_and
    dtype: DT_BOOL
    shape: ()
    name: output_not_and:0
  output key: and
    dtype: DT_BOOL
    shape: ()
    name: output_and:0
  output key: xor
    dtype: DT_BOOL
    shape: ()
    name: output_xor:0
  output key: not_or
    dtype: DT_BOOL
    shape: ()
    name: output_not_or:0
  output key: or
    dtype: DT_BOOL
    shape: ()
    name: output_or:0
Note: SignatureDef info can be obtained by calling TFModel's signatureDefInf...
scala> val result = model.in("input1", true).in("input2", false).out("and", "or").run()
result: org.codait.tf.TFResults =
SignatureDef Key: serving_default
Outputs:
  [1] and (output_and:0): BOOL tensor with shape []
  [2] or (output_or:0): BOOL tensor with shape []

scala> val true_and_false = result.getBoolean("and")
true_and_false: Boolean = false

scala> val true_or_false = result.getBoolean("or")
true_or_false: Boolean = true
```


