package com.errand.task.api;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;

import com.errand.task.vo.QrtzTaskVo;

/**
 * 应用模块名称:
 * 代码描述:
 * Copyright: Copyright (C) 2021, Inc. All rights reserved.
 * 
 *
 * @author: zixuan.yang
 * @since: 2021/11/22 14:04
 */
public interface IErrandTaskService {

	/**
	 * 时间转换cron表达式
	 */
	String DateToCron(String date);

	String DateToCron(Date date);

	/**
	 * 获取所有job
	 * 
	 * @return
	 */
	public List<QrtzTaskVo> getAllJobList();

	/**
	 * 获取所有执行中的job
	 * 
	 * @return
	 */
	public List<QrtzTaskVo> getRunningJobList();

	/**
	 * 添加任务，传入参数
	 * 
	 * @param qrtzTaskVo
	 * @param userid
	 * @param username
	 */
	void addJob(QrtzTaskVo qrtzTaskVo, String userid, String username);

	/**
	 * 暂停任务
	 *
	 * @param name
	 * @param groupName
	 */
	void pauseJob(String name, String groupName, String userid, String username);

	/**
	 * 恢复任务
	 *
	 * @param jobName
	 * @param jobGroupName
	 */
	void resumeJob(String jobName, String jobGroupName, String userid, String username);

	/**
	 * 更新任务
	 * 
	 * @param qrtzTaskVo
	 * @param userid
	 * @param username
	 */
	void updateJob(QrtzTaskVo qrtzTaskVo, String userid, String username);

	/**
	 * 删除任务
	 * 
	 * @param id
	 * @param jobName
	 * @param jobGroupName
	 * @param userid
	 * @param username
	 */
	void deleteJob(Long id, String jobName, String jobGroupName, String userid, String username);

	/**
	 * 启动所有任务
	 */
	void startAllJobs();

	/**
	 * 关闭所有任务
	 */
	void shutdownAllJobs();

	/**
	 * 运行一次
	 * 
	 * @param name
	 * @param groupName
	 */
	public void runJobOnce(String name, String groupName);

	/**
	 * 初始化任务
	 * 
	 * @return
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	Integer initSchedule() throws InvocationTargetException, IllegalAccessException;
}
