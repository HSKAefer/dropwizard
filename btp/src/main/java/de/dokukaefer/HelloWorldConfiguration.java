package de.dokukaefer;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.dropwizard.Configuration;

/**
 * This Configuration file specifies environment specific parameters within a yaml file. This file will be deserialized to an instance of the
 * application's configuration class and be validated. A dropwizard application can hold serveral configuration files
 * 
 * This config file prepares a simple helloworld application with a template for saying hello and a defaultName in case no username is specified
 */
public class HelloWorldConfiguration extends Configuration {

	//if the yaml values are left alone blank an exception will be thrown
	@NotEmpty
	private String template;
	
	@NotEmpty
	private String defaultName = "Mr. Bool";

	//required so that jackson is able to deserialize the properties from a yaml file and back.
	@JsonProperty
	public String getTemplate() {
		return template;
	}

	@JsonProperty
	public void setTemplate(String template) {
		this.template = template;
	}

	@JsonProperty
	public String getDefaultName() {
		return defaultName;
	}

	@JsonProperty
	public void setDefaultName(String defaultName) {
		this.defaultName = defaultName;
	}
	
	
}