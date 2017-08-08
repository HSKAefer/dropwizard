package de.dokukaefer.btp.res;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import de.dokukaefer.btp.core.Team;
import de.dokukaefer.btp.db.TeamDAO;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.LongParam;

@Path("/teams")
@Produces(MediaType.APPLICATION_JSON)
public class TeamResource {
	
	private final TeamDAO teamDAO;
	
	public TeamResource(TeamDAO teamDAO) {
		this.teamDAO = teamDAO;
	}

	@GET
	@Path("/{teamid}")
    @UnitOfWork
    public Team getTeam(@PathParam("teamid") LongParam teamid) {
        return findSafely(teamid.get());
    }

    private Team findSafely(long teamid) {
        return teamDAO.findById(teamid).orElseThrow(() -> new NotFoundException("A team with id " + teamid + " cannot be found"));
    }

    @GET
    @UnitOfWork
    public List<Team> getAllTeams() {
    	if (teamDAO.findAll().isEmpty()) {
    		throw new NotFoundException("No team is available");
    	} else {
    	return teamDAO.findAll();
    	}
    }
    
    @POST
    @UnitOfWork
    public Team createTeam(Team team) {
    	return teamDAO.create(team);
    }
}
