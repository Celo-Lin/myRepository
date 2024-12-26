package com.juan.adx.task.mapper.bidrecord;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.juan.adx.model.entity.api.RtbDisplayRecord;

@Mapper
public interface RtbDisplayRecordMapper {

	
	public static final String TABLE_ADX_AD_BID_RECORD = "adx_ad_bid_record";
	
	String SQL_COLUMN = " 	fid AS id, "
			+ "				ftrace_id AS traceId, "
			+ "				fslot_id AS slotId, "
			+ "				fbudget_id AS budgetId, "
			+ "				fctime AS ctime ";

	
	
	@Insert("	INSERT IGNORE INTO adx_ad_rtb_display_record ("
			+ "		ftrace_id,"
			+ "		fslot_id,"
			+ "		fbudget_id,"
			+ "		fctime"
			+ "	) VALUES ("
			+ "		#{rtbDisplayRecord.traceId},"
			+ "		#{rtbDisplayRecord.slotId},"
			+ "		#{rtbDisplayRecord.budgetId},"
			+ "		#{rtbDisplayRecord.ctime}"
			+ "	) ")
	int saveRtbDisplayRecord(@Param("rtbDisplayRecord") RtbDisplayRecord rtbDisplayRecord);

	
	@Select("	SELECT " + SQL_COLUMN
			+ "	FROM adx_ad_rtb_display_record "
			+ "	WHERE fid > #{startId} "
			+ "	ORDER BY fid ASC "
			+ "	LIMIT #{batchSize} ")
	List<RtbDisplayRecord> queryDisplayRecord(@Param("startId") long startId, @Param("batchSize") int batchSize);

	@Delete("	<script>"
			+ "		DELETE FROM adx_ad_rtb_display_record "
    		+ "		WHERE fid IN "
    		+ "		<foreach item=\"id\" collection=\"ids\" open=\"(\" separator=\",\" close=\")\"> "
    		+ "			#{id} "
    		+ "		</foreach> "
    		+ "	</script>")
	int deleteDisplayRecordByIds(@Param("ids") List<Long> ids);

}
