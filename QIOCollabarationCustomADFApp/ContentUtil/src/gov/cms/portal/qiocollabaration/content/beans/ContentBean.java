package gov.cms.portal.qiocollabaration.content.beans;

import java.io.Serializable;

import java.util.Date;

public class ContentBean implements Serializable{

    private static final long serialVersionUID = 1L;

    public ContentBean() {
        super();
    }

    private String title; //dDocTitle
    private String securityGroup; //dSecurityGroup
    private Date creationDate; //dCreateDate
    private String createdBy; //dDocCreator
    private String owner; //dDocOwner
    private Date lastModifiedDate; //dDocLastModifiedDate
    private String lastModifiedBy; //dDocLastModifier
    private String comments; //xComments
    private String collectionId; //xCollectionID
    private String account; //dDocAccount
    private String author; //dDocAuthor

    public void setSecurityGroup(String securityGroup) {
        this.securityGroup = securityGroup;
    }

    public String getSecurityGroup() {
        return securityGroup;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setCollectionId(String collectionId) {
        this.collectionId = collectionId;
    }

    public String getCollectionId() {
        return collectionId;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getComments() {
        return comments;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getOwner() {
        return owner;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getAccount() {
        return account;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthor() {
        return author;
    }
}
