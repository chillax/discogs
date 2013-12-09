package me.jko.discogs.models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

@JsonIgnoreProperties(ignoreUnknown = true)
@DatabaseTable
public class ReleaseCollection {
	
	@DatabaseField(generatedId = true)
	public int id;

	@DatabaseField
	public int foobar;
	
	private Pagination pagination;
	@ForeignCollectionField(foreignFieldName = "collection")
	private Collection<Release> releases = new ArrayList<Release>();
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();
	
	public int getId() {
		return id;
	}
	
	public int getFoobar() {
		return foobar;
	}
	
	public void setFoobar(int i) {
		this.foobar = i;
	}
	
	public Pagination getPagination() {
		return pagination;
	}
	
	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}
	
	public Collection<Release> getReleases() {
		return releases;
	}
	
	public void setReleases(List<Release> releases) {
		this.releases = releases;
	}
	
	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}
	
	public void setAdditionalProperties(String name, Object value) {
		this.additionalProperties.put(name, value);
	}

}