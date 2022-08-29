package com.errand.task.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Singleton;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import org.springframework.stereotype.Component;

/**
 * Copyright: Copyright (C) 2022, Inc. All rights reserved.
 *
 * @author: zixuan.yang
 * @since: 2022/8/29 14:24
 */
@Component
public class IdUtils {
    public IdUtils() {
    }

    public static long getId() {
        return getNowId();
    }

    public static long getNowId() {
        StringBuilder sb = new StringBuilder();
        long current = DateUtil.current();
        long rdm = RandomUtil.randomLong(1000, 9999);
        StringBuilder append = sb.append(1).append(current).append(rdm);
        return Long.parseLong(append.toString());
    }

}
