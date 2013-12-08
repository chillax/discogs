package me.jko.discogs.models;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@JsonIgnoreProperties(ignoreUnknown = true)
@DatabaseTable(tableName = "profile")
public class Profile {
	
	public Profile() {};

	@DatabaseField(id = true)
	private String username;
	
	@DatabaseField
	private String email;
	
	@DatabaseField
	private int num_collection;

	@DatabaseField
	private int rating_avg;
	
	@DatabaseField
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
