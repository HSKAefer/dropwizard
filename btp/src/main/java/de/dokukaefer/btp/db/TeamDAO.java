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
	
	public Optional<Team> findById(long id) {
        return Optional.ofNullable(get(id));
    }

    public Team create(Team person) {
        return persist(person);
    }

    @SuppressWarnings("unchecked")
	public List<Team> findAll() {
        return list(namedQuery("de.dokukaefer.btp.core.Team.findAll"));
    }
	
}
