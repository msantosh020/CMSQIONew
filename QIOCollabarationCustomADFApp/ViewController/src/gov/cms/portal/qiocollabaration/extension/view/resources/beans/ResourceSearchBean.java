package gov.cms.portal.qiocollabaration.extension.view.resources.beans;

import java.io.Serializable;

public class ResourceSearchBean implements Serializable {
    private static final long serialVersionUID = 1L;

    public ResourceSearchBean() {
        super();
    }

    private String keyphrase;
    private String format;
    private String state;
    private String author;
    private String organization;
    private String publicationDate;
    private String title;
    private String description;
    private String resourceType;
    private String scopeOfWork;


    public void setKeyphrase(String keyphrase) {
        this.keyphrase = keyphrase;
    }

    public String getKeyphrase() {
        return keyphrase;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getFormat() {
        return format;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthor() {
        return author;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getOrganization() {
        return organization;
    }

    public void setPublicationDate(String publicationDate) {
        this.publicationDate = publicationDate;
    }

    public String getPublicationDate() {
        return publicationDate;
    }

    public void clear() {
        setKeyphrase(null);
        setAuthor(null);
        setFormat(null);
        setOrganization(null);
        setPublicationDate(null);
        setState(null);
        setTitle(null);
        setDescription(null);
        setResourceType(null);
        setScopeOfWork(null);
    }

    public boolean isFilterCriteriaEntered() {
        boolean isFilter = false;
        if (isNonEmpty(getAuthor()) || isNonEmpty(getFormat()) || isNonEmpty(getKeyphrase()) || isNonEmpty(getOrganization()) || isNonEmpty(getPublicationDate()) || isNonEmpty(getState())) {
            isFilter = true;
        }
        return isFilter;
    }

    public boolean isNonEmpty(String str) {
        boolean isNonEmpty = true;
        if (str == null || str.isEmpty()) {
            isNonEmpty = false;
        }
        return isNonEmpty;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setScopeOfWork(String scopeOfWork) {
        this.scopeOfWork = scopeOfWork;
    }

    public String getScopeOfWork() {
        return scopeOfWork;
    }
}
