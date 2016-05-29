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
    }

    private String partName;
    private String parentCollectionId;
    private String collectionPath;
    private String collectionId;
    private List<LOAPartTypeBean> loaPartTypeList;
    

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

    public void setLoaPartTypeList(List<LOAPartTypeBean> loaPartTypeList) {
        this.loaPartTypeList = loaPartTypeList;
    }

    public List<LOAPartTypeBean> getLoaPartTypeList() {
        return loaPartTypeList;
    }
}
