package gov.cms.portal.qiocollabaration.extension.view.resources.beans;

import java.util.List;

public class CommunityBean {
    public CommunityBean() {
        super();
    }
    
    private String communityCode;
    private String communityName;
    private String parentCollectionId;
    private String collectionId;
    private String collectionPath;
    List<TopicBean> communityTopics;

    public void setCommunityCode(String communityCode) {
        this.communityCode = communityCode;
    }

    public String getCommunityCode() {
        return communityCode;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }

    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityTopics(List<TopicBean> communityTopics) {
        this.communityTopics = communityTopics;
    }

    public List<TopicBean> getCommunityTopics() {
        return communityTopics;
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
