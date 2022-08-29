package com.errand.task.config.qrtz;

import com.errand.task.api.IErrandTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * 应用模块名称:
 * 代码描述:
 * Copyright: Copyright (C) 2021, Inc. All rights reserved.
 * 
 *
 * @author: zixuan.yang
 * @since: 2021/11/18 19:16
 */
@Component
@Order(1)
@Slf4j
public class ScheduleJobInitListener implements ApplicationRunner {

	@Autowired
	private IErrandTaskService taskService;

	@Override
	public void run(ApplicationArguments args) throws Exception {

		taskService.initSchedule();

		log.info("\n\n\n===========================启动所有定时任务===========================\n\n\n");
	}
}
