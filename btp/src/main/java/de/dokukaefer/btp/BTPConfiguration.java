package de.dokukaefer.btp;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.dropwizard.Configuration;
import io.dropwizard.bundles.assets.AssetsBundleConfiguration;
import io.dropwizard.bundles.assets.AssetsConfiguration;
import io.dropwizard.db.DataSourceFactory;

public class BTPConfiguration extends Configuration implements AssetsBundleConfiguration {
	
	//provides the datasourcefactory instance for a managed connection to the database
	@Valid
    @NotNull
    private DataSourceFactory database = new DataSourceFactory();
	
	//Maps the webpages to the root path
	@Valid
	@NotNull
	@JsonProperty
	private AssetsConfiguration assets = AssetsConfiguration.builder().build();
	
	
	 @JsonProperty("database")
	    public DataSourceFactory getDataSourceFactory() {
	        return database;
	    }
	
	 @JsonProperty("database")
	    public void setDataSourceFactory(DataSourceFactory dataSourceFactory) {
	        this.database = dataSourceFactory;
	    }
 
	@Override
	public AssetsConfiguration getAssetsConfiguration() {
		return assets;
	}
	
//	public void setAssetsConfiguration(AssetsConfiguration assets) {
//		this.assets = assets;
//	}
	

}
