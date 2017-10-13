package de.dokukaefer.btp.core;

import java.util.List;
import java.util.Set;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * association class. Required to save the points from each team for a specific game
 * @author D066730
 *
 */
//@Entity
//@Table(name = "RESULTS")
//@AssociationOverrides({
//    @AssociationOverride(name = "primaryKey.team",
//        joinColumns = @JoinColumn(name = "TEAM_ID")),
//    @AssociationOverride(name = "primaryKey.game",
//        joinColumns = @JoinColumn(name = "GAME_ID")) })
public class Result {

//	 // composite-id key
//	private ResultId primaryKey = new ResultId();
//	//additional Fields
//	private Integer points;
//	
//	@EmbeddedId
//	public ResultId getPrimaryKey() {
//		return primaryKey;
//	}
//	
//	 @Transient
//	    public Team getTeam() {
//	        return getPrimaryKey().getTeam();
//	    }
//	
//	 @Transient
//	    public Game getGame() {
//	        return getPrimaryKey().getGame();
//	    }
//	
//	 @Column(name = "POINTS")
//	 public Integer getPoints() {
//		 return this.points;
//	 }
//	 
//	 //--------------------setter---------------------------------
//	 
//		public void setPrimaryKey(ResultId primaryKey) {
//	        this.primaryKey = primaryKey;
//	    }
//		
//		public void setTeam(Team team) {
//	        getPrimaryKey().setTeam(team);
//	    }
//	 
//	 public void setGame(Game game) {
//	        getPrimaryKey().setGame(game);
//	    }
//
//	public void setPoints(Integer points) {
//		this.points = points;
//	}
	 
	 
}
