package de.dokukaefer.btp.core;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;

import static javax.persistence.TemporalType.DATE;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatTypes;

@Entity
@Table(name = "GAMES")
@NamedQueries(
	{
		@NamedQuery(
			name = "de.dokukaefer.btp.core.Game.findAll",
			query = "SELECT m from Game m"
		)
	}
)
//@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
public class Game {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	@JsonProperty
    private Long id;
	
	@Column(name = "DATE")
//	@Temporal(DATE) //temporal only for date or calendar properties
	//this jsonformat shape needs to be equivalent with the chosen date in the new game date field. 
	//till now it was not possible to enter the input type="date" in games_details.htm
	// because one cannot format the date value of the selected one
	//from yyyy-MM-ddThh:mm:ss:SSSZ to dd.MM.yyyy. So the input type is still text
	// The new LocalDate and ZonedDate does also not work correctly because they only accept yyyy-MM-dd
//	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
	private Date date;
	
//	@OneToMany(mappedBy = "game")
//	@JsonSerialize(using = GameReferenceSerializer.class)
//	private List<Result> results;
	
	@ManyToMany
	@JoinTable(name = "GAMES_TEAMS", 
	joinColumns = @JoinColumn(name = "GAME_ID", referencedColumnName="ID"),
	inverseJoinColumns = @JoinColumn(name = "TEAM_ID", referencedColumnName="ID"))
	@Size(min = 2, max = 2)
	private Set<Team> teams;

	public void addTeam(Team team) {
		teams.add(team);
	}
	
	public void removeTeam(Team team) {
        teams.remove(team);
    }
	
	public Game() {
		
	}
	
	public Game(Date date) {
		this.date = date;
	}
	
//	public List<Result> getResults() {
//		return results;
//	}
	
//	public void addTeam(Team t, int points) {
//		Result result = new Result();
//		result.setGame(this);
//		result.setTeam(t);
//		result.setGameId(this.getId());
//		result.setTeamId(t.getId());
//		result.setPoints(3);
//		if (this.results == null) {
//			this.results = new ArrayList<>();
//		}
//		
//		this.results.add(result);
//		t.getResults().add(result);
//	
//	}

//	public void setResults(List<Result> results) {
//		this.results = results;
//	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	//with this serializer the date changes from 1506549600000 to 25.09.2013
//	@JsonSerialize(using = GameDateSerializer.class) //currently not required because the @jsonformat annotation works already
	public Date getDate() {
		return date;
	}

//	@JsonDeserialize(using = GameDateDeserializer.class)
	public void setDate(Date date) {
		this.date = date;
	}

	public Set<Team> getTeams() {
		return teams;
	}

	public void setTeams(Set<Team> teams) {
		this.teams = teams;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Game other = (Game) obj;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
}
