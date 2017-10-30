package de.dokukaefer.btp.core;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Set;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

//
public class GameReferenceSerializer extends JsonSerializer<Set<Game>> {
	
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
	
	@Override
	public void serialize(Set<Game> gameList, JsonGenerator jgen, SerializerProvider provider)
			throws IOException, JsonProcessingException {
		
//		if (gameList.isEmpty()) {
//			return;
//		}
		
		jgen.writeStartArray();
		for (Game game : gameList) {
			String formattedDate = dateFormat.format(game.getDate());
			jgen.writeStartObject();
			jgen.writeNumberField("id", game.getId());
			jgen.writeStringField("date", formattedDate);			
			jgen.writeEndObject();
		
			
		}
		jgen.writeEndArray();
		
	}

}
