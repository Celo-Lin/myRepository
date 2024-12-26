package com.juan.adx.model.entity;

import java.io.Serializable;

public class BaseEntity implements Serializable {

	private static final long serialVersionUID = -2419230265174180253L;

	protected Long id;

	protected Integer createTime;

	protected Integer updateTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Integer createTime) {
		this.createTime = createTime;
	}

	public Integer getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Integer updateTime) {
		this.updateTime = updateTime;
	}
}
