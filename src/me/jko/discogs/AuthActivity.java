/*
 * This activity handles oAuth, it loads discog.com's authorization site in a webview
 * Most of the code comes from http://login2win.blogspot.fi/2012/07/android-linkedin-oauth-implementation.html with minor tweaks
 */

package me.jko.discogs;

import org.scribe.builder.ServiceBuilder;
import org.scribe.exceptions.OAuthException;
import org.scribe.model.Token;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;
import me.jko.discogs.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class AuthActivity extends Activity {
	
	private OAuthService mService;
	private WebView mWebView;
	private Token mRequestToken;

	 @Override
	 public void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
		 setContentView(R.layout.activity_auth);

		 mService = new ServiceBuilder()
		  .provider(DiscogsApi.class)
		  .apiKey(getString(R.string.api_key))
		  .apiSecret(getString(R.string.api_secret))
		  .callback(getString(R.string.api_callback))
		  .build();
		 
		 new AuthTask().execute();
	 }
	
	 private class AuthTask extends AsyncTask<Void, Void, String> {
		protected String doInBackground(Void... arg0) {
			// Temporary URL
			String authURL = "http://api.discogs.com/";
			
			try {
				mRequestToken = mService.getRequestToken();
				authURL = mService.getAuthorizationUrl(mRequestToken);
			} catch ( OAuthException e ) {
				e.printStackTrace();
				return null;
			}
			
			return authURL;
		}
		 
		@SuppressLint("SetJavaScriptEnabled")
		@Override
		protected void onPostExecute(String authURL) {
			mWebView = (WebView) findViewById(R.id.auth_webview);
			 
			WebSettings webSettings = mWebView.getSettings();
			webSettings.setJavaScriptEnabled(true);
			 
			mWebView.setWebViewClient(new WebViewClient() {
				@Override
				public boolean shouldOverrideUrlLoading(WebView view, String url) {
					super.shouldOverrideUrlLoading(view, url);
					 
					// when the callback url is loaded
					if(url.startsWith("oauth")) {
						mWebView.setVisibility(WebView.GONE);
					 
						final String url1 = url;
						Thread t1 = new Thread() {
							public void run() {
								Uri uri = Uri.parse(url1);
								String verifier = uri.getQueryParameter("oauth_verifier");
								Verifier v = new Verifier(verifier);
								Token accessToken = mService.getAccessToken(mRequestToken, v);
								 
								Intent intent = new Intent();
								intent.putExtra("access_token", accessToken.getToken());
								intent.putExtra("access_secret", accessToken.getSecret());
								setResult(RESULT_OK, intent);
								 
								finish();
							}
						};
						 
						t1.start(); 
					}
					 
					return false;
				}
			});
			 
			mWebView.loadUrl(authURL);
		}
	}
}
