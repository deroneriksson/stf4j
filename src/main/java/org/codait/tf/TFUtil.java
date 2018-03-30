package org.codait.tf;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.tensorflow.SavedModelBundle;
import org.tensorflow.Tensor;
import org.tensorflow.framework.DataType;
import org.tensorflow.framework.MetaGraphDef;
import org.tensorflow.framework.SignatureDef;
import org.tensorflow.framework.TensorInfo;
import org.tensorflow.framework.TensorShapeProto;
import org.tensorflow.framework.TensorShapeProto.Dim;

import com.google.protobuf.InvalidProtocolBufferException;

public class TFUtil {

	public static void displaySignatureDefInfo(SavedModelBundle savedModelBundle)
			throws InvalidProtocolBufferException {
		displaySignatureDefInfo(savedModelBundle.metaGraphDef());
	}

	public static void displaySignatureDefInfo(byte[] metaGraphDefBytes) throws InvalidProtocolBufferException {
		MetaGraphDef mgd = MetaGraphDef.parseFrom(metaGraphDefBytes);
		displaySignatureDefInfo(mgd);
	}

	public static void displaySignatureDefInfo(MetaGraphDef mgd) throws InvalidProtocolBufferException {
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

	public static Tensor<?> convertToTensor(String name, Object value, MetaGraphDef mgd) {
		System.out.println("class:" + value.getClass().toString());
		System.out.println("class:" + value.getClass().getTypeName());
		return null;
	}

	public static String inputKeyToName(String key, MetaGraphDef metaGraphDef) {
		Map<String, SignatureDef> sdm = metaGraphDef.getSignatureDefMap();
		Set<Entry<String, SignatureDef>> sdmEntries = sdm.entrySet();
		for (Entry<String, SignatureDef> sdmEntry : sdmEntries) {
			SignatureDef sigDef = sdmEntry.getValue();
			Map<String, TensorInfo> inputsMap = sigDef.getInputsMap();
			if (inputsMap.containsKey(key)) {
				TensorInfo tensorInfo = inputsMap.get(key);
				String inputName = tensorInfo.getName();
				return inputName;
			}
		}
		throw new TFException("Input key '" + key + "' not found in MetaGraphDef");
	}

	public static String outputKeyToName(String key, MetaGraphDef metaGraphDef) {
		Map<String, SignatureDef> sdm = metaGraphDef.getSignatureDefMap();
		Set<Entry<String, SignatureDef>> sdmEntries = sdm.entrySet();
		for (Entry<String, SignatureDef> sdmEntry : sdmEntries) {
			SignatureDef sigDef = sdmEntry.getValue();
			Map<String, TensorInfo> outputsMap = sigDef.getOutputsMap();
			if (outputsMap.containsKey(key)) {
				TensorInfo tensorInfo = outputsMap.get(key);
				String outputName = tensorInfo.getName();
				return outputName;
			}
		}
		throw new TFException("Output key '" + key + "' not found in MetaGraphDef");
	}

}
