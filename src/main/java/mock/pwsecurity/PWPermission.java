package mock.pwsecurity;

public class PWPermission {

	private int PWPermissionCode;

	public int getPWPermissionCode() {
		return PWPermissionCode;
	}

	public void setPWPermissionCode(int pWPermissionCode) {
		PWPermissionCode = pWPermissionCode;
	}

	@Override
	public String toString() {
		return "PWPermission [PWPermissionCode=" + PWPermissionCode + "]";
	}

}
