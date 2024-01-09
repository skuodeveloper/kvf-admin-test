package com.kalvin.kvf.modules.func.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import com.kalvin.kvf.common.controller.BaseController;
import com.kalvin.kvf.common.dto.R;
import com.kalvin.kvf.modules.func.entity.Depart;
import com.kalvin.kvf.modules.func.service.DepartService;

import java.util.List;


/**
 * <p>
 *  前端控制器
 * </p>
 * @since 2021-04-01 14:33:52
 */
@RestController
@RequestMapping("func/depart")
public class DepartController extends BaseController {

    @Autowired
    private DepartService departService;

    @RequiresPermissions("func:depart:index")
    @GetMapping("index")
    public ModelAndView index() {
        return new ModelAndView("func/depart");
    }

    @GetMapping(value = "edit")
    public ModelAndView edit(Long id) {
        ModelAndView mv = new ModelAndView("func/depart_edit");
        Depart depart;
        if (id == null) {
            depart = new Depart();
        } else {
            depart = departService.getById(id);
        }
        mv.addObject("editInfo", depart);
        return mv;
    }

    @GetMapping(value = "list/data")
    public R listData(Depart depart) {
        Page<Depart> page = departService.listDepartPage(depart);
        return R.ok(page);
    }

    @RequiresPermissions("func:depart:add")
    @PostMapping(value = "add")
    public R add(Depart depart) {
        departService.save(depart);
        return R.ok();
    }

    @RequiresPermissions("func:depart:del")
    @PostMapping(value = "batchdel")
    public R batchdel(@RequestParam("ids") List<Long> ids) {
        departService.removeByIds(ids);
        return R.ok();
    }

    @RequiresPermissions("func:depart:edit")
    @PostMapping(value = "edit")
    public R edit(Depart depart) {
        departService.updateById(depart);
        return R.ok();
    }

    @RequiresPermissions("func:depart:del")
    @PostMapping(value = "del/{id}")
    public R del(@PathVariable Long id) {
        departService.removeById(id);
        return R.ok();
    }

    @GetMapping(value = "get/{id}")
    public R get(@PathVariable Long id) {
        return R.ok(departService.getById(id));
    }

}

