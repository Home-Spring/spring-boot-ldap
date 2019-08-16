package dk.digitalidentity;

import java.util.ArrayList;
import java.util.List;

import dk.digitalidentity.app.LdapGroup;
import dk.digitalidentity.app.LdapGroupRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;

import dk.digitalidentity.app.LdapPerson;
import dk.digitalidentity.app.LdapPersonRepo;
import org.springframework.ldap.query.LdapQuery;
import org.springframework.ldap.query.SearchScope;
import org.springframework.ldap.support.LdapUtils;

import static org.springframework.ldap.query.LdapQueryBuilder.query;

@SpringBootApplication
public class Application implements CommandLineRunner {

	@Autowired
	LdapContextSource ldapContext2;

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

		System.out.println();
//		try {
			for (LdapPerson person : personDao.getAllPerson(andFilter)) System.out.println(person);
//		} catch (Exception e) {
//			System.err.println(e);
//		}
		System.out.println();

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
		System.out.println();

		// /////////////////////////////////////////
		String username = "vladare";
//		String username = "testuser1";
//		String username = "testuser";
		StringBuilder sb = new StringBuilder("username: " + username + ";");
		for (LdapGroup role : groupDao.getAllGroup(andFilter)) if (role.getMemberUids().contains(username)) sb.append(" group: " + role.getCn() + ";");
		System.out.println(sb);
		System.out.println();

		System.out.println("username: " + "vladare" + "; groups: " + getGroupsByUsername(groupDao, "vladare").toString() + ";");
		System.out.println("username: " + "testuser1" + "; groups: " + getGroupsByUsername(groupDao, "testuser1").toString() + ";");
		System.out.println("username: " + "testuser" + "; groups: " + getGroupsByUsername(groupDao, "testuser").toString() + ";");
		System.out.println();

		// /////////////////////////////////////////
//		ldapContext2.setUrl("ldap://192.168.1.100:389");
//		ldapContext2.setBase("cn=lotus,ou=groups,dc=ninja,dc=cts");
////		ldapContext2.setBase("cn=lotus");
//		ldapTemplate.setContextSource(ldapContext2);
//		groupDao.setLdapTemplate(ldapTemplate);

		LdapQuery query = query()
				.searchScope(SearchScope.SUBTREE)
//				.countLimit(10)
//				.attributes("cn")
//				.base(LdapUtils.emptyLdapName())
				.where("objectclass").is("posixGroup");
//				.and("sn").not().is(lastName)
//				.and("sn").like("j*hn")
//				.and("uid").isPresent();

		for (LdapGroup role : groupDao.getAllGroup(query)) System.out.println(role);
		System.out.println();
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
