package gov.cms.portal.qiocollabaration.extension.view.qiu.util;

import gov.cms.portal.qiocollabaration.content.beans.ContentFolderBean;
import gov.cms.portal.qiocollabaration.content.beans.ContentItemBean;
import gov.cms.portal.qiocollabaration.content.util.WCContentUtil;
import gov.cms.portal.qiocollabaration.extension.view.qiu.backingbeans.QIUTopicPageBackingBean;
import gov.cms.portal.qiocollabaration.extension.view.qiu.beans.QIUTopicBean;
import gov.cms.portal.qiocollabaration.extension.view.qiu.beans.QIUTopicCategoryBean;
import gov.cms.portal.qiocollabaration.extension.view.resources.beans.ResourceBean;

import java.util.ArrayList;
import java.util.List;

import oracle.adf.share.logging.ADFLogger;


public class QIUContentUtil {

    private static ADFLogger _logger = ADFLogger.createADFLogger(QIUContentUtil.class);

    public QIUContentUtil() {
        super();
    }

    private static WCContentUtil getWCContentUtil() {
        // Use below lines while deploying to server TODO
       WCContentUtil csUtil = new WCContentUtil();
        // Use below code for running local machine
//                String url = "http://10.163.64.1:16200/cs/idcplg";
//                WCContentUtil csUtil = new WCContentUtil(url, "weblogic");
        return csUtil;
    }

    public static List<QIUTopicBean> loadQIUTopics(String qiuCSParentFolderPath) {
        _logger.info("QIUContentUtil.java loadQIUTopics() starts executing qiuCSParentFolderPath = " + qiuCSParentFolderPath);
        WCContentUtil csUtil = getWCContentUtil();
        List<ContentFolderBean> qiuTopicFolders = null;
        List<QIUTopicBean> qiuTopicList = new ArrayList<QIUTopicBean>();
        QIUTopicBean qiuTopicBean = null;
        try {
            String collectionId = csUtil.getFolderCollectionId(qiuCSParentFolderPath);
            _logger.info("QIUContentUtil.java loadQIUTopics() collectionId = " + collectionId);
            qiuTopicFolders = csUtil.getQIUSubFolders(collectionId);
            _logger.info("QIUContentUtil.java loadQIUTopics() qiuTopicFolders = " + qiuTopicFolders);
            for (ContentFolderBean contentFolder : qiuTopicFolders) {
                qiuTopicBean = getQIUTopicBean(contentFolder, true);
                qiuTopicList.add(qiuTopicBean);
            }

        } catch (Exception e) {
            e.printStackTrace();
            _logger.info("QIUContentUtil.java loadQIUTopics() Exception is " + e);
        }
        _logger.info("QIUContentUtil.java loadQIUTopics() qiuTopicList = " + qiuTopicList);
        return qiuTopicList;
    }

    private static QIUTopicBean getQIUTopicBean(ContentFolderBean contentFolder, Boolean isFromMainPage) {
        QIUTopicBean qiuTopicBean = new QIUTopicBean();
        qiuTopicBean.setCollectionPath(contentFolder.getCollectionPath());
        qiuTopicBean.setParentCollectionId(contentFolder.getParentCollectionId());
        qiuTopicBean.setCollectionId(contentFolder.getCollectionId());
        qiuTopicBean.setTopicName(contentFolder.getCollectionName());

        List<String[]> sectionList = new ArrayList<String[]>();
        String[] sec1DtlArr = null;
        if (contentFolder.getTitleandPubDate() != null) {
            sec1DtlArr = new String[] { contentFolder.getTitleandPubDate(), contentFolder.getFaculty() };
            sectionList.add(sec1DtlArr);
        }
        if (contentFolder.getFaculty() != null && !isFromMainPage) {
            sec1DtlArr = new String[] { contentFolder.getFaculty(), "" };
            sectionList.add(sec1DtlArr);
        }
        if (contentFolder.getBackgroundHeader() != null && !isFromMainPage) {
            sec1DtlArr = new String[] { contentFolder.getBackgroundHeader(), contentFolder.getBackgroundContent() };
            sectionList.add(sec1DtlArr);
        }
        if (contentFolder.getLearningObjectivesHeader() != null) {
            sec1DtlArr = new String[] { contentFolder.getLearningObjectivesHeader(), contentFolder.getLearningObjectivesContent() };
            sectionList.add(sec1DtlArr);
        }
        if (contentFolder.getKeyTakeAwaysHeader() != null && !isFromMainPage) {
            sec1DtlArr = new String[] { contentFolder.getKeyTakeAwaysHeader(), contentFolder.getKeyTakeAwaysContent() };
            sectionList.add(sec1DtlArr);
        }

        qiuTopicBean.setSectionList(sectionList);
        return qiuTopicBean;
    }

