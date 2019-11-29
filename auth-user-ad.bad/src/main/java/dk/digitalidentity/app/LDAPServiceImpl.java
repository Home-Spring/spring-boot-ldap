package dk.digitalidentity.app;

import dk.digitalidentity.app.model.User;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.ldap.AuthenticationException;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.stereotype.Service;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static dk.digitalidentity.app.LdapTemplateConfig.USERS_BASE;
import static org.springframework.ldap.query.LdapQueryBuilder.query;

/**
 * @see https://stackoverflow.com/questions/36030518/spring-boot-active-directory-ldap-connection
 *      http://qaru.site/questions/11600059/spring-boot-active-directoryldap-connection
 *      https://www.oipapio.com/question-6285180
 */

@Service
public class LDAPServiceImpl {

    private final Logger logger = LoggerFactory.getLogger(LDAPServiceImpl.class);

    @Autowired
    @Qualifier("usersLdapTemplate")
    LdapTemplate usersLdapTemplate;

    public boolean authenticate(String userName, String password) {
        final String filter = "CN=" + userName;
//        final String userDn = filter + "," + USERS_BASE;

        try {
            LdapContextSource ldapContextSource = (LdapContextSource) usersLdapTemplate.getContextSource();
            ldapContextSource.setUserDn(userName); // ldapContextSource.setUserDn(userDn);
            ldapContextSource.setPassword(password);
            usersLdapTemplate.setContextSource(ldapContextSource);

            return usersLdapTemplate.authenticate("", filter, password);
        } catch (AuthenticationException ae) { }
        return false;
    }

    public User getUserDetails(String userName) {
        List<User> list = usersLdapTemplate.search(query().where("sAMAccountName").is(userName), new UaserAttributesMapper());
        return ((list != null) && !list.isEmpty()) ? list.get(0) : null;
    }

    public List<User> getUsersDetails() {
        AndFilter filter = new AndFilter();
        filter.and(new EqualsFilter("objectclass", "person"));
        List<User> users = usersLdapTemplate.search("", filter.encode(), new UaserAttributesMapper());
        return (!users.isEmpty()) ? users : new ArrayList<>();
    }

    private class UaserAttributesMapper implements AttributesMapper<User> {

        @Override
        public User mapFromAttributes(Attributes attributes) throws NamingException {
            User user;
            if (attributes == null) {
                return null;
            }
            user = new User();

            if (attributes.get("objectclass") != null) {
                user.setObjectclass(attributes.get("objectclass").get().toString());
            }
            if (attributes.get("distinguishedname") != null) {
                user.setDistinguishedname(attributes.get("distinguishedname").get().toString());
            }
            if (attributes.get("userPassword") != null) {
                user.setUserPassword(attributes.get("userPassword").get().toString());
            }
            if (attributes.get("cn") != null) {
                user.setCn(attributes.get("cn").get().toString());
            }
            if (attributes.get("telephoneNumber") != null) {
                user.setTelephoneNumber(attributes.get("telephoneNumber").get().toString());
            }
            if (attributes.get("lastlogoff") != null) {
                String lastlogoff = attributes.get("lastlogoff").get().toString();
                if (13 <= lastlogoff.length()) {
                    long lLastLogoff = Long.valueOf(lastlogoff.substring(0, 13));
                    Date dLastLogoff = new Date(lLastLogoff);
                    user.setLastLogon(new DateTime(dLastLogoff));
                }
            }
            if (attributes.get("userprincipalname") != null) {
                user.setUserprincipalname(attributes.get("userprincipalname").get().toString());
            }
            if (attributes.get("department") != null) {
                user.setDepartment(attributes.get("department").get().toString());
            }
            if (attributes.get("company") != null) {
                user.setCompany(attributes.get("company").get().toString());
            }
            if (attributes.get("mail") != null) {
                user.setMail(attributes.get("mail").get().toString());
            }
            if (attributes.get("streetAddress") != null) {
                user.setStreetAddress(attributes.get("streetAddress").get().toString());
            }
            if (attributes.get("st") != null) {
                user.setSt(attributes.get("st").get().toString());
            }
            if (attributes.get("postalCode") != null) {
                user.setPostalCode(attributes.get("postalCode").get().toString());
            }
            if (attributes.get("l") != null) {
                user.setL(attributes.get("l").get().toString());
            }
            if (attributes.get("description") != null) {
                user.setDescription(attributes.get("description").get().toString());
            }
            if (attributes.get("c") != null) {
                user.setC(attributes.get("c").get().toString());
            }
            if (attributes.get("countryCode") != null) {
                user.setCountryCode(attributes.get("countryCode").get().toString());
            }
            if (attributes.get("cn") != null) {
                user.setCn(attributes.get("cn").get().toString());
            }
            if (attributes.get("sn") != null) {
                user.setSn(attributes.get("sn").get().toString());
            }
            if (attributes.get("employeeID") != null) {
                user.setEmployeeId(attributes.get("employeeID").get().toString());
            }
            if (attributes.get("lastLogon") != null) {
                String lastLogon = attributes.get("lastLogon").get().toString();
                if (13 <= lastLogon.length()) {
                    long lLastLogon = Long.valueOf(lastLogon.substring(0, 13));
                    Date dLastLogon = new Date(lLastLogon);
                    user.setLastLogon(new DateTime(dLastLogon));
                }
            }
            if (attributes.get("memberof") != null) {
                user.setMemberof(attributes.get("memberof").get().toString());
            }
            if (attributes.get("givenname") != null) {
                user.setGivenname(attributes.get("givenname").get().toString());
            }
            if (attributes.get("logoncount") != null) {
                user.setLogoncount(attributes.get("logoncount").get().toString());
            }
            if (attributes.get("displayName") != null) {
                user.setDisplayname(attributes.get("displayName").get().toString());
            }
            return user;
        }
    }
}
