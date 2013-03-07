/*package com;

import twitter4j.ResponseList;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.http.AccessToken;
import twitter4j.http.RequestToken;

public class TwitMain {

	*//**
	 * @param args
	 *//*
	public static void main(String[] args) {
		// Read the PIN
		
				String pin = request.getParameter("pin");
				
				// Retrieve the request token
				
				RequestToken requestToken = (RequestToken) request.getSession().getAttribute("rt");
				
				// Creates the main object
				
				Twitter twitter = new TwitterFactory().getInstance();
				
				// Ask for an access token
				
				AccessToken accessToken = twitter.getOAuthAccessToken(requestToken, pin);
				
				// Store the token in session
				
				request.getSession().setAttribute("at", accessToken);
				
				// Remove the access token from session
				
				request.getSession().removeAttribute("rt");
				
				// Set the access token on the twitter instance
				
				twitter.setOAuthAccessToken(accessToken);
				
				 
				
				// Now, we can ask for whatever we want!
				
				ResponseList statuses = twitter.getUserTimeline();
	}

}
*/