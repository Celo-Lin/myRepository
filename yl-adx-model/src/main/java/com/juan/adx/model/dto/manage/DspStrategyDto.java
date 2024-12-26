package com.juan.adx.model.dto.manage;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DspStrategyDto {
    private Long id;

    private Integer dspPartnerFid;

    private String code;

    private String parentCode;

    private String note;

    private Integer stgType;

    private String fixedValue;

    private String rangeBeginValue;

    private String rangeEndValue;

    private Integer status;

    private String createBy;

    private LocalDateTime createTime;

    private String updateBy;

    private LocalDateTime updateTime;
}
