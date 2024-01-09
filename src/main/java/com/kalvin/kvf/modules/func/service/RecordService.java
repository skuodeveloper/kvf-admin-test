package com.kalvin.kvf.modules.func.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kalvin.kvf.modules.func.entity.Record;

/**
 * <p>
 *  服务类
 * </p>
 * @since 2021-03-30 16:24:21
 */
public interface RecordService extends IService<Record> {

    /**
     * 获取列表。分页
     * @param record 查询参数
     * @return page
     */
    Page<Record> listRecordPage(Record record);

}
