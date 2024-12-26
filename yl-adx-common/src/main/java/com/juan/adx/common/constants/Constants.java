package com.juan.adx.common.constants;

public interface Constants {



	interface FileUpload{
		/** 上传临时存储目录 */
		String LOCAL_UPLOAD_TEMP_DIR = "/tmp/upload/";


		/** 上传存储目录 */
		String LOCAL_UPLOAD_DIR = "/upload/";
	}

	interface Delete{
		/** 已删 */
		Integer Deleted = 1;
		/** 未删 */
		Integer UnDeleted = 0;
	}

}
