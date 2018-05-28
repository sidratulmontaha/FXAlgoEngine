package fxalgoengine.wholenumberstrategy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import fxalgoengine.marketdata.Instrument;
import fxalgoengine.marketdata.OandaMarketData;
import fxalgoengine.marketdata.Price;
import fxalgoengine.marketdata.Prices;

public class WholeNumberCalculatorJob implements Job{

	private static final Logger LOGGER = Logger.getLogger(WholeNumberCalculatorEngine.class.getName());	

	private static Map<Instrument, List<Double>> wholeNumbers = new HashMap<Instrument, List<Double>>();
	
	public synchronized static Map<Instrument, List<Double>> getWholeNumbers() {
		return wholeNumbers;
	}

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		try {
			LOGGER.info("Calculating Whole Numbers based on latest market price");
			calculateWholeNumbers();			
		} catch (Throwable e) {
			LOGGER.error("Whole Number calculation based on latest market price failed!!!");
			LOGGER.error(e.getMessage());				
		}
	}		
	
	public static void calculateWholeNumbers() throws IOException{
		Prices prices = OandaMarketData.getCurrentPrices(Arrays.asList(Instrument.values()));
		
		for(Price p: prices.getPrices()){
			List<Double> wn = new ArrayList<Double>();
			double price = p.getBid();
						
			if(price < 10){
				double roundedPrice = Math.round(price * 100.0)/100.0;
				
				wn.add(roundedPrice);
				
				for(int i=1; i<11; i++){
					wn.add(roundedPrice + 0.005 * i);
				}
				
				for(int i=1; i<11; i++){
					wn.add(roundedPrice - 0.005 * i);
				}
			}else if(price < 1000){
				double roundedPrice = Math.round(price);
				
				wn.add(roundedPrice);
				
				for(int i=1; i<11; i++){
					wn.add(roundedPrice + 0.5 * i);
				}
				
				for(int i=1; i<11; i++){
					wn.add(roundedPrice - 0.5 * i);
				}
			}

			Collections.sort(wn);
						
			getWholeNumbers().put(Instrument.valueOf(p.getInstrument()), wn);
		}
	}
}