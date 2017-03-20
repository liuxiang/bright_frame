package com.wosai.bright.filter;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class DomainFilter implements Filter {

	private String AccessControlAllowOrigin;

	@Override
	public void destroy() {
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		// 从web.xml配置
		AccessControlAllowOrigin = config.getInitParameter("access.control.allow.origin");
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		HttpServletResponse response = (HttpServletResponse) res;
		response.setHeader("Access-Control-Allow-Origin", AccessControlAllowOrigin);
		response.setHeader("Access-Control-Allow-Methods", "POST, GET, DELETE, PUT, PATCH");
		// response.setHeader("Access-Control-Max-Age", "3600");//缓存时间
		response.setHeader("Access-Control-Allow-Headers", "x-requested-with,token,client_version,server_version");
		chain.doFilter(req, res);
	}



}
