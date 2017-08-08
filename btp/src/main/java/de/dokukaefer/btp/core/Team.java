package de.dokukaefer.btp.core;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

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
	@Column(name = "TEAMID", nullable = false)
    private long teamid;
	
	@Column(name = "TEAMNAME")
	private String teamname;

	public Team() {
		
	}
	
	public Team(String teamname) {
		this.teamname = teamname;
	}

	public long getTeamid() {
		return teamid;
	}

	public void setTeamid(long teamid) {
		this.teamid = teamid;
	}

	public String getTeamname() {
		return teamname;
	}

	public void setTeamname(String teamname) {
		this.teamname = teamname;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (teamid ^ (teamid >>> 32));
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
		if (teamid != other.teamid)
			return false;
		if (teamname == null) {
			if (other.teamname != null)
				return false;
		} else if (!teamname.equals(other.teamname))
			return false;
		return true;
	}
	
	
}

