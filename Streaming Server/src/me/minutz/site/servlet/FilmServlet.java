package me.minutz.site.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import me.minutz.site.MainTip;


public class FilmServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public FilmServlet() {
    	if(!MainTip.loaded){
    	MainTip.load();
    	}
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(!MainTip.loaded){
        	response.sendRedirect("/MinSrv/logout");  
        	return;
        }
		if(!MainTip.connected){
			return;
		}
		Cookie ck = null;
        Cookie cku = null;
        Cookie[] cookies = request.getCookies();
        if(cookies !=null){
        for(Cookie cookie : cookies){
        	if(cookie.getName().equals("u53r")){
        		ck=cookie;
        	}
        	if(cookie.getName().equals("u53ru")){
        		cku=cookie;
        	}
        }
        }
        if(ck!=null){
        	if(cku!=null){
        		//TODO RESTUL
        	}else{
        		response.sendRedirect("/MinSrv/logout");  
        	}
        }else{
        	response.sendRedirect("/MinSrv/logout");  
        }
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
