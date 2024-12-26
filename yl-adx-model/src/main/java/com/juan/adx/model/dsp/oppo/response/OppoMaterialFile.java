package com.juan.adx.model.dsp.oppo.response;
import lombok.Data;

@Data
public class OppoMaterialFile {
    /**
     * <pre>
     * url: 文件 url
     * </pre>
     */
    private String url;

    /**
     * <pre>
     * md5: 文件的 MD5 值
     * </pre>
     */
    private String md5;

    /**
     * <pre>
     * fileType: 文件类型，见附录-文件类型
     * 1 图片
     * 2 视频
     * </pre>
     */
    private Integer fileType;
}
