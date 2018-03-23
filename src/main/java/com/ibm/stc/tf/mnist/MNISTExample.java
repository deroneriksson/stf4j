package com.ibm.stc.tf.mnist;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.tensorflow.SavedModelBundle;
import org.tensorflow.Session;
import org.tensorflow.Session.Runner;
import org.tensorflow.Tensor;
import org.tensorflow.framework.DataType;
import org.tensorflow.framework.MetaGraphDef;
import org.tensorflow.framework.SignatureDef;
import org.tensorflow.framework.TensorInfo;
import org.tensorflow.framework.TensorShapeProto;
import org.tensorflow.framework.TensorShapeProto.Dim;

import com.google.protobuf.InvalidProtocolBufferException;

/**
 * Try an MNIST saved model created in Tensorflow from Java.
 * 
 * For more Python and model information, see info.txt.
 * 
 */
public class MNISTExample {

	public static final String MNIST_DATA_DIR = "/tmp/mnist_data/";
	public static final String MNIST_SAVED_MODEL_DIR = "./model/";

	public static final String TRAIN_IMAGES = "train-images-idx3-ubyte";
	public static final String TRAIN_LABELS = "train-labels-idx1-ubyte";
	public static final String TEST_IMAGES = "t10k-images-idx3-ubyte";
	public static final String TEST_LABELS = "t10k-labels-idx1-ubyte";

	private static SavedModelBundle model = null;
	private static String mDir = null;

	private static int[] labels = null;
	private static int[][][] images = null;

	public static void main(String[] args) {
		try {

			labels = MNISTUtil.getLabels(MNIST_DATA_DIR + TEST_LABELS);
			images = MNISTUtil.getImages(MNIST_DATA_DIR + TEST_IMAGES);

			tfModel(MNIST_SAVED_MODEL_DIR);

			singlePredictionClasses(0, false, false);
			singlePredictionClasses(1, false, false);
			singlePredictionClasses(2, false, false);
			singlePredictionClasses(3, false, false);
			singlePredictionClasses(4, false, false);

			singlePredictionProbabilities(5, false, false);
			singlePredictionProbabilities(6, false, false);
			singlePredictionProbabilities(7, false, false);
			singlePredictionProbabilities(8, false, false);
			singlePredictionProbabilities(9, false, false);

			singlePredictionClassesProbabilities(10, false, false);
			singlePredictionClassesProbabilities(11, false, false);
			singlePredictionClassesProbabilities(12, false, false);
			singlePredictionClassesProbabilities(13, false, false);
			singlePredictionClassesProbabilities(14, false, false);

			// displaySignatureDefInfo(savedModel);

		} catch (Throwable t) {
			System.out.println(t);
		}
	}

	public static void singlePredictionClasses(int testImageNum, boolean displayImage, boolean displayImageAsText)
			throws IOException {
		float[][] image = getTestImage(testImageNum);
		if (displayImage) {
			displayImage(image);
		}
		if (displayImageAsText) {
			displayImageAsText(image);
		}
		int label = getTestImageLabel(testImageNum);

		Tensor<Float> imageTensor = Tensor.create(image, Float.class);
		int prediction = (int) tfRunner().feed("Placeholder", imageTensor).fetch("ArgMax").run().get(0)
				.expect(Long.class).copyTo(new long[1])[0];

		if (label == prediction) {
			System.out.println(String.format("Success, image #%d classes prediction (%d) matched label (%d)",
					testImageNum, prediction, label));
		} else {
			System.out.println(String.format("Failure, image #%d classes prediction (%d) did not match label (%d)",
					testImageNum, prediction, label));
		}
	}

	public static void singlePredictionProbabilities(int testImageNum, boolean displayImage, boolean displayImageAsText)
			throws IOException {
		float[][] image = getTestImage(testImageNum);
		if (displayImage) {
			displayImage(image);
		}
		if (displayImageAsText) {
			displayImageAsText(image);
		}
		int label = getTestImageLabel(testImageNum);

		Tensor<Float> imageTensor = Tensor.create(image, Float.class);
		Tensor<Float> tResult = tfRunner().feed("Placeholder", imageTensor).fetch("Softmax").run().get(0)
				.expect(Float.class);
		float[][] fResult = tResult.copyTo(new float[1][10]);
		int prediction = 0;
		float maxValue = 0.0f;
		for (int i = 0; i < fResult[0].length; i++) {
			if (fResult[0][i] > maxValue) {
				prediction = i;
				maxValue = fResult[0][1];
			}
		}

		if (label == prediction) {
			System.out.println(String.format("Success, image #%d probabilities prediction (%d) matched label (%d)",
					testImageNum, prediction, label));
		} else {
			System.out
					.println(String.format("Failure, image #%d probabilities prediction (%d) did not match label (%d)",
							testImageNum, prediction, label));
		}
	}

