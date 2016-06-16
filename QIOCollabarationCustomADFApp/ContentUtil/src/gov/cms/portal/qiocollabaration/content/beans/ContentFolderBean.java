package gov.cms.portal.qiocollabaration.content.beans;


import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

public class ContentFolderBean extends ContentBean implements Serializable {
    private static final long serialVersionUID = 1L;

    public ContentFolderBean() {
        super();
    }

    private String collectionName; // virutal folder name
    private String parentCollectionId;
    private String folderType;
    private String collectionPath;
    private List<ContentItemBean> folderItems = new ArrayList<ContentItemBean>();
    private List<ContentFolderBean> subFolders = new ArrayList<ContentFolderBean>();
    private String titleandPubDate;
    private String faculty;
    private String backgroundHeader;
    private String backgroundContent;
    private String learningObjectivesHeader;
    private String learningObjectivesContent;
    private String keyTakeAwaysHeader;
    private String keyTakeAwaysContent;
    private String callOutBoxContent;
    private String description;
    
   
    
    
    

    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }

    public String getCollectionName() {
        return collectionName;
    }

    public void setParentCollectionId(String parentCollectionId) {
        this.parentCollectionId = parentCollectionId;
    }

    public String getParentCollectionId() {
        return parentCollectionId;
    }

    public void setFolderItems(List<ContentItemBean> folderItems) {
        this.folderItems = folderItems;
    }

    public List<ContentItemBean> getFolderItems() {
        return folderItems;
    }

    public void setSubFolders(List<ContentFolderBean> subFolders) {
        this.subFolders = subFolders;
    }

    public List<ContentFolderBean> getSubFolders() {
        return subFolders;
    }

    public void setFolderType(String folderType) {
        this.folderType = folderType;
    }

    public String getFolderType() {
        return folderType;
    }

    public void setCollectionPath(String collectionPath) {
        this.collectionPath = collectionPath;
    }

    public String getCollectionPath() {
        return collectionPath;
    }


    public void setTitleandPubDate(String titleandPubDate) {
        this.titleandPubDate = titleandPubDate;
    }

    public String getTitleandPubDate() {
        return titleandPubDate;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setBackgroundHeader(String backgroundHeader) {
        this.backgroundHeader = backgroundHeader;
    }

    public String getBackgroundHeader() {
        return backgroundHeader;
    }

    public void setBackgroundContent(String backgroundContent) {
        this.backgroundContent = backgroundContent;
    }

    public String getBackgroundContent() {
        return backgroundContent;
    }

    public void setLearningObjectivesHeader(String learningObjectivesHeader) {
        this.learningObjectivesHeader = learningObjectivesHeader;
    }

    public String getLearningObjectivesHeader() {
        return learningObjectivesHeader;
    }

    public void setLearningObjectivesContent(String learningObjectivesContent) {
        this.learningObjectivesContent = learningObjectivesContent;
    }

    public String getLearningObjectivesContent() {
        return learningObjectivesContent;
    }

    public void setKeyTakeAwaysHeader(String keyTakeAwaysHeader) {
        this.keyTakeAwaysHeader = keyTakeAwaysHeader;
    }

    public String getKeyTakeAwaysHeader() {
        return keyTakeAwaysHeader;
    }

    public void setKeyTakeAwaysContent(String keyTakeAwaysContent) {
        this.keyTakeAwaysContent = keyTakeAwaysContent;
    }

    public String getKeyTakeAwaysContent() {
        return keyTakeAwaysContent;
    }

    public void setCallOutBoxContent(String callOutBoxContent) {
        this.callOutBoxContent = callOutBoxContent;
    }

    public String getCallOutBoxContent() {
        return callOutBoxContent;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
