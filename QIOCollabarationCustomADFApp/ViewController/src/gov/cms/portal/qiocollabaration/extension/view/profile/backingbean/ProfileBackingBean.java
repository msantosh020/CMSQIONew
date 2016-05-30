package gov.cms.portal.qiocollabaration.extension.view.profile.backingbean;

import gov.cms.portal.qiocollabaration.extension.model.profile.bean.ProfileBean;
import gov.cms.portal.qiocollabaration.extension.view.common.util.ADFUtils;
import gov.cms.portal.qiocollabaration.extension.view.common.util.JSFUtils;

import java.util.HashMap;
import java.util.Map;

public class ProfileBackingBean {
    public ProfileBackingBean() {
        super();
    }

    private ProfileBean profile;

    public void setProfile(ProfileBean profile) {
        this.profile = profile;
    }

    public ProfileBean getProfile() {
        if (profile == null) {
            profile = loadProfile();
        }
        return profile;
    }

    private ProfileBean loadProfile() {
        ProfileBean profile = null;
        Long emailId = (Long)JSFUtils.resolveExpression("#{pageFlowScope.emailId}");
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("emailId", emailId);
        profile = (ProfileBean)ADFUtils.invokeOperationBinding("getProfileDetais", paramMap);
        return profile;
    }
}
