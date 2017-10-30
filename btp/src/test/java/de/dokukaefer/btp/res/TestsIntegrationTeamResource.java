package de.dokukaefer.btp.res;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.failBecauseExceptionWasNotThrown;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;
//import org.glassfish.jersey.test.grizzly.GrizzlyTestContainerFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.dokukaefer.btp.auth.CustomAuthenticator;
import de.dokukaefer.btp.auth.CustomAuthorizer;
import de.dokukaefer.btp.auth.User;
import de.dokukaefer.btp.core.Team;
import de.dokukaefer.btp.db.TeamDAO;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.auth.basic.BasicCredentialAuthFilter;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.mockito.junit.MockitoJUnitRunner;

import com.google.common.collect.ImmutableList;


public class TestsIntegrationTeamResource {
	private static final BasicCredentialAuthFilter<User> BASIC_AUTH_HANDLER = 
		new BasicCredentialAuthFilter.Builder<User>()
		.setAuthenticator(new CustomAuthenticator())
		.setAuthorizer(new CustomAuthorizer())
		.setPrefix("Basic")
		.setRealm("ADMIN ZONE")
		.buildAuthFilter();
	
	@ClassRule
	public static final ResourceTestRule RULE = ResourceTestRule.builder()
		.addProvider(RolesAllowedDynamicFeature.class)
		.addProvider(new AuthDynamicFeature(BASIC_AUTH_HANDLER))
        .addProvider(new AuthValueFactoryProvider.Binder<>(User.class))
       // .setTestContainerFactory(new GrizzlyTestContainerFactory())
        .addProvider(Team.class)
        .build();
	
//	 @Test
//	    public void testProtectedEndpoint() {
//	        String secret = RULE.target("/teams").request()
//	                .header(HttpHeaders.AUTHORIZATION, "Basic Z29vZC1ndXk6c2VjcmV0")
//	                .get(String.class);
//	        assertThat(secret).startsWith("admin");
//	    }
	 
	 @Test
	    public void testProtectedEndpointNoCredentials401() {
	        try {
	            RULE.target("/teams").request()
	                .get(String.class);
	            failBecauseExceptionWasNotThrown(NotAuthorizedException.class);
	        } catch (NotAuthorizedException e) {
	            assertThat(e.getResponse().getStatus()).isEqualTo(401);
	            assertThat(e.getResponse().getHeaders().get(HttpHeaders.WWW_AUTHENTICATE))
	                    .containsOnly("Basic realm=\"ADMIN ZONE\"");
	        }

	    }
//	private static final TeamDAO TEAM_DAO = mock(TeamDAO.class);
//	
//	//ClassRule and Rule are Dropwizard annotations, that allows to boot a standalone container inside a test case 
//	@ClassRule
//	public static final ResourceTestRule RESOURCES = ResourceTestRule.builder()
//			.addResource(new TeamResource(TEAM_DAO))
//			.build();
//	
//	private Team team;
//	
//	@Before
//    public void setup() {
//       team = new Team();
//       team.setTeamname("BV Malsch");
//    }
//	
//	 @After
//	 public void tearDown(){
//	     // we have to reset the mock after each test because of the
//	     // @ClassRule, or use a @Rule as mentioned below.
//	   reset(TEAM_DAO);
//	 }
//	
////	 @Test
////	 public void readTeam() {
////		 when(TEAM_DAO.create(any(Team.class))).thenReturn(team);
////		 final Response response = RESOURCES.target("team")
////				 .request(MediaType.APPLICATION_JSON_TYPE)
////				 .post(Entity.entity(team, MediaType.APPLICATION_JSON_TYPE));
////		 
////		 
////		 assertEquals("BV Malsch", team.getTeamname());
//////		 assertThat(team.getTeamname()).isNotNull();
////		 
//////		 assertThat(team.getTeamname()).isEqualTo("BV Malsch");
//////		 assertThat(RESOURCES.target("teams/1").request().get(Team.class)).isEqualTo(team);
////		 
//////		 verify(TEAM_DAO).findById(1L);
////	 }
//	 
//	 @Test
//	    public void listTeams() throws Exception {
//	        final ImmutableList<Team> teams = ImmutableList.of(team);
//	        when(TEAM_DAO.findAll()).thenReturn(teams);
//
//	        final List<Team> response = RESOURCES.target("/teams")
//	            .request().get(new GenericType<List<Team>>() {
//	            });
//
//	        verify(TEAM_DAO).findAll();
//	        assertThat(response).containsAll(teams);
//	    }
//	 
//	 
}
