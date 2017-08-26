package de.dokukaefer.btp;

import com.codahale.metrics.health.HealthCheckRegistry;

import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.lifecycle.setup.LifecycleEnvironment;
import io.dropwizard.logging.BootstrapLogging;
import io.dropwizard.setup.Environment;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TestsDatabaseConnection {

//	private final DataSourceFactory hsqlConfig = new DataSourceFactory();
//	
//	{
//		BootstrapLogging.bootstrap();
//		hsqlConfig.setUrl("jdbc:h2:mem:databaseConnTest-" + System.currentTimeMillis());
//		hsqlConfig.setUser("sa");
//		hsqlConfig.setPassword("sa");
//		hsqlConfig.setDriverClass("org.h2.Driver");
//        hsqlConfig.setValidationQuery("SELECT 1");
//	}
//	private final HealthCheckRegistry healthChecks = mock(HealthCheckRegistry.class);
//    private final LifecycleEnvironment lifecycleEnvironment = mock(LifecycleEnvironment.class);
//    private final Environment environment = mock(Environment.class);
	
}
