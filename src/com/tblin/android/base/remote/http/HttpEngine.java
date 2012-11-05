package com.tblin.android.base.remote.http;

import java.util.List;
import java.util.Map;

import org.apache.http.message.BasicNameValuePair;

import com.tblin.android.base.event.EventDataProcessor;
import com.tblin.android.base.event.EventDispatcher;
import com.tblin.android.base.event.EventListener;
import com.tblin.android.base.remote.NetworkAware;

public interface HttpEngine extends NetworkAware {
	
	void open();
	
	void close();
	
	void configure(Map<String, String> conf);

	EventDispatcher getEventDispatcher();

	void setEventDispatcher(EventDispatcher eventDispatcher);

	void doGet(String url, List<BasicNameValuePair> params,
			String eventName, Object refObj, EventDataProcessor processor,
			EventListener responseCallback);
	void doPost(String url, List<BasicNameValuePair> params,
			String eventName, Object refObj, EventDataProcessor processor,
			EventListener responseCallback);
}
