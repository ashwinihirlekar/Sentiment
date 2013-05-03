package core;

import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.brickred.socialauth.AuthProvider;
import org.brickred.socialauth.Contact;
import org.brickred.socialauth.Profile;
import org.brickred.socialauth.SocialAuthConfig;
import org.brickred.socialauth.SocialAuthManager;
import org.brickred.socialauth.util.SocialAuthUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


	@RequestMapping(value = "/user")
	@Controller
	public class NewController {
		private static final String SCOPE = "email,offline_access,user_about_me,user_birthday,read_friendlists";
		private static final String REDIRECT_URI = "http://localhost:8080/platform-services/social/facebook/callback";
		private static final String CLIENT_ID = "449204668500332";
		private static final String APP_SECRET = "c428497d6852d1dc178e869fdc7b6042";
		private static final String DIALOG_OAUTH = "https://www.facebook.com/dialog/oauth";
		private static final String ACCESS_TOKEN = "https://graph.facebook.com/oauth/access_token";

		@RequestMapping(value = "/login", method = RequestMethod.GET)
		public void login(HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			System.out.println("inside new controller");
			
			
			//Create an instance of SocialAuthConfgi object
			   SocialAuthConfig config = SocialAuthConfig.getDefault();

			  //load configuration. By default load the configuration from oauth_consumer.properties. 
			  //You can also pass input stream, properties object or properties file name.
			   config.load("oauth_consumer.properties");

			  //Create an instance of SocialAuthManager and set config
			  SocialAuthManager manager = new SocialAuthManager();
			  manager.setSocialAuthConfig(config);

			  //URL of YOUR application which will be called after authentication
			  String successUrl = "http://opensource.brickred.com/socialauthdemo/socialAuthSuccessAction.do";

			  String id="facebook";
			// get Provider URL to which you should redirect for authentication.
			  // id can have values "facebook", "twitter", "yahoo" etc. or the OpenID URL
			  String url = manager.getAuthenticationUrl(id, successUrl);

			  HttpSession session=request.getSession(true);
			// Store in session
			  session.setAttribute("authManager", manager);
			  
			  
			  SocialAuthManager manager1 = (SocialAuthManager)session.getAttribute("authManager");

			  // call connect method of manager which returns the provider object. 
			  // Pass request parameter map while calling connect method. 
			   AuthProvider provider = manager1.connect(SocialAuthUtil.getRequestParametersMap(request));

			  // get profile
			  Profile p = provider.getUserProfile();

			  // you can obtain profile information
			  System.out.println(p.getFirstName());

			  // OR also obtain list of contacts
			  List<Contact> contactsList = provider.getContactList();
		}
	

	/*@RequestMapping(value = "/callback", params = "code", method = RequestMethod.GET)
	@ResponseBody
	public void accessCode(@RequestParam("code") String code,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			response.setContentType("text/html");
			String responseString = IntegrationBase.readURLGET(ACCESS_TOKEN,
					new String[] { "client_id", "redirect_uri", "code",
							"client_secret" }, new String[] { CLIENT_ID,
							REDIRECT_URI, code, APP_SECRET });

			response.getWriter().write(responseString);
			response.flushBuffer();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/callback", params = "error_reason", method = RequestMethod.GET)
	@ResponseBody
	public void error(@RequestParam("error_reason") String errorReason,
			@RequestParam("error") String error,
			@RequestParam("error_description") String description,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, description);
			System.out.println(errorReason);
			System.out.println(error);
			System.out.println(description);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/
}
