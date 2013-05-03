package core;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.brickred.socialauth.SocialAuthConfig;
import org.brickred.socialauth.SocialAuthManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.code.facebookapi.FacebookJsonRestClient;
import com.google.code.facebookapi.IFacebookRestClient;


	@RequestMapping(value = "/user")
	@Controller
	public class FacebookController {
		private static final String SCOPE = "email,offline_access,user_about_me,user_birthday,read_friendlists";
	
		private static final String CLIENT_ID = "449204668500332";
		private static final String APP_SECRET = "c428497d6852d1dc178e869fdc7b6042";
		//private static final String REDIRECT_URI = "http://evening-castle-1742.herokuapp.com/";
		
		
		private static final String REDIRECT_URI = "https://apps.facebook.com/javafbappnamespace";
		private static final String DIALOG_OAUTH = "https://www.facebook.com/dialog/oauth";
		

		private static final String ACCESS_TOKEN = "https://graph.facebook.com/oauth/access_token";

		@RequestMapping(value = "/facebook", method = RequestMethod.GET)
		public void signin(HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			System.out.println("signin:: inside fb controller");
			try { 
				response.sendRedirect(DIALOG_OAUTH + "? client_id=" + CLIENT_ID
						+ "&redirect_uri=" + REDIRECT_URI + "&scope=" + SCOPE);
				
				//response.sendRedirect("/forms.jsp");
				
				HttpSession session= request.getSession(true);
				System.out.println("signin::  session id " + session.getId());
				
				 IFacebookRestClient userClient = new FacebookJsonRestClient(apiKey, secretKey);
				
				/* SocialAuthConfig config = SocialAuthConfig.getDefault();

				  //load configuration. By default load the configuration from oauth_consumer.properties. 
				  //You can also pass input stream, properties object or properties file name.
				   config.load("");

				  //Create an instance of SocialAuthManager and set config
				  SocialAuthManager manager = new SocialAuthManager();
				  manager.setSocialAuthConfig(config);

				  //URL of YOUR application which will be called after authentication
				  String successUrl = "/forms.jsp";

				  // get Provider URL to which you should redirect for authentication.
				  // id can have values "facebook", "twitter", "yahoo" etc. or the OpenID URL
				  String url = manager.getAuthenticationUrl("facebook", successUrl);
				  
				  System.out.println("url----"+url);
				  // Store in session
				  session.setAttribute("authManager", manager);*/
			
			
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	

	@RequestMapping(value = "/callback", params = "code", method = RequestMethod.GET)
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
	}
}
