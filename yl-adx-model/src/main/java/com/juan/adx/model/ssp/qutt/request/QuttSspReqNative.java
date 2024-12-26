package com.juan.adx.model.ssp.qutt.request;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

import java.util.List;

/**
 * @Author: Kevin.赵伟凯
 * @CreateTime: 2024-05-18
 * @Description:
 * @Version: 1.0
 */
@Data
public class QuttSspReqNative {

	/**
	 *请求的物料类型，具体值得含义⻅附录物料类型
	 * 必填字段
	 */
	private Integer type;

	/**
	 *请拉新拉活控制 1:拉新 2:拉活 3:拉新+拉活 ，其余⽆效
	 * 必填字段
	 */
	@JSONField(name = "put_type")
	private Integer putType;

	/**
	 * 标题相关条件
	 */
	private QuttSspReqTitleFormat title;
	

	/**
	 * 描述相关条件
	 */
	private QutSspReqtDescFormat desc;

	private List<QuttSspReqImage> imgs;

	private List<QuttSspReqVideo> videos;

}
