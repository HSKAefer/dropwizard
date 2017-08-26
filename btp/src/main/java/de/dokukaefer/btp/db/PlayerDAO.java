package de.dokukaefer.btp.db;

import java.util.List;
import java.util.Optional;

import org.hibernate.SessionFactory;

import de.dokukaefer.btp.core.Player;
import de.dokukaefer.btp.core.Team;
import io.dropwizard.hibernate.AbstractDAO;

public class PlayerDAO extends AbstractDAO<Player>{

	public PlayerDAO(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	public Optional<Player> findById(Long id) {
		return Optional.ofNullable(get(id));
    }
	
	public Player create(Player player) {
        return persist(player);
    }
	
	 public void delete(Player player) {
	    	this.currentSession().delete(Player.class.getName(), player);
	 }
	 
	  public void update(Player player) {
	    	this.currentSession().update(Player.class.getName(), player);
	    }
	 
	  @SuppressWarnings("unchecked")
		public List<Player> findAll() {
	       return list(namedQuery("de.dokukaefer.btp.core.Player.findAll"));
	    }
}
