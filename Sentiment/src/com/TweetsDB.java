package com;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

public class TweetsDB {
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

		//twitter.updateStatus("Colors  !!!!"); 
			
		System.out.println("Screen name :  "+ twitter.getScreenName());
		System.out.println("\nMy Timeline:");

		// I'm reading your timeline

		ResponseList<Status> list = twitter.getHomeTimeline();

		for (Status each : list) {

			System.out.println("Sent by: @" + each.getUser().getScreenName()

			+ " - " + each.getUser().getName() + "\n" + each.getText()

			+ "\n Location : "+each.getUser().getLocation()+"\n getFavouritesCount  : "+each.getUser().getFavouritesCount() +"\n TimeZone :"+
			each.getUser().getTimeZone()+
			"\n getDescription : "+each.getUser().getDescription()+"\n\n");
			
			int count = 0;
			Connection connection = null;
			Statement statement=null;	
			try {
				System.out.println(" Inside DB code ");
				Class.forName("com.mysql.jdbc.Driver").newInstance();
				connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Ashwini",
						"root","root");
				   
				statement = connection.createStatement();
				String sql = "insert into Sentiment(tweet,sentby,location,FavouriteCount,Timezone) values('" + each.getText() + "','" + each.getUser().getName()
						+ "','" + each.getUser().getLocation() + "'," + each.getUser().getFavouritesCount() +",'" + each.getUser().getTimeZone() + "')";
				
				//String sql = "insert into Sentiment(tweet,sentby) values('" + each.getText() + "','" + each.getUser().getName()+"')";
				count = statement.executeUpdate(sql);
				System.out.println("SQL statement  : " +each.getText());		
				System.out.println("count = " + count);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		
		

	}

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

		new TweetsDB().start();

	}

}
