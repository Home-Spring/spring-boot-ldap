package dk.digitalidentity.app.dao;

import java.util.List;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;

import dk.digitalidentity.app.data.ADLdapPerson;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.filter.Filter;

public class ADPersonDao {
	private LdapTemplate ldapTemplate;

	public void setLdapTemplate(LdapTemplate ldapTemplate) {
		this.ldapTemplate = ldapTemplate;
	}

	@Deprecated
	public List<ADLdapPerson> getAllPerson(Filter filter) {
		return ldapTemplate.search("", filter.encode(), new AttributesMapper() {
			@Override
			public ADLdapPerson mapFromAttributes(Attributes attr) throws NamingException {
				ADLdapPerson person = new ADLdapPerson();
				person.setCn((String) attr.get("cn").get());
				return person;
			}
		});
	}

	public List<ADLdapPerson> getAllPerson(String base, Filter filter) {
		return ldapTemplate.search(base, filter.encode(), new AttributesMapper() {
			@Override
			public ADLdapPerson mapFromAttributes(Attributes attr) throws NamingException {
				ADLdapPerson person = new ADLdapPerson();
				person.setCn((String) attr.get("cn").get());
				return person;
			}
		});
	}
}
