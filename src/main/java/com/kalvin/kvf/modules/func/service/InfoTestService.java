package com.kalvin.kvf.modules.func.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kalvin.kvf.modules.func.entity.InfoTest;

/**
 * <p>
 *  服务类
 * </p>
 * @since 2022-06-15 09:43:42
 */
public interface InfoTestService extends IService<InfoTest> {

    /**
     * 获取列表。分页
     * @param infoTest 查询参数
     * @return page
     */
    Page<InfoTest> listInfoTestPage(InfoTest infoTest);

}
