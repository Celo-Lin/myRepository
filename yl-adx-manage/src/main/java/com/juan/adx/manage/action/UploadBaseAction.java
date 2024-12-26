package com.juan.adx.manage.action;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import com.juan.adx.common.exception.ExceptionEnum;
import com.juan.adx.common.exception.ServiceRuntimeException;
import com.juan.adx.common.utils.ImageUtils;
import com.juan.adx.common.utils.LocalDateUtils;
import com.juan.adx.common.utils.RandomStringGenerator;
import com.juan.adx.model.dto.manage.FileUploadDto;

/**
 * 上传组件
 * 
 */
public abstract class UploadBaseAction {

	protected static final Logger logger = LoggerFactory.getLogger( UploadBaseAction.class );

	private final RandomStringGenerator randomStringGenerator = new RandomStringGenerator( 8 );

	private static final Set<String> IMG_SUFFIX = new HashSet<String>(
			Arrays.asList("JPG", "jpg", "PNG", "png", "JPEG", "jpeg")
	);

	protected final List<FileUploadDto> processMultipartFile(
			Map<String, MultipartFile> multipartFiles, String baseDir ) throws IOException {
		if( null == multipartFiles || multipartFiles.isEmpty() ) {
			throw new ServiceRuntimeException( ExceptionEnum.NO_MULTIPART );
		}
		List<FileUploadDto> fileUploadDtos = new ArrayList<FileUploadDto>(multipartFiles.size());
		for( Entry<String, MultipartFile> en : multipartFiles.entrySet() ) {
			MultipartFile multipartFile = en.getValue();
			String originalFilename = multipartFile.getOriginalFilename();
			String extension = FilenameUtils.getExtension( originalFilename );
			if( !checkExtension( extension ) ) {
				throw new ServiceRuntimeException( ExceptionEnum.NO_SUPPORTED_FILE_TYPE );
			}
			String destFileName = saveMultipartFile( multipartFile, baseDir );
			if( logger.isErrorEnabled() ) {
				logger.info( String.format(
						"Upload file - name:%s|size:%d|extension:%s",
						multipartFile.getName(), multipartFile.getSize(),
						extension ) );
			}
			FileUploadDto fileUploadDto = new FileUploadDto();
			fileUploadDto.setName(multipartFile.getName());
			fileUploadDto.setUrl(destFileName);
			fileUploadDto.setOriginalFilename(originalFilename);
			postProcess( fileUploadDto, baseDir );
			fileUploadDtos.add( fileUploadDto );
		}
		return fileUploadDtos;
	}

	protected abstract boolean checkExtension( String extension );

	protected abstract void postProcess( FileUploadDto response, String baseDir );

	protected String saveMultipartFile( MultipartFile multipartFile, String baseDir )
			throws IOException {
		String name = generateFileName( multipartFile.getOriginalFilename() );
		FileUtils.copyInputStreamToFile( multipartFile.getInputStream(),
				new File( baseDir + "/" + name ) );
		return name;
	}

	protected String generateFileName( String fileName ) {
		String newName = LocalDateUtils.getNowMilliseconds()
				+ randomStringGenerator.getNewString()
				// zq modify: concat the original file name
				+ "_" + getFilenameExcludeExtension(fileName)
				+ FilenameUtils.EXTENSION_SEPARATOR
				+ FilenameUtils.getExtension( fileName );
		return newName;
	}

	private String getFilenameExcludeExtension(String fileName) {
		if (StringUtils.isBlank(fileName)) {
			return "";
		}
		return StringUtils.substringBeforeLast(fileName, ".");
	}

	protected final List<FileUploadDto> processImageFile(Map<String, MultipartFile> multipartFiles, String baseDir, float scale) throws IOException {
		if( null == multipartFiles || multipartFiles.isEmpty() ) {
			throw new ServiceRuntimeException( ExceptionEnum.NO_MULTIPART );
		}
		List<FileUploadDto> fileUploadDtos = new ArrayList<FileUploadDto>(multipartFiles.size());
		for( Entry<String, MultipartFile> entry : multipartFiles.entrySet() ) {
			MultipartFile multipartFile = entry.getValue();
			String originalFilename = multipartFile.getOriginalFilename();
			String extension = FilenameUtils.getExtension( originalFilename );
			if( !isImageExtentsion( extension ) ) {
				throw new ServiceRuntimeException( ExceptionEnum.NO_SUPPORTED_FILE_TYPE );
			}
			String destFileName = saveImageFile( multipartFile, baseDir, scale, extension);
			if( logger.isErrorEnabled() ) {
				logger.info( String.format("Upload file - name:%s|size:%d|extension:%s", multipartFile.getName(), multipartFile.getSize(), extension ) );
			}
			FileUploadDto fileUploadDto = new FileUploadDto();
			fileUploadDto.setName(multipartFile.getName());
			fileUploadDto.setUrl(destFileName);
			fileUploadDto.setOriginalFilename(originalFilename);
			postProcess( fileUploadDto, baseDir );
			fileUploadDtos.add( fileUploadDto );
		}
		return fileUploadDtos;
	}

	protected String saveImageFile( MultipartFile multipartFile, String baseDir, float scale, String extension) throws IOException {
		String name = generateFileName(multipartFile.getOriginalFilename());
		File targetFile = new File(baseDir + "/" + name);
		if (scale > 0) { // cut image
			ImageUtils.cutCenterImage(multipartFile.getInputStream(), targetFile, scale, extension);
		} else {
			FileUtils.copyInputStreamToFile( multipartFile.getInputStream(),targetFile);
		}

		return name;
	}

	private boolean isImageExtentsion(String extension) {
		if (null == extension) {
			return false;
		}
		return IMG_SUFFIX.contains(extension);
	}

}
