package me.jko.discogs;

import me.jko.discogs.R;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginActivity extends Activity {
	 @Override
	 public void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
		 setContentView(R.layout.activity_login);
		 
		 final Button button = (Button) findViewById(R.id.loginButton);
         button.setOnClickListener(new View.OnClickListener() {
             public void onClick(View v) {
    			 Intent intent = new Intent(getApplicationContext(), AuthActivity.class);
    			 
    			 // we start an activity and wait for it's result
    			 startActivityForResult(intent, 1);
             }
         });
	 }
	 
	 /*
	  * Handler for the AuthActivity's result
	  */
	 
	 @Override
	 protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		 super.onActivityResult(requestCode, resultCode, data);
		 
		 if( resultCode == RESULT_OK ) {
			 String access_token = data.getStringExtra("access_token");
			 String access_secret = data.getStringExtra("access_secret");
			 
			 SharedPreferences prefs = getSharedPreferences("DiscogsPrefs", 0);			 
			 SharedPreferences.Editor editor = prefs.edit();
			 
			 editor.putString("access_token", access_token);
			 editor.putString("access_secret", access_secret);
			 editor.commit();
			 
			 Intent intent = new Intent(this, MainActivity.class);
			 startActivity(intent);
		 }
	 }
}
