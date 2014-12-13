package com.example.videoplus;



import io.oauth.OAuth;
import io.oauth.OAuthCallback;
import io.oauth.OAuthData;
import io.oauth.OAuthRequest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.json.JSONException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;

import com.example.videoplus.db.MySQLiteHelperVideos;
import com.example.videoplus.utils.StaticDataReference;

public class MainActivity extends Activity implements OAuthCallback {

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		if(StaticDataReference.getData()==null){
			final OAuth oauth = new OAuth(MainActivity.this);
			oauth.initialize("rJAL3sFZt15uk_cy7cHA1yBvKXk");
			oauth.popup("youtube", MainActivity.this);
		}
		MySQLiteHelperVideos sqLiteHelperVideos = new MySQLiteHelperVideos(this);
		StaticDataReference.setSqlHelper(sqLiteHelperVideos);
		try {
			sqLiteHelperVideos.addVideoRow();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	@SuppressWarnings("deprecation")
	@Override
	public void onFinished(OAuthData data) {
		StaticDataReference.setData(data);
		Intent i = new Intent(this,UserQuery.class);
		startActivity(i);
		StrictMode.ThreadPolicy policy = new
				StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
				StrictMode.setThreadPolicy(policy);
		
		StaticDataReference.getData().http("youtube/v3/activities?part=snippet%2CcontentDetails&mine=true", new OAuthRequest() {
			private URL url;
			private URLConnection con;
			
			@Override
			public void onSetURL(String _url) {
				System.out.println("_url-->"+_url);
				try {
					url = new URL("https://www.googleapis.com/youtube/v3/activities?part=snippet%2CcontentDetails&mine=true&key=AIzaSyBfPHc7Eupc7Zt4AR17qFq9AsvJLIq-UvQ");
					con = url.openConnection();
				} catch (Exception e) { e.printStackTrace(); } 
				
			}
			
			@Override
			public void onSetHeader(String arg0, String arg1) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onReady() {
				// This method is called once url and headers are set.
				try {
					BufferedReader r = new BufferedReader(new InputStreamReader(con.getInputStream()));
					StringBuilder total = new StringBuilder();
					String line;
					while ((line = r.readLine()) != null) {
						total.append(line);
					}
					System.out.println(total);
					//JSONObject result = new JSONObject(total.toString());
					//JSONObject user = new JSONObject(result.getString("user").toString());
					//displayUserInfo(user);
				} catch (Exception e) { e.printStackTrace(); }
			}

			@Override
			public void onError(String message) {
				// This method is called if an error occured
				System.out.println("error-->>"+message);
			}
		});
		

	}
}
