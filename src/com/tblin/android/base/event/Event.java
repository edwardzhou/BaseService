package com.tblin.android.base.event;

import java.util.HashMap;
import java.util.Map;


public class Event {

	public static final String EVENT_ITEM_RESULT = "result";
	public static final String EVENT_ITEM_RESULT_CODE = "result_code";
	public static final String EVENT_ITEM_HTTP_STATUS_CODE = "http_status_code";
	public static final String EVENT_ITEM_DATA = "data";
	public static final String EVENT_ITEM_RAW_DATA = "raw_data";
	public static final String EVENT_ITEM_EXCEPTION = "exception";
	public static final String EVENT_ITEM_NAME = "event_name";
	public static final String EVENT_ITEM_CALLBACK = "callback";
	public static final String EVENT_ITEM_DISPATCHER = "dispatcher";
	public static final String EVENT_ITEM_EVENT_DATA_PROCESSOR = "event_data_processor";
	public static final String EVENT_ITEM_REF_OBJ = "ref_obj";

	private Map<String, Object> data = new HashMap<String, Object>();

	public boolean getResult() {
		Object obj = data.get(EVENT_ITEM_RESULT);
		return (null == obj) ? false : ((Boolean) obj).booleanValue();
	}

	public void setResult(boolean result) {
		data.put(EVENT_ITEM_RESULT, Boolean.valueOf(result));
	}

	public int getResultCode() {
		Object obj = data.get(EVENT_ITEM_RESULT_CODE);
		return (null == obj) ? -1 : ((Integer) obj).intValue();
	}

	public void setResultCode(int result_code) {
		data.put(EVENT_ITEM_RESULT_CODE, Integer.valueOf(result_code));
	}

	public int getHttpStatusCode() {
		Object obj = data.get(EVENT_ITEM_HTTP_STATUS_CODE);
		return (null == obj) ? -1 : ((Integer) obj).intValue();
	}

	public void setHttpStatusCode(int status_code) {
		data.put(EVENT_ITEM_HTTP_STATUS_CODE, Integer.valueOf(status_code));
	}

	public String getEventName() {
		Object obj = data.get(EVENT_ITEM_NAME);
		return (null == obj) ? null : (String) obj;
	}

	public void setEventName(String name) {
		data.put(EVENT_ITEM_NAME, name);
	}
	
	public String getRawData() {
		Object obj = data.get(EVENT_ITEM_RAW_DATA);
		return (null == obj) ? null : (String) obj;
	}

	public void setRawData(String rawData) {
		data.put(EVENT_ITEM_RAW_DATA, rawData);
	}
	
	public Object getData() {
		return data.get(EVENT_ITEM_DATA);
	}
	
	public void setData(Object obj) {
		data.put(EVENT_ITEM_DATA, obj);
	}
	
	public Exception getException() {
		Object obj = data.get(EVENT_ITEM_EXCEPTION);
		return (null == obj) ? null : (Exception) obj;
	}
	
	public void setException(Exception ex) {
		data.put(EVENT_ITEM_DATA, ex);
	}
	
	public Object getRefObj() {
		return data.get(EVENT_ITEM_REF_OBJ);
	}
	
	public void setRefObj(Object obj) {
		data.put(EVENT_ITEM_REF_OBJ, obj);
	}

	public EventDispatcher getDispatch() {
		Object obj = data.get(EVENT_ITEM_DISPATCHER);
		
		return (null == obj) ? null : (EventDispatcher) obj;		
	}
	
	public void setDispatch(EventDispatcher dispatch) {
		data.put(EVENT_ITEM_DISPATCHER, dispatch);
	}

	public EventListener getCallback() {
		Object obj = data.get(EVENT_ITEM_CALLBACK);
		
		return (null == obj) ? null : (EventListener) obj;		
	}
	
	public void setCallback(EventListener callback) {
		data.put(EVENT_ITEM_CALLBACK, callback);
	}

	public EventDataProcessor getEventDataProcessor() {
		Object obj = data.get(EVENT_ITEM_EVENT_DATA_PROCESSOR);
		
		return (null == obj) ? null : (EventDataProcessor) obj;		
	}
	
	public void setEventDataProcessor(EventDataProcessor callback) {
		data.put(EVENT_ITEM_EVENT_DATA_PROCESSOR, callback);
	}

	public Object get(String key) {
		return data.get(key);
	}
	
	public void put(String key, Object value) {
		data.put(key, value);
	}

}
