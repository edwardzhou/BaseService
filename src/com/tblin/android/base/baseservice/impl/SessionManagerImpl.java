package com.tblin.android.base.baseservice.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.tblin.android.base.baseservice.FriendshipService;
import com.tblin.android.base.baseservice.SessionManager;
import com.tblin.android.base.domain.UserSession;
import com.tblin.android.base.event.Event;
import com.tblin.android.base.event.EventListener;
import com.tblin.android.base.event.ServiceEventHandler;
import com.tblin.android.base.event.ServiceEventHandlerImpl;
import com.tblin.android.base.remote.SessionRemoteService;

@Singleton
public class SessionManagerImpl implements SessionManager {
	
	private final ServiceEventHandler _onLoginHandler = new ServiceEventHandlerImpl();
	private final ServiceEventHandler _onLogoutHandler = new ServiceEventHandlerImpl();
	
	private SessionRemoteService sessionRemote = null;
	private EventListener _onLoginEvent = null; 
	
	public SessionManagerImpl() {
		setupEvents();
	}

	private void setupEvents() {
		// TODO Auto-generated method stub
		_onLoginEvent = createLoginListener();
		//getSessionRemote().onLogin().subscribe(_onLoginEvent);
	}

	private EventListener createLoginListener() {
		// TODO Auto-generated method stub
		return new EventListener() {
			
			@Override
			public void handle(Event eventData) {
				// TODO Auto-generated method stub
				_onLoginHandler.fireEvent(eventData);
			}
			
			@Override
			public String getName() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String getFullName() {
				// TODO Auto-generated method stub
				return null;
			}
		};
	}

	@Override
	public void loginViaQQ() {
		// TODO Auto-generated method stub

	}

	@Override
	public void loginViaWeibo() {
		// TODO Auto-generated method stub

	}

	@Override
	public void login(String loginId, String password, EventListener callback) {
		// TODO Auto-generated method stub
		getSessionRemote().onLogin().subscribe(_onLoginEvent);
		getSessionRemote().login(loginId, password, null);
	}

	@Override
	public ServiceEventHandler onLogin() {
		return _onLoginHandler;
	}

	@Override
	public ServiceEventHandler onLogout() {
		return _onLogoutHandler;
	}

	@Override
	public boolean isLogin() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public UserSession getSession() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FriendshipService getFriendshipService() {
		// TODO Auto-generated method stub
		return null;
	}

	public SessionRemoteService getSessionRemote() {
		return sessionRemote;
	}

	@Inject
	public void setSessionRemote(SessionRemoteService sessionRemote) {
		this.sessionRemote = sessionRemote;
	}

}
