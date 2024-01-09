package com.kalvin.kvf.modules.func.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiUserGetRequest;
import com.dingtalk.api.request.OapiUserGetuserinfoRequest;
import com.dingtalk.api.response.OapiUserGetResponse;
import com.dingtalk.api.response.OapiUserGetuserinfoResponse;
import com.kalvin.kvf.common.controller.BaseController;
import com.kalvin.kvf.common.dto.R;
import com.kalvin.kvf.modules.config.URLConstant;
import com.kalvin.kvf.modules.func.entity.InfoTestRecord;
import com.kalvin.kvf.modules.func.entity.QuestionError;
import com.kalvin.kvf.modules.func.entity.Record;
import com.kalvin.kvf.modules.func.entity.UserInfo;
import com.kalvin.kvf.modules.func.mapper.InfoTestRecordMapper;
import com.kalvin.kvf.modules.func.mapper.QuestionErrorMapper;
import com.kalvin.kvf.modules.func.mapper.RecordMapper;
import com.kalvin.kvf.modules.func.service.UserInfoService;
import com.kalvin.kvf.modules.util.AccessTokenUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * <p>
 * 用户信息表 前端控制器
 * </p>
 *
 * @since 2021-03-11 14:26:11
 */
@RestController
@RequestMapping("func/userInfo")
public class UserInfoController extends BaseController {

    @Resource
    private UserInfoService userInfoService;

    @Resource
    private RecordMapper recordMapper;

    @Resource
    private InfoTestRecordMapper infoTestRecordMapper;

    @Resource
    private QuestionErrorMapper questionErrorMapper;

    @RequiresPermissions("func:userInfo:index")
    @GetMapping("index")
    public ModelAndView index() {
        return new ModelAndView("func/userInfo");
    }

    @GetMapping(value = "edit")
    public ModelAndView edit(Long id) {
        ModelAndView mv = new ModelAndView("func/userInfo_edit");
        UserInfo userInfo;
        if (id == null) {
            userInfo = new UserInfo();
        } else {
            userInfo = userInfoService.getById(id);
        }
        mv.addObject("editInfo", userInfo);
        return mv;
    }

    @GetMapping(value = "list/data")
    public R listData(UserInfo userInfo) {
        Page<UserInfo> page = userInfoService.listUserInfoPage(userInfo);
        return R.ok(page);
    }

    @GetMapping(value = "getRank")
    public R getRank(UserInfo userInfo) {
        Page<UserInfo> page;

        switch (userInfo.getUnionid()){
            case "month":
                page = userInfoService.getMonthRank(userInfo);
                break;
            case "all":
            default:
                page = userInfoService.getRank(userInfo);
                break;
        }
        return R.ok(page);
    }

    @RequiresPermissions("func:userInfo:add")
    @PostMapping(value = "add")
    public R add(UserInfo userInfo) {
        userInfoService.save(userInfo);
        return R.ok();
    }

    @RequiresPermissions("func:userInfo:del")
    @PostMapping(value = "batchdel")
    public R batchdel(@RequestParam("ids") List<Long> ids) {
        userInfoService.removeByIds(ids);
        return R.ok();
    }

    @RequiresPermissions("func:userInfo:edit")
    @PostMapping(value = "edit")
    public R edit(UserInfo userInfo) {
        userInfoService.updateById(userInfo);
        return R.ok();
    }

    @RequiresPermissions("func:userInfo:del")
    @PostMapping(value = "del/{id}")
    public R del(@PathVariable Long id) {
        userInfoService.removeById(id);
        return R.ok();
    }

    @GetMapping(value = "get/{id}")
    public R get(@PathVariable Long id) {
        return R.ok(userInfoService.getById(id));
    }

