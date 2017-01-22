import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Iterator;
import java.util.List;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;

public class Main {
	private static Twitter twitter;

	public static void main(String[] args) {
		CountryKeys.init();
		twitter = TwitterFactory.getSingleton();
		getTweets();
	}

	private static Query getQuery(String keywords) {
		String curCountry = "America";
		Query query = new Query("climate change").geoCode(CountryKeys.getLocation(curCountry),
				CountryKeys.getRadius(curCountry), "mi");
		query.setCount(100);
		return query;
	}

	private static List<Status> getTweets() {
		int count = 0;
		Status lastTweet = null;
		int RTcount = 0;
		List<Status> curTweets = null;
		try
		{
			QueryResult result = twitter.search(getQuery("climate change"));
			curTweets = result.getTweets();
			for (Status status : curTweets)
			{
				if (lastTweet != null && status.isRetweet() && status.getText().equals(lastTweet.getText()))
				{
					System.out.println(++RTcount);
				} else
				{
					System.out.println(status.getUser().getScreenName() + ": " + status.getText());
					RTcount = 0;
				}
				lastTweet = status;
				count++;
			}
			StoreData(curTweets);
			System.out.println(count);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return curTweets;
	}

	private static void StoreData(List<Status> Tweets) {
		List<Status> existingTweets = getStoredData("storedTweets.txt");
		try
		{
			ObjectOutputStream output = new ObjectOutputStream(
					new BufferedOutputStream(new FileOutputStream(new File("storedTweets.txt"))));
			boolean flag;
			for (Status status : Tweets)
			{
				flag = false;
				for (Status existingStatus : existingTweets)
				{
					if (status.getText().equals(existingStatus.getText()))
					{
						flag = true;
					}
				}
				if(flag == false){
					existingTweets.add(status);
				}
			}
			for(Status status : existingTweets){
				output.writeObject(status);
			}
			output.close();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	private static List<Status> getStoredData(String fileName) {
		List<Status> tweets = null;
		try
		{
			ObjectInputStream input = new ObjectInputStream(new BufferedInputStream(new FileInputStream(new File("storedTweets.txt"))));
			if(input.available() != 0)
				tweets.add((Status) input.readObject());
			input.close();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return tweets;
	}

}
