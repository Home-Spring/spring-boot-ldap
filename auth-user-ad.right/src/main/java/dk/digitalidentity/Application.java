package dk.digitalidentity;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;

import dk.digitalidentity.app.LdapPerson;
import dk.digitalidentity.app.PersonRepo;

@SpringBootApplication
public class Application implements CommandLineRunner {

	@Autowired
	LdapTemplate ldapTemplate;

	public void run(String... args) {
		PersonRepo dao = new PersonRepo();
		dao.setLdapTemplate(ldapTemplate);

		System.out.println("|||||||||||||||||||||||||||");

		test1(dao);

		test2(dao);
		System.out.println();

		test3(dao);
		System.out.println();

		test4(dao);
		System.out.println();

		test5(dao);

		System.out.println("|||||||||||||||||||||||||||");
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

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
