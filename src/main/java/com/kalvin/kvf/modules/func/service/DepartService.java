package com.kalvin.kvf.modules.func.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kalvin.kvf.modules.func.entity.Depart;

/**
 * <p>
 *  服务类
 * </p>
 * @since 2021-04-01 14:33:52
 */
public interface DepartService extends IService<Depart> {

    /**
     * 获取列表。分页
     * @param depart 查询参数
     * @return page
     */
    Page<Depart> listDepartPage(Depart depart);

}
