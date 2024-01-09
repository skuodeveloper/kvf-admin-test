package com.kalvin.kvf.modules.func.controller;

import cn.hutool.crypto.digest.DigestUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.kalvin.kvf.common.controller.BaseController;
import com.kalvin.kvf.common.dto.R;
import com.kalvin.kvf.modules.func.entity.UserInfo;
import com.kalvin.kvf.modules.func.service.UserInfoService;
import com.kalvin.kvf.modules.util.OkHttpUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Administrator
 */
@RestController
@RequestMapping("zzd")
public class ZzdController extends BaseController {
    @Resource
    private UserInfoService userInfoService;

    private static final String APPKEY = "a36bbb975fea4b90";
    private static final String APPSECRET = "2e39fea3b1f04d74";
    private static final String METHOD = "getUserInfo";

    @RequestMapping("/index")
    public ModelAndView index(HttpServletRequest request) {
        String token = request.getParameter("_token");
        String url = "http://111.1.30.66:8089/redship/index.html#/pages/index/index?token=" + token;
//        String url = "http://localhost:8080/redship#/pages/index/index?token=" + token;
        System.out.println("###########" + url + "############");
        return new ModelAndView(new RedirectView(url));
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public R login(@RequestParam(value = "token") String token) {
        try {
            System.out.println("---------------------token=" + token + "---------------------------\n");
            long timestamp = System.currentTimeMillis();
            String signature = DigestUtil.md5Hex("key" + APPKEY + "secret" + APPSECRET + "stamp" + timestamp);
//            String rs = OkHttpUtils.builder().url("https://xfszmf.nhgaj.com//api/open/user.ashx")
            String rs = OkHttpUtils.builder().url("http://10.203.12.181:8008/api/open/user.ashx")
                    .addHeader("_appkey", APPKEY)
                    .addHeader("_timestamp", String.valueOf(timestamp))
                    .addHeader("_signature", signature)
                    .addHeader("_method", METHOD)
                    .addHeader("_token", token)
//                .addParam("name", "xxx").addParam("pass", "xxx")
                    .get()
                    .sync();

            System.out.println(rs);

            JSONObject jb = (JSONObject) JSONObject.parseObject(rs).get("data");
            String mobile = jb.getString("mobile");
            UserInfo userInfo = userInfoService.getOne(new LambdaQueryWrapper<UserInfo>()
                    .eq(UserInfo::getMobile, mobile));

            if (userInfo == null) {
                userInfo = new UserInfo();
                userInfo.setUserid(jb.getString("openid"));
                userInfo.setRealname(jb.getString("nickname"));
                userInfo.setMobile(mobile);
                userInfo.setOpenid(jb.getString("openid"));
//                userInfo.setUnionid();
//                userInfo.setAvatar();
//                userInfo.setDepartment();

                userInfoService.save(userInfo);
            }

            return R.ok(userInfo);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return R.fail("err:" + ex.getMessage());
        }
    }
}
