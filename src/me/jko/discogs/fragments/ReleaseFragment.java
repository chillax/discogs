package me.jko.discogs.fragments;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import roboguice.util.temp.Ln;

import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.CachedSpiceRequest;
import com.octo.android.robospice.request.listener.RequestListener;
import com.octo.android.robospice.request.simple.BitmapRequest;

import me.jko.discogs.R;
import me.jko.discogs.Request;
import me.jko.discogs.Request.ReleaseRequest;
import me.jko.discogs.models.Artist;
import me.jko.discogs.models.Image;
import me.jko.discogs.models.SingleRelease;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
			
			
					
			
			List<Image> images = res.getImages();
			String url = images.get(0).getUri();
			int width = images.get(0).getWidth();
			int height = images.get(0).getHeight();
			
			File cacheFile = null;
			String filename = null;
			try {
				filename = URLEncoder.encode(url, "UTF-8");
				cacheFile = new File(getActivity().getCacheDir(), filename);
			} catch (UnsupportedEncodingException e) {
				Ln.e(e);
			}
						
			BitmapRequest bitmapreq = new BitmapRequest(url, 400, 400, cacheFile);
			CachedSpiceRequest<Bitmap> cachedreq = new CachedSpiceRequest<Bitmap>(bitmapreq, filename, DurationInMillis.ONE_MINUTE * 15);
			spiceManager.execute(cachedreq, new BitmapRequestListener());
			
			releaseProgressContainer.setVisibility(View.GONE);   
    	}
    }
    
    private class BitmapRequestListener implements RequestListener<Bitmap> {
    	@Override
    	public void onRequestFailure(SpiceException e) {
    		
    	}
    	
    	@Override
    	public void onRequestSuccess(Bitmap bitmap) {
    		ImageView iv = (ImageView) getActivity().findViewById(R.id.releaseImage);
    		iv.setImageBitmap(bitmap);
    	}
    }
}
