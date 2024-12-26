package com.juan.adx.model.form.manage;

import java.util.List;

import lombok.Data;

@Data
public class RoleGrantForm {

	private List<Long> ids;

	private Long roleId;


	@Override
	public String toString() {
		return "GrantVO [ids=" + ids + ", roleId=" + roleId + "]";
	}

}
