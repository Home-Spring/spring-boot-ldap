package dk.digitalidentity.app;

import dk.digitalidentity.app.model.User;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.stereotype.Service;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import java.util.Date;
import java.util.List;

@Service
public class LDAPServiceImpl {

    private final Logger logger = LoggerFactory.getLogger(LDAPServiceImpl.class);

    @Autowired
    LdapTemplate ldapTemplate;

    public User getUserDetails(String userName) {
        AndFilter filter = new AndFilter();
        filter.and(new EqualsFilter("objectclass", "person"));
        List<User> users = ldapTemplate.search("", filter.encode(), new UaserAttributesMapper());
        if (!users.isEmpty()) {
            return users.get(0);
        }
        return null;

        // List<User> list =
        // ldapTemplate.search(query().where("sAMAccountName").is("a.keskempes"),
        // new UserAttributesMapper());
        // if ((list != null) && !list.isEmpty()) {
        // return list.get(0);
        // }
        // return null;

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
