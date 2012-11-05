package com.tblin.android.base.domain.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.json.JSONException;
import org.json.JSONObject;

import com.tblin.android.base.domain.Location;

public class LocationUtil {
	
	final static String FIELD_LONGITUDE = "longitude";
	final static String FIELD_LATITUDE = "latitude";
	final static String FIELD_STREET = "street";
	final static String FIELD_LOCATE_TIME = "locate_time";
	
	public static JSONObject toJson(Location location) {
		return null;
	}

	public static Location fromJson(JSONObject json) {
		Location location = new Location();

		try {
			long v = 0;
			
			if(!json.isNull(FIELD_LONGITUDE)) {
				v = Double.valueOf(json.getDouble(FIELD_LONGITUDE) * Math.pow(10, 6)).longValue();
				location.setLongitude( v );
			}
			if(!json.isNull(FIELD_LATITUDE)) 
				v = Double.valueOf(json.getDouble(FIELD_LATITUDE) * Math.pow(10, 6)).longValue();
				location.setLatitude( v );
			if(!json.isNull(FIELD_STREET))
				location.setStreet(json.getString(FIELD_STREET));
			if(!json.isNull(FIELD_LOCATE_TIME)) {
				SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
				location.setLocateTime(fmt.parse(json.getString(FIELD_LOCATE_TIME)));
			}
		} catch (JSONException e) {
			e.printStackTrace();
			location = null;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return location;

	}

}
