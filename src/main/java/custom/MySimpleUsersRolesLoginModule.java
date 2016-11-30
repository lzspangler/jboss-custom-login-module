package custom;

import javax.security.auth.login.LoginException;

import org.jboss.security.auth.spi.UsersRolesLoginModule;

public class MySimpleUsersRolesLoginModule extends UsersRolesLoginModule {
	
	public boolean login() throws LoginException {
		
		return super.login();
	}
	
	
}
