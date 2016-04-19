package gov.cms.portal.qiocollabaration.extension.view.resources.backingbean;

import gov.cms.portal.qiocollabaration.extension.view.common.util.Util;
import gov.cms.portal.qiocollabaration.extension.view.resources.beans.CommunityBean;
import gov.cms.portal.qiocollabaration.extension.view.resources.beans.ResourceBean;
import gov.cms.portal.qiocollabaration.extension.view.resources.beans.ResourceSearchBean;
import gov.cms.portal.qiocollabaration.extension.view.resources.beans.TopicBean;

import gov.cms.portal.qiocollabaration.extension.view.resources.util.ContainsSearchPredicate;
import gov.cms.portal.qiocollabaration.extension.view.resources.util.ResourceContentUtil;

import gov.cms.portal.qiocollabaration.extension.view.resources.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.apache.commons.beanutils.BeanPredicate;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.collections.PredicateUtils;

public class ResourcesBackingBean {
    public ResourcesBackingBean() {
        super();
    }

    private List<CommunityBean> communities;
    private CommunityBean currentCommunity;
    private List<TopicBean> allTopicsList;
    private List<TopicBean> filteredTopicsList;
    private List<ResourceBean> filteredTopicResources;
    private List<ResourceBean> currentPageTopicResources;
    private List<ResourceBean> featuredTopicResources;
    private static final int NUMBER_RESOURCES_PER_PAGE = 4;
    private int currentCommunityIndex = 0;
    private int currentTopicIndex = 0;
    private int currentPageIndex = 1;
    private Integer totalPagesSize;
    private TopicBean currentTopicBean = null;
    private ResourceSearchBean resourceSearchBean = new ResourceSearchBean();
    private List<SelectItem> pagesSI;
    private List<SelectItem> communitiesSI;
    private String resourcesCSParentFolderPath;

    public void setAllTopicsList(List<TopicBean> allTopicsList) {
        this.allTopicsList = allTopicsList;
    }

    public List<TopicBean> getAllTopicsList() {
        if (allTopicsList == null) {
            //allTopicsList = ResourceContentUtil.getAllTopicsListFromContentserver(getResourcesCSParentFolderPath());
            System.out.println("ResourcesBackingBean.java getAllTopicsList() = " + allTopicsList);
        }
        return allTopicsList;
    }

    public void setFilteredTopicsList(List<TopicBean> filteredTopicsList) {
        this.filteredTopicsList = filteredTopicsList;
    }

    public List<TopicBean> getFilteredTopicsList() {
        if (filteredTopicsList == null) {
            filteredTopicsList = new ArrayList<TopicBean>();
            for (TopicBean topic : getAllTopicsList()) {
                topic.setFilteredTopicResources(applyFilterOnResurces(topic.getTopicResources()));
                if (topic.isNonEmptyFilteredTopicResourcesTopic()) {
                    filteredTopicsList.add(topic);
                }
            }
            System.out.println("ResourcesBackingBean.java getFilteredTopicsList() = " + filteredTopicsList);
        }
        return filteredTopicsList;
    }

    public void setFilteredTopicResources(List<ResourceBean> filteredTopicResources) {
        this.filteredTopicResources = filteredTopicResources;
    }

    public List<ResourceBean> getFilteredTopicResources() {
        if (filteredTopicResources == null) {
            try {
                TopicBean currentTopic = getCurrentCommunity().getCommunityTopics().get(getCurrentTopicIndex());
                if (currentTopic.getTopicResources() == null) {
                    ResourceContentUtil.loadResourcesListOfTopicFromContentServer(currentTopic);
                }
                filteredTopicResources = currentTopic.getFilteredTopicResources();
            } catch (Exception e) {
                filteredTopicResources =  new ArrayList<ResourceBean>();
            }
            
        }
        return filteredTopicResources;
    }

    public void setCurrentTopicIndex(int currentTopicIndex) {
        this.currentTopicIndex = currentTopicIndex;
    }

    public int getCurrentTopicIndex() {
        return currentTopicIndex;
    }

    public void setCurrentTopicBean(TopicBean currentTopicBean) {
        this.currentTopicBean = currentTopicBean;
    }

    public TopicBean getCurrentTopicBean() {
        if (currentTopicBean == null) {
            if(!getCurrentCommunity().isEmptyCommunity()){
                currentTopicBean = getCurrentCommunity().getCommunityTopics().get(getCurrentTopicIndex());
                if (currentTopicBean.getTopicResources() == null) {
                    ResourceContentUtil.loadResourcesListOfTopicFromContentServer(currentTopicBean);
                }
            } else {
                currentTopicBean = new TopicBean(true);
            }
        }
        return currentTopicBean;
    }

