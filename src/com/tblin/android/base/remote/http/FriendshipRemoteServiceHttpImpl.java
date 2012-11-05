package com.tblin.android.base.remote.http;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.tblin.android.base.domain.User;
import com.tblin.android.base.event.Event;
import com.tblin.android.base.event.EventDataProcessor;
import com.tblin.android.base.event.EventListener;
import com.tblin.android.base.event.ServiceEventHandler;
import com.tblin.android.base.event.ServiceEventHandlerImpl;
import com.tblin.android.base.remote.EventManager;
import com.tblin.android.base.remote.FriendshipRemoteService;

public class FriendshipRemoteServiceHttpImpl extends AbstractHttpService implements FriendshipRemoteService {
	
	private EventListener on_get_friends_resp = null;
	private static final String className = "FriendshipRemoteServiceHttpImpl";
	private EventDataProcessor friendsResponeEventDataProcessor = null;
	
	private final ServiceEventHandler on_get_friends_response = new ServiceEventHandlerImpl();
	
	public FriendshipRemoteServiceHttpImpl() {
		setupEvents();
	}

	@Override
	public void requestFriendship(User user, EventListener callback) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getFriends(EventListener callback) {
		String url = "http://192.168.0.240/lbs/api/v1/18620318415/1/friendships.json";
		getHttpEngine().doGet(url, null, EVENT_FRIENDSHIP_ON_GET_FRIENDS_RESP, null, friendsResponeEventDataProcessor, callback);
	}
	
	private void setupEvents() {
		on_get_friends_resp = createOnGetFriendsResp();
		EventManager.getInstance().subscribe(EVENT_FRIENDSHIP_ON_GET_FRIENDS_RESP, on_get_friends_resp);
		
	}

	private EventListener createOnGetFriendsResp() {
		if (null == on_get_friends_resp) {
			on_get_friends_resp = new EventListener() {
				
				@Override
				public void handle(Event eventData) {
					onGetFriendsResponse().fireEvent(eventData);
				}
				
				@Override
				public String getName() {
					return "on_get_friends_resp";
				}
				
				@Override
				public String getFullName() {
					return className + "." + getName();
				}
			};
		}
		
		return on_get_friends_resp;
	}

	@Override
	public final ServiceEventHandler onGetFriendsResponse() {
		return on_get_friends_response;
	}

	public EventDataProcessor getFriendsResponeEventDataProcessor() {
		return friendsResponeEventDataProcessor;
	}

	@Inject
	public void setFriendsResponeEventDataProcessor(@Named("FriendsEventDataProcessor")
			EventDataProcessor friendsResponeEventDataProcessor) {
		this.friendsResponeEventDataProcessor = friendsResponeEventDataProcessor;
	}

}
