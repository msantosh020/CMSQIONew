package gov.cms.portal.qiocollabaration.extension.view.resources.util;

import gov.cms.portal.qiocollabaration.content.beans.ContentFolderBean;
import gov.cms.portal.qiocollabaration.content.beans.ContentItemBean;
import gov.cms.portal.qiocollabaration.content.beans.ResourceSearchBean;
import gov.cms.portal.qiocollabaration.content.util.WCContentUtil;
import gov.cms.portal.qiocollabaration.extension.view.resources.beans.CommunityBean;
import gov.cms.portal.qiocollabaration.extension.view.resources.beans.ResourceBean;
import gov.cms.portal.qiocollabaration.extension.view.resources.beans.SubTopicBean;
import gov.cms.portal.qiocollabaration.extension.view.resources.beans.TopicBean;

import java.text.ParseException;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import java.util.Map;

import javax.naming.NamingException;

import oracle.stellent.ridc.IdcClientException;

public class ResourceContentUtil {
    public ResourceContentUtil() {
        super();
    }

    private static WCContentUtil getWCContentUtil() {
        // Use below lines while deploying to server TODO
        WCContentUtil csUtil = new WCContentUtil();
        // Use below code for running local machine
      //  String url = "idc://hovm1014.keste.com:4444"; TODO
//                String url = "http://10.163.64.1:16200/cs/idcplg";
//                WCContentUtil csUtil = new WCContentUtil(url, "weblogic");
        return csUtil;
    }

    public static List<CommunityBean> getAllTopicsListFromContentserver(String resourcesParentFolderPath) {
        return initializeResources(resourcesParentFolderPath);
    }

    public static List<CommunityBean> initializeResourcesFromContentserver(String resourcesParentFolderPath) {
        return initializeResources(resourcesParentFolderPath);
    }

    public static List<CommunityBean> initializeResources(String resourcesParentFolderPath) {
        System.out.println("ResourceContentUtil.java initializeResources() starts executing resourcesParentFolderPath = " + resourcesParentFolderPath);
        List<CommunityBean> communities = new ArrayList<CommunityBean>();
        WCContentUtil csUtil = getWCContentUtil();
        List<ContentFolderBean> communityFolders = null;
        CommunityBean communityBean = null;
        try {
            communityFolders = csUtil.getSubFoldersByCollectionPath(resourcesParentFolderPath);

            for (ContentFolderBean contentFolder : communityFolders) {
                communityBean = new CommunityBean();
                communityBean.setCollectionPath(contentFolder.getCollectionPath());
                communityBean.setParentCollectionId(contentFolder.getParentCollectionId());
                communityBean.setCollectionId(contentFolder.getCollectionId());
                communityBean.setCommunityName(contentFolder.getCollectionName());
                communityBean.setCommunityCode(contentFolder.getCollectionName());
                communities.add(communityBean);
            }

            if (communities.size() > 0) {
                loadCommunityTopics(communities.get(0));
            }
            System.out.println("ResourceContentUtil.java initializeResources() communities = " + communities);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ResourceContentUtil.java initializeResources() Exception is " + e);
        }
        return communities;
    }

