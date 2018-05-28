package fxalgoengine.marketdata;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

public class OandaMarketData {
	private static final Logger LOGGER = Logger.getLogger(OandaMarketData.class.getName());
	
	private static String apiToken = "c8c427a48b34bbbbd8885ef01fb5cf8a-96e4359e0df2e7487eb81bd1f4a5d546";
	
	private static Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new JsonDeserializer<LocalDateTime>() {
	    @Override
		public LocalDateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
				throws JsonParseException {
	    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'");
	    	LocalDateTime dateTime = LocalDateTime.parse(json.getAsJsonPrimitive().getAsString(), formatter);
			return dateTime;
		}
	}).create();
	
	public static MarketData getMarketData(Instrument instrument, Granularity granularity, int lookbackCount) throws IOException{
		String url = "https://api-fxtrade.oanda.com/v1/candles?instrument=" + instrument + 
				"&granularity=" + granularity + "&count=" + lookbackCount + "&candleFormat=midpoint";
		
		URL urlObj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) urlObj.openConnection();
		con.setRequestProperty("Authorization", "Bearer " + apiToken);

		int responseCode = con.getResponseCode();
		
		if(responseCode != 200){
			RuntimeException re = new RuntimeException("Failed to get MarketData");
			LOGGER.error(re.getMessage());
			throw re;
		}

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
		
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		
		String responseStr = response.toString();

		LOGGER.info(responseStr);
		
		MarketData mktData = gson.fromJson(responseStr, MarketData.class);
		
		mktData.setCandles(new TreeSet<Candle>(mktData.getCandles().stream().filter(c -> c.isComplete()).collect(Collectors.toSet())));
		
		return mktData;		
	}
	
	public static Prices getCurrentPrices(List<Instrument> instruments) throws IOException{		
		String url = "https://api-fxtrade.oanda.com/v1/prices?instruments=" + StringUtils.join(instruments, "%2C");
		
		URL urlObj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) urlObj.openConnection();
		con.setRequestProperty("Authorization", "Bearer " + apiToken);

		int responseCode = con.getResponseCode();
		
		if(responseCode != 200){
			RuntimeException re = new RuntimeException("Failed to get Prices");
			LOGGER.error(re.getMessage());
			throw re;
		}

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
		
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		
		String responseStr = response.toString();

		LOGGER.info(responseStr);
		
		Prices prices = gson.fromJson(responseStr, Prices.class);
		
		return prices;
	}
}
