package me.minutz.site.util;

import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import me.minutz.site.servlet.SerialServlet;

public class CUtil {
	public static String imgToBase64String(final BufferedImage img, final String formatName) {
	    final ByteArrayOutputStream os = new ByteArrayOutputStream();
	    try {
	        ImageIO.write(img, formatName, Base64.getEncoder().wrap(os));
	        return os.toString(StandardCharsets.ISO_8859_1.name());
	    } catch (final IOException ioe) {
	        throw new UncheckedIOException(ioe);
	    }
	}
	
	public static String getIp(HttpServletRequest request) {
	    String ipAddress = request.getHeader("X-FORWARDED-FOR");  
	       if (ipAddress == null) {  
	         ipAddress = request.getRemoteAddr();  
	   }
		ipAddress = ipAddress.contains(",") ? ipAddress.split(",")[0] : ipAddress;
		return ipAddress;
    }

	
	public static String getSerial(String s){
		
		String a = Character.toString(s.charAt(0));
		if(SerialServlet.isInteger(a)){
			String b = Character.toString(s.charAt(1));
			if(SerialServlet.isInteger(b)){
				String c = Character.toString(s.charAt(s.length()-2));
				if(SerialServlet.isInteger(c)){
					String d = Character.toString(s.charAt(s.length()-3));
					if(SerialServlet.isInteger(d)){
						return s.substring(1, s.length()-3);
					}else{
						
					}
				}
			}else{
				
			}
		}
		return null;
	}
}