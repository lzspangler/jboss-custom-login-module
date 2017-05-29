package custom;

import java.io.InputStream;
import java.security.Principal;
import java.security.acl.Group;
import java.util.Properties;

import javax.security.auth.login.LoginException;

import org.jboss.security.SimpleGroup;
import org.jboss.security.SimplePrincipal;
import org.jboss.security.auth.spi.UsernamePasswordLoginModule;

import com.pwj.cid.security.domain.PWLoginProfile;
import com.pwj.cid.security.domain.PWPermission;
import com.pwj.cid.security.domain.PWSecurityImpl;

public class CustomLoginModule extends UsernamePasswordLoginModule {

	private static final String LOGIN_MODULE_PROPERTIES = "login-module.properties";
	private static final String BUSINESS_CENTRAL_APP_CODE = "business.central.app.code";

	private Properties loginProperties = new Properties();
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

		// Retrieve login module properties
		loadLoginProperties();

		try {
			int businessCentralAppCode = Integer.parseInt(loginProperties.getProperty(BUSINESS_CENTRAL_APP_CODE));

			// Retrieve permissions for user and app
			PWPermission[] permissions = pwSecurity.getAllPermissions(getUsername(), businessCentralAppCode);

			for (PWPermission permission : permissions) {
				group.addMember(new SimplePrincipal(Integer.toString(permission.getPWPermissionCode())));
			}
		} catch (Exception e) {
			LoginException loginException = new LoginException("Failed to create group member for " + group);
			loginException.initCause(e);
			throw loginException;
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

	private void loadLoginProperties() throws LoginException {
		try {
			InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(LOGIN_MODULE_PROPERTIES);

			if (inputStream != null) {
				loginProperties.load(inputStream);
			}
		} catch (Exception e) {
			LoginException loginException = new LoginException(
					"Failed to load login module properties: " + e.getLocalizedMessage());
			loginException.initCause(e);
			throw loginException;
		}
	}

}