package com.errand.task.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Entity(name = "qrtz_task")
public class QrtzTaskEntity implements Serializable {

	private static final long serialVersionUID = 1L;
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
	/** 接口参数json字符串 [{"ParamType":"java.lang.Integer","Object":10185}] 一个map一个参数 */
	private String interfaceParam;
	/** 任务状态 */
	private String jobStatus;
	/** 任务描述 */
	private String description;
	/** 创建人编号 */
	private String createUserid;
	/** 创建人姓名 */
	private String createUsername;
	/** 创建时间 */
	private Date createDate;
	/** 修改人编号 */
	private String updateUserid;
	/** 修改人姓名 */
	private String updateUsername;
	/** 修改时间 */
	private Date updateDate;
	/** 删除标志 */
	private Byte active;
	/** 版本 */
	private Integer version;
	/** 是否删除（0：正常/1：已删除） */
	private Boolean isDeleted;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	@Column(name = "id")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "job_name")
	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	@Column(name = "job_group_name")
	public String getJobGroupName() {
		return jobGroupName;
	}

	public void setJobGroupName(String jobGroupName) {
		this.jobGroupName = jobGroupName;
	}

	@Column(name = "cron_expression")
	public String getCronExpression() {
		return cronExpression;
	}

	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}

	@Column(name = "interface_name")
	public String getInterfaceName() {
		return interfaceName;
	}

	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}

	@Column(name = "interface_param")
	public String getInterfaceParam() {
		return interfaceParam;
	}

	public void setInterfaceParam(String interfaceParam) {
		this.interfaceParam = interfaceParam;
	}

	@Column(name = "job_status")
	public String getJobStatus() {
		return jobStatus;
	}

	public void setJobStatus(String jobStatus) {
		this.jobStatus = jobStatus;
	}

	@Column(name = "description")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "create_userid")
	public String getCreateUserid() {
		return createUserid;
	}

	public void setCreateUserid(String createUserid) {
		this.createUserid = createUserid;
	}

	@Column(name = "create_username")
	public String getCreateUsername() {
		return createUsername;
	}

	public void setCreateUsername(String createUsername) {
		this.createUsername = createUsername;
	}

	@Column(name = "create_date")
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Column(name = "update_userid")
	public String getUpdateUserid() {
		return updateUserid;
	}

	public void setUpdateUserid(String updateUserid) {
		this.updateUserid = updateUserid;
	}

	@Column(name = "update_username")
	public String getUpdateUsername() {
		return updateUsername;
	}

	public void setUpdateUsername(String updateUsername) {
		this.updateUsername = updateUsername;
	}

	@Column(name = "update_date")
	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	@Column(name = "active")
	public Byte getActive() {
		return active;
	}

	public void setActive(Byte active) {
		this.active = active;
	}

	@Column(name = "version")
	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	@Column(name = "is_deleted")
	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	@Override
	public boolean equals(Object that) {
		if (this == that) {
			return true;
		}
		if (that == null) {
			return false;
		}
		if (getClass() != that.getClass()) {
			return false;
		}
		QrtzTaskEntity other = (QrtzTaskEntity) that;
		return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
				&& (this.getJobName() == null ? other.getJobName() == null : this.getJobName().equals(other.getJobName()))
				&& (this.getJobGroupName() == null ? other.getJobGroupName() == null : this.getJobGroupName().equals(other.getJobGroupName()))
				&& (this.getCronExpression() == null ? other.getCronExpression() == null : this.getCronExpression().equals(other.getCronExpression()))
				&& (this.getInterfaceName() == null ? other.getInterfaceName() == null : this.getInterfaceName().equals(other.getInterfaceName()))
				&& (this.getInterfaceParam() == null ? other.getInterfaceParam() == null : this.getInterfaceParam().equals(other.getInterfaceParam()))
				&& (this.getJobStatus() == null ? other.getJobStatus() == null : this.getJobStatus().equals(other.getJobStatus()))
				&& (this.getDescription() == null ? other.getDescription() == null : this.getDescription().equals(other.getDescription()))
				&& (this.getCreateUserid() == null ? other.getCreateUserid() == null : this.getCreateUserid().equals(other.getCreateUserid()))
				&& (this.getCreateUsername() == null ? other.getCreateUsername() == null : this.getCreateUsername().equals(other.getCreateUsername()))
				&& (this.getCreateDate() == null ? other.getCreateDate() == null : this.getCreateDate().equals(other.getCreateDate()))
				&& (this.getUpdateUserid() == null ? other.getUpdateUserid() == null : this.getUpdateUserid().equals(other.getUpdateUserid()))
				&& (this.getUpdateUsername() == null ? other.getUpdateUsername() == null : this.getUpdateUsername().equals(other.getUpdateUsername()))
				&& (this.getUpdateDate() == null ? other.getUpdateDate() == null : this.getUpdateDate().equals(other.getUpdateDate()))
				&& (this.getActive() == null ? other.getActive() == null : this.getActive().equals(other.getActive()))
				&& (this.getVersion() == null ? other.getVersion() == null : this.getVersion().equals(other.getVersion()))
				&& (this.getIsDeleted() == null ? other.getIsDeleted() == null : this.getIsDeleted().equals(other.getIsDeleted()));
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
		result = prime * result + ((getJobName() == null) ? 0 : getJobName().hashCode());
		result = prime * result + ((getJobGroupName() == null) ? 0 : getJobGroupName().hashCode());
		result = prime * result + ((getCronExpression() == null) ? 0 : getCronExpression().hashCode());
		result = prime * result + ((getInterfaceName() == null) ? 0 : getInterfaceName().hashCode());
		result = prime * result + ((getInterfaceParam() == null) ? 0 : getInterfaceParam().hashCode());
		result = prime * result + ((getJobStatus() == null) ? 0 : getJobStatus().hashCode());
		result = prime * result + ((getDescription() == null) ? 0 : getDescription().hashCode());
		result = prime * result + ((getCreateUserid() == null) ? 0 : getCreateUserid().hashCode());
		result = prime * result + ((getCreateUsername() == null) ? 0 : getCreateUsername().hashCode());
		result = prime * result + ((getCreateDate() == null) ? 0 : getCreateDate().hashCode());
		result = prime * result + ((getUpdateUserid() == null) ? 0 : getUpdateUserid().hashCode());
		result = prime * result + ((getUpdateUsername() == null) ? 0 : getUpdateUsername().hashCode());
		result = prime * result + ((getUpdateDate() == null) ? 0 : getUpdateDate().hashCode());
		result = prime * result + ((getActive() == null) ? 0 : getActive().hashCode());
		result = prime * result + ((getVersion() == null) ? 0 : getVersion().hashCode());
		result = prime * result + ((getIsDeleted() == null) ? 0 : getIsDeleted().hashCode());
		return result;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getClass().getSimpleName());
		sb.append(" [");
		sb.append("Hash = ").append(hashCode());
		sb.append(", id=").append(id);
		sb.append(", jobName=").append(jobName);
		sb.append(", jobGroupName=").append(jobGroupName);
		sb.append(", cronExpression=").append(cronExpression);
		sb.append(", interfaceName=").append(interfaceName);
		sb.append(", interfaceParam=").append(interfaceParam);
		sb.append(", jobStatus=").append(jobStatus);
		sb.append(", description=").append(description);
		sb.append(", createUserid=").append(createUserid);
		sb.append(", createUsername=").append(createUsername);
		sb.append(", createDate=").append(createDate);
		sb.append(", updateUserid=").append(updateUserid);
		sb.append(", updateUsername=").append(updateUsername);
		sb.append(", updateDate=").append(updateDate);
		sb.append(", active=").append(active);
		sb.append(", version=").append(version);
		sb.append(", isDeleted=").append(isDeleted);
		sb.append(", serialVersionUID=").append(serialVersionUID);
		sb.append("]");
		return sb.toString();
	}
}