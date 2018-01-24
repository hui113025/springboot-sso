package com.zheng.filter;

import com.alibaba.fastjson.JSONObject;
import com.github.kevinsawicki.http.HttpRequest;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

/**
 * Created by zhenghui on 2018/1/18.
 */
public class SSOFilter implements Filter {
    private final String SSO_SERVER_DOMAIN = "http://127.0.0.1"; //认证中心地址 sso-server-url （spring-session支持ip和子域名）
    private final String SSO_SERVER_LOGIN_URL = SSO_SERVER_DOMAIN + "/page/login"; //认证中心登录地址 sso-server-url
    private final String SSO_SERVER_VERIFY_URL = SSO_SERVER_DOMAIN + "/verify"; //子系统验证地址 sso-server-verify-url

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("==>SSOFilter启动");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession();
        String uri = req.getRequestURI();
        String serverUrl = "//" + request.getServerName() + ":" + request.getServerPort();
        String callbackURL = serverUrl + (uri.length() == 1 ? "" : uri);

        if (req.getParameter("logout") != null && session != null) {
            session.invalidate();
            res.sendRedirect(serverUrl);
            return;
        }

        String token = req.getParameter("token");
        if (StringUtils.isNotEmpty(token)) {
            // 去sso认证中心校验token
            if (this.verify(token)) {
                res.sendRedirect(callbackURL);
                return;
            }
        }

        if (session.getAttribute("login") != null && session.getAttribute("login").equals(true)) {
            filterChain.doFilter(req, res);
            return;
        }
        res.sendRedirect(SSO_SERVER_LOGIN_URL + "?callbackURL=" + callbackURL);
    }

    @Override
    public void destroy() {

    }

    public Boolean verify(String token) {
        Map parmMap = Maps.newHashMap();
        parmMap.put("token", token);
        String body = HttpRequest.get(SSO_SERVER_VERIFY_URL).form(parmMap).body();
        JSONObject verify = new Gson().fromJson(body, JSONObject.class);
        if (verify != null && verify.getInteger("code").equals(0)) {
            return true;
        }
        return false;
    }
}
