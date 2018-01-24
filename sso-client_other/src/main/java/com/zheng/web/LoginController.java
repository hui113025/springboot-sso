package com.zheng.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by zhenghui on 2017/12/11.
 */
@Controller
public class LoginController {

    @RequestMapping("/")
    public ModelAndView home(ModelAndView mv) {
        mv.setViewName("home");
        return mv;
    }
}
