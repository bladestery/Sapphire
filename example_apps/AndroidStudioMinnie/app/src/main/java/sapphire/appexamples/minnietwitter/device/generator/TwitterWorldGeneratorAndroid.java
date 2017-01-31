package sapphire.appexamples.minnietwitter.device.generator;

import java.net.InetSocketAddress;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import sapphire.appexamples.minnietwitter.app.TagManager;
import sapphire.appexamples.minnietwitter.app.Timeline;
import sapphire.appexamples.minnietwitter.app.Tweet;
import sapphire.appexamples.minnietwitter.app.TwitterManager;
import sapphire.appexamples.minnietwitter.app.User;
import sapphire.appexamples.minnietwitter.app.UserManager;
import sapphire.kernel.server.KernelServer;
import sapphire.kernel.server.KernelServerImpl;
import sapphire.oms.OMSServer;

import android.util.Log;

public class TwitterWorldGeneratorAndroid {
	static final int EVENTS_PER_USER = 100;
	static final int USERS_NUM = 10;
	static final int TAG_NUM = 5;
	
	static final int MAX_TAGS_PER_TWEET = 2;
	static final int MAX_MENTIONS_PER_TWEET = 2;
	
	private static final Random gen = new Random();
	private static final String[] events = {"TWEET", "TWEET", "TWEET", "RETWEET", "FAVORITE"};
	
	private static String getTweet() {
		int numTags = gen.nextInt(MAX_TAGS_PER_TWEET);
		int numMentions = gen.nextInt(MAX_MENTIONS_PER_TWEET);
		
		String tweet = "Tweet ";
		
		for (int i = 0; i < numTags; i++) {
			tweet += getTag() + " "; 
		}
		
		for (int i = 0; i < numMentions; i++) {
			tweet += "@" + getUserName() + " "; 
		}
		
		return tweet;
	}

	private static String getTag() {
		return "#tag" + gen.nextInt(TAG_NUM);
	}

	private static String getUserName() {
		return "user" + gen.nextInt(USERS_NUM);
	}
	
	private static int getId(int max) {
		return gen.nextInt(max);
	}
	
	private static void printStatistics() {
		
	}

	public static void main(String[] args) {
		Registry registry;
		List<Timeline> timelines = new ArrayList<Timeline>();

		//StringBuilder sb = new StringBuilder();
		Log.d("output", "In MAIN");
		try {
			registry = LocateRegistry.getRegistry("157.82.159.46",22346);
			Log.d("output", "Found Registry");
			OMSServer server = (OMSServer) registry.lookup("SapphireOMS");
			System.out.println(server);
            KernelServer nodeServer = new KernelServerImpl(new InetSocketAddress("10.0.2.15", 22346), new InetSocketAddress("157.82.159.46", 22346));
            Log.d("output", "Created nodeServer");

            /* Get Twitter and User Manager */
			TwitterManager tm = (TwitterManager) server.getAppEntryPoint();
            UserManager userManager = tm.getUserManager();
            TagManager tagManager = tm.getTagManager();
			Log.d("output", "Got Twitter and User Manager");

            /* Create the users */
            List<User> users = new ArrayList<User>();
            for (int i = 0; i < USERS_NUM; i++) {
            	long start = System.nanoTime();
            	User u = userManager.addUser("user" + Integer.toString(i), "user" + Integer.toString(i));
            	timelines.add(i, u.getTimeline());
            	long end = System.nanoTime();
            	//sb.append("Added user " + Integer.toString(i) + " in:" + ((end - start) / 1000000) + "ms");
				Log.d("output", "Added user " + Integer.toString(i) + " in:" + ((end - start) / 1000000) + "ms");
            }
            
			//sb.append("Added users!");
			Log.d("output", "Added users!");
            
            /* Generate events */
            for (int i = 0; i < USERS_NUM * EVENTS_PER_USER; i++) {
            	int userId = i % USERS_NUM;
            	String event = events[gen.nextInt(events.length)];
            	
            	if (event.equals("TWEET")) {
            		long start = System.nanoTime();
            		String tweet = getTweet();
            		Timeline t = timelines.get(userId);
            		t.tweet(tweet);
            		long end = System.nanoTime();
            		//sb.append("@user" + Integer.toString(userId) + " tweeted: " + tweet + " in: " + ((end - start) / 1000000) + "ms");
					Log.d("output", "@user" + Integer.toString(userId) + " tweeted: " + tweet + " in: " + ((end - start) / 1000000) + "ms");
            		continue;
            	}
            	
            	if (event.equals("RETWEET") && userId > 0) {
            		/* Retweet one of the last 10 tweets of some user */
            		long start = System.nanoTime();
            		int id = getId(userId);
            		List<Tweet> lastTweets = timelines.get(id).getTweets(0, 10);
            		if (lastTweets.size() > 0) {
            			Tweet t = lastTweets.get(gen.nextInt(lastTweets.size()));
            			timelines.get(userId).retweet(t);
            			long end = System.nanoTime();
            			//sb.append("@user" + Integer.toString(userId) + " retweeted from @user" + Integer.toString(id) + " in: " + ((end - start) / 1000000) + "ms");
						Log.d("output", "@user" + Integer.toString(userId) + " retweeted from @user" + Integer.toString(id) + " in: " + ((end - start) / 1000000) + "ms");
            		}
            		continue;
            	}
            	
            	if (event.equals("FAVORITE") && userId > 0) {
            		long start = System.nanoTime();
            		/* Favorite one of the last 10 tweets of some user */
            		int id = getId(userId);
            		List<Tweet> lastTweets = timelines.get(id).getTweets(0, 10);
            		if (lastTweets.size() > 0) {
            			timelines.get(id).favorite(lastTweets.get(gen.nextInt(lastTweets.size())).getId(), "user" + Integer.toString(userId));
            			long end = System.nanoTime();
            			//sb.append("@user" + Integer.toString(userId) + " favorited from @user" + Integer.toString(id) + " in: " + ((end - start) / 1000000) + "ms");
						Log.d("output", "@user" + Integer.toString(userId) + " favorited from @user" + Integer.toString(id) + " in: " + ((end - start) / 1000000) + "ms");
            		}
            	}
            }
            
            //sb.append("Done populating!");
			Log.d("output", "Done populating!");
          } catch (Exception e) {
			e.printStackTrace();
		}

		//return sb.toString();
	}
}
