package pv.dotai.datai.message;

/**
 * Interface to implement when making a message handler
 */
public interface MessageHandler<T> {
	void handle(T m);
}
