package com.tblin.android.base.baseservice.impl;

import java.util.Map;

import com.tblin.android.base.baseservice.BaseService;
import com.tblin.android.base.baseservice.EventListener;
import com.tblin.android.base.domain.UserSession;

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
	
	protected void fireEvent(EventListener listener, Map<String, Object> event) {
		if (listener != null) {
			try {
				listener.handle(event);
			} catch(Exception ex) {
				ex.printStackTrace();
			}
		}
	}

}
