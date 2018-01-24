package com.zheng.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AccessControlFilter implements Filter {
	public static Logger logger = LoggerFactory.getLogger(AccessControlFilter.class);

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		HttpServletResponse response = (HttpServletResponse) res;
		HttpServletRequest request = (HttpServletRequest) req;
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
		response.setHeader("Access-Control-Max-Age", "3600");

		response.setHeader("Access-Control-Allow-Headers",
				"x-requested-with, content-type, Content-Disposition");
		response.setHeader("Access-Control-Allow-Credentials", "true");
		if (request.getHeader("Access-Control-Request-Headers") != null) {
			response.setStatus(200);
		} else {
			chain.doFilter(req, res);
		}
	}

	@Override
	public void destroy() {
	}
}