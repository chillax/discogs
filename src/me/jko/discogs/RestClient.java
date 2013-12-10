package me.jko.discogs;

import org.scribe.builder.ServiceBuilder;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * A helper class for building different kinds of HTTP requests using scribe
 * @author joonas
 *
 */

public class RestClient {
	
	private OAuthService service;
	private Token token;
	private SharedPreferences prefs;
	
	// constructor	
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
	
	public String get(String s) {
		OAuthRequest req = new OAuthRequest(Verb.GET, s);
		// discogs api requires this one
		req.addHeader("User-Agent", "DiscogsAndroidClient/0.1 +http://jko.me");
		service.signRequest(token, req);
		Response res = req.send();
		
		return res.getBody();
	}
}
