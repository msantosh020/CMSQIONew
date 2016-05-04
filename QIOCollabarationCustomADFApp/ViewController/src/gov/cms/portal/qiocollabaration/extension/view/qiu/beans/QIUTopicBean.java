package gov.cms.portal.qiocollabaration.extension.view.qiu.beans;

import java.io.Serializable;

import java.util.List;


public class QIUTopicBean implements Serializable {
    private static final long serialVersionUID = 1L;

    public QIUTopicBean() {
        super();
    }

    private String topicName;
    private String parentCollectionId;
    private String collectionPath;
    private String collectionId;

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setParentCollectionId(String parentCollectionId) {
        this.parentCollectionId = parentCollectionId;
    }

    public String getParentCollectionId() {
        return parentCollectionId;
    }

    public void setCollectionPath(String collectionPath) {
        this.collectionPath = collectionPath;
    }

    public String getCollectionPath() {
        return collectionPath;
    }

    public void setCollectionId(String collectionId) {
        this.collectionId = collectionId;
    }

    public String getCollectionId() {
        return collectionId;
    }
}
