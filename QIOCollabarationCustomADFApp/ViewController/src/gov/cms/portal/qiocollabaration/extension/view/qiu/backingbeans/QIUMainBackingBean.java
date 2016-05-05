package gov.cms.portal.qiocollabaration.extension.view.qiu.backingbeans;

import gov.cms.portal.qiocollabaration.extension.view.common.util.Util;
import gov.cms.portal.qiocollabaration.extension.view.qiu.beans.QIUTopicBean;

import gov.cms.portal.qiocollabaration.extension.view.resources.util.QIUContentUtil;

import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;


public class QIUMainBackingBean {
    public QIUMainBackingBean() {
        super();
    }

    public static int NUMBER_RESOURCES_PER_PAGE = 12;

    private List<QIUTopicBean> qiuTopicList;
    private List<QIUTopicBean> currentPageTopics;
    private String qiuCSParentFolderPath;
    private int currentPageIndex = 1;
    private Integer totalPagesSize;
    private List<SelectItem> pagesSI;

    public void setQiuTopicList(List<QIUTopicBean> qiuTopicList) {
        this.qiuTopicList = qiuTopicList;
    }

    public List<QIUTopicBean> getQiuTopicList() {
        if (qiuTopicList == null) {
            qiuTopicList = QIUContentUtil.loadQIUTopics(getQiuCSParentFolderPath());
        }
        return qiuTopicList;
    }

    public void setQiuCSParentFolderPath(String qiuCSParentFolderPath) {
        this.qiuCSParentFolderPath = qiuCSParentFolderPath;
    }

    public String getQiuCSParentFolderPath() {
        if (qiuCSParentFolderPath == null) {
            qiuCSParentFolderPath = Util.getPageFlowScopeParamValue("qiuMainContentPath");
            if (qiuCSParentFolderPath == null) {
                qiuCSParentFolderPath = "/WebCenterSpaces-Root/QIU/";
            }
        }
        return qiuCSParentFolderPath;
    }


    public void setCurrentPageTopics(List<QIUTopicBean> currentPageTopics) {
        this.currentPageTopics = currentPageTopics;
    }

    public List<QIUTopicBean> getCurrentPageTopics() {
        if (currentPageTopics == null) {
            currentPageTopics = new ArrayList<QIUTopicBean>();
            int filteredTopicResourcesSize = getQiuTopicList() != null ? getQiuTopicList().size() : 0;
            if (filteredTopicResourcesSize > 0) {
                int currentPageStartIndex = (getCurrentPageIndex() - 1) * NUMBER_RESOURCES_PER_PAGE;
                int currentPageEndIndex = (getCurrentPageIndex() * NUMBER_RESOURCES_PER_PAGE);
                if (currentPageEndIndex > filteredTopicResourcesSize) {
                    currentPageEndIndex = filteredTopicResourcesSize;
                }

                for (int i = currentPageStartIndex; i < currentPageEndIndex; i++) {
                    currentPageTopics.add(getQiuTopicList().get(i));
                }
            }
            System.out.println("QIUMainBackingBean.java getCurrentPageTopics() = " + currentPageTopics);
        }
        return currentPageTopics;
    }

    public void setCurrentPageIndex(int currentPageIndex) {
        this.currentPageIndex = currentPageIndex;
    }

    public int getCurrentPageIndex() {
        return currentPageIndex;
    }

    public void setTotalPagesSize(Integer totalPagesSize) {
        this.totalPagesSize = totalPagesSize;
    }

    public Integer getTotalPagesSize() {
        if (totalPagesSize == null) {
            int filteredTopicResourcesSize = getQiuTopicList().size();
            totalPagesSize =
                    filteredTopicResourcesSize % NUMBER_RESOURCES_PER_PAGE > 0 ? (filteredTopicResourcesSize / NUMBER_RESOURCES_PER_PAGE) + 1 : filteredTopicResourcesSize / NUMBER_RESOURCES_PER_PAGE;
            System.out.println("QIUMainBackingBean.java getTotalPagesSize() totalPagesSize= " + totalPagesSize);
        }
        return totalPagesSize;
    }

    public void onPageNumberSelection(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        setCurrentPageTopics(null);
    }

    public void setPagesSI(List<SelectItem> pagesSI) {
        this.pagesSI = pagesSI;
    }

    public List<SelectItem> getPagesSI() {
        if (pagesSI == null) {
            pagesSI = new ArrayList<SelectItem>();
            for (int i = 1; i <= getTotalPagesSize(); i++) {
                pagesSI.add(new SelectItem(new Integer(i), "" + i));
            }
        }
        return pagesSI;
    }
}
