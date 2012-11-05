package com.tblin.android.base.remote.http;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.tblin.android.base.event.EventDataProcessor;
import com.tblin.android.base.event.EventListener;
import com.tblin.android.base.remote.NetworkAware;

public class HttpExecutor implements Runnable, NetworkAware {
	private static final String TAG = HttpExecutor.class.getName();
	private static final int MAX_QUEUE = 100;
	private static final Data EOF = new Data(null, null, null, null, null, null);
	private volatile boolean running;
	private Thread thread;
	private final BlockingQueue<Data> taskQ;
	private DefaultHttpClient httpClient = new DefaultHttpClient();
	private static int RETRIES_PER_HTTP = 2;
	private final Logger logger = LogManager.getLogger(HttpExecutor.class);
	private boolean network_ready = false;
	private final Object network_event = new Object();
	//private final ReentrantLock network_event = new ReentrantLock();
	
	
	public HttpExecutor() {
		taskQ = new LinkedBlockingQueue<Data>(MAX_QUEUE);		
	}

	public void configure(Map<String, String> conf) {
		// TODO Auto-generated method stub

	}

	public void open() {
		if (running) {
			logger.warn("HttpExecute opened already.");
			// Logger.i(TAG, "can not open");
			return;
		}
		running = true;
		taskQ.clear();
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
			taskQ.put(EOF);
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

	public boolean invoke(String eventName, Object refObj, HttpUriRequest req,
			EventDataProcessor processor, EventListener responseCallback,  HttpResult callback)
			throws InterruptedException {
		Data d = new Data(eventName, refObj, req, processor, responseCallback, callback);
		return taskQ.offer(d, 100, TimeUnit.MILLISECONDS);
	}

	@Override
	public void run() {
		while (running) {
			try {
				Data d = taskQ.take();
				if (EOF == d)
					break;
				
				if (!network_ready) {
					synchronized (network_event) {
						logger.info("Network is not ready, waiting...");
						System.out.println("Network is not ready, waiting...");
						//network_event.lock();
						network_event.wait();
					}
				}
				
				process(d);
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

	public void join(long millis) throws InterruptedException {
		if (null != thread) {
			thread.join(millis);
		}
	}

	private void process(Data d) throws IOException {
		int retries = 0;
		boolean result = false;
		Exception lastException = null;
		HttpResponse resp = null;
		while (retries < RETRIES_PER_HTTP) {
			retries = retries + 1;
			try {
				resp = httpClient.execute(d.req);
				result = true;
				lastException = null;
			} catch (IOException ex) {
				lastException = ex;
				logError(ex);
			} catch (Exception ex) {
				lastException = ex;
				logError(ex);
			}
		}

		// callback with http result
		try {
			if (d.cb != null) {
				d.cb.on_result(result, d.eventName, d.refObj, d.req, resp, d.processor,
						d.responseCallback, lastException);
			}
		} catch (Exception ex) {
			logError(ex);
		}
	}

	private void logError(Exception e2) {
		if (e2 != null && e2.getMessage() != null) {
			// Logger.e(TAG, "http client execute error " + e2.getMessage());
		} else {
			// Logger.e(TAG, "http client execute error no error information");
		}
	}

	private static final class Data {
		protected HttpUriRequest req;
		protected String eventName;
		protected Object refObj;
		protected HttpResult cb;
		protected EventDataProcessor processor;
		protected EventListener responseCallback;

		protected Data(String eventName, Object refObj, HttpUriRequest req, EventDataProcessor processor, 
				EventListener responseCallback, HttpResult cb) {
			this.eventName = eventName;
			this.refObj = refObj;
			this.processor = processor;
			this.req = req;
			this.responseCallback = responseCallback;
			this.cb = cb;

		}
	}

	public interface HttpResult {
		public void on_result(boolean result, String eventName, Object refObj,
				HttpRequest req, HttpResponse resp, EventDataProcessor processor, 
				EventListener responseCallback, Exception ex)
				throws IOException;

	}

	@Override
	public void onNetworkReady() {
		// TODO Auto-generated method stub
		this.network_ready = true;
		synchronized (this.network_event) {
			this.network_event.notify();
			
		}
	}

	@Override
	public void onNetworkSuspended() {
		// TODO Auto-generated method stub
		this.network_ready = false;
	}

}
