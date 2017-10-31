package de.dokukaefer.btp;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;

import de.dokukaefer.btp.core.Player;
import de.dokukaefer.btp.core.Team;
import io.dropwizard.testing.ConfigOverride;
import io.dropwizard.testing.ResourceHelpers;
import io.dropwizard.testing.junit.DropwizardAppRule;

public class TestsLogFile {

	 private static final String TMP_FILE = createTempFile();
	 
	 //Path to the configuration file
	 private static final String CONFIG_PATH = ResourceHelpers.resourceFilePath("test-config.yml");

	 
	  //Start the application and database before all test methods
	  @ClassRule
	    public static final DropwizardAppRule<BTPConfiguration> RULE = new DropwizardAppRule<>(
	            BTPApplication.class, CONFIG_PATH,
	            ConfigOverride.config("database.url", "jdbc:h2:" + TMP_FILE)); //h2 in memory
	  		
	
	    private static String createTempFile() {
	        try {
	            return File.createTempFile("test-config", null).getAbsolutePath();
	        } catch (IOException e) {
	            throw new IllegalStateException(e);
	        }
	    }
	    
	    
//	    @Test
//	    public void testPostTeam() throws Exception {
//	        final Team team = new Team("BV Malsch");
//	        final Team newTeam = RULE.client().target("http://localhost:" + RULE.getLocalPort() + "/teams")
//	                .request()
//	                .post(Entity.entity(team, MediaType.APPLICATION_JSON_TYPE))
//	                .readEntity(Team.class);
//	        assertThat(newTeam.getId()).isNotNull();
//	        assertThat(newTeam.getTeamname()).isEqualTo(team.getTeamname());
//	    }
	    
	    
	    @Test
	    public void testLogFileWritten() throws IOException {
	        // The log file is using a size and time based policy, which used to silently
	        // fail (and not write to a log file). This test ensures not only that the
	        // log file exists, but also contains the log line that jetty prints on startup
	        final Path log = Paths.get("./tmp/application.log");
	        assertThat(log).exists();
	        final String actual = new String(Files.readAllBytes(log), UTF_8);
	        assertThat(actual).contains("0.0.0.0:" + RULE.getLocalPort());
	    }
	    
}
