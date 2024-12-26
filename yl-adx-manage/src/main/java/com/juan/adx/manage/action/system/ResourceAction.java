package com.juan.adx.manage.action.system;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.juan.adx.manage.service.permission.ResourceService;
import com.juan.adx.common.annotation.Log;
import com.juan.adx.common.enums.BusinessTypeEnum;
import com.juan.adx.common.model.ManageResponse;
import com.juan.adx.common.validator.ParamAssert;
import com.juan.adx.model.entity.permission.AuthResource;

/**
 * 	资源管理
 */
@RestController
@RequestMapping( "/permission/resource" )
public class ResourceAction {

	private static final Logger ILOG = LoggerFactory.getLogger( ResourceAction.class );

	@Autowired
	private ResourceService resourceService;


	@RequestMapping( "/query" )
	public ManageResponse query() {
		return new ManageResponse( resourceService.getResources() );
	}

	@Log(title = "新增资源", businessType = BusinessTypeEnum.INSERT)
	@RequestMapping( "/add" )
	public ManageResponse add(@RequestParam(value = "cuid") String cuid, AuthResource resource ) {
		this.validateResource( resource );
		// 资源内容、资源名称唯一
		Long size;
		size = resourceService.checkResByName( resource.getName() );
		ParamAssert.isTrue( size.longValue() > 0, "资源名称已经存在" );
		size = resourceService.checkResByResource( resource.getResource() );
		ParamAssert.isTrue( size.longValue() > 0, "资源内容已经存在" );

		resourceService.addResource( resource );
		ILOG.info( "[{}]增加权限资源[{}],[{}]成功", cuid, resource.getResource(), resource.getName() );
		return new ManageResponse();
	}

	@RequestMapping( "/get" )
	public ManageResponse info( long id ) {
		return new ManageResponse( resourceService.getResourceById( id ) );
	}

	@Log(title = "更新资源", businessType = BusinessTypeEnum.UPDATE)
	@RequestMapping( "/update" )
	public ManageResponse update(@RequestParam(value = "cuid") String cuid, AuthResource resource ) {
		this.validateResource( resource );

		AuthResource authResource = resourceService.getResourceById( resource.getId() );
		// 资源内容、资源名称唯一
		Long size;
		if( !resource.getName().equals( authResource.getName() ) ) {
			size = resourceService.checkResByName( resource.getName() );
			ParamAssert.isTrue(size.longValue() > 0, "资源名称已经存在");
		}

		if( !resource.getResource().equals( authResource.getResource() ) ) {
			size = resourceService.checkResByResource( resource.getResource() );
			ParamAssert.isTrue( size.longValue() > 0, "资源内容已经存在" );
		}

		resourceService.updateResource( resource );
		ILOG.info( "[{}]修改权限资源[{}],[{}]成功", cuid, resource.getResource(), resource.getName() );
		return new ManageResponse();
	}

	@Log(title = "删除权限资源", businessType = BusinessTypeEnum.DELETE)
	@PostMapping( "/delete" )
	public ManageResponse delete(@RequestParam(value = "cuid") String cuid, long id ) {
		resourceService.deleteResource( id );
		ILOG.info( "[{}]删除权限资源[{}]成功", cuid, id );
		return new ManageResponse();
	}

	public void validateResource( AuthResource resource ) {
		ParamAssert.isTrue( StringUtils.isEmpty( resource.getDescription() ), "资源描述不能为空" );
		ParamAssert.isTrue( StringUtils.isEmpty( resource.getName() ), "资源名称不能为空" );
		ParamAssert.isTrue( StringUtils.isEmpty( resource.getResource() ), "资源内容不能为空" );
	}

}
