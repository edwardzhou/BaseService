package com.tblin.android.base.remote.http;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.google.inject.Singleton;
import com.tblin.android.base.event.Event;
import com.tblin.android.base.event.EventDataProcessor;
import com.tblin.android.base.event.EventDispatcher;
import com.tblin.android.base.event.EventListener;
import com.tblin.android.base.remote.http.HttpExecutor.HttpResult;

@Singleton
public class HttpEngineImpl implements HttpEngine {

	private String mode = "prod";

	private HttpExecutor httpExecutor = null;
	private HttpResult on_http_result = null;
	private EventDispatcher eventDispatcher = null;
	private static HttpEngineImpl instance = null;

	public HttpEngineImpl() {
		httpExecutor = new HttpExecutor();
		on_http_result = createHttpResultCallback();
	}

//	public static HttpEngineImpl getInstance() {
//		if (null != instance)
//			return instance;
//
//		instance = new HttpEngineImpl();
//		return instance;
//	}

	@Override
	public void open() {
		httpExecutor.open();
	}

	@Override
	public void close() {
		httpExecutor.close();
	}


	@Override
	public void configure(Map<String, String> conf) {
		// event_list = new HashMap<String, List<EventListener>>();
		// wildcast_event_list = new HashMap<String, List<EventListener>>();
		this.httpExecutor.configure(conf);
	}

	@Override
	public EventDispatcher getEventDispatcher() {
		return eventDispatcher;
	}

	@Override
	public void setEventDispatcher(EventDispatcher eventDispatcher) {
		this.eventDispatcher = eventDispatcher;
	}

	@Override
	public void doGet(String url, List<BasicNameValuePair> params,
			String eventName, Object refObj, EventDataProcessor processor,
			EventListener responseCallback) {
		// HttpParams httpParams = request.getParams();
		boolean hasMode = false;
		StringBuffer sb = new StringBuffer(url);
		sb.append("?");

		if (params != null) {
			for (BasicNameValuePair nvp : params) {
				sb.append(nvp.getName()).append("=")
						.append(URLEncoder.encode(nvp.getValue())).append("&");
				// httpParams.setParameter(nvp.getName(), nvp.getValue());
				if (nvp.getName().equals("mode")) {
					hasMode = true;
				}
			}
		}

		if (!hasMode)
			try {
				sb.append("mode").append("=").append(URLEncoder.encode(this.mode, "UTF-8"));
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		String targetUrl = sb.toString();

		HttpGet request = new HttpGet(targetUrl);

		try {
			this.httpExecutor.invoke(eventName, refObj, request, processor,
					responseCallback, on_http_result);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void doPost(String url, List<BasicNameValuePair> params,
			String eventName, Object refObj, EventDataProcessor processor,
			EventListener responseCallback) {
		HttpPost request = new HttpPost(url);

		boolean hasMode = false;
		for (BasicNameValuePair nvp : params) {
			if (nvp.getName().equals("mode")) {
				hasMode = true;
			}
		}

		if (!hasMode) {
			params.add(new BasicNameValuePair("mode", this.mode));
		}

		try {
			request.setEntity(new UrlEncodedFormEntity(params));
			this.httpExecutor.invoke(eventName, refObj, request, processor,
					responseCallback, on_http_result);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private HttpResult createHttpResultCallback() {
		// TODO Auto-generated method stub
		return new HttpResult() {

			@Override
			public void on_result(boolean result, String eventName,
					Object refObj, HttpRequest req, HttpResponse resp,
					EventDataProcessor processor,
					EventListener responseCallback, Exception ex)
					throws IOException {
				// TODO Auto-generated method stub
				Event eventData = new Event();
				eventData.setResult(result);
				int statusCode = 0;
				if (resp != null) {
					statusCode = resp.getStatusLine().getStatusCode();
				}
				eventData.setEventName(eventName);
				eventData.setHttpStatusCode(statusCode);
				eventData.setException(ex);
				String raw_data = EntityUtils.toString(resp.getEntity());
				eventData.setRawData(raw_data);
				eventData.setData(raw_data);
				eventData.setEventDataProcessor(processor);
				eventData.setCallback(responseCallback);
				eventData.setDispatch(eventDispatcher);

				if (eventDispatcher != null)
					eventDispatcher.dispatch(eventData);
			}
		};
	}

	@Override
	public void onNetworkReady() {
		// TODO Auto-generated method stub
		httpExecutor.onNetworkReady();
	}

	@Override
	public void onNetworkSuspended() {
		// TODO Auto-generated method stub
		httpExecutor.onNetworkSuspended();
	}

}
