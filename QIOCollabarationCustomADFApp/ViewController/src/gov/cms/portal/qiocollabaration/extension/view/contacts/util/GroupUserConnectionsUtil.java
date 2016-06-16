package gov.cms.portal.qiocollabaration.extension.view.contacts.util;



import java.util.List;

import oracle.webcenter.peopleconnections.profile.WCUserProfile;

public class GroupUserConnectionsUtil {
    String groupName;
    String selectedUser;
    WCUserProfile userProfile;
    String encodedImageString;
    
    public GroupUserConnectionsUtil(){
        
    }
    
    public GroupUserConnectionsUtil(String groupName, WCUserProfile userProfile){
        this.groupName=groupName;
        this.userProfile=userProfile;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setSelectedUser(String selectedUser) {
        this.selectedUser = selectedUser;
    }

    public String getSelectedUser() {
        return selectedUser;
    }

    public void setUserProfile(WCUserProfile userProfile) {
        this.userProfile = userProfile;
    }

    public WCUserProfile getUserProfile() {
        return userProfile;
    }

    public void setEncodedImageString(String encodedImageString) {
        this.encodedImageString = encodedImageString;
    }

    public String getEncodedImageString() {
        return encodedImageString;
    }
}
