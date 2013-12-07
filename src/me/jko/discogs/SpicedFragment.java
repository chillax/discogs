package me.jko.discogs;

import com.octo.android.robospice.SpiceManager;

import android.os.Bundle;
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
		// Please review https://github.com/octo-online/robospice/issues/96 for the reason of this if statement
		if (spiceManager.isStarted()) {
			spiceManager.shouldStop();
		}
		super.onStop();
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
	}

}
