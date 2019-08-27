package marcWeiss.sTracker.component.helper;

public class MissingFormObjectException extends CoreException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1542282776872491141L;

	public MissingFormObjectException() {
		super();
	}

	public MissingFormObjectException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public MissingFormObjectException(String message, Throwable cause) {
		super(message, cause);
	}

	public MissingFormObjectException(String message) {
		super(message);
	}

	public MissingFormObjectException(Throwable cause) {
		super(cause);
	}

}
