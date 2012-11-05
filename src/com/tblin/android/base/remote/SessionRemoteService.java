package com.tblin.android.base.remote;

import com.tblin.android.base.event.EventListener;
import com.tblin.android.base.event.ServiceEventHandler;

public interface SessionRemoteService {
	
	ServiceEventHandler onLogin();
	
	void login(String loginId, String password, EventListener callback);
}
