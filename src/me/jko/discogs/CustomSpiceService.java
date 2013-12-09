package me.jko.discogs;

import java.util.ArrayList;
import java.util.List;

import me.jko.discogs.models.Release;
import me.jko.discogs.models.ReleaseCollection;
import me.jko.discogs.models.Profile;
import android.app.Application;

import com.octo.android.robospice.SpiceService;
import com.octo.android.robospice.persistence.CacheManager;
import com.octo.android.robospice.persistence.memory.LruCacheStringObjectPersister;
import com.octo.android.robospice.persistence.ormlite.InDatabaseObjectPersisterFactory;
import com.octo.android.robospice.persistence.ormlite.RoboSpiceDatabaseHelper;

public class CustomSpiceService extends SpiceService {

	public static final String DATABASE_NAME = "discogs";
	public static final int DATABASE_VERSION = 3;
	
	@Override
	public CacheManager createCacheManager(Application application) {
		CacheManager cacheManager = new CacheManager();
		
		// add models you want to cache in here
		List<Class<?>> classCollection = new ArrayList<Class<?>>();
		classCollection.add(Profile.class);
				
		// We store our cache in an ORM mapped sqlite database
		
		RoboSpiceDatabaseHelper databaseHelper = new RoboSpiceDatabaseHelper( application, DATABASE_NAME, DATABASE_VERSION );
		InDatabaseObjectPersisterFactory inDatabaseObjectPersisterFactory = new InDatabaseObjectPersisterFactory( application, databaseHelper, classCollection );
        cacheManager.addPersister( inDatabaseObjectPersisterFactory );
        
        
		return cacheManager;
	}
}
