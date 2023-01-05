package me.minutz.site.udate;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class Updater {
	  public static String up() {
		    try {
		      URL localURL = new URL("http://ip:8080/MinSrv/update/version.txt");
		      BufferedReader localBufferedReader = new BufferedReader(new InputStreamReader(localURL.openStream()));
		      String str;
		      while ((str = localBufferedReader.readLine()) != null) {
		    	  return str;
		      }
		      localBufferedReader.close();
		    } catch (Exception localException) {
		    	System.out.println("The site is not online!");
		    	try {
					Thread.sleep(3000);
					System.exit(1);
				} catch (InterruptedException e) {
		    	System.exit(1);
					e.printStackTrace();
				}

		    }
			return null;
		  }
}
