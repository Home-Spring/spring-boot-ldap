package dk.digitalidentity.app;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;

@Configuration
public class LdapConfig {

	@Bean
	public LdapContextSource getContextSource() throws Exception{
		LdapContextSource ldapContextSource = new LdapContextSource();
		ldapContextSource.setUrl("ldap://192.168.1.125:389");
		ldapContextSource.setBase("dc=adcts,dc=local");
		ldapContextSource.setUserDn("user1@adcts.local");
		ldapContextSource.setPassword("Qwerty1");
		return ldapContextSource;
	}
	
	@Bean
	public LdapTemplate ldapTemplate() throws Exception{
		LdapTemplate ldapTemplate = new LdapTemplate(getContextSource());
		ldapTemplate.setIgnorePartialResultException(true);
		ldapTemplate.setContextSource(getContextSource());
		return ldapTemplate;
	}
}
