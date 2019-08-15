package dk.digitalidentity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import dk.digitalidentity.app.LdapGroup;
import dk.digitalidentity.app.LdapGroupRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;

import dk.digitalidentity.app.LdapPerson;
import dk.digitalidentity.app.LdapPersonRepo;

@SpringBootApplication
public class Application implements CommandLineRunner {

	@Autowired
	LdapTemplate ldapTemplate;

	public void run(String... args) {
		// /////////////////////////////////////////
		/**
		 * AUTHENTICATE
		 */
		LdapPersonRepo personDao = new LdapPersonRepo();
		personDao.setLdapTemplate(ldapTemplate);

		/**
		 * get all (ldap) Persons
		 */
		AndFilter andFilter = new AndFilter();
		andFilter.and(new EqualsFilter("objectClass", "person"));
//		andFilter.and(new EqualsFilter("sAMAccountName", "daniel"));
//		andFilter.and(new EqualsFilter("memberof", "CN=TestGroup,DC=example,DC=org"));

//		try {
			for (LdapPerson person : personDao.getAllPerson(andFilter)) System.out.println(person);
//		} catch (Exception e) {
//			System.err.println(e);
//		}

		// /////////////////////////////////////////
		LdapGroupRepo groupDao = new LdapGroupRepo();
		groupDao.setLdapTemplate(ldapTemplate);


		/**
		 * get all (ldap) Groups
		 */
		andFilter = new AndFilter();
//		andFilter.and(new EqualsFilter("objectClass", "person"));
		andFilter.and(new EqualsFilter("objectClass", "posixGroup"));

//		try {
			for (LdapGroup role : groupDao.getAllGroup(andFilter)) System.out.println(role);
//		} catch (Exception e) {
//			System.err.println(e);
//		}

		// /////////////////////////////////////////
		String username = "vladare";
//		String username = "testuser1";
//		String username = "testuser";
		StringBuilder sb = new StringBuilder("username: " + username + ";");
		for (LdapGroup role : groupDao.getAllGroup(andFilter)) if (role.getMemberUids().contains(username)) sb.append(" group: " + role.getCn() + ";");
		System.out.println(sb);

		System.out.println("username: " + "vladare" + "; groups: " + getGroupsByUsername(groupDao, "vladare").toString() + ";");
		System.out.println("username: " + "testuser1" + "; groups: " + getGroupsByUsername(groupDao, "testuser1").toString() + ";");
		System.out.println("username: " + "testuser" + "; groups: " + getGroupsByUsername(groupDao, "testuser").toString() + ";");
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	static List<String> getGroupsByUsername(LdapGroupRepo groupDao, String username) {
		AndFilter andFilter = new AndFilter();
		andFilter.and(new EqualsFilter("objectClass", "posixGroup"));

		List<String> groups = new ArrayList<>();
		for (LdapGroup role : groupDao.getAllGroup(andFilter)) if (role.getMemberUids().contains(username)) groups.add(role.getCn());
		return groups;
	}
}
