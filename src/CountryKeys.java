import java.util.HashMap;

import twitter4j.GeoLocation;

public class CountryKeys {
	private static HashMap<String, GeoLocation> countries;
	private static HashMap<String, Integer> radii;
	
	public static void init() {
		initCountries();
	}

	private static void initCountries() {
		countries = new HashMap<String, GeoLocation>();
//		countries.put("California", new GeoLocation(36.7783, 119.4179));
		countries.put("America", new GeoLocation(37.0902, -95.7129));
		countries.put("UK", new GeoLocation(55.3781, -3.4360));
//		countries.put("China", new GeoLocation(35.8617, 104.1954));
		countries.put("Mexico", new GeoLocation(23.6345, -102.5528));
		countries.put("India", new GeoLocation(20.5937, 78.9629));
		countries.put("Brazil", new GeoLocation(-14.2350, -51.9253));
		
		radii = new HashMap<String, Integer>();
//		radii.put("California", 200);
		radii.put("America", 1300);
		radii.put("UK", 400);
		radii.put("China", 2000);
		radii.put("Mexico", 1000);
		radii.put("India", 1500);
		radii.put("Brazil", 1200);
	}
	
	public static int getRadius(String country){
		return radii.get(country);
	}
	
	public static GeoLocation getLocation(String country){
		return countries.get(country);
	}
}
