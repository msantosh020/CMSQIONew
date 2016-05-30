package gov.cms.portal.qiocollabaration.extension.model.profile.dao;

import gov.cms.portal.qiocollabaration.extension.model.common.util.JdbcUtil;
import gov.cms.portal.qiocollabaration.extension.model.profile.bean.ProfileBean;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class ProfileDAO {

    private static final String PROFILE_QRY = "select FIRST_NAME, LAST_NAME, EMAIL, JOB from PROFILE_TABLE where EMAIL_ID = ";
    private static final String PROFILE_DS = "jdbc/ProfileDS";

    public ProfileDAO() {
        super();
    }

    public static ProfileBean getProfileDetaisl(String emailId) {
        ProfileBean profileBean = new ProfileBean();
        Connection conn = null;
        Statement cstmt = null;
        ResultSet rs = null;
        try {
            conn = JdbcUtil.getDSConnection(PROFILE_DS);
            cstmt = conn.createStatement();
            rs = cstmt.executeQuery(PROFILE_QRY + "'" + emailId + "'");
            if (rs.next()) {
                profileBean.setEmail(rs.getString("EMAIL"));
                profileBean.setFirstName(rs.getString("FIRST_NAME"));
                profileBean.setLastName(rs.getString("LAST_NAME"));
                profileBean.setJob(rs.getString("JOB"));
                //TODO - Modify the query to get all the columns from table
                // Added setter for each colun]mn
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.closeStatement(cstmt);
            JdbcUtil.closeConnection(conn);
        }
        return profileBean;
    }
}
