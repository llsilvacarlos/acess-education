package br.com.portal.education.filter;

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
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

@WebFilter(filterName = "securityFilter", urlPatterns = "/pages/*")
public class SecurityFilter implements Filter {

    Logger logger = Logger.getLogger(SecurityFilter.class);

    private final String USER_AUTHENTICATED = "userAuthentication";

    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

	HttpServletRequest httpServletRequest = (HttpServletRequest) request;
	HttpServletResponse httpServletResponse = (HttpServletResponse) response;
	HttpSession session = httpServletRequest.getSession();
	
	String urlRedirect = httpServletRequest.getContextPath() + "/login.xhtml";
	if(session.getAttribute(USER_AUTHENTICATED) == null){
	    httpServletResponse.sendRedirect(urlRedirect);
	}else{
	    chain.doFilter(request, response);
	}
	
	
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {
	// TODO Auto-generated method stub

    }

}
