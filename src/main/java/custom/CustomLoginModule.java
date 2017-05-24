package custom;

import java.security.acl.Group;
import java.util.Map;

import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.LoginException;

import org.jboss.security.SimpleGroup;
import org.jboss.security.SimplePrincipal;
import org.jboss.security.auth.spi.UsernamePasswordLoginModule;

import mock.pwsecurity.PWLoginProfile;
import mock.pwsecurity.PWPermission;
import mock.pwsecurity.PWSecurityImpl;

public class CustomLoginModule extends UsernamePasswordLoginModule {

	private static final int BUSINESS_CENTRAL_APP_CODE = 111;
	private PWSecurityImpl pwSecurity = PWSecurityImpl.getInstance();

	@SuppressWarnings("rawtypes")
	public void initialize(Subject subject, CallbackHandler callbackHandler, Map sharedState, Map options) {
		super.initialize(subject, callbackHandler, sharedState, options);
	}

	@Override
	protected String getUsersPassword() throws LoginException {
		String[] credentials = getUsernameAndPassword();
		String password = credentials[1];

		return password;
	}

	@Override
	public boolean login() throws LoginException {
		boolean authenticated = false;

		try {
			// call PWSecurity login()
			// if it returns a non-null PWPermission, set validated to true
			PWLoginProfile loginProfile = pwSecurity.login(getUsername(), getUsersPassword());

			if (loginProfile != null) {
				authenticated = true;
			}

		} catch (Exception e) {
			System.err.println("Login Exception: " + e.getMessage());
		}

		return authenticated;
	}

	/**
	 * (required) The groups of the user. Must be at least one group called
	 * "Roles" for user.
	 */
	@Override
	protected Group[] getRoleSets() throws LoginException {

		SimpleGroup group = new SimpleGroup("Roles");

		try {
			PWPermission[] permissions = pwSecurity.getAllPermissions(getUsername(), BUSINESS_CENTRAL_APP_CODE);

			for (PWPermission permission : permissions) {
				group.addMember(new SimplePrincipal(Integer.toString(permission.getPWPermissionCode())));
			}

		} catch (Exception e) {
			throw new LoginException("Failed to create group member for " + group);
		}

		System.out.println("Roles for user " + getUsername() + ": " + group.members().toString());

		return new Group[] { group };
	}

}
