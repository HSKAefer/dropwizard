package de.dokukaefer.btp.health;

import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.codahale.metrics.health.HealthCheck;

public class TeamHealthCheck extends HealthCheck {

	private final Client client;
	
	 public TeamHealthCheck(Client client) {
		 super();
	     this.client = client;
	 }
	
	@Override
	protected Result check() throws Exception {
		WebTarget webTarget = client.target("http://localhost:8080/api/teams");
		Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.get();
		@SuppressWarnings("rawtypes")
		List teams = response.readEntity(List.class);
		
		if (teams != null && teams.size() > 0) {
			return Result.healthy("One or more teams are available");
		}
		
		return Result.unhealthy("API failed");
	}

}