    /**
     * 钉钉用户登录，显示当前登录用户的userId和名称
     *
     * @param requestAuthCode 免登临时code
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public R login(@RequestParam(value = "authCode") String requestAuthCode) {
        try {
            //获取accessToken,注意正是代码要有异常流处理
            String accessToken = AccessTokenUtil.getToken();

            //获取用户信息
            DingTalkClient client1 = new DefaultDingTalkClient(URLConstant.URL_GET_USER_INFO);
            OapiUserGetuserinfoRequest request = new OapiUserGetuserinfoRequest();
            request.setCode(requestAuthCode);
            request.setHttpMethod("GET");
            OapiUserGetuserinfoResponse response = client1.execute(request, accessToken);

            // 获得到userId之后应用应该处理应用自身的登录会话管理（session）,避免后续的业务交互（前端到应用服务端）每次都要重新获取用户身份，提升用户体验
            String userId = response.getUserid();
            if (userId == null) {
                return R.fail(response.getErrmsg());
            }

            UserInfo userInfo = userInfoService.getOne(new LambdaQueryWrapper<UserInfo>()
                    .eq(UserInfo::getUserid, userId));

            if (userInfo == null) {
                //获取用户详情
                DingTalkClient client2 = new DefaultDingTalkClient(URLConstant.URL_USER_GET);
                OapiUserGetRequest oapiUserGetRequest = new OapiUserGetRequest();
                oapiUserGetRequest.setUserid(userId);
                oapiUserGetRequest.setHttpMethod("GET");
                OapiUserGetResponse ugr = client2.execute(oapiUserGetRequest, accessToken);
                if (ugr.getErrcode() == 0) {
                    userInfo = new UserInfo();
                    userInfo.setUserid(ugr.getUserid());
                    userInfo.setRealname(ugr.getName());
                    userInfo.setMobile(ugr.getMobile());
                    userInfo.setUnionid(ugr.getUnionid());
                    userInfo.setOpenid(ugr.getOpenId());
                    userInfo.setAvatar(ugr.getAvatar());
                    userInfo.setDepartment(StringUtils.join(ugr.getDepartment(), ","));

                    userInfoService.save(userInfo);
                }
            }

            return R.ok(userInfo);
        } catch (Exception ex) {
            return R.fail(ex.getMessage());
        }
    }

    /**
     * 钉钉用户登录，显示当前登录用户的userId和名称
     *
     * @param requestAuthCode 免登临时code
     */
    @RequestMapping(value = "/auth", method = RequestMethod.POST)
    @ResponseBody
    public R auth(@RequestParam(value = "authCode") String requestAuthCode) {
        try {
            //获取accessToken,注意正是代码要有异常流处理
            String accessToken = AccessTokenUtil.getToken();

            //获取用户信息
            DingTalkClient client = new DefaultDingTalkClient(URLConstant.URL_GET_USER_INFO);
            OapiUserGetuserinfoRequest request = new OapiUserGetuserinfoRequest();
            request.setCode(requestAuthCode);
            request.setHttpMethod("GET");
            OapiUserGetuserinfoResponse response = client.execute(request, accessToken);

            // 获得到userId之后应用应该处理应用自身的登录会话管理（session）,避免后续的业务交互（前端到应用服务端）每次都要重新获取用户身份，提升用户体验
            String body = response.getBody();
            JSONObject JB = JSONObject.parseObject(body);

            String name = JB.getString("name");

            return R.ok(JB);
        } catch (Exception ex) {
            return R.fail(ex.getMessage());
        }
    }

