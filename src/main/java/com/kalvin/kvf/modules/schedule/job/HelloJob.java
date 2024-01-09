package com.kalvin.kvf.modules.schedule.job;

import com.alibaba.xxpt.gateway.shared.client.http.ExecutableClient;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiRobotSendRequest;
import com.dingtalk.api.response.OapiRobotSendResponse;
import com.kalvin.kvf.common.controller.BaseController;
import com.kalvin.kvf.modules.schedule.constant.JobConstant;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 测试定时任务类。可删除
 */
@Slf4j
@Component
public class HelloJob extends QuartzJobBean {
    private Logger LOGGER = LoggerFactory.getLogger(HelloJob.class);

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        // 获得传入的参数
        Object params = jobExecutionContext.getMergedJobDataMap().get(JobConstant.JOB_MAP_KEY);
        Object webhook = jobExecutionContext.getMergedJobDataMap().get(JobConstant.JOB_MAP_ADDRESS);
        log.info("helloJob is running params={}, time:{}", params, new Date());

        try {
            OapiRobotSendRequest request = new OapiRobotSendRequest();
//            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/robot/send?access_token=9933cc8fd30809231360a9a8a54459eb15dd949eac75b1cb159ae0fdf568f546");
            DingTalkClient client = new DefaultDingTalkClient(webhook.toString());

            request.setMsgtype("text");
            OapiRobotSendRequest.Text text = new OapiRobotSendRequest.Text();
            text.setContent("小警提醒：" + params);
            request.setText(text);

            OapiRobotSendResponse response = client.execute(request);
            LOGGER.info("发送成功!");
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
    }
}