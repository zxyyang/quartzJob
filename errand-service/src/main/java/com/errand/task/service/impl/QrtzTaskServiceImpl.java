package com.errand.task.service.impl;


import java.util.List;
import com.errand.task.entity.QrtzTaskEntity;
import com.errand.task.search.QrtzTaskSearch;
import com.errand.task.service.IQrtzTaskService;
import javafx.scene.control.Pagination;
import org.apache.dubbo.config.annotation.Service;


/**
 * 应用模块名称:
 * 代码描述:
 * Copyright: Copyright (C) 2021, Inc. All rights reserved.
 * 
 *
 * @author: zixuan.yang
 * @since: 2021/11/18 13:50
 */
@Service(version = "1.0.0", delay = -1, retries = -1, timeout = 60000)
@org.springframework.stereotype.Service
public class QrtzTaskServiceImpl  implements IQrtzTaskService {


	@Override
	public Integer addQrtzTaskEntity(QrtzTaskEntity entity, String userid, String username) {
		return null;
	}

	@Override
	public Integer updateQrtzTaskEntity(QrtzTaskEntity entity, String userid, String username) {
		return null;
	}

	@Override
	public QrtzTaskEntity getQrtzTaskEntityByID(long id) {
		return null;
	}

	@Override
	public QrtzTaskEntity getQrtzTask(QrtzTaskSearch search) {
		return null;
	}

	@Override
	public List<QrtzTaskEntity> getQrtzTaskLists(QrtzTaskSearch search, Pagination pagination) {
		return null;
	}

	@Override
	public List<QrtzTaskEntity> getAll() {
		return null;
	}

//	@Override
//	public PaginationResult<List<QrtzTaskEntity>> getQrtzTasks(QrtzTaskSearch search, Pagination pagination) {
//		return null;
//	}

	@Override
	public Integer deleteTask(QrtzTaskSearch search, String userid, String username) {
		return null;
	}

	@Override
	public void executed(String jobName, String jobGroupName) {

	}
}
