package de.dokukaefer.btp;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;

import io.dropwizard.testing.ConfigOverride;
import io.dropwizard.testing.ResourceHelpers;
import io.dropwizard.testing.junit.DropwizardAppRule;

public class IntegrationTest {

	 private static final String TMP_FILE = createTempFile();
	 
	 /**
	  * Path to the configuration file
	  */
	 private static final String CONFIG_PATH = ResourceHelpers.resourceFilePath("config.yml");

	 /**
	  * Start the application before all test methods
	  */
	    @ClassRule
	    public static final DropwizardAppRule<BTPConfiguration> RULE = new DropwizardAppRule<>(
	            BTPApplication.class, CONFIG_PATH,
	            ConfigOverride.config("database.url", "jdbc:h2:" + TMP_FILE));

	    @BeforeClass
	    public static void migrateDb() throws Exception {
	        RULE.getApplication().run("db", "migrate", CONFIG_PATH);
	    }
	
	    private static String createTempFile() {
	        try {
	            return File.createTempFile("test-config", null).getAbsolutePath();
	        } catch (IOException e) {
	            throw new IllegalStateException(e);
	        }
	    }
	    
	    @Test
	    public void testLogFileWritten() throws IOException {
	        // The log file is using a size and time based policy, which used to silently
	        // fail (and not write to a log file). This test ensures not only that the
	        // log file exists, but also contains the log line that jetty prints on startup
	        final Path log = Paths.get("./logs/application.log");
	        assertThat(log).exists();
	        final String actual = new String(Files.readAllBytes(log), UTF_8);
	        assertThat(actual).contains("0.0.0.0:" + RULE.getLocalPort());
	    }
	    
}
