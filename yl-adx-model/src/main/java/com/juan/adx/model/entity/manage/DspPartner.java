package com.juan.adx.model.entity.manage;

import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class DspPartner {

	private Integer id;
	
	@Size(min = 1, max = 20, message = "预算方名称长度必须在1到20个字符之间")
	private String name;
	
	@Size(min = 1, max = 30, message = "公司名称长度必须在1到30个字符之间")
	private String company;
	
	@Size(min = 1, max = 10, message = "联系人姓名长度必须在1到10个字符之间")
	private String contactName;
	
	@Size(min = 1, max = 20, message = "联系人电话长度必须在1到20个字符之间")
	private String phone;
	
	private Integer status;
	
	private String pinyinName;
	
	private String apiUrl;
	
	private Long ctime;
	
	private Long utime;
}
