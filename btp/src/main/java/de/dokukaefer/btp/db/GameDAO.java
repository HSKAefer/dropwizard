package de.dokukaefer.btp.db;

import java.util.List;
import java.util.Optional;

import org.hibernate.SessionFactory;

import de.dokukaefer.btp.core.Game;
import io.dropwizard.hibernate.AbstractDAO;

public class GameDAO extends AbstractDAO<Game>{

	public GameDAO(SessionFactory sessionFactory) {
		super(sessionFactory);
	}
	
	public Optional<Game> findById(Long id) {
		return Optional.ofNullable(get(id));
    }

	
    public Game create(Game game) {
        return persist(game);
    }

    public void delete(Game game) {
    	this.currentSession().delete(Game.class.getName(), game);
    }
    
    public void update(Game game) {
    	this.currentSession().update(Game.class.getName(), game);
    }
    
    
    @SuppressWarnings("unchecked")
	public List<Game> findAll() {
        return list(namedQuery("de.dokukaefer.btp.core.Game.findAll"));
    }
	
	

}
