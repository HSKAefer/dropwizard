package de.dokukaefer.btp;

import javax.ws.rs.client.Client;

import org.glassfish.jersey.client.JerseyClientBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.dokukaefer.btp.core.Game;
import de.dokukaefer.btp.core.Player;
import de.dokukaefer.btp.core.Team;
import de.dokukaefer.btp.db.GameDAO;
import de.dokukaefer.btp.db.PlayerDAO;
import de.dokukaefer.btp.db.TeamDAO;
import de.dokukaefer.btp.exceptions.UnprocessableException;
import de.dokukaefer.btp.health.TeamHealthCheck;
import de.dokukaefer.btp.res.GameResource;
import de.dokukaefer.btp.res.PlayerResource;
import de.dokukaefer.btp.res.TeamResource;
import io.dropwizard.Application;
import io.dropwizard.bundles.assets.ConfiguredAssetsBundle;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class BTPApplication extends Application<BTPConfiguration>{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BTPApplication.class);
	
	public static void main(String[] args) throws Exception {
		new BTPApplication().run(args);
	}
	
	private final HibernateBundle<BTPConfiguration> hibernateBundle =
	        new HibernateBundle<BTPConfiguration>(Game.class, Team.class, Player.class) {
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
		// with the configured assets bundle one can change css and js scripts without reloading the server
		// info: configured assets bundle needs to be added in pom.xml
		// see: https://github.com/dropwizard-bundles/dropwizard-configurable-assets-bundle
		bootstrap.addBundle(new ConfiguredAssetsBundle("/webContent/", "/", "btp.htm"));
		// info: assets bundle needs to be added in pom.xml
		//bootstrap.addBundle(new AssetsBundle("/webContent/", "/", "btp.html"));
		
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
		final GameDAO gameDAO = new GameDAO(hibernateBundle.getSessionFactory());
		final TeamDAO teamDAO = new TeamDAO(hibernateBundle.getSessionFactory());
		final PlayerDAO playerDAO = new PlayerDAO(hibernateBundle.getSessionFactory());
		final Client client = new JerseyClientBuilder().build();
		
		LOGGER.info("register the jersey REST resources");
		environment.jersey().register(new TeamResource(teamDAO));
		environment.jersey().register(new GameResource(gameDAO));
		environment.jersey().register(new PlayerResource(playerDAO, teamDAO));
		environment.jersey().register(new UnprocessableException());
		// changes the application root path. since dropwizard 0.8.0 it can be changed in the yaml file via
		// applicationContextPath: /
		//rootPath: /application
//		environment.healthChecks().register("database", new DatabaseHealthCheck());
		environment.healthChecks().register("TeamHealthCheck", new TeamHealthCheck(client));
		//environment.jersey().setUrlPattern("/application/*");
	}
	
	

}
