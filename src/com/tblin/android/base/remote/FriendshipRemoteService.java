package com.tblin.android.base.remote;

import com.tblin.android.base.domain.User;
import com.tblin.android.base.event.EventListener;
import com.tblin.android.base.event.ServiceEventHandler;

public interface FriendshipRemoteService {
	
	final String EVENT_FRIENDSHIP_ON_GET_FRIENDS_RESP = "friendship.get.resp";
	final String EVENT_FRIENDSHIP_ALL = "friendship.*";
	
	ServiceEventHandler onGetFriendsResponse();
	
	void requestFriendship(User user, EventListener callback);
	void getFriends(EventListener callback);
}
