package org.codait.tf;

/**
 * RuntimeException representing that a problem occurred in the API.
 *
 */
public class TFException extends RuntimeException {

	private static final long serialVersionUID = -6854658830095644446L;

	public TFException() {
		super();
	}

	public TFException(String message) {
		super(message);
	}

	public TFException(String message, Throwable cause) {
		super(message, cause);
	}

	public TFException(Throwable cause) {
		super(cause);
	}

}
