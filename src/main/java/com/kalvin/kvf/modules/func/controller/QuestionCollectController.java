package com.kalvin.kvf.modules.func.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.diboot.core.controller.BaseCrudRestController;
import com.kalvin.kvf.modules.func.entity.QuestionBank;
import com.kalvin.kvf.modules.func.mapper.QuestionCollectMapper;
import com.kalvin.kvf.modules.func.vo.AnswerDto;
import com.kalvin.kvf.modules.func.vo.QuestionBankVo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import com.kalvin.kvf.common.controller.BaseController;
import com.kalvin.kvf.common.dto.R;
import com.kalvin.kvf.modules.func.entity.QuestionCollect;
import com.kalvin.kvf.modules.func.service.QuestionCollectService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.kalvin.kvf.common.utils.ShiroKit.getUserId;


/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @since 2022-01-17 08:50:34
 */
@RestController
@RequestMapping("func/questionCollect")
public class QuestionCollectController extends BaseCrudRestController {

    @Autowired
    private QuestionCollectService questionCollectService;

    @Resource
    private QuestionCollectMapper questionCollectMapper;

    @RequiresPermissions("func:questionCollect:index")
    @GetMapping("index")
    public ModelAndView index() {
        return new ModelAndView("func/questionCollect");
    }

    @GetMapping(value = "edit")
    public ModelAndView edit(Long id) {
        ModelAndView mv = new ModelAndView("func/questionCollect_edit");
        QuestionCollect questionCollect;
        if (id == null) {
            questionCollect = new QuestionCollect();
        } else {
            questionCollect = questionCollectService.getById(id);
        }
        mv.addObject("editInfo", questionCollect);
        return mv;
    }

    @GetMapping(value = "list/data")
    public R listData(QuestionCollect questionCollect) {
        Page<QuestionCollect> page = questionCollectService.listQuestionCollectPage(questionCollect);
        return R.ok(page);
    }

    @RequiresPermissions("func:questionCollect:add")
    @PostMapping(value = "add")
    public R add(QuestionCollect questionCollect) {
        questionCollectService.save(questionCollect);
        return R.ok();
    }

    @RequiresPermissions("func:questionCollect:del")
    @PostMapping(value = "batchdel")
    public R batchdel(@RequestParam("ids") List<Long> ids) {
        questionCollectService.removeByIds(ids);
        return R.ok();
    }

    @RequiresPermissions("func:questionCollect:edit")
    @PostMapping(value = "edit")
    public R edit(QuestionCollect questionCollect) {
        questionCollectService.updateById(questionCollect);
        return R.ok();
    }

    @RequiresPermissions("func:questionCollect:del")
    @PostMapping(value = "del/{id}")
    public R del(@PathVariable Long id) {
        questionCollectService.removeById(id);
        return R.ok();
    }

    @GetMapping(value = "get/{id}")
    public R get(@PathVariable Long id) {
        return R.ok(questionCollectService.getById(id));
    }

    @PostMapping(value = "collect")
    public R collect(@RequestParam String userid, @RequestParam Integer qsid) {
        try {
            QuestionCollect questionCollect = questionCollectMapper.selectOne(new LambdaQueryWrapper<QuestionCollect>()
                    .eq(QuestionCollect::getUserid, userid)
                    .eq(QuestionCollect::getQsId, qsid));

            if (questionCollect == null) {
                questionCollect = new QuestionCollect();
                questionCollect.setUserid(userid);
                questionCollect.setQsId(qsid);
                questionCollect.setStatus(false);
                questionCollect.setCreateTime(new Date());

                questionCollectMapper.insert(questionCollect);
            } else {
                questionCollect.setStatus(!questionCollect.getStatus());
                questionCollect.setUpdateTime(new Date());
                questionCollectMapper.updateById(questionCollect);
            }

            return R.ok(questionCollect);
        }catch(Exception ex){
            return R.fail(ex.getMessage());
        }
    }

    @GetMapping(value = "getRandQuestionC")
    public R getRandQuestionC() {
        try {
            List<QuestionBank> questionBanks = questionCollectMapper.getRandQuestionC();

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

