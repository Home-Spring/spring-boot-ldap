package dk.digitalidentity.app;

import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.filter.Filter;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import java.util.ArrayList;
import java.util.List;


public class LdapGroupRepo {

	private LdapTemplate ldapTemplate;

	public void setLdapTemplate(LdapTemplate ldapTemplate) {
		this.ldapTemplate = ldapTemplate;
	}

	public List<LdapGroup> getAllGroup(Filter filter) {
		return ldapTemplate.search("", filter.encode(), new AttributesMapper() {
			@Override
			public LdapGroup mapFromAttributes(Attributes attr) throws NamingException {
                LdapGroup group = new LdapGroup();
				if (attr.get("cn")!=null) group.setCn((String) attr.get("cn").get());
				if (attr.get("gidNumber")!=null) group.setGidNumber((String) attr.get("gidNumber").get());
				if (attr.get("memberUid")!=null) for (int memberUid=0; memberUid<attr.get("memberUid").size(); memberUid++) group.memberUids((String) attr.get("memberUid").get(memberUid));
				return group;
			}
		});
	}

}
