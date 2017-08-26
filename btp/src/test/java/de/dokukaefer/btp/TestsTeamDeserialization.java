package de.dokukaefer.btp;

import static io.dropwizard.testing.FixtureHelpers.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.dokukaefer.btp.core.Team;
import io.dropwizard.jackson.Jackson;
import junit.framework.TestCase;

public class TestsTeamDeserialization {

	private static final ObjectMapper MAPPER = Jackson.newObjectMapper();
	
	@Test
	public void deserializesFromJSON() throws JsonParseException, JsonMappingException, IOException {
		final Team team = new Team("BV Malsch");
		assertThat(MAPPER.readValue(fixture("fixtures/team.json"), Team.class))
		.isEqualTo(team);
	}
	
}
