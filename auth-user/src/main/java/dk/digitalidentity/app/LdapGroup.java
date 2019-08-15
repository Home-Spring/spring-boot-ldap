package dk.digitalidentity.app;

import java.util.ArrayList;
import java.util.List;

public class LdapGroup {

	private String cn;
	private String gidNumber;
	private List<String> memberUids = new ArrayList<>();

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

	public List<String> getMemberUids() {
		return memberUids;
	}

	public void setMemberUids(List<String> memberUids) {
		this.memberUids = memberUids;
	}

	public boolean memberUids(String memberUid) {
		return this.memberUids.add(memberUid);
	}

	@Override
	public String toString() {
		return "LdapGroup{" +
				"cn='" + cn + '\'' +
				", gidNumber='" + gidNumber + '\'' +
				", memberUids=" + memberUids +
				'}';
	}
}
