package de.dokukaefer.btp.res;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import de.dokukaefer.btp.core.Team;
import de.dokukaefer.btp.db.TeamDAO;
import io.dropwizard.testing.junit.ResourceTestRule;


public class TestsIntegrationTeamResource {

	private static final TeamDAO teamDAO = mock(TeamDAO.class);
	
	//ClassRule and Rule are Dropwizard annotations, that allows to boot a standalone container inside a test case 
	@ClassRule
	public static final ResourceTestRule resources = ResourceTestRule.builder()
			.addResource(new TeamResource(teamDAO))
			.build();
	
	private final Team team = new Team("BV Malsch");
	
	@Before
    public void setup() {
        when(teamDAO.findById(eq(1L))).thenReturn(Optional.of(team));
    }
	
	 @After
	 public void tearDown(){
	     // we have to reset the mock after each test because of the
	     // @ClassRule, or use a @Rule as mentioned below.
	   reset(teamDAO);
	 }
	
	 @Test
	 public void readTeam() {
		 assertThat(team.getTeamname()).isNotNull();
		 assertThat(team.getTeamname()).isEqualTo("BV Malsch");
		 assertThat(resources.target("teams/1").request().get(Team.class)).isEqualTo(team);
		 
		 verify(teamDAO).findById(1L);
	 }
	 
}
