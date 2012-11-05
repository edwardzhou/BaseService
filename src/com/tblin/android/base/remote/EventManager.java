package com.tblin.android.base.remote;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.tblin.android.base.event.Event;
import com.tblin.android.base.event.EventDataProcessor;
import com.tblin.android.base.event.EventDispatcher;
import com.tblin.android.base.event.EventListener;

public class EventManager implements EventDispatcher, EventSubscription, Runnable {

	private Map<String, List<EventListener>> event_list = null;
	private Map<String, List<EventListener>> wildcast_event_list = null;
	private final BlockingQueue<Event> eventQ;
	private final Event EOF = new Event();
	private final Logger logger = LogManager.getLogger(EventManager.class); 
	private boolean running = false;
	private Thread thread = null;
	
	private static EventManager instance = null;
	
	protected EventManager() {
		eventQ = new LinkedBlockingQueue<Event>();
		event_list = new HashMap<String, List<EventListener>>();
		wildcast_event_list = new HashMap<String, List<EventListener>>();
	}
	
	public static EventManager getInstance() {
		if (null != instance)
			return instance;
		
		instance = new EventManager();
		return instance;
	}
	
	public void open() {
		if (running) {
			logger.debug("event manager opened already.");
			// Logger.i(TAG, "can not open");
			return;
		}
		running = true;
		eventQ.clear();
		thread = new Thread(this);
		thread.start();
	}
	
	public void close() {
		if (!running) {
			// Logger.i(TAG, "can not close");
			return;
		}
		running = false;
		try {
			eventQ.put(EOF);
		} catch (InterruptedException e) {

		}
		if (null != thread) {
			try {
				thread.join(100);
			} catch (InterruptedException e) {

			}
			thread.interrupt();
			thread = null;
		}
	}
	
	@Override
	public void subscribe(String eventName, EventListener listener) {
		List<EventListener> listeners = getEventListeners(eventName, true);
		
		if (!listeners.contains(listener)) {
			listeners.add(listener);
		} else {
			// TODO: log debug info to say duplicated subscribe
		}
		
	}

	@Override
	public void unsubscribe(String eventName, EventListener listener) {
		List<EventListener> listeners = getEventListeners(eventName, false);
		
		if (listeners != null && listeners.contains(listener)) {
			listeners.remove(listener);
		}
		
	}

	@Override
	public void dispatch(Event eventData) {
		this.eventQ.offer(eventData);
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (running) {
			try {
				Event eventData = eventQ.take();
				if (EOF == eventData)
					break;
				process(eventData);
			} catch (InterruptedException e) {
				running = false;
				break;
			} catch (Exception e) {
				// Logger.e(TAG, "http fetcher exception is handling");
				// Logger.e(TAG, "exception " + e);
				if (e.getMessage() == null) {
					StackTraceElement[] traces = e.getStackTrace();
					if (traces == null || traces.length == 0) {
						// Logger.e(TAG, "NULL POINT EXCEPTION WITHOUT TRACE");
					} else {
						// Logger.e(TAG, "trace length is " + traces.length);
						for (int i = 0; i < traces.length; i++) {
							// Logger.e(TAG, i + ":" + traces[i].toString());
						}
						// Logger.e(TAG, "trace print finish");
					}
				} else {
					// Logger.e(TAG, e.getMessage());
				}
			}
		}

	}

	
	private List<EventListener> getEventListeners(String eventName, boolean createOnNotFound) {
		List<EventListener> listeners = null;
		
		if (!eventName.endsWith("*")) {
			listeners = event_list.get(eventName);
			if (null == listeners && createOnNotFound ) {
				listeners = new ArrayList<EventListener>();
				event_list.put(eventName, listeners);
			}
		} else {
			String wildcast_event_name = eventName.substring(0, eventName.length()-1);
			listeners = wildcast_event_list.get(wildcast_event_name);
			if (null == listeners && createOnNotFound) {
				listeners = new ArrayList<EventListener>();
				wildcast_event_list.put(wildcast_event_name, listeners);
			}
		}
		
		return listeners;
	}

	protected void process(Event eventData) {
		String eventName = eventData.getEventName();
		EventListener responseCallback = eventData.getCallback();
		EventDataProcessor processor = eventData.getEventDataProcessor();
		if (null != processor) {
			try {
				processor.process(eventData);
			} catch (Exception ex) {
				
			}
		}
		fireEvents(eventName, responseCallback, eventData);
	}

	protected void fireEvents(String eventName, EventListener responseCallback,
			Event eventData) {

		fireEvent(eventName, responseCallback, eventData);
		
		// invoke subscription listeners
		List<EventListener> listeners;
		
		listeners = event_list.get(eventName);
		if (listeners != null) {
			fireEvents(listeners, eventData);
		}
		
		fireWildcastEvents(eventName, eventData);		
	}
	
	private void fireWildcastEvents(String eventName, Event eventData) {
		List<EventListener> listeners = null;
		
		wildcast_event_list.entrySet().iterator();
		Iterator<Entry<String, List<EventListener>>> entryIterator = wildcast_event_list.entrySet().iterator();
		
		while (entryIterator.hasNext()) {
			Entry<String, List<EventListener>> entry = entryIterator.next();
			String wildcast_event_name = entry.getKey();
			if( eventName.startsWith(wildcast_event_name) ) {
				listeners = entry.getValue();
				fireEvents(listeners, eventData);
			}
		}
	}

	private void fireEvents(List<EventListener> listeners, Event eventData) {
		// record unused listeners
		ArrayList<EventListener> unused_listeners = null;

		for (EventListener listener : listeners) {
			try {
				listener.handle(eventData);
			} catch (NullPointerException ex) {
				if (null == unused_listeners) {
					unused_listeners = new ArrayList<EventListener>();
				}
				unused_listeners.add(listener);
			} catch (Exception ex) {
				// TODO: log ex
				ex.printStackTrace();
			}
		}

		// clean up unused listerners.
		if (null != unused_listeners) {
			for (EventListener listener : unused_listeners) {
				listeners.remove(listener);
			}
		}
	}
	
	private boolean fireEvent(String eventName, EventListener responseCallback,
			Event eventData) {		
		boolean result = false; 
		
		// invoke on-call listener;
		if (null != responseCallback) {
			try {
				responseCallback.handle(eventData);
				result = true;
				
			} catch (Exception ex) {
				
			}
		}
		
		return result;
	}


	
	

}
