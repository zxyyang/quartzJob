package com.errand.task.enums;


/**
 * 应用模块名称:
 * 代码描述:操作类型的枚举类
 * Copyright: Copyright (C) 2021, Inc. All rights reserved.
 * 
 *
 * @author: zixuan.yang
 * @since: 2021/11/16 16:00
 */

public enum ICorehrFormEditServiceTypeEnum {

	ADDLINE(0, "addForm1LineData", "增加一行数据"), //
	UPDATELINE(1, "updateSingleLineMutiFieldValue", "更新单行多数据"), //
	ADDMUTI(2, "addFormDataMuti", "增加表单数据"), //
	UPDATEMUTI(3, "updateFormDataMuti", "更新表单数据");//

	private Integer code;

	private String name;

//	private Class<T> aClass;

	private String describe;

	ICorehrFormEditServiceTypeEnum(Integer code, String name, String describe) {
		this.code = code;
		this.name = name;
		this.describe = describe;
	}

	public static String getName(int code) {
		for (ICorehrFormEditServiceTypeEnum c : ICorehrFormEditServiceTypeEnum.values()) {
			if (c.getCode() == code) {
				return c.name;
			}
		}
		return null;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	@Override
	public String toString() {
		return "CrudType{" + "code=" + code + ", name='" + name + '\'' + ", describe='" + describe + '\'' + '}';
	}
}
