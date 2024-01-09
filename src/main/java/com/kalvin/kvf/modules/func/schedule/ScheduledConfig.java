package com.kalvin.kvf.modules.func.schedule;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiRobotSendRequest;
import com.dingtalk.api.response.OapiRobotSendResponse;
import com.kalvin.kvf.common.dto.R;
import com.kalvin.kvf.modules.func.entity.Record;
import com.kalvin.kvf.modules.func.entity.UserInfo;
import com.kalvin.kvf.modules.func.mapper.RecordMapper;
import com.kalvin.kvf.modules.func.service.UserInfoService;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Random;
import java.util.concurrent.Executors;

import static com.kalvin.kvf.modules.util.DateUtil.*;

@Component
@EnableScheduling
public class ScheduledConfig implements SchedulingConfigurer {
    @Resource
    private UserInfoService userInfoService;

    @Resource
    private RecordMapper recordMapper;

    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        //参数传入一个size为10的线程池
        scheduledTaskRegistrar.setScheduler(Executors.newScheduledThreadPool(10));
    }

    @Scheduled(cron="0 0 9 * * ?")
    public void schedule_1() {
        try {
            // 吴朝晖
            runBack("04154536361515");

            // 李军
            runBack("1012355633498");

            // 章勤超
            runBack("08594808546880");

            // 许锋
            runBack("08400764101146963");

            // 俞军
            runBack("0405442836181");

            // 李慧
            runBack("1012345921355");

            // 刘敏青
            runBack("1012355734948");

            // 黄燕婷
            runBack("08401237375000");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Scheduled(cron="0 0 10 * * ?")
    public void schedule_2() {
        try {
            DingTalkClient client = new DefaultDingTalkClient("https://openplatform-pro.ding.zj.gov.cn/robot/send?access_token=32b7b93409b6fa33ac2d07af0f09d45953a460deba131f66811fa626c3b8e12c");
            OapiRobotSendRequest request = new OapiRobotSendRequest();

            request.setMsgtype("text");
            OapiRobotSendRequest.Text text = new OapiRobotSendRequest.Text();
            text.setContent("小警提醒：请各位领导登录红色根脉强基系统！");
            request.setText(text);

            OapiRobotSendResponse response = client.execute(request);
            System.out.println("发送机器人：" + response.getErrmsg());
        } catch (Exception ex) {
            System.out.println("发送机器人：" + ex.getMessage());
        }
    }

    /**
     **东栅提醒1
     */
    @Scheduled(cron="0 0 11 * * ?")
    public void schedule_3() {
        try {
            DingTalkClient client = new DefaultDingTalkClient("https://openplatform-pro.ding.zj.gov.cn/robot/send?access_token=1e57cdae8928f24030363b6324f599373b9ff0701676f97689b8535d6c0cb098");
            OapiRobotSendRequest request = new OapiRobotSendRequest();

            request.setMsgtype("text");
            OapiRobotSendRequest.Text text = new OapiRobotSendRequest.Text();
            text.setContent("小警提醒：大家学习强国别忘记登陆，现在要求每天登陆，而且学习分数30分以上!");
            request.setText(text);

            OapiRobotSendResponse response = client.execute(request);
            System.out.println("发送机器人：" + response.getErrmsg());
        } catch (Exception ex) {
            System.out.println("发送机器人：" + ex.getMessage());
        }
    }

    /**
     *东栅提醒2
     */
    @Scheduled(cron="0 0 14 * * ?")
    public void schedule_4() {
        try {
            DingTalkClient client = new DefaultDingTalkClient("https://openplatform-pro.ding.zj.gov.cn/robot/send?access_token=1e57cdae8928f24030363b6324f599373b9ff0701676f97689b8535d6c0cb098");
            OapiRobotSendRequest request = new OapiRobotSendRequest();

            request.setMsgtype("text");
            OapiRobotSendRequest.Text text = new OapiRobotSendRequest.Text();
            text.setContent("小警提醒：大家午睡起床后，寝室内务卫生整理好，灯、空调等电器记得关闭！");
            request.setText(text);

            OapiRobotSendResponse response = client.execute(request);
            System.out.println("发送机器人：" + response.getErrmsg());
        } catch (Exception ex) {
            System.out.println("发送机器人：" + ex.getMessage());
        }
    }

    /**
     *技服提醒1
     */
    @Scheduled(cron="0 30 8 ? * MON-FRI")
    public void schedule_5() {
        try {
            DingTalkClient client = new DefaultDingTalkClient("https://openplatform-pro.ding.zj.gov.cn/robot/send?access_token=f965eb3b207ca46db21e4245ea5b1b1edb75199840e9b157114626f874d1e040");
            OapiRobotSendRequest request = new OapiRobotSendRequest();

            request.setMsgtype("text");
            OapiRobotSendRequest.Text text = new OapiRobotSendRequest.Text();
            text.setContent("大家学习强国别忘记登陆学习！");
            request.setText(text);

            OapiRobotSendResponse response = client.execute(request);
            System.out.println("发送机器人：" + response.getErrmsg());
        } catch (Exception ex) {
            System.out.println("发送机器人：" + ex.getMessage());
        }
    }

    /**
     *技服提醒2
     */
    @Scheduled(cron="0 0 10 ? * SAT,SUN")
    public void schedule_6() {
        try {
            DingTalkClient client = new DefaultDingTalkClient("https://openplatform-pro.ding.zj.gov.cn/robot/send?access_token=f965eb3b207ca46db21e4245ea5b1b1edb75199840e9b157114626f874d1e040");
            OapiRobotSendRequest request = new OapiRobotSendRequest();

            request.setMsgtype("text");
            OapiRobotSendRequest.Text text = new OapiRobotSendRequest.Text();
            text.setContent("大家学习强国别忘记登陆学习！");
            request.setText(text);

            OapiRobotSendResponse response = client.execute(request);
            System.out.println("发送机器人：" + response.getErrmsg());
        } catch (Exception ex) {
            System.out.println("发送机器人：" + ex.getMessage());
        }
    }

    /**
     * 自动做题
     */
    private void runBack(String userid) {
        try {
            UserInfo userInfo = userInfoService.getOne(new LambdaQueryWrapper<UserInfo>()
                    .eq(UserInfo::getUserid, userid));

            Random random = new Random();
            int score = random.nextInt(6) + 5;
            userInfo.setResScore(userInfo.getResScore() + score);

            //累计答题次数
            userInfo.setResCnt(userInfo.getResCnt() + 1);
            //保存本次答题积分，不是最高积分。
            userInfo.setMaxLevel(score);
            //保存今日最高分
            userInfo.setTodayCnt(score);
            userInfoService.saveOrUpdate(userInfo);

            Record record = new Record();
            record.setUserid(userid);
            record.setLevel(score);
            record.setRealname(userInfo.getRealname().replaceAll("　", ""));
            record.setCreateTime(randomDate(todayDateStr(), tomorrowDateStr()));
            recordMapper.insert(record);

        } catch (Exception ex) {

        }
    }
}
