package gov.cms.portal.qiocollabaration.content.exception;

public class CustomException extends Exception {

    // private String errorCode = "Unknown_Exception";

    @SuppressWarnings("compatibility:6181046598527155259")
    private static final long serialVersionUID = -7901660472438231343L;

    public CustomException(Throwable throwable) {
        super(throwable);
    }

    public CustomException(String string, Throwable throwable) {
        super(string, throwable);
    }

    public CustomException(String string) {
        super(string);
    }

    public CustomException() {
        super();
    }

    //    public CustomException(String message, String errorCode) {
    //        super(message);
    //        this.errorCode = errorCode;
    //    }
}
