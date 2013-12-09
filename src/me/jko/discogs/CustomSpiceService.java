package me.jko.discogs;

import android.app.Application;
import com.octo.android.robospice.SpiceService;
import com.octo.android.robospice.persistence.CacheManager;
import com.octo.android.robospice.persistence.binary.InFileBitmapObjectPersister;
import com.octo.android.robospice.persistence.exception.CacheCreationException;
import com.octo.android.robospice.persistence.memory.LruCacheBitmapObjectPersister;
import com.octo.android.robospice.persistence.springandroid.json.jackson.JacksonObjectPersisterFactory;

/**
 * This class defines how robospice caches our requests
 * each "persister" knows what classes it can cache
 * @author joonas
 *
 */


public class CustomSpiceService extends SpiceService {

	public static final String DATABASE_NAME = "discogs";
	public static final int DATABASE_VERSION = 3;
	
    @Override
    public void onCreate() {
        super.onCreate();

        // Logging really causes the app to chug with this many requests
        //Ln.getConfig().setLoggingLevel(Log.ERROR);
    }
    
	@Override
	public CacheManager createCacheManager(Application application) throws CacheCreationException {
		CacheManager cacheManager = new CacheManager();
		
		/*
		 * We have a separate cache for bitmap files
		 */
		
        InFileBitmapObjectPersister filePersister = new InFileBitmapObjectPersister(getApplication());
        LruCacheBitmapObjectPersister memoryPersister = new LruCacheBitmapObjectPersister(filePersister, 1024 * 1024);
		
        cacheManager.addPersister(memoryPersister);
		
		/* 
		 * We cache our requests into memory, objectpersisterfactory 
		 * automatically creates a persister for all the classes we have defined 
		 */
		
	    JacksonObjectPersisterFactory jacksonObjectPersisterFactory = new JacksonObjectPersisterFactory( application );
	    cacheManager.addPersister( jacksonObjectPersisterFactory );
		
		return cacheManager;
	}
}
