package com.tblin.android.base.baseservice;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;
import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.tblin.android.base.domain.Settings;
import com.tblin.android.base.domain.User;
import com.tblin.android.base.event.Event;
import com.tblin.android.base.event.EventListener;
import com.tblin.android.base.remote.EventManager;
import com.tblin.android.base.remote.FriendshipRemoteService;
import com.tblin.android.base.remote.http.HttpEngine;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

//@RunWith(JUnit4ClassRunner.class)
public class MainActivityTest {

	@Test
	public void shouldHaveHappySmiling() throws Exception {
		// String hello = new
		// MainActivity().getResources().getString(R.string.hello_world);
		// assertThat(hello, equalTo("Hello world!"));
	}

	@Test
	public void shouldFireEvent() throws Exception {
		Injector injector = Guice.createInjector(new RemoteServiceModule());
		HttpEngine engine = injector.getInstance(HttpEngine.class);//HttpEngine.getInstance();
		engine.open();

		EventManager eventMgr = EventManager.getInstance();
		eventMgr.open();

		engine.setEventDispatcher(eventMgr);
		engine.onNetworkReady();

		//Settings.SERVER_URL = 

		String url = "http://192.168.0.240/lbs/api/v1/18620318415/1/notifications.json";
		List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
		params.add(new BasicNameValuePair("dm", "Ac%7 ##2"));

		EventListener lsnr = new EventListener() {

			@Override
			public void handle(Event event) {
				// TODO Auto-generated method stub
				System.out.println("data ==> " + event.getRawData());
				User user = (User) event.getData();
				System.out.println("currentUser ==>" + user.getMsisdn() + " , " + user.getNickName());
			}

			@Override
			public String getName() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public String getFullName() {
				// TODO Auto-generated method stub
				return null;
			}
		};

		EventListener lsnrFriends = new EventListener() {

			@Override
			public void handle(Event event) {
				// TODO Auto-generated method stub
				// System.out.println("data ==> " +
				// arg.get(EventDispatcher.EVENT_ITEM_RAW_DATA));
				List<User> users = (List<User>) event.getData();
				for (User u : users) {
					System.out.println("User: " + u.getMsisdn()
							+ ", location: (" + u.getLocation().getLongitude()
							+ ", " + u.getLocation().getLatitude() + "), locate time: " + u.getLocation().getLocateTime());
				}
			}

			@Override
			public String getName() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public String getFullName() {
				// TODO Auto-generated method stub
				return null;
			}
		};

//		FriendshipRemoteService friendshipRemoteHttp = new FriendshipRemoteServiceHttpImpl();
//		// friendshipRemoteHttp.setEventSubscription(eventMgr);
//
//		friendshipRemoteHttp.onGetFriendsResponse().subscribe(lsnrFriends);
//		friendshipRemoteHttp.getFriends(null);
		
		FriendshipRemoteService friendshipRemote = injector.getInstance(FriendshipRemoteService.class);
		friendshipRemote.onGetFriendsResponse().subscribe(lsnrFriends);
		friendshipRemote.getFriends(null);
		
		//HttpEngine engineX = injector.getInstance(HttpEngine.class);
		
		SessionManager sessionMgr = injector.getInstance(SessionManager.class);
		sessionMgr.onLogin().subscribe(lsnr);
		sessionMgr.login("", "", null);
		

		eventMgr.subscribe("notifications.resp", lsnr);
		eventMgr.subscribe("notifications.*", lsnr);

		// System.out.println("send request.");
		// engine.doGet( url, params, "notifications.resp", null, null);
		// Thread.sleep(2 * 1000);
		// System.out.println("notify network ready.");
		// engine.onNetworkReady();
		// Thread.sleep(2 * 1000);
		// System.out.println("notify network suspended");
		// engine.onNetworkSuspended();
		// System.out.println("send request.");
		// engine.doGet( url, params, "notifications.resp", null, lsnr);
		// Thread.sleep(2 * 1000);
		// System.out.println("notify network ready.");
		// engine.onNetworkReady();
		// engine.join();
		Thread.sleep(100 * 10 * 1000);
		engine.onNetworkReady();
	}

}
