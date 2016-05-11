package gov.cms.portal.qiocollabaration.extension.view.common.util;

public class StringUtil {
    public StringUtil() {
        super();
    }
    
    public static boolean isNonEmpty(String str) {
        boolean isNonEmpty = true;
        if (str == null || str.isEmpty()) {
            isNonEmpty = false;
        }
        return isNonEmpty;
    }
}
