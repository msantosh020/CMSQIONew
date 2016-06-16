package gov.cms.portal.qiocollabaration.extension.view.login;



/*import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;*/

import com.oblix.access.ObAccessException;

import com.oblix.access.ObConfig;
import com.oblix.access.ObResourceRequest;
import com.oblix.access.ObUserSession;

import java.util.Hashtable;

import javax.faces.application.FacesMessage;

import javax.faces.context.FacesContext;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import oracle.adf.share.ADFContext;
import oracle.adf.share.logging.ADFLogger;

public class OAMLoginBean {
    public OAMLoginBean() {
        super();
    }

  

  public String doLogin()
  {
    String METHOD_NAME = "doOAMLogin"; 
    ObUserSession user = null;
    ObResourceRequest resource = null;
    LOGGER.entering(CLASS_NAME, METHOD_NAME);

    //obtain the servlet request and response objects
    HttpServletRequest request =
      (HttpServletRequest) ADFContext.getCurrent().getEnvironment().getRequest();
    HttpServletResponse response =
      (HttpServletResponse) ADFContext.getCurrent().getEnvironment().getResponse();

    //initialize credential map
    Hashtable cred = new Hashtable();

    //obtain success rule and failure rules
    String successRule = getLoginSuccessUrlNavigationRule();
    successRule =
        successRule == null || successRule.isEmpty()? DEFAULT_LOGIN_SUCCESS_OUTCOME:
        successRule;

    String failureRule = getLoginFailureUrlNavigationRule();
    failureRule =
        failureRule == null || failureRule.isEmpty()? DEFAULT_LOGIN_SUCCESS_OUTCOME:
        failureRule;

    try
    {
      //construct oam protected resource to use for authentication
      String oamProtectedRes =
        "//" + ObConfig.getItem("preferredHost") + request.getContextPath() +
        "/adfAuthentication";
      LOGGER.fine(CLASS_NAME, METHOD_NAME, "OAMResource: " + oamProtectedRes);
      resource = new ObResourceRequest("http", oamProtectedRes, "GET");

      //this should return true if the webcontextroot/adfAuthentication is configured
      //in the OAMpolicy domain
      if (resource.isProtected())
      {
//        char[] username = null; 
//        char[] password = null; 
//        if (request.getParameter("j_username") != null)
//          username = request.getParameter("j_username").toCharArray(); 
//        if (request.getParameter("j_password") != null)
//          password = request.getParameter("j_password").toCharArray(); 

        String username = request.getParameter("j_username");
        String password = request.getParameter("j_password");

        //obtain credentials from input text bindings
        cred.put("userid", username);
        cred.put("password", password);

        //create a new OAM session with given credentials
        user = new ObUserSession(resource, cred);

        //user successfully authenticated
        if (user.getStatus() == ObUserSession.LOGGEDIN)
        {
          //create a session if it doesn't exist already
          HttpSession session = request.getSession(true);

          LOGGER.fine(CLASS_NAME, METHOD_NAME, 
                      "Session Token: " + user.getSessionToken());

          //Create Set Obsso cookie using the session token on the response
          Cookie obcookie =
            new Cookie("ObSSOCookie", user.getSessionToken());
          obcookie.setPath("/");
          obcookie.setDomain(ObConfig.getItem("primaryDomain"));
          response.addCookie(obcookie);

          LOGGER.fine(CLASS_NAME, METHOD_NAME, "Cookie: " + obcookie.getValue());
          String success_url_request_param = "success_url"; 
          FacesContext ctx = FacesContext.getCurrentInstance();
          
          String success_url = request.getParameter(success_url_request_param);
          if (success_url == null)
            success_url = (String) request.getSession(false)
              .getAttribute(success_url_request_param);
          String viewID = getViewId(request); 
          if (success_url == null) 
          {
            if (viewID.contains("businessRolePages/Landing.jspx"))
            {
              success_url = "/webcenter";
            }
            else
            {
              success_url = "/webcenter/faces" + getViewId(request);
            }
          }
            

          LOGGER.fine(CLASS_NAME, METHOD_NAME, "redirecting to " + success_url);
          ctx.getExternalContext().redirect(success_url);
          ctx.responseComplete();

          return successRule;
        }
        else
        {
          FacesMessage errMessage =
            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid Credentials",
                             "Login Failed");
          FacesContext.getCurrentInstance()
            .addMessage(null, errMessage);
          LOGGER.fine(CLASS_NAME, METHOD_NAME, "User Credentials Invalid");
        }
      }
      else
      {
        LOGGER.fine(CLASS_NAME, METHOD_NAME, 
                    "OAMResource not protected in the policy domain");
      }

      //in all other cases return failure rule prompting user to relogin
      return failureRule;
    }
    catch (ObAccessException e)
    {
      FacesMessage errMessage =
        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Login Failed",
                         "Login Failed");
      FacesContext.getCurrentInstance()
        .addMessage(null, errMessage);
      LOGGER.warning(e);
      return failureRule;
    }
    catch (Exception e)
    {
      LOGGER.warning(e);
      FacesMessage errMessage =
        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Login Failed",
                         "Unexpected Exception" + e.getLocalizedMessage());
      FacesContext.getCurrentInstance()
        .addMessage(null, errMessage);
      return failureRule;
    }
  }
  
  private String getViewId(final HttpServletRequest request)
  {
    String viewId = (String) request.getAttribute("oracle.webcenter.webcenterapp.view.shell.WebCenterShellManager.VIEWID");
    if (viewId == null)
    {
      String requestURI = request.getRequestURI();
      int iStartServletPath = requestURI.indexOf('/', 1);
      if (iStartServletPath >= 0)
      {
        int iStartViewId = requestURI.indexOf('/', iStartServletPath + 1);
        if (iStartViewId >= 0)
          viewId = requestURI.substring(iStartViewId);
      }
    }
    return viewId;
  }
  private String getLoginSuccessUrlNavigationRule()
  {
    return "success";
  }
  
  private String getLoginFailureUrlNavigationRule()
  {
    return "failure";
  }
  
 
  private static String DEFAULT_LOGIN_SUCCESS_OUTCOME = "none";
  private static String CLASS_NAME
    = OAMLoginBean.class.getName();
  private static ADFLogger LOGGER =
    ADFLogger.createADFLogger("custom.webcenter.spaces");

}
