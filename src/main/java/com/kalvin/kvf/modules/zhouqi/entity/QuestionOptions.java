package com.kalvin.kvf.modules.zhouqi.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import com.kalvin.kvf.common.entity.BaseEntity;

/**
 * <p>
 * 
 * </p>
 * @since 2022-01-20 11:05:50
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("zhouqi_question_options")
public class QuestionOptions extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    @TableId(value = "id", type = IdType.AUTO)
    private String id;

    /**
     * 
     */
    private String guid;

    /**
     * 
     */
    private String parentid;

    /**
     * 
     */
    private String optionName;

    /**
     * 
     */
    private String optionContent;

    /**
     * 
     */
    private Boolean isAnswer;

}
