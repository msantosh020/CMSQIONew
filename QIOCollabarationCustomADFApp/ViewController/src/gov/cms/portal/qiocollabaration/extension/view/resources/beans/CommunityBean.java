package gov.cms.portal.qiocollabaration.extension.view.resources.beans;

import java.util.List;

public class CommunityBean {
    public CommunityBean() {
        super();
    }
    
    public CommunityBean(boolean emptyCommunity) {
        super();
        this.emptyCommunity = emptyCommunity;
    }
    
    private String communityCode;
    private String communityName;
    private String parentCollectionId;
    private String collectionId;
    private String collectionPath;
    List<TopicBean> communityTopics;
    private boolean emptyCommunity;

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

    public void setEmptyCommunity(boolean emptyCommunity) {
        this.emptyCommunity = emptyCommunity;
    }

    public boolean isEmptyCommunity() {
        return emptyCommunity;
    }
}
