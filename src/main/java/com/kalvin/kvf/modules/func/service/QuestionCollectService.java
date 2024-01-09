package com.kalvin.kvf.modules.func.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kalvin.kvf.modules.func.entity.QuestionCollect;

/**
 * <p>
 *  服务类
 * </p>
 * @since 2022-01-17 08:50:34
 */
public interface QuestionCollectService extends IService<QuestionCollect> {

    /**
     * 获取列表。分页
     * @param questionCollect 查询参数
     * @return page
     */
    Page<QuestionCollect> listQuestionCollectPage(QuestionCollect questionCollect);

}
