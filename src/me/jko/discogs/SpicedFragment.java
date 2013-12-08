package me.jko.discogs;

import com.octo.android.robospice.SpiceManager;

import android.support.v4.app.Fragment;

/*
 * Base class for all fragments to extend
 */

public class SpicedFragment extends Fragment {

	protected SpiceManager spiceManager = new SpiceManager(CustomSpiceService.class);

	public SpicedFragment() {
        // Empty constructor required for fragment subclasses
    }
	
	@Override
	public void onStart() {
		super.onStart();
		spiceManager.start(getActivity());
	}

	@Override
	public void onStop() {
		// Reason for if https://github.com/octo-online/robospice/issues/96
		if (spiceManager.isStarted()) {
			spiceManager.shouldStop();
		}
		
		super.onStop();
	}

	protected SpiceManager getSpiceManager() {
    	return spiceManager;
	}

}
