package custom;

import java.security.acl.Group;
import java.util.Map;

import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.LoginException;

import org.jboss.security.SimpleGroup;
import org.jboss.security.SimplePrincipal;
import org.jboss.security.auth.spi.UsernamePasswordLoginModule;

public class CustomLoginModule extends UsernamePasswordLoginModule {

	@SuppressWarnings("rawtypes")
	public void initialize(Subject subject, CallbackHandler callbackHandler,
			Map sharedState, Map options) {

		super.initialize(subject, callbackHandler, sharedState, options);
	}

	/**
	 * Compares the result of this method with the entered password.
	 * If validatePassword isn't overridden here, it will do a String compare.
	 */
	@Override
	protected String getUsersPassword() throws LoginException {

		System.out.println("Authenticating user " + getUsername());

		String password = super.getUsername();
		return password;
	}

	@Override
	protected boolean validatePassword(String inputPassword,
			String expectedPassword) {

		return true;
	}

	/**
	 * (required) The groups of the user. Must be at least one group called
	 * "Roles" for user.
	 */
	@Override
	protected Group[] getRoleSets() throws LoginException {

		SimpleGroup group = new SimpleGroup("Roles");

		try {
			group.addMember(new SimplePrincipal("user_role"));
			group.addMember(new SimplePrincipal("admin"));
		} catch (Exception e) {
			throw new LoginException("Failed to create group member for "
					+ group);
		}

		System.out.println("Role for user " + getUsername() + ": "
				+ group.members().nextElement().toString());

		return new Group[] { group };
	}

}
