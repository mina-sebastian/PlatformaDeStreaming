package me.minutz.site.servlet;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import me.minutz.site.MainTip;
import me.minutz.site.Role;
import me.minutz.site.captcha.Captcha;
import me.minutz.site.captcha.backgrounds.SquigglesBackgroundProducer;
import me.minutz.site.captcha.gimpy.DropShadowGimpyRenderer;
import me.minutz.site.captcha.gimpy.GimpyRenderer;
import me.minutz.site.captcha.text.renderer.DefaultWordRenderer;
import me.minutz.site.captcha.text.renderer.WordRenderer;
import me.minutz.site.util.CUtil;
import me.minutz.site.util.CryptoUtil;
import me.minutz.site.variabili.useri.User;

/**
 * Servlet implementation class Home
 */
@WebServlet("/Home")
public class Home extends HttpServlet {
	private static final long serialVersionUID = 1L;
       int w=150,h=70;
	   List<Color> colors = new ArrayList<Color>();
		 List<Font> fonts = new ArrayList<Font>();
    public Home() {
    	if(!MainTip.loaded){
        MainTip.load();
    	}
    	colors.add(Color.GREEN);
		 colors.add(Color.BLUE);
		 colors.add(Color.MAGENTA);
		 colors.add(Color.BLACK);
		 colors.add(Color.YELLOW);
		 
		 fonts.add(new Font("Geneva", 2, 40));
		 fonts.add(new Font("Geneva", 2, 52));
		 fonts.add(new Font("Courier", 3, 40));
		 fonts.add(new Font("Courier", 3, 52));
    }
    
    public String get(String g){
    	return "<span class="+'"'+"error"+'"'+">"+g+"</span>";
    }
    
    public String getCM(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        if(cookies !=null){
        for(Cookie cookie : cookies){
        	if(cookie.getName().equals("u53rc")){
        		 CryptoUtil cr=new CryptoUtil();
        		return cr.decrypt("cheie-decriptare", cookie.getValue());
        	}
    }
        }
        return null;
    }
    
    public void delCM(HttpServletRequest request,HttpServletResponse response){
    	Cookie[] cookies = request.getCookies();
    	if(cookies != null){
        for(Cookie cookie : cookies){
        	if(cookie.getName().equals("u53rc")){
        		cookie.setMaxAge(0);
        		response.addCookie(cookie);
        	}
    }
    	}
    }
    
