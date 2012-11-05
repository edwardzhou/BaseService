package com.tblin.android.base.domain.utils;

import org.json.JSONException;
import org.json.JSONObject;

import com.tblin.android.base.domain.UserProfile;

public class UserProfileUtil {
	
	final static String FIELD_GENDER = "gender";
	final static String FIELD_BIRTHDAY = "birthday";
	final static String FIELD_LAST_LOGIN_AT = "last_login_at";
	final static String FIELD_LAST_LOGIN_IP = "last_login_ip";
	
	public static JSONObject toJson(UserProfile user) {
		return null;
	}

	public static UserProfile fromJson(JSONObject json) {
		UserProfile user_profile = new UserProfile();

		try {
			if(!json.isNull(FIELD_GENDER))
				user_profile.setGender(json.getString(FIELD_GENDER));
			if(!json.isNull(FIELD_BIRTHDAY)) 
				user_profile.setBirthday(json.getString(FIELD_BIRTHDAY));
//			if(!json.isNull(FIELD_LAST_LOGIN_AT))
//				user_profile.setl)
		} catch (JSONException e) {
			e.printStackTrace();
			user_profile = null;
		}

		return user_profile;

	}
	
}
