package com.kalvin.kvf.modules.func.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kalvin.kvf.modules.func.entity.SummerCamp;

/**
 * <p>
 * 夏令营 服务类
 * </p>
 * @since 2022-08-16 14:54:46
 */
public interface SummerCampService extends IService<SummerCamp> {

    /**
     * 获取列表。分页
     * @param summerCamp 查询参数
     * @return page
     */
    Page<SummerCamp> listSummerCampPage(SummerCamp summerCamp);

}
