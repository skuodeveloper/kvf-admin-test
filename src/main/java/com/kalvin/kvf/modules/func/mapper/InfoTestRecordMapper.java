package com.kalvin.kvf.modules.func.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kalvin.kvf.modules.func.entity.InfoTestRecord;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 * @since 2022-06-16 16:59:58
 */
public interface InfoTestRecordMapper extends BaseMapper<InfoTestRecord> {

    /**
     * 查询列表(分页)
     * @param infoTestRecord 查询参数
     * @param page 分页参数
     * @return list
     */
    List<InfoTestRecord> selectInfoTestRecordList(InfoTestRecord infoTestRecord, IPage page);

}
