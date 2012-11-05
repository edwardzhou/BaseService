package com.tblin.android.base.remote.http;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.tblin.android.base.domain.User;
import com.tblin.android.base.domain.utils.UserUtil;
import com.tblin.android.base.event.Event;
import com.tblin.android.base.event.EventDataProcessor;

public class FriendsResponeEventDataProcessor implements EventDataProcessor {

	@Override
	public void process(Event eventData) {
		String raw_data = eventData.getRawData();
		try {
			JSONObject obj = new JSONObject(raw_data);
			int result_code = obj.getInt("result_code");
			eventData.setResultCode(result_code);
			List<User> users = UserUtil.fromjson(obj.getJSONArray("friends"));
			eventData.setData(users);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
