package com.kalvin.kvf.modules.func.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kalvin.kvf.common.entity.BaseEntity;
import com.kalvin.kvf.modules.func.vo.AnswerDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 题库
 * </p>
 *
 * @since 2020-07-20 10:24:50
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("func_question_bank")
public class QuestionBank extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 题目类型
     */
    private Long subjectType;

    /**
     * 题目类别
     */
    private String classify;

    /**
     * 示例
     */
    private String example;

    /**
     * 问题
     */
    private String question;

    /**
     * 答案
     */
    private String answer;

    /**
     * 正确答案列表
     */
    @TableField(exist = false)
    private List<AnswerDto> answerDtos;

    /**
     * 正确答案ABCD表示
     */
    @TableField(exist = false)
    private String correct;

    /**
     * 状态
     */
    private Integer status;


    /**
     * 创建人
     */
    private Long createBy;

    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 修改人
     */
    private Long updateBy;

    /**
     * 修改时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**
     * 查询条件
     */
    @TableField(exist = false)
    private String createBys;
}
