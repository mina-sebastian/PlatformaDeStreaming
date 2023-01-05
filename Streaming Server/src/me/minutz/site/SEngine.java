package me.minutz.site;

import java.util.ArrayList;
import java.util.List;

import me.minutz.site.variabili.posts.Postare;

public class SEngine {
	
	private static float comp(String corect,String d){
		List<String> lsd = new ArrayList<String>();
		List<String> lss = new ArrayList<String>();
		int le=0;
		if(corect.length()>=2){
		for(int i=0;i<corect.length();i++){
			lss.add(Character.toString(corect.charAt(i)));
		}
		}else{
			return 0.0f;
		}
		
		if(d.length()>=2){
		for(int i=0;i<d.length();i++){
			lsd.add(Character.toString(d.charAt(i)));
		}
		}else{
			return 0.0f;
		}
		
		for(String un:lsd){
			for(String doi:lss){
				if(un.equals(doi)){
					le++;
				}
			}
		}
		
		int inc = d.length()-le;
		if(inc-3>le){
			return 0.0f;
		}
		
		float laSutaDinNr = (50.0f/100.0f)*Float.parseFloat(Integer.toString(corect.length()));
		float rez = Float.parseFloat(Integer.toString(le));
		if(rez>=laSutaDinNr){
			return rez;
		}
		return 0.0f;
	}
	
	private static String ec(String c){
		return c.toLowerCase().replace("-", "").replace(".", "").replace(" ","");
	}
	
	private static Postare getPostByID(int id,List<Postare> lp){
		for(Postare p:lp){
			if(p.getId()==id){
				return p;
			}
		}
		return null;
	}
	private static List<Postare> searchByParams(String[] params){
		List<Postare> l = new ArrayList<Postare>();
		for(String s:params){
			for(Postare p:searchByCuvant(s)){
				if(getPostByID(p.getId(), l)==null){
				l.add(p);
				}
			}
		}
		return l;
	}
	
	private static List<Postare> searchByCuvant(String c){
		List<Postare> sl = new ArrayList<Postare>();
		String d = c.toLowerCase().replace("-", "").replace(".", "");
		for(Postare p: MainTip.getPostari()){
			if(ec(p.getTitlu()).contains(d)){
				sl.add(p);
			}else{
				if(ec(p.getDescriere()).contains(d)){
					sl.add(p);
				}else{
					if(ec(Integer.toString(p.getRating())).contains(d)){
						sl.add(p);
					}else{
						if(Integer.toString(p.getAn()).contains(d)){
							sl.add(p);
						}
						if(ec(p.getDesc2()).contains(d)){
							sl.add(p);
						}
					}
				}
			}
		}
		return sl;
	}
	
	public static List<Postare> searchEngine(String s){
		if(s.contains(" ")){
		String[] params = s.split(" ");
		return searchByParams(params);
		}else{
			return searchByCuvant(s);
		}
	}
	
	private static String best(String s){
		float b=0.0f;
		String f = "";
		for(String c:MainTip.cuvinte){
			if(comp(c,s)>b){
			b=comp(c,s);
			f=c;
			}
		}
		return f;
	}
	
	public static List<Postare> searchEngineA(String q){
		String s=best(q);
		if(!s.isEmpty()){
		if(s.contains(" ")){
		String[] params = s.split(" ");
		return searchByParams(params);
		}else{
			return searchByCuvant(s);
		}
		}else{
			return null;
		}
	}

}
