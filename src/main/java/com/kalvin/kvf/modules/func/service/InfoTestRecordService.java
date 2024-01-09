package com.kalvin.kvf.modules.func.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kalvin.kvf.modules.func.entity.InfoTestRecord;

/**
 * <p>
 *  服务类
 * </p>
 * @since 2022-06-16 16:59:58
 */
public interface InfoTestRecordService extends IService<InfoTestRecord> {

    /**
     * 获取列表。分页
     * @param infoTestRecord 查询参数
     * @return page
     */
    Page<InfoTestRecord> listInfoTestRecordPage(InfoTestRecord infoTestRecord);

}
