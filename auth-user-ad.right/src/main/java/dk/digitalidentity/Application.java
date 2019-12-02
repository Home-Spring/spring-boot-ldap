package dk.digitalidentity;

import java.util.List;

import dk.digitalidentity.app.config.ADLdapConfig;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.ldap.AuthenticationException;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;

import dk.digitalidentity.app.data.ADLdap;
import dk.digitalidentity.app.dao.ADLdapDao;

@SpringBootApplication
public class Application implements CommandLineRunner {

	@Autowired
	@Qualifier("adLdapTemplate")
	LdapTemplate adLdapTemplate;

    public void run(String... args) {
		ADLdapDao dao = new ADLdapDao();
		dao.setLdapTemplate(adLdapTemplate);

		boolean isAuthenticate = authenticate("", "user2", "Qwerty12");
		System.out.println("(user2) AUTHENTICATE: " + isAuthenticate); //TODO:  authenticate = false

        isAuthenticate = authenticate("", "user1", "Qwerty1");
		System.out.println("(user1) AUTHENTICATE: " + isAuthenticate); //TODO:  authenticate = true

		System.out.println("|||||||||||||||||||||||||||");
		if (isAuthenticate) {
			test1(dao);

			test2(dao);

			test3(dao);

			test4(dao);

            test5_1(dao);

            test5_2(dao);

			test6(dao);
		}
		System.out.println("|||||||||||||||||||||||||||");

////        String ROOT = "";                            //TODO:  user1
////        String ROOT = "DC=adcts";                    //TODO:  user1@adcts
//        String ROOT = "DC=adcts,DC=local";           //TODO:  user1@adcts.local
////        String ROOT = "DC=blabla,DC=adcts,DC=local"; //TODO:  user1@blabla.adcts.local
//
//        System.out.println("UserDn = " + getUserDn("user1", ADLdapConfig.ROOT));
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

    boolean authenticate(String base, String userName, String password) {
        try {
            LdapContextSource ldapContextSource = (LdapContextSource) adLdapTemplate.getContextSource();
            ldapContextSource.setUserDn( getUserDn(userName, ADLdapConfig.ROOT) );
            ldapContextSource.setPassword(password);
            adLdapTemplate.setContextSource(ldapContextSource);
            return adLdapTemplate.authenticate(base, "CN=" + userName, password);
        } catch (AuthenticationException ae) { }
        return false;
    }

	void test1(ADLdapDao dao) {
		System.out.println("Search All Users:\n----------------");

		AndFilter andFilter = new AndFilter();
		andFilter.and(new EqualsFilter("objectclass", "person"));

		List<ADLdap> allPerson = dao.getAllPerson(andFilter);
		for (ADLdap p : allPerson) System.out.println(p.getCn());
	}

	void test2(ADLdapDao dao) {
		System.out.println("\nSearch User=user2:\n----------------");

		AndFilter andFilter = new AndFilter();
		andFilter.and(new EqualsFilter("sAMAccountName", "user2"));

		List<ADLdap> allPerson = dao.getAllPerson(andFilter);
		for (ADLdap p : allPerson) System.out.println(p.getCn());
	}

	void test3(ADLdapDao dao) {
		System.out.println("\nSearch All Groups:\n----------------");

		AndFilter andFilter = new AndFilter();
		andFilter.and(new EqualsFilter("objectclass", "group"));

		List<ADLdap> allPerson = dao.getAllPerson(andFilter);
		for (ADLdap p : allPerson) System.out.println(p.getCn());
	}

	void test4(ADLdapDao dao) {
		System.out.println("\nSearch Group(s) by User=user1:\n----------------");

		AndFilter andFilter = new AndFilter();
		andFilter.and(new EqualsFilter("member", "CN=user1,CN=Users,DC=adcts,DC=local"));

		List<ADLdap> allPerson = dao.getAllPerson(andFilter);
		for (ADLdap p : allPerson) System.out.println(p.getCn());
	}

	void test5_1(ADLdapDao dao) {
		System.out.println("\n* Search Group(s) by User=user2 in OU=ctsuser:\n----------------");

		AndFilter andFilter = new AndFilter();
		andFilter.and(new EqualsFilter("member", "CN=user2,CN=Users,DC=adcts,DC=local"));
		List<ADLdap> allPerson = dao.getAllPerson(ADLdapConfig.ROLE_1, andFilter);

		for (ADLdap p : allPerson) System.out.println(p.getCn());
	}

	void test5_2(ADLdapDao dao) {
		System.out.println("\n* Search Group(s) by User=user2 in OU=Ctsprog:\n----------------");

		AndFilter andFilter = new AndFilter();
		andFilter.and(new EqualsFilter("member", "CN=user2,CN=Users,DC=adcts,DC=local"));
		List<ADLdap> allPerson = dao.getAllPerson(ADLdapConfig.ROLE_2, andFilter);

		for (ADLdap p : allPerson) System.out.println(p.getCn());
	}

    void test6(ADLdapDao dao) {
        System.out.println("\n* Search Group(s) by User=user4 in OU=Ctsprog:\n----------------");

        AndFilter andFilter = new AndFilter();
        andFilter.and(new EqualsFilter("member", "CN=user4,CN=Users,DC=adcts,DC=local"));

        List<ADLdap> allPerson = dao.getAllPerson(ADLdapConfig.ROLE_2, andFilter);
        for (ADLdap p : allPerson) System.out.println(p.getCn());
    }

    public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
