package com.tblin.android.base.baseservice;

import java.util.Map;

public interface ServiceEventHandler {
	void subscribe(EventListener listener);
	void unsubscribe(EventListener listener);
	
	void fireEvent(final Map<String, Object> event);
}
