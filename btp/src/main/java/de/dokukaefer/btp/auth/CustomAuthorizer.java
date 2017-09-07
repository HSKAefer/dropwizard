package de.dokukaefer.btp.auth;

import io.dropwizard.auth.Authorizer;

public class CustomAuthorizer implements Authorizer<User> {

	@Override
	public boolean authorize(User user, String role) {
		if (user.getRoles() != null && user.getRoles().contains(role)) {
			return user.getName().equals("admin") && role.equals("ADMIN");
		}
		return false;
	}

	
}
