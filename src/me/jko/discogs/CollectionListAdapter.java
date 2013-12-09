package me.jko.discogs;

import java.util.ArrayList;
import java.util.List;

import me.jko.discogs.models.Artist;
import me.jko.discogs.models.Release;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CollectionListAdapter extends ArrayAdapter<Release> {
	
	public CollectionListAdapter(Context context, int textViewResourceId) {
		super(context, textViewResourceId);
	}
	
	private ArrayList<Release> releases;
	
	public CollectionListAdapter(Context context, int resource, ArrayList<Release> releases) {
		super(context, resource, releases);
		
		this.releases = releases;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		if(v == null) {
			LayoutInflater vi;
			vi = LayoutInflater.from(getContext());
			v = vi.inflate(R.layout.collection_list_item, null);
		}
		
		Release r = releases.get(position);
		
		if(r != null) {
			
			// for some reason certain collections have their year set to 0
			String titleString = "";
			if(r.getBasic_information().getYear() == 0) {
				titleString = r.getBasic_information().getTitle();
			} else {
				titleString = r.getBasic_information().getTitle() + " (" + r.getBasic_information().getYear() + ")";
			}
			
			TextView title = (TextView) v.findViewById(R.id.collectionListItemTitle);
			title.setText(titleString);
			
			List<Artist> artists = r.getBasic_information().getArtists();
			String artistString = "";
			for(Artist artist: artists) {
				if(artistString == "") {
					artistString = artistString + artist.getName();
				} else {
					artistString = artistString + ", " + artist.getName();
				}
			}
		
			TextView details = (TextView) v.findViewById(R.id.collectionListItemDetails);			
			details.setText(artistString);
		}
		
		return v;
	}
	
    @Override
    public int getCount() {                
    	return releases.size() ;
    }
    
    @Override
    public Release getItem(int position) {                
        return releases.get(position);
    }
    
    @Override
    public long getItemId(int position) {                
    	return releases.get(position).hashCode();
    }


}
