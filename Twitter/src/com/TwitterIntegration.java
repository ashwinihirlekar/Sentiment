package com;

import java.util.List;

import twitter4j.ResponseList;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.http.AccessToken;
import twitter4j.http.RequestToken;

public class TwitterIntegration {
	private static final String username = "akshartejas1989@gmail.com";
	private static final String password = "sahajanand";

	public static void main(String[] args) throws TwitterException {

		
		Twitter twitter=new Twitter(username, password);
		 try {System.out.println("hii");
		     twitter.updateStatus("My tweet");
		     System.out.println("Success!!!");
		 } catch (TwitterException e) {
		   
		 }

}}
