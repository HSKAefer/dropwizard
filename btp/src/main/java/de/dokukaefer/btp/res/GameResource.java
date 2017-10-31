package de.dokukaefer.btp.res;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.ws.rs.Consumes;
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

import org.eclipse.jetty.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.dokukaefer.btp.core.Game;
import de.dokukaefer.btp.db.GameDAO;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.LongParam;
import io.swagger.annotations.Api;

@Path("/games")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Api(value = "Games")
public class GameResource {

	private static final Logger LOGGER = LoggerFactory.getLogger(GameResource.class);
	
	
	private final GameDAO gameDAO;
	
	public GameResource(GameDAO gameDAO) {
		this.gameDAO = gameDAO;
	}
	
	@GET
	@Path("/{gameid}")
    @UnitOfWork
    public Response getGame(@PathParam("gameid") LongParam gameid) {
		Optional<Game> gameOptional = gameDAO.findById(gameid.get());
		
		return Response.status(Response.Status.OK).entity(gameOptional).build();
    }

    @GET
    @UnitOfWork
    public Response getAllGames() {
    	if (gameDAO.findAll().isEmpty()) {
    		throw new NotFoundException("No game is available");
    	} else {
    	List<Game> gameFound = gameDAO.findAll();
    	return Response.status(Response.Status.OK).entity(gameFound).build();
    	}
    }
    
    @POST
    @UnitOfWork
    public Response createGame(Game game) { //(@NotNull @Valid Game game) {
    	if (game.getId() != null) {
    		LOGGER.error("id field is not empty ");
    		throw new WebApplicationException("id field is not empty ", HttpStatus.IM_A_TEAPOT_418);
    	} 
    	
    	Game newGame = gameDAO.create(game);
    	URI uri = UriBuilder.fromResource(GameResource.class).build(newGame);
    	LOGGER.info("the response uri is " + uri);
    	
    	return Response.created(uri).status(Response.Status.CREATED).entity(newGame).build();
    }
    
    @PUT
    @Path("/{gameid}")
    @UnitOfWork
    public Response updateGame(@PathParam("gameid") LongParam gameid, Game game) {// @Valid @NotNull Game game) {

       	Optional<Game> foundGame = gameDAO.findById(gameid.get());
    	if (!foundGame.isPresent()) {
    		LOGGER.error("The id " + gameid + " cannot be found");
    		throw new WebApplicationException("The id " + gameid + " cannot be found", HttpStatus.NOT_FOUND_404);
    	}
    	    	
    	LOGGER.info("game updated successfully");
    	

    	foundGame.get().setDate(game.getDate());
    	foundGame.get().setTeams(game.getTeams());
    	gameDAO.update(foundGame.get());

    	URI uri = UriBuilder.fromResource(GameResource.class).build(foundGame);
    	return Response.created(uri).status(Response.Status.OK).entity(foundGame).build();
    }
 
    
}
