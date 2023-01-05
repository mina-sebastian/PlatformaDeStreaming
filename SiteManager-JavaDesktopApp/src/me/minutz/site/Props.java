package me.minutz.site;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

public class Props {
	
	Properties appProps;
	String p = System.getProperty("user.dir")+"\\props.properties";
	FileWriter w;
	
	public Props(Main m){
		File f = new File(p);
		appProps = new Properties();
		if(!f.exists()){
			try {
				w = new FileWriter(p);
				appProps.setProperty("dbPass", "null");
				appProps.setProperty("upass", "null");
				appProps.setProperty("uname", "null");
				appProps.store(w, "Autor: Minutzu");
				w.close();
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			
		}
	}
	
	public void set(String u,String s){
		
		appProps.setProperty(u, s);
		try {
			w = new FileWriter(p);
			appProps.store(w, "Autor: Minutzu");
			w.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getDbPass(){
		try {
			appProps.load(new FileInputStream(p));
			if(appProps.getProperty("dbPass")!=null){
				if(!appProps.getProperty("dbPass").equals("null")){
					return appProps.getProperty("dbPass");
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String getPass(){
		try {
			appProps.load(new FileInputStream(p));
			if(appProps.getProperty("upass")!=null){
				if(!appProps.getProperty("upass").equals("null")){
					return appProps.getProperty("upass");
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String getUName(){
		try {
			appProps.load(new FileInputStream(p));
			if(appProps.getProperty("uname")!=null){
				if(!appProps.getProperty("uname").equals("null")){
					return appProps.getProperty("uname");
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
