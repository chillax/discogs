package me.jko.discogs.fragments;

import java.util.ArrayList;

import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import me.jko.discogs.R;
import me.jko.discogs.Request;
import me.jko.discogs.Request.SearchRequest;
import me.jko.discogs.SearchListAdapter;
import me.jko.discogs.models.Result;
import me.jko.discogs.models.SearchResults;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class SearchFragment extends SpicedFragment {
	
	private Request request;
	
	public SearchFragment() {
        // Empty constructor required for fragment subclasses
    }
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
	}
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	View rootView = inflater.inflate(R.layout.fragment_search, container, false);
 	
        return rootView;
    }
    
    @Override 
    public void onViewCreated(View view, Bundle savedInstanceState) {
    	super.onViewCreated(view, savedInstanceState);
    	
    	final Button button = (Button) getActivity().findViewById(R.id.searchButton);
    	final EditText field = (EditText) getActivity().findViewById(R.id.searchField);
    	
    	request = new Request(getActivity());
    	
		
		
    	
    	button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				String query = field.getText().toString();
				SearchRequest searchreq = request.new SearchRequest(getActivity(), query);
				getSpiceManager().execute(searchreq, new SearchRequestListener());
			}
    	});
    }
    
    private final class SearchRequestListener implements RequestListener<SearchResults> {
    	
    	@Override
    	public void onRequestFailure(SpiceException ex) {
    		Toast.makeText(getActivity(), "Error: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
    	}
    	
    	@Override
    	public void onRequestSuccess(SearchResults res) {    		    		
    		ArrayList<Result> results = new ArrayList<Result>(res.getResults());
    		ListView sl = (ListView) getActivity().findViewById(R.id.searchResults);
    		SearchListAdapter adapter = new SearchListAdapter(getActivity(), R.layout.search_list_item, results);
    		sl.setAdapter(adapter);
    		
    		sl.setOnItemClickListener(new AdapterView.OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> adapter, View view, int position, long arg) {
				      Result result = (Result) adapter.getAdapter().getItem(position);
				      
				      // set up the fragment and set release id as its argument
				      Fragment fragment = new ReleaseFragment();
				      Bundle args = new Bundle();
				      args.putInt(ReleaseFragment.ARG_RELEASE_ID, result.getId());
				      fragment.setArguments(args);
				      
				      Log.d("DEBUG", Integer.toString(result.getId()));
				      
				      FragmentManager fragmentManager = getFragmentManager();
				      fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
				}
    			
			});
    	}
    }
}
