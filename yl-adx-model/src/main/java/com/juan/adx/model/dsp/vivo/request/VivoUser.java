package com.juan.adx.model.dsp.vivo.request;

import lombok.Data;

import java.util.List;

/**
 * @author： HeWen Zhou
 * @date： 2024/5/25 16:05
 */
@Data
public class VivoUser {

    /** 用户安装列表 */
    private List<String> appList;

    /** 用户性别 */
    private String gender;

    /** 用户年龄 */
    private Integer age;

    /** 用户兴趣标签 */
    private List<String> interest;

}
