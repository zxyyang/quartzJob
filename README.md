# errand(后端定时调用接口服务)

## 一、 介绍

> **功能列表：**
>
>  1.实现定时调度后端接口
>
>  2.范化调用后端接口，随意调用配置
>
>  3.数据库持久化，调度任务，监控、查询、重启、等操作
>
>  **优点：**
>
> 1.区别于xxl-job 此调度方式更加贴近业务后端调用。区别于脚本
>
> 2.泛化调用可以实现基于dubbo的调度中心
>
> 3.持久化任务，方便监控
>
> **业务场景：**
>
> 业务属性
>
> ```java
> if(房租到期){
> 实现调用B服务催收账单短信接口
> }
> if(某人到达转正日期 and 完成转正答辩会){
> 调用C服务下的转正操作接口
> }
> 
> ```
>
>



##  二、 使用

**1.需要在application-yaml配置文件中配置**

>  数据库链接地址
>
>  端口号
>
> duuboo地址
>
> 服务中心
>
> 等基础的设置

```yaml
server:
  port: 9132
jdbc:
  driver: com.mysql.cj.jdbc.Driver
  url:
  username:
  password:
  validationQuery:
spring:
  datasource:
    driver-class-name:
    url:
    username:
    password:
dubbo:
  application.name: errand-service
  registry.address: zookeeper://
  protocol.name: dubbo
  protocol.port: 20914
  scan.base-packages:
  provider:
    filter: dubboTraceIdFilter
    payload: 83886080

  consumer:
    filter: dubboTraceIdFilter
    timeout: 60000
    check: false
    default: false

management:
  health:
    elasticsearch:
      enabled:
        false
    redis:
      enabled:
        false
    db:
      enabled:
        false
    rabbit:
      enabled: false
  endpoint:
    web:
      exposure:
        include: health

```

**2.按照目前给出的接口api 实现自己的数据库操作**

```properties
<sql id="getQuartzTasks">
        <![CDATA[
            SELECT * FROM `qrtz_task` WHERE active=1
            <#if id??>
                and id=${id}
            </#if>
            <#if jobName??>
                and job_name='${jobName}'
            </#if>
            <#if jobGroupName??>
                and job_group_name='${jobGroupName}'
            </#if>
             <#if cronExpression??>
                and cron_expression='${cronExpression}'
            </#if>
             <#if interfaceName??>
                and interface_name='${interfaceName}'
            </#if>
            <#if jobStatus??>
                and job_status='${jobStatus}'
            </#if>
            <#if description??>
                and description='${description}'
            </#if>
            <#if createUserid??>
                and create_userid='${createUserid}'
            </#if>
             <#if createUsername??>
                and create_username='${createUsername}'
            </#if>
             <#if createDate??>
                and create_date='${createDate}'
            </#if>
            <#if updateUserid??>
                and update_userid='${updateUserid}'
            </#if>
            <#if updateUsername??>
                and update_username='${updateUsername}'
            </#if>
             <#if updateDate??>
                and update_date='${updateDate}'
            </#if>
            <#if active??>
                and active='${active}'
            </#if>
            <#if version??>
                and version='${version}'
            </#if>
            <#if is_deleted??>
                and is_deleted='${is_deleted}'
            </#if>
            <#if interfaceParam??>
                and interface_param='${interfaceParam}'
            </#if>

        ]]>
    </sql>
```

**3.在quartz.properties 配置文件中修改自己想要的配置（可不用修改）**

```properties
#quartz????
# ===========================================================================
# Configure Main Scheduler Properties ?????
# ===========================================================================
#????? ??????????????????
org.quartz.scheduler.instanceName=ErrandQuartzScheduler
#ID??????? ???????
org.quartz.scheduler.instanceid=AUTO
#============================================================================
# Configure ThreadPool
#============================================================================
#????????????SimpleThreadPool??????????????
org.quartz.threadPool.class=org.quartz.simpl.SimpleThreadPool
#?????????1??????(?????1-100???????)
org.quartz.threadPool.threadCount=50
#????????????java.lang.Thread.MAX_PRIORITY 10????Thread.MIN_PRIORITY 1????5?
org.quartz.threadPool.threadPriority=5
#============================================================================
# Configure JobStore
#============================================================================
# ?????? ???60?
org.quartz.jobStore.misfireThreshold=60000
#?????????????
org.quartz.jobStore.class=org.quartz.impl.jdbcjobstore.JobStoreTX
#?????????org.quartz.impl.jdbcjobstore.StdJDBCDelegate??????????
org.quartz.jobStore.driverDelegateClass=org.quartz.impl.jdbcjobstore.StdJDBCDelegate
#JobDataMaps????String??
org.quartz.jobStore.useProperties=false
#????? ???
org.quartz.jobStore.dataSource=myDS
#???????QRTZ_
org.quartz.jobStore.tablePrefix=QRTZ_
#??????
org.quartz.jobStore.isClustered=false
#?????????????
org.quartz.jobStore.clusterCheckinInterval=20000
#============================================================================
# Configure Datasources
#============================================================================
##?????
org.quartz.dataSource.myDS.driver=com.mysql.jdbc.Driver
#?????
org.quartz.dataSource.myDS.URL=jdbc:mysql://rm-bp182zi1u38e0s8o8wo.mysql.rds.aliyuncs.com/corehr-develop?useUnicode=true&characterEncoding=utf-8&txcAppName=errandService
org.quartz.dataSource.myDS.user=root
#?????
org.quartz.dataSource.myDS.password=meichai2016
##??????
#org.quartz.dataSource.myDS.maxConnections=5
##????sql,?????
org.quartz.dataSource.myDS.validationQuery=select 0 from dual

```

**3.运行测试类 测试**

```sql
		qrtzTaskVo.setInterfaceName("com.api.ICorehrFormEditService:addForm1LineData");//需要修改的接口名，需要实际存在，也可以在本项目中新建
	    qrtzTaskVo.setCronExpression("0/10 * * * * ? ");//cron表达式	
```

**4.额外-修改logback.xml**

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<configuration>

在此处填写自己的日志设置

</configuration>
```



## 三、拓展



**注意：**

>本版本只是给出思路和某些实现，具体想要运行起来还需要理解代码，修改某些数据库操作的代码实现，才可以运行部署
>
>代码没有走过专业测试，商业用途需要打磨

