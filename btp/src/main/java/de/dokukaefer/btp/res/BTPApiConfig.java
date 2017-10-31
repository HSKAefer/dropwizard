package de.dokukaefer.btp.res;

import io.swagger.annotations.Contact;
import io.swagger.annotations.Info;
import io.swagger.annotations.License;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;

/**
 * To use this interface just annotate resource classes with the @Api annotation and the corresponding tag name.
 * e.G.
 * 		<pre>
 * 	&#064;Api(value = "Teams")
 * 	public clas TeamResource { }
 * 
 * for more than one value use following code:
 * 		
 * 	&#064;Api(tags = {"Teams", "Players"})
 * 	public class TeamResource { }
 * 		</pre>
 *
 *	for more information
 *	@see <a href="https://github.com/swagger-api/swagger-core/wiki/Annotations-1.5.X#swaggerdefinition">https://github.com/swagger-api/swagger-core/wiki/Annotations-1.5.X#swaggerdefinition</a>
 */
//swagger annotations for hierarchical setup
@SwaggerDefinition(
	tags = {
        @Tag(name = "Teams", description = "Managing the teams"),
        @Tag(name = "Players", description = "Managing the players"),
        @Tag(name = "Games", description = "Managing the games")
    },
	info = @Info(
			description = "Overview of the HTTP methods",
			title = "Badminton Tournament Platform API",
			version = "V1.0",
			contact = @Contact(
					name = "Daniel Schaefer",
					email = "dokukaefer@t-online.de",
					url = "https://github.com/HSKAefer/BTP"
			),
			license = @License(
				name = "Apache 2.0",
				url = "http://www.apache.org/licenses/LICENSE-2.0"
			)
	)
)
public interface BTPApiConfig {

}
