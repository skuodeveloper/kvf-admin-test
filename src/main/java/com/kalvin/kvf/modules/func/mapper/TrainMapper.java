package com.kalvin.kvf.modules.func.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kalvin.kvf.modules.func.entity.Train;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 在线培训表 Mapper 接口
 * </p>
 * @since 2022-06-08 09:36:53
 */
public interface TrainMapper extends BaseMapper<Train> {

    /**
     * 查询列表(分页)
     * @param train 查询参数
     * @param page 分页参数
     * @return list
     */
    List<Train> selectTrainList(Train train, IPage page);

    /**
     * 查询列表(分页)
     * @param train 查询参数
     * @param page 分页参数
     * @return list
     */
    List<Train> getTrainList(Train train, IPage page);

    @Select("SELECT DISTINCT department FROM func_user_tab")
    List<String> getDepts();
}
