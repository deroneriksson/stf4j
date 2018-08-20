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

import org.codait.stf4j.TFModel;

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
tensorflow, libtensorflow, libtensorflow_jni, proto, protobuf-java, and log4j.


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

### Introduction

To use STF4J with the Scala REPL, we can build the STF4J uberjar and start the Scala REPL
with the uberjar on the classpath.

```
mvn clean package -DskipTests -Pshade
scala -cp target/stf4j-uber-1.10.0-SNAPSHOT.jar
```

First, we'll import the `org.codait.stf4j` package, which contains the `TFModel` and `TFResults` classes.
Next, we create a `TFModel` object based on the `add_float32` model, which adds two float
`Tensors`.
We set the signature definition to be "serving_default".

```
import org.codait.stf4j._
val model = new TFModel("../stf4j-test-models/simple_saved_models/add_float32")
model.sig("serving_default")
val result = model.in("input1", 1.0f).in("input2", 2.0f).out("output").run()
val sum = result.getFloat("output")
```

Below we see the REPL console output. Notice that when we create our `model` object, the contained signature
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
scala> import org.codait.stf4j._
import org.codait.stf4j._

scala> val model = new TFModel("../stf4j-test-models/simple_saved_models/add_float32")
2018-08-19 12:59:09.927801: I tensorflow/cc/saved_model/reader.cc:31] Reading SavedModel from: ../stf4j-test-models/simple_saved_models/add_float32
2018-08-19 12:59:09.927952: I tensorflow/cc/saved_model/reader.cc:54] Reading meta graph with tags { serve }
2018-08-19 12:59:09.928248: I tensorflow/cc/saved_model/loader.cc:113] Restoring SavedModel bundle.
2018-08-19 12:59:09.928271: I tensorflow/cc/saved_model/loader.cc:123] The specified SavedModel has no variables; no checkpoints were restored.
2018-08-19 12:59:09.928278: I tensorflow/cc/saved_model/loader.cc:148] Running LegacyInitOp on SavedModel bundle.
2018-08-19 12:59:09.928285: I tensorflow/cc/saved_model/loader.cc:233] SavedModel load for tags { serve }; Status: success. Took 487 microseconds.
model: org.codait.stf4j.TFModel =
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
res50: org.codait.stf4j.TFModel =
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
result: org.codait.stf4j.TFResults =
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
model: org.codait.stf4j.TFModel =
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
result: org.codait.stf4j.TFResults =
SignatureDef Key: serving_default
Outputs:
  [1] and (output_and:0): BOOL tensor with shape []
  [2] or (output_or:0): BOOL tensor with shape []

scala> val true_and_false = result.getBoolean("and")
true_and_false: Boolean = false

scala> val true_or_false = result.getBoolean("or")
true_or_false: Boolean = true
```


### MNIST

Next, we'll take a look at doing predictions using the TensorFlow MNIST model.
We'll start the Scala REPL with more memory allocated since we load 10,000 MNIST
test images into memory in this example.

```
scala -cp target/stf4j-uber-1.10.0-SNAPSHOT.jar -J-Xmx4g
```


In the Scala REPL, in addition to the primary STF4J package, we import the `util` package
since it contains the `MNISTUtil` class that can be used to load the 10,000 MNIST test
labels and images.

In this example, we load the MNIST model, load the test labels and test images, and then
run the model on the first image. Even though the model requires `Float`s, STF4J allows
the images to be fed in as `Int`s, since automatic type coercion is performed.

The prediction value, `classes`, is defined as an INT64 (Long value). STF4J allows the value
to be retrieved and type coerced as an `Int` using the `getInt()` method. We display the label
and the prediction to the console.

```
import org.codait.stf4j._
import org.codait.stf4j.util._
val mnist = new TFModel("../stf4j-test-models/mnist_saved_model/").sig("serving_default")
val labels = MNISTUtil.getLabels("../stf4j-test-models/mnist_data/t10k-labels-idx1-ubyte")
val images = MNISTUtil.getImages("../stf4j-test-models/mnist_data/t10k-images-idx3-ubyte")
val label = labels(0);
val prediction = mnist.in("image", images(0)).out("classes").run().getInt("classes")
print("Label: " + label + ", Prediction: " + prediction)
```


Output:

```
scala> import org.codait.stf4j._
import org.codait.stf4j._

scala> import org.codait.stf4j.util._
import org.codait.stf4j.util._

