package com.tblin.android.base.baseservice;

import com.tblin.android.base.domain.UserSession;

public interface BaseService {
	UserSession getSession();
	void setSession(UserSession userSession);
}
