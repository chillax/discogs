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
	
	@ForeignCollectionField(foreignFieldName = "collection")
	private Collection<CollectionRelease> releases = new ArrayList<CollectionRelease>();
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
	
	public Collection<CollectionRelease> getReleases() {
		return releases;
	}
	
	public void setReleases(List<CollectionRelease> releases) {
		this.releases = releases;
	}
	
	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}
	
	public void setAdditionalProperties(String name, Object value) {
		this.additionalProperties.put(name, value);
	}

}