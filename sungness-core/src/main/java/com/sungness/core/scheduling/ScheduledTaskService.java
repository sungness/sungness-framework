package com.sungness.core.scheduling;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.CronTask;
import org.springframework.scheduling.config.IntervalTask;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.config.TriggerTask;
import org.springframework.scheduling.support.ScheduledMethodRunnable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

/**
 * 调度任务处理类
 * Created by wanghongwei on 6/13/16.
 */
@Service
public class ScheduledTaskService {

    private static final Logger log = LoggerFactory.getLogger(ScheduledTaskService.class);

    @Autowired
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;

    private Map<String, ScheduledTaskRegistrar> taskRegistrarMap;

    @Autowired
    public void setTaskRegistrarMap(Map<String, ScheduledTaskRegistrar> taskRegistrarMap) {
        this.taskRegistrarMap = taskRegistrarMap;
    }

    public void getTaskList() {
        int poolSize = threadPoolTaskScheduler.getPoolSize();
        int activeCount = threadPoolTaskScheduler.getActiveCount();
        log.debug("poolSize=" + poolSize);
        log.debug("activeCount=" + activeCount);
        log.debug("completedTaskCount=" + threadPoolTaskScheduler.getScheduledThreadPoolExecutor().getCompletedTaskCount());
        BlockingQueue<Runnable> queue =
                threadPoolTaskScheduler.getScheduledThreadPoolExecutor().getQueue();
        for (Runnable runnable: queue) {
            log.debug(runnable.getClass().toString());
        }

        for (ScheduledTaskRegistrar taskRegistrar: taskRegistrarMap.values()) {
            List<TriggerTask> triggerTasks = taskRegistrar.getTriggerTaskList();
            for (TriggerTask triggerTask: triggerTasks) {
                log.debug("triggerTask:" + triggerTask.getRunnable().getClass().getSimpleName()
                        + "," + triggerTask.getTrigger().toString());
            }
            List<CronTask> cronTasks = taskRegistrar.getCronTaskList();
            for (CronTask cronTask: cronTasks) {
                log.debug("cronTask:" + cronTask.getRunnable().getClass().getSimpleName()
                        + "," + cronTask.getTrigger().toString());
            }

            //固定频率调度任务(定义两次调度开始时间之间的时间间隔)
            List<IntervalTask> fixedRateTasks = taskRegistrar.getFixedRateTaskList();
            for (IntervalTask intervalTask: fixedRateTasks) {
                ScheduledMethodRunnable scheduledMethodRunnable = (ScheduledMethodRunnable)intervalTask.getRunnable();
                log.debug("fixedRateTask:" + scheduledMethodRunnable.getTarget().getClass().getSimpleName()
                        + ",getInitialDelay=" + intervalTask.getInitialDelay()
                        + ",getInterval=" + intervalTask.getInterval());
            }
            //固定延迟调度任务(定义前次调度结束与后一次调度开始之间的时间间隔)
            List<IntervalTask> fixedDelayTasks = taskRegistrar.getFixedDelayTaskList();
            for (IntervalTask intervalTask: fixedDelayTasks) {
                ScheduledMethodRunnable scheduledMethodRunnable = (ScheduledMethodRunnable)intervalTask.getRunnable();
                log.debug("fixedDelayTask:" + scheduledMethodRunnable.getTarget().getClass().getSimpleName()
                        + ",getInitialDelay=" + intervalTask.getInitialDelay()
                        + ",getInterval=" + intervalTask.getInterval());
                String appName = scheduledMethodRunnable.getTarget().getClass().getSimpleName();
//                if (appName.equals("CrawlerLoginApp")) {
//                    scheduledMethodRunnable.run();
//                    log.debug("手动调度完成");
//                }
            }
        }
    }
}
