package me.jko.discogs.models;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Profile {
	
	public Profile() {};

	private String username;
	
	private String email;
	
	private int num_collection;

	private int rating_avg;
	
	private int num_wantlist;
	
	
	public String getUsername() {
		return this.username;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	public int getNum_collection() {
		return this.num_collection;
	}
	
	public int getRating_avg() {
		return this.rating_avg;
	}
	
	public int getNum_wantlist() {
		return this.num_wantlist;
	}
}
