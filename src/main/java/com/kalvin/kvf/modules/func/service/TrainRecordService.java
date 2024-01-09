package com.kalvin.kvf.modules.func.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kalvin.kvf.modules.func.entity.TrainRecord;

/**
 * <p>
 *  服务类
 * </p>
 * @since 2022-06-10 09:34:01
 */
public interface TrainRecordService extends IService<TrainRecord> {

    /**
     * 获取列表。分页
     * @param trainRecord 查询参数
     * @return page
     */
    Page<TrainRecord> listTrainRecordPage(TrainRecord trainRecord);

}
