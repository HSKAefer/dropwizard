package de.dokukaefer.btp.res;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
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
import io.dropwizard.jersey.params.LongParam;
import io.dropwizard.jersey.sessions.Session;

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
    public Response getTeam(@PathParam("teamid") LongParam teamid) {
//		Team teamFound = teamDAO.findById(teamid);
		Optional<Team> teamOptional = teamDAO.findById(teamid.get());
		
		return Response.status(Response.Status.OK).entity(teamOptional).build();
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
    
    @POST
    @UnitOfWork
    public Response createTeam(@NotNull @Valid Team team) {
    	if (team.getId() != null) {
    		LOGGER.error("id field is not empty ");
    		//die entity hier ist der return value... gibt das object zurück.. eher errorEntity??
//    		return Response.serverError().status(HttpStatus.UNPROCESSABLE_ENTITY_422).entity(team).build();
    		throw new WebApplicationException("id field is not empty ", HttpStatus.UNPROCESSABLE_ENTITY_422);
    	} 
    	
//		this assertion is already covered by the not null annotation
//    	if (team.getTeamname().isEmpty()) {
//    		LOGGER.error("teamname is empty for " + team.getId());
//    		throw new WebApplicationException("teamname is empty ", HttpStatus.UNPROCESSABLE_ENTITY_422);
//    	}
    		
    	Team newteam = teamDAO.create(team);
    	URI uri = UriBuilder.fromResource(TeamResource.class).build(newteam);
    	LOGGER.info("the response uri is " + uri);
    	
    	return Response.created(uri).status(Response.Status.CREATED).entity(newteam).build();
    }
    
    @DELETE
    @Path("/{teamid}")
    @UnitOfWork
    public Response deleteTeam(@PathParam("teamid") LongParam teamid) {
    	Optional<Team> teamOptional = teamDAO.findById(teamid.get());
    	
    	if (!teamOptional.isPresent()) {
    		throw new WebApplicationException("the id " + teamid + " cannot be found", Status.NOT_FOUND);
    	}
    	
    	teamDAO.delete(teamOptional.get());
    	
    	return Response.ok("{ \"team successfully deleted\" : \"" + teamOptional.get().getTeamname() + "\" }").build();
    }
    
    @PUT
    @Path("/{teamid}")
    @UnitOfWork
    public Response updateTeam(@PathParam("teamid") LongParam teamid, @Valid @NotNull Team team) {

       	Optional<Team> foundTeam = teamDAO.findById(teamid.get());
    	if (!foundTeam.isPresent()) {
    		LOGGER.error("The id " + teamid + " cannot be found");
    		throw new WebApplicationException("The id " + teamid + " cannot be found", HttpStatus.NOT_FOUND_404);
    	}
    	    	
    	LOGGER.info("team updated successfully");
    	
    	//the teamid values needs to be stored and therefore the update should work with it
    	//the teamname that needs to be changed comes from the team param
    	foundTeam.get().setTeamname(team.getTeamname());
    	teamDAO.update(foundTeam.get());

    	URI uri = UriBuilder.fromResource(TeamResource.class).build(foundTeam);
    	return Response.created(uri).status(Response.Status.OK).entity(foundTeam).build();
    }
 
}
