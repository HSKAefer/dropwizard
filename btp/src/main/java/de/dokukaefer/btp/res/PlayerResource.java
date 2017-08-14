package de.dokukaefer.btp.res;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import de.dokukaefer.btp.core.Player;
import de.dokukaefer.btp.core.Team;
import de.dokukaefer.btp.db.PlayerDAO;
import de.dokukaefer.btp.db.TeamDAO;
import io.dropwizard.hibernate.UnitOfWork;

@Path("/players")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PlayerResource {

	private final PlayerDAO playerDAO;
	private final TeamDAO teamDAO;
	
	public PlayerResource(PlayerDAO playerDAO, TeamDAO teamDAO) {
		this.playerDAO = playerDAO;
		this.teamDAO = teamDAO;
	}

	@GET
	@Path("/{playerid}")
    @UnitOfWork
    public Response getPlayer(@PathParam("playerid") Long playerid) {
//		return playerDAO.findById(playerid).orElseThrow(() -> new NotFoundException("A player with id " + playerid + " cannot be found"));
		Player playerFound = playerDAO.findById(playerid);
		
		return Response.status(Response.Status.OK).entity(playerFound).build();
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
    public Response createPlayer(Player player) {
    	Team teamFound = teamDAO.findById(player.getTeam());
    	teamFound.addPlayer(player);
  
    	Player newplayer = playerDAO.create(player);

    	return Response.status(Response.Status.CREATED).entity(newplayer).build();
    }
    
    
}
