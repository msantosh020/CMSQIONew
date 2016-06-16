package gov.cms.portal.qiocollabaration.extension.view.loa.util;

import java.io.IOException;
import java.io.InputStream;

import java.util.Iterator;
import java.util.Properties;
import java.util.ResourceBundle;

public class LOAPropertiesUtil {
    public LOAPropertiesUtil() {
        super();
    }

    private static Properties appProperties = null;

    /**
     * Returns the propery value as String
     * @param propName
     * @return
     */
    public static String getPropertiesString(String propName) {
        if (appProperties == null) {
            readProperties();
        }

        return appProperties.getProperty(propName);
    }

    /**
     * Returns the propery value as Long
     * @param propName
     * @return
     */
    public static long getPropertiesLong(String propName) {
        if (appProperties == null) {
            readProperties();
        }

        return Long.parseLong(appProperties.getProperty(propName));
    }

    /**
     * Returns the propery value as Int
     * @param propName
     * @return
     */
    public static int getPropertiesInt(String propName) {
        if (appProperties == null) {
            readProperties();
        }

        return Integer.parseInt(appProperties.getProperty(propName));
    }

    /**
     * Static Method to Read Properties as save in static properties object appProperties
     */
    private static void readProperties() {
        InputStream propertiesStream = null;
        try {
            appProperties = null;
            // Get appropriate file stream (Jar, File System, OS)
            LOAPropertiesUtil prop = new LOAPropertiesUtil();
            propertiesStream = prop.getFileStream();
            // Setup Properties
            appProperties = new Properties();
            appProperties.load(propertiesStream);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("QIUPropertiesBean Exception is ------------------------" + e);
            appProperties = new Properties();
            ResourceBundle rs = ResourceBundle.getBundle("LOA");
            System.out.println("QIUPropertiesBean  --------------------- rs" + rs);
            Iterator iter = rs.keySet().iterator();
            String key = null;
            String value = null;
            while (iter.hasNext()) {
                key = (String)iter.next();
                value = rs.getString(key);
                System.out.println("QIUPropertiesBean --------------------- key" + key + ":;value=" + value);
                appProperties.setProperty(key, value);
            }

        } finally {

            // Close stream
            try {
                if (propertiesStream != null) {
                    propertiesStream.close();
                }
            } catch (IOException e) {
                System.out.println("Unable to close the Input stream " + e);
            }
        }
    }

    /**
     * Method to return InputSteam based on Jar, File System, OS
     * @return
     */
    public InputStream getFileStream() {
        InputStream in = null;
        ClassLoader servletClassLoader = this.getClass().getClassLoader();
        // Externalizing Environment.properties
        try {
            in = servletClassLoader.getSystemResourceAsStream("LOA.properties");
        } catch (Exception e) {
            System.out.println("No Environment Properties File Found -" + e);
        }
        return in;
    }
}
