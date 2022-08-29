package com.errand.task.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.errand.task.api.IErrandTaskService;
import com.errand.task.constant.CronFormate;
import com.errand.task.entity.QrtzTaskEntity;
import com.errand.task.job.commonJob.InterfaceJob;
import com.errand.task.search.QrtzTaskSearch;
import com.errand.task.service.IQrtzTaskService;
import com.errand.task.vo.QrtzTaskVo;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.curator.shaded.com.google.common.util.concurrent.AbstractService;
import org.apache.dubbo.config.annotation.Service;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
//import com.nc.framework.service.AbstractService;

import cn.hutool.core.date.DateUtil;
import lombok.SneakyThrows;

/**
 * 应用模块名称:
 * 代码描述:
 * Copyright: Copyright (C) 2021, Inc. All rights reserved.
 * 
 *
 * @author: zixuan.yang
 * @since: 2021/11/22 14:04
 */
@Service(version = "1.0.0", delay = -1, retries = -1, timeout = 60000)
@com.alibaba.dubbo.config.annotation.Service
@org.springframework.stereotype.Service
public class IErrandTaskServiceImpl  implements IErrandTaskService {

	private final Class aClass = InterfaceJob.class;

	@Autowired
	Scheduler scheduler;

	@Autowired
	private IQrtzTaskService qrtzTaskService;

	public String DateToCron(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat(CronFormate.DATEFORMAT_YEAR);
		String formatTimeStr = null;
		if (date != null) {
			formatTimeStr = sdf.format(date);
		}
		return formatTimeStr;
	}

	@Override
	public String DateToCron(String date) {
		Date parse = DateUtil.parse(date, "yyyy-MM-dd HH:mm:ss");
		return DateToCron(parse);

	}

	@Override
	public List<QrtzTaskVo> getAllJobList() {
		List<QrtzTaskVo> qrtzTaskVoList = new ArrayList<>();
		try {
			GroupMatcher<JobKey> matcher = GroupMatcher.anyJobGroup();
			Set<JobKey> jobKeySet = scheduler.getJobKeys(matcher);
			for (JobKey jobKey : jobKeySet) {
				List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
				for (Trigger trigger : triggers) {
					QrtzTaskVo qrtzTaskVo = new QrtzTaskVo();
					// trigger.get
					this.wrapScheduleJob(qrtzTaskVo, scheduler, jobKey, trigger);
					qrtzTaskVoList.add(qrtzTaskVo);
				}
			}

		} catch (SchedulerException e) {
			e.printStackTrace();
		}
		return qrtzTaskVoList;

	}

