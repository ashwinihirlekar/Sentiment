package core;

import java.io.IOException;

import twitter4j.DirectMessage;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.http.AccessToken;

public class JavaTwit1 {
	private final static String CONSUMER_KEY = "2s7odGLKFoV69wCnK77tqw";

	private final static String CONSUMER_KEY_SECRET =

	"G0EcwBDIV1Tavp3mJmcxhur5nYWb46uHpUnKHPjepeE";

	public void start() throws TwitterException, IOException {

		Twitter twitter = new TwitterFactory().getInstance();

		twitter.setOAuthConsumer(CONSUMER_KEY, CONSUMER_KEY_SECRET);

		// here's the difference

		String accessToken = getSavedAccessToken();

		String accessTokenSecret = getSavedAccessTokenSecret();

		AccessToken oathAccessToken = new AccessToken(accessToken,

		accessTokenSecret);

		twitter.setOAuthAccessToken(oathAccessToken);

		// end of difference

		twitter.updateStatus("All is well....status from my office for Demo");

		System.out.println("Screen name of tejas = "+ twitter.getScreenName());
		System.out.println("\nMy Timeline:");

		// I'm reading your timeline

		ResponseList<Status> list = twitter.getHomeTimeline();

		for (Status each : list) {

			System.out.println("Sent by: @" + each.getUser().getScreenName()

			+ " - " + each.getUser().getName() + "\n" + each.getText()

			+ "\n");

		}

	}

	private String getSavedAccessTokenSecret() {

		// consider this is method to get your previously saved Access Token

		// Secret

		return "OzjK4d531RK8I6RRdd1gd213vB56DpafMHFjO4yYtQ";

	}

	private String getSavedAccessToken() {

		// consider this is method to get your previously saved Access Token

		return "388824237-J6XEtU5sXqPWPjhV8lrxskMTB0Fd9CnfhtM3cskz";

	}

	public static void main(String[] args) throws Exception {

		new JavaTwit1().start();

	}

}
