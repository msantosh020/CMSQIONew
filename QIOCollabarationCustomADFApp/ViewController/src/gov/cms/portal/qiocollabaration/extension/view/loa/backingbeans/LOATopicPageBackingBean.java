package gov.cms.portal.qiocollabaration.extension.view.loa.backingbeans;


import gov.cms.portal.qiocollabaration.extension.view.common.util.Util;
import gov.cms.portal.qiocollabaration.extension.view.loa.beans.LOAModuleBean;
import gov.cms.portal.qiocollabaration.extension.view.loa.beans.LOAModuleCategoryBean;

import gov.cms.portal.qiocollabaration.extension.view.loa.util.LOAContentUtil;

import java.util.List;


public class LOATopicPageBackingBean {
    public LOATopicPageBackingBean() {
        super();
    }

    private LOAModuleBean loaModule;
    private String loaModuleCSParentFolderPath;

    public void setLoaModule(LOAModuleBean loaModule) {
        this.loaModule = loaModule;
    }

    public LOAModuleBean getLoaModule() {
        if (loaModule == null) {
            loaModule = LOAContentUtil.getLOAModule(getLoaModuleCSParentFolderPath());
        }
        return loaModule;
    }

    public void setLoaModuleCSParentFolderPath(String loaModuleCSParentFolderPath) {
        this.loaModuleCSParentFolderPath = loaModuleCSParentFolderPath;
    }

    public String getLoaModuleCSParentFolderPath() {
        if (loaModuleCSParentFolderPath == null) {
            loaModuleCSParentFolderPath = Util.getPageFlowScopeParamValue("loaModuleCSParentFolderPath");
            if (loaModuleCSParentFolderPath == null) {
                loaModuleCSParentFolderPath = "/WebCenterSpaces-Root/LOA/";
            }
        }
        return loaModuleCSParentFolderPath;
    }
}

