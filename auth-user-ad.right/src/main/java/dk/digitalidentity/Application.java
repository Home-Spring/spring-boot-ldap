package dk.digitalidentity;

import java.util.List;

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

import dk.digitalidentity.app.data.LdapPerson;
import dk.digitalidentity.app.dao.PersonRepo;

@SpringBootApplication
public class Application implements CommandLineRunner {

	@Autowired
	@Qualifier("adLdapTemplate")
	LdapTemplate adLdapTemplate;

	public void run(String... args) {
		PersonRepo dao = new PersonRepo();
		dao.setLdapTemplate(adLdapTemplate);

		boolean isAuthenticate = authenticate("", "CN=user2", "Qwerty12");
		System.out.println("(user2) AUTHENTICATE: " + isAuthenticate); //TODO:  authenticate = false

		isAuthenticate = authenticate("", "CN=user2", "Qwerty1");
		System.out.println("(user2) AUTHENTICATE: " + isAuthenticate); //TODO:  authenticate = true

		System.out.println("|||||||||||||||||||||||||||");
		if (isAuthenticate) {
			test1(dao);

			test2(dao);

			test3(dao);

			test4(dao);

			test5(dao);

			test6_1(dao);
			test6_2(dao);
		}
		System.out.println("|||||||||||||||||||||||||||");
	}

	boolean authenticate(String base, String filter, String password) {
		try {
			LdapContextSource ldapContextSource = (LdapContextSource) adLdapTemplate.getContextSource();
			ldapContextSource.setUserDn("user1@adcts.local");
			ldapContextSource.setPassword(password);
			adLdapTemplate.setContextSource(ldapContextSource);
			return adLdapTemplate.authenticate(base, filter, password);
		} catch (AuthenticationException ae) { }
		return false;
	}

	void test1(PersonRepo dao) {
		System.out.println("Search All Users:\n----------------");

		AndFilter andFilter = new AndFilter();
		andFilter.and(new EqualsFilter("objectclass", "person"));

		List<LdapPerson> allPerson = dao.getAllPerson(andFilter);
		for (LdapPerson p : allPerson) System.out.println(p.getCn());
	}

	void test2(PersonRepo dao) {
		System.out.println("\nSearch User=user2:\n----------------");

		AndFilter andFilter = new AndFilter();
		andFilter.and(new EqualsFilter("sAMAccountName", "user2"));

		List<LdapPerson> allPerson = dao.getAllPerson(andFilter);
		for (LdapPerson p : allPerson) System.out.println(p.getCn());
	}

	void test3(PersonRepo dao) {
		System.out.println("\nSearch All Groups:\n----------------");

		AndFilter andFilter = new AndFilter();
		andFilter.and(new EqualsFilter("objectclass", "group"));

		List<LdapPerson> allPerson = dao.getAllPerson(andFilter);
		for (LdapPerson p : allPerson) System.out.println(p.getCn());
	}

	void test4(PersonRepo dao) {
		System.out.println("\nSearch Group(s) by User=user1:\n----------------");

		AndFilter andFilter = new AndFilter();
		andFilter.and(new EqualsFilter("member", "CN=user1,CN=Users,DC=adcts,DC=local"));

		List<LdapPerson> allPerson = dao.getAllPerson(andFilter);
		for (LdapPerson p : allPerson) System.out.println(p.getCn());
	}

	void test5(PersonRepo dao) {
		System.out.println("\nSearch Group(s) by User=user4:\n----------------");

		AndFilter andFilter = new AndFilter();
		andFilter.and(new EqualsFilter("member", "CN=user4,CN=Users,DC=adcts,DC=local"));

		List<LdapPerson> allPerson = dao.getAllPerson(andFilter);
		for (LdapPerson p : allPerson) System.out.println(p.getCn());
	}

	void test6_1(PersonRepo dao) {
		System.out.println("\nSearch Group(s) by User=user2 in OU=ctsuser:\n----------------");

		AndFilter andFilter = new AndFilter();
		andFilter.and(new EqualsFilter("member", "CN=user2,CN=Users,DC=adcts,DC=local"));
		List<LdapPerson> allPerson = dao.getAllPerson("OU=ctsuser", andFilter);

		for (LdapPerson p : allPerson) System.out.println(p.getCn());
	}

	void test6_2(PersonRepo dao) {
		System.out.println("\nSearch Group(s) by User=user2 in OU=Ctsprog:\n----------------");

		AndFilter andFilter = new AndFilter();
		andFilter.and(new EqualsFilter("member", "CN=user2,CN=Users,DC=adcts,DC=local"));
		List<LdapPerson> allPerson = dao.getAllPerson("OU=Ctsprog", andFilter);

		for (LdapPerson p : allPerson) System.out.println(p.getCn());
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
