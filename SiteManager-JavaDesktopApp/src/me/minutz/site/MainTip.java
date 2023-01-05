package me.minutz.site;

import java.util.ArrayList;
import java.util.List;

import me.minutz.site.mysql.MySQL;
import me.minutz.site.posts.Episod;
import me.minutz.site.posts.Postare;
import me.minutz.site.posts.Serial;

public class MainTip {
	
	public static boolean loaded=false,connected=false;
	public static MySQL SQL;
	
	public static String user = "root";
	public static String host = "localhost";
	public static String db = "minsrv";
	
	public static void load(String pass){
		SQL=new MySQL("DB","useri");
		connected=SQL.Connect(host, db, user, pass);
		SQL.loadUseri();
		SQL.loadEpisoade();
		SQL.loadSeriale();
		SQL.loadPosts();
		loaded=true;
	}
	
	static List<User> uO = new ArrayList<User>();
	static List<User> ulist = new ArrayList<User>();
	static List<Postare> plist = new ArrayList<Postare>();
	static List<Episod> elist = new ArrayList<Episod>();
	static List<Serial> slist = new ArrayList<Serial>();
	
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
}