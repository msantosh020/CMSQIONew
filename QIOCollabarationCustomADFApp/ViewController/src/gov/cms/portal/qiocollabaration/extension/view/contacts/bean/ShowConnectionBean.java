package gov.cms.portal.qiocollabaration.extension.view.contacts.bean;


import gov.cms.portal.qiocollabaration.extension.view.contacts.util.ContactConnectionsUtil;

import gov.cms.portal.qiocollabaration.extension.view.contacts.util.GroupUserConnectionsUtil;

import gov.cms.portal.qiocollabaration.extension.view.contacts.util.WCConnectionUtil;

import java.awt.image.BufferedImage;

import java.io.ByteArrayOutputStream;
import java.io.File;

import java.io.IOException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import javax.imageio.ImageIO;

import javax.naming.NamingException;

import oracle.adf.share.ADFContext;
import oracle.adf.view.rich.component.rich.layout.RichPanelBox;
import oracle.adf.view.rich.component.rich.nav.RichCommandLink;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;

import oracle.webcenter.peopleconnections.profile.ProfileException;

import org.apache.commons.codec.binary.Base64;
import org.apache.myfaces.trinidad.component.UIXIterator;


import weblogic.management.utils.InvalidCursorException;
import weblogic.management.utils.InvalidParameterException;
import weblogic.management.utils.NotFoundException;

public class ShowConnectionBean {
    private String invitationMessage;
    private String selectedUser;
    private List<SelectItem> dropdownOptions=new ArrayList<SelectItem>();
    private String searchText="";
    private List<String> paginationArrayList=new ArrayList<String>();
    private int paginationIteratorFirstIndex=0;
    private RichCommandLink pageCommandLink;
    private UIXIterator contactIterator;
    private RichPanelBox contactPanelBox;
    private String testImage="";
    private Integer rows;

    public ShowConnectionBean() {
    }
    
    private List<GroupUserConnectionsUtil> contactList = new ArrayList<GroupUserConnectionsUtil>();
    private List<GroupUserConnectionsUtil> originalList = new ArrayList<GroupUserConnectionsUtil>(); 
    

