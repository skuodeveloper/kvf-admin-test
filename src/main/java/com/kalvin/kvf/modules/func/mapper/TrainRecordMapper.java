package com.kalvin.kvf.modules.func.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kalvin.kvf.modules.func.entity.TrainRecord;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 * @since 2022-06-10 09:34:01
 */
public interface TrainRecordMapper extends BaseMapper<TrainRecord> {

    /**
     * 查询列表(分页)
     * @param trainRecord 查询参数
     * @param page 分页参数
     * @return list
     */
    List<TrainRecord> selectTrainRecordList(TrainRecord trainRecord, IPage page);

}
