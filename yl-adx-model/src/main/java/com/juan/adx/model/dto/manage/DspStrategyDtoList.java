package com.juan.adx.model.dto.manage;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class DspStrategyDtoList {

    private String dspId;

    private List<DspStrategyDtoList> dspStrategyDtoList;
}

