package com.errand.task.vo;

import java.io.Serializable;

import lombok.Data;

/**
 * 应用模块名称:
 * 代码描述:
 * Copyright: Copyright (C) 2021, Inc. All rights reserved.
 * 
 *
 * @author: zixuan.yang
 * @since: 2021/11/18 10:43
 */
@Data
public class QrtzTaskVo implements Serializable {

	private static final long serialVersionUID = -2804443468276676760L;

	/** 主键 */
	private Long id;

	/** 任务名称 */
	private String jobName;

	/** 任务所属组名称 */
	private String jobGroupName;

	/** cron表达式 */
	private String cronExpression;

	/**
	 * 任务执行接口-Path（dubbo接口类路径） 例子： dubbo接口 路径:方法 com.app.dubboservice.goods.DubboGoodsService:getGoodsDetailById
	 */
	private String interfaceName;

	/** 任务状态 */
	private String jobStatus = "NORMAL";

	/** 任务描述 */
	private String description;

	/** 是否删除（0：正常/1：已删除） */
	private Boolean isDeleted;

	/** 接口参数json字符串 [{"ParamType":"java.lang.Integer","Object":10185}] 一个map一个参数 */
	private String interfaceParam;
}