    public void onTopicSelectionAction(ActionEvent actionEvent) {
        // Add event code here...
        Integer topicIndex = (Integer)actionEvent.getComponent().getAttributes().get("topicIndex");
        setCurrentTopicIndex(topicIndex);
        setFilteredTopicResources(null);
        setCurrentTopicBean(null);
        setCurrentPageTopicResources(null);
        setPagesSI(null);
        setTotalPagesSize(null);
        setCurrentPageIndex(1);
    }

    public void setResourceSearchBean(ResourceSearchBean resourceSearchBean) {
        this.resourceSearchBean = resourceSearchBean;
    }

    public ResourceSearchBean getResourceSearchBean() {
        return resourceSearchBean;
    }

    public void onResourceSearchClearAction(ActionEvent actionEvent) {
        // Add event code here...
        System.out.println("ResourcesBackingBean.java onResourceSearchClearAction() starts executing");
        getResourceSearchBean().clear();
        setFilteredTopicsList(null);
        setFilteredTopicResources(null);
        setCurrentTopicIndex(0);
        setCurrentPageIndex(1);
        setCurrentTopicBean(null);
        setCurrentPageTopicResources(null);
        setPagesSI(null);
        setTotalPagesSize(null);
        System.out.println("ResourcesBackingBean.java onResourceSearchClearAction() end of executing");
    }

    public void onResourceSearchAction(ActionEvent actionEvent) {
        // Add event code here...
        System.out.println("ResourcesBackingBean.java onResourceSearchAction() starts executing");
        setFilteredTopicsList(null);
        setFilteredTopicResources(null);
        setCurrentTopicIndex(0);
        setCurrentTopicBean(null);
        setCurrentPageTopicResources(null);
        setPagesSI(null);
        setTotalPagesSize(null);
        setCurrentPageIndex(1);
        System.out.println("ResourcesBackingBean.java onResourceSearchAction() end of executing");
    }

    private List<ResourceBean> applyFilterOnResurces(List<ResourceBean> topicResourceList) {
        System.out.println("ResourcesBackingBean.java applyFilterOnResurces() topicResourceList = " + topicResourceList);
        List<ResourceBean> filteredTopicResources = new ArrayList<ResourceBean>();
        if (getResourceSearchBean().isFilterCriteriaEntered()) {
            List<Predicate> lstAnyPredicate = new ArrayList<Predicate>();
            if (StringUtil.isNonEmpty(getResourceSearchBean().getAuthor())) {
                lstAnyPredicate.add(new BeanPredicate("author", new ContainsSearchPredicate(getResourceSearchBean().getAuthor())));
            }
            if (StringUtil.isNonEmpty(getResourceSearchBean().getFormat())) {
                lstAnyPredicate.add(new BeanPredicate("resourceFormat", new ContainsSearchPredicate(getResourceSearchBean().getFormat())));
            }
            if (StringUtil.isNonEmpty(getResourceSearchBean().getKeyphrase())) {
                lstAnyPredicate.add(new BeanPredicate("resourceDescription", new ContainsSearchPredicate(getResourceSearchBean().getKeyphrase())));
            }
            if (StringUtil.isNonEmpty(getResourceSearchBean().getOrganization())) {
                lstAnyPredicate.add(new BeanPredicate("uploadedOrganization", new ContainsSearchPredicate(getResourceSearchBean().getOrganization())));
            }
            if (StringUtil.isNonEmpty(getResourceSearchBean().getPublicationDate())) {
                lstAnyPredicate.add(new BeanPredicate("addedDate", new ContainsSearchPredicate(getResourceSearchBean().getPublicationDate())));
            }
            if (StringUtil.isNonEmpty(getResourceSearchBean().getState())) {
                lstAnyPredicate.add(new BeanPredicate("resourceTitle", new ContainsSearchPredicate(getResourceSearchBean().getState()))); //Need to change
            }
            Predicate anyPredicate = PredicateUtils.anyPredicate(lstAnyPredicate);
            filteredTopicResources = (List<ResourceBean>)CollectionUtils.select(topicResourceList, anyPredicate);
        } else {
            for (ResourceBean resource : topicResourceList) {
                filteredTopicResources.add(resource);
            }
        }
        System.out.println("ResourcesBackingBean.java applyFilterOnResurces() filteredTopicResources = " + filteredTopicResources);
        return filteredTopicResources;
    }

    public void setCurrentPageTopicResources(List<ResourceBean> currentPageTopicResources) {
        this.currentPageTopicResources = currentPageTopicResources;
    }

