package com.kalvin.kvf.modules.func.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import com.kalvin.kvf.common.entity.BaseEntity;

/**
 * <p>
 * 用户表
 * </p>
 * @since 2021-03-30 14:33:54
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("func_user_tab")
public class UserTab extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户id
     */
    private String userid;

    /**
     * 姓名
     */
    private String realname;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 部门id
     */
    private String department;

    /**
     * union
     */
    private String unionid;

    /**
     * 头像
     */
    private String avatar;

    @TableField(exist = false)
    private Integer todayCnt;
}
