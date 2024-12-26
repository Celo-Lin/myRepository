package com.juan.adx.model.entity;

import lombok.Data;

@Data
public class Resource {

	private long id;

	private String name;// 权限资源名称

	private Boolean isGrant;// 是否授权

}
