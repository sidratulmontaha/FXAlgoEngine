package fxalgoengine.wholenumberstrategy;

import java.util.concurrent.TimeUnit;

import fxalgoengine.marketdata.Instrument;
import net.jodah.expiringmap.ExpiringMap;

public class AlertCache {
	static ExpiringMap<Instrument, Double> alertCache;
	
	public static void init(){
		alertCache = ExpiringMap.builder().expiration(15, TimeUnit.MINUTES).build();
	}
	
	public static void addItem(Instrument inst, double wholeNumber){
		alertCache.put(inst, wholeNumber);
	}
}
