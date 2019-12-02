package dk.digitalidentity;

import dk.digitalidentity.app.config.ADLdapConfig;
import dk.digitalidentity.provider.ADLdapProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import dk.digitalidentity.app.data.ADLdap;

@SpringBootApplication
public class Application implements CommandLineRunner {

	@Autowired
	private ADLdapProvider adLdapProvider;

    public void run(String... args) {
        boolean isAuthenticate = adLdapProvider.authenticate("", "user2", "Qwerty12");
		System.out.println("(user2) AUTHENTICATE: " + isAuthenticate); //TODO:  authenticate = false     LDAP: error code 49 - comment: AcceptSecurityContext error, data 52e

		isAuthenticate = adLdapProvider.authenticate("CN=Users2", "user1", "Qwerty1");
		System.out.println("(user1 in CN=Users2) AUTHENTICATE: " + isAuthenticate); //TODO:  authenticate = false     LDAP: error code 32 - problem 2001 (NO_OBJECT)

		isAuthenticate = adLdapProvider.authenticate(ADLdapConfig.BASE_USERDIR, "user1", "Qwerty1");
		System.out.println("(user1 in " + ADLdapConfig.BASE_USERDIR + ") AUTHENTICATE: " + isAuthenticate); //TODO:  authenticate = true

		System.out.println("|||||||||||||||||||||||||||");
		if (isAuthenticate) {
			System.out.println("1) Search All Users:\n----------------");
			for (ADLdap group : adLdapProvider.allUsers()) System.out.println(group.getCn());

			System.out.println("\n3) Search All Groups:\n----------------");
			for (ADLdap group : adLdapProvider.allGroups()) System.out.println(group.getCn());

			System.out.println("\n2) Search User by Name=user2:\n----------------");
			for (ADLdap person : adLdapProvider.findUser("user2")) System.out.println(person.getCn());

			System.out.println("\n4) Search Group(s) by UserName=user1:\n----------------");
			for (ADLdap group : adLdapProvider.findGroups("user1", ADLdapConfig.BASE_USERDIR)) System.out.println(group.getCn());

			System.out.println("\n5.1) * Search Group(s) by UserName=user2 in OU=ctsuser:\n----------------");
			for (ADLdap group : adLdapProvider.findGroups("user2", ADLdapConfig.BASE_USERDIR, ADLdapConfig.ROLE_NAMES2)) System.out.println(group.getCn());

			System.out.println("\n5.2) * Search Group(s) by UserName=user2 in OU=Ctsprog:\n----------------");
			for (ADLdap group : adLdapProvider.findGroups("user2", ADLdapConfig.BASE_USERDIR, ADLdapConfig.ROLE_NAMES1)) System.out.println(group.getCn());

			System.out.println("\n6) * Search Group(s) by UserName=user4 in OU=Ctsprog:\n----------------");
			for (ADLdap group : adLdapProvider.findGroups("user4", ADLdapConfig.BASE_USERDIR, ADLdapConfig.ROLE_NAMES1)) System.out.println(group.getCn());
		}
		System.out.println("|||||||||||||||||||||||||||");

////        String ROOT_DIR = "";                            //TODO:  user1
////        String ROOT_DIR = "DC=adcts";                    //TODO:  user1@adcts
//        String ROOT_DIR = "DC=adcts,DC=local";           //TODO:  user1@adcts.local
////        String ROOT_DIR = "DC=blabla,DC=adcts,DC=local"; //TODO:  user1@blabla.adcts.local
//
//        System.out.println("UserDn = " + getUserDn("user1", ADLdapConfig.ROOT_DIR));
	}

    public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
