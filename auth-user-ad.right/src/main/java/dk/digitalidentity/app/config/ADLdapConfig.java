package dk.digitalidentity.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;

@Configuration
public class ADLdapConfig {

	@Bean
	public LdapContextSource adContextSource() throws Exception{
		LdapContextSource ldapContextSource = new LdapContextSource();
		ldapContextSource.setUrl("ldap://192.168.1.125:389");
		ldapContextSource.setBase("dc=adcts,dc=local");
//		ldapContextSource.setUserDn("user1@adcts.local");
//		ldapContextSource.setPassword("Qwerty1");
		return ldapContextSource;
	}
	
	@Bean(name = "adLdapTemplate")
	public LdapTemplate adLdapTemplate() throws Exception{
		LdapTemplate ldapTemplate = new LdapTemplate(adContextSource());
		ldapTemplate.setIgnorePartialResultException(true);
		ldapTemplate.setContextSource(adContextSource());
		return ldapTemplate;
	}
}
