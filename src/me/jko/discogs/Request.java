package me.jko.discogs;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import me.jko.discogs.models.ReleaseCollection;
import me.jko.discogs.models.Identity;
import me.jko.discogs.models.Profile;
import me.jko.discogs.models.SearchResults;
import me.jko.discogs.models.SingleRelease;
import android.app.Activity;
import android.util.Log;

import com.octo.android.robospice.request.SpiceRequest;

/**
 * 
 * @author joonas
 * All robospice requests go here
 *
 */


public class Request {
	
	private RestClient client;
	private ObjectMapper mapper;
	
	public Request(Activity activity) {
		this.client = new RestClient(activity);
		this.mapper = new ObjectMapper();
	}
	
	/*
	 * Identity request
	 */
	
	public class IdentityRequest extends SpiceRequest<Identity> {
		public IdentityRequest(Activity activity) {
			super(Identity.class);
		}
		
		@Override 
		public Identity loadDataFromNetwork() throws Exception {
			String res;
			Identity ident = new Identity();
			res = client.get("http://api.discogs.com/oauth/identity");
			
			try {
				ident = mapper.readValue(res, Identity.class);
			} catch (JsonGenerationException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			return ident;
		}
	}
	
	/*
	 * Profile request
	 */

	public class ProfileRequest extends SpiceRequest<Profile> {
		
		private String username;
		
		public ProfileRequest(Activity activity, String username) {
			super(Profile.class);
			this.username = username;
		}
		
		@Override 
		public Profile loadDataFromNetwork() throws Exception {
			String res;
			Profile profile = new Profile();
			res = client.get(String.format("http://api.discogs.com/users/%s", username));
			
			try {
				profile = mapper.readValue(res, Profile.class);
			} catch (JsonGenerationException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
						
			return profile;
		}
		
		/*
		 * Create unique cache key, in profile case profile.username is a good one
		 */
		
		public String createCacheKey() {
			return "profile." + username;
		}
	}
	
	/*
	 * Collection
	 */

	public class CollectionRequest extends SpiceRequest<ReleaseCollection> {
		
		private String username;
		private int page;
		
		public CollectionRequest(Activity activity, String username, int page) {
			super(ReleaseCollection.class);
			this.username = username;
			this.page = page;
		}
		
		@Override 
		public ReleaseCollection loadDataFromNetwork() throws Exception {
			String res;
			ReleaseCollection collection = new ReleaseCollection();
			res = client.get(String.format("http://api.discogs.com/users/%s/collection/folders/0/releases?sort=artist&per_page=25&page=%s", username, page));
			
			try {
				collection = mapper.readValue(res, ReleaseCollection.class);
			} catch (JsonGenerationException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			return collection;
		}
		
		/*
		 * Create unique cache key, in profile case profile.username is a good one
		 */
		
		public String createCacheKey() {
			return "collection." + username + "." + page;
		}
	}
	
	/*
	 * Release
	 */
	
	public class ReleaseRequest extends SpiceRequest<SingleRelease> {
		
		private int id;
		
		public ReleaseRequest(Activity activity, int id) {
			super(SingleRelease.class);
			this.id = id;
		}
	
		@Override 
		public SingleRelease loadDataFromNetwork() throws Exception {
			String res;
			SingleRelease release = new SingleRelease();
			res = client.get(String.format("http://api.discogs.com/releases/%s", id));
			Log.d("GET", res);
			
			try {
				release = mapper.readValue(res, SingleRelease.class);
			} catch (JsonGenerationException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			return release;
		}
		
		/*
		 * Create unique cache key, in profile case profile.username is a good one
		 */
		
		public String createCacheKey() {
			return "release." + id;
		}
	}
	
	/*
	 * Search query
	 */

	public class SearchRequest extends SpiceRequest<SearchResults> {
		
		private String query;
		
		public SearchRequest(Activity activity, String query) {
			super(SearchResults.class);
			this.query = query;
		}
	
		@Override 
		public SearchResults loadDataFromNetwork() throws Exception {
			String res;
			SearchResults results = new SearchResults();
			res = client.get(String.format("http://api.discogs.com/database/search?q=%s&type=release&release_title", query));
			
			try {
				results= mapper.readValue(res, SearchResults.class);
			} catch (JsonGenerationException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			return results;
		}
		
		/*
		 * Create unique cache key, in profile case profile.username is a good one
		 */
		
		public String createCacheKey() {
			return "search." + query;
		}
	}	
}
