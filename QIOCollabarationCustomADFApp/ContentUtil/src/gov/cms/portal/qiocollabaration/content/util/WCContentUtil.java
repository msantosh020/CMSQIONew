package gov.cms.portal.qiocollabaration.content.util;


import gov.cms.portal.qiocollabaration.content.beans.ContentFolderBean;
import gov.cms.portal.qiocollabaration.content.beans.ContentItemBean;
import gov.cms.portal.qiocollabaration.content.exception.CustomException;

import java.io.InputStream;
import java.io.Serializable;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;

import oracle.stellent.ridc.IdcClient;
import oracle.stellent.ridc.IdcClientException;
import oracle.stellent.ridc.IdcClientManager;
import oracle.stellent.ridc.IdcContext;
import oracle.stellent.ridc.model.DataBinder;
import oracle.stellent.ridc.model.DataObject;
import oracle.stellent.ridc.model.DataResultSet;
import oracle.stellent.ridc.model.TransferFile;
import oracle.stellent.ridc.protocol.ServiceException;
import oracle.stellent.ridc.protocol.ServiceResponse;

import oracle.webcenter.doclib.model.DocLibUtils;


public class WCContentUtil implements Serializable {

    @SuppressWarnings("compatibility:-1915941260478322630")
    private static final long serialVersionUID = 7011037583889654793L;
    public static DocLibUtils utils = new DocLibUtils();

