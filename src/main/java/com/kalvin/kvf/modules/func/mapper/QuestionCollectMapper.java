package com.kalvin.kvf.modules.func.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kalvin.kvf.modules.func.entity.QuestionBank;
import com.kalvin.kvf.modules.func.entity.QuestionCollect;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 * @since 2022-01-17 08:50:34
 */
public interface QuestionCollectMapper extends BaseMapper<QuestionCollect> {

    /**
     * 查询列表(分页)
     * @param questionCollect 查询参数
     * @param page 分页参数
     * @return list
     */
    List<QuestionCollect> selectQuestionCollectList(QuestionCollect questionCollect, IPage page);

    @Select("select b.* from func_question_collect a\n" +
            "left join func_question_bank b\n" +
            "on a.qs_id = b.id\n" +
            "where a.status = 0\n" +
            "order by RAND()\n" +
            "limit 10")
    List<QuestionBank> getRandQuestionC();
}
