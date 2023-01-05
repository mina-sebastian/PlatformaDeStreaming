package me.minutz.site.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import me.minutz.site.MainTip;
import me.minutz.site.config.OConf;
import me.minutz.site.variabili.posts.Episod;
import me.minutz.site.variabili.posts.Postare;
import me.minutz.site.variabili.posts.Serial;
import me.minutz.site.variabili.posts.Sezon;
import me.minutz.site.variabili.useri.User;

@WebServlet("/Serial")
public class SerialServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String link="link";
	public static String lk = "cod";
	public SerialServlet() {
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
        	        if(!u.getLastViz().isEmpty()){
//        	        	MainTip.SQL.loadEpisoade();
//        	        	MainTip.SQL.loadSeriale();
        	            Serial serial = MainTip.getSerialByNume(u.getLastViz());
        	            if(serial != null){
        	            	Postare p = MainTip.getPostByNume(serial.getNume());
        	            	if(request.getParameter(link)!= null){
        	            		if(request.getParameter(link).contains("x")){
        	            			String a = request.getParameter(link).split("x")[0];
        	            			String b = request.getParameter(link).split("x")[1];
        	            			if(a.isEmpty()){
        	            				return;
        	            			}
        	            			if(b.isEmpty()){
        	            				return;
        	            			}
        	            			if(!isInteger(a)){
        	        	            	response.sendRedirect("/MinSrv/serial");
        	            				return;
        	            			}
        	            			if(!isInteger(b)){
        	        	            	response.sendRedirect("/MinSrv/serial");      
        	            				return;
        	            			}
        	            		int sezon = Integer.parseInt(a);
        	            		int numar = Integer.parseInt(b);
        	            		Episod e = MainTip.getEp(serial.getNume(), sezon, numar);
        	            		if(e != null){
        	            		String d = OConf.SEREP;
        	            		d=d.replace("%img", p.getImage());
        	            		d=d.replace("%link", "http://188.173.52.120:8080/MinSrv/video?"+lk+"="+sezon+serial.getNume()+numar+".mp4");
        	            		d=d.replace("%sezon", Integer.toString(sezon));
        	            		d=d.replace("%episod", Integer.toString(numar));
        	            		d=d.replace("%cover", e.getPosterlink());
        	            		d=d.replace("%nume", "Sezonul "+sezon+" Episodul "+numar);
        	            		if(getNextEp(serial,e) != null){
        	            		d=d.replace("%next", link+"="+getNextEp(serial,e).getSezon()+"x"+getNextEp(serial,e).getNumar());
        	            		d=d.replace("%isnext", "block");
        	            		}else{
        	            	    	d=d.replace("%isnext", "none");
        	            		}
        	            		if(getAntEp(serial,e) != null){
        	            		d=d.replace("%ant", link+"="+getAntEp(serial,e).getSezon()+"x"+getAntEp(serial,e).getNumar());
        	            		d=d.replace("%isant", "block");
        	            		}else{
        	            	    	d=d.replace("%isant", "none");
        	            	    }
        	            		request.setAttribute("titlu", p.getTitlu()+"-S"+sezon+"-E"+numar);
        	            		request.setAttribute("nu", d);
        	            		request.getRequestDispatcher("/WEB-INF/views/episod.jsp").forward(request, response);
        	            		return;
        	            		}
        	            	}
        	            	}
        	            	String a = OConf.SERIAL;
        	            	String bc = OConf.SEZON;
        	            	String c = OConf.EPISOD;
        	            	StringBuilder b = new StringBuilder();
        	            	if(serial.getSezoane() != null){
        	            	for(Sezon s:serial.getSezoane()){
        	            		StringBuilder d = new StringBuilder();
        	            		String fdf=bc;
        	            		fdf=fdf.replaceAll("%sezon", Integer.toString(s.getNumar()));
        	            		for(Episod e:getEp46List(s)){
        	            		String eprez=c;
        	            		eprez=eprez.replaceAll("%sezon", Integer.toString(s.getNumar()));
        	            		eprez=eprez.replaceAll("%episod", Integer.toString(e.getNumar()));
        	            		eprez=eprez.replaceAll("%an", Integer.toString(e.getAn()));
        	            		eprez=eprez.replaceAll("%link", link);
        	            		d.append(eprez);
        	            		}
        	            		fdf=fdf.replaceAll("%episoade", d.toString());
        	            		b.append(fdf);
        	            	}
        	            	}
        	            	a=a.replaceAll("%episoade", Integer.toString(serial.getEpisoade().size()));
        	            	a=a.replaceAll("%image", p.getImage());
        	            	a=a.replaceAll("%titlu", p.getTitlu());
        	            	a=a.replaceAll("%sezoanelist", b.toString());
        	            	if(serial.getSezoane() != null){
        	            		a=a.replaceAll("%sezoane", Integer.toString(serial.getSezoane().size()));	
        	            	}else{
        	            		a=a.replaceAll("%sezoane","0");
        	            	}
        	            	a=a.replaceAll("%cover", serial.getImagine());
        	            	request.setAttribute("titlu", p.getTitlu());
        	            	request.setAttribute("da", a);
        	            	request.getRequestDispatcher("/WEB-INF/views/posts.jsp").forward(request, response);
        	            }else{
        	            	response.sendRedirect("/MinSrv/posts");   
        	            }
    	                
        	            }else{
        	            	response.sendRedirect("/MinSrv/posts");      	
        	            }
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
	
	public static boolean isInteger(String s) {
	    return isInteger(s,10);
	}

	public static boolean isInteger(String s, int radix) {
	    if(s.isEmpty()) return false;
	    for(int i = 0; i < s.length(); i++) {
	        if(i == 0 && s.charAt(i) == '-') {
	            if(s.length() == 1) return false;
	            else continue;
	        }
	        if(Character.digit(s.charAt(i),radix) < 0) return false;
	    }
	    return true;
	}
	
	public Episod getAntEp(Serial s,Episod e){
		int ep = e.getNumar()-1;
		int se = e.getSezon();
		Sezon sez = s.getSezonByNr(se);
		Episod ev = getEp(sez,ep);
		if(ev != null){
			return ev;
		}else{
			Sezon sf = getSez(s,sez.getNumar()-1);
			if(sf != null){
				return sf.getEplist().get(sf.getEplist().size()-1);
			}
		}
		return null;		
	}
	
	public List<Episod> getEp46List(Sezon s){
		List<Episod> gsa = new ArrayList<Episod>();
		for(int g=1;g<=s.getEplist().size();g++){
			gsa.add(getEp(s,g));
		}
		return gsa;
	}
	
	public Episod getNextEp(Serial s,Episod e){
		int ep = e.getNumar()+1;
		int se = e.getSezon();
		Sezon sez = s.getSezonByNr(se);
		Episod ev = getEp(sez,ep);
		if(ev != null){
			return ev;
		}else{
			Sezon sf = getSez(s,sez.getNumar()+1);
			if(sf != null){
				return sf.getEplist().get(0);
			}
		}
		return null;
	}
	
	public Sezon getSez(Serial s,int cs){
		for(Sezon se:s.getSezoane()){
			if(se.getNumar()==cs){
				return se;
			}
		}
		return null;
	}
	
	public Episod getEp(Sezon sez,int ep){
		for(Episod ec:sez.getEplist()){
			if(ec.getNumar()==ep){
				return ec;
			}
		}
		return null;
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