    public WCContentUtil() {
        super();

        try {
            IdcContext userContext = null;
            String connName = utils.getDefaultConnectionName();
            userContext = utils.getDefaultIdcContext(connName);
            connName = utils.getDefaultConnectionName();
            idcClient = utils.getIdcClient(connName);

            //userContext = new IdcContext("appadmin");
            userContext = utils.getDefaultIdcContext(connName);

        } catch (IdcClientException ice) {
            ice.printStackTrace();
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    public WCContentUtil(String idcUrl, String userName) {
        super();
        try {
            IdcContext userContext = null;
            IdcClientManager manager = new IdcClientManager();
            idcClient = manager.createClient(idcUrl);
            //dataBinder = idcClient.createBinder();
            userContext = new IdcContext(userName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static IdcClient idcClient;

    // IDC SERVICES and RESULTS CONSTANTS
    private static final String SUBFOLDERS_SERVICE = "COLLECTION_GET_COLLECTIONS";
    private static final String UNIV_CHECKIN_SERVICE = "CHECKIN_UNIVERSAL";
    private static final String CHECKOUT_BY_DOCNAME = "CHECKOUT_BY_NAME";
    private static final String UNDO_CHECKOUT_BY_DOCNAME = "UNDO_CHECKOUT_BY_NAME";
    private static final String GET_SEARCH_RESULTS = "GET_SEARCH_RESULTS";
    private static final String IDCSERVICE = "IdcService";
    private static final String hasCollectionPath = "hasCollectionPath";
    private static final String dCollectionPath = "dCollectionPath";
    private static final String dCollectionName = "dCollectionName";
    private static final String dCollectionID = "dCollectionID";
    private static final String TRUE = "true";
    private static final String COLLECTIONS_RESULTSET = "COLLECTIONS";
    private static final SimpleDateFormat UCM_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final SimpleDateFormat UCM_DATE_FORMAT_MILLIS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    private static final String COLLECTION_CONTENT_SERVICE = "COLLECTION_GET_CONTENTS";
    private static final String hasCollectionID = "hasCollectionID";
    private static final String CONTENTS_RESULTSET = "CONTENTS";

    // CONTENT METADATA CONSTANTS
    private static final String dDocID = "dDocID";
    private static final String dDocAccount = "dDocAccount";
    private static final String dDocOwner = "dDocOwner";
    private static final String dCollectionOwner = "dCollectionOwner";
    private static final String dDocType = "dDocType";
    private static final String dFileSize = "dFileSize";
    private static final String dExtension = "dExtension";
    private static final String dWebExtension = "dWebExtension";
    private static final String dWebFilename = "dWebFilename";
    private static final String dWebURL = "dWebURL";
    private static final String URL = "URL";
    private static final String dFormat = "dFormat";
    private static final String dStatus = "dStatus";
    private static final String dRevisionID = "dRevisionID";
    private static final String xCollectionID = "xCollectionID";
    private static final String dDocName = "dDocName";
    private static final String dDocTitle = "dDocTitle";
    private static final String dOriginalName = "dOriginalName";
    private static final String dDocLastModifiedDate = "dDocLastModifiedDate";
    private static final String xWebsites = "xWebsites";
    private static final String dDocAuthor = "dDocAuthor";
    private static final String dDocCreator = "dDocCreator";
    private static final String dCollectionCreator = "dCollectionCreator";
    private static final String dDocLastModifier = "dDocLastModifier";
    private static final String dCollectionModifier = "dCollectionModifier";
    private static final String dSecurityGroup = "dSecurityGroup";
    private static final String dInDate = "dInDate";
    private static final String dDocCreatedDate = "dDocCreatedDate";
    private static final String dCreateDate = "dCreateDate";
    private static final String dLastModifiedDate = "dLastModifiedDate";
    private static final String dParentCollectionID = "dParentCollectionID";
    private static final String xComments = "xComments";
    private static final String dId = "dID";
    private static final String dRevLabel = "dRevLabel";

    // custom metadata fields
    private static final String xContentCategory = "xContentCategory";
    private static final String xDescription = "xDescription";
    private static final String xFolderType = "xFolderType";
    private static final String xContentType = "xContentType";
    private static final String PARTNER_PORTAL_PATH = "/Contribution Folders/PartnerPortal";
    private static final String IMAGE_LIBRARY = "Image Library";
    private static final int MAX_ITEMS_IN_A_FOLDER = 1000;
    public static final String ITEM_CONTENT_ID_PREFIX = "ITEM-";

    public List<ContentItemBean> getFolderContentItemsByCollectionPath(String collectionPath) throws IdcClientException, ParseException, NamingException {
        //logger.fine("Start of getFolderContentItemsByCollectionPath");
        System.out.println("WCContentUtil.java getFolderContentItemsByCollectionPath() starts executing collectionPath= "+collectionPath);
        List<ContentItemBean> contentItems = null;
        ServiceResponse response = null;
        DataBinder dataBinder = null;
        if (collectionPath != null) {

            try {
                dataBinder = idcClient.createBinder();
                dataBinder.putLocal(IDCSERVICE, COLLECTION_CONTENT_SERVICE);
                dataBinder.putLocal(hasCollectionPath, TRUE);
                dataBinder.putLocal(dCollectionPath, collectionPath);
//                IdcContext userContext = null;
//                String connName = utils.getDefaultConnectionName();
//                userContext = utils.getDefaultIdcContext(connName);

               IdcContext userContext = new IdcContext("weblogic");

                response = idcClient.sendRequest(userContext, dataBinder);
                DataBinder serverBinder = response.getResponseAsBinder();

                DataResultSet resultSet = serverBinder.getResultSet(CONTENTS_RESULTSET);
                contentItems = new ArrayList<ContentItemBean>();
                for (DataObject obj : resultSet.getRows()) {
                    ContentItemBean item = getPopulatedContentItem(obj);
                    contentItems.add(item);
                }
            } catch (IdcClientException e) {
                //logger.severe("Error in getFolderContentItemsByCollectionPath - " + e);
                e.printStackTrace();
                throw e;
            } catch (ParseException e) {
                //logger.severe("Error in getFolderContentItemsByCollectionPath - " + e);
                e.printStackTrace();
                throw e;
            } 
//            catch (NamingException e) {
//                //logger.severe("Error in getFolderContentItemsByCollectionPath - " + e);
//                e.printStackTrace();
//                throw e;
//            }            
            finally {
                if (response != null) {
                    response.close();
                }
            }
        }
        //logger.fine("End of getFolderContentItemsByCollectionPath");
        System.out.println("WCContentUtil.java getFolderContentItemsByCollectionPath() contentItems= "+contentItems);
        return contentItems;
    }
    
    public List<ContentItemBean> getFolderContentItemsByCollectionID(String parentFolderID) throws IdcClientException, ParseException, NamingException {
        //logger.fine("Start of getFolderContentItemsByCollectionPath");
        System.out.println("WCContentUtil.java getFolderContentItemsByCollectionID() starts executing parentFolderID= "+parentFolderID);
        List<ContentItemBean> contentItems = null;
        ServiceResponse response = null;
        DataBinder dataBinder = null;
        if (parentFolderID != null) {

            try {
                dataBinder = idcClient.createBinder();
                dataBinder.putLocal(IDCSERVICE, COLLECTION_CONTENT_SERVICE);
                dataBinder.putLocal(hasCollectionID, TRUE);
                dataBinder.putLocal(dCollectionID, parentFolderID);
    //                IdcContext userContext = null;
    //                String connName = utils.getDefaultConnectionName();
    //                userContext = utils.getDefaultIdcContext(connName);

               IdcContext userContext = new IdcContext("weblogic");

                response = idcClient.sendRequest(userContext, dataBinder);
                DataBinder serverBinder = response.getResponseAsBinder();

                DataResultSet resultSet = serverBinder.getResultSet(CONTENTS_RESULTSET);
                contentItems = new ArrayList<ContentItemBean>();
                for (DataObject obj : resultSet.getRows()) {
                    ContentItemBean item = getPopulatedContentItem(obj);
                    contentItems.add(item);
                }
            } catch (IdcClientException e) {
                //logger.severe("Error in getFolderContentItemsByCollectionPath - " + e);
                e.printStackTrace();
                throw e;
            } catch (ParseException e) {
                //logger.severe("Error in getFolderContentItemsByCollectionPath - " + e);
                e.printStackTrace();
                throw e;
            } 
    //            catch (NamingException e) {
    //                //logger.severe("Error in getFolderContentItemsByCollectionPath - " + e);
    //                e.printStackTrace();
    //                throw e;
    //            }
            finally {
                if (response != null) {
                    response.close();
                }
            }
        }
        //logger.fine("End of getFolderContentItemsByCollectionPath");
        System.out.println("WCContentUtil.java getFolderContentItemsByCollectionID() contentItems= "+contentItems);
        return contentItems;
    }

    /**
     * Reusable method to get populated contentItems from the binder's dataObject
     * @param obj
     * @return
     */
    private ContentItemBean getPopulatedContentItem(DataObject obj) throws ParseException {
        ContentItemBean item = new ContentItemBean();
        item.setDocId(obj.get(dDocID));
        item.setAccount(obj.get(dDocAccount));
        item.setOwner(obj.get(dDocOwner));
        item.setDocType(obj.get(dDocType));
        item.setFileSize(obj.get(dFileSize) != null ? new Long(obj.get(dFileSize)) : null);
        item.setExtension(obj.get(dExtension));
        item.setWebExtension(obj.get(dWebExtension));
        item.setWebFileName(obj.get(dWebFilename));
        item.setNativeWebURL(obj.get(URL));
        item.setDocFormat(obj.get(dFormat));
        item.setStatus(obj.get(dStatus));
        item.setRevisionNumber(obj.get(dRevisionID) != null ? new Long(obj.get(dRevisionID)) : null);
        item.setCollectionId(obj.get(xCollectionID));
        item.setDocInternalName(obj.get(dDocName));
        item.setTitle(obj.get(dDocTitle));
        item.setDocNativeName(obj.get(dOriginalName));
        item.setSecurityGroup(obj.get(dSecurityGroup));
        item.setDescription(obj.get(xDescription));
        item.setContentCategory(obj.get(xContentCategory));

        if (obj.get(dDocLastModifiedDate) != null) {
            try {
                item.setLastModifiedDate(UCM_DATE_FORMAT.parse(obj.get(dDocLastModifiedDate)));

            } catch (ParseException ex) {
                if (obj.get(dDocLastModifiedDate).contains("ts")) {
                    String lastModDate =
                        obj.get(dDocLastModifiedDate).substring(obj.get(dDocLastModifiedDate).indexOf("'") + 1, obj.get(dDocLastModifiedDate).indexOf("'", obj.get(dDocLastModifiedDate).indexOf("'") +
                                                                                                                                                      1) + 1);
                    item.setLastModifiedDate(UCM_DATE_FORMAT_MILLIS.parse(lastModDate));
                }
            }
        }

        if (obj.get(dDocCreatedDate) != null) {
            try {
                item.setCreationDate(UCM_DATE_FORMAT.parse(obj.get(dDocCreatedDate)));
            } catch (ParseException ex) {
                if (obj.get(dDocCreatedDate).contains("ts")) {
                    String crtDate =
                        obj.get(dDocCreatedDate).substring(obj.get(dDocCreatedDate).indexOf("'") + 1, obj.get(dDocCreatedDate).indexOf("'", obj.get(dDocCreatedDate).indexOf("'") + 1) + 1);
                    item.setCreationDate(UCM_DATE_FORMAT_MILLIS.parse(crtDate));
                }
            }
        }

        if (obj.get(dInDate) != null) {
            try {
                item.setReleaseDate(UCM_DATE_FORMAT.parse(obj.get(dInDate)));
            } catch (ParseException ex) {
                if (obj.get(dInDate).contains("ts")) {
                    String releaseDate = obj.get(dInDate).substring(obj.get(dInDate).indexOf("'") + 1, obj.get(dInDate).indexOf("'", obj.get(dInDate).indexOf("'") + 1) + 1);
                    item.setReleaseDate(UCM_DATE_FORMAT_MILLIS.parse(releaseDate));
                }
            }
        }

        item.setComments(obj.get(xComments));
        item.setAuthor(obj.get(dDocAuthor));
        item.setCreatedBy(obj.get(dDocCreator));
        item.setLastModifiedBy(obj.get(dDocLastModifier));
        item.setDId(obj.get(dId));
        item.setRevisionLabel(obj.get(dRevLabel));
        item.setTagNames(obj.get("xsbiApprovalType"));
        item.setTopicsNames(obj.get("xsbiCreatedBy"));
        
        return item;
    }

    public InputStream getContentItemStream(String contentId) {
        if (contentId == null)
            return null;
        DataBinder dataBinder = null;
        dataBinder = idcClient.createBinder();
        IdcContext userContext = null;
        InputStream is = null;
        dataBinder.putLocal("IdcService", "GET_FILE");
        dataBinder.putLocal("dDocName", contentId);
        dataBinder.putLocal("RevisionSelectionMethod", "LatestReleased");
        try {
            String connName = utils.getDefaultConnectionName();
            userContext = utils.getDefaultIdcContext(connName);
            ServiceResponse response = idcClient.sendRequest(userContext, dataBinder);
            if (ServiceResponse.ResponseType.STREAM.equals(response.getResponseType()))
                is = response.getResponseStream();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return is;
    }


    public ContentItemBean checkInFile(InputStream fileStream, long fileSize, ContentItemBean newItem) throws IdcClientException, ParseException, CustomException, NamingException {
        //logger.fine("Start of checkInFile");
        ContentItemBean savedContentItem = null;
        ServiceResponse response = null;
        ContentFolderBean rootFolder = null;
        DataBinder dataBinder = null;
        DataBinder serverBinder = null;

        try {
            IdcContext userContext = null;
            String connName = utils.getDefaultConnectionName();
            userContext = utils.getDefaultIdcContext(connName);
            rootFolder = getFolderInfoFromPath(PARTNER_PORTAL_PATH);

            dataBinder = idcClient.createBinder();
            dataBinder.putLocal(IDCSERVICE, UNIV_CHECKIN_SERVICE);

            if (newItem.getDocInternalName() != null)
                dataBinder.putLocal(dDocName, newItem.getDocInternalName());

            //            if (newItem.getAuthor() != null)
            //                dataBinder.putLocal(dDocAuthor, newItem.getAuthor());
            //            else
            dataBinder.putLocal(dDocAuthor, userContext.getUser());

            if (newItem.getTitle() != null)
                dataBinder.putLocal(dDocTitle, newItem.getTitle());

            if (newItem.getDocType() != null)
                dataBinder.putLocal(dDocType, newItem.getDocType());

            if (newItem.getSecurityGroup() != null)
                dataBinder.putLocal(dSecurityGroup, newItem.getSecurityGroup());
            else
                dataBinder.putLocal(dSecurityGroup, rootFolder.getSecurityGroup());

            if (newItem.getAccount() != null)
                dataBinder.putLocal(dDocAccount, newItem.getAccount());
            else
                dataBinder.putLocal(dDocAccount, rootFolder.getAccount());

            if (newItem.getCollectionId() != null)
                dataBinder.putLocal(dCollectionID, newItem.getCollectionId());

            if (newItem.getDocFormat() != null)
                dataBinder.putLocal(dFormat, newItem.getDocFormat());

            if (newItem.getDocNativeName() != null)
                dataBinder.putLocal(dOriginalName, newItem.getDocNativeName());

            if (newItem.getDescription() != null)
                dataBinder.putLocal(xDescription, newItem.getDescription());

            dataBinder.putLocal("doFileCopy", "1");

            TransferFile primaryFile = new TransferFile(fileStream, newItem.getTitle(), fileSize, newItem.getDocFormat());
            dataBinder.addFile("primaryFile", primaryFile);
            IdcContext userContext2 = null;
            userContext2 = new IdcContext("weblogic"); //TODO: Check why userContext has issues
            response = idcClient.sendRequest(userContext2, dataBinder);
            serverBinder = response.getResponseAsBinder();

            savedContentItem = getPopulatedContentItem(serverBinder.getLocalData());

        } catch (IdcClientException e) {
            //logger.severe("Error in checkInFile - " + e);
            e.printStackTrace();
            throw e;
        } catch (ParseException e) {
            //logger.severe("Error in checkInFile - " + e);
            e.printStackTrace();
            throw e;
        } catch (NamingException e) {
            //logger.severe("Error in checkInFile - " + e);
            e.printStackTrace();
            throw e;
        } finally {
            if (response != null) {
                response.close();
            }
        }
        //logger.fine("End of checkInFile");
        return savedContentItem;
    }

    public Boolean checkOutContentByDocName(String docName) throws IdcClientException, NamingException {
        Boolean isCheckOutSuccessful = false;
        ServiceResponse response = null;
        DataBinder serverBinder = null;
        DataBinder dataBinder = null;
        try {
            IdcContext userContext = null;
            String connName = utils.getDefaultConnectionName();
            userContext = utils.getDefaultIdcContext(connName);
            dataBinder = idcClient.createBinder();
            dataBinder.putLocal(IDCSERVICE, CHECKOUT_BY_DOCNAME);
            dataBinder.putLocal(dDocName, docName);

            response = idcClient.sendRequest(userContext, dataBinder);
            serverBinder = response.getResponseAsBinder();
            isCheckOutSuccessful = "1".equals(serverBinder.getLocal("dIsCheckedOut"));
        } catch (IdcClientException e) {
            //logger.severe("Checkout content failed due to " + e.getMessage());
            e.printStackTrace();
            throw e;
        } catch (NamingException e) {
            //logger.severe("Checkout content failed due to " + e.getMessage());
            e.printStackTrace();
            throw e;
        } finally {
            if (response != null) {
                response.close();
            }
        }
        return isCheckOutSuccessful;
    }

    public Boolean undoCheckOutContentByDocName(String docName) throws IdcClientException, ServiceException, NamingException {
        Boolean isUndoCheckOutSuccessful = false;
        DataBinder dataBinder = null;
        ServiceResponse response = null;
        DataBinder serverBinder = null;
        try {
            IdcContext userContext = null;
            String connName = utils.getDefaultConnectionName();
            userContext = utils.getDefaultIdcContext(connName);
            dataBinder = idcClient.createBinder();
            dataBinder.putLocal(IDCSERVICE, UNDO_CHECKOUT_BY_DOCNAME);
            dataBinder.putLocal(dDocName, docName);
            response = idcClient.sendRequest(userContext, dataBinder);
            serverBinder = response.getResponseAsBinder();
            isUndoCheckOutSuccessful = "0".equals(serverBinder.getLocal("dIsCheckedOut"));

        } catch (ServiceException e) {
            // ignore if the file is not in checked out state
            if (e.getMessage().contains("The content item is not currently checked out."))
                isUndoCheckOutSuccessful = true;
            else {
                //logger.severe("Unable to undo check out the file");
                isUndoCheckOutSuccessful = false;
                throw e;
            }
        } catch (IdcClientException e) {
            //logger.severe("Error in undoCheckOutContentByDocName - " + e);
            e.printStackTrace();
            throw e;
        } catch (NamingException e) {
            //logger.severe("Error in undoCheckOutContentByDocName - " + e);
            e.printStackTrace();
            throw e;
        } finally {
            if (response != null) {
                response.close();
            }
        }
        return isUndoCheckOutSuccessful;
    }

    public List<ContentItemBean> searchFileByTitleInFolder(String title, String collectionId) throws ParseException, IdcClientException, NamingException {
        StringBuilder queryText = new StringBuilder();
        if (title != null && !"".equals(title)) {
            queryText.append("dDocTitle <substring> `" + title + "` ");
            queryText.append("<AND> xCollectionID = `" + collectionId + "` ");
        } else {
            queryText.append("xCollectionID = `" + collectionId + "` ");
        }
        return getContentItemsFromQuery(queryText.toString(), null, null);
    }

    public List<ContentItemBean> searchFileByTitle(String title) throws ParseException, IdcClientException, NamingException {
        StringBuilder queryText = new StringBuilder();
        if (title != null && !"".equals(title)) {
            queryText.append("dDocTitle <substring> `" + title + "` ");
            queryText.append("<AND> xContentType <matches> `" + IMAGE_LIBRARY + "` ");
            return getContentItemsFromQuery(queryText.toString(), null, null);
        } else
            return null;
    }

    public ContentItemBean findContentItemById(String contentId) throws IdcClientException, ParseException, NamingException {
        List<ContentItemBean> results = null;
        ContentItemBean item = null;
        ServiceResponse serviceResponse = null;
        try {
            results = getContentItemsFromQuery("dDocName <matches> `" + contentId + "`", null, null);
            if (results != null && results.size() > 0)
                item = results.get(0);
        } catch (IdcClientException e) {
            //logger.severe("Unable to find content item due to : " + e.getMessage());
            e.printStackTrace();
            throw e;
        } catch (ParseException e) {
            //logger.severe("Unable to find content item due to : " + e.getMessage());
            e.printStackTrace();
            throw e;
        } finally {
            if (serviceResponse != null) {
                serviceResponse.close();
            }
        }
        return item;
    }

    public List<ContentItemBean> getContentItemsFromQuery(String queryText, String sortField, String sortOrder) throws ParseException, IdcClientException, NamingException {
        ServiceResponse serviceResponse = null;
        int totalRows = 0;
        int rowsPerPage = 0;
        int endRow = 0;
        int numOfPages = 0;
        DataBinder dataBinder = null;
        List<ContentItemBean> contentItems = new ArrayList<ContentItemBean>();

        try {
            IdcContext userContext = null;
            String connName = utils.getDefaultConnectionName();
            userContext = utils.getDefaultIdcContext(connName);
            dataBinder = idcClient.createBinder();
            dataBinder.putLocal(IDCSERVICE, GET_SEARCH_RESULTS);
            dataBinder.putLocal("QueryText", queryText);

            //            if (noOfItems != -1) {
            //                dataBinder.putLocal("ResultCount", noOfItems + "");
            //            }

            if (sortField != null && !"".equals(sortField.trim()))
                dataBinder.putLocal("SortField", sortField);

            if (sortField != null && !"".equals(sortField.trim()))
                dataBinder.putLocal("SortOrder", sortOrder);

            serviceResponse = idcClient.sendRequest(userContext, dataBinder);
            DataBinder responseDataBinder = serviceResponse.getResponseAsBinder();

            DataResultSet dataResultSet = responseDataBinder.getResultSet("SearchResults");
            for (DataObject obj : dataResultSet.getRows()) {
                ContentItemBean item = getPopulatedContentItem(obj);
                contentItems.add(item);
            }

            totalRows = Integer.parseInt(responseDataBinder.getLocal("TotalRows"));

            if (totalRows > 0) {

                endRow = Integer.parseInt(responseDataBinder.getLocal("EndRow"));
                numOfPages = Integer.parseInt(responseDataBinder.getLocal("NumPages"));

                // fetch the remaining results recursively using the same binder
                if (numOfPages > 1) {

                    rowsPerPage = endRow;
                    int startRow = endRow + 1;
                    int i = 1;

                    while (i <= numOfPages) {

                        if (totalRows >= (startRow + rowsPerPage - 1))
                            endRow = startRow + rowsPerPage - 1;
                        else
                            endRow = totalRows;

                        dataBinder.putLocal("StartRow", startRow + "");
                        dataBinder.putLocal("EndRow", endRow + "");
                        serviceResponse = idcClient.sendRequest(userContext, dataBinder);
                        responseDataBinder = serviceResponse.getResponseAsBinder();
                        dataResultSet = responseDataBinder.getResultSet("SearchResults");
                        for (DataObject obj : dataResultSet.getRows()) {
                            ContentItemBean item = getPopulatedContentItem(obj);
                            contentItems.add(item);
                        }

                        startRow = endRow + 1;
                        i++;
                    }
                }
            }

        } catch (IdcClientException e) {
            //logger.severe("Unable to fetch content items matching the query due to : " + e.getMessage());
            e.printStackTrace();
            throw e;
        } catch (ParseException e) {
            //logger.severe("Unable to fetch content items matching the query due to : " + e.getMessage());
            e.printStackTrace();
            throw e;
        } catch (NamingException e) {
            //logger.severe("Unable to fetch content items matching the query due to : " + e.getMessage());
            e.printStackTrace();
            throw e;
        } finally {
            if (serviceResponse != null) {
                serviceResponse.close();
            }
        }

        return contentItems;
    }

    public List<ContentFolderBean> getContentFoldersFromQuery(String fieldName, String operator, String criteria, int noOfItems, String sortField, String sortOrder) throws IdcClientException,
                                                                                                                                                                            ParseException,
                                                                                                                                                                            NamingException {
        ServiceResponse serviceResponse = null;
        List<ContentFolderBean> contentFolders = new ArrayList<ContentFolderBean>();
        DataBinder dataBinder = null;
        try {
            IdcContext userContext = null;
            String connName = utils.getDefaultConnectionName();
            userContext = utils.getDefaultIdcContext(connName);
            dataBinder = idcClient.createBinder();
            dataBinder.putLocal(IDCSERVICE, "COLLECTION_SEARCH_RESULTS");
            dataBinder.putLocal(fieldName, "true");
            dataBinder.putLocal("op" + fieldName, operator);
            dataBinder.putLocal("compare" + fieldName, criteria);

            if (noOfItems != -1) {
                dataBinder.putLocal("ResultCount", noOfItems + "");
            }

            if (sortField != null && !"".equals(sortField.trim()))
                dataBinder.putLocal("SortField", sortField);

            if (sortField != null && !"".equals(sortField.trim()))
                dataBinder.putLocal("SortOrder", sortOrder);

            serviceResponse = idcClient.sendRequest(userContext, dataBinder);
            DataBinder responseDataBinder = serviceResponse.getResponseAsBinder();
            DataResultSet dataResultSet = responseDataBinder.getResultSet("SearchResults");
            for (DataObject obj : dataResultSet.getRows()) {
                ContentFolderBean item;
                item = getPopulatedContentFolder(obj);
                contentFolders.add(item);
            }
        } catch (IdcClientException e) {
            //logger.severe("Unable to get folders information due to : " + e.getMessage());
            e.printStackTrace();
            throw e;
        } catch (ParseException e) {
            //logger.severe("Unable to get folders information due to : " + e.getMessage());
            e.printStackTrace();
            throw e;
        } catch (NamingException e) {
            //logger.severe("Unable to get folders information due to : " + e.getMessage());
            e.printStackTrace();
            throw e;
        } finally {
            if (serviceResponse != null) {
                serviceResponse.close();
            }
        }
        return contentFolders;
    }

    /**
     * Reusable method to get populated contentItems from the binder's dataObject
     * @param obj
     * @return
     */
    private ContentFolderBean getPopulatedContentFolder(DataObject obj) throws ParseException {
        ContentFolderBean item = new ContentFolderBean();

        item.setAccount(obj.get(dDocAccount));
        item.setOwner(obj.get(dCollectionOwner));
        item.setCollectionName(obj.get(dCollectionName));
        item.setCollectionId(obj.get(dCollectionID));
        item.setParentCollectionId(obj.get(dParentCollectionID));
        item.setTitle(obj.get(dDocTitle));
        item.setFolderType(obj.get(xContentType));
        item.setSecurityGroup(obj.get(dSecurityGroup));

        if (obj.get(dLastModifiedDate) != null) {
            try {
                item.setLastModifiedDate(UCM_DATE_FORMAT.parse(obj.get(dLastModifiedDate)));

            } catch (ParseException ex) {
                if (obj.get(dLastModifiedDate).contains("ts")) {
                    String lastModDate =
                        obj.get(dLastModifiedDate).substring(obj.get(dLastModifiedDate).indexOf("'") + 1, obj.get(dLastModifiedDate).indexOf("'", obj.get(dLastModifiedDate).indexOf("'") + 1) + 1);
                    item.setLastModifiedDate(UCM_DATE_FORMAT_MILLIS.parse(lastModDate));
                }
            }
        }

        if (obj.get(dCreateDate) != null) {
            try {
                item.setCreationDate(UCM_DATE_FORMAT.parse(obj.get(dCreateDate)));
            } catch (ParseException ex) {
                if (obj.get(dCreateDate).contains("ts")) {
                    String crtDate = obj.get(dCreateDate).substring(obj.get(dCreateDate).indexOf("'") + 1, obj.get(dCreateDate).indexOf("'", obj.get(dCreateDate).indexOf("'") + 1) + 1);
                    item.setCreationDate(UCM_DATE_FORMAT_MILLIS.parse(crtDate));
                }
            }
        }


        item.setComments(obj.get(xComments));
        item.setAuthor(obj.get(dDocAuthor));
        item.setCreatedBy(obj.get(dCollectionCreator));
        item.setLastModifiedBy(obj.get(dCollectionModifier));

        return item;
    }


    public List<ContentFolderBean> fetchAllImageFolders() throws IdcClientException, ParseException, NamingException {

        return getContentFoldersFromQuery(xContentType, "equals", "Image Library", -1, null, null);
    }

    public Boolean overwriteContent(ContentItemBean item, InputStream fileStream, long fileSize) throws IdcClientException, ParseException, CustomException, NamingException {
        Boolean overwriteStatus = false;
        Boolean isItemCheckedOut = checkOutContentByDocName(item.getDocInternalName());

        if (isItemCheckedOut) {

            ContentItemBean savedContentItem = checkInFile(fileStream, fileSize, item);

            if (savedContentItem != null) {
                overwriteStatus = true;
                undoCheckOutContentByDocName(item.getDocInternalName());
            } else {
                overwriteStatus = false;
                undoCheckOutContentByDocName(item.getDocInternalName());
            }
        } else {
            overwriteStatus = false;
            undoCheckOutContentByDocName(item.getDocInternalName());
        }

        return overwriteStatus;
    }

    public static String getFolderIdFromPath(String path) throws NamingException {
        String folderId = null;
        try {
            DataBinder dataBinder = idcClient.createBinder();
            dataBinder.putLocal("IdcService", "COLLECTION_INFO");
            dataBinder.putLocal("hasCollectionPath", "true");
            dataBinder.putLocal("dCollectionPath", path);
            IdcContext userContext = null;
            String connName = utils.getDefaultConnectionName();
            userContext = utils.getDefaultIdcContext(connName);
            ServiceResponse response = idcClient.sendRequest(userContext, dataBinder);
            DataBinder serverBinder = response.getResponseAsBinder();
            DataResultSet resultSet = serverBinder.getResultSet("PATH");
            DataObject dataObject = resultSet.getRows().get(resultSet.getRows().size() - 1);
            folderId = dataObject.get("dCollectionID");
        } catch (IdcClientException e) {
            e.printStackTrace();
        }

        return folderId;
    }

    /** Get folder details from the given folder path */

    public ContentFolderBean getFolderInfoFromPath(String path) throws IdcClientException, NamingException {
        ServiceResponse response = null;
        ContentFolderBean folder = null;
        DataBinder dataBinder = null;
        if (path != null) {
            try {
                dataBinder = idcClient.createBinder();
                dataBinder.putLocal("IdcService", "COLLECTION_INFO");
                dataBinder.putLocal("hasCollectionPath", "true");
                dataBinder.putLocal("dCollectionPath", path);
                IdcContext userContext = null;
                String connName = utils.getDefaultConnectionName();
                userContext = utils.getDefaultIdcContext(connName);
                response = idcClient.sendRequest(userContext, dataBinder);
                DataBinder serverBinder = response.getResponseAsBinder();
                DataResultSet resultSet = serverBinder.getResultSet("PATH");
                DataObject obj = resultSet.getRows().get(resultSet.getRows().size() - 1);
                folder = new ContentFolderBean();
                folder.setCollectionName(obj.get(dCollectionName));
                folder.setCollectionId(obj.get(dCollectionID));
                folder.setSecurityGroup(obj.get(dSecurityGroup));
                folder.setParentCollectionId(obj.get(dParentCollectionID));
                folder.setCollectionPath(obj.get(dCollectionPath));
                folder.setAccount(obj.get(dDocAccount));
                folder.setTitle(obj.get(dDocTitle));
                folder.setCollectionPath(path);
            } catch (IdcClientException e) {
                //logger.severe("Unable to get folder information due to : " + e.getMessage());
                e.printStackTrace();
                throw e;
            } catch (NamingException e) {
                //logger.severe("Unable to get folder information due to : " + e.getMessage());
                e.printStackTrace();
                throw e;
            } finally {
                if (response != null) {
                    response.close();
                }
            }
        }
        return folder;
    }

    public String createSubfolder(String parentCollectionId, String subFolderName) throws IdcClientException, NamingException {
        String newFolderCollectionId = null;
        ServiceResponse response = null;
        DataBinder dataBinder = null;
        if (parentCollectionId != null && !"".equals(parentCollectionId.trim())) {
            try {
                dataBinder = idcClient.createBinder();
                dataBinder.putLocal("IdcService", "COLLECTION_ADD");
                dataBinder.putLocal("hasParentCollectionID", "true");
                dataBinder.putLocal("dParentCollectionID", parentCollectionId);
                dataBinder.putLocal("dCollectionName", subFolderName);
                IdcContext userContext = null;
                String connName = utils.getDefaultConnectionName();
                userContext = utils.getDefaultIdcContext(connName);
                response = idcClient.sendRequest(userContext, dataBinder);
                DataBinder serverBinder = response.getResponseAsBinder();
                newFolderCollectionId = serverBinder.getLocal("dCollectionID");

            } catch (IdcClientException e) {
                //logger.severe("Unable to create sub folder due to : " + e.getMessage());
                e.printStackTrace();
                throw e;
            } catch (NamingException e) {
                //logger.severe("Unable to create sub folder due to : " + e.getMessage());
                e.printStackTrace();
                throw e;
            } finally {
                if (response != null) {
                    response.close();
                }
            }
        }
        return newFolderCollectionId;
    }


    public int getNumberOfContentItemsInFolder(String collectionId) throws IdcClientException, NamingException {
        ServiceResponse serviceResponse = null;
        int totalRows = 0;
        DataBinder dataBinder = null;
        try {
            IdcContext userContext = null;
            String connName = utils.getDefaultConnectionName();
            userContext = utils.getDefaultIdcContext(connName);
            dataBinder = this.idcClient.createBinder();
            dataBinder.putLocal("IdcService", "GET_SEARCH_RESULTS");
            dataBinder.putLocal("QueryText", "xCollectionID = `" + collectionId + "`");
            serviceResponse = idcClient.sendRequest(userContext, dataBinder);
            DataBinder responseDataBinder = serviceResponse.getResponseAsBinder();
            totalRows = Integer.parseInt(responseDataBinder.getLocal("TotalRows"));
        } catch (IdcClientException e) {
            //logger.severe("Unable to get number of folder items due to : " + e.getMessage());
            e.printStackTrace();
            throw e;
        } catch (NamingException e) {
            //logger.severe("Unable to get number of folder items due to : " + e.getMessage());
            e.printStackTrace();
            throw e;
        } finally {
            if (serviceResponse != null) {
                serviceResponse.close();
            }
        }
        return totalRows;
    }

    public List<ContentFolderBean> getSubFolders(String parentFolderID) throws IdcClientException, NamingException {
        List<ContentFolderBean> subfolders = new ArrayList<ContentFolderBean>();
        ServiceResponse response = null;
        DataBinder dataBinder = null;
        if (parentFolderID != null) {
            try {
                dataBinder = this.idcClient.createBinder();
                dataBinder.putLocal(IDCSERVICE, SUBFOLDERS_SERVICE);
                dataBinder.putLocal(hasCollectionID, TRUE);
                dataBinder.putLocal(dCollectionID, parentFolderID);
                IdcContext userContext = null;
                String connName = utils.getDefaultConnectionName();
                userContext = utils.getDefaultIdcContext(connName);
                response = idcClient.sendRequest(userContext, dataBinder);
                DataBinder serverBinder = response.getResponseAsBinder();

                DataResultSet resultSet = serverBinder.getResultSet(COLLECTIONS_RESULTSET);

                for (DataObject obj : resultSet.getRows()) {
                    ContentFolderBean subfolder = new ContentFolderBean();
                    subfolder.setCollectionName(obj.get(dCollectionName));
                    subfolder.setCollectionId(obj.get(dCollectionID));
                    subfolder.setSecurityGroup(obj.get(dSecurityGroup));
                    subfolder.setParentCollectionId(obj.get(dParentCollectionID));
                    subfolder.setCollectionPath(obj.get(dCollectionPath));
                    subfolder.setAccount(obj.get(dDocAccount));
                    subfolders.add(subfolder);
                }
            } catch (IdcClientException e) {
                //logger.severe("Unable to fetch the sub folders information due to : " + e.getMessage());
                throw e;
            } catch (NamingException e) {
                //logger.severe("Unable to fetch the sub folders information due to : " + e.getMessage());
                throw e;
            } finally {
                if (response != null) {
                    response.close();
                }
            }
        }
        return subfolders;
    }
    
    public List<ContentFolderBean> getSubFoldersByCollectionPath(String collectionPath) throws IdcClientException, NamingException {
        List<ContentFolderBean> subfolders = new ArrayList<ContentFolderBean>();
        ServiceResponse response = null;
        DataBinder dataBinder = null;
        if (collectionPath != null) {
            try {                
                dataBinder = this.idcClient.createBinder();
                dataBinder.putLocal(IDCSERVICE, SUBFOLDERS_SERVICE);
                dataBinder.putLocal(hasCollectionPath, TRUE);
                dataBinder.putLocal(dCollectionPath, collectionPath);
                IdcContext userContext = null;
                String connName = utils.getDefaultConnectionName();
                userContext = utils.getDefaultIdcContext(connName);
                response = idcClient.sendRequest(userContext, dataBinder);
                DataBinder serverBinder = response.getResponseAsBinder();

                DataResultSet resultSet = serverBinder.getResultSet(COLLECTIONS_RESULTSET);

                for (DataObject obj : resultSet.getRows()) {
                    ContentFolderBean subfolder = new ContentFolderBean();
                    subfolder.setCollectionName(obj.get(dCollectionName));
                    subfolder.setCollectionId(obj.get(dCollectionID));
                    subfolder.setSecurityGroup(obj.get(dSecurityGroup));
                    subfolder.setParentCollectionId(obj.get(dParentCollectionID));
                    subfolder.setCollectionPath(obj.get(dCollectionPath));
                    subfolder.setAccount(obj.get(dDocAccount));
                    subfolders.add(subfolder);
                }
            } catch (IdcClientException e) {
                //logger.severe("Unable to fetch the sub folders information due to : " + e.getMessage());
                throw e;
            } catch (NamingException e) {
                //logger.severe("Unable to fetch the sub folders information due to : " + e.getMessage());
                throw e;
            } finally {
                if (response != null) {
                    response.close();
                }
            }
        }
        return subfolders;
    }
    
    public static void deleteContentItem(String dId, String dDocName) {
        ServiceResponse serviceResponse = null;
        try {

            IdcContext userContext = null;
            String connName = utils.getDefaultConnectionName();
            userContext = utils.getDefaultIdcContext(connName);
            DataBinder dataBinder = idcClient.createBinder();

            dataBinder.putLocal(IDCSERVICE, "DELETE_DOC");

            dataBinder.putLocal("dID", dId); // Document ID
            dataBinder.putLocal("dDocName", dDocName); // Document Name

            serviceResponse = idcClient.sendRequest(new IdcContext("weblogic"), dataBinder);
            DataBinder myResponseDataBinder = serviceResponse.getResponseAsBinder();
            System.out.println("File deleted successfully");
        } catch (IdcClientException idcce) {
            System.out.println("IDC Client Exception occurred. Unable to delete file. Message: " + idcce.getMessage() + ", Stack trace: ");
        } catch (Exception e) {
            System.out.println("Exception occurred. Unable to delete file. Message: " + e.getMessage() + ", Stack trace: ");
            e.printStackTrace();
        } finally {
            if (serviceResponse != null) {
                serviceResponse.close();
            }
        }
    }

    // main method


    public static void main(String[] args) throws NamingException {
        
        WCContentUtil scUtil = new WCContentUtil("idc://hovm1014.keste.com:4444", "weblogic");
        List<ContentItemBean> results = null;
        try {
            results = scUtil.getFolderContentItemsByCollectionPath("/Contribution Folders/TestPortal/Resources/Images/");
        } catch (IdcClientException e) {
        } catch (ParseException e) {
        }
        System.out.println(results);
        //        ContentService service = new ContentService();
        //        DocLibUtils utils = new DocLibUtils();
        //        System.out.println(utils.getDefaultConnectionName());


        //        try {
        //            System.out.println(utils.getDefaultIdcContext(utils.getDefaultConnectionName()));
        //        } catch (NamingException e) {
        //        } catch (IdcClientException e) {
        //        }
        //        try {
        //            System.out.println(utils.getIdcClient(utils.getDefaultConnectionName()));
        //        } catch (NameNotFoundException e) {
        //        } catch (NamingException e) {
        //        } catch (IdcClientException e) {
        //        }
        //
        //        System.out.println(service.idcClient);


        // test getFolderContentItemsByCollectionPath
        //        List<ContentItem> results =
        //            service.getFolderContentItemsByCollectionPath("/Contribution Folders/WebPortals/My Store/MyStoreAdminContent/Product Images/");
        //        System.out.println(results);

        // test checkin
        //        File testFile =
        //            new File("C:\\Users\\hpothuri\\Desktop\\sofa models\\images.jpg");
        //        InputStream is = null;
        //        try {
        //            is = new FileInputStream(testFile);
        //        } catch (FileNotFoundException e) {
        //        }
        //        ContentItem newItem = new ContentItem();
        //        newItem.setAuthor("appadmin");
        //        newItem.setTitle(testFile.getName());
        //        newItem.setSecurityGroup("MyStore");
        //        newItem.setCollectionId("902508865447002611");
        //        ContentItem savedItem =
        //            service.checkInFile(is, testFile.length(), newItem);


        // test checkout
        //        service.checkOutContentByDocName("MS_002021");
        //        service.undoCheckOutContentByDocName("MS_002021");

        // test search
        //service.searchFileByTitleInFolder(null, "902508865447002611");
        //service.fetchAllImageFolders();

        //        List<ContentFolder> folders;
        //        try {
        //            folders = service.getContentFoldersFromQuery("dDocAccount", "equals", "Allowances_MyPromotions", -1, null, null);
        //            System.err.println(folders.size());
        //        } catch (Exception e) {
        //        }
        //        try {
        //            System.out.println("");
        //        } catch (IdcClientException e) {
        //            e.printStackTrace();
        //        } catch (CustomException e) {
        //        }
    }

}
