package me.minutz.site.config;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class OConf {
	public static String FILM,POSTSHTML,POSTDYNAMIC,SEZON,EPISOD,SERIAL,SEREP,VIDEO=System.getProperty("user.dir")+"\\SiteDataBase\\videouri";
	public static void mdir(){
		File d = new File(VIDEO);
		if(!d.exists()){
			d.mkdir();
		}
		
		File m = new File(System.getProperty("user.dir")+"\\SiteDataBase\\matrita.txt");
    	if(!m.exists()){
    	try {
			m.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	m.setWritable(true);
    	}
    	
		File home = new File(System.getProperty("user.dir")+"\\SiteDataBase\\home.txt");
    	if(!home.exists()){
    	try {
			home.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	home.setWritable(true);
    	}
    	
		File serial = new File(System.getProperty("user.dir")+"\\SiteDataBase\\serial.txt");
    	if(!serial.exists()){
    	try {
			serial.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	serial.setWritable(true);
    	}
    	
		File sezon = new File(System.getProperty("user.dir")+"\\SiteDataBase\\sezon.txt");
    	if(!sezon.exists()){
    	try {
			sezon.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	sezon.setWritable(true);
    	}
    	
		File episod = new File(System.getProperty("user.dir")+"\\SiteDataBase\\episod.txt");
    	if(!episod.exists()){
    	try {
			episod.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	episod.setWritable(true);
    	}
    	
		File serep = new File(System.getProperty("user.dir")+"\\SiteDataBase\\serep.txt");
    	if(!serep.exists()){
    	try {
			serep.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	serep.setWritable(true);
    	}
    	
		File fep = new File(System.getProperty("user.dir")+"\\SiteDataBase\\fep.txt");
    	if(!fep.exists()){
    	try {
			fep.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	fep.setWritable(true);
    	}
    	SEREP=getSerep();
    	POSTSHTML=getHomeHTML();
    	POSTDYNAMIC=getMatrita();
    	SERIAL=getSerial();
    	SEZON=getSezon();
    	EPISOD=getEpisod();
    	FILM=getFilm();
	}
	
	public static String getMatrita(){
		StringBuilder contentBuilder = new StringBuilder();
		try {
		    BufferedReader in = new BufferedReader(new FileReader(new File(System.getProperty("user.dir")+"\\SiteDataBase\\matrita.txt")));
		    String str;
		    while ((str = in.readLine()) != null) {
		        contentBuilder.append(str);
		    }
		    in.close();
		} catch (IOException e) {
		}
		String content = contentBuilder.toString();
		return content;
	}
	
	public static String getHomeHTML(){
		StringBuilder contentBuilder = new StringBuilder();
		try {
		    BufferedReader in = new BufferedReader(new FileReader(new File(System.getProperty("user.dir")+"\\SiteDataBase\\home.txt")));
		    String str;
		    while ((str = in.readLine()) != null) {
		        contentBuilder.append(str);
		    }
		    in.close();
		} catch (IOException e) {
		}
		String content = contentBuilder.toString();
		return content;
	}
	
	public static String getSerial(){
		StringBuilder contentBuilder = new StringBuilder();
		try {
		    BufferedReader in = new BufferedReader(new FileReader(new File(System.getProperty("user.dir")+"\\SiteDataBase\\serial.txt")));
		    String str;
		    while ((str = in.readLine()) != null) {
		        contentBuilder.append(str);
		    }
		    in.close();
		} catch (IOException e) {
		}
		String content = contentBuilder.toString();
		return content;
	}
	
	public static String getFilm(){
		StringBuilder contentBuilder = new StringBuilder();
		try {
		    BufferedReader in = new BufferedReader(new FileReader(new File(System.getProperty("user.dir")+"\\SiteDataBase\\fep.txt")));
		    String str;
		    while ((str = in.readLine()) != null) {
		        contentBuilder.append(str);
		    }
		    in.close();
		} catch (IOException e) {
		}
		String content = contentBuilder.toString();
		return content;
	}
	
	public static String getSezon(){
		StringBuilder contentBuilder = new StringBuilder();
		try {
		    BufferedReader in = new BufferedReader(new FileReader(new File(System.getProperty("user.dir")+"\\SiteDataBase\\sezon.txt")));
		    String str;
		    while ((str = in.readLine()) != null) {
		        contentBuilder.append(str);
		    }
		    in.close();
		} catch (IOException e) {
		}
		String content = contentBuilder.toString();
		return content;
	}
	
	public static String getEpisod(){
		StringBuilder contentBuilder = new StringBuilder();
		try {
		    BufferedReader in = new BufferedReader(new FileReader(new File(System.getProperty("user.dir")+"\\SiteDataBase\\episod.txt")));
		    String str;
		    while ((str = in.readLine()) != null) {
		        contentBuilder.append(str);
		    }
		    in.close();
		} catch (IOException e) {
		}
		String content = contentBuilder.toString();
		return content;
	}

	public static String getSerep(){
		StringBuilder contentBuilder = new StringBuilder();
		try {
		    BufferedReader in = new BufferedReader(new FileReader(new File(System.getProperty("user.dir")+"\\SiteDataBase\\serep.txt")));
		    String str;
		    while ((str = in.readLine()) != null) {
		        contentBuilder.append(str);
		    }
		    in.close();
		} catch (IOException e) {
		}
		String content = contentBuilder.toString();
		return content;
	}
}
