package com.kalvin.kvf.modules.zhouqi.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import com.kalvin.kvf.common.controller.BaseController;
import com.kalvin.kvf.common.dto.R;
import com.kalvin.kvf.modules.zhouqi.entity.Question;
import com.kalvin.kvf.modules.zhouqi.service.QuestionService;

import java.util.List;


/**
 * <p>
 *  前端控制器
 * </p>
 * @since 2022-01-20 11:05:36
 */
@RestController
@RequestMapping("zhouqi/question")
public class QuestionController extends BaseController {

    @Autowired
    private QuestionService questionService;

    @RequiresPermissions("zhouqi:question:index")
    @GetMapping("index")
    public ModelAndView index() {
        return new ModelAndView("zhouqi/question");
    }

    @GetMapping(value = "edit")
    public ModelAndView edit(Long id) {
        ModelAndView mv = new ModelAndView("zhouqi/question_edit");
        Question question;
        if (id == null) {
            question = new Question();
        } else {
            question = questionService.getById(id);
        }
        mv.addObject("editInfo", question);
        return mv;
    }

    @GetMapping(value = "list/data")
    public R listData(Question question) {
        Page<Question> page = questionService.listQuestionPage(question);
        return R.ok(page);
    }

    @RequiresPermissions("zhouqi:question:add")
    @PostMapping(value = "add")
    public R add(Question question) {
        questionService.save(question);
        return R.ok();
    }

    @RequiresPermissions("zhouqi:question:del")
    @PostMapping(value = "batchdel")
    public R batchdel(@RequestParam("ids") List<Long> ids) {
        questionService.removeByIds(ids);
        return R.ok();
    }

    @RequiresPermissions("zhouqi:question:edit")
    @PostMapping(value = "edit")
    public R edit(Question question) {
        questionService.updateById(question);
        return R.ok();
    }

    @RequiresPermissions("zhouqi:question:del")
    @PostMapping(value = "del/{id}")
    public R del(@PathVariable Long id) {
        questionService.removeById(id);
        return R.ok();
    }

    @GetMapping(value = "get/{id}")
    public R get(@PathVariable Long id) {
        return R.ok(questionService.getById(id));
    }

}

