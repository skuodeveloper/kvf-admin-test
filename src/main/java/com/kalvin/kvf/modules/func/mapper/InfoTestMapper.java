package com.kalvin.kvf.modules.func.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kalvin.kvf.modules.func.entity.InfoTest;
import com.kalvin.kvf.modules.func.entity.QuestionBank;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 * @since 2022-06-15 09:43:42
 */
public interface InfoTestMapper extends BaseMapper<InfoTest> {

    /**
     * 查询列表(分页)
     * @param infoTest 查询参数
     * @param page 分页参数
     * @return list
     */
    List<InfoTest> selectInfoTestList(InfoTest infoTest, IPage page);

    @Select("SELECT\n" +
            "\ta.*, b.score \n" +
            "FROM\n" +
            "\tfunc_info_test a\n" +
            "LEFT JOIN ( SELECT test_id, score FROM func_info_test_record WHERE user_id = #{userId} ) b \n" +
            "ON a.id = b.test_id")
    List<InfoTest> getInfotests(@Param("userId") String userId);

    @Select("SELECT * from func_question_bank\n" +
            "where id in(${ids})\n" +
            "ORDER BY subject_type")
    List<QuestionBank> getInfotest(@Param("ids") String ids);
}
