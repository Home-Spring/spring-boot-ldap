package dk.digitalidentity.app.dao;

import java.util.List;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;

import dk.digitalidentity.app.data.ADLdap;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.filter.Filter;

public class ADLdapDao {
	private LdapTemplate ldapTemplate;

	public void setLdapTemplate(LdapTemplate ldapTemplate) {
		this.ldapTemplate = ldapTemplate;
	}

	public List<ADLdap> getAllPerson(String base, Filter filter) {
		return ldapTemplate.search(base, filter.encode(), new AttributesMapper() {
			@Override
			public ADLdap mapFromAttributes(Attributes attr) throws NamingException {
				ADLdap adLdap = new ADLdap();
				adLdap.setCn((String) attr.get("cn").get());
				return adLdap;
			}
		});
	}

	@Deprecated
	public List<ADLdap> getAllPerson(Filter filter) {
		return ldapTemplate.search("", filter.encode(), new AttributesMapper() {
			@Override
			public ADLdap mapFromAttributes(Attributes attr) throws NamingException {
				ADLdap adLdap = new ADLdap();
				adLdap.setCn((String) attr.get("cn").get());
				return adLdap;
			}
		});
	}
}
