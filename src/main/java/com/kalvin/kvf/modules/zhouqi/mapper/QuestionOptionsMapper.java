package com.kalvin.kvf.modules.zhouqi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kalvin.kvf.modules.zhouqi.entity.QuestionOptions;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 * @since 2022-01-20 11:05:50
 */
public interface QuestionOptionsMapper extends BaseMapper<QuestionOptions> {

    /**
     * 查询列表(分页)
     * @param questionOptions 查询参数
     * @param page 分页参数
     * @return list
     */
    List<QuestionOptions> selectQuestionOptionsList(QuestionOptions questionOptions, IPage page);

}
