package de.dokukaefer.btp.auth;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;

/**
 * TODO for readme
 * It is possible to secure the resources that are provided. Therefore two main protection strategies are availabl.
 * HTTP Basic Authentication via Username and Password and the OAuth 2 security with bearer tokens.
 * 
 * In this example the HTTP Basic Credentionals is used - seen by the implementation of the Authenticator interface and the parameter of 
 * BasicCredentionals.
 * 
 * It is possible to use both. OAuth2 and BASIC in one Application.
 * In this case two response methods are required with the annotated parameters @ Auth BasicPrincipal and @ Auth OAuthPrincipal
 * see: http://www.dropwizard.io/1.1.4/docs/manual/auth.html
 * @author D066730
 *
 */
public class CustomAuthenticator implements Authenticator<BasicCredentials, User>{

	//mapping users to roles
	private static final Map<String, Set<String>> USERS = ImmutableMap.of(
			"guest", ImmutableSet.of(),
			"admin", ImmutableSet.of("ADMIN")
			);

	@Override
	public Optional<User> authenticate(BasicCredentials credentials) throws AuthenticationException {
		if (USERS.containsKey(credentials.getUsername()) && "admin".equals(credentials.getPassword())) {
			return Optional.of(new User(credentials.getUsername(), USERS.get(credentials.getUsername())));
		}
		
		return Optional.empty();
	}

}