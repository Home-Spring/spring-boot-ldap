package dk.digitalidentity;

import java.util.List;

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

		try {
			List<LdapPerson> persons = personDao.getAllPerson(andFilter);
			for (LdapPerson person : persons) {
				System.out.println(person);
			}
		} catch (Exception e) {
			System.err.println(e);
		}

		// /////////////////////////////////////////
		LdapGroupRepo groupDao = new LdapGroupRepo();
		groupDao.setLdapTemplate(ldapTemplate);


		/**
		 * get all (ldap) Groups
		 */
		andFilter = new AndFilter();
		andFilter.and(new EqualsFilter("objectClass", "person"));

		try {
			List<LdapGroup> groups = groupDao.getAllGroup(andFilter);
			for (LdapGroup group : groups) {
				System.out.println(group);
			}
		} catch (Exception e) {
			System.err.println(e);
		}
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
