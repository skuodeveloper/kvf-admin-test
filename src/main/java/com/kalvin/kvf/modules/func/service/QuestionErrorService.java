package com.kalvin.kvf.modules.func.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kalvin.kvf.modules.func.entity.QuestionError;

/**
 * <p>
 *  服务类
 * </p>
 * @since 2022-01-14 09:46:07
 */
public interface QuestionErrorService extends IService<QuestionError> {

    /**
     * 获取列表。分页
     * @param questionError 查询参数
     * @return page
     */
    Page<QuestionError> listQuestionErrorPage(QuestionError questionError);

}
