package com.tblin.android.base.baseservice;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.google.inject.name.Names;
import com.tblin.android.base.baseservice.impl.SessionManagerImpl;
import com.tblin.android.base.event.EventDataProcessor;
import com.tblin.android.base.remote.FriendshipRemoteService;
import com.tblin.android.base.remote.SessionRemoteService;
import com.tblin.android.base.remote.http.FriendsResponeEventDataProcessor;
import com.tblin.android.base.remote.http.FriendshipRemoteServiceHttpImpl;
import com.tblin.android.base.remote.http.HttpEngine;
import com.tblin.android.base.remote.http.HttpEngineImpl;
import com.tblin.android.base.remote.http.SessionLoginEventDataProcessor;
import com.tblin.android.base.remote.http.SessionRemoteServiceImpl;

public class RemoteServiceModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(HttpEngine.class).to(HttpEngineImpl.class).in(Scopes.SINGLETON);

		bind(FriendshipRemoteService.class).to(
				FriendshipRemoteServiceHttpImpl.class);
		
		bind(EventDataProcessor.class).annotatedWith(Names.named("FriendsEventDataProcessor")).to(
				FriendsResponeEventDataProcessor.class);

		bind(SessionManager.class).to(SessionManagerImpl.class).in(Scopes.SINGLETON);
		
		bind(SessionRemoteService.class).to(SessionRemoteServiceImpl.class).in(Scopes.SINGLETON);
		
		bind(EventDataProcessor.class).annotatedWith(Names.named("SessionLoginEventDataProcess")).to(SessionLoginEventDataProcessor.class);
		
	}

}