    public void setCM(HttpServletRequest request,HttpServletResponse response,String v){
        Cookie[] cookies = request.getCookies();
        CryptoUtil cr=new CryptoUtil();
        if(cookies !=null){
        for(Cookie cookie : cookies){
        	if(cookie.getName().equals("u53rc")){
        		cookie.setValue(cr.encrypt("cheie-decriptare", v));
        		response.addCookie(cookie);
        		return;
        	}
    }
        
        Cookie cku=new Cookie("u53rc",cr.encrypt("cheie-decriptare", v));
        cku.setMaxAge(9000*60);
        response.addCookie(cku);        
        }else{
	           Cookie cku=new Cookie("u53rc",cr.encrypt("cheie-decriptare", v));
	           cku.setMaxAge(9000*60);
	           response.addCookie(cku);
        }
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(!MainTip.connected){
			return;
		}
		if(request.getParameter("form")==null){
			response.sendRedirect("/MinSrv/home?form=login"); 
			return;
		}
		if(request.getParameter("form").equals("login")){
			delCM(request, response);
			request.getRequestDispatcher("/WEB-INF/views/home.jsp").forward(request, response);	
			return;
		}
		if(request.getParameter("form").equals("register")){
		 WordRenderer wordRenderer = new DefaultWordRenderer(colors, fonts);
		 SquigglesBackgroundProducer b = new SquigglesBackgroundProducer();
		 
		 Captcha captcha = new Captcha.Builder(w, h).addText(wordRenderer).addBackground(b).gimp().gimp().build();
		 GimpyRenderer r = new DropShadowGimpyRenderer();
		 BufferedImage i = captcha.getImage();
		 r.gimp(i);
		 request.setAttribute("d", "data:image/png;base64,"+CUtil.imgToBase64String(i, "png"));
		 setCM(request, response, captcha.getAnswer());
		request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
		}else{
			response.sendRedirect("/MinSrv/home?form=login"); 
			return;
		}

	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    String luser=request.getParameter("luser");  
	    String lpass=request.getParameter("lpass");
	    
	    String ruser=request.getParameter("ruser");  
	    String rpass=request.getParameter("rpass"); 
	    String rrpass=request.getParameter("rrpass");
	    String captchas=request.getParameter("captcha");
	    
	    //LOGIN CHECK
	    if(luser !=null&&!luser.isEmpty()){
	    	String d=luser.replace(" ", "");
	    	if(d ==null||d.isEmpty()){
	    		request.setAttribute("err", get("Name can't be empty."));
	    		request.getRequestDispatcher("/WEB-INF/views/home.jsp").forward(request, response);
	    		return;
	    	}
	    }
	    	if(lpass !=null&&!lpass.isEmpty()){
		    	String p=lpass.replace(" ", "");
		    	if(p ==null||p.isEmpty()){
		    		request.setAttribute("err", get("Password can't be empty."));
		    		request.getRequestDispatcher("/WEB-INF/views/home.jsp").forward(request, response);
		    		return;
		    	}
	    	}
	    	
	    	//REGISTER CHECK
	    	if(ruser !=null&&!ruser.isEmpty()){
		    	String p=ruser.replace(" ", "");
		    	if(p ==null||p.isEmpty()){
		    		request.setAttribute("err", get("Name can't be empty."));
		    		request.getRequestDispatcher("/WEB-INF/views/home.jsp").forward(request, response);
		    		return;
		    	}
	    	}
	    	
	    	if(rpass !=null&&!rpass.isEmpty()){
		    	String p=rpass.replace(" ", "");
		    	if(p ==null||p.isEmpty()){
		    		request.setAttribute("err", get("Password can't be empty."));
		    		request.getRequestDispatcher("/WEB-INF/views/home.jsp").forward(request, response);
		    		return;
		    	}
	    	}
	    	
	    	if(rrpass !=null&&!rrpass.isEmpty()){
		    	String p=rrpass.replace(" ", "");
		    	if(p ==null||p.isEmpty()){
	                request.setAttribute("err", get("Password can't be empty."));
	                request.getRequestDispatcher("/WEB-INF/views/home.jsp").forward(request, response);
		    		return;
		    	}
	    	}
	    	
	    //LOGIN
	    if(luser !=null&&!luser.isEmpty()){
	    	if(lpass !=null&&!lpass.isEmpty()){
	    		
	    		if(MainTip.getUserByNume(luser)!=null){
	    				login(luser,lpass,response,request);
	    		}else{
	                request.setAttribute("err", get("Username not registered."));
	                request.getRequestDispatcher("/WEB-INF/views/home.jsp").forward(request, response);
	                return;
	    		}
	    		
	    	}else{
	    		//REGISTER
	    		register(ruser,rpass,rrpass,response,request,captchas);
	    	}
	    }else{
	    	//REGISTER
	    	register(ruser,rpass,rrpass,response,request,captchas);
	    }
	    	}
	
//	public void loginv(String luser,String lpass,HttpServletResponse response,HttpServletRequest request) throws ServletException, IOException{
//		CryptoUtil cr = new CryptoUtil();
//		String uuid = Config.cont.getString("Profil."+luser+".uuid");
//		String parolacriptata=Config.cont.getString("Profil."+luser+".parola");
//		String decrk = Config.cont.getString("Profil."+luser+".uuid").replace("-", "")+"morcoveisaltareti";
//		String parola = cr.decrypt(decrk, parolacriptata);
//		if(parola.equals(lpass)){
//			
//			//BANAT
//			String banat = Config.cont.getString("Profil."+luser+".ban");
//			
//			if(banat.equals("nu")){
//	           Cookie ck=new Cookie("u53r",luser);  
//	           ck.setMaxAge(30*60);
//	           Cookie cku=new Cookie("u53ru",Config.cont.getString("Profil."+luser+".uuid"));  
//	           cku.setMaxAge(30*60);
//	            response.addCookie(ck);
//	            response.addCookie(cku);
//	            
//	            //DATA
//				DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
//				Date today = Calendar.getInstance().getTime();
//				String data = df.format(today);
//				Config.cont.set("Profil."+luser+".lastActiv", data);
//				Config.saveConturi();
//				
//				//IP
//				String lastIp = CUtil.getIp(request);
//				Config.cont.set("Profil."+luser+".lastIP", lastIp);
//				Config.saveConturi();
//				
//				//ROL
//				String numar = Config.cont.getString("Profil."+luser+".rol");
//				Role rol = Role.User;
//				for(Role r : Role.values()){
//					String toV = ""+r.getI();
//					if(toV.equals(numar)){
//						rol=r;
//					}
//				}
//				
//				User user = new User(luser,lpass,uuid,data,lastIp,rol,false);
//				MainTip.remUser(uuid);
//				MainTip.addUser(user);
//				System.out.println("Rol: "+rol.getNick());
//	            response.sendRedirect("/MinSrv/posts");
//			}else{
//	            request.setAttribute("err", "You are banned.");
//	            request.getRequestDispatcher("/WEB-INF/views/home.jsp").forward(request, response);
//	    		return;
//			}
//		}else{
//            request.setAttribute("err", "Incorrect password.");
//            request.getRequestDispatcher("/WEB-INF/views/home.jsp").forward(request, response);
//    		return;
//		}
//	}
	
//	public void registerv(String ruser,String rpass,String rrpass,HttpServletResponse response,HttpServletRequest request) throws ServletException, IOException{
//    	if(ruser !=null&&!ruser.isEmpty()){
//    		if(rpass !=null&&!rpass.isEmpty()){
//    			if(rrpass !=null&&!rrpass.isEmpty()){
//    				if(Config.cont.getString("Profil."+ruser+".nume")!=null){
//    	                request.setAttribute("err", "Username already registered.");
//    	                request.getRequestDispatcher("/WEB-INF/views/home.jsp").forward(request, response);
//    					return;
//    				}
//    				if(rpass.equals(rrpass)){
//    					CryptoUtil cr = new CryptoUtil();
//    					String uuid = UUID.randomUUID().toString();
//    					Config.cont.set("Profil."+ruser+".uuid", uuid);
//    					Config.saveConturi();
//    					Config.cont.set("Profil."+ruser+".nume", ruser);
//    					Config.saveConturi();
//    					Config.cont.set("Profil."+ruser+".parola", cr.encrypt(uuid.replace("-", "")+"morcoveisaltareti", rpass));
//    					Config.saveConturi();
//    					Config.cont.set("Profil."+ruser+".rol", ""+Role.User.getI());
//    					Config.saveConturi();
//    					Config.cont.set("Profil."+ruser+".ban", "nu");
//    					Config.saveConturi();
//    					login(ruser,rrpass,response,request);
//    				}else{
//    	                request.setAttribute("err", "Passwords don't match.");
//    	                request.getRequestDispatcher("/WEB-INF/views/home.jsp").forward(request, response);
//    				}
//    			}
//    		}
//    	}
//	}
	
