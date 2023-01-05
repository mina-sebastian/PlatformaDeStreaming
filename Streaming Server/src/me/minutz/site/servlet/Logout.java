package me.minutz.site.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import me.minutz.site.MainTip;

@WebServlet("/Logout")
public class Logout extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public Logout() {
    	if(!MainTip.loaded){
    	MainTip.load();
    	}
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
        if(ck != null){
            if(cku != null){
        	ck.setMaxAge(0);
        	cku.setMaxAge(0);
        	response.addCookie(ck);
        	response.addCookie(cku);
            response.sendRedirect("/MinSrv/home");
            }else{
            	if(ck != null){
                	ck.setMaxAge(0);
                	response.addCookie(ck);
            	}
            	response.sendRedirect("/MinSrv/home");   	
            }
        }else{
        	if(cku != null){
            	cku.setMaxAge(0);
            	response.addCookie(cku);
        	}
         response.sendRedirect("/MinSrv/home");
}
        	
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