scala> val mnist = new TFModel("../stf4j-test-models/mnist_saved_model/").sig("serving_default")
log4j:WARN No appenders could be found for logger (org.codait.stf4j.TFModel).
log4j:WARN Please initialize the log4j system properly.
log4j:WARN See http://logging.apache.org/log4j/1.2/faq.html#noconfig for more info.
2018-08-19 15:18:04.456606: I tensorflow/cc/saved_model/reader.cc:31] Reading SavedModel from: ../stf4j-test-models/mnist_saved_model/
2018-08-19 15:18:04.457571: I tensorflow/cc/saved_model/reader.cc:54] Reading meta graph with tags { serve }
2018-08-19 15:18:04.458685: I tensorflow/core/platform/cpu_feature_guard.cc:141] Your CPU supports instructions that this TensorFlow binary was not compiled to use: SSE4.2 AVX AVX2 FMA
2018-08-19 15:18:04.461273: I tensorflow/cc/saved_model/loader.cc:113] Restoring SavedModel bundle.
2018-08-19 15:18:04.493649: I tensorflow/cc/saved_model/loader.cc:148] Running LegacyInitOp on SavedModel bundle.
2018-08-19 15:18:04.497168: I tensorflow/cc/saved_model/loader.cc:233] SavedModel load for tags { serve }; Status: success. Took 40572 microseconds.
mnist: org.codait.stf4j.TFModel =
Model directory: ../stf4j-test-models/mnist_saved_model/

SignatureDef key: classify
method name: tensorflow/serving/predict
inputs:
  input key: image
    dtype: DT_FLOAT
    shape: (-1, 28, 28)
    name: Placeholder:0
outputs:
  output key: probabilities
    dtype: DT_FLOAT
    shape: (-1, 10)
    name: Softmax:0
  output key: classes
    dtype: DT_INT64
    shape: (-1)
    name: ArgMax:0
SignatureDef key: serving_default
method name: tensorflow/serving/predict
inputs:
  input key: image
    dtype: DT_FLOAT
    shape: (-1, 28, 28)
    name: Placeholder:0
outputs:
  output key: probabilities
    dtype: DT_FLOAT
    shape: (-1, 10)
    name: Softmax:0
  output key: classes
    dtype: DT_INT64
    shape: (-1)
    name: ArgMax:0
