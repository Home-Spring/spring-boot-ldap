//package dk.digitalidentity.app;
//
//import org.activiti.engine.ActivitiException;
//import org.activiti.engine.identity.User;
//import org.activiti.engine.impl.Page;
//import org.activiti.engine.impl.UserQueryImpl;
//import org.activiti.engine.impl.persistence.entity.UserEntity;
//import org.activiti.engine.impl.persistence.entity.UserManager;
//import org.apache.directory.ldap.client.api.LdapConnection;
//import org.apache.directory.ldap.client.api.exception.LdapException;
//import org.apache.directory.ldap.client.api.message.BindResponse;
//import org.apache.directory.ldap.client.api.message.SearchResponse;
//import org.apache.directory.ldap.client.api.message.SearchResultEntry;
//import org.apache.directory.shared.ldap.cursor.Cursor;
//import org.apache.directory.shared.ldap.entry.EntryAttribute;
//import org.apache.directory.shared.ldap.filter.SearchScope;
//import org.apache.directory.shared.ldap.message.ResultCodeEnum;
//import org.apache.mina.core.session.IoSession;
//import org.slf4j.LoggerFactory;
//import org.slf4j.Logger;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//public class LDAPUserManager extends UserManager {
//    private final static Logger logger = LoggerFactory.getLogger(LDAPUserManager.class);
//
//    private LDAPConnectionParams ldapConnectionParams;
//
//    public LDAPUserManager(LDAPConnectionParams ldapConnectionParams) {
//        this.ldapConnectionParams = ldapConnectionParams;
//    }
//
//    public Boolean checkPassword(String userId, String password) {
//        Boolean result;
//        LdapConnection connection;
//
//        String userDN = ldapConnectionParams.getUserPrefix() + "=" +
//                userId + "," + ldapConnectionParams.getUserGroup();
//        logger.debug("Checking password, using connection string: '" + userDN + "'");
//        try {
//            connection = openConnection();
//            BindResponse bindResponse = connection.bind(userDN, password);
//            result = bindResponse.getLdapResult().getResultCode() == ResultCodeEnum.SUCCESS;
//        } catch (LdapException e) {
//            throw new ActivitiException("LDAP exception while binding", e);
//        } catch (IOException e) {
//            throw new ActivitiException("IO exception while binding", e);
//        }
//        // TODO: move this into a finally clause above
//        closeConnection(connection);
//
//        return result;
//    }
//
//    public List<User> findUserByQueryCriteria(Object o, Page page) {
//        List<User> result = new ArrayList<User>();
//
//        UserQueryImpl userQuery = (UserQueryImpl)o;
//        StringBuilder queryString = new StringBuilder();
//        queryString.append("(").append(ldapConnectionParams.getUserPrefix()).append("=")
//                .append(userQuery.getId()).append(")");
//
//        logger.debug("Looking for users: '" + queryString + "'");
//        LdapConnection connection;
//
//        try {
//            connection = openConnection();
//            Cursor<SearchResponse> responseCursor = connection.search(
//                    ldapConnectionParams.getUserGroup(), queryString.toString(),
//                    SearchScope.ONELEVEL,
//                    "cn", "sAMAccountName", "sn");
//
//            logger.debug("Got cursor: " + responseCursor);
//
//            for (SearchResponse response : responseCursor) {
//                logger.debug("It's a rsponse: " + response);
//            }
//
//            int maxUsers = 10;
//            while (responseCursor.next() && maxUsers-- > 0) {
//                User user = new UserEntity();
//                SearchResultEntry searchResponse = (SearchResultEntry)responseCursor.get();
//                logger.debug("Got item: " + searchResponse);
//                result.add(user);
//            }
//            responseCursor.close();
//        } catch (LdapException e) {
//            throw new ActivitiException("While searching for user in LDAP", e);
//        } catch (Exception e) {
//            throw new ActivitiException("While searching for user in LDAP", e);
//        }
//        // TODO: move this into a finally clause above
//        closeConnection(connection);
//        logger.debug("Returning users: " + result);
//        return result;
//    }
//
//    private void closeConnection(LdapConnection connection) {
//        try {
//            connection.unBind();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        try {
//            connection.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private LdapConnection openConnection() throws LdapException, IOException {
//        LdapConnection connection = new LdapConnection(
//                ldapConnectionParams.getLdapServer(),
//                ldapConnectionParams.getLdapPort()) {
//
//            public void exceptionCaught(IoSession ioSession, Throwable throwable) throws Exception {
//                logger.error("Exception thrown in " + ioSession, throwable);
//            }
//        };
//        connection.connect();
//        return connection;
//    }
//
//}
