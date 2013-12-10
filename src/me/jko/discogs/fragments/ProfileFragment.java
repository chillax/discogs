package me.jko.discogs.fragments;

import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import me.jko.discogs.R;
import me.jko.discogs.Request;
import me.jko.discogs.Request.IdentityRequest;
import me.jko.discogs.Request.ProfileRequest;
import me.jko.discogs.models.Identity;
import me.jko.discogs.models.Profile;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Fragment for user's profile information
 * @author joonas
 *
 */

public class ProfileFragment extends SpicedFragment {

	private SharedPreferences prefs;
	private Request request;
	public static final String PREFS_NAME = "DiscogsPrefs";
	RelativeLayout profileProgressContainer;
	
	
	public ProfileFragment() {
        // Empty constructor required for fragment subclasses
    }

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
	}
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
 	
        return rootView;
    }

    @Override 
    public void onViewCreated(View view, Bundle savedInstanceState) {
    	super.onViewCreated(view, savedInstanceState);
    	
    	profileProgressContainer = (RelativeLayout) getActivity().findViewById(R.id.profileProgressContainer);
    	profileProgressContainer.setVisibility(View.VISIBLE);    	
    	
    	request = new Request(getActivity());
    	
    	prefs = getActivity().getSharedPreferences(PREFS_NAME, 0);
    	
    	getActivity().setTitle("Fetching your profile");
    	
    	if(prefs.getString("username", null) == null) {
    		IdentityRequest identreq = request.new IdentityRequest(this.getActivity());
    		getSpiceManager().execute(identreq, new IdentityRequestListener());
    	} else {
    		ProfileRequest profilereq = request.new ProfileRequest(this.getActivity(), prefs.getString("username", null));
    		String lastRequestCacheKey = profilereq.createCacheKey();
    		getSpiceManager().execute(profilereq, lastRequestCacheKey, DurationInMillis.ONE_MINUTE * 15, new ProfileRequestListener());
    	}   	
    }
    
    /*
     * Listeners
     */
    
    
    // Identity
    private final class IdentityRequestListener implements RequestListener<Identity> {
    	@Override
    	public void onRequestFailure(SpiceException ex) {
    		Toast.makeText(getActivity(), "Error: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
    	}
    	
    	@Override
    	public void onRequestSuccess(Identity res) {    		    		
			SharedPreferences prefs = getActivity().getSharedPreferences(PREFS_NAME, 0);
			SharedPreferences.Editor editor = prefs.edit();
			editor.putString("username", res.getUsername());
			editor.commit();
						
			ProfileRequest profilereq = request.new ProfileRequest(ProfileFragment.this.getActivity(), res.getUsername());
			
			String lastRequestCacheKey = profilereq.createCacheKey();			
			getSpiceManager().execute(profilereq, lastRequestCacheKey, DurationInMillis.ONE_MINUTE * 15, new ProfileRequestListener());
    	}
    }

    // Profile
    private final class ProfileRequestListener implements RequestListener<Profile> {
    	@Override
    	public void onRequestFailure(SpiceException e) {
    		Toast.makeText(getActivity(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
    	}
    	
    	@Override
    	public void onRequestSuccess(Profile res) {
    		TextView usernameText = (TextView) getActivity().findViewById(R.id.profileUsername);
    		usernameText.setText("Username: " + res.getUsername());
    		
    		TextView emailText = (TextView) getActivity().findViewById(R.id.profileEmail);
    		emailText.setText("Email: " + res.getEmail());
    		
    		TextView collectionText = (TextView) getActivity().findViewById(R.id.titleCollection);
    		collectionText.setText(Integer.toString(res.getNum_collection()) + " items in collection");
    		
    		profileProgressContainer.setVisibility(View.GONE);
    		getActivity().setTitle("Profile");
    	}
    }
}
