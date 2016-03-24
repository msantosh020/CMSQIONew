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
}
