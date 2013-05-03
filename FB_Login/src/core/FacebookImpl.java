package core;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.brickred.socialauth.AbstractProvider;
import org.brickred.socialauth.AuthProvider;
import org.brickred.socialauth.Contact;
import org.brickred.socialauth.Permission;
import org.brickred.socialauth.Profile;
import org.brickred.socialauth.exception.ProviderStateException;
import org.brickred.socialauth.exception.ServerDataException;
import org.brickred.socialauth.exception.SocialAuthConfigurationException;
import org.brickred.socialauth.exception.SocialAuthException;
import org.brickred.socialauth.exception.UserDeniedPermissionException;
import org.brickred.socialauth.oauthstrategy.OAuthStrategyBase;
import org.brickred.socialauth.util.AccessGrant;
import org.brickred.socialauth.util.Constants;
import org.brickred.socialauth.util.HttpUtil;
import org.brickred.socialauth.util.MethodType;
import org.brickred.socialauth.util.OAuthConfig;
import org.brickred.socialauth.util.Response;
import org.json.JSONArray;
import org.json.JSONObject;


	public class FacebookImpl extends AbstractProvider implements AuthProvider,
    Serializable {

private static final long serialVersionUID = 8644510564735754296L;
private static final String PROPERTY_DOMAIN = "graph.facebook.com";
private static final String AUTHORIZATION_URL = "https://graph.facebook.com/oauth/authorize?client_id=%1$s&display=page&redirect_uri=%2$s";
private static final String ACCESS_TOKEN_URL = "https://graph.facebook.com/oauth/access_token?client_id=%1$s&redirect_uri=%2$s&client_secret=%3$s&code=%4$s";
private static final String PROFILE_URL = "https://graph.facebook.com/me?access_token=%1$s";
private static final String CONTACTS_URL = "https://graph.facebook.com/me/friends?access_token=%1$s";
private static final String UPDATE_STATUS_URL = "https://graph.facebook.com/me/feed";
private static final String PROFILE_IMAGE_URL = "http://graph.facebook.com/%1$s/picture";
private static final String PUBLIC_PROFILE_URL = "http://www.facebook.com/profile.php?id=";
private final Log LOG = LogFactory.getLog(FacebookImpl.class);

private String accessToken;
private String redirectUri;
private Permission scope;
private Properties properties;
private boolean isVerify;
private OAuthConfig config;

//  set this to the list of extended permissions you want
private static final String[] AllPerms = new String[] { "publish_stream",
            "email", "user_birthday", "user_location" };
private static final String[] AuthPerms = new String[] { "email",
            "user_birthday", "user_location" };

/**
* Reads properties provided in the configuration file
* 
* @param props
*            Properties for consumer key
* @param scope
*            scope is a permission setting. It can be
*            AuthProvider.AUTHENTICATION_ONLY or
*            AuthProvider.ALL_PERMISSIONS
*/
public FacebookImpl(final Properties props) throws Exception {
    try {
            this.properties = props;
            config = OAuthConfig.load(this.properties, PROPERTY_DOMAIN);
    } catch (IllegalStateException e) {
            throw new SocialAuthConfigurationException(e);
    }

    if (config.get_consumerSecret().length() <= 0) {
            throw new SocialAuthConfigurationException(
                            "graph.facebook.com.consumer_secret value is null");
    }
    if (config.get_consumerKey().length() <= 0) {
            throw new SocialAuthConfigurationException(
                            "graph.facebook.com.consumer_key value is null");
    }
}

/**
* This is the most important action. It redirects the browser to an
* appropriate URL which will be used for authentication with the provider
* that has been set using setId()
* 
*/
@Override
public String getLoginRedirectURL(final String redirectUri) {
    LOG.info("Determining URL for redirection");
    setProviderState(true);
    this.redirectUri = redirectUri;
    String url = String.format(AUTHORIZATION_URL, config.get_consumerKey(),
                    redirectUri);
    StringBuffer result = new StringBuffer();
    boolean isFirstSet = false;
    if (Permission.AUHTHENTICATE_ONLY.equals(scope)) {
            result.append(AuthPerms[0]);
            for (int i = 1; i < AuthPerms.length; i++) {
                    if (isFirstSet) {
                            result.append(",").append(AuthPerms[i]);
                    }
            }
    } else {
            result.append(AllPerms[0]);
            for (int i = 1; i < AllPerms.length; i++) {
                    result.append(",").append(AllPerms[i]);
            }
    }
    url += "&scope=" + result.toString();
    LOG.info("Redirection to following URL should happen : " + url);
    return url;
}

/**
* Verifies the user when the external provider redirects back to our
* application.
* 
* @return Profile object containing the profile information
* @param request
*            Request object the request is received from the provider
* @throws Exception
*/

public Profile verifyResponse(final HttpServletRequest httpReq)
            throws Exception {
    LOG.info("Retrieving Access Token in verify response function");
    if (httpReq.getParameter("error_reason") != null
                    && "user_denied".equals(httpReq.getParameter("error_reason"))) {
            throw new UserDeniedPermissionException();
    }
    if (!isProviderState()) {
            throw new ProviderStateException();
    }
    String code = httpReq.getParameter("code");
    if (code == null || code.length() == 0) {
            throw new SocialAuthException("Verification code is null");
    }
    LOG.debug("Verification Code : " + code);
    String acode;
    try {
            acode = URLEncoder.encode(code, "UTF-8");
    } catch (Exception e) {
            acode = code;
    }
    String authURL = String.format(ACCESS_TOKEN_URL,
                    config.get_consumerKey(), redirectUri,
                    config.get_consumerSecret(), acode);
    Response response;
    try {
            response = HttpUtil.doHttpRequest(authURL,
                            MethodType.GET.toString(), null, null);
    } catch (Exception e) {
            throw new SocialAuthException("Error in url : " + authURL, e);
    }
    String result;
    try {
            result = response.getResponseBodyAsString(Constants.ENCODING);
    } catch (IOException io) {
            throw new SocialAuthException(io);
    }
    Integer expires = null;
    String[] pairs = result.split("&");
    for (String pair : pairs) {
            String[] kv = pair.split("=");
            if (kv.length != 2) {
                    throw new SocialAuthException("Unexpected auth response from "
                                    + authURL);
            } else {
                    if (kv[0].equals("access_token")) {
                            accessToken = kv[1];
                    }
                    if (kv[0].equals("expires")) {
                            expires = Integer.valueOf(kv[1]);
                    }
                    LOG.debug("Access Token : " + accessToken);
                    LOG.debug("Expires : " + expires);
            }
    }
    if (accessToken != null && expires != null) {
            isVerify = true;
            LOG.debug("Obtaining user profile");
            return authFacebookLogin(accessToken, expires);
    } else {
            throw new SocialAuthException(
                            "Access token and expires not found from " + authURL);
    }
}

private Profile authFacebookLogin(final String accessToken,
            final int expires) throws Exception {
    String presp;
    String aToken;
    try {
            aToken = URLEncoder.encode(accessToken, Constants.ENCODING);
    } catch (Exception e) {
            aToken = accessToken;
    }
    String profileurl = String.format(PROFILE_URL, aToken);
    try {
            Response response = HttpUtil.doHttpRequest(profileurl,
                            MethodType.GET.toString(), null, null);
            presp = response.getResponseBodyAsString(Constants.ENCODING);
    } catch (Exception e) {
            throw new SocialAuthException("Error while getting profile from "
                            + profileurl, e);
    }
    try {
            LOG.debug("User Profile : " + presp);
            JSONObject resp = new JSONObject(presp);
            Profile p = new Profile();
            p.setValidatedId(resp.getString("id"));
            p.setFirstName(resp.getString("first_name"));
            p.setLastName(resp.getString("last_name"));
            p.setEmail(resp.getString("email"));
            if (resp.has("location")) {
                    p.setLocation(resp.getJSONObject("location").getString("name"));
            }
            if (resp.has("birthday")) {
                    p.setDob(resp.getString("birthday"));
            }
            if (resp.has("gender")) {
                    p.setGender(resp.getString("gender"));
            }
            p.setProfileImageURL(String.format(PROFILE_IMAGE_URL,
                            resp.getString("id")));
            String locale = resp.getString("locale");
            if (locale != null) {
                    String a[] = locale.split("_");
                    p.setLanguage(a[0]);
                    p.setCountry(a[1]);
            }

            return p;

    } catch (Exception ex) {
            throw new ServerDataException(
                            "Failed to parse the user profile json : " + presp, ex);
    }
}

/**
* Updates the status on the chosen provider if available. This may not be
* implemented for all providers.
* 
* @param msg
*            Message to be shown as user's status
* @throws Exception
*/

@Override
public void updateStatus(final String msg) throws Exception {
    LOG.info("Updating status : " + msg);
    if (!isVerify) {
            throw new SocialAuthException(
                            "Please call verifyResponse function first to get Access Token and then update status");
    }
    if (msg == null || msg.trim().length() == 0) {
            throw new ServerDataException("Status cannot be blank");
    }
    StringBuilder strb = new StringBuilder();
    strb.append("access_token=").append(accessToken).append("&");
    strb.append("message=").append(msg);

    Response serviceResponse;
    try {
            serviceResponse = HttpUtil.doHttpRequest(UPDATE_STATUS_URL,
                            MethodType.POST.toString(), strb.toString(), null);

            if (serviceResponse.getStatus() != 200) {
                    throw new SocialAuthException(
                                    "Status not updated. Return Status code :"
                                                    + serviceResponse.getStatus());
            }
    } catch (Exception e) {
            throw new SocialAuthException(e);
    }

}

/**
* Gets the list of contacts of the user. this may not be available for all
* providers.
* 
* @return List of contact objects representing Contacts. Only name will be
*         available
*/

@Override
public List<Contact> getContactList() throws Exception {
    if (!isProviderState()) {
            throw new ProviderStateException();
    }
    if (!isVerify) {
            throw new SocialAuthException(
                            "Please call verifyResponse function first to get Access Token");
    }
    List<Contact> plist = new ArrayList<Contact>();
    String contactURL = String.format(CONTACTS_URL, accessToken);
    LOG.info("Fetching contacts from " + contactURL);
    String respStr;
    try {
            Response response = HttpUtil.doHttpRequest(contactURL,
                            MethodType.GET.toString(), null, null);
            respStr = response.getResponseBodyAsString(Constants.ENCODING);
    } catch (Exception e) {
            throw new SocialAuthException("Error while getting contacts from "
                            + contactURL);
    }
    try {
            LOG.debug("User Contacts list in json : " + respStr);
            JSONObject resp = new JSONObject(respStr);
            JSONArray data = resp.getJSONArray("data");
            LOG.debug("Found contacts : " + data.length());
            for (int i = 0; i < data.length(); i++) {
                    JSONObject obj = data.getJSONObject(i);
                    Contact p = new Contact();
                    p.setFirstName(obj.getString("name"));
                    p.setProfileUrl(PUBLIC_PROFILE_URL + obj.getString("id"));
                    plist.add(p);
            }
    } catch (Exception e) {
            throw new ServerDataException(
                            "Failed to parse the user profile json : " + respStr, e);
    }
    return plist;
}

/**
* Logout
*/
@Override
public void logout() {
    accessToken = null;
}

/**
* 
* @param p
*            Permission object which can be Permission.AUHTHENTICATE_ONLY,
*            Permission.ALL, Permission.DEFAULT
*/
@Override
public void setPermission(final Permission p) {
    LOG.debug("Permission requested : " + p.toString());
    this.scope = p;
}

/**
* Makes HTTP request to a given URL.It attaches access token in URL.
* 
* @param url
*            URL to make HTTP request.
* @param methodType
*            Method type can be GET, POST or PUT
* @param params
*            Not using this parameter in Google API function
* @param headerParams
*            Parameters need to pass as Header Parameters
* @param body
*            Request Body
* @return Response object
* @throws Exception
*/
@Override
public Response api(final String url, final String methodType,
            final Map<String, String> params,
            final Map<String, String> headerParams, final String body)
            throws Exception {
    Response response = null;
    if (!isProviderState()) {
            throw new ProviderStateException();
    }
    if (!isVerify) {
            throw new SocialAuthException(
                            "Please call verifyResponse function first to get Access Token");
    }
    if (MethodType.GET.toString().equals(methodType)) {
            String urlStr = url;
            try {
                    char separator = url.indexOf('?') == -1 ? '?' : '&';
                    urlStr = urlStr + separator + "access_token=" + accessToken;
                    LOG.debug("Calling URL : " + urlStr);
                    response = HttpUtil.doHttpRequest(urlStr,
                                    MethodType.GET.toString(), null, headerParams);
            } catch (Exception e) {
                    throw new SocialAuthException(
                                    "Error while making request to URL : " + urlStr, e);
            }
    } else if (MethodType.PUT.toString().equals(methodType)
                    || MethodType.POST.toString().equals(methodType)) {
            StringBuilder strb = new StringBuilder();
            strb.append("access_token=").append(accessToken);
            if (body != null) {
                    strb.append("&").append(body);
            }
            try {
                    LOG.debug("Calling URL : " + url);
                    LOG.debug("Request Body : " + strb.toString());
                    response = HttpUtil.doHttpRequest(url, methodType,
                                    strb.toString(), headerParams);
            } catch (Exception e) {
                    throw new SocialAuthException(
                                    "Error while making request to URL : " + url, e);
            }

    }
    return response;
}

@Override
public AccessGrant getAccessGrant() {
	// TODO Auto-generated method stub
	return null;
}

@Override
public String getProviderId() {
	// TODO Auto-generated method stub
	return null;
}

@Override
public Profile getUserProfile() throws Exception {
	// TODO Auto-generated method stub
	return null;
}

@Override
public void setAccessGrant(AccessGrant arg0) throws Exception {
	// TODO Auto-generated method stub
	
}

@Override
public Response uploadImage(String arg0, String arg1, InputStream arg2)
		throws Exception {
	// TODO Auto-generated method stub
	return null;
}

@Override
public Profile verifyResponse(Map<String, String> arg0) throws Exception {
	// TODO Auto-generated method stub
	return null;
}

@Override
protected OAuthStrategyBase getOauthStrategy() {
	// TODO Auto-generated method stub
	return null;
}

@Override
protected List<String> getPluginsList() {
	// TODO Auto-generated method stub
	return null;
}

}


