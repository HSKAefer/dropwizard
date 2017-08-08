package de.dokukaefer.btp;

import de.dokukaefer.btp.core.Team;
import de.dokukaefer.btp.db.TeamDAO;
import de.dokukaefer.btp.res.TeamResource;
import io.dropwizard.Application;
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
	}
	
	

}
