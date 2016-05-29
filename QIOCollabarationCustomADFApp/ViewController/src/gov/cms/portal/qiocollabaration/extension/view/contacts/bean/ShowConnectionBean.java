package gov.cms.portal.qiocollabaration.extension.view.contacts.bean;

import gov.cms.portal.qiocollabaration.extension.view.contacts.util.ConnectionUtil;

import gov.cms.portal.qiocollabaration.extension.view.contacts.util.GroupUserUtil;

import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import oracle.adf.share.ADFContext;
import oracle.adf.view.rich.event.DialogEvent;
import weblogic.management.utils.InvalidCursorException;
import weblogic.management.utils.InvalidParameterException;
import weblogic.management.utils.NotFoundException;


public class ShowConnectionBean {
    private String invitationMessage;
    private String selectedUser;
    public ShowConnectionBean() {
    }
    
    private List<GroupUserUtil> contactList = new ArrayList<GroupUserUtil>();
    

    public void fetchCompleteConnectionList() {
        // Add event code here...
        System.out.println("Action listener called");
        String userName=ADFContext.getCurrent().getSecurityContext().getUserName();
        ConnectionUtil connectionUtil=new ConnectionUtil();
        try {
            contactList=connectionUtil.getAllUserProfilesGroupWise(userName);
        } catch (InvalidParameterException e) {
            System.out.println("Invalid Parameter found");
            e.printStackTrace();
        } catch (InvalidCursorException e) {
            System.out.println("Invalid Cursor");
            e.printStackTrace();
        } catch (NamingException e) {
            System.out.println("Invalid Name");
            e.printStackTrace();
        } catch (NotFoundException e) {
            System.out.println("User not found");
            e.printStackTrace();
        }
    }

    public void setContactList(List<GroupUserUtil> contactList) {
        this.contactList = contactList;
    }

    public List<GroupUserUtil> getContactList() {
        return contactList;
    }

    public void setInvitationMessage(String invitationMessage) {
        this.invitationMessage = invitationMessage;
    }

    public String getInvitationMessage() {
        return invitationMessage;
    }

    public void inviteUserDialogListener(DialogEvent dialogEvent) {
        ConnectionUtil connUtil=new ConnectionUtil();
        connUtil.inviteUser(selectedUser, invitationMessage);
        // Add event code here...
    }

    public void setSelectedUser(String selectedUser) {
        this.selectedUser = selectedUser;
    }

    public String getSelectedUser() {
        return selectedUser;
    }
}
