package gov.cms.portal.qiocollabaration.extension.view.contacts.util;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;


public class ContactUtil {
    public static final String JNDI_FACTORY = "weblogic.jndi.WLInitialContextFactory";
    public static final String MBEAN_SERVER = "weblogic.management.mbeanservers.domainruntime";
    public static final String JNDI_ROOT = "/jndi/";
    public static final String DEFAULT_PROTOCOL = "t3";
    public static final String PROTOCOL_PROVIDER_PACKAGES = "weblogic.management.remote";
    //This how we get our DomainRuntimeService, this is where DomainConfigurationMBeans exists
    public static final String DOMAIN_MBEAN_NAME = "com.bea:Name=DomainRuntimeService,Type=weblogic.management.mbeanservers.domainruntime.DomainRuntimeServiceMBean";
    private static MBeanServerConnection connection;
    private static ObjectName oidAuthenticator;
    private static ObjectName[] authenticationProviders;
    private static String authenticatorName="OIDAuthenticator";
    public static void main(String[] args) throws Exception {
        ContactUtil util = new ContactUtil();
        util.getAllUsersGroupWise("weblogic");
    }
    
    public Map<String,List<String>> getAllUsersGroupWise(String loggedInUserName){
        getAuthenticator();
        List<String> groups=getUserGroups(loggedInUserName);
        Map<String,List<String>> userGroupMap=new HashMap<String,List<String>>();
        for(String groupName:groups){
            List<String> usersInGroup=new ArrayList<String>();
            System.out.println(groupName);
            usersInGroup=getGroupMembers(groupName,loggedInUserName);
            System.out.println(groupName+usersInGroup.size());
            for(String userName:usersInGroup){
                System.out.println(userName);
            }
            userGroupMap.put(groupName, usersInGroup);
        }
        return userGroupMap;
    }
    
    public static List<String> getGroupMembers(String groupName,String currentUser) throws RuntimeException {
            try {
                List<String> allGroupMembers=new ArrayList<String>();
                String cursor =
                                (String)connection.invoke(oidAuthenticator, "listGroupMembers",
                                                          new Object[] { groupName, "*", new java.lang.Integer(0) },
                                                          new String [] { "java.lang.String", "java.lang.String", "java.lang.Integer" });

                            boolean haveCurrent =
                                ((Boolean)connection.invoke(oidAuthenticator,
                                                            "haveCurrent",
                                                            new Object[] { cursor },
                                                            new String[] { "java.lang.String" })).booleanValue();

                            while (haveCurrent) {
                                String currentName =
                                    (String)connection.invoke(oidAuthenticator,
                                                              "getCurrentName",
                                                              new Object[] { cursor },
                                                              new String[] { "java.lang.String" });

                                allGroupMembers.add(currentName);

                                connection.invoke(oidAuthenticator, "advance",
                                                  new Object[] { cursor },
                                                  new String[] { "java.lang.String" });

                                haveCurrent =
                                        ((Boolean)connection.invoke(oidAuthenticator, "haveCurrent",
                                                                    new Object[] { cursor },
                                                                    new String[] { "java.lang.String" })).booleanValue();
                            }

                            return allGroupMembers;
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
    
    public static List<String> getUserGroups(String username) throws RuntimeException {
            try {
                List<String> allUserGroups = new ArrayList<String>();
     
                String cursor =
                    (String)connection.invoke(oidAuthenticator, "listMemberGroups",
                                              new Object[] { username },
                                              new String[] { "java.lang.String"});
     
                boolean haveCurrent =
                    ((Boolean)connection.invoke(oidAuthenticator,
                                                "haveCurrent",
                                                new Object[] { cursor },
                                                new String[] { "java.lang.String" })).booleanValue();
     
                while (haveCurrent) {
                    String currentName =
                        (String)connection.invoke(oidAuthenticator,
                                                  "getCurrentName",
                                                  new Object[] { cursor },
                                                  new String[] { "java.lang.String" });
     
                    allUserGroups.add(currentName);
     
                    connection.invoke(oidAuthenticator, "advance",
                                      new Object[] { cursor },
                                      new String[] { "java.lang.String" });
     
                    haveCurrent =
                            ((Boolean)connection.invoke(oidAuthenticator, "haveCurrent",
                                                        new Object[] { cursor },
                                                        new String[] { "java.lang.String" })).booleanValue();
                }
     
                return allUserGroups;
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
    
    public ObjectName getAuthenticator(){
        try {
                    String host = "10.163.64.1";
                    String port = "7001";
                    String username = "weblogic";
                    String password = "password123";
                    Hashtable h = new Hashtable();
                    JMXServiceURL serviceURL;

                    serviceURL =
                            new JMXServiceURL(DEFAULT_PROTOCOL, host, Integer.valueOf(port).intValue(),
                                              "/jndi/weblogic.management.mbeanservers.domainruntime");

                    h.put("java.naming.security.principal", username);
                    h.put("java.naming.security.credentials", password);
                    h.put("jmx.remote.protocol.provider.pkgs",
                          "weblogic.management.remote");

                    //Creating a JMXConnector to connect to JMX
                    JMXConnector connector =
                        JMXConnectorFactory.connect(serviceURL, h);

                    connection = connector.getMBeanServerConnection();

                    /****
                      We Get Objects by creating ObjectName with it's Qualified name.
                      The constructor take a String of the full Qualified name of the MBean
                      We then use connection to get Attribute out of this ObjectName but specifying a String of
                      this Attribute
                      *****/

                    ObjectName configurationMBeans=
                        new ObjectName(DOMAIN_MBEAN_NAME);
                    ObjectName domain =
                        (ObjectName)connection.getAttribute(configurationMBeans, "DomainConfiguration");

                    ObjectName security =
                        (ObjectName)connection.getAttribute(domain, "SecurityConfiguration");

                    ObjectName realm =
                        (ObjectName)connection.getAttribute(security, "DefaultRealm");

                    authenticationProviders =
                            (ObjectName[])connection.getAttribute(realm,
                                                                  "AuthenticationProviders");

                    for (int i = 0; i < authenticationProviders.length; i++) {
                        String name =
                            (String)connection.getAttribute(authenticationProviders[i],
                                                            "Name");
                        System.out.println("Authenticator name : "+name);
                        if (name.equals(authenticatorName)){
                            oidAuthenticator = authenticationProviders[i];
                            return oidAuthenticator;   
                        }
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
        return null;
    }
}
