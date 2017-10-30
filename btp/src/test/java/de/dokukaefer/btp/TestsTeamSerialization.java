package de.dokukaefer.btp;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.dokukaefer.btp.core.Team;

import static io.dropwizard.testing.FixtureHelpers.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;

import org.junit.Test;

import io.dropwizard.jackson.Jackson;

public class TestsTeamSerialization {

	private static final ObjectMapper MAPPER = Jackson.newObjectMapper();
	
	@Test
	public void serializesToJson() throws JsonParseException, JsonMappingException, JsonProcessingException, IOException {
		Team team = new Team("BV Malsch");
		team.setId(1L);
		
		final String expected = MAPPER.writeValueAsString(
				MAPPER.readValue(fixture("fixtures/team.json"), Team.class));
		
		//assertJ assertions
		assertThat(MAPPER.writeValueAsString(team)).isEqualTo(expected);
		
	}
	
}
