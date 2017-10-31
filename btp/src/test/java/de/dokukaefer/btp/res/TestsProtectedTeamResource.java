package de.dokukaefer.btp.res;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.failBecauseExceptionWasNotThrown;
import static org.mockito.Mockito.mock;

import javax.ws.rs.ForbiddenException;
import javax.ws.rs.core.HttpHeaders;

import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;
import org.glassfish.jersey.test.grizzly.GrizzlyWebTestContainerFactory;
import org.junit.ClassRule;
import org.junit.Test;

import de.dokukaefer.btp.auth.CustomAuthenticator;
import de.dokukaefer.btp.auth.CustomAuthorizer;
import de.dokukaefer.btp.auth.User;
import de.dokukaefer.btp.core.Team;
import de.dokukaefer.btp.db.TeamDAO;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.auth.basic.BasicCredentialAuthFilter;
import io.dropwizard.testing.junit.ResourceTestRule;
import io.federecio.dropwizard.sample.SampleBasicAuthenticator;

public class TestsProtectedTeamResource {

	 private static final BasicCredentialAuthFilter<User> BASIC_AUTH_HANDLER =
	            new BasicCredentialAuthFilter.Builder<User>()
	                    .setAuthenticator(new CustomAuthenticator())
	                    .setAuthorizer(new CustomAuthorizer())
	                    .setPrefix("Basic")
	                    .setRealm("SUPER SECRET STUFF")
	                    .buildAuthFilter();
	
	 @ClassRule
	    public static final ResourceTestRule RULE = ResourceTestRule.builder()
	            .addProvider(RolesAllowedDynamicFeature.class)
	            .addProvider(new AuthDynamicFeature(BASIC_AUTH_HANDLER))
	            .addProvider(new AuthValueFactoryProvider.Binder<>(User.class))
	            .setTestContainerFactory(new GrizzlyWebTestContainerFactory())
	            .addProvider(TeamResource.class)
	            .build();
	 
	 //TODO tests are missing here
	 
}
