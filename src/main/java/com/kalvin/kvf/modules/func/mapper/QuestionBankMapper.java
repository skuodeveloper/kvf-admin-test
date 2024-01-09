package com.kalvin.kvf.modules.func.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kalvin.kvf.modules.func.entity.QuestionBank;
import com.kalvin.kvf.modules.sys.entity.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 题库 Mapper 接口
 * </p>
 * @since 2020-07-20 10:24:50
 */
public interface QuestionBankMapper extends BaseMapper<QuestionBank> {

    /**
     * 查询列表(分页)
     * @param questionBank 查询参数
     * @param page 分页参数
     * @return list
     */
    List<QuestionBank> selectQuestionBankList(@Param("questionBank") QuestionBank questionBank, IPage page);

    /**
     * 查询所有的出题人
     * @return
     */
    @Select("SELECT b.* FROM\n" +
            "\t(SELECT DISTINCT create_by FROM func_question_bank) a\n" +
            "LEFT JOIN  sys_user b\n" +
            "ON a.create_by = b.id")
    List<User> getBankCreators();

    /**
     * 获取专项类别列表
     * @return
     */
    @Select("SELECT distinct classify from func_question_bank\n" +
            "WHERE classify is not null;")
    List<String> getClassify();

    /**
     *
     * @return
     */
    @Select("(select * from func_question_bank\n" +
            "where classify = '科技应用' and subject_type = 1\n" +
            "order by RAND()\n" +
            "limit 20)\n" +
            "UNION \n" +
            "(select * from func_question_bank\n" +
            "where classify = '科技应用' and subject_type = 2\n" +
            "order by RAND()\n" +
            "limit 20)\n" +
            "UNION \n" +
            "(select * from func_question_bank\n" +
            "where classify = '科技应用' and subject_type = 3\n" +
            "order by RAND()\n" +
            "limit 10)")
    List<Integer> getRandom50();

    @Select("(select * from func_question_bank\n" +
            "where subject_type = 1 and classify = '新警题库'\n" +
            "order by RAND()\n" +
            "limit 60)\n" +
            "union  all \n" +
            "(select * from func_question_bank\n" +
            "where subject_type = 2 and classify = '新警题库'\n" +
            "order by RAND()\n" +
            "limit 40)")
    List<QuestionBank> getNewtests();
}
