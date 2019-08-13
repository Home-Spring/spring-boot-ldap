package dk.digitalidentity.app;

public class LdapPerson {

	private String cn;
	private String uid;
	private String gidNumber;
	private String uidNumber;
	private String mail;

	public String getCn() {
		return cn;
	}

	public void setCn(String cn) {
		this.cn = cn;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getGidNumber() {
		return gidNumber;
	}

	public void setGidNumber(String gidNumber) {
		this.gidNumber = gidNumber;
	}

	public String getUidNumber() {
		return uidNumber;
	}

	public void setUidNumber(String uidNumber) {
		this.uidNumber = uidNumber;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	@Override
	public String toString() {
		return "LdapPerson{" +
				"cn='" + cn + '\'' +
				", uid='" + uid + '\'' +
				", gidNumber='" + gidNumber + '\'' +
				", uidNumber='" + uidNumber + '\'' +
				", mail='" + mail + '\'' +
				'}';
	}
}
