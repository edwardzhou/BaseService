package com.tblin.android.base.domain.utils;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.tblin.android.base.domain.User;

public class UserUtil {

	public static JSONObject toJson(User user) {
		return null;
	}

	public static User fromJson(JSONObject json) {
		User user = new User();

		try {
			user.setUserId(json.getInt("id"));
			user.setLoginId(json.getString("login_id"));
			user.setEmail("");
			user.setMsisdn(json.getString("msisdn"));
			user.setImsi(json.getString("imsi"));
			user.setNickName(json.getString("nick_name"));
			
			if (!json.isNull("user_profile")) {
				user.setUserProfile( UserProfileUtil.fromJson(json.getJSONObject("user_profile")) );
			}
			
			if (!json.isNull("location")) {
				user.setLocation(LocationUtil.fromJson(json.getJSONObject("location")));
			}
			
		} catch (JSONException e) {
			e.printStackTrace();
			user = null;
		}

		return user;

	}
	
	public static List<User> fromjson(JSONArray jsonArray) {
		List<User> users = new ArrayList<User>();
		
		for(int n = 0; n < jsonArray.length(); n++) {
			JSONObject json;
			try {
				json = jsonArray.getJSONObject(n);
				User user = fromJson(json);
				if (user != null) {
					users.add(user);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return users;
	}

}
