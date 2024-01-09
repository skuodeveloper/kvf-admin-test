package com.kalvin.kvf.modules.func.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kalvin.kvf.modules.func.entity.Depart;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 * @since 2021-04-01 14:33:52
 */
public interface DepartMapper extends BaseMapper<Depart> {

    /**
     * 查询列表(分页)
     * @param depart 查询参数
     * @param page 分页参数
     * @return list
     */
    List<Depart> selectDepartList(Depart depart, IPage page);

}
