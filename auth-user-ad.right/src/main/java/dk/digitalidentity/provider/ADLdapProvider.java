package dk.digitalidentity.provider;

import dk.digitalidentity.app.config.ADLdapConfig;
import dk.digitalidentity.app.data.ADLdap;
import dk.digitalidentity.app.service.ADLdapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ADLdapProvider {

    @Autowired
    private ADLdapService adLdapService;

    public boolean authenticate(String base, String userName, String password) {
        return adLdapService.authenticate(base, userName, password);
    }

    @Deprecated
    public List<ADLdap> allUsers() {
        AndFilter andFilter = new AndFilter();
        andFilter.and(new EqualsFilter("objectclass", "person"));
        return adLdapService.getAll(andFilter);
    }

    @Deprecated
    public List<ADLdap> allGroups() {
        AndFilter andFilter = new AndFilter();
        andFilter.and(new EqualsFilter("objectclass", "group"));
        return adLdapService.getAll(andFilter);
    }

    @Deprecated
    public List<ADLdap> findUser(String userName) {
        AndFilter andFilter = new AndFilter();
        andFilter.and(new EqualsFilter("sAMAccountName", userName));
        return adLdapService.getAll(andFilter);
    }

    public List<ADLdap> findGroups(String userName, String baseUserDir, String roleNames) {
        AndFilter filter = new AndFilter();
        filter.and(new EqualsFilter("member", "CN=" + userName + "," + baseUserDir + "," + ADLdapConfig.ROOT_DIR));
        return adLdapService.getAll(roleNames, filter);
    }

    @Deprecated
    public List<ADLdap> findGroups(String userName, String baseUserDir) {
        AndFilter filter = new AndFilter();
        filter.and(new EqualsFilter("member", "CN=" + userName + "," + baseUserDir + "," + ADLdapConfig.ROOT_DIR));
        return adLdapService.getAll(filter);
    }
}
