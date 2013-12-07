package me.jko.discogs;

import android.app.Application;

import com.octo.android.robospice.SpiceService;
import com.octo.android.robospice.persistence.CacheManager;
import com.octo.android.robospice.persistence.memory.LruCacheStringObjectPersister;

public class CustomSpiceService extends SpiceService {

	public static final String DATABASE_NAME = "discogs";
	public static final int DATABASE_VERSION = 1;
	
	@Override
	public CacheManager createCacheManager(Application application) {
		CacheManager cacheManager = new CacheManager();

		// We can just use memory caching as this point
		LruCacheStringObjectPersister memoryPersister = new LruCacheStringObjectPersister(1024 * 1024);
		cacheManager.addPersister(memoryPersister);
		
		return cacheManager;
	}
}
