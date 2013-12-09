package me.jko.discogs.fragments;

import me.jko.discogs.R;
import me.jko.discogs.R.layout;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ReleaseFragment extends SpicedFragment {
	public ReleaseFragment() {}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
	}
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	View rootView = inflater.inflate(R.layout.fragment_release, container, false);
 	
        return rootView;
    }
}
