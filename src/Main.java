//Data Collection Dates and Times

//America: 1/22/17 6:00
//Mexico:  1/22/17 6:45 somehow a guy in Wales got a Tweet in this section
//Brazil:  1/22/17 7:41 only one tweet from one guy who's not even from Brazil that got retweeted 100 times :( (can't use brazil now)

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;

public class Main {
	private static Twitter twitter;
	private static File sampleStorage;

	public static void main(String[] args) {
		CountryKeys.init();
		sampleStorage = new File("Australia.txt");
		try
		{
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(sampleStorage, true));
			out.close();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		twitter = TwitterFactory.getSingleton();
		getTweets();
	}

	private static Query getQuery(String keywords) {
		String curCountry = "Australia";
		Query query = new Query("climate change").geoCode(CountryKeys.getLocation(curCountry),
				CountryKeys.getRadius(curCountry), "mi");
		query.setCount(1000);
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
//		List<Status> existingTweets = getStoredData(sampleStorage);
		try
		{
			ObjectOutputStream output = new ObjectOutputStream(
					new BufferedOutputStream(new FileOutputStream(sampleStorage, true)));
			boolean flag;
			for (Status status : Tweets)
			{
				output.writeObject(status);
//				flag = false;
//				if (!existingTweets.isEmpty())
//					for (Status existingStatus : existingTweets)
//					{
//						if (status.getText().equals(existingStatus.getText()))
//						{
//							flag = true;
//						}
//					}
//				if (flag == false)
//				{
//					existingTweets.add(status);
//				}
			}
//			for (Status status : existingTweets)
//			{
//				output.writeObject(status);
//			}
//			output.writeObject(Tweets.get(0));
			output.close();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	private static List<Status> getStoredData(File file) {
		List<Status> tweets = new ArrayList<Status>();
		try
		{
			ObjectInputStream input = new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)));
			if (input.available() > 0)
				tweets.add((Status) input.readObject());
			else
			{
				input.close();
				return null;
			}
			input.close();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return tweets;
	}

}
