package gov.cms.portal.qiocollabaration.extension.view.resources.util;

import gov.cms.portal.qiocollabaration.content.beans.ContentFolderBean;
import gov.cms.portal.qiocollabaration.content.beans.ContentItemBean;
import gov.cms.portal.qiocollabaration.content.util.WCContentUtil;
import gov.cms.portal.qiocollabaration.extension.view.resources.beans.ResourceBean;
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

    public static List<TopicBean> getAllTopicsListFromContentserver(String resourcesParentFolderPath) {
        return loadTopicsListFromContentserver(resourcesParentFolderPath);
    }

    private static List<TopicBean> loadTopicsListFromContentserver(String resourcesParentFolderPath) {
        System.out.println("ResourceContentUtil.java loadTopicsListFromContentserver() starts executing resourcesParentFolderPath = " + resourcesParentFolderPath);
        WCContentUtil csUtil = new WCContentUtil("idc://hovm1014.keste.com:4444", "weblogic");
        List<ContentFolderBean> contentFolders = null;
        List<TopicBean> allTopicsList = null;
        try {
            contentFolders = csUtil.getSubFoldersByCollectionPath(resourcesParentFolderPath);
            System.out.println("ResourceContentUtil.java loadTopicsListFromContentserver() contentFolders = " + contentFolders);
            allTopicsList = formTopicBeansFromContentItemBeans(contentFolders);
            if (allTopicsList != null && allTopicsList.size() > 0) {
                loadResourcesListOfTopicFromContentServer(allTopicsList.get(0));
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ResourceContentUtil.java loadTopicsListFromContentserver() Exception is " + e);
        }
        System.out.println("ResourceContentUtil.java loadTopicsListFromContentserver() allTopicsList = " + allTopicsList);
        return allTopicsList;
    }

    public static void loadResourcesListOfTopicFromContentServer(TopicBean topicBean) {
        System.out.println("ResourceContentUtil.java loadTopicsListFromContentserver2() starts executing = ");
        WCContentUtil csUtil = new WCContentUtil("idc://hovm1014.keste.com:4444", "weblogic");
        List<ContentItemBean> contentItems = null;
        try {
            contentItems = csUtil.getFolderContentItemsByCollectionPath(topicBean.getCollectionPath());
            System.out.println("ResourceContentUtil.java loadTopicsListFromContentserver2() contentItems = " + contentItems);
            topicBean.setTopicResources(getResources(contentItems));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ResourceContentUtil.java loadTopicsListFromContentserver2() Exception is " + e);
        }
        System.out.println("ResourceContentUtil.java loadTopicsListFromContentserver2() allTopicsList = " + topicBean.getTopicResources());
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
        topicBean.setTopicName(contentFolder.getTitle());
        topicBean.setTopicTaskTag(contentFolder.getComments());
        return topicBean;
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

    public static void main(String[] args) {
        List<TopicBean> allTopicsList = getAllTopicsListFromContentserver("");
        System.out.println(allTopicsList);
    }
}
