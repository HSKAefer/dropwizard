package de.dokukaefer.btp;

import de.dokukaefer.btp.core.Team;
import de.dokukaefer.btp.db.TeamDAO;
import de.dokukaefer.btp.res.TeamResource;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class BTPApplication extends Application<BTPConfiguration>{
	
	public static void main(String[] args) throws Exception {
		new BTPApplication().run(args);
	}
	
	private final HibernateBundle<BTPConfiguration> hibernateBundle =
	        new HibernateBundle<BTPConfiguration>(Team.class) {
	            @Override
	            public DataSourceFactory getDataSourceFactory(BTPConfiguration configuration) {
	                return configuration.getDataSourceFactory();
	            }
	        };
	
    @Override
    public String getName() {
    	return "Badminton Tournament Platform";
    }
	  
	@Override
	public void initialize(Bootstrap<BTPConfiguration> bootstrap) {
		// Map requests to /${1} to be found in the class path at /webContent/${1}
		// see: https://github.com/dropwizard-bundles/dropwizard-configurable-assets-bundle
		// configured assets bundle via bootstrap.addBundle(new ConfiguredAssetsBundle("/webContent/", "/", "btp.html"));
		// info: assets bundle needs to be added in pom.xml
		bootstrap.addBundle(new AssetsBundle("/webContent/", "/", "btp.html"));
		// the MigrationsBundle is required to execute ..*.jar db migrate config.yml
		// it is possible to refactor databases with xml/yaml/json/sql formats
		// here we use the migrations.xml file in src/main/resources
		// see: http://www.liquibase.org/
		// info: requires the dropwizard-migrations artifact in pom.xml
		bootstrap.addBundle(new MigrationsBundle<BTPConfiguration>() {
			@Override
			public DataSourceFactory getDataSourceFactory(BTPConfiguration configuration) {
				return configuration.getDataSourceFactory();
			}
		});
		bootstrap.addBundle(hibernateBundle);
	}        
	        
	@Override
	public void run(BTPConfiguration configuration, Environment environment) throws Exception {
		final TeamDAO teamDAO = new TeamDAO(hibernateBundle.getSessionFactory());
		environment.jersey().register(new TeamResource(teamDAO));
		// changes the application root path. since dropwizard 0.8.0 it can be changed in the yaml file via
		// applicationContextPath: /
		//rootPath: /application
		//environment.jersey().setUrlPattern("/application/*");
	}
	
	

}
