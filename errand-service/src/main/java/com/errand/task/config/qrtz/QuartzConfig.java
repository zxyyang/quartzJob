package com.errand.task.config.qrtz;

import java.io.IOException;
import java.util.Properties;

import javax.sql.DataSource;

import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

/**
 * 应用模块名称:
 * 代码描述:
 * Copyright: Copyright (C) 2021, Inc. All rights reserved.
 * 
 *
 * @author: zixuan.yang
 * @since: 2021/11/18 19:14
 */
@Configuration
public class QuartzConfig {

	@Autowired
	private DataSource dataSource;

	@Autowired
	private JobFactory jobFactory;

	@Bean(name = "schedulerFactory")
	public SchedulerFactoryBean schedulerFactoryBean() throws IOException {
		SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
		schedulerFactoryBean.setQuartzProperties(quartzProperties());
		schedulerFactoryBean.setJobFactory(jobFactory);
		schedulerFactoryBean.setOverwriteExistingJobs(true);
		// 设置自行启动
		schedulerFactoryBean.setAutoStartup(true);
		// 延时启动
		schedulerFactoryBean.setStartupDelay(1);
		schedulerFactoryBean.setDataSource(dataSource);
		return schedulerFactoryBean;
	}

	@Bean(name = "scheduler")
	public Scheduler scheduler() throws IOException {
		return schedulerFactoryBean().getScheduler();
	}

	@Bean(name = "quartzProperties")
	public Properties quartzProperties() throws IOException {
		PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
		propertiesFactoryBean.setLocation(new ClassPathResource("quartz.properties"));
		propertiesFactoryBean.afterPropertiesSet();
		return propertiesFactoryBean.getObject();
	}

}
