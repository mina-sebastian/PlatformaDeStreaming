package me.minutz.site;

import java.util.ArrayList;
import java.util.List;

import me.minutz.site.config.OConf;
import me.minutz.site.mysql.MySQL;
import me.minutz.site.variabili.posts.Episod;
import me.minutz.site.variabili.posts.Film;
import me.minutz.site.variabili.posts.Postare;
import me.minutz.site.variabili.posts.Serial;
import me.minutz.site.variabili.useri.User;

public class MainTip {
	
	public static boolean loaded=false,connected=false,request=false;
	public static MySQL SQL;
	
	public static String user = "root";
	public static String pass = "--------------";
	public static String host = "localhost";
	public static String db = "minsrv";
	
	public static void load(){
		if(!request){
			request=true;
		OConf.mdir();
		SQL=new MySQL("db","useri");
		connected=SQL.Connect(host, db, user, pass);
		SQL.loadFilme();
		SQL.loadUseris();
		SQL.loadEpisoades();
		SQL.loadSeriales();
		SQL.loadPosts();
		
		loadCuv();
		loaded=true;
		}
	}
	static List<String> cuvinte = new ArrayList<String>();
	static List<User> uO = new ArrayList<User>();
	static List<User> ulist = new ArrayList<User>();
	public static List<Postare> plist = new ArrayList<Postare>();
	static List<Episod> elist = new ArrayList<Episod>();
	static List<Serial> slist = new ArrayList<Serial>();
	static List<Film> flist = new ArrayList<Film>();
	
	private static String c(String d){
		return d.toLowerCase().replace(".", "").replace("-", "").replace(",", "");
	}
	
	private static boolean ac(String c){
		for(String s:cuvinte){
			if(c.equals(s)){
				return true;
			}
		}
		return false;
	}
	
	private static void addC(String d){
		String c = c(d);
		if(!ac(c)){
		cuvinte.add(c);
		}
	}
	
	public static void loadCuv(){
		for(Postare p: plist){
			if(p.getTitlu().contains(" ")){
				String[] s = p.getTitlu().split(" ");
				for(String d:s){
					addC(d);
				}
			}else{
				addC(p.getTitlu());
			}
			if(p.getDesc2().contains(" ")){
				String[] s = p.getDesc2().split(" ");
				for(String d:s){
					addC(d);
				}
			}else{
				addC(p.getDesc2());
			}
			if(p.getDescriere().contains(" ")){
				String[] s = p.getDescriere().split(" ");
				for(String d:s){
					addC(d);
				}
			}else{
				addC(p.getDescriere());
			}
		}
		System.out.println(cuvinte.size()+" cuvinte adaugate");
	}
	
	public static List<User> getUseriOnline(){
		return uO;
	}
	
	public static void addUserOnline(User u){
		uO.add(u);
	}
	
	public static void remUserOnline(String uuid){
		User user = getOnlineUserByUUID(uuid);
		if(user != null){
			uO.remove(user);
		}
	}
	
	public static User getOnlineUserByNume(String nume){
		for(User u:uO){
			if(u.getNume().equals(nume)){
				return u;
			}
		}
		return null;
	}
	
	public static User getOnlineUserByUUID(String uuid){
		for(User u:uO){
			if(u.getUUID().equals(uuid)){
				return u;
			}
		}
		return null;
	}
	
	//USERI
	public static List<User> getUseri(){
		return ulist;
	}
	
	public static void addUser(User u){
		ulist.add(u);
		
	}
	
	public static void remUser(String uuid){
		User user = getUserByUUID(uuid);
		if(user != null){
			ulist.remove(user);
		}
	}
	
	public static User getUserByNume(String nume){
		for(User u:ulist){
			if(u.getNume().equals(nume)){
				return u;
			}
		}
		return null;
	}
	
	public static User getUserByIP(String ip){
		for(User u:ulist){
			if(u.getLastIp().equals(ip)){
				return u;
			}
		}
		return null;
	}
	
	public static User getUserByUUID(String uuid){
		for(User u:ulist){
			if(u.getUUID().equals(uuid)){
				return u;
			}
		}
		return null;
	}
	//POSTARI
	public static List<Postare> getPostari(){
		return plist;
	}
	
	public static void addPost(Postare p){
		plist.add(p);
	}
	
	public static String getHTMLPosts(List<Postare> list){
		StringBuilder sb = new StringBuilder();
		for(Postare ps:list){
			String s = OConf.POSTDYNAMIC;
			s=s.replaceAll("%id", Integer.toString(ps.getId()));
			s=s.replaceAll("%nume", ps.getNume());
			s=s.replaceAll("%image", ps.getImage());
			s=s.replaceAll("%titlu", ps.getTitlu());
			double rate = Double.parseDouble(Integer.toString(ps.getRating()))/10;
			rate=(double)Math.round(rate * 100000d) / 100000d;
			s=s.replaceAll("%rate", Double.toString(rate));
			s=s.replaceAll("%descriere", ps.getDescriere());
			s=s.replaceAll("%rating", Integer.toString(ps.getRating()));
			s=s.replaceAll("%an", Integer.toString(ps.getAn()));
			s=s.replaceAll("%desc2", ps.getDesc2());
			sb.append(s);
		}
		return sb.toString();
	}
	
	public static void remPost(int id){
		Postare post = getPostById(id);
		if(post != null){
			plist.remove(post);
		}
	}
	
	public static Postare getPostById(int id){
		for(Postare u:plist){
			if(u.getId()==id){
				return u;
			}
		}
		return null;
	}
	
	public static Postare getPostByNume(String nume){
		for(Postare u:plist){
			if(u.getNume().equals(nume)){
				return u;
			}
		}
		return null;
	}
	//EPISOADE
	public static List<Episod> getEpisoade(){
		return elist;
	}
	
	public static void addEp(Episod p){
		elist.add(p);
	}
	
	public static void remEpisod(String serial,int sezon,int numar){
		Episod e = getEp(serial,sezon,numar);
		if(e != null){
			elist.remove(e);
		}
	}
	
	public static Episod getEp(String serial,int sezon,int numar){
		for(Episod u:elist){
			if(u.getSerial().equals(serial)){
				if(u.getSezon()==sezon){
					if(u.getNumar()==numar){
				return u;
					}
				}
			}
		}
		return null;
	}
	//SERIALE
	public static List<Serial> getSeriale(){
		return slist;
	}
	
	public static void addSerial(Serial p){
		slist.add(p);
	}
	
	public static void remSerial(String serial){
		Serial e = getSerialByNume(serial);
		if(e != null){
			slist.remove(e);
		}
	}
	
	public static Serial getSerialByNume(String serial){
		for(Serial u:slist){
			if(u.getNume().equals(serial)){
				return u;
			}
		}
		return null;
	}
	//FILME
	public static List<Film> getFilme(){
		return flist;
	}
	
	public static void addFilm(Film f){
		flist.add(f);
	}
	
	public static void remFilm(String f){
		Film e = getFilmByID(f);
		if(e != null){
			flist.remove(e);
		}
	}
	
	public static Film getFilmByID(String id){
		for(Film u:flist){
			if(u.getId().equals(id)){
				return u;
			}
		}
		return null;
	}
}
