import tensorflow as tf
import tensorflow.saved_model as saved_model


def add_int32():
  tf.reset_default_graph()
  sess = tf.Session()
  input1 = tf.placeholder(tf.int32, name="input1")
  input2 = tf.placeholder(tf.int32, name="input2")
  sum = input1 + input2
  output = tf.identity(sum, "output")
  print(input1)
  print(input2)
  print(sum)
  print(output)

  dict = {input1: 1, input2: 2}
  result = sess.run(output, dict)
  print(result)

  saved_model.simple_save(sess,
                          '/Users/deroneriksson/Documents/workspace5/tf-java-tryout/simple/add_int32',
                          inputs={"input1": input1, "input2": input2},
                          outputs={"output": output})

  sess.close()


def add_int64():
  tf.reset_default_graph()
  sess = tf.Session()
  input1 = tf.placeholder(tf.int64, name="input1")
  input2 = tf.placeholder(tf.int64, name="input2")
  sum = input1 + input2
  output = tf.identity(sum, "output")
  print(input1)
  print(input2)
  print(sum)
  print(output)

  dict = {input1: 1, input2: 2}
  result = sess.run(output, dict)
  print(result)

  saved_model.simple_save(sess,
                          '/Users/deroneriksson/Documents/workspace5/tf-java-tryout/simple/add_int64',
                          inputs={"input1": input1, "input2": input2},
                          outputs={"output": output})

  sess.close()


def add_float32():
  tf.reset_default_graph()
  sess = tf.Session()
  input1 = tf.placeholder(tf.float32, name="input1")
  input2 = tf.placeholder(tf.float32, name="input2")
  sum = input1 + input2
  output = tf.identity(sum, "output")
  print(input1)
  print(input2)
  print(sum)
  print(output)

  dict = {input1: 1.0, input2: 2.0}
  result = sess.run(output, dict)
  print(result)

  saved_model.simple_save(sess,
                          '/Users/deroneriksson/Documents/workspace5/tf-java-tryout/simple/add_float32',
                          inputs={"input1": input1, "input2": input2},
                          outputs={"output": output})

  sess.close()


def add_float64():
  tf.reset_default_graph()
  sess = tf.Session()
  input1 = tf.placeholder(tf.float64, name="input1")
  input2 = tf.placeholder(tf.float64, name="input2")
  sum = input1 + input2
  output = tf.identity(sum, "output")
  print(input1)
  print(input2)
  print(sum)
  print(output)

  dict = {input1: 1.0, input2: 2.0}
  result = sess.run(output, dict)
  print(result)

  saved_model.simple_save(sess,
                          '/Users/deroneriksson/Documents/workspace5/tf-java-tryout/simple/add_float64',
                          inputs={"input1": input1, "input2": input2},
                          outputs={"output": output})

  sess.close()


def add_string():
  tf.reset_default_graph()
  sess = tf.Session()
  input1 = tf.placeholder(tf.string, name="input1")
  input2 = tf.placeholder(tf.string, name="input2")
  sum = input1 + input2
  output = tf.identity(sum, "output")
  print(input1)
  print(input2)
  print(sum)
  print(output)

  dict = {input1: "hello", input2: "world"}
  result = sess.run(output, dict).decode()  # decode the bytestring
  print(result)

  saved_model.simple_save(sess,
                          '/Users/deroneriksson/Documents/workspace5/tf-java-tryout/simple/add_string',
                          inputs={"input1": input1, "input2": input2},
                          outputs={"output": output})


def boolean_logic():
  tf.reset_default_graph()
  sess = tf.Session()
  input1 = tf.placeholder(tf.bool, name="input1")
  input2 = tf.placeholder(tf.bool, name="input2")
  output_and = tf.identity(input1 & input2, "output_and")
  output_or = tf.identity(input1 | input2, "output_or")
  output_not_and = tf.identity(~(input1 & input2), "output_not_and")
  output_not_or = tf.identity(~(input1 | input2), "output_not_or")
  output_xor = tf.identity(input1 ^ input2, "output_xor")
  print(input1)
  print(input2)
  print(output_and)
  print(output_or)
  print(output_not_and)
  print(output_not_or)
  print(output_xor)

  dict = {input1: True, input2: False}
  result = sess.run([output_and, output_or, output_not_and, output_not_or, output_xor], dict)
  print(result)

  saved_model.simple_save(sess,
                          '/Users/deroneriksson/Documents/workspace5/tf-java-tryout/simple/boolean_logic',
                          inputs={"input1": input1, "input2": input2},
                          outputs={"and": output_and, "or": output_or,
                                   "not_and": output_not_and, "not_or": output_not_or,
                                   "xor": output_xor})


if __name__ == "__main__":
  add_int32()
  add_int64()
  add_float32()
  add_float64()
  add_string()
  boolean_logic()
