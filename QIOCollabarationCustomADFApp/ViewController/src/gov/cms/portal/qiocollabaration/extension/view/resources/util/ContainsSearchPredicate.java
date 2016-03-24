package gov.cms.portal.qiocollabaration.extension.view.resources.util;

import java.io.Serializable;

import org.apache.commons.collections.Predicate;

public class ContainsSearchPredicate implements Predicate, Serializable {
    @SuppressWarnings("compatibility:-7633011214579068908")
    private static final long serialVersionUID = 1L;
    private final String searchValue;

    public ContainsSearchPredicate(String value) {
        super();
        searchValue = value;
    }

    public boolean evaluate(Object object) {
        if (object instanceof Integer) {
            if (object != null) {
                return object.toString().contains(searchValue) ? true : false;
            } else {
                return false;
            }
        }
        if (object instanceof String) {
            if (object != null) {
                return object.toString().toUpperCase().contains(searchValue.toUpperCase()) ? true : false;
            } else {
                return false;
            }
        }
        return false;
    }
}


