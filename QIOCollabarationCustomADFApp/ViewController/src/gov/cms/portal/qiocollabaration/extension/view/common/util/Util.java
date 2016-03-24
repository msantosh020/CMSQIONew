package gov.cms.portal.qiocollabaration.extension.view.common.util;

import java.util.Map;

import org.apache.myfaces.trinidad.context.RequestContext;

public class Util {
    public Util() {
        super();
    }

    public static String getPageFlowScopeParamValue(String key) {
        Map pageFlowScope = RequestContext.getCurrentInstance().getPageFlowScope();
        return (String)pageFlowScope.get(key);
    }
    
}
