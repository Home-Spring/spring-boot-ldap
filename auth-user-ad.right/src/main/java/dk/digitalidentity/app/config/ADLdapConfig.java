package dk.digitalidentity.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;

@Configuration
public class ADLdapConfig {

	public static final String ROOT = "DC=adcts,DC=local";

	@Bean
	public LdapContextSource adContextSource() {
		LdapContextSource ldapContextSource = new LdapContextSource();
		ldapContextSource.setUrl("ldap://192.168.1.125:389");
		ldapContextSource.setBase(ROOT);
//		ldapContextSource.setUserDn("user1@adcts.local");
//		ldapContextSource.setPassword("Qwerty1");
		return ldapContextSource;
	}

	@Bean(name = "adLdapTemplate")
	public LdapTemplate adLdapTemplate() {
		LdapTemplate ldapTemplate = new LdapTemplate(adContextSource());
		ldapTemplate.setIgnorePartialResultException(true);
		ldapTemplate.setContextSource(adContextSource());
		return ldapTemplate;
	}
}
