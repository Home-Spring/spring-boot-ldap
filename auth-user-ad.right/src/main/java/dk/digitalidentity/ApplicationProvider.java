package dk.digitalidentity;

import java.util.List;

import dk.digitalidentity.app.config.ADLdapConfig;
import dk.digitalidentity.app.service.ADLdapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;

import dk.digitalidentity.app.data.ADLdap;

@SpringBootApplication
public class ApplicationProvider implements CommandLineRunner {

	@Autowired
    private ADLdapService adLdapService;

    public void run(String... args) {
        boolean isAuthenticate = adLdapService.authenticate("", "user2", "Qwerty12");
		System.out.println("(user2) AUTHENTICATE: " + isAuthenticate); //TODO:  authenticate = false     LDAP: error code 49 - comment: AcceptSecurityContext error, data 52e

		isAuthenticate = adLdapService.authenticate("CN=Users2", "user1", "Qwerty1");
		System.out.println("(user1 in CN=Users2) AUTHENTICATE: " + isAuthenticate); //TODO:  authenticate = false     LDAP: error code 32 - problem 2001 (NO_OBJECT)

		isAuthenticate = adLdapService.authenticate(ADLdapConfig.BASE_USERDIR, "user1", "Qwerty1");
		System.out.println("(user1 in " + ADLdapConfig.BASE_USERDIR + ") AUTHENTICATE: " + isAuthenticate); //TODO:  authenticate = true

		System.out.println("|||||||||||||||||||||||||||");
		if (isAuthenticate) {
			test1();

			test2("user2");

			test3();

			test4();

            test5_1("user2");

            test5_2("user2");

			test6("user4");
		}
		System.out.println("|||||||||||||||||||||||||||");

////        String ROOT_DIR = "";                            //TODO:  user1
////        String ROOT_DIR = "DC=adcts";                    //TODO:  user1@adcts
//        String ROOT_DIR = "DC=adcts,DC=local";           //TODO:  user1@adcts.local
////        String ROOT_DIR = "DC=blabla,DC=adcts,DC=local"; //TODO:  user1@blabla.adcts.local
//
//        System.out.println("UserDn = " + getUserDn("user1", ADLdapConfig.ROOT_DIR));
	}

	void test1() {
		System.out.println("1) Search All Users:\n----------------");

		AndFilter andFilter = new AndFilter();
		andFilter.and(new EqualsFilter("objectclass", "person"));

        List<ADLdap> persons = adLdapService.getAll(andFilter);
		for (ADLdap person : persons) System.out.println(person.getCn());
	}

	void test2(String userName) {
		System.out.println("\n2) Search User=user2:\n----------------");

		AndFilter andFilter = new AndFilter();
		andFilter.and(new EqualsFilter("sAMAccountName", userName));

        List<ADLdap> persons = adLdapService.getAll(andFilter);
		for (ADLdap person : persons) System.out.println(person.getCn());
	}

	void test3() {
		System.out.println("\n3) Search All Groups:\n----------------");

		AndFilter andFilter = new AndFilter();
		andFilter.and(new EqualsFilter("objectclass", "group"));

        List<ADLdap> groups = adLdapService.getAll(andFilter);
		for (ADLdap group : groups) System.out.println(group.getCn());
	}

	void test4() {
		System.out.println("\n4) Search Group(s) by User=user1:\n----------------");

		AndFilter andFilter = new AndFilter();
		andFilter.and(new EqualsFilter("member", "CN=user1," + ADLdapConfig.BASE_USERDIR + "," + ADLdapConfig.ROOT_DIR));

        List<ADLdap> groups = adLdapService.getAll(andFilter);
		for (ADLdap group : groups) System.out.println(group.getCn());
	}

	void test5_1(String userName) {
		System.out.println("\n5.1) * Search Group(s) by User=user2 in OU=ctsuser:\n----------------");

		AndFilter andFilter = new AndFilter();
		andFilter.and(new EqualsFilter("member", "CN=" + userName + "," + ADLdapConfig.BASE_USERDIR + "," + ADLdapConfig.ROOT_DIR));

        List<ADLdap> groups = adLdapService.getAll(ADLdapConfig.ROLE_NAMES2, andFilter);
		for (ADLdap group : groups) System.out.println(group.getCn());
	}

	void test5_2(String userName) {
		System.out.println("\n5.2) * Search Group(s) by User=user2 in OU=Ctsprog:\n----------------");

		AndFilter andFilter = new AndFilter();
		andFilter.and(new EqualsFilter("member", "CN=" + userName + "," + ADLdapConfig.BASE_USERDIR + "," + ADLdapConfig.ROOT_DIR));

        List<ADLdap> groups = adLdapService.getAll(ADLdapConfig.ROLE_NAMES1, andFilter);
		for (ADLdap group : groups) System.out.println(group.getCn());
	}

    void test6(String userName) {
        System.out.println("\n6) * Search Group(s) by User=user4 in OU=Ctsprog:\n----------------");

        AndFilter andFilter = new AndFilter();
        andFilter.and(new EqualsFilter("member", "CN=" + userName + "," + ADLdapConfig.BASE_USERDIR + "," + ADLdapConfig.ROOT_DIR));

        List<ADLdap> groups = adLdapService.getAll(ADLdapConfig.ROLE_NAMES1, andFilter);
        for (ADLdap group : groups) System.out.println(group.getCn());
    }

    public static void main(String[] args) {
		SpringApplication.run(ApplicationProvider.class, args);
	}
}