	public static void singlePredictionClassesProbabilities(int testImageNum, boolean displayImage,
			boolean displayImageAsText) throws IOException {
		float[][] image = getTestImage(testImageNum);
		if (displayImage) {
			displayImage(image);
		}
		if (displayImageAsText) {
			displayImageAsText(image);
		}
		int label = getTestImageLabel(testImageNum);

		Tensor<Float> imageTensor = Tensor.create(image, Float.class);

		List<Tensor<?>> result = tfRunner().feed("Placeholder", imageTensor).fetch("ArgMax").fetch("Softmax").run();
		int argMaxPrediction = (int) result.get(0).expect(Long.class).copyTo(new long[1])[0];

		float[][] fResult = result.get(1).expect(Float.class).copyTo(new float[1][10]);
		int softmaxPrediction = 0;
		float maxValue = 0.0f;
		for (int i = 0; i < fResult[0].length; i++) {
			if (fResult[0][i] > maxValue) {
				softmaxPrediction = i;
				maxValue = fResult[0][1];
			}
		}

		if ((label == argMaxPrediction) && (label == softmaxPrediction)) {
			System.out.println(String.format("Success, image #%d label (%d) equals ArgMax (%d) and Softmax (%d)",
					testImageNum, label, argMaxPrediction, softmaxPrediction));
		} else {
			System.out.println(
					String.format("Failure, image #%d label (%d), ArgMax (%d), and Softmax (%d) are not all equal",
							testImageNum, label, argMaxPrediction, softmaxPrediction));
		}
	}

	public static SavedModelBundle tfModel() {
		return model;
	}

	public static SavedModelBundle tfModel(String modelDir) {
		return tfModel(modelDir, "serve");
	}

	public static SavedModelBundle tfModel(String modelDir, String... metaGraphDefTags) {
		if (modelDir == null) {
			return null;
		}
		if (!modelDir.equals(mDir)) {
			model = SavedModelBundle.load(modelDir, metaGraphDefTags);
			mDir = modelDir;
		}
		return model;
	}

	public static Session tfSession() {
		return model.session();
	}

	public static Runner tfRunner() {
		return tfSession().runner();
	}

	public static float[][] getTestImage(int imageNum) throws IOException {
		int[][] iImage = getTestImageAsInts(imageNum);
		float[][] fImage = iToF(iImage);
		return fImage;
	}

	public static int[][] getTestImageAsInts(int imageNum) throws IOException {
		return images[imageNum];
	}

	public static int getTestImageLabel(int imageNum) throws IOException {
		return labels[imageNum];
	}

