package gov.cms.portal.qiocollabaration.extension.view.resources.beans;

import java.util.ArrayList;
import java.util.List;

public class SubTopicBean {
    public SubTopicBean() {
        super();
    }
    
    public SubTopicBean(String subTopicName) {
        super();
        this.subTopicName = subTopicName;
        topicResources = new ArrayList<ResourceBean>();
        filteredTopicResources = new ArrayList<ResourceBean>();
    }
    
    private String subTopicName;
    private String subTopicTaskTag;
    private String parentCollectionId;
    private String folderType;
    private String collectionPath;
    private List<ResourceBean> topicResources;
    private List<ResourceBean> filteredTopicResources;
    private String collectionId;

    public void setSubTopicName(String subTopicName) {
        this.subTopicName = subTopicName;
    }

    public String getSubTopicName() {
        return subTopicName;
    }

    public void setSubTopicTaskTag(String subTopicTaskTag) {
        this.subTopicTaskTag = subTopicTaskTag;
    }

    public String getSubTopicTaskTag() {
        return subTopicTaskTag;
    }

    public void setParentCollectionId(String parentCollectionId) {
        this.parentCollectionId = parentCollectionId;
    }

    public String getParentCollectionId() {
        return parentCollectionId;
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

    public void setTopicResources(List<ResourceBean> topicResources) {
        this.topicResources = topicResources;
    }

    public List<ResourceBean> getTopicResources() {
        return topicResources;
    }

    public void setFilteredTopicResources(List<ResourceBean> filteredTopicResources) {
        this.filteredTopicResources = filteredTopicResources;
    }

    public List<ResourceBean> getFilteredTopicResources() {
        return filteredTopicResources;
    }

    public void setCollectionId(String collectionId) {
        this.collectionId = collectionId;
    }

    public String getCollectionId() {
        return collectionId;
    }
}
