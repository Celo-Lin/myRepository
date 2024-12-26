package com.juan.adx.common.utils.http;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: Kevin.赵伟凯
 * @CreateTime: 2024-12-13 15:02
 * @Description: 像DSP  Proxy发起请求服务
 * @Version: 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PoolStats implements Serializable {
    private int leased;
    private int pending;
    private int available;
    private int max;

    @Override
    public String toString() {
        String buffer = "[leased: " +
                this.leased +
                "; pending: " +
                this.pending +
                "; available: " +
                this.available +
                "; max: " +
                this.max +
                "]";
        return buffer;
    }

}
