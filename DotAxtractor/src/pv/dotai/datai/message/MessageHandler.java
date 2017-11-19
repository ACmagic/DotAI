package pv.dotai.datai.message;

import com.google.protobuf.AbstractMessage;

public interface MessageHandler<T extends AbstractMessage> {
	void handle(T message);
}
