package me.jko.discogs;

import org.scribe.builder.ServiceBuilder;
import org.scribe.model.Token;
import org.scribe.oauth.OAuthService;

import android.content.Context;
import android.content.SharedPreferences;

public class RestClient {
	
	private OAuthService service;
	private Token token;
	private SharedPreferences prefs;
	
	public RestClient(Context c) {
		 service = new ServiceBuilder()
		  .provider(DiscogsApi.class)
		  .apiKey(c.getString(R.string.api_key))
		  .apiSecret(c.getString(R.string.api_secret))
		  .callback(c.getString(R.string.api_callback))
		  .build();
		 
		 prefs = c.getSharedPreferences("DiscogsPrefs", 0);
		 
		 token = new Token(prefs.getString("access_token", null), prefs.getString("access_secret", null));
	}
}
