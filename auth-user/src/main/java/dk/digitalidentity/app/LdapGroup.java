package dk.digitalidentity.app;

public class LdapGroup {

	private String cn;
	private String gidNumber;

	public String getCn() {
		return cn;
	}

	public void setCn(String cn) {
		this.cn = cn;
	}

	public String getGidNumber() {
		return gidNumber;
	}

	public void setGidNumber(String gidNumber) {
		this.gidNumber = gidNumber;
	}

	@Override
	public String toString() {
		return "LdapGroup{" +
				"cn='" + cn + '\'' +
				", gidNumber='" + gidNumber + '\'' +
				'}';
	}
}
