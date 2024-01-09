package com.kalvin.kvf.modules.func.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.diboot.core.controller.BaseCrudRestController;
import com.kalvin.kvf.modules.func.entity.QuestionBank;
import com.kalvin.kvf.modules.func.mapper.QuestionErrorMapper;
import com.kalvin.kvf.modules.func.vo.AnswerDto;
import com.kalvin.kvf.modules.func.vo.QuestionBankVo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import com.kalvin.kvf.common.controller.BaseController;
import com.kalvin.kvf.common.dto.R;
import com.kalvin.kvf.modules.func.entity.QuestionError;
import com.kalvin.kvf.modules.func.service.QuestionErrorService;

import javax.annotation.Resource;
import java.util.List;


/**
 * <p>
 *  前端控制器
 * </p>
 * @since 2022-01-14 09:46:07
 */
@RestController
@RequestMapping("func/questionError")
public class QuestionErrorController extends BaseCrudRestController {

    @Autowired
    private QuestionErrorService questionErrorService;

    @Resource
    private QuestionErrorMapper questionErrorMapper;

    @RequiresPermissions("func:questionError:index")
    @GetMapping("index")
    public ModelAndView index() {
        return new ModelAndView("func/questionError");
    }

    @GetMapping(value = "edit")
    public ModelAndView edit(Long id) {
        ModelAndView mv = new ModelAndView("func/questionError_edit");
        QuestionError questionError;
        if (id == null) {
            questionError = new QuestionError();
        } else {
            questionError = questionErrorService.getById(id);
        }
        mv.addObject("editInfo", questionError);
        return mv;
    }

    @GetMapping(value = "list/data")
    public R listData(QuestionError questionError) {
        Page<QuestionError> page = questionErrorService.listQuestionErrorPage(questionError);
        return R.ok(page);
    }

    @RequiresPermissions("func:questionError:add")
    @PostMapping(value = "add")
    public R add(QuestionError questionError) {
        questionErrorService.save(questionError);
        return R.ok();
    }

    @RequiresPermissions("func:questionError:del")
    @PostMapping(value = "batchdel")
    public R batchdel(@RequestParam("ids") List<Long> ids) {
        questionErrorService.removeByIds(ids);
        return R.ok();
    }

    @RequiresPermissions("func:questionError:edit")
    @PostMapping(value = "edit")
    public R edit(QuestionError questionError) {
        questionErrorService.updateById(questionError);
        return R.ok();
    }

    @RequiresPermissions("func:questionError:del")
    @PostMapping(value = "del/{id}")
    public R del(@PathVariable Long id) {
        questionErrorService.removeById(id);
        return R.ok();
    }

    @GetMapping(value = "get/{id}")
    public R get(@PathVariable Long id) {
        return R.ok(questionErrorService.getById(id));
    }

    @GetMapping(value = "getRandQuestionE")
    public R getRandQuestionE() {
        try {
            List<QuestionBank> questionBanks = questionErrorMapper.getRandQuestionE();

            for (QuestionBank o : questionBanks) {
                List<AnswerDto> answerDtos = JSON.parseArray(o.getAnswer(), AnswerDto.class);
                o.setAnswerDtos(answerDtos);

                StringBuilder correct = new StringBuilder();
                for (int i = 0; i < answerDtos.size(); i++) {
                    if(answerDtos.get(i).isValue()){
                        correct.append(i);
                    }
                }

                String c = correct.toString().replaceAll("0","A")
                        .replaceAll("1","B")
                        .replaceAll("2","C")
                        .replaceAll("3","D");

                o.setCorrect(c);
            }

            List<QuestionBankVo> questionBankVos = super.convertToVoAndBindRelations (questionBanks, QuestionBankVo.class);

            return R.ok(questionBankVos);
        } catch (Exception ex) {
            return R.fail(ex.getMessage());
        }
    }
}

