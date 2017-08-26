package de.dokukaefer.btp.core;

import java.io.IOException;
import java.util.Set;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

//
public class GameReferenceSerializer extends JsonSerializer<Set<Game>> {

	@Override
	public void serialize(Set<Game> gameList, JsonGenerator jgen, SerializerProvider provider)
			throws IOException, JsonProcessingException {
		
//		if (gameList.isEmpty()) {
//			return;
//		}
		
		jgen.writeStartArray();
		for (Game game : gameList) {
			jgen.writeStartObject();
			jgen.writeNumberField("id", game.getId());
			jgen.writeNumberField("date", game.getDate());
			jgen.writeEndObject();
		}
		jgen.writeEndArray();
		
	}

}
