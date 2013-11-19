package me.jko.discogs;

import org.json.JSONException;
import org.json.JSONObject;

import me.jko.discogs.R;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ProfileFragment extends Fragment {

	private RestClient client;
	
	public ProfileFragment() {
        // Empty constructor required for fragment subclasses
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	
    	client = new RestClient( getActivity() );
    	new GetProfileTask().execute();

    	View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        //int i = getArguments().getInt(ARG_PLANET_NUMBER);

        getActivity().setTitle("Profile");
        return rootView;
    }
   
	private class GetProfileTask extends AsyncTask<Void,Integer,Void> {
	      private String res;
	      public static final String PREFS_NAME = "DiscogsPrefs";
		
		  protected void onPreExecute() {}

	      protected Void doInBackground(Void... parameters) {
	    	  SharedPreferences prefs = getActivity().getSharedPreferences(PREFS_NAME, 0);
	    	  
	    	  String baseUrl = "http://api.discogs.com/users/%s";
	    	  String profileUrl = String.format(baseUrl, prefs.getString("username", null));
	    	  res = client.get(profileUrl);
	          
	    	  return null;
	       }
	       protected void onProgressUpdate(Integer... parameters) {
	    	   //progressBar.setProgress(parameters[0]);
	       }
	       protected void onPostExecute(Void parameters) {
	    	   //startButton.setEnabled(true);
	    	   
	    	   try {
					JSONObject json = new JSONObject(res);
					Log.d("DEBUG", res);

				} catch(JSONException e) {
				
				}
	       }
	}	
    
}
