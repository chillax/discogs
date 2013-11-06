package me.jko.discogs;

import me.jko.discogs.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ProfileFragment extends Fragment {
    public ProfileFragment() {
        // Empty constructor required for fragment subclasses
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	
    	
    	
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        //int i = getArguments().getInt(ARG_PLANET_NUMBER);

        getActivity().setTitle("Profile");
        return rootView;
    }
}
