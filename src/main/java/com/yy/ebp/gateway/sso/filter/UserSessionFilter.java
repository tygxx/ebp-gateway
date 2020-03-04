package com.yy.ebp.gateway.sso.filter;

import com.alibaba.fastjson.JSONObject;
import com.yy.framework.web.lang.RemoteIpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserSessionFilter implements Filter {

	private final Logger logger = LoggerFactory.getLogger(UserSessionFilter.class);

	/** 需要排除（不拦截）的URL */
//	private List<String> except;
//
//	public UserSessionFilter(List<String> except) {
//		this.except = except;
//	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		String path = request.getServletPath();

		JSONObject json = new JSONObject();
		json.put("method", request.getMethod());
		json.put("path", path);
		json.put("contentType", request.getContentType());
		json.put("ip", RemoteIpUtils.getIpAddress(request));
		json.put("scheme", request.getScheme());
		json.put("protocol", request.getProtocol());
		json.put("userId", request.getHeader("userId"));
		json.put("timestamp", request.getHeader("timestamp"));
		json.put("signature", request.getHeader("signature"));
		json.put("user-agent", request.getHeader("User-Agent"));
		logger.debug(json.toJSONString());

		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {

	}

}
