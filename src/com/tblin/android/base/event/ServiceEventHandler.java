package com.tblin.android.base.event;


public interface ServiceEventHandler {
	void subscribe(EventListener listener);
	void unsubscribe(EventListener listener);
	
	void fireEvent(final Event event);
}
