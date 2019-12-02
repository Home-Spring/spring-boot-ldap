package dk.digitalidentity.app.service;

import dk.digitalidentity.app.config.ADLdapConfig;
import dk.digitalidentity.app.dao.ADLdapDao;
import dk.digitalidentity.app.data.ADLdap;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.ldap.AuthenticationException;
import org.springframework.ldap.NameNotFoundException;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.ldap.filter.Filter;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class ADLdapService {

    @Autowired
    @Qualifier("adLdapTemplate")
    private LdapTemplate adLdapTemplate;

    private ADLdapDao dao;

    @PostConstruct
    public void init() {
        dao = new ADLdapDao();
        dao.setLdapTemplate(adLdapTemplate);
    }

    public boolean authenticate(String base, String userName, String password) {
        try {
            LdapContextSource ldapContextSource = (LdapContextSource) adLdapTemplate.getContextSource();
            ldapContextSource.setUserDn( getUserDn(userName,ADLdapConfig.ROOT_DIR) );
            ldapContextSource.setPassword(password);
            adLdapTemplate.setContextSource(ldapContextSource);
            return adLdapTemplate.authenticate(base, "CN=" + userName, password);
        } catch (AuthenticationException ae) {
            System.err.println(ae.getLocalizedMessage());
        } catch (NameNotFoundException nnfe) {
            System.err.println(nnfe.getLocalizedMessage());
        }
        return false;
    }

    public List<ADLdap> getAll(String base, Filter filter) {
        return dao.getAll(base, filter);
    }

    @Deprecated
    public List<ADLdap> getAll(Filter filter) {
        return dao.getAll(filter);
    }

    private String getUserDn(final String userName, final String root) {
        StringBuilder userDn = new StringBuilder(userName);
        if (StringUtils.isNotBlank(root)) {
            userDn.append("@");
            String[] rootKeyVals = root.replace(" ", "").split(",");
            for (int i = 0; i < rootKeyVals.length; i++) {
                String[] keyVal = rootKeyVals[i].split("=", 2);
                if (StringUtils.isNotBlank(keyVal[1])) userDn.append(((i+1) == rootKeyVals.length) ? keyVal[1] : keyVal[1] + ".");
            }
        }
        return userDn.toString();
    }
}
