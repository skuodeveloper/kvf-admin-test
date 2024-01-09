package com.kalvin.kvf.modules.func.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kalvin.kvf.modules.func.entity.DeptSys;
import com.kalvin.kvf.modules.func.entity.UserSys;
import com.kalvin.kvf.modules.func.mapper.UserTabMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import com.kalvin.kvf.common.controller.BaseController;
import com.kalvin.kvf.common.dto.R;
import com.kalvin.kvf.modules.func.entity.UserTab;
import com.kalvin.kvf.modules.func.service.UserTabService;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * <p>
 * 用户表 前端控制器func/userTab
 * </p>
 *
 * @since 2021-03-30 14:33:54
 */
@RestController
@RequestMapping("func/userTab")
public class UserTabController extends BaseController {

    @Autowired
    private UserTabService userTabService;

    @Resource
    private UserTabMapper userTabMapper;

    @RequiresPermissions("func:userTab:index")
    @GetMapping("index")
    public ModelAndView index() {
        return new ModelAndView("func/userTab");
    }

    @GetMapping(value = "edit")
    public ModelAndView edit(Long id) {
        ModelAndView mv = new ModelAndView("func/userTab_edit");
        UserTab userTab;
        if (id == null) {
            userTab = new UserTab();
        } else {
            userTab = userTabService.getById(id);
        }
        mv.addObject("editInfo", userTab);
        return mv;
    }

    @GetMapping(value = "list/data")
    public R listData(UserTab userTab) {
        Page<UserTab> page = userTabService.listUserTabPage(userTab);
        return R.ok(page);
    }

    @RequiresPermissions("func:userTab:add")
    @PostMapping(value = "add")
    public R add(UserTab userTab) {
        userTabService.save(userTab);
        return R.ok();
    }

    @RequiresPermissions("func:userTab:del")
    @PostMapping(value = "batchdel")
    public R batchdel(@RequestParam("ids") List<Long> ids) {
        userTabService.removeByIds(ids);
        return R.ok();
    }

    @RequiresPermissions("func:userTab:edit")
    @PostMapping(value = "edit")
    public R edit(UserTab userTab) {
        userTabService.updateById(userTab);
        return R.ok();
    }

    @RequiresPermissions("func:userTab:del")
    @PostMapping(value = "del/{id}")
    public R del(@PathVariable Long id) {
        userTabService.removeById(id);
        return R.ok();
    }

    @GetMapping(value = "get/{id}")
    public R get(@PathVariable Long id) {
        return R.ok(userTabService.getById(id));
    }

    /**
     * 获取今日未答题人员
     *
     * @return
     */
    @GetMapping(value = "getUserList")
    public R getUserList() {
        return R.ok(userTabMapper.selectUserList());
    }

    @GetMapping(value = "getUndoDays/{userid}")
    public R getUndoDays(@PathVariable String userid) {
        try {
            List<String> users = userTabMapper.getUndoDays(userid);
            return R.ok(users);
        } catch (Exception ex) {
            return R.fail(ex.getMessage());
        }
    }

    @GetMapping(value = "getUserSys")
    public R getUserSys(@RequestParam(value = "deptName") String deptName, @RequestParam(value = "date",required = false) String date) {
        try {
            if(StringUtils.isAllEmpty(date)){
                //设置日期格式
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                date = dateFormat.format(new Date()) + "%";
            }else{
                date = date + "%";
            }
            List<UserSys> users = userTabMapper.getUserSys(deptName, date);
            return R.ok(users);
        } catch (Exception ex) {
            return R.fail(ex.getMessage());
        }
    }

    /**
     * 获取部门挑战统计
     *
     * @return
     */
    @GetMapping(value = "getDeptSys")
    public R getDeptSys(@RequestParam(value = "date",required = false) String date) {
        try {
            if(StringUtils.isAllEmpty(date)){
                //设置日期格式
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                date = dateFormat.format(new Date()) + "%";
            }
            List<DeptSys> deptSys = userTabMapper.getDeptSys(date + "%");
            return R.ok(deptSys);
        } catch (Exception ex) {
            return R.fail(ex.getMessage());
        }
    }

    @GetMapping(value = "getUserStatus")
    public R getUserStatus(@RequestParam(value = "deptName") String deptName, @RequestParam Integer trainId) {
        try {
            List<UserSys> users = userTabMapper.getUserStatus(deptName, trainId);
            return R.ok(users);
        } catch (Exception ex) {
            return R.fail(ex.getMessage());
        }
    }
}

