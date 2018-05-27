package com.wushaoning.www.util;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 转换编码的过滤器
 * @author 10620
 *
 */
@WebFilter("/ChangeEncodingUtils")
public class ChangeEncodingUtils implements Filter {


    public ChangeEncodingUtils() {
    }

    @Override
	public void destroy() {
	}


	/**
	 * 过滤编码
	 */
    @Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse rep = (HttpServletResponse) response;
		req.setCharacterEncoding("utf-8");
		rep.setContentType("text/html;charset=utf-8");
		rep.setCharacterEncoding("utf-8");
		chain.doFilter(req, rep);
	}

    @Override
	public void init(FilterConfig fConfig) throws ServletException {
		System.out.println("成功");
	}

}
