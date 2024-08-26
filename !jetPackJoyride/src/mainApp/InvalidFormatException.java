package mainApp;

/**
 * Class: InvalidFormatException
 * @author W24_A304
 * <br>Purpose: An error that indicates a file is of invalid format for displaying
 * <br>Restrictions: None
 */
public class InvalidFormatException extends Exception {

	public InvalidFormatException() {
	}

	public InvalidFormatException(String message) {
		super(message);
		
	}

	public InvalidFormatException(Throwable cause) {
		super(cause);
		
	}

	public InvalidFormatException(String message, Throwable cause) {
		super(message, cause);
		
	}

	public InvalidFormatException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
