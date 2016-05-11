package gov.cms.portal.qiocollabaration.extension.view.qiu.util;

import gov.cms.portal.qiocollabaration.content.beans.ContentFolderBean;
import gov.cms.portal.qiocollabaration.content.beans.ContentItemBean;
import gov.cms.portal.qiocollabaration.content.util.WCContentUtil;
import gov.cms.portal.qiocollabaration.extension.view.qiu.beans.QIUTopicBean;
import gov.cms.portal.qiocollabaration.extension.view.qiu.beans.QIUTopicCategoryBean;
import gov.cms.portal.qiocollabaration.extension.view.resources.beans.ResourceBean;

import java.util.ArrayList;
import java.util.List;


public class QIUContentUtil {
    public QIUContentUtil() {
        super();
    }

    private static WCContentUtil getWCContentUtil() {
        // Use below lines while deploying to server TODO
        WCContentUtil csUtil = new WCContentUtil();
        // Use below code for running local machine
//        String url = "http://10.163.64.1:16200/cs/idcplg";
//        WCContentUtil csUtil = new WCContentUtil(url, "weblogic");
        return csUtil;
    }

    public static List<QIUTopicBean> loadQIUTopics(String qiuCSParentFolderPath) {
        System.out.println("QIUContentUtil.java loadQIUTopics() starts executing qiuCSParentFolderPath = " + qiuCSParentFolderPath);
        WCContentUtil csUtil = getWCContentUtil();
        List<ContentFolderBean> qiuTopicFolders = null;
        List<QIUTopicBean> qiuTopicList = new ArrayList<QIUTopicBean>();
        QIUTopicBean qiuTopicBean = null;
        try {
            String collectionId = csUtil.getFolderCollectionId(qiuCSParentFolderPath);
            System.out.println("QIUContentUtil.java loadQIUTopics() collectionId = " + collectionId);
            qiuTopicFolders = csUtil.getSubFolders(collectionId);
            System.out.println("QIUContentUtil.java loadQIUTopics() qiuTopicFolders = " + qiuTopicFolders);
            for (ContentFolderBean contentFolder : qiuTopicFolders) {
                qiuTopicBean = getQIUTopicBean(contentFolder);
                qiuTopicList.add(qiuTopicBean);
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("QIUContentUtil.java loadQIUTopics() Exception is " + e);
        }
        System.out.println("QIUContentUtil.java loadQIUTopics() qiuTopicList = " + qiuTopicList);
        return qiuTopicList;
    }

    private static QIUTopicBean getQIUTopicBean(ContentFolderBean contentFolder) {
        QIUTopicBean qiuTopicBean = new QIUTopicBean();
        qiuTopicBean.setCollectionPath(contentFolder.getCollectionPath());
        qiuTopicBean.setParentCollectionId(contentFolder.getParentCollectionId());
        qiuTopicBean.setCollectionId(contentFolder.getCollectionId());
        qiuTopicBean.setTopicName(contentFolder.getCollectionName());
        return qiuTopicBean;
    }

    public static List<QIUTopicCategoryBean> getQiuTopicCategoryList(String qiuTopcCollectionId) {
        System.out.println("QIUContentUtil.java getQiuTopicCategoryList() starts executing qiuTopcCollectionId = " + qiuTopcCollectionId);
        List<QIUTopicCategoryBean> qiuTopicCategoryList = new ArrayList<QIUTopicCategoryBean>();
        WCContentUtil csUtil = getWCContentUtil();
        List<ContentFolderBean> qiuTopicCategoryFolders = null;
        QIUTopicCategoryBean qiuTopicCategoryBean = null;
        try {
            qiuTopicCategoryFolders = csUtil.getSubFolders(qiuTopcCollectionId);
            System.out.println("QIUContentUtil.java getQiuTopicCategoryList() qiuTopicFolders = " + qiuTopicCategoryFolders);
            for (ContentFolderBean contentFolder : qiuTopicCategoryFolders) {
                qiuTopicCategoryBean = getQIUTopicCategoryBean(contentFolder);
                loadQIUTopicCategoryResources(csUtil, qiuTopicCategoryBean);
                qiuTopicCategoryList.add(qiuTopicCategoryBean);
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("QIUContentUtil.java getQiuTopicCategoryList() Exception is " + e);
        }
        System.out.println("QIUContentUtil.java getQiuTopicCategoryList() qiuTopicCategoryList = " + qiuTopicCategoryList);
        return qiuTopicCategoryList;
    }

    private static QIUTopicCategoryBean getQIUTopicCategoryBean(ContentFolderBean contentFolder) {
        QIUTopicCategoryBean qiuTopicCategoryBean = new QIUTopicCategoryBean();
        qiuTopicCategoryBean.setCollectionPath(contentFolder.getCollectionPath());
        qiuTopicCategoryBean.setParentCollectionId(contentFolder.getParentCollectionId());
        qiuTopicCategoryBean.setCollectionId(contentFolder.getCollectionId());
        qiuTopicCategoryBean.setCategoryName(contentFolder.getCollectionName());
        return qiuTopicCategoryBean;
    }

    private static void loadQIUTopicCategoryResources(WCContentUtil csUtil, QIUTopicCategoryBean qiuTopicCategoryBean) {
        List<ContentItemBean> categoryContentItems = null;
        try {
            categoryContentItems = csUtil.getFolderContentItemsByCollectionID(qiuTopicCategoryBean.getCollectionId());
            qiuTopicCategoryBean.setResources(getResources(categoryContentItems));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ResourceContentUtil.java loadResourcesListOfTopicFromContentServer() Exception is " + e);
        }
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
        resourceBean.setResourceNativeWebUrl(contentItem.getNativeWebURL());
        resourceBean.setResourceWebUrl(contentItem.getWebURL());
        return resourceBean;
    }

    public static void main(String[] args) throws Exception {
        List<QIUTopicBean> qiuTopicList = loadQIUTopics("/WebCenterSpaces-Root/QIU/");
        System.out.println("qiuTopicList =" + qiuTopicList);
        for (QIUTopicBean qiuTopic : qiuTopicList) {
            System.out.println("qiuTopic.getCollectionId() =" + qiuTopic.getCollectionId());
            List<QIUTopicCategoryBean> qiuTopicCategoryList = getQiuTopicCategoryList(qiuTopic.getCollectionId());
            System.out.println("contentList =" + qiuTopicCategoryList);
        }
    }
}
