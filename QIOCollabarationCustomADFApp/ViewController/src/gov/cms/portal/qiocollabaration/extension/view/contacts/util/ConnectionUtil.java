package gov.cms.portal.qiocollabaration.extension.view.contacts.util;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import java.util.Map;
import javax.naming.Context;
import javax.naming.NamingException;

import oracle.webcenter.framework.service.BaseWCServiceException;
import oracle.webcenter.peopleconnections.connections.ConnectionsException;
import oracle.webcenter.peopleconnections.connections.ConnectionsServiceFactory;
import oracle.webcenter.peopleconnections.connections.internal.model.ConnectionsManagerImpl;
import oracle.webcenter.peopleconnections.connections.internal.model.InvitationsManagerImpl;
import oracle.webcenter.peopleconnections.profile.ProfileException;
import oracle.webcenter.peopleconnections.profile.ProfileFactory;
import oracle.webcenter.peopleconnections.profile.UserProfileManager;
import oracle.webcenter.peopleconnections.profile.WCUserProfile;

import weblogic.jndi.Environment;
import weblogic.security.providers.authentication.DefaultAuthenticatorMBean;
import weblogic.management.security.authentication.UserReaderMBean;
import weblogic.management.security.authentication.GroupReaderMBean;
import weblogic.management.MBeanHome;
import weblogic.management.security.authentication.*;
import weblogic.management.utils.InvalidCursorException;
import weblogic.management.utils.InvalidParameterException;
import weblogic.management.utils.NotFoundException;
import weblogic.security.providers.authentication.OracleInternetDirectoryAuthenticatorMBean;

public class ConnectionUtil {
    public ConnectionUtil() {
        super();
    }
    public static void main(String[] args) throws InvalidParameterException, InvalidCursorException, NamingException, NotFoundException {
        ConnectionUtil util=new ConnectionUtil();
        util.getAllUserProfilesGroupWise("weblogic");
    }
    
    public void inviteUser(String userName,String invitationMessage){
        ConnectionsServiceFactory connService =ConnectionsServiceFactory.getInstance();
        InvitationsManagerImpl inviteManager=(InvitationsManagerImpl)connService.getInstance().getInvitationsManager();
        try {
            inviteManager.inviteUser(userName, invitationMessage);
        } catch (BaseWCServiceException e) {
            System.out.println("Cannot Invite User");
        }
    }
    
    public List<GroupUserUtil> getAllUserProfilesGroupWise(String loggedInUserName) throws InvalidParameterException,
                                                                                           InvalidCursorException,
                                                                                           NamingException,
                                                                                           NotFoundException {
        List<GroupUserUtil> groupUserMap=new ArrayList<GroupUserUtil>();
        Map<String,List<String>> userNamesGroupWise=new HashMap<String,List<String>>();
        ContactUtil util=new ContactUtil();
        userNamesGroupWise=util.getAllUsersGroupWise(loggedInUserName);
        for (Map.Entry<String, List<String>> entry : userNamesGroupWise.entrySet()){
            System.out.println("Group Name : "+entry.getKey());
            List<String> userList=entry.getValue();
            List<WCUserProfile> userProfileListForAGroup=getListOfAllUserProfiles(userList);
            groupUserMap.add(new GroupUserUtil(entry.getKey(),userProfileListForAGroup));
        }
        return groupUserMap;
    }
    
    public List<WCUserProfile> getListOfAllUserProfiles(List<String> usersInAGroup){
        List<WCUserProfile> userProfileList=new ArrayList<WCUserProfile>();
        try{
            UserProfileManager manager = ProfileFactory.getProfileManager();
            for(String user:usersInAGroup){
                WCUserProfile userProfile=manager.getProfile(user);
                userProfileList.add(userProfile);
            }
            return userProfileList;
        }catch (BaseWCServiceException e) {
            System.out.println("Exception Occurred");
        }
        return userProfileList;
    }
    
//    public Map<String,List<String>> fetchAllUsersGroupWise(String loggedInUserName) throws InvalidParameterException, InvalidCursorException, NamingException, NotFoundException {
//        Map<String,List<String>> groupUserMap=new HashMap<String,List<String>>();
//        GroupReaderMBean groupReaderMBean=(GroupReaderMBean)getAuthenticator();
//        List<String> groupMembershipList=fetchAllGroupsInWeblogic(loggedInUserName);
//        List<String> userList=fetchAllUsersInWeblogic();
//        for(String name:groupMembershipList){
//            List<String> userInParticularGroup=new ArrayList<String>();
//            for(String userName:userList){
//                if(!userName.equalsIgnoreCase(loggedInUserName)){
//                    if(groupReaderMBean.isMember(name, userName,true)){
//                        userInParticularGroup.add(userName);
//                    }
//                }
//            }
//            groupUserMap.put(name, userInParticularGroup);
//        }
//        return groupUserMap;
//    }
//    
//    public List<String> fetchAllUsersInWeblogic() throws InvalidParameterException, InvalidCursorException, NamingException, NotFoundException {
//        List<String> allUserList=new ArrayList<String>();
//        UserReaderMBean userReaderMBean = (UserReaderMBean)getAuthenticator();
//        String userCurName = userReaderMBean.listUsers("*", 100);
//        while (userReaderMBean.haveCurrent(userCurName) ){
//            String user = userReaderMBean.getCurrentName(userCurName);
//            allUserList.add(user);
//            userReaderMBean.advance(userCurName);
//        }
//        return allUserList;
//    }
//    
//    public List<String> fetchAllGroupsInWeblogic(String loggedInUserName) throws InvalidParameterException, InvalidCursorException, NamingException, NotFoundException {
//        List<String> allGroupList=new ArrayList<String>();
//        GroupReaderMBean groupReaderMBean = (GroupReaderMBean)getAuthenticator();
//        String cursorName = groupReaderMBean.listGroups("*", 100);
//        while (groupReaderMBean.haveCurrent(cursorName) )
//        {
//            String group = groupReaderMBean.getCurrentName(cursorName);
//            if(groupReaderMBean.isMember(group, loggedInUserName,true)){
//                allGroupList.add(group);   
//            }
//            groupReaderMBean.advance(cursorName);
//        }
//        return allGroupList;
//    }
//    
//    public OracleInternetDirectoryAuthenticatorMBean getAuthenticator() throws NamingException{
//        OracleInternetDirectoryAuthenticatorMBean oidAuthenticationMBean=null;
//        MBeanHome home = null;
//            Environment env = new Environment();
//            env.setProviderUrl("t3://localhost:7101");
//            env.setSecurityPrincipal("weblogic");
//            env.setSecurityCredentials("welcome1");
//            Context ctx = env.getInitialContext();
//        
//            home = (MBeanHome)ctx.lookup("weblogic.management.adminhome");
//        
//            weblogic.management.security.RealmMBean rmBean = home.getActiveDomain().getSecurityConfiguration().getDefaultRealm();
//        
//            AuthenticationProviderMBean[] authenticationBeans = rmBean.getAuthenticationProviders();
//            oidAuthenticationMBean = (OracleInternetDirectoryAuthenticatorMBean)authenticationBeans[0];
//        
//        return oidAuthenticationMBean;
//    }
}

