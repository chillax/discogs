package me.jko.discogs.models;

import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)

public class CollectionRelease {
	
	private ReleaseCollection collection;
	
	private Integer id;
	private Integer instance_id;
	private Integer folder_id;

	private Integer rating;
	private Basic_information basic_information;
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getInstance_id() {
		return instance_id;
	}
	
	public void setInstance_id(Integer instance_id) {
		this.instance_id = instance_id;
	}
	
	public Integer getFolder_id() {
		return folder_id;
	}
	
	public void setFolder_id(Integer folder_id) {
		this.folder_id = folder_id;
	}
	
	public Integer getRating() {
		return rating;
	}
	
	public void setRating(Integer rating) {
		this.rating = rating;
	}
	
	public Basic_information getBasic_information() {
		return basic_information;
	}
	
	public void setBasic_information(Basic_information basic_information) {
		this.basic_information = basic_information;
	}
	
	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}
	
	public void setAdditionalProperties(String name, Object value) {
		this.additionalProperties.put(name, value);
	}

}