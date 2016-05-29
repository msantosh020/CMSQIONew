package gov.cms.portal.qiocollabaration.extension.view.qiu.beans;

import gov.cms.portal.qiocollabaration.extension.view.resources.beans.ResourceBean;

import java.io.Serializable;

import java.util.List;

public class QIUTopicCategoryBean  implements Serializable {
    private static final long serialVersionUID = 1L;

    public QIUTopicCategoryBean() {
        super();
    }
    
    private String categoryName;
    private String parentCollectionId;
    private String collectionPath;
    private String collectionId;
    private List<ResourceBean> resources;
    private String categoryType;

    public void setResources(List<ResourceBean> resources) {
        this.resources = resources;
    }

    public List<ResourceBean> getResources() {
        return resources;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryName() {
        return categoryName;
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

    public void setCategoryType(String categoryType) {
        this.categoryType = categoryType;
    }

    public String getCategoryType() {
        return categoryType;
    }
}
