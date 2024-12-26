package com.juan.adx.model.entity.manage;

import lombok.Data;

import javax.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * 2024年12月10日17:45:55
 * Auth: Kevin.zhao
 * Desc: dsp预算方测试配置表实体
 */
@Data
public class DspPartnerStrategy {

	private Long id;

	private Integer dspPartnerFid;

	@Size(min = 1, max = 20, message = "策略编码，每一种策略编码必须唯一，最长20字符")
	private String code;

	@Size(min = 1, max = 20, message = "父策略编码，每一种策略编码必须唯一，最长20字符")
	private String parentCode;

	@Size(min = 1, max = 20, message = "策略描述，最长200字符")
	private String note;

	@Size(min = 1, max = 2, message = "策略配置类型， 1为固定值， 2为区间值")
	private Integer stgType;

	@Size(min = 1, max = 100, message = "固定值, 字符类型，最大100字符")
	private String fixedValue;

	@Size(min = 1, max = 100, message = "区间开始值，包含, 字符类型，最大100字符")
	private String rangeBeginValue;

	@Size(min = 1, max = 100, message = "区间结束值，包含, 字符类型，最大100字符")
	private String rangeEndValue;

	@Size(min = 1, max = 1, message = "1有效，0无效")
	private Integer status;

	private String createBy;
	private LocalDateTime createTime;
	private String updateBy;
	private LocalDateTime updateTime;

}
