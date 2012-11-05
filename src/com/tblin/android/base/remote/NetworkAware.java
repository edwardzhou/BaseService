package com.tblin.android.base.remote;

public interface NetworkAware {
	void onNetworkReady();
	void onNetworkSuspended();
}