    /**
     * @param userid
     * @param errors
     * @param score
     * @return
     */
    @Transactional
    @PostMapping(value = "submit")
    public R submit(@RequestParam String userid, @RequestParam(required = false) List<Integer> corrects, @RequestParam(required = false) List<Integer> errors, @RequestParam Integer score) {
        try {
            UserInfo userInfo = userInfoService.getOne(new LambdaQueryWrapper<UserInfo>()
                    .eq(UserInfo::getUserid, userid));

            //今日最高得分
            Integer maxLevelToday = recordMapper.getMaxLevel(userid);
            if (maxLevelToday == null) {
                maxLevelToday = 0;
                //累计得分
                userInfo.setResScore(userInfo.getResScore() + score);
            } else {
                //本次答题比今天最高成绩还要好
                if (score > maxLevelToday) {
                    userInfo.setResScore(userInfo.getResScore() - maxLevelToday + score);
                }
            }

            //累计答题次数
            userInfo.setResCnt(userInfo.getResCnt() + 1);
            //保存本次答题积分，不是最高积分。
            userInfo.setMaxLevel(score);
            //保存今日最高分
            userInfo.setTodayCnt(Math.max(maxLevelToday, score));
            userInfoService.saveOrUpdate(userInfo);

            Record record = new Record();
            record.setUserid(userid);
            record.setLevel(score);
            record.setRealname(userInfo.getRealname().replaceAll("　", ""));
            recordMapper.insert(record);

            if (corrects != null && corrects.size() > 0) {
                for (Integer qsId : corrects) {
                    LambdaUpdateChainWrapper<QuestionError> lambdaUpdateChainWrapper = new LambdaUpdateChainWrapper<>(questionErrorMapper);
                    boolean update = lambdaUpdateChainWrapper
                            .eq(QuestionError::getUserid, userid)
                            .eq(QuestionError::getQsId, qsId)
                            .set(QuestionError::getStatus, 1).update();
                }
            }

            //有错题
            if (errors != null && errors.size() > 0) {
                List<QuestionError> questionErrors = new ArrayList<>();
                for (Integer qsId : errors) {
                    QuestionError questionError = new QuestionError();
                    questionError.setUserid(userid);
                    questionError.setQsId(qsId);
                    questionError.setStatus(false);
                    questionError.setCreateTime(new Date());

                    QuestionError Qe = questionErrorMapper.selectOne(new LambdaQueryWrapper<QuestionError>()
                            .eq(QuestionError::getQsId, qsId)
                            .eq(QuestionError::getUserid, userid)
                            .eq(QuestionError::getStatus, false));

                    //如果不存在，则插入错误题库
                    if (Qe == null) {
                        questionErrorMapper.insert(questionError);
                    }
                }
            }
            return R.ok(userInfo);
        } catch (Exception ex) {
            return R.fail(ex.getMessage());
        }
    }

    /**
     * @param userid
     * @param testid
     * @param score
     * @return
     */
    @Transactional
    @PostMapping(value = "testSubmit")
    public R testSubmit(@RequestParam String userid, @RequestParam Integer testid, @RequestParam Integer score) {
        try {
            UserInfo userInfo = userInfoService.getOne(new LambdaQueryWrapper<UserInfo>()
                    .eq(UserInfo::getUserid, userid));

            InfoTestRecord infoTestRecord = new InfoTestRecord();
            infoTestRecord.setTestId(testid);
            infoTestRecord.setUserId(userid);
            infoTestRecord.setUserName(userInfo.getRealname());
            infoTestRecord.setScore(score * 2);

            infoTestRecordMapper.insert(infoTestRecord);
            return R.ok();
        } catch (Exception ex) {
            return R.fail(ex.getMessage());
        }
    }

    /**
     * @param userid
     * @param testid
     * @param score
     * @return
     */
    @Transactional
    @PostMapping(value = "newSubmit")
    public R newSubmit(@RequestParam String userid, @RequestParam Integer testid, @RequestParam Integer score) {
        try {
            UserInfo userInfo = userInfoService.getOne(new LambdaQueryWrapper<UserInfo>()
                    .eq(UserInfo::getUserid, userid));

            InfoTestRecord infoTestRecord = new InfoTestRecord();
            infoTestRecord.setTestId(testid);
            infoTestRecord.setUserId(userid);
            infoTestRecord.setUserName(userInfo.getRealname());
            infoTestRecord.setScore(score);

            infoTestRecordMapper.insert(infoTestRecord);
            return R.ok();
        } catch (Exception ex) {
            return R.fail(ex.getMessage());
        }
    }
}

