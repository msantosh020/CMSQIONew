package gov.cms.portal.qiocollabaration.extension.model.profile.bean;

public class ProfileBean {
    public ProfileBean() {
        super();
    }

    private String firstName;
    private String lastName;
    private String job;
    private String email;

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getJob() {
        return job;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
