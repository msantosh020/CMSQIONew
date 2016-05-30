package gov.cms.portal.qiocollabaration.extension.model.profile.service;

import gov.cms.portal.qiocollabaration.extension.model.profile.bean.ProfileBean;
import gov.cms.portal.qiocollabaration.extension.model.profile.dao.ProfileDAO;

public class ProfileServiceBean {
    public ProfileServiceBean() {
        super();
    }

    public ProfileBean getProfileDetais(String emailId) {
        ProfileBean profileBean = null;
        ProfileDAO dao = new ProfileDAO();
        try {
            profileBean = dao.getProfileDetaisl(emailId);
        } catch (Exception e) {
            // TODO: Add catch code
            e.printStackTrace();
        }
        return profileBean;
    }
}
