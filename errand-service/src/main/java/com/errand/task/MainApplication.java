package com.errand.task;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;

import lombok.extern.slf4j.Slf4j;

/**
 * 应用模块名称:
 * 代码描述:
 * Copyright: Copyright (C) 2021, Inc. All rights reserved.
 * 
 *
 * @author: zixuan.yang
 * @since: 2021/11/11 16:10
 */
@SpringBootApplication
@EnableCaching
@ComponentScan(basePackages = { "com.errand.task", "com.errand.task.config",  "com.errand",
		"cn.hutool.extra.spring", "com.errand.task.config.qrtz" })
@EnableDubbo
//@EnableApolloConfig
@EnableScheduling
@Slf4j
public class MainApplication {

	public static void main(String[] args) {
		SpringApplication.run(MainApplication.class, args);
		log.info("  ______                          _        _____ _             _   \n"
				+ " |  ____|                        | |      / ____| |           | |  \n"
				+ " | |__   _ __ _ __ __ _ _ __   __| |_____| (___ | |_ __ _ _ __| |_ \n"
				+ " |  __| | '__| '__/ _` | '_ \\ / _` |______\\___ \\| __/ _` | '__| __|\n"
				+ " | |____| |  | | | (_| | | | | (_| |      ____) | || (_| | |  | |_ \n"
				+ " |______|_|  |_|  \\__,_|_| |_|\\__,_|     |_____/ \\__\\__,_|_|   \\__|\n"
				+ "                                                                   \n"
				+ "                                                                   ");
	}

}
