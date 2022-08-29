package com.errand.task.job.commonJob;

import java.util.List;
import java.util.Map;

import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.utils.ReferenceConfigCache;
import org.apache.dubbo.rpc.service.GenericService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;

/**
 * 应用模块名称:
 * 代码描述:
 * Copyright: Copyright (C) 2021, Inc. All rights reserved.
 * 
 *
 * @author: zixuan.yang
 * @since: 2021/11/22 12:11
 */
public class DubboServiceFactory {

	Logger logger = LoggerFactory.getLogger(DubboServiceFactory.class);

	@Value("${dubbo.application.name}")
	private String name;

	@Value("${dubbo.registry.address}")
	private String address;

	@Value("${dubbo.protocol.name}")
	private String protocolName;

	@Value("${dubbo.protocol.port}")
	private Integer protocolPort;

	@Value("${dubbo.consumer.check}")
	private Boolean check;

	@Value("${dubbo.consumer.timeout}")
	private Integer timeout;

	private DubboServiceFactory() {

	}

	public static DubboServiceFactory getInstance() {
		return SingletonHolder.INSTANCE;
	}

	public Object genericInvoke(String interfaceClass, String methodName, List<Map> parameters) {
		try {

			ApplicationConfig application = new ApplicationConfig();
			application.setName(name);
			RegistryConfig registryConfig = new RegistryConfig();
			registryConfig.setAddress(address);
			application.setRegistry(registryConfig);

			// 引用远程服务
			ReferenceConfig<GenericService> reference = new ReferenceConfig<>();
			// reference.setApplication(application);
			reference.setRegistry(registryConfig);
			reference.setRetries(0);
			reference.setInterface(interfaceClass); // 接口名
			reference.setGeneric(true); // 声明为泛化接口
			reference.setCheck(false);
			reference.setVersion("1.0.0");
			reference.setTimeout(timeout);
			reference.setAsync(true);
			// RpcContext中设置generic=gson
			// RpcContext.getContext().setAttachment("generic", "gson");
			GenericService genericService = ReferenceConfigCache.getCache().get(reference);
			// ReferenceConfigCache cache = ReferenceConfigCache.getCache();
			//
			// GenericService genericService = cache.get(reference);
			// GenericService genericService = reference.get();
			// 用org.apache.dubbo.rpc.service.GenericService可以替代所有接口引用
			Object object = new DubboServiceFactory();
			if (CollectionUtils.isNotEmpty(parameters)) {
				int len = parameters.size();
				String[] invokeParamTyeps = new String[len];
				Object[] invokeParams = new Object[len];
				for (int i = 0; i < len; i++) {
					invokeParamTyeps[i] = parameters.get(i).get("ParamType") + "";
					invokeParams[i] = parameters.get(i).get("Object");
				}
				object = genericService.$invoke(methodName, invokeParamTyeps, invokeParams);

			} else {
				object = genericService.$invoke(methodName, null, null);
			}

			return object;
		} catch (Exception e) {
			logger.error("genericInvoke", e);
			e.printStackTrace();
		}
		return "500";
	}

	// 单例
	private static class SingletonHolder {

		private static DubboServiceFactory INSTANCE = new DubboServiceFactory();
	}
}
