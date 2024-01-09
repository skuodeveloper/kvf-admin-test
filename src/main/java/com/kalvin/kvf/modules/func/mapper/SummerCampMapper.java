package com.kalvin.kvf.modules.func.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kalvin.kvf.modules.func.entity.SummerCamp;

import java.util.List;

/**
 * <p>
 * 夏令营 Mapper 接口
 * </p>
 * @since 2022-08-16 14:54:46
 */
public interface SummerCampMapper extends BaseMapper<SummerCamp> {

    /**
     * 查询列表(分页)
     * @param summerCamp 查询参数
     * @param page 分页参数
     * @return list
     */
    List<SummerCamp> selectSummerCampList(SummerCamp summerCamp, IPage page);

}
