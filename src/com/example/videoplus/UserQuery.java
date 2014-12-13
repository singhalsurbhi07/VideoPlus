package com.example.videoplus;



import com.example.videoplus.services.ResponseFileService;
import com.example.videoplus.utils.ExternalStorageUtil;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class UserQuery extends Activity {
	ExternalStorageUtil util = new ExternalStorageUtil();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_query);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.user_query, menu);
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
	
	public void getTopLike(View v){
		String query = "select videoId, url, title from VIDEO;" ;
		util.writeRequestToDownloads(query);
		String uri = "file://"+Environment.getExternalStoragePublicDirectory(
				Environment.DIRECTORY_DOWNLOADS).getAbsolutePath()+"/videoplus/videoRequest.json";
		Intent sendin  = new Intent();
		sendin.setAction(Intent.ACTION_SEND);
		sendin.putExtra(Intent.EXTRA_STREAM, Uri.parse(uri));
		sendin.setType("application/json");
		startActivityForResult(Intent.createChooser(sendin, "share file via"),0);
		Intent i = new Intent(this,ResponseFileService.class);
		startService(i);
	}
}
