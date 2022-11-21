package com.config;

import java.io.IOException;



import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.web.AuthenticationEntryPoint;

import com.model.ExceptionResponse;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

	  
		@Override
		public void commence(HttpServletRequest request, HttpServletResponse res,
				org.springframework.security.core.AuthenticationException authException)
				throws IOException, ServletException {
			
			ExceptionResponse ex = new ExceptionResponse(false,"Please get the key from authenticate api", "403");
			
			  res.setContentType("application/text;charset=UTF-8");
		        res.setStatus(403);
		        res.getWriter().write(ex.toString());
		}

		
	
}
