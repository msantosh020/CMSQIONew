package gov.cms.portal.qiocollabaration.extension.view.loa.backingbeans;

import gov.cms.portal.qiocollabaration.extension.view.common.util.Util;
import gov.cms.portal.qiocollabaration.extension.view.loa.beans.LOAModuleBean;
import gov.cms.portal.qiocollabaration.extension.view.loa.beans.LOAPartBean;
import gov.cms.portal.qiocollabaration.extension.view.loa.beans.LOAPartTypeBean;
import gov.cms.portal.qiocollabaration.extension.view.loa.util.LOAContentUtil;

import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;


public class LOAMainBackingBean {
    public LOAMainBackingBean() {
        super();
    }

    public static int NUMBER_RESOURCES_PER_PAGE = 12;
    private List<LOAModuleBean> currentPageModules;
    private String loaCSParentFolderPath;
    private int currentPageIndex = 1;
    private Integer totalPagesSize;
    private List<SelectItem> pagesSI;
    private List<LOAPartBean> loaPartList;
    private int selectedPartIndex = 0;
    private LOAPartBean selectedLOAPartBean;
    private String selectedPartType;
    private LOAPartTypeBean selectedLOAPartTypeBean;
    private int selectedPartTypeIndex = 0;

    public void setLoaCSParentFolderPath(String loaCSParentFolderPath) {
        this.loaCSParentFolderPath = loaCSParentFolderPath;
    }

    public String getLoaCSParentFolderPath() {
        if (loaCSParentFolderPath == null) {
            loaCSParentFolderPath = Util.getPageFlowScopeParamValue("loaMainContentPath");
            if (loaCSParentFolderPath == null) {
                loaCSParentFolderPath = "/WebCenterSpaces-Root/QIOCollaboration/LOA/LOA 1.0 (2015)";
            }
        }
        return loaCSParentFolderPath;
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
            int filteredTopicResourcesSize = getSelectedLOAPartBean().getLoaPartTypeList().size();
            totalPagesSize =
                    filteredTopicResourcesSize % NUMBER_RESOURCES_PER_PAGE > 0 ? (filteredTopicResourcesSize / NUMBER_RESOURCES_PER_PAGE) + 1 : filteredTopicResourcesSize / NUMBER_RESOURCES_PER_PAGE;
            System.out.println("LOAMainBackingBean.java getTotalPagesSize() totalPagesSize= " + totalPagesSize);
        }
        return totalPagesSize;
    }

    public void onPageNumberSelection(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        setCurrentPageModules(null);
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

    public void setLoaPartList(List<LOAPartBean> loaPartList) {
        this.loaPartList = loaPartList;
    }

    public List<LOAPartBean> getLoaPartList() {
        if (loaPartList == null) {
            loaPartList = LOAContentUtil.getLOAParts(getLoaCSParentFolderPath());
        }
        return loaPartList;
    }

    public void setSelectedPartIndex(int selectedPartIndex) {
        this.selectedPartIndex = selectedPartIndex;
    }

    public int getSelectedPartIndex() {
        return selectedPartIndex;
    }

    public void setSelectedLOAPartBean(LOAPartBean selectedLOAPartBean) {
        this.selectedLOAPartBean = selectedLOAPartBean;
    }

    public LOAPartBean getSelectedLOAPartBean() {
        if (selectedLOAPartBean == null) {
            selectedLOAPartBean = getLoaPartList().get(getSelectedPartIndex());
        }
        return selectedLOAPartBean;
    }

    public void setSelectedPartType(String selectedPartType) {
        this.selectedPartType = selectedPartType;
    }

    public String getSelectedPartType() {
        if (selectedPartType == null) {
            selectedPartType = getSelectedLOAPartTypeBean().getPartTypeName();
        }
        return selectedPartType;
    }

    public void setCurrentPageModules(List<LOAModuleBean> currentPageModules) {
        this.currentPageModules = currentPageModules;
    }

    public List<LOAModuleBean> getCurrentPageModules() {
        if (currentPageModules == null) {
            currentPageModules = new ArrayList<LOAModuleBean>();
            int filteredTopicResourcesSize = getSelectedLOAPartTypeBean().getModuleList() != null ? getSelectedLOAPartTypeBean().getModuleList().size() : 0;
            if (filteredTopicResourcesSize > 0) {
                int currentPageStartIndex = (getCurrentPageIndex() - 1) * NUMBER_RESOURCES_PER_PAGE;
                int currentPageEndIndex = (getCurrentPageIndex() * NUMBER_RESOURCES_PER_PAGE);
                if (currentPageEndIndex > filteredTopicResourcesSize) {
                    currentPageEndIndex = filteredTopicResourcesSize;
                }

                for (int i = currentPageStartIndex; i < currentPageEndIndex; i++) {
                    currentPageModules.add(getSelectedLOAPartTypeBean().getModuleList().get(i));
                }
            }
            System.out.println("LOAMainBackingBean.java getCurrentPageModules() = " + currentPageModules);
        }
        return currentPageModules;
    }

    public void onLOAPartSelection(ActionEvent actionEvent) {
        // Add event code here...
        setSelectedLOAPartBean(null);
        setSelectedPartType(null);
        setCurrentPageIndex(1);
        setCurrentPageModules(null);
        setPagesSI(null);
        setTotalPagesSize(null);
        setSelectedPartTypeIndex(0);
        setSelectedLOAPartTypeBean(null);
    }

    public void setSelectedLOAPartTypeBean(LOAPartTypeBean selectedLOAPartTypeBean) {
        this.selectedLOAPartTypeBean = selectedLOAPartTypeBean;
    }

    public LOAPartTypeBean getSelectedLOAPartTypeBean() {
        if (selectedLOAPartTypeBean == null) {
            selectedLOAPartTypeBean = getSelectedLOAPartBean().getLoaPartTypeList().get(getSelectedPartTypeIndex());
        }
        return selectedLOAPartTypeBean;
    }

    public void setSelectedPartTypeIndex(int selectedPartTypeIndex) {
        this.selectedPartTypeIndex = selectedPartTypeIndex;
    }

    public int getSelectedPartTypeIndex() {
        return selectedPartTypeIndex;
    }
}
