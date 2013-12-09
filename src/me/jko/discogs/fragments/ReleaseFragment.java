package me.jko.discogs.fragments;

import java.util.List;

import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import me.jko.discogs.R;
import me.jko.discogs.Request;
import me.jko.discogs.Request.ReleaseRequest;
import me.jko.discogs.models.Artist;
import me.jko.discogs.models.SingleRelease;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ReleaseFragment extends SpicedFragment {
	
	public static final String ARG_RELEASE_ID = "release_id";
	private Request request;
	private int id;
	RelativeLayout releaseProgressContainer;
	
	public ReleaseFragment() {}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
	}
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	View rootView = inflater.inflate(R.layout.fragment_release, container, false);
 	
    	this.id = getArguments().getInt(ARG_RELEASE_ID);
    	
        return rootView;
    }
    
    @Override 
    public void onViewCreated(View view, Bundle savedInstanceState) {
    	
    	releaseProgressContainer = (RelativeLayout) getActivity().findViewById(R.id.releaseProgressContainer);
    	releaseProgressContainer.setVisibility(View.VISIBLE);   
    	
    	request = new Request(getActivity());
    	
    	getActivity().setTitle("Fetching the release");
    	
		ReleaseRequest releasereq = request.new ReleaseRequest(this.getActivity(), id);
		String ck = releasereq.createCacheKey();
		getSpiceManager().execute(releasereq, ck, DurationInMillis.ONE_MINUTE * 15, new ReleaseRequestListener());
    }
    
    private final class ReleaseRequestListener implements RequestListener<SingleRelease> {
    	
    	@Override
    	public void onRequestFailure(SpiceException ex) {
    		Toast.makeText(getActivity(), "Error: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
    	}
    	
    	@Override
    	public void onRequestSuccess(SingleRelease res) {    		    		
    		TextView titleText = (TextView) getActivity().findViewById(R.id.releaseTitle);
    		titleText.setText(res.getTitle());
    		
			List<Artist> artists = res.getArtists();
			String artistString = "";
			for(Artist artist: artists) {
				if(artistString == "") {
					artistString = artistString + artist.getName();
				} else {
					artistString = artistString + ", " + artist.getName();
				}
			}
			
			TextView artistText = (TextView) getActivity().findViewById(R.id.releaseArtist);
			artistText.setText(artistString);
			
			TextView yearText = (TextView) getActivity().findViewById(R.id.releaseYear);
			yearText.setText(Integer.toString(res.getYear()));
			
			getActivity().setTitle(res.getTitle());
			
			releaseProgressContainer.setVisibility(View.GONE);   
    	}
    }
}
