package de.dokukaefer.btp.db;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import de.dokukaefer.btp.core.Game;
import de.dokukaefer.btp.core.Player;
import de.dokukaefer.btp.core.Team;
import io.dropwizard.testing.junit.DAOTestRule;

public class TestsTeamDAO {

	// the player.class is required because of the oneToMany / manyToOne relationsship
	@Rule
	public DAOTestRule daoTestRule = DAOTestRule.newBuilder()
		.addEntityClass(Team.class)
		.addEntityClass(Player.class)
		.addEntityClass(Game.class)
		.build();
	
	private TeamDAO teamDAO;
	
	@Before
	public void setUp() {
		teamDAO = new TeamDAO(daoTestRule.getSessionFactory());
	}
	
	@Test
	public void createTeam() {
		final Team teamMalsch = daoTestRule.inTransaction(() -> teamDAO.create(new Team("BV Malsch")));
		assertThat(teamMalsch.getId()).isGreaterThan(0);
		assertThat(teamMalsch.getId()).isEqualTo(1);
		assertThat(teamMalsch.getTeamname()).isEqualTo("BV Malsch");
		assertThat(teamDAO.findById(teamMalsch.getId())).isEqualTo(Optional.of(teamMalsch));
	}
	
	@Test
	public void findAll() {
		daoTestRule.inTransaction((Runnable) () -> {
			teamDAO.create(new Team("BV Malsch"));
			teamDAO.create(new Team("TUS Bietigheim"));		
		});
		
		final List<Team> teams = teamDAO.findAll();
		assertThat(teams).extracting("teamname").containsOnly("BV Malsch", "TUS Bietigheim");
	}
	
	@Test(expected = NullPointerException.class)
	public void handlesNullTeamname() {
		daoTestRule.inTransaction(() -> teamDAO.create(new Team(null)));
	}
	
	@Test
	public void findById() {
		final Team teamMalsch = daoTestRule.inTransaction(() -> teamDAO.create(new Team("BV Malsch")));
		final Team teamBietigheim = daoTestRule.inTransaction(() -> teamDAO.create(new Team("TUS Bietigheim")));

		assertThat(teamDAO.findById(teamMalsch.getId()).get().getId()).isEqualTo(1);
		assertThat(teamDAO.findById(teamBietigheim.getId()).get().getId()).isEqualTo(2);
		assertThat(teamDAO.findById(teamBietigheim.getId()).get().getId()).isNotEqualTo(1);
		assertThat(teamDAO.findById(teamBietigheim.getId()).get().getId()).isNotEqualTo(3);
	}
	
	@Test
	public void idGenerated() {
		final Team teamMalsch = daoTestRule.inTransaction(() -> teamDAO.create(new Team("BV Malsch")));
		final Team teamBietigheim = daoTestRule.inTransaction(() -> teamDAO.create(new Team("TUS Bietigheim")));

		assertThat(teamMalsch.getId() + 1).isEqualTo(teamBietigheim.getId());
	}
	
}