    public void fetchCompleteConnectionList() {
        String userName=ADFContext.getCurrent().getSecurityContext().getUserName();
        System.out.println(userName);
        WCConnectionUtil connectionUtil=new WCConnectionUtil();
        try {
            contactList=connectionUtil.getAllUserProfilesGroupWise(userName);
            originalList.addAll(contactList);
            dropdownOptions.clear();
            List<String> dropdownLabels=new ArrayList<String>();
            for(String groupName:ContactConnectionsUtil.getUserGroups(userName)){
                for(GroupUserConnectionsUtil groupObj:contactList){
                    String[] groupNames=null;
                    if(groupObj.getGroupName().indexOf(",")>0){
                        groupNames=groupObj.getGroupName().split(",");   
                    }else{
                        groupNames=new String[]{groupObj.getGroupName()};
                    }
                    for(String group:groupNames){
                        if(groupName.equalsIgnoreCase(group)){
                            dropdownLabels.add(groupName);
                        }
                    }
                }
            }
            Set<String> tempSet=new HashSet<String>();
            tempSet.addAll(dropdownLabels);
            dropdownLabels.clear();
            dropdownLabels.addAll(tempSet);
            for(String label:dropdownLabels){
                SelectItem si=new SelectItem();
                si.setLabel(label);
                si.setValue(label);
                dropdownOptions.add(si);
            }
            SelectItem allSI=new SelectItem();
            allSI.setLabel("All");
            allSI.setValue("All");
            dropdownOptions.add(allSI);
            setPagination();
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
    
    public void setPagination(){
        paginationArrayList.clear();
        int count=0;
        System.out.println("contact list size in pagination : "+contactList.size());
        if(contactList.size()%16 == 0){
            count=contactList.size()/16;
        }else{
            count=(contactList.size()/16)+1;
        }
        for(int i=0;i<count;i++){
            paginationArrayList.add(String.valueOf(i));
        }
    }

    public void setContactList(List<GroupUserConnectionsUtil> contactList) {
        this.contactList = contactList;
    }

    public List<GroupUserConnectionsUtil> getContactList() {
        return contactList;
    }

    public void setInvitationMessage(String invitationMessage) {
        this.invitationMessage = invitationMessage;
    }

    public String getInvitationMessage() {
        return invitationMessage;
    }

    public void setSelectedUser(String selectedUser) {
        this.selectedUser = selectedUser;
    }

    public String getSelectedUser() {
        return selectedUser;
    }

    public void groupChangeListener(ValueChangeEvent valueChangeEvent) {
        if(null!=valueChangeEvent && null!=valueChangeEvent.getNewValue()){
            contactList.clear();
            if(valueChangeEvent.getNewValue().toString().equalsIgnoreCase("All")){
                contactList.addAll(originalList);
            }else{
                System.out.println(originalList.size());
                contactList.addAll(originalList);
                String selectedGroup=valueChangeEvent.getNewValue().toString();
                Iterator it = contactList.iterator(); 
                while(it.hasNext()){
                    GroupUserConnectionsUtil o = (GroupUserConnectionsUtil)it.next();
                    String[] groupNames=null;
                    if(o.getGroupName().indexOf(",")>0){
                        groupNames=o.getGroupName().split(",");
                    }else{
                        groupNames=new String[]{o.getGroupName()};
                    }
                    boolean foundGroupName=false;
                    for(String group:groupNames){
                        if(selectedGroup.equalsIgnoreCase(group)){
                            foundGroupName=true;
                            break;
                        }
                    }
                    if(!foundGroupName){
                        it.remove();
                    }
                }
            }
            setPagination();
        }
        // Add event code here...
    }

    public void setDropdownOptions(List<SelectItem> dropdownOptions) {
        this.dropdownOptions = dropdownOptions;
    }

    public List<SelectItem> getDropdownOptions() {
        return dropdownOptions;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    public String getSearchText() {
        return searchText;
    }

    public void searchTextVCL(ValueChangeEvent valueChangeEvent) {
        System.out.println("search called");
        try{
        searchText=valueChangeEvent.getNewValue().toString();
        contactList.clear();
        contactList.addAll(originalList);
        Iterator it = contactList.iterator(); 
        while(it.hasNext()){
            GroupUserConnectionsUtil o = (GroupUserConnectionsUtil)it.next();
            if(null!=o.getUserProfile().getFirstName() && o.getUserProfile().getFirstName().contains(searchText)){
            }else{
                if(null!=o.getUserProfile().getLastName() && o.getUserProfile().getLastName().contains(searchText)){
                }else{
                    it.remove();
                } 
            }
        }
        setPagination();
        }catch(ProfileException e){
            FacesContext.getCurrentInstance().addMessage("An exception occurred", new FacesMessage("An exception occurred"));
        }
    }

    public void setPaginationArrayList(List<String> paginationArrayList) {
        this.paginationArrayList = paginationArrayList;
    }

    public List<String> getPaginationArrayList() {
        return paginationArrayList;
    }

    public void setPaginationIteratorFirstIndex(int paginationIteratorFirstIndex) {
        this.paginationIteratorFirstIndex = paginationIteratorFirstIndex;
    }

    public int getPaginationIteratorFirstIndex() {
        return paginationIteratorFirstIndex;
    }

    public void changePageListener(ActionEvent actionEvent) {
        rows = null;
        String currentPageNo=pageCommandLink.getText();
        paginationIteratorFirstIndex=(new Integer(currentPageNo)-1)*20;
        AdfFacesContext.getCurrentInstance().addPartialTarget(contactPanelBox);
        // Add event code here...
    }

    public void setPageCommandLink(RichCommandLink pageCommandLink) {
        this.pageCommandLink = pageCommandLink;
    }

    public RichCommandLink getPageCommandLink() {
        return pageCommandLink;
    }

    public void setContactIterator(UIXIterator contactIterator) {
        this.contactIterator = contactIterator;
    }

    public UIXIterator getContactIterator() {
        return contactIterator;
    }

    public void setContactPanelBox(RichPanelBox contactPanelBox) {
        this.contactPanelBox = contactPanelBox;
    }

    public RichPanelBox getContactPanelBox() {
        return contactPanelBox;
    }

    public void setTestImage(String testImage) {
        this.testImage = testImage;
    }

    public String getTestImage() {
        return testImage;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public Integer getRows() {
        if(rows == null){
            int size = getContactList() != null ? getContactList().size() : 0;
            if(size == 0){
                rows = 0;
                return rows;
            }
            int startIndex = getPaginationIteratorFirstIndex();
            int endIndex = getPaginationIteratorFirstIndex() + 20 > size ? size :  getPaginationIteratorFirstIndex() + 20;
            int totalItemsInCurentPage = endIndex - startIndex;
            rows =  totalItemsInCurentPage % 4 > 0 ? ((totalItemsInCurentPage/4)+1) : (totalItemsInCurentPage/4);            
        }
        return rows;
    }
}
