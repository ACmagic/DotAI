package pv.dotai.datai;

/**
 * Exception class to throw when the replay seems illegally formated or unparsable
 * @author Thomas Ibanez
 * @since  1.0
 */
public class ReplayException extends RuntimeException {

	private static final long serialVersionUID = 4906584936425897626L;

	public ReplayException(String s) {
		super(s);
	}
	
	@Override
	public String getMessage() {
		return super.getMessage();
	}
	
}
