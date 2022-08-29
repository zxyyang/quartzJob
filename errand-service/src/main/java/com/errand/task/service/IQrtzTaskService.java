package com.errand.task.service;

import java.util.List;

import com.errand.task.entity.QrtzTaskEntity;
import com.errand.task.search.QrtzTaskSearch;
import javafx.scene.control.Pagination;

/**
 * 应用模块名称:
 * 代码描述:
 * Copyright: Copyright (C) 2021, Inc. All rights reserved.
 * 
 *
 * @author: zixuan.yang
 * @since: 2021/11/18 12:39
 */
public interface IQrtzTaskService {

	Integer addQrtzTaskEntity(QrtzTaskEntity entity, String userid, String username);

	Integer updateQrtzTaskEntity(QrtzTaskEntity entity, String userid, String username);

	QrtzTaskEntity getQrtzTaskEntityByID(long id);

	QrtzTaskEntity getQrtzTask(QrtzTaskSearch search);

	List<QrtzTaskEntity> getQrtzTaskLists(QrtzTaskSearch search, Pagination pagination);

	List<QrtzTaskEntity> getAll();

	//PaginationResult<List<QrtzTaskEntity>> getQrtzTasks(QrtzTaskSearch search, Pagination pagination);

	Integer deleteTask(QrtzTaskSearch search, String userid, String username);

	void executed(String jobName, String jobGroupName);
}
