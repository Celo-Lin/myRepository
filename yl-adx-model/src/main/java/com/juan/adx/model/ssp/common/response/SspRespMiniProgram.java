package com.juan.adx.model.ssp.common.response;

import lombok.Data;

@Data
public class SspRespMiniProgram {
	
    /**
     * 微信小程序ID
     */
    private String  miniProgramId;
    
    /**
     * 微信小程序唤起路径
     */
    private String miniProgramPath;

}
