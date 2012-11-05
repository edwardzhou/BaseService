package com.tblin.android.base.baseservice.impl;

import java.util.List;

import com.tblin.android.base.baseservice.FriendshipService;
import com.tblin.android.base.domain.User;
import com.tblin.android.base.event.Event;
import com.tblin.android.base.event.EventListener;
import com.tblin.android.base.event.ServiceEventHandler;
import com.tblin.android.base.event.ServiceEventHandlerImpl;

public class FriendshipServiceImpl extends BaseServiceImpl implements
		FriendshipService {

	private final ServiceEventHandler _eventOnFriendshipRequest = new ServiceEventHandlerImpl();
	private final ServiceEventHandler _eventOnFriendshipAccept = new ServiceEventHandlerImpl();
	private final ServiceEventHandler _eventOnFriendshipDenied = new ServiceEventHandlerImpl();
	private final ServiceEventHandler _eventOnFriendshipDrop = new ServiceEventHandlerImpl();
	
	private final ServiceEventHandler _eventOnFriendshipRequestResponse = new ServiceEventHandlerImpl();
	private final ServiceEventHandler _eventOnFriendshipAcceptResponse = new ServiceEventHandlerImpl();
	private final ServiceEventHandler _eventOnFriendshipDeniedResponse = new ServiceEventHandlerImpl();
	private final ServiceEventHandler _eventOnFriendshipDropResponse = new ServiceEventHandlerImpl();
	
	
	@Override
	public ServiceEventHandler onFriendshipRequest() {
		return _eventOnFriendshipRequest;
	}

	@Override
	public ServiceEventHandler onFriendshipAccept() {
		return _eventOnFriendshipAccept;
	}

	@Override
	public ServiceEventHandler onFriendshipDenied() {
		return _eventOnFriendshipDenied;
	}

	@Override
	public ServiceEventHandler onFriendshipDrop() {
		return _eventOnFriendshipDrop;
	}

	@Override
	public ServiceEventHandler onFriendshipRequestResponse() {
		return _eventOnFriendshipRequestResponse;
	}

	@Override
	public ServiceEventHandler onFriendshipAcceptResponse() {
		return _eventOnFriendshipAcceptResponse;
	}

	@Override
	public ServiceEventHandler onFriendshipDeniedResponse() {
		return _eventOnFriendshipDeniedResponse;
	}

	@Override
	public ServiceEventHandler onFriendshipDropResponse() {
		return _eventOnFriendshipDropResponse;
	}

	@Override
	public void request(User user, EventListener responseCallback) {
		
		Event event = new Event();
		
		this.fireEvent(responseCallback, event);

	}

	@Override
	public void accept(User user, EventListener responseCallback) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deny(User user, EventListener responseCallback) {
		// TODO Auto-generated method stub

	}

	@Override
	public void drop(User user, EventListener responseCallback) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<User> getFriends() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> getPendingRequest() {
		// TODO Auto-generated method stub
		return null;
	}

}
