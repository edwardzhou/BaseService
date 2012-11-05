package com.tblin.android.base.event;


public interface EventListener {
	
	String getFullName();
	String getName();

	void handle(final Event eventData);
	
}
