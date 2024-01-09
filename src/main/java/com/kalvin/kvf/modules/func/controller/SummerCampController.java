package com.kalvin.kvf.modules.func.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import com.kalvin.kvf.common.controller.BaseController;
import com.kalvin.kvf.common.dto.R;
import com.kalvin.kvf.modules.func.entity.SummerCamp;
import com.kalvin.kvf.modules.func.service.SummerCampService;

import java.util.List;


/**
 * <p>
 * 夏令营 前端控制器
 * </p>
 *
 * @since 2022-08-16 14:54:46
 */
@RestController
@RequestMapping("func/summerCamp")
public class SummerCampController extends BaseController {

    @Autowired
    private SummerCampService summerCampService;

    @RequiresPermissions("func:summerCamp:index")
    @GetMapping("index")
    public ModelAndView index() {
        return new ModelAndView("func/summerCamp");
    }

    @GetMapping(value = "edit")
    public ModelAndView edit(Long id) {
        ModelAndView mv = new ModelAndView("func/summerCamp_edit");
        SummerCamp summerCamp;
        if (id == null) {
            summerCamp = new SummerCamp();
        } else {
            summerCamp = summerCampService.getById(id);
        }
        mv.addObject("editInfo", summerCamp);
        return mv;
    }

    @GetMapping(value = "list/data")
    public R listData(SummerCamp summerCamp) {
        Page<SummerCamp> page = summerCampService.listSummerCampPage(summerCamp);
        return R.ok(page);
    }

    //    @RequiresPermissions("func:summerCamp:add")
    @PostMapping(value = "add")
    public R add(SummerCamp summerCamp) {
        SummerCamp sc = summerCampService.getOne(new LambdaQueryWrapper<SummerCamp>()
                .eq(SummerCamp::getTel, summerCamp.getTel()));

        if (sc != null) {
            return R.fail("您已报名，不可重复报名！");
        }

        Integer count = summerCampService.count();
        if (count == 15) {
            return R.fail("对不起，夏令营总数15人，报名人数已满！");
        }

        summerCampService.save(summerCamp);
        return R.ok();
    }

    @RequiresPermissions("func:summerCamp:del")
    @PostMapping(value = "batchdel")
    public R batchdel(@RequestParam("ids") List<Long> ids) {
        summerCampService.removeByIds(ids);
        return R.ok();
    }

    @RequiresPermissions("func:summerCamp:edit")
    @PostMapping(value = "edit")
    public R edit(SummerCamp summerCamp) {
        summerCampService.updateById(summerCamp);
        return R.ok();
    }

    @RequiresPermissions("func:summerCamp:del")
    @PostMapping(value = "del/{id}")
    public R del(@PathVariable Long id) {
        summerCampService.removeById(id);
        return R.ok();
    }

    @GetMapping(value = "get/{id}")
    public R get(@PathVariable Long id) {
        return R.ok(summerCampService.getById(id));
    }

    /**
     * 获取已报名数
     *
     * @return
     */
    @GetMapping(value = "getCount")
    public R getCount() {
        return R.ok(summerCampService.count());
    }
}

