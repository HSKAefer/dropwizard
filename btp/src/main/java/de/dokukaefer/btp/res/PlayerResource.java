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
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.Response.Status;

import org.eclipse.jetty.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.dokukaefer.btp.core.Player;
import de.dokukaefer.btp.core.Team;
import de.dokukaefer.btp.db.PlayerDAO;
import de.dokukaefer.btp.db.TeamDAO;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.LongParam;

@Path("/players")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PlayerResource {

	private static final Logger LOGGER = LoggerFactory.getLogger(PlayerResource.class);
	
	private final PlayerDAO playerDAO;
	private final TeamDAO teamDAO;
	
	public PlayerResource(PlayerDAO playerDAO, TeamDAO teamDAO) {
		this.playerDAO = playerDAO;
		this.teamDAO = teamDAO;
	}

	@GET
	@Path("/{playerid}")
    @UnitOfWork
    public Response getPlayer(@PathParam("playerid") LongParam playerid) {
		Optional<Player> playerOptional = playerDAO.findById(playerid.get());
		
		return Response.status(Response.Status.OK).entity(playerOptional).build();
	}

    @GET
    @UnitOfWork
    public List<Player> getAllPlayers() {
    	if (playerDAO.findAll().isEmpty()) {
    		throw new NotFoundException("No player is available");
    	} else {
    	return playerDAO.findAll();
    	}
    }
    
    @POST
    @UnitOfWork
    public Response createPlayer (Player player) { //(@NotNull @Valid Player player) {
    	if (player.getId() != null) {
    		LOGGER.error("id field is not empty ");
    		throw new WebApplicationException("id field is not empty ", HttpStatus.UNPROCESSABLE_ENTITY_422);
    	} 

        Player newPlayer = playerDAO.create(player);
    	URI uri = UriBuilder.fromResource(PlayerResource.class).build(newPlayer);
    	LOGGER.info("the response uri is " + uri);
    	
    	return Response.created(uri).status(Response.Status.CREATED).entity(newPlayer).build();
    }
    
    @PUT
    @Path("/{playerid}")
    @UnitOfWork
    public Response updatePlayer(@PathParam("playerid") LongParam playerid, Player player) { //@Valid @NotNull Player player) {

       	Optional<Player> foundPlayer = playerDAO.findById(playerid.get());
    	if (!foundPlayer.isPresent()) {
    		LOGGER.error("The id " + playerid + " cannot be found");
    		throw new WebApplicationException("The id " + playerid + " cannot be found", HttpStatus.NOT_FOUND_404);
    	}
    	    	
    	LOGGER.info("player updated successfully");
    	
    	//the teamid values needs to be stored and therefore the update should work with it
    	//the teamname that needs to be changed comes from the team param
    	foundPlayer.get().setFirstname(player.getFirstname());
    	foundPlayer.get().setLastname(player.getLastname());
    	foundPlayer.get().setTeam(player.getTeam());

    	playerDAO.update(foundPlayer.get());

    	URI uri = UriBuilder.fromResource(TeamResource.class).build(foundPlayer);
    	return Response.created(uri).status(Response.Status.OK).entity(foundPlayer).build();
    }
    
    @DELETE
    @Path("/{playerid}")
    @UnitOfWork
    public Response deleteTeam(@PathParam("playerid") LongParam playerid) {
    	Optional<Player> playerOptional = playerDAO.findById(playerid.get());
    	
    	if (!playerOptional.isPresent()) {
    		throw new WebApplicationException("the id " + playerid + " cannot be found", Status.NOT_FOUND);
    	}
    	
    	playerDAO.delete(playerOptional.get());
    	
    	return Response.ok("{ \"team successfully deleted\" : \"" + playerOptional.get().getFirstname() + " " 
    																+ playerOptional.get().getLastname() + "\" }").build();
    }
    
}
