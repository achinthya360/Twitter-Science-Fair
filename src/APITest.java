import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class APITest {
	public static void main(String[] args) {
		
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
		  .setOAuthConsumerKey("b32CCPSnEvQHDraYu51WbQmcH")
		  .setOAuthConsumerSecret("WEooM1QDYSI4oFMp8fXRgkcaQoC60iQ4BmMmM8g3yEcRsS55v5")
		  .setOAuthAccessToken("816034614263181312-dZHCA1XJJfj8ZANU48rOW3sHIUMhxTB")
		  .setOAuthAccessTokenSecret("11B68yMNbSqG9WbHMdZFlFU4QCCPL4PlPb6lapDA8uJWN");
		
		TwitterFactory tf = new TwitterFactory(cb.build());
		Twitter twitter = tf.getInstance();
		
		Query query = new Query("japan climate change");
		query.setCount(5);
		QueryResult result;
		try
		{
			result = twitter.search(query);
			for (Status status : result.getTweets())
			{
			System.out.println("@" + status.getUser().getScreenName() + ":" + status.getText() + status.getWithheldInCountries());
			}
		} 
		catch (TwitterException e)
		{
			e.printStackTrace();
		}
	}
}