    public static QIUTopicBean loadQIUTopicBean(String qiuTopcCollectionId) {
        QIUTopicBean qiuTopicBean = null;
        _logger.info("QIUContentUtil.java loadQIUTopicBean() starts executing qiuTopcCollectionId = " + qiuTopcCollectionId);
        WCContentUtil csUtil = getWCContentUtil();
        ContentFolderBean qiuTopicFolderBean = null;
        try {
            qiuTopicFolderBean = csUtil.getQIUFolderInfoFromCollectionId(qiuTopcCollectionId);
            qiuTopicBean = getQIUTopicBean(qiuTopicFolderBean, false);
        } catch (Exception e) {
            e.printStackTrace();
            _logger.info("QIUContentUtil.java loadQIUTopicBean() Exception is " + e);
        }
        _logger.info("QIUContentUtil.java loadQIUTopicBean() qiuTopicBean = " + qiuTopicBean);
        return qiuTopicBean;
    }

    public static List<QIUTopicCategoryBean> getQiuTopicCategoryList(String qiuTopcCollectionId) {
        _logger.info("QIUContentUtil.java getQiuTopicCategoryList() starts executing qiuTopcCollectionId = " + qiuTopcCollectionId);
        List<QIUTopicCategoryBean> qiuTopicCategoryList = new ArrayList<QIUTopicCategoryBean>();
        WCContentUtil csUtil = getWCContentUtil();
        List<ContentFolderBean> qiuTopicCategoryFolders = null;
        QIUTopicCategoryBean qiuTopicCategoryBean = null;
        try {
            qiuTopicCategoryFolders = csUtil.getSubFolders(qiuTopcCollectionId);
            _logger.info("QIUContentUtil.java getQiuTopicCategoryList() qiuTopicFolders = " + qiuTopicCategoryFolders);
            for (ContentFolderBean contentFolder : qiuTopicCategoryFolders) {
                qiuTopicCategoryBean = getQIUTopicCategoryBean(contentFolder);
                loadQIUTopicCategoryResources(csUtil, qiuTopicCategoryBean);
                qiuTopicCategoryList.add(qiuTopicCategoryBean);
            }

        } catch (Exception e) {
            e.printStackTrace();
            _logger.info("QIUContentUtil.java getQiuTopicCategoryList() Exception is " + e);
        }
        _logger.info("QIUContentUtil.java getQiuTopicCategoryList() qiuTopicCategoryList = " + qiuTopicCategoryList);
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
            _logger.info("ResourceContentUtil.java loadResourcesListOfTopicFromContentServer() Exception is " + e);
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
        List<QIUTopicBean> qiuTopicList = loadQIUTopics("/WebCenterSpaces-Root/QIOCollaboration/QIU/");
        _logger.info("qiuTopicList =" + qiuTopicList);
        for (QIUTopicBean qiuTopic : qiuTopicList) {
            _logger.info("qiuTopic.getCollectionId() =" + qiuTopic.getCollectionId());
            List<QIUTopicCategoryBean> qiuTopicCategoryList = getQiuTopicCategoryList(qiuTopic.getCollectionId());
            _logger.info("contentList =" + qiuTopicCategoryList);
            QIUTopicBean qIUTopicBean =  loadQIUTopicBean(qiuTopic.getCollectionId());
            _logger.info("contentList =" + qIUTopicBean);
        }
        
    }
}
