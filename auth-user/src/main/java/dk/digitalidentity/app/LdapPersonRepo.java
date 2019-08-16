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
				if (attr.get("cn")!=null) person.setCn((String) attr.get("cn").get());
				if (attr.get("uid")!=null) person.setUid((String) attr.get("uid").get());
				if (attr.get("gidNumber")!=null) person.setGidNumber((String) attr.get("gidNumber").get());
				if (attr.get("uidNumber")!=null) person.setUidNumber((String) attr.get("uidNumber").get());
				if (attr.get("mail")!=null) person.setMail((String) attr.get("mail").get());
				if (attr.get("sn")!=null) person.setSn((String) attr.get("sn").get());
				return person;
			}
		});
	}

}
