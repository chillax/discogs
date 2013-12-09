package me.jko.discogs.fragments;

import java.util.ArrayList;

import me.jko.discogs.CollectionListAdapter;
import me.jko.discogs.CollectionListView;
import me.jko.discogs.CollectionListView.CollectionListListener;
import me.jko.discogs.R;
import me.jko.discogs.Request;
import me.jko.discogs.R.id;
import me.jko.discogs.R.layout;
import me.jko.discogs.Request.CollectionRequest;
import me.jko.discogs.Request.IdentityRequest;
import me.jko.discogs.Request.ProfileRequest;
import me.jko.discogs.models.CollectionRelease;
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
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class CollectionFragment extends SpicedFragment implements CollectionListListener {
	
	private Request request;
	public static final String PREFS_NAME = "DiscogsPrefs";
	private int currentPage;
	RelativeLayout collectionProgressContainer;
	private SharedPreferences prefs;
	private boolean firstLoad;
	CollectionListView collectionListView;
	
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
    	
    	prefs = getActivity().getSharedPreferences(PREFS_NAME, 0);
    	
    	getActivity().setTitle("Fetching your collection");
    	
    	this.firstLoad = true;
    	currentPage = 1;
		CollectionRequest collectionreq = request.new CollectionRequest(this.getActivity(), prefs.getString("username", null), currentPage);
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
    		ArrayList<CollectionRelease> releases = new ArrayList<CollectionRelease>(res.getReleases());
	
    		// on the first time we set up the list view, after that we just append the incoming data
    		if(firstLoad) {
    			collectionListView = (CollectionListView) getActivity().findViewById(R.id.collectionListView);
        		CollectionListAdapter clAdapter = new CollectionListAdapter(getActivity(), R.layout.collection_list_item, releases);
        		collectionListView.setLoadingView(R.layout.collection_list_loading);
        		collectionListView.setAdapter(clAdapter);
        		collectionListView.setListener(CollectionFragment.this);
        		
    			collectionListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				   @Override
				   public void onItemClick(AdapterView<?> adapter, View view, int position, long arg) {
				      CollectionRelease release = (CollectionRelease) adapter.getAdapter().getItem(position);
				      Log.d("DEBUG", release.getBasic_information().getTitle());
				   } 
    			});
        		
        		collectionProgressContainer.setVisibility(View.GONE);
        		getActivity().setTitle("Collection");
        		firstLoad = false;
    		} else {
    			collectionListView.addNewData(releases);
    		}

    	}
    }
    
    @Override
    public void loadData() {
        Log.d("DEBUG", "loading more!");
        currentPage += 1;
        // We load more data here
		CollectionRequest collectionreq = request.new CollectionRequest(this.getActivity(), prefs.getString("username", null), currentPage);
		getSpiceManager().execute(collectionreq, new CollectionRequestListener());
    }
}
