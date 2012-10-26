package com.tblin.android.base.remote;

import com.tblin.android.base.domain.User;

public interface FriendshipRemoteService {
	void requestFriendship(User user, Object callback);
}
