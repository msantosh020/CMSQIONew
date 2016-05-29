package gov.cms.portal.qiocollabaration.extension.view.loa.beans;

import java.util.List;

public class LOAPartTypeBean {
    public LOAPartTypeBean() {
        super();
    }
    
    private String partTypeName;
    private String parentCollectionId;
    private String collectionPath;
    private String collectionId;
    private List<LOAModuleBean> moduleList;
    

    public void setModuleList(List<LOAModuleBean> moduleList) {
        this.moduleList = moduleList;
    }

    public List<LOAModuleBean> getModuleList() {
        return moduleList;
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

    public void setPartTypeName(String partTypeName) {
        this.partTypeName = partTypeName;
    }

    public String getPartTypeName() {
        return partTypeName;
    }
}
