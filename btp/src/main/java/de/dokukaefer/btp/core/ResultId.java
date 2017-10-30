package de.dokukaefer.btp.core;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;


//@Embeddable
public class ResultId { //implements Serializable {
//	
//	private Long teamId;
//	
//	private Long gameId;
//
//	@Override
//	public int hashCode() {
//		return (int) (teamId + gameId);
//	}
//
//	@Override
//	public boolean equals(Object obj) {
//		if (obj instanceof ResultId) {
//			ResultId otherId = (ResultId) obj;
//			return (otherId.teamId == this.teamId) && (otherId.gameId == this.gameId);
//		}
//		return false;
//	} 
//	
//	

}