    public List<ResourceBean> getCurrentPageTopicResources() {
        if (currentPageTopicResources == null) {
            currentPageTopicResources = new ArrayList<ResourceBean>();
//            int filteredTopicResourcesSize = getFilteredTopicResources().size();
            int filteredTopicResourcesSize = getCurrentTopicBean().getFilteredTopicResources() != null ? getCurrentTopicBean().getFilteredTopicResources().size() : 0;
            if(filteredTopicResourcesSize > 0){
                int currentPageStartIndex = (getCurrentPageIndex() - 1) * NUMBER_RESOURCES_PER_PAGE;
                int currentPageEndIndex = (getCurrentPageIndex() * NUMBER_RESOURCES_PER_PAGE);
                if (currentPageEndIndex > filteredTopicResourcesSize) {
                    currentPageEndIndex = filteredTopicResourcesSize;
                }
    
                for (int i = currentPageStartIndex; i < currentPageEndIndex; i++) {
                    currentPageTopicResources.add(getFilteredTopicResources().get(i));
                }
            }
            System.out.println("ResourcesBackingBean.java getCurrentPageTopicResources() = " + currentPageTopicResources);
        }
        return currentPageTopicResources;
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
            int filteredTopicResourcesSize = getFilteredTopicResources().size();
            totalPagesSize =
                    filteredTopicResourcesSize % NUMBER_RESOURCES_PER_PAGE > 0 ? (filteredTopicResourcesSize / NUMBER_RESOURCES_PER_PAGE) + 1 : filteredTopicResourcesSize / NUMBER_RESOURCES_PER_PAGE;
            System.out.println("ResourcesBackingBean.java getTotalPagesSize() totalPagesSize= " + totalPagesSize);
        }
        return totalPagesSize;
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

    public void onPageNumberSelection(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        setCurrentPageTopicResources(null);
    }

    public void setFeaturedTopicResources(List<ResourceBean> featuredTopicResources) {
        this.featuredTopicResources = featuredTopicResources;
    }

    public List<ResourceBean> getFeaturedTopicResources() {
        if (featuredTopicResources == null) {
            featuredTopicResources = new ArrayList<ResourceBean>();
//            for (int i = 0; i < 4; i++) {
//                featuredTopicResources.add(getFilteredTopicResources().get(i));
//            }
//
//            System.out.println("ResourcesBackingBean.java getFeaturedTopicResources() = " + featuredTopicResources);
        }
        return featuredTopicResources;
    }


    public void setResourcesCSParentFolderPath(String resourcesCSParentFolderPath) {
        this.resourcesCSParentFolderPath = resourcesCSParentFolderPath;
    }

    public String getResourcesCSParentFolderPath() {
        if (resourcesCSParentFolderPath == null) {
            resourcesCSParentFolderPath = Util.getPageFlowScopeParamValue("resourcesCSParentFolderPath");
            if(resourcesCSParentFolderPath == null){
                resourcesCSParentFolderPath = "/WebCenterSpaces-Root/Resources/";
            }
        }
        return resourcesCSParentFolderPath;
    }

    public void setCurrentCommunity(CommunityBean currentCommunity) {
        this.currentCommunity = currentCommunity;
    }

    public CommunityBean getCurrentCommunity() {
        if (currentCommunity == null) {
            currentCommunity = getCommunities() != null && getCommunities().size() > 0 ? getCommunities().get(getCurrentCommunityIndex()) : new CommunityBean(true);
            if (currentCommunity != null && !currentCommunity.isEmptyCommunity() && currentCommunity.getCommunityTopics() == null) {
                ResourceContentUtil.loadCommunityTopics(currentCommunity);
            }
        }
        return currentCommunity;
    }

    public void setCommunities(List<CommunityBean> communities) {
        this.communities = communities;
    }

    public List<CommunityBean> getCommunities() {
        if (communities == null) {
            if(getResourcesCSParentFolderPath() != null){
                communities = ResourceContentUtil.initializeResourcesFromContentserver(getResourcesCSParentFolderPath());
            } else {
                communities = new ArrayList<CommunityBean>();
            }
            System.out.println("ResourcesBackingBean.java getCommunities() = " + communities);
        }
        return communities;
    }

    public void setCurrentCommunityIndex(int currentCommunityIndex) {
        this.currentCommunityIndex = currentCommunityIndex;
    }

    public int getCurrentCommunityIndex() {
        return currentCommunityIndex;
    }

    public void onCommunityChange(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        String selectedCommunity = (String)valueChangeEvent.getNewValue();
        int count = getCommunities().size();
        CommunityBean community = null;
        for (int i = 0; i < count; i++) {
            community = getCommunities().get(i);
            if (selectedCommunity.equals(community.getCommunityName())) {
                setCurrentCommunityIndex(i);
                setCurrentCommunity(null);
                break;
            }
        }
    }

    public void setCommunitiesSI(List<SelectItem> communitiesSI) {
        this.communitiesSI = communitiesSI;
    }

    public List<SelectItem> getCommunitiesSI() {
        if (communitiesSI == null) {
            communitiesSI = new ArrayList<SelectItem>();
            for (CommunityBean communityBean : getCommunities()) {
                communitiesSI.add(new SelectItem(communityBean.getCommunityName(), communityBean.getCommunityName()));
            }
        }
        return communitiesSI;
    }
}


