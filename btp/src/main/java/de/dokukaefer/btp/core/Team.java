package de.dokukaefer.btp.core;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;


import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "TEAMS")
@NamedQueries(
		{
			@NamedQuery(
				name = "de.dokukaefer.btp.core.Team.findAll",
				query = "SELECT t FROM Team t"
			)
		}
)
public class Team {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	@JsonProperty
    private Long id;
	
	@Column(name = "TEAMNAME")
	@JsonProperty
	private String teamname;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "team", cascade = CascadeType.ALL)
	@JsonProperty
	private List<Player> players;
		
	public Team() {
		
	}
	
	public Team(String teamname) {
		this.teamname = teamname;
	}

	public void addPlayer(Player player) {
		players.add(player);
		player.setTeam(this);
	}
	
	public void removePlayer(Player player) {
		players.remove(player);
		player.setTeam(null);
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTeamname() {
		return teamname;
	}

	public void setTeamname(String teamname) {
		this.teamname = teamname;
	}
	
	
	public List<Player> getPlayers() {
		return players;
	}

	public void setPlayers(List<Player> players) {
		this.players = players;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((players == null) ? 0 : players.hashCode());
		result = prime * result + ((teamname == null) ? 0 : teamname.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Team other = (Team) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (players == null) {
			if (other.players != null)
				return false;
		} else if (!players.equals(other.players))
			return false;
		if (teamname == null) {
			if (other.teamname != null)
				return false;
		} else if (!teamname.equals(other.teamname))
			return false;
		return true;
	}
	
}

