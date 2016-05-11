package gov.cms.portal.qiocollabaration.extension.view.loa.beans;

import java.util.ArrayList;
import java.util.List;

public class LOAPartBean {
    public LOAPartBean() {
        super();
    }

    public LOAPartBean(String partName, int noOfModules) {
        super();
        this.partName = partName;
        moduleList = new ArrayList<LOAModuleBean>();
        for (int i = 0; i < noOfModules; i++) {
            moduleList.add(new LOAModuleBean("Module : " + i, "This description for DDDDDDD_" + i));
        }
    }

    private String partName;
    private String parentCollectionId;
    private String collectionPath;
    private String collectionId;
    private List<LOAModuleBean> moduleList;

    public void setPartName(String partName) {
        this.partName = partName;
    }

    public String getPartName() {
        return partName;
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

    public void setModuleList(List<LOAModuleBean> moduleList) {
        this.moduleList = moduleList;
    }

    public List<LOAModuleBean> getModuleList() {
        return moduleList;
    }
}
