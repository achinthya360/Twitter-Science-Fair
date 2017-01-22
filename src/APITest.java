import twitter4j.GeoLocation;
import twitter4j.GeoQuery;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

public class APITest {
	public static void main(String[] args) {
		Twitter twitter = TwitterFactory.getSingleton();
		GeoLocation US = new GeoLocation(36.7783, 119.4179);
		GeoQuery america = new GeoQuery(US);
		Query query = new Query("climate change");
		query.setCount(100);
		QueryResult result;
		try
		{
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
