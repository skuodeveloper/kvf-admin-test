package com.kalvin.kvf.modules.func.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kalvin.kvf.modules.func.entity.Train;

/**
 * <p>
 * 在线培训表 服务类
 * </p>
 * @since 2022-06-08 09:36:53
 */
public interface TrainService extends IService<Train> {

    /**
     * 获取列表。分页
     * @param train 查询参数
     * @return page
     */
    Page<Train> listTrainPage(Train train);

    /**
     * 获取列表。分页
     * @param train 查询参数
     * @return page
     */
    Page<Train> getTrainPage(Train train);
}
