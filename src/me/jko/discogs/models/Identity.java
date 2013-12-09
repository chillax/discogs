package me.jko.discogs.models;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Identity {
	
	public Identity() {};
	
	private String username;
	
	public String getUsername() {
		return this.username;
	}
}
