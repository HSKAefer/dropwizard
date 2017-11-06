package de.dokukaefer.btp.health;

import java.util.Map.Entry;
import java.util.Set;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.codahale.metrics.health.HealthCheck.Result;
import com.codahale.metrics.health.HealthCheckRegistry;

/**
 * With this Resource it is possible to use the health check of a resource without
 * the admin port 8081. Simply use /status within your running application
 * to use it register a new jersey resource:
 * e.jersey().register(new HealthCheckResource(e.healthchecks()));
 * see: https://howtodoinjava.com/dropwizard/health-check-configuration-example/
 * @author D066730
 *
 */
@Produces(MediaType.APPLICATION_JSON)
@Path("/status")
public class HealthCheckResource {
    private HealthCheckRegistry registry;
 
    public HealthCheckResource(HealthCheckRegistry registry) {
        this.registry = registry;
    }
     
    @GET
    public Set<Entry<String, Result>> getStatus(){
        return registry.runHealthChecks().entrySet();
    }
}