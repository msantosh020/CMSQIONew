package gov.cms.portal.qiocollabaration.extension.view.loa.util;

import gov.cms.portal.qiocollabaration.content.beans.ContentFolderBean;
import gov.cms.portal.qiocollabaration.content.beans.ContentItemBean;
import gov.cms.portal.qiocollabaration.content.util.WCContentUtil;
import gov.cms.portal.qiocollabaration.extension.view.loa.beans.LOAModuleBean;
import gov.cms.portal.qiocollabaration.extension.view.loa.beans.LOAModuleCategoryBean;
import gov.cms.portal.qiocollabaration.extension.view.loa.beans.LOAPartBean;

import gov.cms.portal.qiocollabaration.extension.view.loa.beans.LOAPartTypeBean;
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

    public static List<LOAPartBean> getLOAParts(String loaCSParentFolderPath) {
        //return getLOAPartsByCollectionId(null);
        System.out.println("LOAContentUtil.java getLOAParts() starts executing loaCSParentFolderPath = " + loaCSParentFolderPath);
        WCContentUtil csUtil = getWCContentUtil();
        List<ContentFolderBean> loaPartFolders = null;
        List<LOAPartBean> loaPartList = new ArrayList<LOAPartBean>();
        LOAPartBean loaPartBean = null;
        try {
            String collectionId = csUtil.getFolderCollectionId(loaCSParentFolderPath);
            System.out.println("LOAContentUtil.java getLOAParts() collectionId = " + collectionId);
            loaPartFolders = csUtil.getSubFolders(collectionId);
            System.out.println("LOAContentUtil.java getLOAParts() loaPartFolders = " + loaPartFolders);
            for (ContentFolderBean contentFolder : loaPartFolders) {
                loaPartBean = getLOAPartBean(contentFolder);
                loadLOAPartTypes(loaPartBean);
                loaPartList.add(loaPartBean);
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("LOAContentUtil.java loadLOATopics() Exception is " + e);
        }
        System.out.println("LOAContentUtil.java loadLOATopics() loaTopicList = " + loaPartList);
        return loaPartList;
    }

    private static LOAPartBean getLOAPartBean(ContentFolderBean loaPartFolder) {
        LOAPartBean loaPartBean = new LOAPartBean();
        loaPartBean.setPartName(loaPartFolder.getCollectionName());
        loaPartBean.setCollectionId(loaPartFolder.getCollectionId());
        loaPartBean.setCollectionPath(loaPartFolder.getCollectionPath());
        return loaPartBean;
    }

    private static void loadLOAPartTypes(LOAPartBean loaPartBean) {
        System.out.println("LOAContentUtil.java loadLOAPartTypes() starts executing loaPartBean.getCollectionId() = " + loaPartBean.getCollectionId());
        WCContentUtil csUtil = getWCContentUtil();
        List<ContentFolderBean> loaPartModuleFolders = null;
        List<LOAPartTypeBean> loaPartTypeList = new ArrayList<LOAPartTypeBean>();
        LOAPartTypeBean loaPartTypeBean = null;
        try {
            loaPartModuleFolders = csUtil.getSubFolders(loaPartBean.getCollectionId());
            System.out.println("LOAContentUtil.java loadLOAPartTypes() loaPartModuleFolders = " + loaPartModuleFolders);
            for (ContentFolderBean contentFolder : loaPartModuleFolders) {
                loaPartTypeBean = getLOoAPartTypeBean(contentFolder);
                loadLOAPartModules(loaPartTypeBean);
                loaPartTypeList.add(loaPartTypeBean);
            }
            loaPartBean.setLoaPartTypeList(loaPartTypeList);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("LOAContentUtil.java loadLOAPartTypes() Exception is " + e);
        }
        System.out.println("LOAContentUtil.java loadLOAPartTypes() loaPartModuleList = " + loaPartTypeList);
    }

    private static LOAPartTypeBean getLOoAPartTypeBean(ContentFolderBean loaPartFolder) {
        LOAPartTypeBean loaPartTypeBean = new LOAPartTypeBean();
        loaPartTypeBean.setPartTypeName(loaPartFolder.getCollectionName());
        loaPartTypeBean.setCollectionId(loaPartFolder.getCollectionId());
        loaPartTypeBean.setCollectionPath(loaPartFolder.getCollectionPath());
        return loaPartTypeBean;
    }

    private static void loadLOAPartModules(LOAPartTypeBean loaPartTypeBean) {
        System.out.println("LOAContentUtil.java loadLOAPartModules() starts executing loaPartBean.getCollectionId() = " + loaPartTypeBean.getCollectionId());
        WCContentUtil csUtil = getWCContentUtil();
        List<ContentFolderBean> loaPartModuleFolders = null;
        List<LOAModuleBean> loaPartModuleList = new ArrayList<LOAModuleBean>();
        LOAModuleBean loaPartModuleBean = null;
        try {
            loaPartModuleFolders = csUtil.getLOASubFolders(loaPartTypeBean.getCollectionId());
            System.out.println("LOAContentUtil.java loadLOAPartModules() loaPartModuleFolders = " + loaPartModuleFolders);
            for (ContentFolderBean contentFolder : loaPartModuleFolders) {
                loaPartModuleBean = getLOAPartModuleBean(contentFolder, true);
                loaPartModuleList.add(loaPartModuleBean);
            }
            loaPartTypeBean.setModuleList(loaPartModuleList);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("LOAContentUtil.java loadLOATopics() Exception is " + e);
        }
        System.out.println("LOAContentUtil.java loadLOATopics() loaPartModuleList = " + loaPartModuleList);
    }

    private static LOAModuleBean getLOAPartModuleBean(ContentFolderBean contentFolder,Boolean isFromMainPage ) {
        LOAModuleBean loaPartModuleBean = new LOAModuleBean();
        loaPartModuleBean.setModuleName(contentFolder.getCollectionName());
        loaPartModuleBean.setCollectionId(contentFolder.getCollectionId());
        loaPartModuleBean.setCollectionPath(contentFolder.getCollectionPath());

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

        loaPartModuleBean.setSectionList(sectionList);

        return loaPartModuleBean;
    }

    public static LOAModuleBean getLOAModule(String loaModuleCollectionId) {
        System.out.println("LOAContentUtil.java getLOAModule() starts executing loaModuleCollectionId = " + loaModuleCollectionId);
        WCContentUtil csUtil = getWCContentUtil();
        LOAModuleBean loaPartModuleBean = null;
        try {
            ContentFolderBean contentFolder = csUtil.getLOAFolderInfoFromCollectionId(loaModuleCollectionId);
            loaPartModuleBean = getLOAPartModuleBean(contentFolder,false);
            loadLOAModule(loaPartModuleBean);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("LOAContentUtil.java loadLOATopics() Exception is " + e);
        }
        System.out.println("LOAContentUtil.java getLOAModule() loaPartModuleBean = " + loaPartModuleBean);
        return loaPartModuleBean;
    }

    private static void loadLOAModule(LOAModuleBean loaPartModuleBean) {
        System.out.println("LOAContentUtil.java loadLOAModule() starts executing loaPartModuleBean.getCollectionId() = " + loaPartModuleBean.getCollectionId());
        WCContentUtil csUtil = getWCContentUtil();
        List<ContentFolderBean> loaModuleCategoryFolders = null;
        List<LOAModuleCategoryBean> loaModuleCategoryList = new ArrayList<LOAModuleCategoryBean>();
        LOAModuleCategoryBean loaModuleCategoryBean = null;
        try {
            loaModuleCategoryFolders = csUtil.getSubFolders(loaPartModuleBean.getCollectionId());
            System.out.println("LOAContentUtil.java loadLOAModule() loaModuleCategoryFolders = " + loaModuleCategoryFolders);
            for (ContentFolderBean contentFolder : loaModuleCategoryFolders) {
                loaModuleCategoryBean = getLOAModuleCategoryBean(contentFolder);
                loadLOAModuleCategoryResources(csUtil, loaModuleCategoryBean);
                loaModuleCategoryList.add(loaModuleCategoryBean);
            }
            loaPartModuleBean.setModuleCategoryList(loaModuleCategoryList);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("LOAContentUtil.java loadLOAModule() Exception is " + e);
        }
        System.out.println("LOAContentUtil.java loadLOAModule() loaPartModuleList = " + loaModuleCategoryList);
    }

    private static LOAModuleCategoryBean getLOAModuleCategoryBean(ContentFolderBean loaPartFolder) {
        LOAModuleCategoryBean loaModuleCategoryBean = new LOAModuleCategoryBean();
        loaModuleCategoryBean.setCategoryName(loaPartFolder.getCollectionName());
        loaModuleCategoryBean.setCollectionId(loaPartFolder.getCollectionId());
        loaModuleCategoryBean.setCollectionPath(loaPartFolder.getCollectionPath());
        //loaModuleCategoryBean.setCategoryType(categoryType);
        return loaModuleCategoryBean;
    }

    private static void loadLOAModuleCategoryResources(WCContentUtil csUtil, LOAModuleCategoryBean loaTopicCategoryBean) {
        List<ContentItemBean> categoryContentItems = null;
        try {
            categoryContentItems = csUtil.getFolderContentItemsByCollectionID(loaTopicCategoryBean.getCollectionId());
            loaTopicCategoryBean.setResources(getResources(categoryContentItems));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("LOAContentUtil.java loadLOAModuleCategoryResources() Exception is " + e);
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
        //Testig main page
        /*
        List<LOAPartBean> loaPartList = getLOAParts("/WebCenterSpaces-Root/QIOCollaboration/LOA/LOA 1.0 (2015)");
        System.out.println("loaTopicList =" + loaPartList);
        for (LOAPartBean loaPartBean : loaPartList) {
            System.out.println("loaTopic.getCollectionId() =" + loaPartBean.getCollectionId());
            //List<LOAModuleCategoryBean> loaTopicCategoryList = getLoaTopicCategoryList(loaPartBean.getCollectionId());
            for (LOAPartTypeBean loaPartTypeBean : loaPartBean.getLoaPartTypeList()) {
                System.out.println("----   Part Types =" + loaPartTypeBean.getModuleList() + " --> CollectionId =" + loaPartTypeBean.getCollectionId());
                for (LOAModuleBean loaModuleBean : loaPartTypeBean.getModuleList()) {
                    System.out.println("------------ ModuleName =" + loaModuleBean.getModuleName() + " --> CollectionId =" + loaModuleBean.getCollectionId());
                }
            }
        }
        */
        // Testing module page

        String loaModuleCollectionId = "709937743070003430";
        LOAModuleBean loaModuleBean = getLOAModule(loaModuleCollectionId);
        for (LOAModuleCategoryBean loaModuleCategoryBean : loaModuleBean.getModuleCategoryList()) {
            System.out.println("ModuleCategoryName =" + loaModuleCategoryBean.getCategoryName() + "CollectionId =" + loaModuleCategoryBean.getCollectionId());
            for (ResourceBean resourceBean : loaModuleCategoryBean.getResources()) {
                System.out.println("ModuleName =" + resourceBean.getResourceTitle() + " --> CollectionId =" + resourceBean.getResourceDescription());
            }
        }

    }
}
