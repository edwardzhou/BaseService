package com.tblin.android.base.remote.http;

import org.json.JSONException;
import org.json.JSONObject;

import com.tblin.android.base.domain.User;
import com.tblin.android.base.domain.utils.UserUtil;
import com.tblin.android.base.event.Event;
import com.tblin.android.base.event.EventDataProcessor;

public class SessionLoginEventDataProcessor implements EventDataProcessor {

	@Override
	public void process(Event eventData) {
		// TODO Auto-generated method stub
		
		String raw_data = eventData.getRawData();
		try {
			System.out.println("parse login data...");
			JSONObject obj = new JSONObject(raw_data);
			int result_code = obj.getInt("result_code");
			eventData.setResultCode(result_code);
			User currentUser = UserUtil.fromJson(obj.getJSONObject("myself"));
			//User
			eventData.setData(currentUser);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
