package mock.pwsecurity;

public class PWSecurityImpl {

	// Throws exception if user doesn't authenticate
	public PWLoginProfile login(String uuname, String password) throws Exception {
		PWLoginProfile pwLoginProfile = null;

		System.out.println(uuname);
		System.out.println(password);

		if (uuname.equals("testuser") && password.equals("testpassword")) {
			pwLoginProfile = new PWLoginProfile();
		} else {
			throw new Exception("Login failed");
		}

		return pwLoginProfile;
	}

	public PWPermission[] getAllPermissions(String uuname, int appCode) {
		PWPermission[] permissions = new PWPermission[3];

		permissions[0].setPWPermissionCode(0);
		permissions[1].setPWPermissionCode(1);
		permissions[2].setPWPermissionCode(2);

		return permissions;
	}

	public static PWSecurityImpl getInstance() {
		return new PWSecurityImpl();
	}
}
