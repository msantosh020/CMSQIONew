package gov.cms.portal.qiocollabaration.extension.view.resources.beans;

import java.io.Serializable;

import java.util.Date;

public class ResourceBean implements Serializable {
    private static final long serialVersionUID = 1L;

    public ResourceBean() {
        super();
    }

    public ResourceBean(String resourceTitle, String resourceDescription, String resourceFormat, String length, Date addedDate, Date updatedDate, String uploadedOrganization, String author,
                        String thumbnailImageUrl, String resourceUrl, String community) {
        super();
        this.resourceTitle = resourceTitle;
        this.resourceDescription = resourceDescription;
        this.resourceFormat = resourceFormat;
        this.length = length;
        this.addedDate = addedDate;
        this.updatedDate = updatedDate;
        this.uploadedOrganization = uploadedOrganization;
        this.author = author;
        this.thumbnailImageUrl = thumbnailImageUrl;
        this.resourceUrl = resourceUrl;
        this.community = community;
    }

    private String resourceTitle;
    private String resourceDescription;
    private String resourceFormat;
    private String length;
    private Date addedDate;
    private Date updatedDate;
    private String uploadedOrganization;
    private String author;
    private String thumbnailImageUrl;
    private String resourceUrl;
    private String resourceWebUrl;
    private String resourceNativeWebUrl;
    private String community;

    public void setResourceTitle(String resourceTitle) {
        this.resourceTitle = resourceTitle;
    }

    public String getResourceTitle() {
        return resourceTitle;
    }

    public void setResourceDescription(String resourceDescription) {
        this.resourceDescription = resourceDescription;
    }

    public String getResourceDescription() {
        return resourceDescription;
    }

    public void setResourceFormat(String resourceFormat) {
        this.resourceFormat = resourceFormat;
    }

    public String getResourceFormat() {
        return resourceFormat;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getLength() {
        return length;
    }

    public void setAddedDate(Date addedDate) {
        this.addedDate = addedDate;
    }

    public Date getAddedDate() {
        return addedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUploadedOrganization(String uploadedOrganization) {
        this.uploadedOrganization = uploadedOrganization;
    }

    public String getUploadedOrganization() {
        return uploadedOrganization;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthor() {
        return author;
    }

    public void setThumbnailImageUrl(String thumbnailImageUrl) {
        this.thumbnailImageUrl = thumbnailImageUrl;
    }

    public String getThumbnailImageUrl() {
        return thumbnailImageUrl;
    }

    public void setResourceUrl(String resourceUrl) {
        this.resourceUrl = resourceUrl;
    }

    public String getResourceUrl() {
        return resourceUrl;
    }

    public void setCommunity(String community) {
        this.community = community;
    }

    public String getCommunity() {
        return community;
    }

    public void setResourceWebUrl(String resourceWebUrl) {
        this.resourceWebUrl = resourceWebUrl;
    }

    public String getResourceWebUrl() {
        return resourceWebUrl;
    }

    public void setResourceNativeWebUrl(String resourceNativeWebUrl) {
        this.resourceNativeWebUrl = resourceNativeWebUrl;
    }

    public String getResourceNativeWebUrl() {
        return resourceNativeWebUrl;
    }
}
