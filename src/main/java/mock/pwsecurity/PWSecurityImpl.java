package mock.pwsecurity;

public class PWSecurityImpl {

	// Throws exception if user doesn't authenticate
	public PWLoginProfile login(String uuname, String password) throws Exception {
		PWLoginProfile pwLoginProfile = null;

		return pwLoginProfile;
	}

	public PWPermission[] getAllPermissions(String uuname, int appCode) {
		PWPermission[] permissions = new PWPermission[5];

		return permissions;
	}

	public static PWSecurityImpl getInstance() {
		return new PWSecurityImpl();
	}
}