	public void register(String ruser,String rpass,String rrpass,HttpServletResponse response,HttpServletRequest request,String rasp) throws ServletException, IOException{
    	if(ruser !=null&&!ruser.isEmpty()){
    		if(rpass !=null&&!rpass.isEmpty()){
    			if(rrpass !=null&&!rrpass.isEmpty()){
    				if(getCM(request) != null){
    				if(getCM(request).equals(rasp)){
    				if(MainTip.SQL.existUser(ruser)){
    	                request.setAttribute("err", get("Username already registered."));
          	       		 WordRenderer wordRenderer = new DefaultWordRenderer(colors, fonts);
           	    		 SquigglesBackgroundProducer b = new SquigglesBackgroundProducer();
           	    		 
           	    		 Captcha captcha = new Captcha.Builder(w, h).addText(wordRenderer).addBackground(b).gimp().gimp().build();
           	    		 GimpyRenderer r = new DropShadowGimpyRenderer();
           	    		 BufferedImage i = captcha.getImage();
           	    		 r.gimp(i);
           	    		 request.setAttribute("d", "data:image/png;base64,"+CUtil.imgToBase64String(i, "png"));
           	    		 setCM(request, response, captcha.getAnswer());
    	                request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
    					return;
    				}
    				String lastIP = CUtil.getIp(request);
    				if(MainTip.getUserByIP(lastIP)!=null){
    					User us = MainTip.getUserByIP(lastIP);
    					if(us.getRole()!=Role.Minutz){
        	                request.setAttribute("err", get("You already have an account on your ip: "+us.getNume()));
              	       		 WordRenderer wordRenderer = new DefaultWordRenderer(colors, fonts);
               	    		 SquigglesBackgroundProducer b = new SquigglesBackgroundProducer();
               	    		 
               	    		 Captcha captcha = new Captcha.Builder(w, h).addText(wordRenderer).addBackground(b).gimp().gimp().build();
               	    		 GimpyRenderer r = new DropShadowGimpyRenderer();
               	    		 BufferedImage i = captcha.getImage();
               	    		 r.gimp(i);
               	    		 request.setAttribute("d", "data:image/png;base64,"+CUtil.imgToBase64String(i, "png"));
               	    		 setCM(request, response, captcha.getAnswer());
        	                request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
    						return;
    					}
    				}
    				
    				if(rpass.equals(rrpass)){
    					DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
    					Date today = Calendar.getInstance().getTime();
    					String data = df.format(today);

    					CryptoUtil cr = new CryptoUtil();
    					String uuid = UUID.randomUUID().toString();
    					String pass = cr.encrypt(uuid.replace("-", "")+"cheie-decriptare", rpass);
    					
    					MainTip.SQL.register(ruser, pass, uuid, data, lastIP, Role.User.getI(), "nu");
    					login(ruser,rpass,response,request);
    				}else{
    	                request.setAttribute("err", get("Passwords don't match."));
          	       		 WordRenderer wordRenderer = new DefaultWordRenderer(colors, fonts);
           	    		 SquigglesBackgroundProducer b = new SquigglesBackgroundProducer();
           	    		 
           	    		 Captcha captcha = new Captcha.Builder(w, h).addText(wordRenderer).addBackground(b).gimp().gimp().build();
           	    		 GimpyRenderer r = new DropShadowGimpyRenderer();
           	    		 BufferedImage i = captcha.getImage();
           	    		 r.gimp(i);
           	    		 request.setAttribute("d", "data:image/png;base64,"+CUtil.imgToBase64String(i, "png"));
           	    		 setCM(request, response, captcha.getAnswer());
    	                request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
    				}
    				}else{
    	                request.setAttribute("err", get("Incorrect captcha "+getCM(request)));
    	       		 WordRenderer wordRenderer = new DefaultWordRenderer(colors, fonts);
    	    		 SquigglesBackgroundProducer b = new SquigglesBackgroundProducer();
    	    		 
    	    		 Captcha captcha = new Captcha.Builder(w, h).addText(wordRenderer).addBackground(b).gimp().gimp().build();
    	    		 GimpyRenderer r = new DropShadowGimpyRenderer();
    	    		 BufferedImage i = captcha.getImage();
    	    		 r.gimp(i);
    	    		 request.setAttribute("d", "data:image/png;base64,"+CUtil.imgToBase64String(i, "png"));
    	    		 setCM(request, response, captcha.getAnswer());
    	                request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);

    				}
    				}else{
    					
    	                request.setAttribute("err", get("Incorrect captcha!"));
       	       		 WordRenderer wordRenderer = new DefaultWordRenderer(colors, fonts);
       	    		 SquigglesBackgroundProducer b = new SquigglesBackgroundProducer();
       	    		 
       	    		 Captcha captcha = new Captcha.Builder(w, h).addText(wordRenderer).addBackground(b).gimp().gimp().build();
       	    		 GimpyRenderer r = new DropShadowGimpyRenderer();
       	    		 BufferedImage i = captcha.getImage();
       	    		 r.gimp(i);
       	    		 request.setAttribute("d", "data:image/png;base64,"+CUtil.imgToBase64String(i, "png"));
       	    		 setCM(request, response, captcha.getAnswer());
    	                request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);    					
    				}
    			}
    		}
    	}
	}
	
	
	public void login(String luser,String lpass,HttpServletResponse response,HttpServletRequest request) throws ServletException, IOException{
		User user = MainTip.getUserByNume(luser);
		if(user==null){
			System.out.println("User null");
		}
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		Date today = Calendar.getInstance().getTime();
		String data = df.format(today);
		if(user.getToBl()!= null){
			System.out.println("nuenull");
			if(user.getToBl().before(today)){
	            request.setAttribute("err", get("The account is blocked.Try again later!"));
	            request.getRequestDispatcher("/WEB-INF/views/home.jsp").forward(request, response);
	    		return;
			}
		}
		if(user.getParola().equals(lpass)){
			if(!user.isBanned()){
		           Cookie ck=new Cookie("u53r",luser);  
		           ck.setMaxAge(9000*60);
		           Cookie cku=new Cookie("u53ru",user.getUUID());  
		           cku.setMaxAge(9000*60);
					
					MainTip.SQL.uvar(user.getNume(),data);
					
		            response.addCookie(ck);
		            response.addCookie(cku);
		            
		            response.sendRedirect("/MinSrv/posts");
				}else{
		            request.setAttribute("err", get("You are banned."));
		            request.getRequestDispatcher("/WEB-INF/views/home.jsp").forward(request, response);
		    		return;
				}
			}else{
				user.setI(user.getI()+1);
				if(user.getI()>=3){
					long ONE_MINUTE_IN_MILLIS=60000;//millisecs

					Calendar date = Calendar.getInstance();
					long t= date.getTimeInMillis();
					Date a=new Date(t + (5 * ONE_MINUTE_IN_MILLIS));
					user.setToBl(a);
					System.out.println("3646");
				}

	            request.setAttribute("err", get("Incorrect password."));
	            request.getRequestDispatcher("/WEB-INF/views/home.jsp").forward(request, response);
	    		return;
			}

	}

}
