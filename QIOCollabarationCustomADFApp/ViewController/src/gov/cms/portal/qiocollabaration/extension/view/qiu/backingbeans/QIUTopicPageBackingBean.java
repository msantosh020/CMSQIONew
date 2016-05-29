package gov.cms.portal.qiocollabaration.extension.view.qiu.backingbeans;

import gov.cms.portal.qiocollabaration.extension.view.common.util.Util;
import gov.cms.portal.qiocollabaration.extension.view.qiu.beans.QIUTopicBean;
import gov.cms.portal.qiocollabaration.extension.view.qiu.beans.QIUTopicCategoryBean;
import gov.cms.portal.qiocollabaration.extension.view.qiu.util.QIUContentUtil;

import java.util.List;

import oracle.adf.share.logging.ADFLogger;


public class QIUTopicPageBackingBean {
    private static ADFLogger _logger = ADFLogger.createADFLogger(QIUTopicPageBackingBean.class);

    public QIUTopicPageBackingBean() {
        super();
    }
    private List<QIUTopicCategoryBean> qiuTopicCategoryList;
    private String qiuTopicCSParentFolderPath;
    private QIUTopicBean qiuTopicBean;
   

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
                qiuTopicCSParentFolderPath = "/WebCenterSpaces-Root/QIOCollaboration/QIU/";
            }
        }
        return qiuTopicCSParentFolderPath;
    }

    public void setQiuTopicBean(QIUTopicBean qiuTopicBean) {
        this.qiuTopicBean = qiuTopicBean;
    }

    public QIUTopicBean getQiuTopicBean() {
        if (qiuTopicBean == null) {
            _logger.info("in the null section of getQiuTopicBean =");
            qiuTopicBean = QIUContentUtil.loadQIUTopicBean(getQiuTopicCSParentFolderPath());
            _logger.info("after  of getQiuTopicBean = " + qiuTopicBean);
        }
        return qiuTopicBean;
    }

}
