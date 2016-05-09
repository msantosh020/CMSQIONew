package gov.cms.portal.qiocollabaration.extension.view.loa.backingbeans;

import gov.cms.portal.qiocollabaration.extension.view.common.util.Util;
import gov.cms.portal.qiocollabaration.extension.view.loa.beans.LOATopicBean;

import gov.cms.portal.qiocollabaration.extension.view.resources.util.LOAContentUtil;

import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;


public class  LOAMainBackingBean{
    public LOAMainBackingBean() {
        super();
    }

    public static int NUMBER_RESOURCES_PER_PAGE = 12;
    private List<LOATopicBean> loaTopicList;
    private List<LOATopicBean> currentPageTopics;
    private String loaCSParentFolderPath;
    private int currentPageIndex = 1;
    private Integer totalPagesSize;
    private List<SelectItem> pagesSI;

    public void setLoaTopicList(List<LOATopicBean> loaTopicList) {
        this.loaTopicList = loaTopicList;
    }

    public List<LOATopicBean> getLoaTopicList() {
        if (loaTopicList == null) {
            loaTopicList = LOAContentUtil.loadLOATopics(getLoaCSParentFolderPath());
        }
        return loaTopicList;
    }

    public void setLoaCSParentFolderPath(String loaCSParentFolderPath) {
        this.loaCSParentFolderPath = loaCSParentFolderPath;
    }

    public String getLoaCSParentFolderPath() {
        if (loaCSParentFolderPath == null) {
            loaCSParentFolderPath = Util.getPageFlowScopeParamValue("loaMainContentPath");
            if (loaCSParentFolderPath == null) {
                loaCSParentFolderPath = "/WebCenterSpaces-Root/LOA/";
            }
        }
        return loaCSParentFolderPath;
    }


    public void setCurrentPageTopics(List<LOATopicBean> currentPageTopics) {
        this.currentPageTopics = currentPageTopics;
    }

    public List<LOATopicBean> getCurrentPageTopics() {
        if (currentPageTopics == null) {
            currentPageTopics = new ArrayList<LOATopicBean>();
            int filteredTopicResourcesSize = getLoaTopicList() != null ? getLoaTopicList().size() : 0;
            if (filteredTopicResourcesSize > 0) {
                int currentPageStartIndex = (getCurrentPageIndex() - 1) * NUMBER_RESOURCES_PER_PAGE;
                int currentPageEndIndex = (getCurrentPageIndex() * NUMBER_RESOURCES_PER_PAGE);
                if (currentPageEndIndex > filteredTopicResourcesSize) {
                    currentPageEndIndex = filteredTopicResourcesSize;
                }

                for (int i = currentPageStartIndex; i < currentPageEndIndex; i++) {
                    currentPageTopics.add(getLoaTopicList().get(i));
                }
            }
            System.out.println("LOAMainBackingBean.java getCurrentPageTopics() = " + currentPageTopics);
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
            int filteredTopicResourcesSize = getLoaTopicList().size();
            totalPagesSize =
                    filteredTopicResourcesSize % NUMBER_RESOURCES_PER_PAGE > 0 ? (filteredTopicResourcesSize / NUMBER_RESOURCES_PER_PAGE) + 1 : filteredTopicResourcesSize / NUMBER_RESOURCES_PER_PAGE;
            System.out.println("LOAMainBackingBean.java getTotalPagesSize() totalPagesSize= " + totalPagesSize);
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
