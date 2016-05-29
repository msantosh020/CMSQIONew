package gov.cms.portal.qiocollabaration.extension.view.contacts.bean;

import gov.cms.portal.qiocollabaration.extension.view.contacts.util.UserProfile;

import java.util.HashMap;
import java.util.List;

import java.util.Map;

import javax.faces.event.ActionEvent;

import javax.naming.NamingException;

import oracle.adf.share.ADFContext;

import oracle.adf.view.rich.component.rich.layout.RichPanelBox;

import oracle.adf.view.rich.context.AdfFacesContext;

import util.ContactUtil;


import weblogic.management.security.authentication.UserAttributeReaderMBean;
import weblogic.management.utils.InvalidCursorException;
import weblogic.management.utils.InvalidParameterException;
import weblogic.management.utils.NotFoundException;

public class AllTestPageBean {

    public AllTestPageBean() {
    }
    
    private Map<String, UserProfile> contactList = new HashMap<String, UserProfile>();

    public void fetchGroupActionListener(ActionEvent actionEvent) {
        // Add event code here...
        System.out.println("Action listener called");
        String userName=ADFContext.getCurrent().getSecurityContext().getUserName();
        ContactUtil contactUtil=new ContactUtil();
        Map<String,List<Map<String,UserAttributeReaderMBean>>> userGroupMap;
        try {
            userGroupMap = contactUtil.fetchAllUsersGroupWise(userName);
            for (Map.Entry<String, List<Map<String,UserAttributeReaderMBean>>> entry : userGroupMap.entrySet()){
                System.out.println("Group Name : "+entry.getKey());
                List<Map<String,UserAttributeReaderMBean>> userList=entry.getValue();
                for(Map<String,UserAttributeReaderMBean> userObjMap:userList){
                    for(Map.Entry<String,UserAttributeReaderMBean> userEntry:userObjMap.entrySet()){
                        System.out.println("\n"+"  User Name : "+userEntry.getKey());
                        UserAttributeReaderMBean userAttrBean=userEntry.getValue();
                        String[] attrs=userAttrBean.getSupportedUserAttributeNames();
                        UserProfile userProfile=new UserProfile();
                        for(String attr:attrs){
                            if(null!=userAttrBean.getUserAttributeValue(userEntry.getKey(), attr)){
                                System.out.println("    "+attr+" :: "+userAttrBean.getUserAttributeValue(userEntry.getKey(), attr));
                            }
                            userProfile.setUserName(userEntry.getKey());
                            if(null!=userAttrBean.getUserAttributeValue(userEntry.getKey(), "mobile")){
                                userProfile.setMobileNo(userAttrBean.getUserAttributeValue(userEntry.getKey(), "mobile").toString());   
                            }
                            if(null!=userAttrBean.getUserAttributeValue(userEntry.getKey(), "mail")){
                                userProfile.setEmailID(userAttrBean.getUserAttributeValue(userEntry.getKey(), "mail").toString());   
                            }
                        }
                        contactList.put(entry.getKey(), userProfile);
                    }
                }
            }
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

    public void setContactList(Map<String, UserProfile> contactList) {
        this.contactList = contactList;
    }

    public Map<String, UserProfile> getContactList() {
        return contactList;
    }
}
