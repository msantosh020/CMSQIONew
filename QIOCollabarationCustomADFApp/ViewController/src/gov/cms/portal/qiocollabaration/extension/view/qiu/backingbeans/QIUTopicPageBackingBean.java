package gov.cms.portal.qiocollabaration.extension.view.qiu.backingbeans;

import gov.cms.portal.qiocollabaration.extension.view.common.util.Util;
import gov.cms.portal.qiocollabaration.extension.view.qiu.beans.QIUTopicCategoryBean;
import gov.cms.portal.qiocollabaration.extension.view.resources.util.QIUContentUtil;

import java.util.List;


public class QIUTopicPageBackingBean {
    public QIUTopicPageBackingBean() {
        super();
    }
    private List<QIUTopicCategoryBean> qiuTopicCategoryList;
    private String qiuTopicCSParentFolderPath;

    public void setQiuTopicCategoryList(List<QIUTopicCategoryBean> qiuTopicCategoryList) {
        this.qiuTopicCategoryList = qiuTopicCategoryList;
    }

    public List<QIUTopicCategoryBean> getQiuTopicCategoryList() {
        if (qiuTopicCategoryList == null) {
            qiuTopicCategoryList = QIUContentUtil.getQiuTopicCategoryList(getQiuTopicCSParentFolderPath());
        }
        return qiuTopicCategoryList;
    }

    public void setQiuTopicCSParentFolderPath(String qiuTopicCSParentFolderPath) {
        this.qiuTopicCSParentFolderPath = qiuTopicCSParentFolderPath;
    }

    public String getQiuTopicCSParentFolderPath() {
        if (qiuTopicCSParentFolderPath == null) {
            qiuTopicCSParentFolderPath = Util.getPageFlowScopeParamValue("qiuTopicCSParentFolderPath");
            if (qiuTopicCSParentFolderPath == null) {
                qiuTopicCSParentFolderPath = "/WebCenterSpaces-Root/QIU/";
            }
        }
        return qiuTopicCSParentFolderPath;
    }
}
