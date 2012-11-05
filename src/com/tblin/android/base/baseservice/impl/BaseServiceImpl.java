package com.tblin.android.base.baseservice.impl;

import com.tblin.android.base.baseservice.BaseService;
import com.tblin.android.base.domain.UserSession;
import com.tblin.android.base.event.Event;
import com.tblin.android.base.event.EventListener;

public class BaseServiceImpl implements BaseService {
	
	private UserSession userSession = null;
	

	@Override
	public UserSession getSession() {
		return userSession;
	}

	@Override
	public void setSession(UserSession userSession) {
		this.userSession = userSession;  

	}
	
	protected void fireEvent(EventListener listener, Event event) {
		if (listener != null) {
			try {
				listener.handle(event);
			} catch(Exception ex) {
				ex.printStackTrace();
			}
		}
	}

}
