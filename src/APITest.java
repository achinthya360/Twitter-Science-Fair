import twitter4j.GeoLocation;
import twitter4j.GeoQuery;
import twitter4j.Place;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class APITest {
	public static void main(String[] args) {
		// APITest object = new APITest();
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true).setOAuthConsumerKey("b32CCPSnEvQHDraYu51WbQmcH")
				.setOAuthConsumerSecret("WEooM1QDYSI4oFMp8fXRgkcaQoC60iQ4BmMmM8g3yEcRsS55v5")
				.setOAuthAccessToken("816034614263181312-dZHCA1XJJfj8ZANU48rOW3sHIUMhxTB")
				.setOAuthAccessTokenSecret("11B68yMNbSqG9WbHMdZFlFU4QCCPL4PlPb6lapDA8uJWN");

		TwitterFactory tf = new TwitterFactory(cb.build());
		Twitter twitter = tf.getInstance();
		GeoLocation US = new GeoLocation(36.7783, 119.4179);
		GeoQuery america = new GeoQuery(US);
		Query query = new Query("climate change");
		query.setCount(100);
		QueryResult result;
		try
		{
			// ResponseList<Place> britishPlaces =
			// (object.reverseGeoCode(britain));;
			// Place place = object.getGeoDetails(britishPlaces.get(0).getId());
			ResponseList<Place> places = twitter.reverseGeoCode(america);
			result = twitter.search(query);
			System.out.println(result.getTweets().size());
			for (Status status : result.getTweets())
			{
				if(!status.isRetweet())
					System.out.println(/*"@" + status.getUser().getScreenName() + ": + */status.getText());
				else
					System.out.println("Retweet: " + status.getRetweetCount() + status.getText());
//				System.out.println(status.isRetweet());
			}
		} catch (TwitterException e)
		{
			e.printStackTrace();
		}
	}

}
