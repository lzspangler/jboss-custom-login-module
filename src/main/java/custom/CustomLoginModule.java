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
	 * (required) Compares the result of this method with the actual password.
	 */
	@Override
	protected String getUsersPassword() throws LoginException {

		System.out.println("Authenticating user " + getUsername());

		String password = super.getUsername();
		password = password.toUpperCase();
		return password;
	}

	@Override
	protected boolean validatePassword(String inputPassword,
			String expectedPassword) {

		String encryptedInputPassword = (inputPassword == null) ? null
				: inputPassword.toUpperCase();

		System.out.println("Entered password: " + encryptedInputPassword);
		System.out.println("Expected password: " + expectedPassword);

		return super.validatePassword(encryptedInputPassword, expectedPassword);
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
