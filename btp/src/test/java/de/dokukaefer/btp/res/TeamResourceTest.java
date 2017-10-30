package de.dokukaefer.btp.res;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.JerseyClientBuilder;
//import org.glassfish.jersey.test.grizzly.GrizzlyTestContainerFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import de.dokukaefer.btp.BTPApplication;
import de.dokukaefer.btp.BTPConfiguration;
import de.dokukaefer.btp.core.Team;
import de.dokukaefer.btp.db.TeamDAO;
import io.dropwizard.testing.ResourceHelpers;
import io.dropwizard.testing.junit.DropwizardAppRule;
import io.dropwizard.testing.junit.ResourceTestRule;

/**
 * Unit Test for the {@link TeamResource}
 * @author D066730
 *
 */
public class TeamResourceTest {

	@Rule
	public final DropwizardAppRule<BTPConfiguration> RULE = new DropwizardAppRule<BTPConfiguration>(BTPApplication.class, ResourceHelpers.resourceFilePath("test-config.yml"));
	
	@Test
	public void runServerTest() {
		Client client = new JerseyClientBuilder().build();
		Team team = client.target(
				String.format("http://localhost:%d", RULE.getLocalPort()))
				.path("api/teams").request().get(Team.class);
		
		assertThat(team.getId()).isEqualTo(null);
		
	}
//	private static final TeamDAO TEAM_DAO = mock(TeamDAO.class);
//	
//	// Pre-work -----------------------
//	@ClassRule
//	public static final ResourceTestRule RULE = ResourceTestRule.builder()
//	            .addResource(new TeamResource(TEAM_DAO))
//	          //  .setTestContainerFactory(new GrizzlyTestContainerFactory())
//	            .build();
//	
//	    private Team team;
//
//	    @Before
//	    public void setup() {
//	        team = new Team();
//	        team.setId(1L);
//	    }
//
//	    @After
//	    public void tearDown() {
//	        reset(TEAM_DAO);
//	    }
//	  // --------------------------------
//	    
//	    @Test
//	    public void getTeamSuccess() {
//	        when(TEAM_DAO.findById(1L)).thenReturn(Optional.of(team));
//
//	        Team teamFound = RULE.target("/teams/1").request().get(Team.class);
//
//	        assertThat(teamFound.getId()).isEqualTo(team.getId());
//	        verify(TEAM_DAO).findById(1L);
//	    }
	    
	    
}
