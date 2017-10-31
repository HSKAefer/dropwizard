package de.dokukaefer.btp.core;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;


public class GameDateDeserializer extends JsonDeserializer<Date>{
	
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
	
	@Override
	public Date deserialize(JsonParser jparser, DeserializationContext context)
			throws IOException, JsonProcessingException {
		
		String formattedDate = jparser.getText();
		
//		if (formattedDate.length() > 20) {
//			ZonedDateTime zonedDateTime = ZonedDateTime.parse(formattedDate);
//			return zonedDateTime.toLocalDate();
//		}
		
//		return LocalDate.parse(formattedDate);
		
		try {
			return dateFormat.parse(formattedDate);
		} catch (ParseException e) {
			 throw new RuntimeException(e);
		}
		
	}
	
	
}
