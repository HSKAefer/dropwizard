package de.dokukaefer.btp.auth;

import io.dropwizard.auth.Authorizer;

/**
 * This class handles the authorization with a given principal and role.
 * It decides wheter the access to an endpoint oder method is granted or not
 * @author D066730
 *
 */
public class CustomAuthorizer implements Authorizer<User> {

	@Override
	public boolean authorize(User user, String role) {
		if (user.getRoles() != null && user.getRoles().contains(role)) {
			return user.getName().equals("admin") && role.equals("ADMIN");
		}
		return false;
	}

	
}
