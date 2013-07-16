package com;

import java.io.FileWriter;
import java.io.IOException;

import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

public class JavaTwit1 {
	private final static String CONSUMER_KEY = "ovWPxdqUXGc2xaQaMQ0cVg";

	private final static String CONSUMER_KEY_SECRET =

	"Erkm5NEw6wMvc5Yg7jASUDvoV0QQgmNgeCJLjpqueo";

	public void start() throws TwitterException, IOException {

		Twitter twitter = new TwitterFactory().getInstance();

		twitter.setOAuthConsumer(CONSUMER_KEY, CONSUMER_KEY_SECRET);

		// here's the difference

		String accessToken = getSavedAccessToken();

		String accessTokenSecret = getSavedAccessTokenSecret();

		twitter4j.auth.AccessToken oathAccessToken = new AccessToken(accessToken,

		accessTokenSecret);

		twitter.setOAuthAccessToken(oathAccessToken);

		// end of difference

		twitter.updateStatus("New update ...............");

		System.out.println("Screen name of tejas = "+ twitter.getScreenName());
		System.out.println("\nMy Timeline:");

		FileWriter fwrite = new FileWriter("D:\\twitter.txt");
		
		
		// I'm reading your timeline

		ResponseList<Status> list = twitter.getHomeTimeline();

		String str="";
		for (Status each : list) {

			System.out.println("Sent by: @" + each.getUser().getScreenName()

			+ " - " + each.getUser().getName() + "\n" + each.getText()

			+ "\n");
			
			
			str=str+each.getText()+"\n";System.out.println("string "+str);
			fwrite.write(str+"\r\n");
			fwrite.write("\r\n");
			
			fwrite.close(); 

		}

	}

	private String getSavedAccessTokenSecret() {

		// consider this is method to get your previously saved Access Token

		// Secret

		return "PTww0AbuoF6C3nACU8yaPG7oSsZilm98Zi5fxcpos";

	}

	private String getSavedAccessToken() {

		// consider this is method to get your previously saved Access Token

		return "388824237-Xa6TCVhVGp6O5MEcmrDANXHRqTJr0bIoQWEceMqB";

	}

	public static void main(String[] args) throws Exception {

		new JavaTwit1().start();

	}
	

}
