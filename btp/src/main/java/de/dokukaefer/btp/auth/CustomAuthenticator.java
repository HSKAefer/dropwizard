package de.dokukaefer.btp.auth;

import java.io.IOException;
import java.security.Principal;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.ws.rs.container.ContainerRequestContext;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import io.dropwizard.auth.Authenticator;

import io.dropwizard.auth.AuthFilter;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.basic.BasicCredentials;

public class CustomAuthenticator implements Authenticator<BasicCredentials, User>{

	//mapping users to roles
	private static final Map<String, Set<String>> USERS = ImmutableMap.of(
			"guest", ImmutableSet.of(),
			"admin", ImmutableSet.of("ADMIN")
			);

	@Override
	public Optional<User> authenticate(BasicCredentials credentials) throws AuthenticationException {
//		if (USERS.containsKey(credentials.getUsername()) && "secret".equals(credentials.getPassword())) {
//			return Optional.of(new User(credentials.getUsername(), USERS.get(credentials.getUsername())));
//		}
		
		//first try of credentionals
		if("secret".equals(credentials.getPassword())) {
			return Optional.of(new User(credentials.getUsername()));
		}
		return Optional.empty();
	}

}
