package pv.dotai.datai.message;

public interface MessageHandler<T> {
	void handle(T m);
}
