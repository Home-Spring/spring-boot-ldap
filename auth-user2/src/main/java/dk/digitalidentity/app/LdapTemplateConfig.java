package dk.digitalidentity.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;

/**
 * @see https://stackoverflow.com/questions/36030518/spring-boot-active-directory-ldap-connection
 *      http://qaru.site/questions/11600059/spring-boot-active-directoryldap-connection
 *      https://www.oipapio.com/question-6285180
 * @see http://qaru.site/questions/384166/ldap-error-code-49-80090308-ldaperr-dsid-0c0903a9-comment-acceptsecuritycontext-error-data-52e-v1db1
 */
@Configuration
public class LdapTemplateConfig {

    public static final String base = "CN=Users,DC=adcts,DC=local";

    private final String url = "ldap://192.168.1.125:389";
    private final Logger log = LoggerFactory.getLogger(LdapTemplateConfig.class);

    @Bean(name = "contextSource")
    // @Scope("singleton")
    public LdapContextSource ldapContextSource() {
        if (isConfigurationValid(url, base)) {
            LdapContextSource ldapContextSource = new LdapContextSource();
            ldapContextSource.setUrl(url);
            ldapContextSource.setBase(base);
            ldapContextSource.setReferral("follow");
            // lcs.setPooled(false);
            // lcs.setDirObjectFactory(DefaultDirObjectFactory.class);
            ldapContextSource.afterPropertiesSet();
            return ldapContextSource;
        }
        return null;
    }

    @Bean(name = "ldapTemplate")
    // @Scope("singleton")
    public LdapTemplate ldapTemplate() {
        LdapTemplate ldapTemplate = new LdapTemplate(ldapContextSource());
        return ldapTemplate;
    }

    public boolean isConfigurationValid(String url, String base) {
        if ((url == null) || url.isEmpty() || (base == null) || base.isEmpty()) {
            log.error("Warning! Your LDAP server is not configured.");
            log.info("Did you configure your LDAP settings in your application.yml?");
            return false;
        } else {
            return true;
        }
    }
}
