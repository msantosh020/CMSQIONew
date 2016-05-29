package gov.cms.portal.qiocollabaration.extension.view.loa.beans;

import java.util.List;

public class LOAModuleBean {
    public LOAModuleBean() {
        super();
    }

    public LOAModuleBean(String moduleName, String moduleDesc) {
        super();
        this.moduleName = moduleName;
        this.moduleDesc = moduleDesc;
    }

    private String moduleName;
    private String parentCollectionId;
    private String collectionPath;
    private String collectionId;
    private String moduleDesc;
    private String moduleImageUrl;
    
    private String moduleTitleDesc;
    private String moduleIconUrl;
    private String moduleSpeakerBioLinkUrl;
    private List<LOAModuleCategoryBean> moduleCategoryList;
    private List<String[]> sectionList;

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getModuleName() {
        return moduleName;
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

    public void setModuleDesc(String moduleDesc) {
        this.moduleDesc = moduleDesc;
    }

    public String getModuleDesc() {
        return moduleDesc;
    }

    public void setModuleImageUrl(String moduleImageUrl) {
        this.moduleImageUrl = moduleImageUrl;
    }

    public String getModuleImageUrl() {
        return moduleImageUrl;
    }

    public void setModuleTitleDesc(String moduleTitleDesc) {
        this.moduleTitleDesc = moduleTitleDesc;
    }

    public String getModuleTitleDesc() {
        return moduleTitleDesc;
    }

    public void setModuleCategoryList(List<LOAModuleCategoryBean> moduleCategoryList) {
        this.moduleCategoryList = moduleCategoryList;
    }

    public List<LOAModuleCategoryBean> getModuleCategoryList() {
        return moduleCategoryList;
    }

    public void setModuleIconUrl(String moduleIconUrl) {
        this.moduleIconUrl = moduleIconUrl;
    }

    public String getModuleIconUrl() {
        return moduleIconUrl;
    }

    public void setModuleSpeakerBioLinkUrl(String moduleSpeakerBioLinkUrl) {
        this.moduleSpeakerBioLinkUrl = moduleSpeakerBioLinkUrl;
    }

    public String getModuleSpeakerBioLinkUrl() {
        return moduleSpeakerBioLinkUrl;
    }

    public void setSectionList(List<String[]> sectionList) {
        this.sectionList = sectionList;
    }

    public List<String[]> getSectionList() {
        return sectionList;
    }
}
