package de.dokukaefer.btp.auth;

import java.security.Principal;
import java.util.Set;

public class User implements Principal {

	private final String userName;
	private final Set<String> roles;
	
	public User(String userName) {
		this.userName = userName;
		this.roles = null;
	}
	
	public User(String userName, Set<String> roles) {
        this.userName = userName;
        this.roles = roles;
    }
	
	//only getters are allowed
	//-----------------------
	//@Override
	public String getName() {
		return userName;
	}

	 public int getId() {
	        return (int) (Math.random() * 100);
	    }

	    public Set<String> getRoles() {
	        return roles;
	    }
	//------------------------
}
