package com;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

public class JavaTwit {				//App name on twitter site= Ashwini Tweet demo   url=https://dev.twitter.com/apps/4182940/

	//private final static String CONSUMER_KEY = "2s7odGLKFoV69wCnK77tqw";

	private final static String CONSUMER_KEY = "2XC641KqO2h7qK0wrMZzg";
	//private final static String CONSUMER_KEY_SECRET = "G0EcwBDIV1Tavp3mJmcxhur5nYWb46uHpUnKHPjepeE";

	private final static String CONSUMER_KEY_SECRET = "r2rWDSxXoLml8MsJ6hOHNZOGsnKXA1DFeAmvEzS0bzs";
	
	public void start() throws TwitterException, IOException {

		Twitter twitter = new TwitterFactory().getInstance();

		twitter.setOAuthConsumer(CONSUMER_KEY, CONSUMER_KEY_SECRET);

		RequestToken requestToken = twitter.getOAuthRequestToken();

		System.out.println("Authorization URL: \n"

		+ requestToken.getAuthorizationURL());

		AccessToken accessToken = null;

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		while (null == accessToken) {

			try {

				System.out.print("Input PIN here: ");

				String pin = br.readLine();

				accessToken = twitter.getOAuthAccessToken(requestToken, pin);

			} catch (TwitterException te) {

				te.printStackTrace();
				System.out.println("Failed to get access token, caused by: "

				+ te.getMessage());

				System.out.println("Retry input PIN");

			}

		}

		System.out.println("Access Token: " + accessToken.getToken());

		System.out.println("Access Token Secret: "

		+ accessToken.getTokenSecret());

		twitter.updateStatus("Best channel......Colors TV ...!!!! ");

	}

	public static void main(String[] args) throws Exception {

		new JavaTwit().start();// run the Twitter client

	}

}
