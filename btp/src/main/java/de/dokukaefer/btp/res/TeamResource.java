package de.dokukaefer.btp.res;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
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

import de.dokukaefer.btp.auth.User;
import de.dokukaefer.btp.core.Team;
import de.dokukaefer.btp.db.TeamDAO;
import io.dropwizard.auth.Auth;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.LongParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


@Path("/teams")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Api(value = "Teams")//swagger annotation shown on localhost:8080/api/swagger - value Teams needs to be fit with the tag name in BTPApiConfig interface
public class TeamResource {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TeamResource.class);
	
	
	private final TeamDAO teamDAO;
	
	public TeamResource(TeamDAO teamDAO) {
		this.teamDAO = teamDAO;
	}

	  
	//security restrictions for the resources
	//following roles are allowed: rolesallowed, permitall and denyall
	//see: https://spin.atomicobject.com/2016/07/26/dropwizard-dive-part-1/
	@GET
	@Path("/{teamid}")
    @UnitOfWork
    @PermitAll //all authenticated users are allowed to access the method - remember that a guest user was registered
    @ApiOperation(value = "Find team by id", notes = "Find a team by id", response = Team.class)
	@ApiResponses( {
	    @ApiResponse( code = 404, message = "No team is existing" ), 
	    @ApiResponse( code = 400, message = "The given id is not a number"),
	    @ApiResponse( code = 200, message = "OK - Returns the team object")
	} )
	public Response getTeam(@ApiParam(value="The id of a team") @PathParam("teamid") LongParam teamid) {
//		Team teamFound = teamDAO.findById(teamid);
		Optional<Team> teamOptional = teamDAO.findById(teamid.get());
		
		return Response.status(Response.Status.OK).entity(teamOptional).build();
    }

	@GET
	@Path("/{teamid}/xml") //with this annotation an additional xml endpoint is given
    @UnitOfWork
    @PermitAll //all authenticated users are allowed to access the method - remember that a guest user was registered
    @Produces(MediaType.APPLICATION_XML)
	@ApiOperation(value = "Find team by id", notes = "find a team by id (xml) format")
    public Response getTeamXML(@PathParam("teamid") LongParam teamid) {
//		Team teamFound = teamDAO.findById(teamid);
		Optional<Team> teamOptional = teamDAO.findById(teamid.get());
		
		return Response.status(Response.Status.OK).entity(teamOptional).build();
    }
	
	
    @GET
    @UnitOfWork
    @PermitAll
    @ApiOperation(value = "List all teams", notes = "List all the teams")
    public Response getAllTeams(@Auth Optional<User> user) {
    	if (user.isPresent()) {
    		LOGGER.info("Hello, " + user.get().getName());
    	} else {
    		LOGGER.info("Unknown user detected in GET method ");
    	}
    	
    	if (teamDAO.findAll().isEmpty()) {
    		throw new NotFoundException("No team is available");
    	} else {
    	List<Team> teamFound = teamDAO.findAll();
    	return Response.status(Response.Status.OK).entity(teamFound).build();
    	}
    }
    
    @POST
    @UnitOfWork
    //permitall works with the given credentionals .. for the roles allowed like the name mentioned, the roles are required
//    @PermitAll
    @RolesAllowed({"ADMIN"})
    @ApiOperation(value = "Create new team", notes = "Creates a new team, requires admin login", response = Team.class)
    public Response createTeam(@Auth User user, @NotNull @Valid Team team) {
    	
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
    @RolesAllowed("ADMIN")
    @ApiOperation(value = "Delete existing team", notes = "Deletes an exsiting team")
    public Response deleteTeam(@Auth User user, @PathParam("teamid") LongParam teamid) {
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
//    @RolesAllowed({"ADMIN"})
    @ApiOperation(value="Update existing team", notes = "updates the name of an existing team")
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
