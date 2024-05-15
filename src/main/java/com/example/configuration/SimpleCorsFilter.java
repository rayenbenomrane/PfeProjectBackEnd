package com.example.configuration;

import java.io.IOException;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)

public class SimpleCorsFilter implements Filter{

	  @Override
	    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		  HttpServletResponse response = (HttpServletResponse) res;
	        HttpServletRequest request = (HttpServletRequest) req;

	        response.setHeader("Access-Control-Allow-Origin", "http://localhost:4200");
	        response.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE");
	        response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
	        response.setHeader("Access-Control-Max-Age", "3600");
	        response.setHeader("Access-Control-Allow-Credentials", "true");

	        if (!(request.getMethod().equalsIgnoreCase("OPTIONS"))) {
	            try {
	                chain.doFilter(req, res);
	            } catch(Exception e) {
	                e.printStackTrace();
	            }
	        } else {
	            System.out.println("Pre-flight");
	            response.setHeader("Access-Control-Allow-Methods", "POST,PUT,GET,DELETE");
	            response.setHeader("Access-Control-Max-Age", "3600");
	            response.setHeader("Access-Control-Allow-Headers", "authorization, content-type," +
	                    "access-control-request-headers,access-control-request-method,accept,origin,authorization,x-requested-with");
	            response.setStatus(HttpServletResponse.SC_OK);
	        }

	    }
	 @Override
	    public void init(FilterConfig filterConfig) throws ServletException {
	        Filter.super.init(filterConfig);
	    }

	    @Override
	    public void destroy() {
	        Filter.super.destroy();
	    }

}