	public static void displaySignatureDefInfo(SavedModelBundle savedModelBundle)
			throws InvalidProtocolBufferException {
		byte[] metaGraphDefBytes = savedModelBundle.metaGraphDef();
		MetaGraphDef mgd = MetaGraphDef.parseFrom(metaGraphDefBytes);

		Map<String, SignatureDef> sdm = mgd.getSignatureDefMap();
		Set<Entry<String, SignatureDef>> sdmEntries = sdm.entrySet();
		for (Entry<String, SignatureDef> sdmEntry : sdmEntries) {
			System.out.println("\nSignatureDef key: " + sdmEntry.getKey());
			SignatureDef sigDef = sdmEntry.getValue();
			String methodName = sigDef.getMethodName();
			System.out.println("method name: " + methodName);

			System.out.println("inputs:");
			Map<String, TensorInfo> inputsMap = sigDef.getInputsMap();
			Set<Entry<String, TensorInfo>> inputEntries = inputsMap.entrySet();
			for (Entry<String, TensorInfo> inputEntry : inputEntries) {
				System.out.println("  input key: " + inputEntry.getKey());
				TensorInfo inputTensorInfo = inputEntry.getValue();
				DataType inputTensorDtype = inputTensorInfo.getDtype();
				System.out.println("    dtype: " + inputTensorDtype);
				System.out.print("    shape: (");
				TensorShapeProto inputTensorShape = inputTensorInfo.getTensorShape();
				int dimCount = inputTensorShape.getDimCount();
				for (int i = 0; i < dimCount; i++) {
					Dim dim = inputTensorShape.getDim(i);
					long dimSize = dim.getSize();
					if (i > 0) {
						System.out.print(", ");
					}
					System.out.print(dimSize);
				}
				System.out.println(")");
				String inputTensorName = inputTensorInfo.getName();
				System.out.println("    name: " + inputTensorName);
			}

			System.out.println("outputs:");
			Map<String, TensorInfo> outputsMap = sigDef.getOutputsMap();
			Set<Entry<String, TensorInfo>> outputEntries = outputsMap.entrySet();
			for (Entry<String, TensorInfo> outputEntry : outputEntries) {
				System.out.println("  output key: " + outputEntry.getKey());
				TensorInfo outputTensorInfo = outputEntry.getValue();
				DataType outputTensorDtype = outputTensorInfo.getDtype();
				System.out.println("    dtype: " + outputTensorDtype);
				System.out.print("    shape: (");
				TensorShapeProto outputTensorShape = outputTensorInfo.getTensorShape();
				int dimCount = outputTensorShape.getDimCount();
				for (int i = 0; i < dimCount; i++) {
					Dim dim = outputTensorShape.getDim(i);
					long dimSize = dim.getSize();
					if (i > 0) {
						System.out.print(", ");
					}
					System.out.print(dimSize);
				}
				System.out.println(")");
				String inputTensorName = outputTensorInfo.getName();
				System.out.println("    name: " + inputTensorName);
			}
		}

	}

	public static void displayImageAsText(int[][] image) {
		StringBuilder sb = new StringBuilder();
		for (int r = 0; r < image.length; r++) {
			for (int c = 0; c < image[0].length; c++) {
				sb.append(String.format("%3d ", image[r][c]));
			}
			sb.append("\n");
		}
		System.out.println(sb.toString());
	}

	public static void displayImageAsText(float[][] image) {
		int[][] iImage = fToI(image);
		displayImageAsText(iImage);
	}

	public static float[][] iToF(int[][] image) {
		float[][] fImage = new float[image.length][image[0].length];
		for (int r = 0; r < image.length; r++) {
			for (int c = 0; c < image[0].length; c++) {
				fImage[r][c] = image[r][c];
			}
		}
		return fImage;
	}

	public static int[][] fToI(float[][] image) {
		int[][] iImage = new int[image.length][image[0].length];
		for (int r = 0; r < image.length; r++) {
			for (int c = 0; c < image[0].length; c++) {
				iImage[r][c] = (int) image[r][c];
			}
		}
		return iImage;
	}

	public static float[][][] f2ToF3(float[][] image) {
		float[][][] fImage = new float[1][image.length][image[0].length];
		for (int r = 0; r < image.length; r++) {
			for (int c = 0; c < image[0].length; c++) {
				fImage[0][r][c] = image[r][c];
			}
		}
		return fImage;
	}

	public static void displayImage(int[][] image) {
		BufferedImage bi = iToBuff(image);
		displayBufferedImage(bi);
	}

	public static void displayImage(float[][] image) {
		int[][] iImage = fToI(image);
		displayImage(iImage);
	}

	public static void displayBufferedImage(BufferedImage bi) {
		JFrame jframe = new JFrame();
		JLabel jlabel = new JLabel(new ImageIcon(bi));
		jframe.getContentPane().add(jlabel);
		jframe.setSize(bi.getWidth() + 50, bi.getHeight() + 50);
		jframe.setVisible(true);
		jframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	public static BufferedImage iToBuff(int[][] i) {
		int cols = i[0].length;
		int rows = i.length;

		// convert pixels to rgb colorspace values
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < cols; c++) {
				i[r][c] = 255 - i[r][c]; // invert colors
				i[r][c] = 256 * 256 * i[r][c] + 256 * i[r][c] + i[r][c];
			}
		}

		BufferedImage bi = new BufferedImage(cols, rows, BufferedImage.TYPE_INT_RGB);
		for (int y = 0; y < rows; y++) {
			int[] row = i[y];
			bi.setRGB(0, y, cols, 1, row, 0, 1);
		}
		return bi;
	}

}