	@Override
	public List<QrtzTaskVo> getRunningJobList() {
		List<QrtzTaskVo> jobList = null;
		try {
			List<JobExecutionContext> executingJobList = scheduler.getCurrentlyExecutingJobs();
			jobList = new ArrayList<>(executingJobList.size());
			for (JobExecutionContext executingJob : executingJobList) {
				QrtzTaskVo scheduleJob = new QrtzTaskVo();
				JobDetail jobDetail = executingJob.getJobDetail();
				JobKey jobKey = jobDetail.getKey();
				Trigger trigger = executingJob.getTrigger();
				this.wrapScheduleJob(scheduleJob, scheduler, jobKey, trigger);
				jobList.add(scheduleJob);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jobList;
	}

	/**
	 * 添加任务，可传入参数
	 * 
	 * @param qrtzTaskVo
	 * @param userid
	 * @param username
	 */
	@Override
	public void addJob(QrtzTaskVo qrtzTaskVo, String userid, String username) {
		try {

			// 构建job信息
			JobDetail jobDetail = JobBuilder.newJob(((Job) aClass.newInstance()).getClass())
					.withIdentity(qrtzTaskVo.getJobName(), qrtzTaskVo.getJobGroupName()).build();
			// 表达式调度构建器(即任务执行的时间)
			CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(qrtzTaskVo.getCronExpression());
			// 按新的cronExpression表达式构建一个新的trigger
			CronTrigger trigger = null;
			TriggerBuilder<CronTrigger> withSchedule = TriggerBuilder.newTrigger().withIdentity(qrtzTaskVo.getJobName(), qrtzTaskVo.getJobGroupName())
					.withSchedule(scheduleBuilder);
			trigger = withSchedule.build();

			// 获得JobDataMap，写入数据
			if (qrtzTaskVo != null) {
				trigger.getJobDataMap().put(qrtzTaskVo.getJobName(), qrtzTaskVo);
			}
			if (StringUtils.equals(qrtzTaskVo.getJobStatus(), "NORMAL")) {
				scheduler.scheduleJob(jobDetail, trigger);
			} else if (StringUtils.equals(qrtzTaskVo.getJobStatus(), "PAUSED")) {
				TriggerKey triggerKey = TriggerKey.triggerKey(qrtzTaskVo.getJobName(), qrtzTaskVo.getJobGroupName());
				scheduler.scheduleJob(jobDetail, trigger);
				scheduler.pauseTrigger(triggerKey);
			}
			QrtzTaskEntity qrtzTaskEntity = new QrtzTaskEntity();
			BeanUtils.copyProperties(qrtzTaskEntity, qrtzTaskVo);
			qrtzTaskEntity.setCreateDate(new Date());
			qrtzTaskEntity.setCreateUserid(userid);
			qrtzTaskEntity.setCreateUsername(username);
			if (qrtzTaskEntity.getId() != 0) {
				qrtzTaskService.updateQrtzTaskEntity(qrtzTaskEntity, userid, username);
			} else {
				qrtzTaskService.addQrtzTaskEntity(qrtzTaskEntity, userid, username);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void pauseJob(String jobName, String jobGroupName, String userid, String username) {
		try {

			if (StringUtils.equals(checkJobStatus(jobName, jobGroupName), "NORMAL")) {
				scheduler.pauseJob(JobKey.jobKey(jobName, jobGroupName));
				QrtzTaskSearch search = new QrtzTaskSearch();
				search.setJobName(jobName);
				search.setJobGroupName(jobGroupName);
				QrtzTaskEntity qrtzTask = qrtzTaskService.getQrtzTask(search);
				qrtzTask.setJobStatus("PAUSED");
				qrtzTaskService.updateQrtzTaskEntity(qrtzTask, userid, username);
			}
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}

	/**
	 * STATE_BLOCKED 4 阻塞
	 * STATE_COMPLETE 2 完成
	 * STATE_ERROR 3 错误
	 * STATE_NONE -1 不存在
	 * STATE_NORMAL 0 正常
	 * STATE_PAUSED 1 暂停
	 **/
	public String checkJobStatus(String jobName, String jobGroupName) {
		TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroupName);
		Trigger.TriggerState triggerState = null;
		try {
			triggerState = scheduler.getTriggerState(triggerKey);
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (triggerState != null) {
			return triggerState.name();
		}
		return "";
	}

	@SneakyThrows
	@Override
	public void resumeJob(String jobName, String jobGroupName, String userid, String username) {
		try {
			if (StringUtils.equals(checkJobStatus(jobName, jobGroupName), "NONE")) {
				QrtzTaskSearch search = new QrtzTaskSearch();
				search.setJobName(jobName);
				search.setJobGroupName(jobGroupName);
				QrtzTaskEntity qrtzTask = qrtzTaskService.getQrtzTask(search);
				qrtzTask.setJobStatus("NORMAL");
				QrtzTaskVo qrtzTaskVo = new QrtzTaskVo();
				BeanUtils.copyProperties(qrtzTaskVo, qrtzTask);
				addJob(qrtzTaskVo, userid, username);
				return;
			} else if (StringUtils.equals(checkJobStatus(jobName, jobGroupName), "PAUSED")) {
				QrtzTaskSearch search = new QrtzTaskSearch();
				search.setJobName(jobName);
				search.setJobGroupName(jobGroupName);
				QrtzTaskEntity qrtzTask = qrtzTaskService.getQrtzTask(search);
				qrtzTask.setJobStatus("NORMAL");
				qrtzTaskService.updateQrtzTaskEntity(qrtzTask, userid, username);
				return;
			}
			scheduler.resumeJob(JobKey.jobKey(jobName, jobGroupName));
		} catch (SchedulerException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void updateJob(QrtzTaskVo qrtzTaskVo, String userid, String username) {
		try {
			String name = qrtzTaskVo.getJobName();
			String groupName = qrtzTaskVo.getJobGroupName();
			String cronExp = qrtzTaskVo.getCronExpression();
			if (StringUtils.equals(checkJobStatus(name, groupName), "NONE")) {
				addJob(qrtzTaskVo, userid, username);
				return;
			}
			TriggerKey triggerKey = TriggerKey.triggerKey(name, groupName);
			CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
			if (cronExp != null) {
				// 表达式调度构建器
				CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExp);
				// 按新的cronExpression表达式重新构建trigger
				TriggerBuilder<CronTrigger> withSchedule = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder);
				trigger = withSchedule.build();
			}

			// 修改map
			if (qrtzTaskVo != null) {
				trigger.getJobDataMap().put(name, qrtzTaskVo);
			}
			// 按新的trigger重新设置job执行
			scheduler.rescheduleJob(triggerKey, trigger);
			QrtzTaskEntity qrtzTaskEntity = new QrtzTaskEntity();
			BeanUtils.copyProperties(qrtzTaskEntity, qrtzTaskVo);
			qrtzTaskService.updateQrtzTaskEntity(qrtzTaskEntity, userid, username);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 删除
	 */
	@Override
	public void deleteJob(Long id, String jobName, String jobGroupName, String userid, String username) {

		try {
			scheduler.pauseTrigger(TriggerKey.triggerKey(jobName, jobGroupName));// 停止触发器  
			scheduler.unscheduleJob(TriggerKey.triggerKey(jobName, jobGroupName));// 移除触发器  
			scheduler.deleteJob(JobKey.jobKey(jobName, jobGroupName));// 删除任务
			QrtzTaskSearch search = new QrtzTaskSearch();
			search.setId(id);
			qrtzTaskService.deleteTask(search, userid, username);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 启动所有定时任务
	 */
	@Override
	public void startAllJobs() {
		try {
			scheduler.start();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	/**
	 * 关闭所有定时任务
	 */
	@Override
	public void shutdownAllJobs() {
		try {
			if (!scheduler.isShutdown()) {
				scheduler.shutdown();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	/**
	 * 运行一次
	 */
	@Override
	public void runJobOnce(String name, String groupName) {
		try {
			scheduler.triggerJob(JobKey.jobKey(name, groupName));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Integer initSchedule() throws InvocationTargetException, IllegalAccessException {
		int result = 0;
		List<QrtzTaskVo> list = new ArrayList<>();
		List<QrtzTaskEntity> listEntity = qrtzTaskService.getAll();
		if (!CollectionUtils.isEmpty(listEntity)) {
			BeanUtils.copyProperties(list, listEntity);
			for (QrtzTaskVo task : list) {
				if (StringUtils.equals(checkJobStatus(task.getJobName(), task.getJobGroupName()), "NORMAL")) {
					result++;
					resumeJob(task.getJobName(), task.getJobGroupName(), null, null);
				}
			}
		} else {
			return -1;
		}
		return result;

	}

	private void wrapScheduleJob(QrtzTaskVo qrtzTaskVo, Scheduler scheduler, JobKey jobKey, Trigger trigger) {
		try {
			qrtzTaskVo.setJobName(jobKey.getName());
			qrtzTaskVo.setJobGroupName(jobKey.getGroup());
			JobDataMap jobDataMap = trigger.getJobDataMap();

			QrtzTaskVo job = (QrtzTaskVo) jobDataMap.getWrappedMap().get(jobKey.getName());
			qrtzTaskVo.setDescription(job.getDescription());
			qrtzTaskVo.setId(job.getId());
			qrtzTaskVo.setInterfaceName(job.getInterfaceName());
			Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
			qrtzTaskVo.setJobStatus(triggerState.name());
			if (trigger instanceof CronTrigger) {
				CronTrigger cronTrigger = (CronTrigger) trigger;
				String cronExp = cronTrigger.getCronExpression();
				qrtzTaskVo.setCronExpression(cronExp);
			}
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
}
