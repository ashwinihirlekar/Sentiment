package simple;


import java.io.IOException;

import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterException;
import twitter4j.TwitterStream;

import twitter4j.TwitterStreamFactory;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;

public class TwitterTest {
 static EPServiceProvider epService;

 public static void main(String[] args) throws TwitterException, IOException {

  // Creating and registering the CEP listener

  com.espertech.esper.client.Configuration config1 = new com.espertech.esper.client.Configuration();
  config1.addEventType("HappyMessage", HappyMessage.class.getName());
  epService = EPServiceProviderManager.getDefaultProvider(config1);
  String expression = "select user, sum(ctr) from simple.HappyMessage.win:time(20 seconds) having sum(ctr) > 2";

  EPStatement statement = epService.getEPAdministrator().createEPL(
    expression);
  HappyEventListener happyListener = new HappyEventListener();
  statement.addListener(happyListener);

  ConfigurationBuilder cb = new ConfigurationBuilder();
  cb.setDebugEnabled(true);
  //simple http form based authentication, you can use oAuth if you have one, check Twitter4j documentation
  
  cb.setOAuthConsumerKey("ovWPxdqUXGc2xaQaMQ0cVg");
  cb.setOAuthConsumerSecret("Erkm5NEw6wMvc5Yg7jASUDvoV0QQgmNgeCJLjpqueo");
  cb.setOAuthAccessToken("388824237-Xa6TCVhVGp6O5MEcmrDANXHRqTJr0bIoQWEceMqB");
  cb.setOAuthAccessTokenSecret("PTww0AbuoF6C3nACU8yaPG7oSsZilm98Zi5fxcpos");
  // creating the twitter listener

  Configuration cfg = cb.build();		
  TwitterStream twitterStream = new TwitterStreamFactory(cfg)
    .getInstance();
  StatusListener listener = new StatusListener() {
   public void onStatus(Status status) {

    if (status.getText().indexOf("lol") > 0) {
     System.out.println("********* lol found *************");
     raiseEvent(epService, status.getUser().getScreenName(),
       status);
    }
   }

   public void onDeletionNotice(
     StatusDeletionNotice statusDeletionNotice) {
    System.out.println("Got a status deletion notice id:"
      + statusDeletionNotice.getStatusId());
   }

   public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
    System.out.println("Got track limitation notice:"
      + numberOfLimitedStatuses);
   }

   public void onScrubGeo(long userId, long upToStatusId) {
    System.out.println("Got scrub_geo event userId:" + userId
      + " upToStatusId:" + upToStatusId);
   }

   public void onException(Exception ex) {
    ex.printStackTrace();
   }
  };
 
  twitterStream.addListener(listener);
  //twitterStream.setStatusListener(listener);
    
  //
  twitterStream.sample();

 }

 private static void raiseEvent(EPServiceProvider epService, String name,
   Status status) {
  HappyMessage msg = new HappyMessage();
  msg.setUser(status.getUser().getScreenName());
  epService.getEPRuntime().sendEvent(msg);
 }

}