Note: SignatureDef info can b...
scala> val labels = MNISTUtil.getLabels("../stf4j-test-models/mnist_data/t10k-labels-idx1-ubyte")
labels: Array[Int] = Array(7, 2, 1, 0, 4, 1, 4, 9, 5, 9, 0, 6, 9, 0, 1, 5, 9, 7, 3, 4, 9, 6, 6, 5, 4, 0, 7, 4, 0, 1, 3, 1, 3, 4, 7, 2, 7, 1, 2, 1, 1, 7, 4, 2, 3, 5, 1, 2, 4, 4, 6, 3, 5, 5, 6, 0, 4, 1, 9, 5, 7, 8, 9, 3, 7, 4, 6, 4, 3, 0, 7, 0, 2, 9, 1, 7, 3, 2, 9, 7, 7, 6, 2, 7, 8, 4, 7, 3, 6, 1, 3, 6, 9, 3, 1, 4, 1, 7, 6, 9, 6, 0, 5, 4, 9, 9, 2, 1, 9, 4, 8, 7, 3, 9, 7, 4, 4, 4, 9, 2, 5, 4, 7, 6, 7, 9, 0, 5, 8, 5, 6, 6, 5, 7, 8, 1, 0, 1, 6, 4, 6, 7, 3, 1, 7, 1, 8, 2, 0, 2, 9, 9, 5, 5, 1, 5, 6, 0, 3, 4, 4, 6, 5, 4, 6, 5, 4, 5, 1, 4, 4, 7, 2, 3, 2, 7, 1, 8, 1, 8, 1, 8, 5, 0, 8, 9, 2, 5, 0, 1, 1, 1, 0, 9, 0, 3, 1, 6, 4, 2, 3, 6, 1, 1, 1, 3, 9, 5, 2, 9, 4, 5, 9, 3, 9, 0, 3, 6, 5, 5, 7, 2, 2, 7, 1, 2, 8, 4, 1, 7, 3, 3, 8, 8, 7, 9, 2, 2, 4, 1, 5, 9, 8, 7, 2, 3, 0, 4, 4, 2, 4, 1, 9, 5, 7, 7, 2,...
scala> val images = MNISTUtil.getImages("../stf4j-test-models/mnist_data/t10k-images-idx3-ubyte")
images: Array[Array[Array[Int]]] = Array(Array(Array(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0), Array(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0), Array(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0), Array(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0), Array(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0), Array(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0), Array(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0), Array(0, 0, 0, 0, 0, 0, 84, 185, 159, 151, 60, 36, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0), Array(0, 0, 0...
scala> val label = labels(0);
label: Int = 7

scala> val prediction = mnist.in("image", images(0)).out("classes").run().getInt("classes")
prediction: Int = 7

scala> print("Label: " + label + ", Prediction: " + prediction)
Label: 7, Prediction: 7
```


Since the MNIST model takes any number of 28x28 images (indicated by the -1 first dimension value of the `image` input shape), we can
pass the entire array of MNIST test images to the model and obtain the entire set of predictions.

```
val predictions = mnist.in("image", images).out("classes").run().getIntArray("classes")
```

Output:

```
scala> val predictions = mnist.in("image", images).out("classes").run().getIntArray("classes")
predictions: Array[Int] = Array(7, 2, 1, 0, 4, 1, 4, 9, 5, 9, 0, 6, 9, 0, 1, 5, 9, 7, 3, 4, 9, 6, 6, 5, 4, 0, 7, 4, 0, 1, 3, 1, 3, 4, 7, 2, 7, 1, 2, 1, 1, 7, 4, 2, 3, 5, 1, 2, 4, 4, 6, 3, 5, 5, 6, 0, 4, 1, 9, 5, 7, 8, 9, 3, 7, 4, 6, 4, 3, 0, 7, 0, 2, 9, 1, 7, 3, 2, 9, 7, 7, 6, 2, 7, 8, 4, 7, 3, 6, 1, 3, 6, 9, 3, 1, 4, 1, 7, 6, 9, 6, 0, 5, 4, 9, 9, 2, 1, 9, 4, 8, 7, 3, 9, 7, 9, 4, 4, 9, 2, 5, 4, 7, 6, 7, 9, 0, 5, 8, 5, 6, 6, 5, 7, 8, 1, 0, 1, 6, 4, 6, 7, 3, 1, 7, 1, 8, 2, 0, 2, 9, 9, 5, 5, 1, 5, 6, 0, 3, 4, 4, 6, 5, 4, 6, 5, 4, 5, 1, 4, 4, 7, 2, 3, 2, 7, 1, 8, 1, 8, 1, 8, 5, 0, 8, 9, 2, 5, 0, 1, 1, 1, 0, 9, 0, 3, 1, 6, 4, 2, 3, 6, 1, 1, 1, 3, 9, 5, 2, 9, 4, 5, 9, 3, 9, 0, 3, 6, 5, 5, 7, 2, 2, 7, 1, 2, 8, 4, 1, 7, 3, 3, 8, 8, 7, 9, 2, 2, 4, 1, 5, 9, 8, 7, 2, 3, 0, 4, 4, 2, 4, 1, 9, 5, 7, ...
```


### CIFAR-10


Next, we'll perform predictions using the TensorFlow CIFAR-10 model.
The CIFAR-10 test data consists of 10,000 32x32 3-channel images. We'll load the
entire test dataset into memory, so let's start the Scala REPL with
additional memory allocated.

```
scala -cp target/stf4j-uber-1.10.0-SNAPSHOT.jar -J-Xmx4g
```

We start by creating a `TFModel` object for the CIFAR-10 SavedModel. We
specify "serving_default" as the signature definition to use. The
possible signature definitions are displayed by the TFModel's `toString()`
method, which is displayed to the REPL when the `model` object is created.

We'll utilize the CIFAR10Util class to obtain the 10,000 CIFAR-10 test
labels and images. After that, we'll perform a prediction on the first
image and output the label and prediction to the console.

```
import org.codait.stf4j._
import org.codait.stf4j.util._
val cifar10 = new TFModel("../stf4j-test-models/cifar10_saved_model/").sig("serving_default")
val testDataFile = "../stf4j-test-models/cifar10_data/cifar-10-batches-bin/test_batch.bin"
val labels = CIFAR10Util.getLabels(testDataFile);
val images = CIFAR10Util.getPreprocessedImages(testDataFile, CIFAR10Util.DimOrder.ROWS_COLS_CHANNELS);
val label = labels(0);
val prediction = cifar10.in("input", images(0)).out("classes").run().getInt("classes")
print("Label: " + label + ", Prediction: " + prediction)
```


In the console output, notice that when we create the `model` object, we see the
possible `SignatureDef` keys, `serving_default` and `predict`. Also, notice that
the input image data is expected to be `Floats`. We see that the images are expected
to be 32 rows by 32 columns by 3 channels. Although the input shape specifies 128
input images, any number of images can be fed into the model. The `classes`
output is specified to be an `INT64` (`Long`) value. However, we implicitly convert
it to an `Int` value by calling the `TFResults` `getInt` method.

```
scala> import org.codait.stf4j._
import org.codait.stf4j._

scala> import org.codait.stf4j.util._
import org.codait.stf4j.util._

scala> val cifar10 = new TFModel("../stf4j-test-models/cifar10_saved_model/").sig("serving_default")
log4j:WARN No appenders could be found for logger (org.codait.stf4j.TFModel).
log4j:WARN Please initialize the log4j system properly.
log4j:WARN See http://logging.apache.org/log4j/1.2/faq.html#noconfig for more info.
2018-08-20 10:30:55.876988: I tensorflow/cc/saved_model/reader.cc:31] Reading SavedModel from: ../stf4j-test-models/cifar10_saved_model/
2018-08-20 10:30:55.892737: I tensorflow/cc/saved_model/reader.cc:54] Reading meta graph with tags { serve }
2018-08-20 10:30:55.901850: I tensorflow/core/platform/cpu_feature_guard.cc:141] Your CPU supports instructions that this TensorFlow binary was not compiled to use: SSE4.2 AVX AVX2 FMA
2018-08-20 10:30:55.919080: I tensorflow/cc/saved_model/loader.cc:113] Restoring SavedModel bundle.
2018-08-20 10:30:55.948538: I tensorflow/cc/saved_model/loader.cc:148] Running LegacyInitOp on SavedModel bundle.
2018-08-20 10:30:55.969714: I tensorflow/cc/saved_model/loader.cc:233] SavedModel load for tags { serve }; Status: success. Took 92737 microseconds.
cifar10: org.codait.stf4j.TFModel =
Model directory: ../stf4j-test-models/cifar10_saved_model/

