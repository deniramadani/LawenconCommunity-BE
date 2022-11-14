package com.lawencon.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;

/**
 * @author lawencon05
 */

@Component
public class SchedulerUtil {

	private ExecutorService checkingService = Executors.newCachedThreadPool();
	private ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);

	public void startScheduler(Runnable command, long initialDelay, long period, TimeUnit unit) {
		executorService.scheduleAtFixedRate(() -> {
			checkingService.execute(command);
		}, initialDelay, period, unit);
	}

	public void startSchedulerOnce(Runnable command, long delay, TimeUnit unit) {
		executorService.schedule(() -> {
			checkingService.execute(command);
		}, delay, unit);
	}
}
