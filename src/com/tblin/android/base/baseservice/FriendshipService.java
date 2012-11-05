package com.tblin.android.base.baseservice;

import java.util.List;

import com.tblin.android.base.domain.User;
import com.tblin.android.base.event.EventListener;
import com.tblin.android.base.event.ServiceEventHandler;

public interface FriendshipService extends BaseService {
	ServiceEventHandler onFriendshipRequest();
	ServiceEventHandler onFriendshipAccept();
	ServiceEventHandler onFriendshipDenied();
	ServiceEventHandler onFriendshipDrop();
	ServiceEventHandler onFriendshipRequestResponse();
	ServiceEventHandler onFriendshipAcceptResponse();
	ServiceEventHandler onFriendshipDeniedResponse();
	ServiceEventHandler onFriendshipDropResponse();
	
	void request(User user, EventListener responseCallback);
	void accept(User user, EventListener responseCallback);
	void deny(User user, EventListener responseCallback);
	void drop(User user, EventListener responseCallback);
	
	List<User> getFriends();
	List<User> getPendingRequest();
	
}
