package com;

import java.io.IOException;
import java.util.List;

import twitter4j.Location;
import twitter4j.Paging;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

public class JavaTwit1 {
	/*private final static String CONSUMER_KEY = "2s7odGLKFoV69wCnK77tqw";

	private final static String CONSUMER_KEY_SECRET =

	"G0EcwBDIV1Tavp3mJmcxhur5nYWb46uHpUnKHPjepeE";*/
	
	private final static String CONSUMER_KEY = "2XC641KqO2h7qK0wrMZzg";

	private final static String CONSUMER_KEY_SECRET =

	"r2rWDSxXoLml8MsJ6hOHNZOGsnKXA1DFeAmvEzS0bzs";

	public void start() throws TwitterException, IOException {

		Twitter twitter = new TwitterFactory().getInstance();

		twitter.setOAuthConsumer(CONSUMER_KEY, CONSUMER_KEY_SECRET);

		// here's the difference

		String accessToken = getSavedAccessToken();

		String accessTokenSecret = getSavedAccessTokenSecret();

		AccessToken oathAccessToken = new AccessToken(accessToken,

		accessTokenSecret);

		twitter.setOAuthAccessToken(oathAccessToken);

		// end of difference		https://twitter.com/ColorsTV

		//twitter.updateStatus("Colors TV .....!!");

		System.out.println("Screen name :  "+ twitter.getScreenName());
		System.out.println("\nMy Timeline:");

		// I'm reading your timeline

		/*Paging paging = new Paging(1, 10);
		ResponseList<Status> list = twitter.getUserTimeline("ColorsTV",paging);

		for (Status each : list) {

			System.out.println("Sent by: @" + each.getUser().getScreenName()

			+ " - " + each.getUser().getName() + "\n" + each.getText()

			+ "\n Location : "+each.getUser().getLocation()+"\n getFavouritesCount  : "+each.getUser().getFavouritesCount() +
			"\n getDescription : "+each.getUser().getDescription()+"\n\n");

		}*/
		
		
		for (int page = 1; page <= 2; page++) {
	        System.out.println("\nPage: " + page);
	        Query query = new Query("#MakesMeStronger"); // trending right now
	        /*query.setRpp(100);
	        query.setSince(page);*/
	        /*query.setSince("2013-01-01");
	        query.setUntil("2013-07-05");*/
	        QueryResult qr = twitter.search(query);
	        List<Status> qrTweets = qr.getTweets();

	        if(qrTweets.size() == 0) break;

	        for(Status t : qrTweets) {
	            System.out.println("\n\n" +t.getId() + " - " + t.getCreatedAt() + ": " + t.getText()+ "\n User : "+t.getUser().getName());
	        }
	    }
	}
		
		
		
		
		
		/*ResponseList<Location> locations;
        locations = twitter.getAvailableTrends();
        System.out.println("Showing available trends");
        for (Location location : locations) {
            System.out.println(location.getName() + " (woeid:" + location.getWoeid() + ")");
        }
        System.out.println("done.");*/
		
		
	
		



	private String getSavedAccessTokenSecret() {

		// consider this is method to get your previously saved Access Token

		// Secret

		return "p0j9N3dpvs26RaLYXE0Rxr3JcjIhJc7oUfcXyRvHo";

	}

	private String getSavedAccessToken() {

		// consider this is method to get your previously saved Access Token

		return "388824237-l4YS7H3SVMmYrPaJEED8bkNIontNULrSFGjuKsaY";

	}

	public static void main(String[] args) throws Exception {

		new JavaTwit1().start();

	}

}
