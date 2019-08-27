package marcWeiss.sTracker.component.helper;

public class CoreException extends RuntimeException{

	private static final long serialVersionUID = -3571264726571736005L;

	public CoreException() {
	}

	public CoreException(String message) {
		super(message);
	}

	public CoreException(Throwable cause) {
		super(cause);
	}

	public CoreException(String message, Throwable cause) {
		super(message, cause);
	}

	public CoreException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
