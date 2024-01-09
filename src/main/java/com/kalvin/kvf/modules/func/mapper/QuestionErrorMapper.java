package com.kalvin.kvf.modules.func.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kalvin.kvf.modules.func.entity.QuestionBank;
import com.kalvin.kvf.modules.func.entity.QuestionError;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 * @since 2022-01-14 09:46:07
 */
public interface QuestionErrorMapper extends BaseMapper<QuestionError> {

    /**
     * 查询列表(分页)
     * @param questionError 查询参数
     * @param page 分页参数
     * @return list
     */
    List<QuestionError> selectQuestionErrorList(QuestionError questionError, IPage page);

    @Select("select b.* from func_question_error a\n" +
            "left join func_question_bank b\n" +
            "on a.qs_id = b.id\n" +
            "where a.status = 0\n" +
            "order by RAND()\n" +
            "limit 10")
    List<QuestionBank> getRandQuestionE();
}
