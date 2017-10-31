package de.dokukaefer.btp.db;

import java.util.List;
import java.util.Optional;

import org.hibernate.SessionFactory;

import de.dokukaefer.btp.core.Team;
import io.dropwizard.hibernate.AbstractDAO;

public class TeamDAO extends AbstractDAO<Team> {

	public TeamDAO(SessionFactory factory) {
		super(factory);
	}
	
	public Optional<Team> findById(Long id) {
		return Optional.ofNullable(get(id));
    }
	
    public Team create(Team team) {
        return persist(team);
    }

    public void delete(Team team) {
    	this.currentSession().delete(Team.class.getName(), team);
    }
    
    public void update(Team team) {
    	this.currentSession().update(Team.class.getName(), team);
    }
    
    
    @SuppressWarnings("unchecked")
	public List<Team> findAll() {
        return list(namedQuery("de.dokukaefer.btp.core.Team.findAll"));
    }
	
}
