package me.jko.discogs;

import android.app.Activity;

import com.octo.android.robospice.request.SpiceRequest;

public class Request extends SpiceRequest<String> {
	
	private String method;
	private String url;
	private RestClient client;
	
	public Request(Activity activity, String method, String url) {
		super(String.class);
		this.method = method;
		this.url = url;
		this.client = new RestClient(activity);
	}
	
	@Override
	public String loadDataFromNetwork() throws Exception {
		String res = "";

		if(this.method == "get") {
			res = client.get(this.url);
		}	
		
		return res;
	}
}
