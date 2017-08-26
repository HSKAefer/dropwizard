package de.dokukaefer.btp.core;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * This one can also be used to stop the infinite recursion of teams -> players.
 * It serializes the json object and adds specific values that need to be shown . for example here the id field
 * To use it, simply put the annotation @JsonSerialize(using = ParentReferenceSerializer.class) to the match value of player
 * 
 * @author D066730
 *
 */
public class TeamReferenceSerializer extends JsonSerializer<Team>{

	@Override
	public void serialize(Team team, JsonGenerator jgen, SerializerProvider provider)
			throws IOException, JsonProcessingException {
		jgen.writeStartObject();
//		jgen.writeNumberField("id", team.getId());
		jgen.writeStringField("teamname", team.getTeamname());
		jgen.writeEndObject();
		
	}
}
