package gov.cms.portal.qiocollabaration.extension.view.resources.beans;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

public class TopicBean implements Serializable {
    private static final long serialVersionUID = 0L;

    public TopicBean() {
        super();
    }

    public TopicBean(boolean emptyTopic) {
        super();
        this.emptyTopic = emptyTopic;
    }

    public TopicBean(String topicName, String topicTaskTag) {
        super();
        this.topicName = topicName;
        this.topicTaskTag = topicTaskTag;
        topicResources = new ArrayList<ResourceBean>();
    }

    private String topicName;
    private String topicTaskTag;
    private String parentCollectionId;
    private String folderType;
    private String collectionPath;
    private String collectionId;
    private boolean emptyTopic;

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicTaskTag(String topicTaskTag) {
        this.topicTaskTag = topicTaskTag;
    }

    public String getTopicTaskTag() {
        return topicTaskTag;
    }

    private List<ResourceBean> topicResources;
    private List<ResourceBean> filteredTopicResources;
    private List<SubTopicBean> filteredSubTopics;

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

    public boolean isNonEmptyFilteredTopicResourcesTopic() {
        boolean state = true;
        if (getFilteredTopicResources() == null || getFilteredTopicResources().isEmpty()) {
            state = false;
        }
        return state;
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

    public void setFilteredSubTopics(List<SubTopicBean> filteredSubTopics) {
        this.filteredSubTopics = filteredSubTopics;
    }

    public List<SubTopicBean> getFilteredSubTopics() {
        return filteredSubTopics;
    }


    public void setCollectionId(String collectionId) {
        this.collectionId = collectionId;
    }

    public String getCollectionId() {
        return collectionId;
    }

    public void setEmptyTopic(boolean emptyTopic) {
        this.emptyTopic = emptyTopic;
    }

    public boolean isEmptyTopic() {
        return emptyTopic;
    }

    public void addResource(String subTopicName, ResourceBean resource) {
        SubTopicBean subTopicBean = getSubTopicBean(subTopicName);
        subTopicBean.getFilteredTopicResources().add(resource);
        subTopicBean.getTopicResources().add(resource);
    }

    private SubTopicBean getSubTopicBean(String subTopicName) {
        SubTopicBean subTopicBean = null;
        for (SubTopicBean stBean : getFilteredSubTopics()) {
            if (subTopicName.equals(stBean.getSubTopicName())) {
                subTopicBean = stBean;
                break;
            }
        }
        if (subTopicBean == null) {
            subTopicBean = new SubTopicBean(subTopicName);
            getFilteredSubTopics().add(subTopicBean);
        }
        return subTopicBean;
    }
}
