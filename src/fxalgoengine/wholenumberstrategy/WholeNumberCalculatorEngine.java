package fxalgoengine.wholenumberstrategy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.quartz.CronScheduleBuilder;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import fxalgoengine.marketdata.Instrument;
import fxalgoengine.marketdata.OandaMarketData;
import fxalgoengine.marketdata.Price;
import fxalgoengine.marketdata.Prices;

public class WholeNumberCalculatorEngine {

	private Scheduler scheduler;
	private static final Logger LOGGER = Logger.getLogger(WholeNumberCalculatorEngine.class.getName());
	
	private final String cronSchedule = "0 0 6,12,18,23 ? * MON-FRI";
	
	public void start() throws IOException, SchedulerException{		
		WholeNumberCalculatorJob.calculateWholeNumbers();
		
		LOGGER.info("Scheduling WholeNumberCalculator");
		scheduler = new StdSchedulerFactory().getScheduler();
		Trigger trigger = TriggerBuilder.newTrigger().withIdentity("wholeNumberCalculatorTrigger")
				.withSchedule(CronScheduleBuilder.cronSchedule(cronSchedule))
				.build();
		scheduler.start();
		scheduler.scheduleJob(JobBuilder.newJob(WholeNumberCalculatorJob.class).build(), trigger);
	}	
	
	public void stop() throws SchedulerException{
		scheduler.shutdown();
	}
}
