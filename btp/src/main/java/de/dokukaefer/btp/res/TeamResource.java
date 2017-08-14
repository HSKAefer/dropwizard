package de.dokukaefer.btp.res;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;

import org.eclipse.jetty.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.dokukaefer.btp.core.Team;
import de.dokukaefer.btp.db.TeamDAO;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.IntParam;
import io.dropwizard.jersey.params.LongParam;

@Path("/teams")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TeamResource {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TeamResource.class);
	
	
	private final TeamDAO teamDAO;
	
	public TeamResource(TeamDAO teamDAO) {
		this.teamDAO = teamDAO;
	}

	@GET
	@Path("/{teamid}")
    @UnitOfWork
    public Response getTeam(@PathParam("teamid") Long teamid) {
		Team teamFound = teamDAO.findById(teamid);
		return Response.status(Response.Status.OK).entity(teamFound).build();
    }

    @GET
    @UnitOfWork
    public Response getAllTeams() {
    	if (teamDAO.findAll().isEmpty()) {
    		throw new NotFoundException("No team is available");
    	} else {
    	List<Team> teamFound = teamDAO.findAll();
    	return Response.status(Response.Status.OK).entity(teamFound).build();
    	}
    }
    
//    @POST
//    @UnitOfWork
//    public Team createTeam(Team team) {
////    	if (team.getTeamid() != 0) {
////    		throw new WebApplicationException("the id is not empty", HttpStatus.UNPROCESSABLE_ENTITY_422);
////    	}
//    	return teamDAO.create(team);
//    }
    
    @POST
    @UnitOfWork
    public Response createTeam(@Valid Team team) {
    	Team newteam = teamDAO.create(team);
    	
    	URI uri = UriBuilder.fromResource(TeamResource.class).build(newteam.getId());
    	LOGGER.info("the response uri is " + uri);
    	
    	//    	return Response.created(uri).status(Response.Status.CREATED).entity(newteam).build();
    	return Response.created(uri).build();
//    	return Response.status(Response.Status.CREATED).entity(newteam).build();
    }
    
    
    
//    @DELETE
//    @Path("/{teamid}")
//    @UnitOfWork
//    public void deleteTeam(@PathParam("teamid") IntParam teamid) {
//    	Optional<Team> teamOptional = teamDAO.findById(teamid.get());
//    	if (teamOptional.isPresent()) {
//    		teamDAO.delete(teamOptional.get());
//    	} else {
//    		throw new WebApplicationException("the id " + teamid + " cannot be found", Status.NOT_FOUND);
//    	}
//    }
    
//    @PUT
//    @Path("/{teamid}")
//    @UnitOfWork
//    public Optional<Team> updateTeam(@PathParam("teamid") IntParam teamid, Team team) {
////    	if (team.getTeamid() != null) {
////    		throw new WebApplicationException("the id is not empty", HttpStatus.UNPROCESSABLE_ENTITY_422);
////    	}
//    	team.setTeamid(teamid.get());
//    	teamDAO.update(team);
//    	return teamDAO.findById(teamid.get());
//    }
 
}
