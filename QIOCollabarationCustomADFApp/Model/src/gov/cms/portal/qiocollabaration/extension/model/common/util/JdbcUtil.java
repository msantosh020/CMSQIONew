package gov.cms.portal.qiocollabaration.extension.model.common.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import javax.sql.DataSource;

public class JdbcUtil {
    public JdbcUtil() {
        super();
    }

    public static Connection getDSConnection(String dataSourceName) throws NamingException, SQLException {
        return getConnection(dataSourceName);
    }

    public static Connection getConnection() throws Exception {
        Class.forName("oracle.jdbc.driver.OracleDriver");
        Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "11", "11");
        return conn;
    }

    public static Connection getConnection(String jndiName) throws NamingException, SQLException {
        Context ctx = new InitialContext();
        DataSource ds = (DataSource)ctx.lookup(jndiName);
        return ds.getConnection();
    }

    public static void closeConnection(Connection con) {
        try {
            if (con != null)
                con.close();
        } catch (Exception e) {
        }
    }

    public static void closeStatement(Statement st) {
        try {
            if (st != null)
                st.close();
        } catch (Exception e) {
        }
    }

    public static void closeResultSet(ResultSet rs) {
        try {
            if (rs != null)
                rs.close();
        } catch (Exception e) {
        }
    }

    public static int getNextSeq(String dataSourceName, String sequenceName) {
        int seqId = -1;
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            con = getDSConnection(dataSourceName);
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT " + sequenceName + ".NEXTVAL FROM DUAL");
            if (rs.next()) {
                seqId = rs.getInt(1);
            }
        } catch (SQLException e) {
        } catch (NamingException e) {
        } finally {
            closeResultSet(rs);
            closeStatement(stmt);
            closeConnection(con);
        }
        return seqId;
    }
}
