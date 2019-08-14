package dk.digitalidentity.app;

import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.filter.Filter;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;
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
                group.setCn((String) attr.get("cn").get());
                group.setGidNumber((String) attr.get("gidNumber").get());
				return group;
			}
		});
	}

}
