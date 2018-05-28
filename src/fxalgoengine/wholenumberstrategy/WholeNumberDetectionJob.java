package fxalgoengine.wholenumberstrategy;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.google.common.cache.CacheBuilder;

import fxalgoengine.emailer.EmailManager;
import fxalgoengine.marketdata.Candle;
import fxalgoengine.marketdata.Granularity;
import fxalgoengine.marketdata.Instrument;
import fxalgoengine.marketdata.MarketData;
import fxalgoengine.marketdata.OandaMarketData;
import net.jodah.expiringmap.ExpiringMap;


public class WholeNumberDetectionJob implements Job{
	static final Logger LOGGER = Logger.getLogger(WholeNumberDetectionJob.class.getName());	

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		try{
			LOGGER.info("Detecting any whole number intersection with hammer candle. Granularity: " + context.getJobDetail().getJobDataMap().get("Granularity"));
			detectWholeNumberIntersection((Granularity) context.getJobDetail().getJobDataMap().get("Granularity"));
		}catch(Throwable e){
			LOGGER.error("Whole Number calculation based on latest market price failed!!!");
			LOGGER.error(e.getMessage());
		}		
	}
	
	private boolean isLondonSession(){
		LocalDateTime now = LocalDateTime.ofInstant(Instant.now(), ZoneId.of("Europe/London"));
		return now.getHour() >= 7 && now.getHour() <= 17;
	}
	
	private void detectWholeNumberIntersection(Granularity granularity) throws IOException{
		if(!isLondonSession()){
			LOGGER.info("Outside London Session");
			return;
		}
		
		String emailText = "";
		for(Instrument inst: Instrument.values()){
			MarketData md = OandaMarketData.getMarketData(inst, granularity, 3);
			Candle latestCandle = md.getCandles().last();
			//Candle previousCandle = md.getCandles().first();
			for(Double wn : WholeNumberCalculatorJob.getWholeNumbers().get(inst)){
				if(/*isHammer(latestCandle) && */
						withinRange(latestCandle, wn) /*&& 
						latestCandle.getVolume() > previousCandle.getVolume()*/){
					LOGGER.info("Whole Number intersection Detected!!!");

					String append = "\r\n" + inst + " " + granularity + " Lvl:" + String.format("%.4f", wn) + 
							" O: " + latestCandle.getOpenMid() + " H: " + latestCandle.getHighMid() + 
							" L: " + latestCandle.getLowMid() +	" C: " + latestCandle.getCloseMid();
					LOGGER.info(append);
					emailText += append;
				}
			}
		}
		
		if(!"".equals(emailText)){
			LOGGER.debug("SENDING EMAIL: " + emailText);
			EmailManager.sendEmail(emailText, "borakk@gmail.com", "WholeNumberStrategyEngine");
		}
	}
	
	private boolean withinRange(Candle c, double price){
		return price > c.getLowMid() && price < c.getHighMid();
	}
	
	private boolean isHammer(Candle c){
		if(c.getCloseMid() > c.getOpenMid()){
	        return (Math.abs(c.getHighMid() - c.getCloseMid()) + Math.abs(c.getLowMid() - c.getOpenMid())) > (Math.abs(c.getCloseMid() - c.getOpenMid()) * 2);
	    }
	    else{
	        return (Math.abs(c.getHighMid() - c.getOpenMid()) + Math.abs(c.getLowMid() - c.getCloseMid())) > (Math.abs(c.getOpenMid() - c.getCloseMid()) * 2);
	    }
	}

}
