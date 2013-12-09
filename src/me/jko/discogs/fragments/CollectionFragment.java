package me.jko.discogs.fragments;

import java.util.ArrayList;

import me.jko.discogs.CollectionListAdapter;
import me.jko.discogs.R;
import me.jko.discogs.Request;
import me.jko.discogs.R.id;
import me.jko.discogs.R.layout;
import me.jko.discogs.Request.CollectionRequest;
import me.jko.discogs.Request.IdentityRequest;
import me.jko.discogs.Request.ProfileRequest;
import me.jko.discogs.models.Release;
import me.jko.discogs.models.ReleaseCollection;

import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class CollectionFragment extends SpicedFragment {
	
	private Request request;
	public static final String PREFS_NAME = "DiscogsPrefs";
	RelativeLayout collectionProgressContainer;
	
	public CollectionFragment() {
        // Empty constructor required for fragment subclasses
    }
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
	}
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	View rootView = inflater.inflate(R.layout.fragment_collection, container, false);
 	
        return rootView;
    }
    
    @Override 
    public void onViewCreated(View view, Bundle savedInstanceState) {
    	super.onViewCreated(view, savedInstanceState);
    	
    	collectionProgressContainer = (RelativeLayout) getActivity().findViewById(R.id.collectionProgressContainer);
    	collectionProgressContainer.setVisibility(View.VISIBLE);       	
    	
    	
    	request = new Request(getActivity());
    	
    	SharedPreferences prefs = getActivity().getSharedPreferences(PREFS_NAME, 0);
    	
    	getActivity().setTitle("Fetching your collection");
    	

		CollectionRequest collectionreq = request.new CollectionRequest(this.getActivity(), prefs.getString("username", null), 0);
		String lastRequestCacheKey = collectionreq.createCacheKey();
		getSpiceManager().execute(collectionreq, new CollectionRequestListener());
    }
    
    // Collection
    private final class CollectionRequestListener implements RequestListener<ReleaseCollection> {
    	@Override
    	public void onRequestFailure(SpiceException e) {
    		Toast.makeText(getActivity(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
    	}
    	
    	@Override
    	public void onRequestSuccess(ReleaseCollection res) {
    		ArrayList<Release> releases = new ArrayList<Release>(res.getReleases());

    		ListView collectionListView = (ListView) getActivity().findViewById(R.id.collectionListView);
    		CollectionListAdapter clAdapter = new CollectionListAdapter(getActivity(), R.layout.collection_list_item, releases);
    		collectionListView.setAdapter(clAdapter);
    		
    		collectionProgressContainer.setVisibility(View.GONE);
    		getActivity().setTitle("Collection");
    	}
    }
}
