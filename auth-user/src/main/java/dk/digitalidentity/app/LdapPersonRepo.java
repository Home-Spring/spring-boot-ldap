package dk.digitalidentity.app;

import java.util.List;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.filter.Filter;


public class LdapPersonRepo {

	private LdapTemplate ldapTemplate;

	public void setLdapTemplate(LdapTemplate ldapTemplate) {
		this.ldapTemplate = ldapTemplate;
	}

	public List<LdapPerson> getAllPerson(Filter filter) {
		return ldapTemplate.search("", filter.encode(), new AttributesMapper() {
			@Override
			public LdapPerson mapFromAttributes(Attributes attr) throws NamingException {
				LdapPerson person = new LdapPerson();
				person.setCn((String) attr.get("cn").get());
				person.setUid((String) attr.get("uid").get());
				person.setGidNumber((String) attr.get("gidNumber").get());
				person.setUidNumber((String) attr.get("uidNumber").get());
				person.setMail((String) attr.get("mail").get());
				return person;
			}
		});
	}

}
