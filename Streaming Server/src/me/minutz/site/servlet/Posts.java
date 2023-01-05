package me.minutz.site.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import me.minutz.site.MainTip;
import me.minutz.site.SEngine;
import me.minutz.site.config.OConf;
import me.minutz.site.variabili.posts.Serial;
import me.minutz.site.variabili.useri.User;

@WebServlet("/Posts")
public class Posts extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String c = "cod";
    public Posts() {
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
        		User u = MainTip.getUserByUUID(cku.getValue());
        		if(u != null){
        		if(u.getUUID().equals(cku.getValue())){
        	        if(request.getParameter(c) != null){
        	            Serial serial = MainTip.getSerialByNume(request.getParameter(c));
        	            if(serial != null){
        	        		u.setLastViz(serial.getNume());
        	        		response.sendRedirect("/MinSrv/serial");  		
        	        		return;
        	            }
        	            }
        	        String content = OConf.POSTSHTML;
        	        String c2 = MainTip.getHTMLPosts(MainTip.getPostari());
        	        content=content.replace("%content", c2);
        	        request.setAttribute("da", content);
	                request.getRequestDispatcher("/WEB-INF/views/indx.jsp").forward(request, response);
        		}else{
//        			System.out.println("ERR:1");
        			response.sendRedirect("/MinSrv/logout");  
        		}
        		}else{
        			response.sendRedirect("/MinSrv/logout");  
        		}
        	}else{
//        		System.out.println("ERR:2");
        		response.sendRedirect("/MinSrv/logout");  
        	}
        }else{
//        	System.out.println("ERR:3");
        	response.sendRedirect("/MinSrv/logout");  
        }
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String s=request.getParameter("s");
		if(s!=null&&!s.isEmpty()){
	        String content = OConf.POSTSHTML;
	        String top = "<div class="+'"'+"no_contenido_home"+'"'+">Nu am gasit ce cautai !</div>";
	        if(s.length()>=2){
	        if(SEngine.searchEngine(s).size()>0){
	        top = MainTip.getHTMLPosts(SEngine.searchEngine(s));
	        }else{
	        	if(SEngine.searchEngineA(s)!=null){
	        		top = MainTip.getHTMLPosts(SEngine.searchEngineA(s));
	        	}
	        }
	        }
	        content=content.replace("%content", top);
	        request.setAttribute("da", content);
            request.getRequestDispatcher("/WEB-INF/views/indx.jsp").forward(request, response);
		}else{
			doGet(request, response);
		}
	}

}
