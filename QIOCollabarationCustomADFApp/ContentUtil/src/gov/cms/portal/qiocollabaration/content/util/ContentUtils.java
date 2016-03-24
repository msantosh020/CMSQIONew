package gov.cms.portal.qiocollabaration.content.util;

import java.util.List;

import oracle.stellent.ridc.IdcClient;
import oracle.stellent.ridc.IdcClientException;
import oracle.stellent.ridc.IdcClientManager;
import oracle.stellent.ridc.IdcContext;
import oracle.stellent.ridc.model.DataBinder;
import oracle.stellent.ridc.model.DataObject;
import oracle.stellent.ridc.model.DataResultSet;
import oracle.stellent.ridc.protocol.ServiceResponse;

public class ContentUtils {
    public ContentUtils() {
        super();
    }
    private static IdcClient idcClient;
    private static final String IDCSERVICE = "IdcService";

    public ContentUtils(String idcUrl, String userName) {
        super();
        try {
            IdcClientManager manager = new IdcClientManager();
            idcClient = manager.createClient(idcUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] ar) {
        ContentUtils contUtils = new ContentUtils("idc://hovm1014.keste.com:4444", "weblogic");
        IdcContext userContext = new IdcContext("weblogic");
        // IdcService=RENDITION_PARAMETERS&dDocName=ITEM-004767748263&dID=2302&renditionName=Thumbnail
        DataBinder dataBinder = null;
        dataBinder = idcClient.createBinder();
        dataBinder.putLocal("dDocName", "ITEM-004767748263");
        ServiceResponse response;
        try {
            response = idcClient.sendRequest(userContext, dataBinder);
            DataBinder serverBinder = response.getResponseAsBinder();
            System.out.println(serverBinder);
            serverBinder.getLocalData().get("width");
            DataResultSet resultSet = serverBinder.getResultSet("manifest");
            List<DataObject> rows = resultSet.getRows();
            //  System.out.println(rows.get(0).get("extRenditionWidth"));
            System.out.println(rows.get(1).get("extRenditionWidth"));
            //  System.out.println(rows.get(2).get("extRenditionWidth"));
            System.out.println(resultSet);
        } catch (IdcClientException e) {
            e.printStackTrace();
        }
    }
}
