package com.tblin.android.base.remote.http;

import com.google.inject.Inject;

public class AbstractHttpService implements HttpEngineAware {

	private HttpEngine httpEngine = null;
	

	public HttpEngine getHttpEngine() {
		return httpEngine;
	}

	@Inject
	public void setHttpEngine(HttpEngine httpEngine) {
		this.httpEngine = httpEngine;
	}

	protected String combineUrl(String base, String url) {
		if (base.endsWith("/"))
			return base + url;
		else
			return base + "/" + url;
	}

}
