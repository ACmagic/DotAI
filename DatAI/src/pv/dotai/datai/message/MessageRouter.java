package pv.dotai.datai.message;

import java.util.HashMap;
import java.util.Map;

/**
 * Message router to send the read messages to their right handler
 */
public class MessageRouter {

    private final Map<Class<?>, MessageHandler> routes = new HashMap<>();
    
    /**
     * Sends a message to it's right handler (if registered)
     * @param m message to send
     */
	public<T> void sendMessage(final T m) {
    		if(routes.get(m.getClass()) != null) {
    			routes.get(m.getClass())	.handle(m);
    		}
    }

	/**
	 * Registers a handler for a certain message type
	 * @param eventClass Type of message to handle
	 * @param handler instance of the handler
	 */
    public<T> void registerHandler(Class<T> eventClass, MessageHandler<T> handler) {
        routes.put(eventClass, handler);
    }
	
}
