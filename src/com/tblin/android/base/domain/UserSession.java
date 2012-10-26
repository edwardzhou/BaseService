package com.tblin.android.base.domain;

public class UserSession {
	private User currentUser = null;

	public User getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}
}
