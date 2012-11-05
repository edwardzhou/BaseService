package com.tblin.android.base.remote;

import com.tblin.android.base.event.EventListener;

public interface EventSubscription {
	void subscribe(String eventName, EventListener listener);
	void unsubscribe(String eventName, EventListener listener);
}
