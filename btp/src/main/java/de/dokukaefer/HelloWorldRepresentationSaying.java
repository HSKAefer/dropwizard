package de.dokukaefer;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Simple POJO. but its immutable (unveränderbar) which makes it very easy to reason about in multi threaded environments as well as single threades ones
 * Java Bean Standard allowes Jackson to deserialize the id and content properties to the JSON format
 */
public class HelloWorldRepresentationSaying {

	private long id;
	private String content;
	
	public HelloWorldRepresentationSaying() {
		//jackson deserialization
	}
	
	public HelloWorldRepresentationSaying(long id, String content) {
		this.id = id;
		this.content = content;
	}

	@JsonProperty
	public long getId() {
		return id;
	}

	@JsonProperty
	public String getContent() {
		return content;
	}
	
	
	
}
