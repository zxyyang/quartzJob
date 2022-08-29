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
 * @since: 2021/11/16 14:05
 */
@Data
public class RequestBean<T> implements Serializable {

	private Integer code;

	private String msg;

	private T data;

	private RequestBean(Integer code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	private RequestBean(Integer code, String msg, T data) {
		this.code = code;
		this.msg = msg;
		this.data = data;
	}

	/**
	 * 状态码 + 成功提示信息
	 */
	public static <T> RequestBean<T> Success() {
		return new RequestBean<>(200, "success");
	}

	/**
	 * 状态码 + 成功提示信息 + 数据
	 */
	public static <T> RequestBean<T> Success(T data) {
		return new RequestBean<>(200, "success", data);
	}

	/**
	 * 状态码 + 成功提示信息 + 数据
	 */
	public static <T> RequestBean<String> Success(String data) {
		return new RequestBean<String>(200, "success", data);
	}

	/**
	 * 状态码 + 错误信息
	 */
	public static <T> RequestBean<T> Error() {
		return new RequestBean<>(500, "error");
	}

	/**
	 * 状态码 + 错误信息(自定义)
	 */
	public static <T> RequestBean<T> Error(String msg) {
		return new RequestBean<>(500, msg);
	}

	/**
	 * 状态码（自定义） + 错误信息(自定义)
	 */
	public static <T> RequestBean<T> Error(Integer code, String msg) {
		return new RequestBean<>(code, msg);
	}

}