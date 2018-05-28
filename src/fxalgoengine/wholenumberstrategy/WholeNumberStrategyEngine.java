package fxalgoengine.wholenumberstrategy;

import java.util.HashSet;

import org.apache.log4j.Logger;
import org.quartz.CronScheduleBuilder;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import fxalgoengine.marketdata.Granularity;

public class WholeNumberStrategyEngine {
	private Scheduler scheduler;
	private static final Logger LOGGER = Logger.getLogger(WholeNumberStrategyEngine.class.getName());
	
	private String M5CronSchedule = "0 1,6,11,16,21,26,31,36,41,46,51,56 * * * ? *";
	private String M15CronSchedule = "0 1,16,31,46 * * * ? *";
	private String M30CronSchedule = "0 1,31 * * * ? *";
	private String H1CronSchedule = "0 1 * * * ? *";
	
	public static final String ALERT_CACHE_KEY = "WHOLE_NUMBER_STRATEGY_ALERT";
	
	public void start() throws SchedulerException{
		LOGGER.info("Scheduling WholeNumberStrategyEngine");
		scheduler = new StdSchedulerFactory().getScheduler();
		Trigger m5Trigger = TriggerBuilder.newTrigger().withIdentity("M5WholeNumberStrategyEngine")
				.withSchedule(CronScheduleBuilder.cronSchedule(M5CronSchedule))
				.build();
		Trigger m15Trigger = TriggerBuilder.newTrigger().withIdentity("M15WholeNumberStrategyEngine")
				.withSchedule(CronScheduleBuilder.cronSchedule(M15CronSchedule))
				.build();
		Trigger m30Trigger = TriggerBuilder.newTrigger().withIdentity("M30WholeNumberStrategyEngine")
				.withSchedule(CronScheduleBuilder.cronSchedule(M30CronSchedule))
				.build();
		Trigger h1Trigger = TriggerBuilder.newTrigger().withIdentity("H1WholeNumberStrategyEngine")
				.withSchedule(CronScheduleBuilder.cronSchedule(H1CronSchedule))
				.build();
		
		scheduler.start();
		
		JobDetail m5Job = JobBuilder.newJob(WholeNumberDetectionJob.class).build();
		m5Job.getJobDataMap().put("Granularity", Granularity.M5);
		
		JobDetail m15Job = JobBuilder.newJob(WholeNumberDetectionJob.class).build();
		m15Job.getJobDataMap().put("Granularity", Granularity.M15);
		
		JobDetail m30Job = JobBuilder.newJob(WholeNumberDetectionJob.class).build();
		m30Job.getJobDataMap().put("Granularity", Granularity.M30);
		
		JobDetail h1Job = JobBuilder.newJob(WholeNumberDetectionJob.class).build();
		h1Job.getJobDataMap().put("Granularity", Granularity.H1);
		
		scheduler.scheduleJob(m5Job, m5Trigger);
		scheduler.scheduleJob(m15Job, m15Trigger);
		//scheduler.scheduleJob(m30Job, m30Trigger);
		//scheduler.scheduleJob(h1Job, h1Trigger);
	}
}
