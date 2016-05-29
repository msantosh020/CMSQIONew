package gov.cms.portal.qiocollabaration.extension.view.contacts.util;

import java.util.List;

import oracle.webcenter.peopleconnections.profile.WCUserProfile;

public class GroupUserUtil {
    String groupName;
    String selectedUser;
    List<WCUserProfile> userProfileList;
    
    public GroupUserUtil(String groupName, List<WCUserProfile> userProfileList){
        this.groupName=groupName;
        this.userProfileList=userProfileList;
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

    public void setUserProfileList(List<WCUserProfile> userProfileList) {
        this.userProfileList = userProfileList;
    }

    public List<WCUserProfile> getUserProfileList() {
        return userProfileList;
    }
}
