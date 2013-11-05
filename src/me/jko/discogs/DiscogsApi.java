package me.jko.discogs;

import org.scribe.builder.api.DefaultApi10a;
import org.scribe.model.*;

/*
 * Discogs OAuth api definition for scribe
 */

public class DiscogsApi extends DefaultApi10a {
	
	private static final String AUTHORIZE_URL = "http://www.discogs.com/oauth/authorize?oauth_token=%s";
	
	@Override
	public String getAccessTokenEndpoint() {
		return "http://api.discogs.com/oauth/access_token";
	}

	@Override
	public String getAuthorizationUrl(Token requestToken) {
		return String.format(AUTHORIZE_URL, requestToken.getToken());
	}

	@Override
	public String getRequestTokenEndpoint() {
		return "http://api.discogs.com/oauth/request_token";
	}

}