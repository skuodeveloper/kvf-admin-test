package com.kalvin.kvf.modules.func.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kalvin.kvf.modules.func.entity.Record;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 * @since 2021-03-30 16:24:21
 */
public interface RecordMapper extends BaseMapper<Record> {

    /**
     * 查询列表(分页)
     * @param record 查询参数
     * @param page 分页参数
     * @return list
     */
    List<Record> selectRecordList(Record record, IPage page);

    /**
     * 获取当天最高积分
     * @param userid
     * @return
     */
    @Select("SELECT MAX(level) level FROM func_record\n" +
            "WHERE TO_DAYS(create_time) = TO_DAYS(NOW())\n" +
            "and  userid = #{userid}")
    Integer getMaxLevel(String userid);
}
