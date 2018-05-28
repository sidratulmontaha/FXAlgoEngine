package fxalgoengine.launcher;

import java.io.IOException;
import java.util.Arrays;

import org.quartz.SchedulerException;

import fxalgoengine.emailer.EmailManager;
import fxalgoengine.marketdata.Granularity;
import fxalgoengine.marketdata.Instrument;
import fxalgoengine.marketdata.OandaMarketData;
import fxalgoengine.wholenumberstrategy.WholeNumberCalculatorEngine;
import fxalgoengine.wholenumberstrategy.WholeNumberStrategyEngine;

public class AlgoEngineStarter {
	public static void main(String[] args) throws IOException, SchedulerException{
		
		WholeNumberCalculatorEngine wnce = new WholeNumberCalculatorEngine();		
		wnce.start();
		
		WholeNumberStrategyEngine wnse = new WholeNumberStrategyEngine();
		wnse.start();
	}
}
