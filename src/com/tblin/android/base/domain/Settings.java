package com.tblin.android.base.domain;

public class Settings {

	public static String MODE = "prod";

	// public static String SERVER_URL = "http://lbs.tblin.com/api";// "http://192.168.0.240/lbs/api";//
	public static String SERVER_URL = "http://192.168.0.240/lbs/api";

	/**
	 * 登录超时时间
	 */
	public static long REGIST_TIME_OUT = 3 * 60 * 1000;// 3 minute

	/**
	 * 需要设置这个值的地方： 1. Application onCreate 2. 从SD卡读取到mobile的时候 3. 登录上服务器之后
	 */
	public static String MY_MOBILE = "";

	/**
	 * 后台定位间隔
	 */
	public static long NORMAL_LOCATE_INTERVAL = 60 * 60 * 1000;

	/**
	 * 后台定位，每次定位时长
	 */
	public static long NORMAL_LOCATE_TIMEOUT = 3 * 60 * 1000;

	/**
	 * 约会定位间隔
	 */
	public static long DATE_LOCATE_INTERVAL = 30 * 1000;

	/**
	 * 约会定位每次定位时长
	 */
	public static long DATE_LOCATE_TIMEOUT = 10 * 1000;
	
	/**
	 * ui定位间隔
	 */
	public static long UI_LOCATE_INTERVAL = 30 * 1000;

	/**
	 * ui定位每次定位时长
	 */
	public static long UI_LOCATE_TIMEOUT = 10 * 1000;

	/**
	 * 定位好友普通定位定位间隔
	 * 
	 */
	public static long GET_PERSON_ADDR_INTERVAL = 60 * 1000;

	public static long MAP_TOP_VIEW_SHOW_TIME = 5 * 1000;

	/**
	 * 显示好友多少天前登录的最长显示时长
	 */
	public static int MAX_SHOW_DAY = 7;

	/**
	 * 调用notifications接口的时间间隔
	 */
	public static long MESSAGE_SERVER_INTERVAL = 20 * 1000;
	
	/**
	 * 后台调用notifications接口的时间间隔
	 */
	public static long MESSAGE_SERVER_INTERVAL_BACK = 2 * 60 * 1000;

	public static int DATE_CHECK_ALARM_INTERVAL_MINUTE = 30;

	public static int DATE_NOTIFY_BEFORE_MINUTE = 3;

	/**
	 * 位置界面顶上的地址栏的我的位置的刷新周期
	 */
	public static long MY_ADDRESS_REFRESH_TIME = 30 * 1000;
	
	/**
	 * 位置界面顶上的地址栏的我的位置的刷新周期,此时没有获得第一次地址
	 */
	public static long MY_ADDRESS_REFRESH_TIME_NO_ADDR = 10 * 1000;
	
	/**
	 * 开机广播等待sim卡准备好的时间
	 */
	public static long BOOT_WAIT_SIM_TIME = 10 * 1000;
	
	public static String SERVER_BASE_URL = null;
	
	/**
	 * 显示最近多少天的聊天记录
	 */
	public static int SHOW_RECENT_DATE = 2;
	
	/**
	 * 最多显示多少条聊天记录
	 */
	public static int MAX_MESSAGE_COUNT = 30;
	
	

}
