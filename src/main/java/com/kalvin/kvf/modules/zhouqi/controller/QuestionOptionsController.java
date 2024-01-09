package com.kalvin.kvf.modules.zhouqi.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import com.kalvin.kvf.common.controller.BaseController;
import com.kalvin.kvf.common.dto.R;
import com.kalvin.kvf.modules.zhouqi.entity.QuestionOptions;
import com.kalvin.kvf.modules.zhouqi.service.QuestionOptionsService;

import java.util.List;


/**
 * <p>
 *  前端控制器
 * </p>
 * @since 2022-01-20 11:05:50
 */
@RestController
@RequestMapping("zhouqi/questionOptions")
public class QuestionOptionsController extends BaseController {

    @Autowired
    private QuestionOptionsService questionOptionsService;

    @RequiresPermissions("zhouqi:questionOptions:index")
    @GetMapping("index")
    public ModelAndView index() {
        return new ModelAndView("zhouqi/questionOptions");
    }

    @GetMapping(value = "edit")
    public ModelAndView edit(Long id) {
        ModelAndView mv = new ModelAndView("zhouqi/questionOptions_edit");
        QuestionOptions questionOptions;
        if (id == null) {
            questionOptions = new QuestionOptions();
        } else {
            questionOptions = questionOptionsService.getById(id);
        }
        mv.addObject("editInfo", questionOptions);
        return mv;
    }

    @GetMapping(value = "list/data")
    public R listData(QuestionOptions questionOptions) {
        Page<QuestionOptions> page = questionOptionsService.listQuestionOptionsPage(questionOptions);
        return R.ok(page);
    }

    @RequiresPermissions("zhouqi:questionOptions:add")
    @PostMapping(value = "add")
    public R add(QuestionOptions questionOptions) {
        questionOptionsService.save(questionOptions);
        return R.ok();
    }

    @RequiresPermissions("zhouqi:questionOptions:del")
    @PostMapping(value = "batchdel")
    public R batchdel(@RequestParam("ids") List<Long> ids) {
        questionOptionsService.removeByIds(ids);
        return R.ok();
    }

    @RequiresPermissions("zhouqi:questionOptions:edit")
    @PostMapping(value = "edit")
    public R edit(QuestionOptions questionOptions) {
        questionOptionsService.updateById(questionOptions);
        return R.ok();
    }

    @RequiresPermissions("zhouqi:questionOptions:del")
    @PostMapping(value = "del/{id}")
    public R del(@PathVariable Long id) {
        questionOptionsService.removeById(id);
        return R.ok();
    }

    @GetMapping(value = "get/{id}")
    public R get(@PathVariable Long id) {
        return R.ok(questionOptionsService.getById(id));
    }

}

