package dk.digitalidentity.app;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;

@Configuration
public class LdapConfig {

	@Bean
	public LdapContextSource getContextSource() {
		LdapContextSource ldapContextSource = new LdapContextSource();
		ldapContextSource.setUrl("ldap://192.168.1.100:389");
		ldapContextSource.setBase("dc=ninja,dc=cts");
//		ldapContextSource.setUserDn("daniel@example");
//		ldapContextSource.setPassword("Test1234");
		return ldapContextSource;
	}

	@Bean
	public LdapTemplate ldapTemplate() {
		LdapTemplate ldapTemplate = new LdapTemplate(getContextSource());
		ldapTemplate.setIgnorePartialResultException(true);
		ldapTemplate.setContextSource(getContextSource());

		System.out.println("(testuser1) AUTHENTICATE: " + ldapTemplate.authenticate("", "uid=testuser1", "!QAZxcft6")); //TODO:  authenticate = true
		System.out.println("(test_operator) AUTHENTICATE: " + ldapTemplate.authenticate("", "uid=test_operator", "test_operator")); //TODO:  authenticate = true
//		System.out.println("AUTHENTICATE: " + ldapTemplate.authenticate("", "uid=testuser1", "testuser1")); //TODO:  authenticate = false

		return ldapTemplate;
	}
}
