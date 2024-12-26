package com.juan.adx.model.ssp.qutt.request;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

import java.util.List;

/**
 * 应用信息
 */
@Data
public class QuttSspReqApp {
	
	/**
	 *否
	 * App所属⾏业id。暂不⽀持
	 */
	@JSONField(name = "industry_id")
	private String industryId;
	
	/**
	 *否
	 * ⼩于256字节
	 * App 的唯⼀标识。Android应⽤
	 * 是packagename，IOS应⽤是
	 * bundle id
	 */
	@JSONField(name = "app_bundle_id")
	private String appBundleId;
	
	/**
	 * 否
	 * app的名称。
	 */
	@JSONField(name = "app_name")
	private String appName;
	
	/**
	 * 否
	 * app的版本号，如：1.6.13
	 */
	@JSONField(name = "app_version")
	private String appVersion;

	/**
	 *否
	 * 个数上限30个
	 * ⽤户安装的dsp客户app包名列表
	 */
	@JSONField(name = "installed_client_apps")
	private List<String> installedClientApps;
	/**
	 *否
	 * ⽤户安装的app对应的id列表。
	 * 映射关系请联系趣头条运营同事
	 * 提供。
	 */
	@JSONField(name = "installed_client_app_ids")
	private Integer installedClientAppIds;
	/**
	 *媒体唯⼀标识。渠道+App的全局
	 * 唯⼀
	 * 否
	 */
	@JSONField(name = "app_bundle_uid")
	private String appBundleUid;

}
