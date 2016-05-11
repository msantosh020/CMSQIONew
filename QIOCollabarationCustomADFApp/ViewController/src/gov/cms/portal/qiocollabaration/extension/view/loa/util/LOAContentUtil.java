package gov.cms.portal.qiocollabaration.extension.view.loa.util;

import gov.cms.portal.qiocollabaration.content.beans.ContentFolderBean;
import gov.cms.portal.qiocollabaration.content.beans.ContentItemBean;
import gov.cms.portal.qiocollabaration.content.util.WCContentUtil;
import gov.cms.portal.qiocollabaration.extension.view.loa.beans.LOAModuleBean;
import gov.cms.portal.qiocollabaration.extension.view.loa.beans.LOAModuleCategoryBean;
import gov.cms.portal.qiocollabaration.extension.view.loa.beans.LOAPartBean;

import gov.cms.portal.qiocollabaration.extension.view.resources.beans.ResourceBean;

import java.util.ArrayList;
import java.util.List;

public class LOAContentUtil {
    public LOAContentUtil() {
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

    public static List<LOAPartBean> getLOAParts(String loaCollectionPath) {
        return getLOAPartsByCollectionId(null);
    }

    private static List<LOAPartBean> getLOAPartsByCollectionId(String loaCollectionId) {
        List<LOAPartBean> partList = new ArrayList<LOAPartBean>();
        partList.add(new LOAPartBean("Part 1 : LOA Fundamentals", 100));
        partList.add(new LOAPartBean("Part 2 : Advanced Topics", 60));
        return partList;
    }


    public static LOAModuleBean getLOAModule(String loaModuleCollectionPath) {
        LOAModuleBean loaModule = new LOAModuleBean();
        loaModule.setModuleName("Module 111111");
        loaModule.setModuleDesc("Module DESCCCCC 111111");
        loaModule.setModuleTitleDesc("This is title desc.............");
        List<LOAModuleCategoryBean> moduleCategoryList = new ArrayList<LOAModuleCategoryBean>();

        LOAModuleCategoryBean c1 = new LOAModuleCategoryBean();
        c1.setCategoryName("Activities");
        c1.setCategoryType("TEXT");
        List<ResourceBean> resources = new ArrayList<ResourceBean>();
        for (int i = 0; i < 5; i++) {
            resources.add(new ResourceBean("R Title- " + i, "R DDDDDDDDD- " + i));
        }
        c1.setResources(resources);
        moduleCategoryList.add(c1);


        LOAModuleCategoryBean c2 = new LOAModuleCategoryBean();
        c2.setCategoryName("Modules and Materials");
        c2.setCategoryType("LINKS");
        List<ResourceBean> resources2 = new ArrayList<ResourceBean>();
        for (int i = 0; i < 5; i++) {
            resources2.add(new ResourceBean("R Title- " + i, "R DDDDDDDDD- " + i));
        }
        c2.setResources(resources2);
        moduleCategoryList.add(c2);


        LOAModuleCategoryBean c3 = new LOAModuleCategoryBean();
        c3.setCategoryName("Lecture and Recordings");
        c3.setCategoryType("VIDOES");
        List<ResourceBean> resources3 = new ArrayList<ResourceBean>();
        for (int i = 0; i < 5; i++) {
            resources3.add(new ResourceBean("R Title- " + i, "R DDDDDDDDD- " + i));
        }
        c3.setResources(resources3);
        moduleCategoryList.add(c3);

        loaModule.setModuleCategoryList(moduleCategoryList);

        return loaModule;
    }

    /*
    public static List<LOATopicBean> loadLOATopics(String loaCSParentFolderPath) {
        System.out.println("LOAContentUtil.java loadLOATopics() starts executing loaCSParentFolderPath = " + loaCSParentFolderPath);
        WCContentUtil csUtil = getWCContentUtil();
        List<ContentFolderBean> loaTopicFolders = null;
        List<LOATopicBean> loaTopicList = new ArrayList<LOATopicBean>();
        LOATopicBean loaTopicBean = null;
        try {
            String collectionId = csUtil.getFolderCollectionId(loaCSParentFolderPath);
            System.out.println("LOAContentUtil.java loadLOATopics() collectionId = " + collectionId);
            loaTopicFolders = csUtil.getSubFolders(collectionId);
            System.out.println("LOAContentUtil.java loadLOATopics() loaTopicFolders = " + loaTopicFolders);
            for (ContentFolderBean contentFolder : loaTopicFolders) {
                loaTopicBean = getLOATopicBean(contentFolder);
                loaTopicList.add(loaTopicBean);
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("LOAContentUtil.java loadLOATopics() Exception is " + e);
        }
        System.out.println("LOAContentUtil.java loadLOATopics() loaTopicList = " + loaTopicList);
        return loaTopicList;
    }

    private static LOATopicBean getLOATopicBean(ContentFolderBean contentFolder) {
        LOATopicBean loaTopicBean = new LOATopicBean();
        loaTopicBean.setCollectionPath(contentFolder.getCollectionPath());
        loaTopicBean.setParentCollectionId(contentFolder.getParentCollectionId());
        loaTopicBean.setCollectionId(contentFolder.getCollectionId());
        loaTopicBean.setTopicName(contentFolder.getCollectionName());
        return loaTopicBean;
    }

    public static List<LOAModuleCategoryBean> getLoaTopicCategoryList(String loaTopcCollectionId) {
        System.out.println("LOAContentUtil.java getLoaTopicCategoryList() starts executing loaTopcCollectionId = " + loaTopcCollectionId);
        List<LOAModuleCategoryBean> loaTopicCategoryList = new ArrayList<LOAModuleCategoryBean>();
        WCContentUtil csUtil = getWCContentUtil();
        List<ContentFolderBean> loaTopicCategoryFolders = null;
        LOAModuleCategoryBean loaTopicCategoryBean = null;
        try {
            loaTopicCategoryFolders = csUtil.getSubFolders(loaTopcCollectionId);
            System.out.println("LOAContentUtil.java getLoaTopicCategoryList() loaTopicFolders = " + loaTopicCategoryFolders);
            for (ContentFolderBean contentFolder : loaTopicCategoryFolders) {
                loaTopicCategoryBean = getLOATopicCategoryBean(contentFolder);
                loadLOATopicCategoryResources(csUtil, loaTopicCategoryBean);
                loaTopicCategoryList.add(loaTopicCategoryBean);
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("LOAContentUtil.java getLoaTopicCategoryList() Exception is " + e);
        }
        System.out.println("LOAContentUtil.java getLoaTopicCategoryList() loaTopicCategoryList = " + loaTopicCategoryList);
        return loaTopicCategoryList;
    }

    private static LOAModuleCategoryBean getLOATopicCategoryBean(ContentFolderBean contentFolder) {
        LOAModuleCategoryBean loaTopicCategoryBean = new LOAModuleCategoryBean();
        loaTopicCategoryBean.setCollectionPath(contentFolder.getCollectionPath());
        loaTopicCategoryBean.setParentCollectionId(contentFolder.getParentCollectionId());
        loaTopicCategoryBean.setCollectionId(contentFolder.getCollectionId());
        loaTopicCategoryBean.setCategoryName(contentFolder.getCollectionName());
        return loaTopicCategoryBean;
    }

    private static void loadLOATopicCategoryResources(WCContentUtil csUtil, LOAModuleCategoryBean loaTopicCategoryBean) {
        List<ContentItemBean> categoryContentItems = null;
        try {
            categoryContentItems = csUtil.getFolderContentItemsByCollectionID(loaTopicCategoryBean.getCollectionId());
            loaTopicCategoryBean.setResources(getResources(categoryContentItems));
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
        List<LOATopicBean> loaTopicList = loadLOATopics("/WebCenterSpaces-Root/LOA/");
        System.out.println("loaTopicList =" + loaTopicList);
        for (LOATopicBean loaTopic : loaTopicList) {
            System.out.println("loaTopic.getCollectionId() =" + loaTopic.getCollectionId());
            List<LOAModuleCategoryBean> loaTopicCategoryList = getLoaTopicCategoryList(loaTopic.getCollectionId());
            System.out.println("contentList =" + loaTopicCategoryList);
        }
    }
    */
}
