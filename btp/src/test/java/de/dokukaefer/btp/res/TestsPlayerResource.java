package de.dokukaefer.btp.res;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import de.dokukaefer.btp.core.Player;
import de.dokukaefer.btp.db.PlayerDAO;
import io.dropwizard.testing.junit.ResourceTestRule;

public class TestsPlayerResource {

	private static final PlayerDAO PLAYER_DAO = mock(PlayerDAO.class);
	
	@ClassRule
	public static final ResourceTestRule resources = ResourceTestRule.builder()
		.addResource(new PlayerResource(PLAYER_DAO))
		.build();
	
	private final Player player1 = new Player("Lars", "Ricken");
	private final Player player2 = new Player("Karl-Heinz", "Riedle");
	
	@Before
	public void setup() {
		when(PLAYER_DAO.findById(1L)).thenReturn(Optional.of(player1));
		when(PLAYER_DAO.findById(2L)).thenReturn(Optional.of(player2));
		
		
	}
	
	@After
	public void tearDown() {
		 // we have to reset the mock after each test because of the
        // @ClassRule, or use a @Rule as mentioned below.
		reset(PLAYER_DAO);
	}
	
	@Test
    public void testGetPlayer() {
        assertThat(resources.target("/players/1").request().get(Player.class))
                .isEqualTo(player1);
        verify(PLAYER_DAO).findById(1L);
    }
	
	@Test
	public void getFirstName() {
		Player actual = resources.target("/players/2").request().get(Player.class);
		assertThat(actual.getFirstname()).isEqualTo(player2.getFirstname());
	}
	
	
}
