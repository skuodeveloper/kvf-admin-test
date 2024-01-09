package com.kalvin.kvf.modules.func.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kalvin.kvf.common.controller.BaseController;
import com.kalvin.kvf.common.dto.R;
import com.kalvin.kvf.modules.func.entity.TalkShow;
import com.kalvin.kvf.modules.func.service.TalkShowService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;


/**
 * <p>
 * 脱口秀 前端控制器
 * </p>
 * @since 2022-08-22 08:41:19
 */
@RestController
@RequestMapping("func/talkShow")
public class TalkShowController extends BaseController {

    @Autowired
    private TalkShowService talkShowService;

    @RequiresPermissions("func:talkShow:index")
    @GetMapping("index")
    public ModelAndView index() {
        return new ModelAndView("func/talkShow");
    }

    @GetMapping(value = "edit")
    public ModelAndView edit(Long id) {
        ModelAndView mv = new ModelAndView("func/talkShow_edit");
        TalkShow talkShow;
        if (id == null) {
            talkShow = new TalkShow();
        } else {
            talkShow = talkShowService.getById(id);
        }
        mv.addObject("editInfo", talkShow);
        return mv;
    }

    @GetMapping(value = "list/data")
    public R listData(TalkShow talkShow) {
        Page<TalkShow> page = talkShowService.listTalkShowPage(talkShow);
        return R.ok(page);
    }

    //    @RequiresPermissions("func:summerCamp:add")
    @PostMapping(value = "add")
    public R add(TalkShow talkShow) {
        TalkShow sc = talkShowService.getOne(new LambdaQueryWrapper<TalkShow>()
                .eq(TalkShow::getTel, talkShow.getTel()));

        if (sc != null) {
            return R.fail("您已报名，不可重复报名！");
        }

        Integer count = talkShowService.count();
        if (count == 20) {
            return R.fail("对不起，报名人数已满！");
        }

        talkShowService.save(talkShow);
        return R.ok();
    }

    @RequiresPermissions("func:talkShow:del")
    @PostMapping(value = "batchdel")
    public R batchdel(@RequestParam("ids") List<Long> ids) {
        talkShowService.removeByIds(ids);
        return R.ok();
    }

    @RequiresPermissions("func:talkShow:edit")
    @PostMapping(value = "edit")
    public R edit(TalkShow talkShow) {
        talkShowService.updateById(talkShow);
        return R.ok();
    }

    @RequiresPermissions("func:talkShow:del")
    @PostMapping(value = "del/{id}")
    public R del(@PathVariable Long id) {
        talkShowService.removeById(id);
        return R.ok();
    }

    @GetMapping(value = "get/{id}")
    public R get(@PathVariable Long id) {
        return R.ok(talkShowService.getById(id));
    }

    /**
     * 获取已报名数
     *
     * @return
     */
    @GetMapping(value = "getCount")
    public R getCount() {
        return R.ok(talkShowService.count());
    }
}

