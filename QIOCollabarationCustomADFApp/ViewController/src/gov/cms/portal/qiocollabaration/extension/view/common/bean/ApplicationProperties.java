package gov.cms.portal.qiocollabaration.extension.view.common.bean;

import javax.servlet.ServletRequest;

import oracle.adf.share.ADFContext;

public class ApplicationProperties {
    public ApplicationProperties() {
        super();
    }

    private String portNumber;
    private String serverName;

    public void setPortNumber(String portNumber) {
        this.portNumber = portNumber;
    }

    public String getPortNumber() {
        if (portNumber == null) {
            portNumber = "" + ((ServletRequest)ADFContext.getCurrent().getEnvironment().getRequest()).getLocalPort();
        }
        return portNumber;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getServerName() {
        if (serverName == null) {
            serverName = ((ServletRequest)ADFContext.getCurrent().getEnvironment().getRequest()).getLocalName();
        }
        return serverName;
    }
}
