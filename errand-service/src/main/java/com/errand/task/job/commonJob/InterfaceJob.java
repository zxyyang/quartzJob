package com.errand.task.job.commonJob;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.rpc.RpcContext;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.errand.task.service.IQrtzTaskService;
import com.errand.task.vo.QrtzTaskVo;

import lombok.extern.slf4j.Slf4j;

/**
 * 应用模块名称:
 * 代码描述:
 * Copyright: Copyright (C) 2021, Inc. All rights reserved.
 * 
 *
 * @author: zixuan.yang
 * @since: 2021/11/22 12:04
 */
@Slf4j
public class InterfaceJob implements Job {

	Logger logger = LoggerFactory.getLogger(InterfaceJob.class);

	@Autowired
	private IQrtzTaskService qrtzTaskService;

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		String jobName = "";
		String desc = "";
		String interfaceName = "";
		String interfaceParam = "";
		try {
			// 获取任务名
			String taskName = context.getJobDetail().getKey().getName();
			JobDataMap jobDataMap = context.getTrigger().getJobDataMap();
			QrtzTaskVo job = (QrtzTaskVo) jobDataMap.getWrappedMap().get(taskName);
			jobName = job.getJobName();
			desc = job.getDescription();
			interfaceName = job.getInterfaceName();
			interfaceParam = job.getInterfaceParam();
			// dubbo
			{
				// RPC接口 dubbo
				List<Map> parameters = JSONArray.parseArray(interfaceParam, Map.class);
				dubbo(interfaceName.split(":")[0], interfaceName.split(":")[1], parameters);
			}
			// 处理执行任务之后的业务
			qrtzTaskService.executed(job.getJobName(), job.getJobGroupName());
		} catch (Exception e) {
			logger.error(jobName + ":" + desc + ":" + interfaceName + "={}", "调度失败");
			e.printStackTrace();
		}
	}

	/**
	 * @param interfaceClass
	 *            接口类路径
	 * @param methodName
	 *            接口类中的方法名
	 * @param parameters
	 *            接口参数 暂时不用默认为空
	 */
	public void dubbo(String interfaceClass, String methodName, List<Map> parameters) {
		printlnDubboResult(DubboServiceFactory.getInstance().genericInvoke(interfaceClass, methodName, parameters));
	}

	/**
	 * 打印dubbo返回
	 * 
	 * @param result
	 */
	public void printlnDubboResult(Object result) {
		// 拿到调用的Future引用，当结果返回后，会被通知和设置到此Futrue
		// 异步获取泛化调用结果
		Future<Object> fooFuture = RpcContext.getContext().getFuture();
		try {
			result = fooFuture.get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		Object userMap = result;
		String jsonObject = JSON.toJSONString(userMap);
		log.info("这是返回结果：\n" + jsonObject);
	}
}
