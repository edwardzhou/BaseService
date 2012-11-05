package com.tblin.android.base.baseservice;

import com.tblin.android.base.domain.UserSession;
import com.tblin.android.base.event.EventListener;
import com.tblin.android.base.event.ServiceEventHandler;

public interface SessionManager {
	
	void loginViaQQ();
	void loginViaWeibo();
	void login(String loginId, String password, EventListener callback);
	
	ServiceEventHandler onLogin();
	ServiceEventHandler onLogout();
	
	boolean isLogin();
	UserSession getSession();
	
	FriendshipService getFriendshipService();	
}
