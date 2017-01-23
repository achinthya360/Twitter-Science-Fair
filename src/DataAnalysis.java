
// this class will rate tweets by education level

//FOR LATER: make sure to count all exact copy tweets and all retweets
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import twitter4j.Status;

public class DataAnalysis {
	private static List<Status> America;
	private static ArrayList<String> goodKeywords;
	private static ArrayList<String> badKeywords;

	public static void main(String[] args) {
//		analyzeAmerica();
	}

	public static void analyzeAmerica() {
		try
		{
			America = new ArrayList<Status>();
			ObjectInputStream in = new ObjectInputStream(
					new BufferedInputStream(new FileInputStream(new File("America.txt"))));
			Status trialTweet = (Status) in.readObject();
			System.out.println(trialTweet.getUser().getScreenName() + ": " + trialTweet.getText());
			America.add((Status) in.readObject());
			System.out.println(America.get(0).getText());
			System.out.println(analysisAlgorithm(America));
		} catch (Exception e)
		{
			e.printStackTrace();
		}

	}

	private static int analysisAlgorithm(List<Status> tweetsToAnalyze) {
		String[] goodWords = { "climate change", "is not a myth", "the guardian", "leading", "leader", "scientist", "energy",
				"renewable", "global warming", "solutions", "progress", "Al Gore", "conserve", "environment", "nature", "addressing", "weather channel" };
		// goodKeywords = (ArrayList<String>) Arrays.asList(goodWords);
		String[] badWords = { "is a myth", "huffington", "hoax", "conservative", "ulterior motive", "conspiracy", "hide"};
		// badKeywords = (ArrayList<String>) Arrays.asList(badWords);
		int points = 0;
		for (Status status : tweetsToAnalyze)
		{
			if(!status.getText().contains("climate change")){
				points--;
			}
			for (String phrase : goodWords)
			{
				if (status.getText().contains(phrase))
				{
					points++;
					System.out.println("hello");
				}
			}
			for (String phrase : badWords)
			{
				if (status.getText().contains(phrase))
				{
					points--;
					System.out.println("bye");
				}
			}
		}
		return points;
	}
}