SignatureDef key: serving_default
method name: tensorflow/serving/predict
inputs:
  input key: input
    dtype: DT_FLOAT
    shape: (128, 32, 32, 3)
    name: input_tensor:0
outputs:
  output key: probabilities
    dtype: DT_FLOAT
    shape: (128, 10)
    name: softmax_tensor:0
  output key: classes
    dtype: DT_INT64
    shape: (128)
    name: ArgMax:0
SignatureDef key: predict
method name: tensorflow/serving/predict
inputs:
  input key: input
    dtype: DT_FLOAT
    shape: (128, 32, 32, 3)
    name: input_tensor:0
outputs:
  output key: classes
    dtype: DT_INT64
    shape: (128)
    name: ArgMax:0
  output key: probabilities
    dtype: DT_FLOAT
    shape: (128, 10)
    name: softmax_tensor:...
scala> val testDataFile = "../stf4j-test-models/cifar10_data/cifar-10-batches-bin/test_batch.bin"
testDataFile: String = ../stf4j-test-models/cifar10_data/cifar-10-batches-bin/test_batch.bin

scala> val labels = CIFAR10Util.getLabels(testDataFile);
labels: Array[Int] = Array(3, 8, 8, 0, 6, 6, 1, 6, 3, 1, 0, 9, 5, 7, 9, 8, 5, 7, 8, 6, 7, 0, 4, 9, 5, 2, 4, 0, 9, 6, 6, 5, 4, 5, 9, 2, 4, 1, 9, 5, 4, 6, 5, 6, 0, 9, 3, 9, 7, 6, 9, 8, 0, 3, 8, 8, 7, 7, 4, 6, 7, 3, 6, 3, 6, 2, 1, 2, 3, 7, 2, 6, 8, 8, 0, 2, 9, 3, 3, 8, 8, 1, 1, 7, 2, 5, 2, 7, 8, 9, 0, 3, 8, 6, 4, 6, 6, 0, 0, 7, 4, 5, 6, 3, 1, 1, 3, 6, 8, 7, 4, 0, 6, 2, 1, 3, 0, 4, 2, 7, 8, 3, 1, 2, 8, 0, 8, 3, 5, 2, 4, 1, 8, 9, 1, 2, 9, 7, 2, 9, 6, 5, 6, 3, 8, 7, 6, 2, 5, 2, 8, 9, 6, 0, 0, 5, 2, 9, 5, 4, 2, 1, 6, 6, 8, 4, 8, 4, 5, 0, 9, 9, 9, 8, 9, 9, 3, 7, 5, 0, 0, 5, 2, 2, 3, 8, 6, 3, 4, 0, 5, 8, 0, 1, 7, 2, 8, 8, 7, 8, 5, 1, 8, 7, 1, 3, 0, 5, 7, 9, 7, 4, 5, 9, 8, 0, 7, 9, 8, 2, 7, 6, 9, 4, 3, 9, 6, 4, 7, 6, 5, 1, 5, 8, 8, 0, 4, 0, 5, 5, 1, 1, 8, 9, 0, 3, 1, 9, 2, 2, 5, 3, 9, 9, 4, 0, 3,...
scala> val images = CIFAR10Util.getPreprocessedImages(testDataFile, CIFAR10Util.DimOrder.ROWS_COLS_CHANNELS);
images: Array[Array[Array[Array[Float]]]] = Array(Array(Array(Array(1.0636618, 0.077478275, -1.2731644), Array(1.0851005, 0.0560395, -1.316042), Array(1.2137332, 0.16323337, -1.2302868), Array(1.235172, 0.20611091, -1.1874093), Array(1.1065394, 0.077478275, -1.3374807), Array(1.0207843, 0.0131619545, -1.4446746), Array(1.1494169, 0.14179459, -1.316042), Array(1.0851005, 0.098917045, -1.3589195), Array(1.0636618, 0.0560395, -1.3803582), Array(1.0851005, 0.098917045, -1.4446746), Array(1.1279781, 0.16323337, -1.4446746), Array(1.1065394, 0.0560395, -1.2088481), Array(1.1279781, 0.0560395, -1.2731644), Array(1.235172, 0.18467213, -1.4446746), Array(1.2994883, 0.18467213, -1.3589195), Array(1.3209271, 0.22754969, -1.3803582), Array(1.2566108, 0.18467213, -1.4661133), Array(1.1494169, 0.0989...
scala> val label = labels(0);
label: Int = 3

scala> val prediction = cifar10.in("input", images(0)).out("classes").run().getInt("classes")
prediction: Int = 3

scala> print("Label: " + label + ", Prediction: " + prediction)
Label: 3, Prediction: 3
```


Here, we feed in all 10,000 images as a 4-dimensional `Float` array. In the
background, this is converted to a 4-dimensional `Tensor` and this is fed into
the model. The 10,000 predictions are returned as an `Int` array.

```
val predictions = cifar10.in("input", images).out("classes").run().getIntArray("classes")
```


Output:

```
scala> val predictions = cifar10.in("input", images).out("classes").run().getIntArray("classes")
predictions: Array[Int] = Array(3, 8, 8, 0, 6, 6, 1, 6, 3, 1, 0, 9, 5, 7, 9, 6, 5, 7, 8, 6, 7, 0, 4, 9, 5, 2, 4, 0, 9, 6, 6, 5, 4, 5, 9, 2, 4, 1, 9, 5, 4, 6, 5, 6, 0, 9, 3, 9, 7, 6, 9, 8, 5, 3, 8, 8, 7, 7, 7, 3, 7, 3, 6, 3, 6, 2, 1, 2, 3, 7, 2, 6, 8, 8, 0, 2, 9, 3, 3, 8, 8, 1, 1, 7, 2, 5, 2, 7, 8, 9, 0, 3, 8, 6, 4, 3, 6, 0, 0, 7, 4, 5, 6, 3, 1, 1, 3, 6, 8, 7, 4, 0, 6, 2, 1, 3, 0, 4, 2, 7, 8, 3, 1, 2, 8, 1, 8, 3, 3, 2, 4, 1, 8, 9, 1, 2, 9, 7, 2, 9, 6, 5, 6, 3, 8, 2, 6, 6, 5, 2, 8, 9, 6, 0, 0, 5, 2, 9, 3, 4, 2, 1, 6, 6, 0, 4, 8, 4, 5, 8, 9, 0, 9, 8, 9, 9, 3, 7, 2, 0, 0, 5, 2, 2, 3, 8, 6, 3, 4, 0, 5, 8, 0, 1, 7, 2, 8, 8, 7, 8, 5, 1, 8, 7, 1, 3, 0, 5, 7, 9, 7, 4, 5, 9, 0, 0, 7, 9, 8, 2, 7, 6, 9, 4, 3, 9, 0, 4, 7, 6, 5, 1, 3, 8, 8, 0, 4, 7, 5, 5, 1, 1, 8, 9, 0, 3, 1, 9, 2, 2, 5, 3, 9, 9, 4, ...
```

