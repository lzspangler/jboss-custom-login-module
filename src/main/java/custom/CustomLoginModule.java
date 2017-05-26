package custom;

import java.security.Principal;
import java.security.acl.Group;

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
	private Principal identity;

	@Override
	public boolean login() throws LoginException {
		boolean authenticated = false;
		super.loginOk = false;

		String[] credentials = getUsernameAndPassword();
		String username = credentials[0];
		String password = credentials[1];

		try {
			// call PWSecurity login()
			// if it returns a non-null PWPermission, set authenticated to true
			PWLoginProfile loginProfile = pwSecurity.login(username, password);
			if (loginProfile != null) {
				authenticated = true;
			}
		} catch (Exception e) {
			LoginException loginException = new LoginException(
					"Failed to authenticate through PWSecurity: " + e.getLocalizedMessage());
			loginException.initCause(e);
			throw loginException;
		}

		try {
			identity = createIdentity(username);
		} catch (Exception e) {
			LoginException loginException = new LoginException(
					"Failed to create principal: " + e.getLocalizedMessage());
			loginException.initCause(e);
			throw loginException;
		}

		super.loginOk = authenticated;
		return authenticated;
	}

	@Override
	protected Group[] getRoleSets() throws LoginException {
		SimpleGroup group = new SimpleGroup("Roles");

		try {
			// retrieve permissions for user and app
			PWPermission[] permissions = pwSecurity.getAllPermissions(getUsername(), BUSINESS_CENTRAL_APP_CODE);

			for (PWPermission permission : permissions) {
				group.addMember(new SimplePrincipal(Integer.toString(permission.getPWPermissionCode())));
			}
		} catch (Exception e) {
			throw new LoginException("Failed to create group member for " + group);
		}

		return new Group[] { group };
	}

	@Override
	protected Principal getIdentity() {
		return this.identity;
	}

	// This is not used, but required to implement if extending
	// UsernamePasswordLoginModule
	@Override
	protected String getUsersPassword() throws LoginException {
		return null;
	}

}