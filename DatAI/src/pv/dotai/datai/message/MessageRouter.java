package pv.dotai.datai.message;

import java.util.HashMap;
import java.util.Map;

public class MessageRouter {

    private final Map<Class<?>, MessageHandler> routes = new HashMap<>();
    
	public<T> void sendMessage(final T m) {
    		if(routes.get(m.getClass()) != null) {
    			routes.get(m.getClass())	.handle(m);
    		}
    }

    public<T> void registerHandler(Class<T> eventClass, MessageHandler<T> handler) {
        routes.put(eventClass, handler);
    }
	
}