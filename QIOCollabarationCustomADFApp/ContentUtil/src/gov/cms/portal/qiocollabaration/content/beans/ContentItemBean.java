package gov.cms.portal.qiocollabaration.content.beans;


import gov.cms.portal.qiocollabaration.content.util.WCContentUtil;

import java.io.Serializable;

import java.util.Date;


public class ContentItemBean extends ContentBean implements Serializable {
    @SuppressWarnings("compatibility:-6437756331644262301")
    private static final long serialVersionUID = 1L;

    public ContentItemBean() {
        super();
    }

    private String docId; //dDocID
    private String docInternalName; //dDocName
    private String docType; //dDocType
    private Long fileSize; //dFileSize
    private String extension; //dExtension
    private String webExtension; //dWebExtension
    private String webFileName; //dWebFilename
    private String webURL;
    private String nativeWebURL; //dWebURL
    private String docFormat; //dFormat
    private String status; //dStatus
    private Long revisionNumber; //dRevisionID
    private String docNativeName; //dOriginalName
    private String dId; // dID
    private Date expirationDate; //dOutDate
    private Date releaseDate; //dInDate
    private String revisionLabel; //dRevLabel
    // Custom Meta-data
    private String contentCategory;
    private String description;
    private String tagNames;
    private String topicsNames;   //xsow
    private String communityName; //xcommunityoforigin
    private String subTopicName;  //xtopics


    public void setDocId(String docId) {
        this.docId = docId;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocInternalName(String docInternalName) {
        this.docInternalName = docInternalName;
    }

    public String getDocInternalName() {
        return docInternalName;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

    public String getDocType() {
        return docType;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getExtension() {
        return extension;
    }

    public void setWebExtension(String webExtension) {
        this.webExtension = webExtension;
    }

    public String getWebExtension() {
        return webExtension;
    }

    public void setWebFileName(String webFileName) {
        this.webFileName = webFileName;
    }

    public String getWebFileName() {
        return webFileName;
    }

    public void setWebURL(String webURL) {
        this.webURL = webURL;
    }

    public String getWebURL() {
        if (webURL == null) {
            StringBuffer reqUrl = new StringBuffer("");
            reqUrl.append("/ShowProperty?nodeId=/" + WCContentUtil.utils.getDefaultConnectionName() + "/" + docInternalName + "//idcPrimaryFile&amp;revision=latestreleased");
            webURL = reqUrl.toString();
        }
        return webURL;
    }

    public void setNativeWebURL(String nativeWebURL) {
        this.nativeWebURL = nativeWebURL;
    }

    public String getNativeWebURL() {
        return nativeWebURL;
    }

    public void setDocFormat(String docFormat) {
        this.docFormat = docFormat;
    }

    public String getDocFormat() {
        return docFormat;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setRevisionNumber(Long revisionNumber) {
        this.revisionNumber = revisionNumber;
    }

    public Long getRevisionNumber() {
        return revisionNumber;
    }

    public void setDocNativeName(String docNativeName) {
        this.docNativeName = docNativeName;
    }

    public String getDocNativeName() {
        return docNativeName;
    }


    public void setDId(String dId) {
        this.dId = dId;
    }

    public String getDId() {
        return dId;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setRevisionLabel(String revisionLabel) {
        this.revisionLabel = revisionLabel;
    }

    public String getRevisionLabel() {
        return revisionLabel;
    }

    public void setContentCategory(String contentCategory) {
        this.contentCategory = contentCategory;
    }

    public String getContentCategory() {
        return contentCategory;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setTagNames(String tagNames) {
        this.tagNames = tagNames;
    }

    public String getTagNames() {
        return tagNames;
    }

    public void setTopicsNames(String topicsNames) {
        this.topicsNames = topicsNames;
    }

    public String getTopicsNames() {
        return topicsNames;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }

    public String getCommunityName() {
        return communityName;
    }

    public void setSubTopicName(String subTopicName) {
        this.subTopicName = subTopicName;
    }

    public String getSubTopicName() {
        return subTopicName;
    }
}
