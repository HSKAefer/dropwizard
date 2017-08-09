package de.dokukaefer.btp;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;

public class BTPConfiguration extends Configuration {//implements AssetsBundleConfiguration {
	
	@Valid
    @NotNull
    private DataSourceFactory database = new DataSourceFactory();
	
	
	
	 @JsonProperty("database")
	    public DataSourceFactory getDataSourceFactory() {
	        return database;
	    }
	
	 @JsonProperty("database")
	    public void setDataSourceFactory(DataSourceFactory dataSourceFactory) {
	        this.database = dataSourceFactory;
	    }

// not required right now - maybe later	 
//	@Override
//	public AssetsConfiguration getAssetsConfiguration() {
//		return assets;
//	}
//	
//	@Valid
//	@NotNull
//	private AssetsConfiguration assets = AssetsConfiguration.builder().build();
}