    public static void loadCommunityTopics(CommunityBean community) {
        System.out.println("ResourceContentUtil.java loadCommunityTopics() starts executing community = " + community);
        WCContentUtil csUtil = getWCContentUtil();
        List<ContentFolderBean> coomunityTopicFolders = null;
        List<TopicBean> communityTopicsList = null;
        try {
            coomunityTopicFolders = csUtil.getSubFolders(community.getCollectionId());
            System.out.println("ResourceContentUtil.java loadCommunityTopics() contentFolders = " + coomunityTopicFolders);
            communityTopicsList = formTopicBeansFromContentItemBeans(coomunityTopicFolders);
            community.setCommunityTopics(communityTopicsList);
            if (communityTopicsList != null && communityTopicsList.size() > 0) {
                loadResourcesListOfTopicFromContentServer(communityTopicsList.get(0));
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ResourceContentUtil.java loadCommunityTopics() Exception is " + e);
        }
        System.out.println("ResourceContentUtil.java loadCommunityTopics() allTopicsList = " + communityTopicsList);
    }

    public static void loadResourcesListOfTopicFromContentServer(TopicBean topicBean) {
        System.out.println("ResourceContentUtil.java loadResourcesListOfTopicFromContentServer() starts executing topicBean = " + topicBean);
        WCContentUtil csUtil = getWCContentUtil();
        List<ContentFolderBean> subTopicFolders = null;
        List<SubTopicBean> subTopics = new ArrayList<SubTopicBean>();
        SubTopicBean subTopicBean = null;
        List<ContentItemBean> subTopicsContentItems = null;
        List<ResourceBean> allTopicsContentItems = new ArrayList<ResourceBean>();
        try {
            subTopicFolders = csUtil.getSubFolders(topicBean.getCollectionId());
            System.out.println("ResourceContentUtil.java loadResourcesListOfTopicFromContentServer() contentItems = " + subTopicFolders);
            for (ContentFolderBean contentFolder : subTopicFolders) {
                subTopicBean = getSubTopicBean(contentFolder);
                subTopics.add(subTopicBean);
                subTopicsContentItems = csUtil.getFolderContentItemsByCollectionID(subTopicBean.getCollectionId());
                subTopicBean.setTopicResources(getResources(subTopicsContentItems));
                allTopicsContentItems.addAll(subTopicBean.getTopicResources());
            }
            topicBean.setFilteredSubTopics(subTopics);
            topicBean.setFilteredTopicResources(allTopicsContentItems);
            topicBean.setTopicResources(allTopicsContentItems);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ResourceContentUtil.java loadResourcesListOfTopicFromContentServer() Exception is " + e);
        }
        System.out.println("ResourceContentUtil.java loadResourcesListOfTopicFromContentServer() allTopicsList = " + topicBean.getTopicResources());
    }

    private static List<TopicBean> formTopicBeansFromContentItemBeans(List<ContentFolderBean> contentFolders) {
        System.out.println("ResourceContentUtil.java formTopicBeansFromContentItemBeans() contentFolders = " + contentFolders);
        List<TopicBean> allTopicsList = new ArrayList<TopicBean>();
        TopicBean topicBean = null;
        for (ContentFolderBean contentFolder : contentFolders) {
            topicBean = getTopicBean(contentFolder);
            allTopicsList.add(topicBean);
        }
        System.out.println("ResourceContentUtil.java formTopicBeansFromContentItemBeans() allTopicsList = " + allTopicsList);
        return allTopicsList;
    }

    private static TopicBean getTopicBean(ContentFolderBean contentFolder) {
        TopicBean topicBean = new TopicBean();
        topicBean.setCollectionPath(contentFolder.getCollectionPath());
        topicBean.setFolderType(contentFolder.getFolderType());
        topicBean.setParentCollectionId(contentFolder.getParentCollectionId());
        topicBean.setCollectionId(contentFolder.getCollectionId());
        topicBean.setTopicName(contentFolder.getCollectionName());
        topicBean.setTopicTaskTag(contentFolder.getComments());
        return topicBean;
    }

    private static SubTopicBean getSubTopicBean(ContentFolderBean contentFolder) {
        SubTopicBean subTopicBean = new SubTopicBean();
        subTopicBean.setCollectionPath(contentFolder.getCollectionPath());
        subTopicBean.setFolderType(contentFolder.getFolderType());
        subTopicBean.setParentCollectionId(contentFolder.getParentCollectionId());
        subTopicBean.setCollectionId(contentFolder.getCollectionId());
        subTopicBean.setSubTopicName(contentFolder.getCollectionName());
        subTopicBean.setSubTopicTaskTag(contentFolder.getComments());
        return subTopicBean;
    }

    private static List<ResourceBean> getResources(List<ContentItemBean> contentItems) {
        List<ResourceBean> resources = new ArrayList<ResourceBean>();
        for (ContentItemBean contentItem : contentItems) {
            resources.add(getResourceBean(contentItem));
        }
        return resources;
    }

    private static ResourceBean getResourceBean(ContentItemBean contentItem) {
        ResourceBean resourceBean = new ResourceBean();
        resourceBean.setAddedDate(contentItem.getCreationDate());
        resourceBean.setAuthor(contentItem.getAuthor());
        resourceBean.setCommunity(contentItem.getDocType());
        resourceBean.setLength("" + contentItem.getFileSize());
        resourceBean.setResourceDescription(contentItem.getComments());
        resourceBean.setResourceFormat(contentItem.getDocFormat());
        resourceBean.setResourceTitle(contentItem.getTitle());
        resourceBean.setResourceUrl(contentItem.getWebURL());
        resourceBean.setThumbnailImageUrl(contentItem.getWebURL());
        resourceBean.setUpdatedDate(contentItem.getLastModifiedDate());
        resourceBean.setUploadedOrganization(contentItem.getOwner());
        return resourceBean;
    }

    private static void addResourceToTopic(Map<String, TopicBean> topicsMap, String commaSepTopicNames, String commaSepTagNames, ResourceBean resourceBean) {
        String[] topicNameArr = commaSepTopicNames.split(",");
        String[] tagNameArr = commaSepTagNames.split(",");
        int count = topicNameArr.length;
        int count1 = tagNameArr.length;
        TopicBean topicBean = null;
        for (int i = 0; i < count; i++) {
            topicBean = getTopicBean(topicsMap, topicNameArr[i].trim(), (i < count1 ? tagNameArr[i].trim() : ""));
            topicBean.getTopicResources().add(resourceBean);
        }
    }

    private static TopicBean getTopicBean(Map<String, TopicBean> topicsMap, String topicName, String topicTag) {
        TopicBean topicBean = null;
        if (topicsMap.containsKey(topicName)) {
            topicBean = topicsMap.get(topicName);
        } else {
            topicBean = new TopicBean(topicName, topicTag);
            topicsMap.put(topicName, topicBean);
        }
        return topicBean;
    }

    private static List<TopicBean> loadTopicsListTest() {
        List<TopicBean> allTopicsList = new ArrayList<TopicBean>();
        List<ResourceBean> topicResources = null;

        ResourceBean[] resources = new ResourceBean[100];
        for (int i = 0; i < 100; i++) {
            resources[i] =
                    new ResourceBean("ResourceTitle_" + i, "ResourceDescription_" + i, "ResourceFormat_" + i, "Length_" + i, new Date(), new Date(), "UploadedOrganization_" + i, "Author_" + i, "ThumbnailImageUrl_" +
                                     i, "ResourceUrl_" + i, "Community_" + i);
        }

        TopicBean topic = null;
        for (int i = 0; i < 10; i++) {
            topicResources = new ArrayList<ResourceBean>();
            topic = new TopicBean("Topic_" + i, "TaskTag_" + i);
            topicResources = new ArrayList<ResourceBean>();
            for (int j = 0; j < 10; j++) {
                topicResources.add(resources[i * 10 + j]);
            }
            topic.setTopicResources(topicResources);
            allTopicsList.add(topic);
        }

        return allTopicsList;
    }

    public static List<CommunityBean> searchResurces(String topFolderPath, ResourceSearchBean searchBean) {
        System.out.println("ResourceContentUtil.java searchResurces() starts executing searchBean = " + searchBean + ";; topFolderPath=" + topFolderPath);
        List<CommunityBean> communities = new ArrayList<CommunityBean>();
        WCContentUtil csUtil = getWCContentUtil();
        try {
            String collectionId = csUtil.getFolderCollectionId(topFolderPath);
            System.out.println("collectionId =" + collectionId);
            List<ContentItemBean> searchContentItemsList = csUtil.searchFileByTitleInFolder(collectionId, searchBean);
            communities = formSearchResources(searchContentItemsList);
            System.out.println("ResourceContentUtil.java initializeResources() communities = " + communities);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ResourceContentUtil.java initializeResources() Exception is " + e);
        }
        return communities;
    }

    private static void addResourceToCommunity(Map<String, CommunityBean> communitMap, ContentItemBean contentItemBean) {
        CommunityBean community = communitMap.get(contentItemBean.getCommunityName());
        if (community == null) {
            community = new CommunityBean(contentItemBean.getCommunityName());
            communitMap.put(contentItemBean.getCommunityName(), community);
        }
        community.addResource(contentItemBean.getTopicsNames(), contentItemBean.getSubTopicName(), getResourceBean(contentItemBean));
    }

    private static List<CommunityBean> formSearchResources(List<ContentItemBean> searchContentItemsList) {
        List<CommunityBean> communityList = new ArrayList<CommunityBean>();
        Map<String, CommunityBean> communitMap = new HashMap<String, CommunityBean>();
        for (ContentItemBean contentItemBean : searchContentItemsList) {
            addResourceToCommunity(communitMap, contentItemBean);
        }

        for (Map.Entry<String, CommunityBean> entry : communitMap.entrySet()) {
            communityList.add(entry.getValue());
        }

        return communityList;
    }

    public static void main(String[] args) throws Exception {
        //List<CommunityBean> allTopicsList = getAllTopicsListFromContentserver("/WebCenterSpaces-Root/Resources/");
        String url = "http://10.163.64.1:16200/cs/idcplg";
        WCContentUtil csUtil = new WCContentUtil(url, "weblogic");
        String collectionId = csUtil.getFolderCollectionId("/WebCenterSpaces-Root/Resources/");
        System.out.println("collectionId =" + collectionId);
        //List<ContentItemBean> contentList = csUtil.searchFileByTitleInFolder("QIN", collectionId);
        ResourceSearchBean searchBean = new ResourceSearchBean();
//        searchBean.setTitle("AA");
       searchBean.setScopeOfWork("10thSOW");
//        searchBean.setResourceType("General");
        List<ContentItemBean> contentList = csUtil.searchFileByTitleInFolder(collectionId, searchBean);
        System.out.println("contentList =" + contentList);
        List<CommunityBean> communityList = formSearchResources(contentList);
        System.out.println("communityList =" + communityList);
    }
}
