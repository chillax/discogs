package me.jko.discogs;

import java.util.ArrayList;
import java.util.List;

import me.jko.discogs.models.Artist;
import me.jko.discogs.models.CollectionRelease;
import me.jko.discogs.models.Result;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * A custom adapter for SearchFragment's ListView
 * @author joonas
 *
 */

public class SearchListAdapter extends ArrayAdapter<Result> {
	private Context ctx;
	private ArrayList<Result> results;
	
	public SearchListAdapter(Context context, int textViewResourceId) {
		super(context, textViewResourceId);
		this.ctx = context;
	}
	
	private ArrayList<Result> releases;
	
	public SearchListAdapter(Context context, int resource, ArrayList<Result> results) {
		super(context, resource, results);
		
		this.results = results;
		this.ctx = context;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		if(v == null) {
			LayoutInflater vi = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.search_list_item, parent, false);
		}
		
		Result r = results.get(position);
		
		if(r != null) {
			TextView titleText = (TextView) v.findViewById(R.id.resultTitle);
			titleText.setText(r.getTitle());
		}
		
		return v;
	}
}
