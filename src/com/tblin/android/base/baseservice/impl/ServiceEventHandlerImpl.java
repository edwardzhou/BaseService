package com.tblin.android.base.baseservice.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.tblin.android.base.baseservice.EventListener;
import com.tblin.android.base.baseservice.ServiceEventHandler;

public class ServiceEventHandlerImpl implements ServiceEventHandler {
	
	private List<EventListener> listeners = new ArrayList<EventListener>();

	@Override
	public void subscribe(EventListener listener) {
		if (!listeners.contains(listener))
			listeners.add(listener);
	}

	@Override
	public void unsubscribe(EventListener listener) {
		if (listeners.contains(listener))
			listeners.remove(listener);
	}

	@Override
	public void fireEvent(final Map<String, Object> event) {
		for (EventListener listener: listeners) {
			try {
				listener.handle(event);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
	
	

}
