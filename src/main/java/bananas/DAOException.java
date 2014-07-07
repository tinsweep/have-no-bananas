package bananas;

public class DAOException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	/**
	 * @author Bryan
	 */
//throws an exception with a detail message
    public DAOException(String message) {
        super(message);
    }
//throws an exception with the root cause
    public DAOException(Throwable cause) {
        super(cause);
    }
//throws an exception with a detail message and the root cause
    public DAOException(String message, Throwable cause) {
        super(message, cause);
    }
}
