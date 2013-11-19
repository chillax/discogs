package me.jko.discogs;

import org.json.JSONException;
import org.json.JSONObject;

import me.jko.discogs.R;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

    	View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        //int i = getArguments().getInt(ARG_PLANET_NUMBER);

        getActivity().setTitle("Profile");
        return rootView;
    }
   

}
