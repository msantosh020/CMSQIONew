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

import util.ContactUtil;
import util.GroupUserUtil;

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

public class WCConnectionUtil {
    public WCConnectionUtil() {
        super();
    }
    public static void main(String[] args) {
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
    
    public List<GroupUserConnectionsUtil> getAllUserProfilesGroupWise(String loggedInUserName) throws InvalidParameterException,
                                                                                           InvalidCursorException,
                                                                                           NamingException,
                                                                                           NotFoundException {
        List<GroupUserConnectionsUtil> groupUserMap=new ArrayList<GroupUserConnectionsUtil>();
        Map<String,String> userNamesGroupWise=new HashMap<String,String>();
        ContactConnectionsUtil util=new ContactConnectionsUtil();
        userNamesGroupWise=util.getAllUsersGroupWise(loggedInUserName);
        for (Map.Entry<String, String> entry : userNamesGroupWise.entrySet()){
            String userName=entry.getKey();
            String groupName=entry.getValue();
            WCUserProfile userProfile=getUserProfile(userName);
            groupUserMap.add(new GroupUserConnectionsUtil(groupName,userProfile));
        }
        return groupUserMap;
    }
    
    public WCUserProfile getUserProfile(String userInAGroup){
        try{
            UserProfileManager manager = ProfileFactory.getProfileManager();
                WCUserProfile userProfile=manager.getProfile(userInAGroup);
            return userProfile;
        }catch (BaseWCServiceException e) {
            System.out.println("Exception Occurred");
        }
        return null;
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

