package com.tblin.android.base.event;

import java.util.ArrayList;
import java.util.List;


public class ServiceEventHandlerImpl implements ServiceEventHandler {
	
	private List<EventListener> listeners = new ArrayList<EventListener>();

	@Override
	public synchronized void subscribe(EventListener listener) {
		if (!listeners.contains(listener))
			listeners.add(listener);
	}

	@Override
	public synchronized void unsubscribe(EventListener listener) {
		if (listeners.contains(listener))
			listeners.remove(listener);
	}

	@Override
	public synchronized void fireEvent(final Event event) {
		List<EventListener> invalid_listeners = null;
		for (EventListener listener: listeners) {
			try {
				listener.handle(event);
			} catch (NullPointerException ex) {
				if (null == invalid_listeners) {
					invalid_listeners = new ArrayList<EventListener>();
				}
				invalid_listeners.add(listener);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		
		if (null != invalid_listeners) {
			listeners.removeAll(invalid_listeners);
		}
	}
	
	

}
