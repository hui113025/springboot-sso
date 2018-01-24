package com.zheng.web;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.UUID;

/**
 * Created by zhenghui on 2017/12/11.
 * 认证中心
 */
@Controller
public class LoginController {

    /**
     * 登录页面
     *
     * @param mv
     * @param request
     * @return
     */
    @RequestMapping("/page/login")
    public ModelAndView home(ModelAndView mv, HttpServletRequest request) {
        request.setAttribute("callbackURL", request.getParameter("callbackURL"));
        mv.setViewName("home");
        return mv;
    }

    /**
     * 登录
     *
     * @param username
     * @param password
     * @param request
     * @return
     */
    @RequestMapping("/login")
    @ResponseBody
    public String login(String username, String password, HttpServletRequest request) {
        this.checkLoginInfo(username, password);
        String token = UUID.randomUUID().toString();
        HttpSession session = request.getSession();
        try {
            session.setAttribute("login", true);
            session.setAttribute("token", token);
            session.setAttribute("username", username);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String backUrl = request.getParameter("callbackURL") + "?token=" + token;
        return backUrl;
    }

    /**
     * db用户验证
     *
     * @param username
     * @param password
     * @return
     */
    public boolean checkLoginInfo(String username, String password) {
        return true;
    }

    /**
     * 验证token
     *
     * @param token
     * @param request
     * @return
     */
    @RequestMapping("/verify")
    @ResponseBody
    public JSONObject checkoutToken(String token, HttpServletRequest request) {
        HttpSession session = request.getSession();
        JSONObject result = new JSONObject();
        if (session.getAttribute("token") != null && token.equals(session.getAttribute("token"))) {
            result.put("code", "0");
            result.put("msg", "认证成功");
        } else {
            result.put("code", "-1");
            result.put("msg", "token已失效，请重新登录！");
        }
        return result;
    }
}
