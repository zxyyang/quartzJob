package com.errand.task.constant;

/**
 * 应用模块名称:
 * 代码描述:
 * Copyright: Copyright (C) 2021, Inc. All rights reserved.
 * 
 *
 * @author: zixuan.yang
 * @since: 2021/11/19 17:58
 */
public class CronFormate {

	/**
	 * 每年时间format格式
	 */
	public static final String DATEFORMAT_YEAR = "ss mm HH dd MM ? yyyy";

	/**
	 * 每天时间format格式
	 */
	public static final String DATEFORMAT_EVERYDAY = "ss mm HH * * ?";

	/**
	 * 每周时间format格式
	 */
	public static final String DATEFORMAT_SUN = "ss mm HH ? * 1";

	public static final String DATEFORMAT_MON = "ss mm HH ? * 2";

	public static final String DATEFORMAT_TUE = "ss mm HH ? * 3";

	public static final String DATEFORMAT_WED = "ss mm HH ? * 4";

	public static final String DATEFORMAT_THU = "ss mm HH ? * 5";

	public static final String DATEFORMAT_FRI = "ss mm HH ? * 6";

	public static final String DATEFORMAT_SAT = "ss mm HH ? * 7";

}
