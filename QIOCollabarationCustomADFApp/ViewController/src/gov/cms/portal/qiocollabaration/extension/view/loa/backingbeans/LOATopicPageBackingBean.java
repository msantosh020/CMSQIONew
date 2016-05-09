package gov.cms.portal.qiocollabaration.extension.view.loa.backingbeans;


import gov.cms.portal.qiocollabaration.extension.view.common.util.Util;
import gov.cms.portal.qiocollabaration.extension.view.loa.beans.LOATopicCategoryBean;
import gov.cms.portal.qiocollabaration.extension.view.resources.util.LOAContentUtil;

import gov.cms.portal.qiocollabaration.extension.view.resources.util.QIUContentUtil;

import java.util.List;


public class LOATopicPageBackingBean {
    public LOATopicPageBackingBean() {
        super();
    }

    private List<LOATopicCategoryBean> loaTopicCategoryList;
    private String loaTopicCSParentFolderPath;

    public void setLoaTopicCategoryList(List<LOATopicCategoryBean> loaTopicCategoryList) {
        this.loaTopicCategoryList = loaTopicCategoryList;
    }

    public List<LOATopicCategoryBean> getLoaTopicCategoryList() {
        if (loaTopicCategoryList == null) {
            loaTopicCategoryList = LOAContentUtil.getLoaTopicCategoryList(getLoaTopicCSParentFolderPath());
        }
        return loaTopicCategoryList;
    }

    public void setLoaTopicCSParentFolderPath(String loaTopicCSParentFolderPath) {
        this.loaTopicCSParentFolderPath = loaTopicCSParentFolderPath;
    }

    public String getLoaTopicCSParentFolderPath() {
        if (loaTopicCSParentFolderPath == null) {
            loaTopicCSParentFolderPath = Util.getPageFlowScopeParamValue("loaTopicCSParentFolderPath");
            if (loaTopicCSParentFolderPath == null) {
                loaTopicCSParentFolderPath = "/WebCenterSpaces-Root/LOA/";
            }
        }
        return loaTopicCSParentFolderPath;
    }
}

