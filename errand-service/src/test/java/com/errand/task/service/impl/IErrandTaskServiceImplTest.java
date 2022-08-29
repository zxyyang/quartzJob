package com.errand.task.service.impl;

import java.util.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.errand.task.vo.QrtzTaskVo;

import junit.framework.TestCase;


/**
 * 应用模块名称:
 * 代码描述:
 * Copyright: Copyright (C) 2021, Inc. All rights reserved.
 * 
 *
 * @author: zixuan.yang
 * @since: 2021/11/22 17:12
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class IErrandTaskServiceImplTest extends TestCase {

	@Autowired
	private IErrandTaskServiceImpl errandTaskService;

	@Test
	public void testAddJob() {
		QrtzTaskVo qrtzTaskVo = new QrtzTaskVo();
		qrtzTaskVo.setJobName("123");
		qrtzTaskVo.setJobGroupName("1");
		Date date = new Date();
		Date late = new Date(date.getTime() + 60000);
		// qrtzTaskVo.setCronExpression(errandTaskService.DateToCron(late));
		qrtzTaskVo.setCronExpression("0/10 * * * * ? ");
		qrtzTaskVo.setDescription("描述");
		qrtzTaskVo.setInterfaceName("com.api.ICorehrFormEditService:addForm1LineData");
		List<Map> mapList = new ArrayList<>();
		Map map = new HashMap<>();
		QrtzTaskVo vo = new QrtzTaskVo();
		vo.setId(111111111l);
		vo.setJobName("任务名字");
		vo.setJobGroupName("分组名字");
		vo.setJobStatus("状态");
		Map map1 = new HashMap<>();
		map1.put("ParamType", "boolean");
		map1.put("Object", true);
		Map map2 = new HashMap<>();
		map2.put("ParamType", "java.lang.Long");
		map2.put("Object", 1422438067150592l);
		mapList.add(map);
		//String param = JSONArray.fromObject(mapList).toString();
		qrtzTaskVo.setInterfaceParam(
				"[{\"Object\":true,\"ParamType\":\"boolean\"},{\"Object\":1422438067150592,\"ParamType\":\"java.lang.Long\"},{\"Object\":{\"dataId\":0,\"employeeId\":0,\"employeeRosterFormFieldValueVos\":[{\"fieldCode\":\"f618e443be4b0bd00c9ee4d34\",\"value\":\"errand修改\",\"valueId\":0}],\"formId\":1423800309122304,\"tag\":0,\"taskId\":0},\"ParamType\":\"com.humancloud.saas.vo.employeeRoster2.EmployeeRosterFormSingleLineMutiFieldValueVo\"}]");
		errandTaskService.addJob(qrtzTaskVo, "999", "Errand-service");

	}
}