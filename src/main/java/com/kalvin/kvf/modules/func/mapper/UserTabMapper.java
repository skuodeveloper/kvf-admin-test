package com.kalvin.kvf.modules.func.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kalvin.kvf.modules.func.entity.DeptSys;
import com.kalvin.kvf.modules.func.entity.UserSys;
import com.kalvin.kvf.modules.func.entity.UserTab;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 * @since 2021-03-30 14:33:54
 */
public interface UserTabMapper extends BaseMapper<UserTab> {

    /**
     * 查询列表(分页)
     * @param userTab 查询参数
     * @param page 分页参数
     * @return list
     */
    List<UserTab> selectUserTabList(UserTab userTab, IPage page);

    /**
     * 用户列表
     * @return List<UserTab>
     */
    @Select("select a.*,IFNULL(b.today_cnt,0) todayCnt from func_user_tab a\n" +
            "left join func_user_info b\n" +
            "on a.userid = b.userid\n" +
            "order by todayCnt ")
    List<UserTab> selectUserList();

    @Select("SELECT t1.days\n" +
            "FROM\n" +
            "/*连续天数表*/\n" +
            "(SELECT\n" +
            "    date_format(@cdate := DATE_ADD(@cdate, INTERVAL - 1 DAY),'%Y-%m-%d') as days\n" +
            "  FROM\n" +
            "    (SELECT @cdate := DATE_ADD(NOW(), INTERVAL + 1 DAY) FROM func_record) t0\n" +
            "  LIMIT 10) t1\n" +
            "/*左连接数据表*/\n" +
            "LEFT JOIN\n" +
            "(select id,userid, date_format(create_time,'%Y-%m-%d') as days from func_record ) t2\n" +
            "ON t2.days = t1.days and t2.userid = #{userid}\n" +
            "GROUP BY t1.days\n" +
            "HAVING COUNT(t2.id) = 0")
    List<String> getUndoDays(@Param("userid") String userid);

    @Select("select T01.*,T02.cnt,round(T01.cs/T02.cnt * 100,2) bfb from (\n" +
            "select department,sum(case when userid is null then 0 else 1 end) cs from (\n" +
            "select T01.department,T02.userid,T02.realname from (\n" +
            "select distinct department from func_user_tab) T01 left join \n" +
            "(select T01.*,T02.department from (\n" +
            "SELECT distinct userid, realname  FROM func_record\n" +
            "WHERE create_time like #{date} )T01 LEFT JOIN func_user_tab T02 on T01.userid = T02.userid) T02 on T01.department = T02.department\n" +
            ") T01 group by department ) T01,(SELECT department, COUNT(*) cnt from func_user_tab\n" +
            "GROUP BY department) T02 where T01.department = T02.department\n" +
            "ORDER BY bfb desc")
    List<DeptSys> getDeptSys(String date);

    @Select("select realname ,avatar, (case when userid is null then 0 else 1 end) status from(\n" +
            "select T1.realname, T1.avatar, T2.userid from(\n" +
            "select * from func_user_tab\n" +
            "WHERE department = #{deptName}\n" +
            ")T1\n" +
            "LEFT JOIN(\n" +
            "select distinct userid, realname from func_record\n" +
            "where create_time like #{date}) T2\n" +
            "ON T1.userid = T2.userid) T3\n" +
            "order by status")
    List<UserSys> getUserSys(@Param("deptName") String deptName, @Param("date") String date);

    @Select("SELECT\n" +
            "\trealname,\n" +
            "\tavatar,\n" +
            "\t( CASE WHEN userid IS NULL THEN 0 ELSE 1 END ) status \n" +
            "FROM\n" +
            "\t(\n" +
            "\tSELECT\n" +
            "\t\tT1.realname,\n" +
            "\t\tT1.avatar,\n" +
            "\t\tT2.userid \n" +
            "\tFROM\n" +
            "\t\t( SELECT * FROM func_user_tab WHERE department = #{deptName} ) T1\n" +
            "\t\tLEFT JOIN ( SELECT DISTINCT user_id userid FROM func_train_record WHERE train_id = #{trainId}) T2 ON T1.userid = T2.userid \n" +
            "\t) T3 \n" +
            "ORDER BY status")
    List<UserSys> getUserStatus(@Param("deptName") String deptName, @Param("trainId") Integer trainId);
}
