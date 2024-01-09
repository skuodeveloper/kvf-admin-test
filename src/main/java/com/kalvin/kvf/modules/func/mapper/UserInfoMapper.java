package com.kalvin.kvf.modules.func.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kalvin.kvf.modules.func.entity.UserInfo;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 用户信息表 Mapper 接口
 * </p>
 * @since 2021-03-11 14:26:11
 */
public interface UserInfoMapper extends BaseMapper<UserInfo> {

    /**
     * 查询列表(分页)
     * @param userInfo 查询参数
     * @param page 分页参数
     * @return list
     */
    List<UserInfo> selectUserInfoList(UserInfo userInfo, IPage page);

    /**
     * 查询列表(分页)
     * @param userInfo 查询参数
     * @param page 分页参数
     * @return list
     */
    List<UserInfo> getRank(UserInfo userInfo, IPage page);

    @Select("SELECT S.realname, S.avatar, S.resCnt, SUM(S.level) resScore\n" +
            "FROM(\n" +
            "SELECT\n" +
            "\t\tA.realname,\n" +
            "\t\tB.avatar,\n" +
            "\t\tC.resCnt,\n" +
            "    DATE(A.create_time) AS date,\n" +
            "    MAX(A.level) AS level\n" +
            "FROM\n" +
            "    func_record A\n" +
            "LEFT JOIN func_user_info B ON A.userid = B.userid\n" +
            "LEFT JOIN (\n" +
            "SELECT userid,COUNT(*) resCnt FROM func_record\n" +
            "WHERE create_time >= '2022-12-21 00:00:00'" +
            "GROUP BY userid\n" +
            ") C ON A.userid = C.userid\n" +
            "WHERE A.create_time >= '2022-12-21 00:00:00'" +
            "GROUP BY A.realname, date,B.avatar,C.resCnt\n" +
            ") S\n" +
            "GROUP BY S.realname, S.avatar, S.resCnt\n" +
            "ORDER BY resScore desc , resCnt asc, realname")
    List<UserInfo> getMonthRank(UserInfo userInfo, IPage page);
}
