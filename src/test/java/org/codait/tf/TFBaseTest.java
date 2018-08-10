package org.codait.tf;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * Abstract base test class for common test functionality.
 *
 */
public abstract class TFBaseTest {

	protected static Logger log = LogManager.getLogger(TFBaseTest.class);

	/**
	 * Display expected and predicted values at log level of debug.
	 * 
	 * @param expected
	 *            expected value
	 * @param prediction
	 *            predicted value
	 */
	protected void displayDebug(double expected, double prediction) {
		log.debug(String.format("Expected: %f, Prediction: %f", expected, prediction));
	}

	/**
	 * Display expected and predicted values at log level of debug.
	 * 
	 * @param expected
	 *            array of expected values
	 * @param predictions
	 *            array of predicted values
	 */
	protected void displayDebug(double[] expected, double[] predictions) {
		for (int i = 0; i < expected.length; i++) {
			double exp = expected[i];
			double prediction = predictions[i];
			displayDebug(exp, prediction);
		}
	}

	/**
	 * Display expected and predicted values at log level of debug.
	 * 
	 * @param expected
	 *            expected value
	 * @param prediction
	 *            predicted value
	 */
	protected void displayDebug(float expected, float prediction) {
		log.debug(String.format("Expected: %f, Prediction: %f", expected, prediction));
	}

	/**
	 * Display expected and predicted values at log level of debug.
	 * 
	 * @param expected
	 *            array of expected values
	 * @param predictions
	 *            array of predicted values
	 */
	protected void displayDebug(float[] expected, float[] predictions) {
		for (int i = 0; i < expected.length; i++) {
			float exp = expected[i];
			float prediction = predictions[i];
			displayDebug(exp, prediction);
		}
	}

	/**
	 * Display expected and predicted values at log level of debug.
	 * 
	 * @param expected
	 *            array of expected values
	 * @param predictions
	 *            array of predicted values
	 */
	protected void displayDebug(int[] expected, int[] predictions) {
		for (int i = 0; i < expected.length; i++) {
			long exp = expected[i];
			long prediction = predictions[i];
			displayDebug(exp, prediction);
		}
	}

	/**
	 * Display labels, classes predictions, and probabilities predictions at log level of debug.
	 * 
	 * @param labels
	 *            the labels
	 * @param cPredictions
	 *            the classes predictions
	 * @param pPredictions
	 *            the probabilities predictions
	 */
	protected void displayDebug(int[] labels, int[] cPredictions, int[] pPredictions) {
		for (int i = 0; i < labels.length; i++) {
			displayDebug(labels[i], cPredictions[i], pPredictions[i]);
		}
	}

	/**
	 * Display expected and predicted values at log level of debug.
	 * 
	 * @param expected
	 *            expected value
	 * @param prediction
	 *            predicted value
	 */
	protected void displayDebug(long expected, long prediction) {
		log.debug(String.format("Expected: %d, Prediction: %d", expected, prediction));
	}

	/**
	 * Display label, classes prediction, and probabilities prediction at log level of debug.
	 * 
	 * @param label
	 *            the label
	 * @param cPrediction
	 *            the classes prediction
	 * @param pPrediction
	 *            the probabilities prediction
	 */
	protected void displayDebug(long label, long cPrediction, long pPrediction) {
		log.debug(String.format("Label: %d, Classes Prediction: %d, Probabilities Prediction: %d", label, cPrediction,
				pPrediction));
	}

	/**
	 * Display expected and predicted values at log level of debug.
	 * 
	 * @param expected
	 *            array of expected values
	 * @param predictions
	 *            array of predicted values
	 */
	protected void displayDebug(long[] expected, long[] predictions) {
		for (int i = 0; i < expected.length; i++) {
			long exp = expected[i];
			long prediction = predictions[i];
			displayDebug(exp, prediction);
		}
	}

	/**
	 * Display expected and predicted values at log level of debug.
	 * 
	 * @param expected
	 *            expected value
	 * @param prediction
	 *            predicted value
	 */
	protected void displayDebug(String expected, String prediction) {
		log.debug(String.format("Expected: %s, Prediction: %s", expected, prediction));
	}

	/**
	 * Display expected and predicted values at log level of debug.
	 * 
	 * @param expected
	 *            array of expected values
	 * @param predictions
	 *            array of predicted values
	 */
	protected void displayDebug(String[] expected, String[] predictions) {
		for (int i = 0; i < expected.length; i++) {
			String exp = expected[i];
			String prediction = predictions[i];
			displayDebug(exp, prediction);
		}
	}

}
