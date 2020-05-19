package dk.digitalidentity.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;

@Configuration
public class ADLdapConfig2 {

    /*
        authenticate.provider.ldap.user-base-cn = OU=Users,OU=People,OU=HO
        authenticate.provider.ldap.user-base-cn = OU=People,OU=OPC
        authenticate.provider.ldap.user-base-cn = OU=People,OU=ZT
     */
    public static final String URL = "ldap://192.168.1.125:389";
    public static final String ROOT_DIR = "DC=adtest,DC=local";
    public static final String BASE_USERDIR = "OU=Users,OU=People,OU=HO";
    public static final String ROLE_NAMES1 = "OU=Ctsprog,OU=People,OU=HO";
    public static final String ROLE_NAMES2 = "OU=ctsuser";

    @Bean
    public LdapContextSource adContextSource() {
        LdapContextSource ldapContextSource = new LdapContextSource();
        ldapContextSource.setUrl(URL);
        ldapContextSource.setBase(ROOT_DIR);
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
