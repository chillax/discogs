package me.jko.discogs;

import org.json.JSONException;
import org.json.JSONObject;

import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

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
import android.widget.TextView;
import android.widget.Toast;

public class ProfileFragment extends SpicedFragment {

	private RestClient client;
	private SharedPreferences prefs;
	public static final String PREFS_NAME = "DiscogsPrefs";
	
	public ProfileFragment() {
        // Empty constructor required for fragment subclasses
    }
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        //int i = getArguments().getInt(ARG_PLANET_NUMBER);
 	
        getActivity().setTitle("Profile");
        return rootView;
    }

    @Override 
    public void onViewCreated(View view, Bundle savedInstanceState) {
    	super.onViewCreated(view, savedInstanceState);
    	
    	ProfileFragment.this.getActivity().setProgressBarIndeterminateVisibility(true);
    	Request request = new Request(this.getActivity(), "GET", "http://api.discogs.com/oauth/identity");
    	spiceManager.execute(request, "identity", DurationInMillis.ALWAYS_RETURNED, new IdentityRequestListener());
    }
    
    private final class IdentityRequestListener implements RequestListener<String> {
    	@Override
    	public void onRequestFailure(SpiceException ex) {
    		Toast.makeText(getActivity(), "Error: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
    		ProfileFragment.this.getActivity().setProgressBarIndeterminateVisibility(false);
    	}
    	
    	@Override
    	public void onRequestSuccess(String res) {
    		JSONObject json;
			try {
				json = new JSONObject(res);
	    		ProfileFragment.this.getActivity().setProgressBarIndeterminateVisibility(false);

	    		
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    }
    
    
    
    /*
     * Task to get the users ident from the API, we can use this to see if our login has been successful
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
	    	   
	    	   try {
					Log.d("GET", res);
					JSONObject json = new JSONObject(res);
					
					// save fetched username to shared preferences
					SharedPreferences prefs = getActivity().getSharedPreferences(PREFS_NAME, 0);
					SharedPreferences.Editor editor = prefs.edit();
					editor.putString("username", json.getString("username"));
					editor.commit();
					
			    	new GetProfileTask().execute();

				} catch(JSONException e) {
				
				}
	       }
	}	    
    
	/*
	 * This task fetches the actual profile information of the user
	 */
    
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
					
					// Update profile fields
				    TextView username = (TextView) getActivity().findViewById(R.id.profileUsername);
				    username.setText(json.getString("username"));
					
				    TextView email = (TextView) getActivity().findViewById(R.id.profileEmail);
				    email.setText(json.getString("email"));
				    
				} catch(JSONException e) {
				
				}
	       }
	}	
    
}
