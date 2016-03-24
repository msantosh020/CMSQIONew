package gov.cms.portal.qiocollabaration.extension.view.resources.util;

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

    public static List<TopicBean> getAllTopicsListFromContentserver() {
        return loadTopicsListTest();
        /*
        System.out.println("ResourceContentUtil.java getAllTopicsListFromContentserver() starts executing = ");
        List<TopicBean> allTopicsList1 = loadTopicsListFromContentserver1();
        System.out.println("ResourceContentUtil.java getAllTopicsListFromContentserver() allTopicsList1 = " + allTopicsList1);
        List<TopicBean> allTopicsList2 = loadTopicsListFromContentserver2();
        System.out.println("ResourceContentUtil.java getAllTopicsListFromContentserver() allTopicsList2 = " + allTopicsList2);
        return allTopicsList1;
        */
    }

    private static List<TopicBean> loadTopicsListFromContentserver1() {
        System.out.println("ResourceContentUtil.java loadTopicsListFromContentserver1() starts executing = ");
        WCContentUtil scUtil = new WCContentUtil("idc://hovm1014.keste.com:4444", "weblogic");
        List<ContentItemBean> contentItems = null;
        List<TopicBean> allTopicsList = null;
        try {
            contentItems = scUtil.getFolderContentItemsByCollectionPath("/Contribution Folders/TestPortal/Resources/Images/");
            System.out.println("ResourceContentUtil.java loadTopicsListFromContentserver1() contentItems = " + contentItems);
            allTopicsList = formTopicBeansFromContentItemBeans(contentItems);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ResourceContentUtil.java loadTopicsListFromContentserver1() Exception is " + e);
        }
        System.out.println("ResourceContentUtil.java loadTopicsListFromContentserver1() allTopicsList = " + allTopicsList);
        return allTopicsList;
    }

    private static List<TopicBean> loadTopicsListFromContentserver2() {
        System.out.println("ResourceContentUtil.java loadTopicsListFromContentserver2() starts executing = ");
        WCContentUtil scUtil = new WCContentUtil();
        List<ContentItemBean> contentItems = null;
        List<TopicBean> allTopicsList = null;
        try {
            contentItems = scUtil.getFolderContentItemsByCollectionPath("/Contribution Folders/TestPortal/Resources/Images/");
            System.out.println("ResourceContentUtil.java loadTopicsListFromContentserver2() contentItems = " + contentItems);
            allTopicsList = formTopicBeansFromContentItemBeans(contentItems);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ResourceContentUtil.java loadTopicsListFromContentserver2() Exception is " + e);
        }
        System.out.println("ResourceContentUtil.java loadTopicsListFromContentserver2() allTopicsList = " + allTopicsList);
        return allTopicsList;
    }

    private static List<TopicBean> formTopicBeansFromContentItemBeans(List<ContentItemBean> contentItems) {
        System.out.println("ResourceContentUtil.java formTopicBeansFromContentItemBeans() contentItems = " + contentItems);
        ResourceBean resourceBean = null;
        Map<String, TopicBean> topicsMap = new HashMap<String, TopicBean>();
        for (ContentItemBean contentItem : contentItems) {
            resourceBean = getResourceBean(contentItem);
            addResourceToTopic(topicsMap, contentItem.getTopicsNames(), contentItem.getTagNames(), resourceBean);
        }

        List<TopicBean> allTopicsList = new ArrayList<TopicBean>();
        for (String topicName : topicsMap.keySet()) {
            allTopicsList.add(topicsMap.get(topicName));
        }
        System.out.println("ResourceContentUtil.java formTopicBeansFromContentItemBeans() allTopicsList = " + allTopicsList);
        return allTopicsList;
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
        List<TopicBean> allTopicsList = getAllTopicsListFromContentserver();
        System.out.println(allTopicsList);
    }
}
