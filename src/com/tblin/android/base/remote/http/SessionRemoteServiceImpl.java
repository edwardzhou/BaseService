package com.tblin.android.base.remote.http;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.tblin.android.base.domain.Settings;
import com.tblin.android.base.event.Event;
import com.tblin.android.base.event.EventDataProcessor;
import com.tblin.android.base.event.EventListener;
import com.tblin.android.base.event.ServiceEventHandler;
import com.tblin.android.base.event.ServiceEventHandlerImpl;
import com.tblin.android.base.remote.EventManager;
import com.tblin.android.base.remote.SessionRemoteService;

public class SessionRemoteServiceImpl extends AbstractHttpService implements SessionRemoteService {
	private final static String EVENT_LOGIN = "session.on_login";

	private ServiceEventHandler _onLoginHandler = new ServiceEventHandlerImpl();
	
	private EventListener _eventLoginListener = null;
	private EventDataProcessor sessionLoginEventDataProcessor = null;
	
	public SessionRemoteServiceImpl() {
		setupListeners();
		EventManager.getInstance().subscribe(EVENT_LOGIN, _eventLoginListener);
	}
	
	private void setupListeners() {
		_eventLoginListener = createLoginListener();
	}

	private EventListener createLoginListener() {
		EventListener listener = new EventListener() {
			
			@Override
			public void handle(Event eventData) {
				_onLoginHandler.fireEvent(eventData);
			}
			
			@Override
			public String getName() {
				return "on_login_listener";
			}
			
			@Override
			public String getFullName() {
				return SessionRemoteServiceImpl.class.getName() + "." + getName();
			}
		}; 
		
		return listener;
	}

	@Override
	public void login(String loginId, String password, EventListener callback) {
		
		String url = getLoginUrl();
		
		List<BasicNameValuePair> params = getLoginParams(loginId, password);
		this.getHttpEngine().doPost(url, params, EVENT_LOGIN, null, getSessionLoginEventDataProcessor(), null);
		//this.getHttpEngine().doPost(url, params, eventName, refObj, processor, responseCallback)
	}

	private List<BasicNameValuePair> getLoginParams(String loginId, String password) {
		List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();

		params.add( new BasicNameValuePair("msisdn", "18620318415") );
		params.add( new BasicNameValuePair("imsi", "460014316640456") );
		params.add( new BasicNameValuePair("mac", "044665CD5D0A") );
		
		return params;
	}

	private String getLoginUrl() {
		String loginUrl = combineUrl(Settings.SERVER_URL, "login.json");
		
		return loginUrl;
	}

	@Override
	public ServiceEventHandler onLogin() {
		return _onLoginHandler;
	}

	public EventDataProcessor getSessionLoginEventDataProcessor() {
		return sessionLoginEventDataProcessor;
	}

	@Inject
	public void setSessionLoginEventDataProcessor(@Named("SessionLoginEventDataProcess")
			EventDataProcessor sessionLoginEventDataProcessor) {
		this.sessionLoginEventDataProcessor = sessionLoginEventDataProcessor;
	}


}
