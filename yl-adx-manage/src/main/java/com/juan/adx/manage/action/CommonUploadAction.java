package com.juan.adx.manage.action;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.juan.adx.common.constants.Constants;
import com.juan.adx.common.model.ManageResponse;
import com.juan.adx.model.dto.manage.FileUploadDto;

/**
 * 通用上传组件<br>
 * 支持上传所有文件类型
 */
@RestController
@RequestMapping("/adx/common")
public class CommonUploadAction extends UploadBaseAction {
	

	private static final Set<String> SUFFIX = new HashSet<String>(
			Arrays.asList("JPG", "jpg", "PNG", "png", "JPEG", "jpeg", "txt",
					"TXT", "xls", "xlsx", "mp3", "zip", "gif", "jar", "apk",
					"java", "xml", "aar","act","bnr","mp4"));


	@RequestMapping("/upload")
	public ManageResponse upload( MultipartHttpServletRequest request) throws IOException {
		if (request.getCharacterEncoding() == null) {
			request.setCharacterEncoding("UTF-8");
		}
		String baseDir = Constants.FileUpload.LOCAL_UPLOAD_TEMP_DIR;
		List<FileUploadDto> fileUploadDtos = super.processMultipartFile(request.getFileMap(), baseDir);
		return new ManageResponse(fileUploadDtos);
	}

	@RequestMapping("/image/upload")
	public ManageResponse imageUpload(@RequestParam(required = false, name = "scale") String scaleStr, MultipartHttpServletRequest request) throws IOException {
		if (request.getCharacterEncoding() == null) {
			request.setCharacterEncoding("UTF-8");
		}
		//String baseDir = request.getSession().getServletContext().getRealPath(LOCAL_UPLOAD_DIR);
		String baseDir = Constants.FileUpload.LOCAL_UPLOAD_TEMP_DIR;
		//裁剪比例scale=height/with(期望的高宽比例)
		float scale = 0;//default cut rate, don't cut
		if (StringUtils.isNotBlank(scaleStr)) {
			scale = Float.valueOf(scaleStr);
		}
		List<FileUploadDto> fileUploadDtos = super.processImageFile(request.getFileMap(), baseDir, scale);
		return new ManageResponse(fileUploadDtos);
	}

	@Override
	protected boolean checkExtension(String extension) {
		if (null == extension) {
			return false;
		}
		return SUFFIX.contains(extension);
	}

	@Override
	protected void postProcess(FileUploadDto response, String prefixPath) {

	}
}
