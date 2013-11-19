package me.jko.discogs;

import org.json.JSONException;
import org.json.JSONObject;

import me.jko.discogs.R;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.widget.ListView;
import android.widget.ArrayAdapter;

public class MainActivity extends FragmentActivity {

    private String[] mSectionTitles;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    
    // put this to R
    public static final String PREFS_NAME = "DiscogsPrefs";
    private RestClient client;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        checkLogin();
        
        // 
    	client = new RestClient(this);
    	new GetIdentityTask().execute();
        
        setContentView(R.layout.activity_main);

        // mPlanetTitles = getResources().getStringArray(R.array.planets_array);
        mSectionTitles = new String[] {"Profile", "Collection", "Browse", "Marketplace"};
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        // Set the adapter for the drawer's listview
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, mSectionTitles));
        // Set the list's click listener
        //mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        
        // Load default fragment
        Fragment fragment = new ProfileFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
    }
    
    /*
     * Task to get the users ident from the API, we can use the ident to fetch user's profile
     * in ProfileFragment
     */
    
	private class GetIdentityTask extends AsyncTask<Void,Integer,Void> {
	      private String res;
		
		  protected void onPreExecute() {}

	      protected Void doInBackground(Void... parameters) {
	    	  	res = client.get("http://api.discogs.com/oauth/identity");
	            return null;
	       }
	       protected void onProgressUpdate(Integer... parameters) {
	    	   //progressBar.setProgress(parameters[0]);
	       }
	       protected void onPostExecute(Void parameters) {
	    	   //startButton.setEnabled(true);
	    	   
	    	   try {
	    		   JSONObject json = new JSONObject(res);
	    		   SharedPreferences prefs = getSharedPreferences(PREFS_NAME, 0);
	    		   SharedPreferences.Editor editor = prefs.edit();
	    		   editor.putString("access_token", json.getString("username"));
	    		   editor.commit();
	    	   } catch(JSONException e) {
	    		   
	    	   }
	       }
	}	

	/*
	 * We use this to check if we already have the user's credentials stored
	 */
	
	private void checkLogin() {
		// check if the user has already logged in
		SharedPreferences prefs = getSharedPreferences(PREFS_NAME, 0);
		if(prefs.getString("access_token", null) == null || prefs.getString("access_secret", null) == null) {
			// if token or secret doesn't exist, start login activity
			Intent intent = new Intent(this, LoginActivity.class);
			startActivity(intent);
		}
	}
}
