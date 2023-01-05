package me.minutz.site.mysql;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import me.minutz.site.MainTip;
import me.minutz.site.Role;
import me.minutz.site.util.CryptoUtil;
import me.minutz.site.variabili.posts.Episod;
import me.minutz.site.variabili.posts.Film;
import me.minutz.site.variabili.posts.Postare;
import me.minutz.site.variabili.posts.Serial;
import me.minutz.site.variabili.useri.User;

public class MySQL
{
  private String HOST = null;
  private String DB = null;
  private String USER = null;
  private String PASS = null;
  private boolean connected = false;

  private Statement st = null;
  private Connection con = null;
  private MySQLFunc MySQL;
  private String conName;
  private String tname;
  private String posts;
  private String ep;
  private String seriale,filme;

  public MySQL(String name,String tname)
  {
	this.tname = tname;
	posts=tname+"post";
	ep=tname+"ep";
	seriale=tname+"seriale";
	filme=tname+"filme";
    this.conName = name;
    this.connected = false;
  }

  public Boolean Connect(String host, String db, String user, String pass) {
    this.HOST = host;
    this.DB = db;
    this.USER = user;
    this.PASS = pass;
    this.MySQL = new MySQLFunc(host, db, user, pass);
    this.con = this.MySQL.open();
    try {
      this.st = this.con.createStatement();
      this.connected = true;
      if(!isTabel()){
      createT();
      }
      if(!isPTabel()){
    	  createP();
      }
      if(!isETabel()){
    	  createE();
      }
      if(!isSTabel()){
    	  createS();
      }
      if(!isFTabel()){
    	  createF();
      }
  	System.out.println("[" + this.conName + "] Connected to the database.");
    } catch (SQLException e) {
      this.connected = false;
  	System.out.println("[" + this.conName + "] Could not connect to the database.");
    }
    this.MySQL.close(this.con);
    return Boolean.valueOf(this.connected);
  }

  public int countRows()
  {
    int count = 0;
    ResultSet set = query(String.format("SELECT * FROM %s", new Object[] { tname }));
    try {
      while (set.next())
        count++;
    }
    catch (SQLException e) {
    	System.out.println("Could not select all rows from table: " + tname + ", error: " + e.getErrorCode());
    }
    return count;
  }
  
  public void uvar(String nume,String lastAct){
	  String q = "UPDATE "+tname+" SET "
			  	+"lastActivity='%lact'"
//			  	+"lastIP='%lap'"
			  	+" WHERE `nume`='%num'";
	  q=q.replaceAll("%lact", lastAct);
	  q=q.replaceAll("%num", nume);
	  execute(q);
	  loadUseris();
  }
  
  public void update(String nume,String parola,String uuid,String lastActivity,String lastIP,int role,String banned){
	  String q = "UPDATE "+tname+" SET "
			  	+"parola='%pass',"
			  	+"uuid='%uui',"
			  	+"lastActivity='%lact',"
			  	+"lastIP='%lap',"
			  	+"role='%rol',"
			  	+"banned='%ban'"
			  	+" WHERE `nume`='%num'";
	  q=q.replaceAll("%pass", parola);
	  q=q.replaceAll("%uui", uuid);
	  q=q.replaceAll("%lact", lastActivity);
	  q=q.replaceAll("%lap", lastIP);
	  q=q.replaceAll("%rol", ""+role);
	  q=q.replaceAll("%ban", banned);
	  q=q.replaceAll("%num", nume);
	  execute(q);
	  loadUseris();
  }
  
  public void register(String nume,String parola,String uuid,String lastActivity,String lastIP,int role,String banned){
	  String q = "INSERT INTO "+tname+" (nume, parola, uuid, lastActivity, lastIP, role, banned) VALUES ('"+nume+"' ,'"+parola+"' ,'"+uuid+"' ,'"+lastActivity+"' ,'"+lastIP+"' ,'"+role+"' ,'"+banned+"');";
	  execute(q);
	  loadUseris();
  }
  
  public boolean existUser(String user){
	  for(User u:MainTip.getUseri()){
		  if(u.getNume().equals(user)){
			  return true;
		  }
	  }
	  return false;
  }
  
  public void loadUseris(){
		String query = "select * from "+tname;
		ResultSet rs = query(query);
		try{
		while(rs.next()){
			String uuid = rs.getString("uuid");
			MainTip.remUser(uuid);
			String nume = rs.getString("nume");
			
			String ps = rs.getString("parola");
			CryptoUtil cr = new CryptoUtil();
			String parola = cr.decrypt(uuid.replace("-", "")+"morcoveisaltareti", ps);
			
//			String lastViz= rs.getString("lastViz");
			String lastActivity = rs.getString("lastActivity");
			String lastIP = rs.getString("lastIP");
			int ro = rs.getInt("role");
			Role role = Role.User;
			for(Role r : Role.values()){
				int toV = r.getI();
				if(toV==ro){
					role=r;
				}
			}
			String s = rs.getString("banned");
			boolean banned = true;
			if(s.equals("nu")){
				banned=false;
			}
			User u = new User(nume,parola,uuid,lastActivity,lastIP,role,banned,"null");
			MainTip.addUser(u);
		}
		}catch(SQLException e){
  	    	System.out.println("[" + this.conName + "] Error loading users: " + e.getMessage());
		}
  }
  
  public void loadSeriales(){
		String query = "select * from "+seriale;
		ResultSet rs = query(query);
		try{
		while(rs.next()){
			String serial = rs.getString("serial");
			MainTip.remSerial(serial);
			String img = rs.getString("poster");
			List<Episod> eps = new ArrayList<Episod>();
//			List<Episod> ewr = new ArrayList<Episod>();
			for(Episod e:MainTip.getEpisoade()){
				if(e.getSerial().equals(serial)){
					eps.add(e);
				}
			}
			
			Serial s = new Serial(serial,eps,img);
			MainTip.addSerial(s);
		}
		}catch(SQLException e){
	    	System.out.println("[" + this.conName + "] Error loading seriale: " + e.getMessage());
		}	  
  }
  
  public void loadFilme(){
		String query = "select * from "+filme;
		ResultSet rs = query(query);
		try{
		while(rs.next()){
  		   String id = rs.getString("id");

			MainTip.remFilm(id);
			int an = rs.getInt("an");
			String nume = rs.getString("nume");
			String posterLink = rs.getString("poster");
			Film f = new Film(id,nume,posterLink,an);
			MainTip.addFilm(f);
		}
		}catch(SQLException e){
	    	System.out.println("[" + this.conName + "] Error loading filme: " + e.getMessage());
		}	  
  }
  
  public void loadEpisoades(){
		String query = "select * from "+ep;
		ResultSet rs = query(query);
		try{
		while(rs.next()){
			int nr = rs.getInt("numar");
			int sezon = rs.getInt("sezon");
			int an = rs.getInt("an");
			String serial = rs.getString("serial");
			MainTip.remEpisod(serial, sezon, nr);
			String posterLink = rs.getString("posterlink");
			Episod ep = new Episod(serial,posterLink,nr,sezon,an);
			MainTip.addEp(ep);
		}
		}catch(SQLException e){
	    	System.out.println("[" + this.conName + "] Error loading episodes: " + e.getMessage());
		}	  
  }
  
  public void loadPosts(){
		String query = "select * from "+posts;
		ResultSet rs = query(query);
		try{
		while(rs.next()){
			int id = rs.getInt("id");
			String nume = rs.getString("nume");
			String image = rs.getString("image");
			int rating = rs.getInt("rating");
			String titlu = rs.getString("titlu");
			String descriere = rs.getString("descriere");
			int an = rs.getInt("an");
			String desc2 = rs.getString("desc2");
			Postare p = new Postare(nume, image, titlu, descriere, desc2, id, rating, an);
			Postare po = MainTip.getPostById(id);
			if(po!=null){
				if(po.getTitlu().equals(titlu)){
				MainTip.remPost(id);
				}
			}
			MainTip.addPost(p);
		
		}
		}catch(SQLException e){
  	    	System.out.println("[" + this.conName + "] Error loading posts: " + e.getMessage());
		}
  }

  public boolean isFTabel(){
		try {
			DatabaseMetaData dbm = con.getMetaData();
			ResultSet tables = dbm.getTables(null, null, filme, null);
			if (!tables.next()) {
				return false;
			}else{
				return true;
			}
		} catch (SQLException e) {
	    	System.out.println("[" + this.conName + "] Error executing tabel check: " + e.getMessage());
		}
		return true;
	}
  
  
  
  public boolean isTabel(){
  		try {
  			DatabaseMetaData dbm = con.getMetaData();
  			ResultSet tables = dbm.getTables(null, null, tname, null);
  			if (!tables.next()) {
  				return false;
  			}else{
  				return true;
  			}
  		} catch (SQLException e) {
  	    	System.out.println("[" + this.conName + "] Error executing tabel check: " + e.getMessage());
  		}
  		return true;
  	}
  
  public boolean isPTabel(){
		try {
			DatabaseMetaData dbm = con.getMetaData();
			ResultSet tables = dbm.getTables(null, null, posts, null);
			if (!tables.next()) {
				return false;
			}else{
				return true;
			}
		} catch (SQLException e) {
	    	System.out.println("[" + this.conName + "] Error executing tabel check: " + e.getMessage());
		}
		return true;
	}
  
  public boolean isETabel(){
		try {
			DatabaseMetaData dbm = con.getMetaData();
			ResultSet tables = dbm.getTables(null, null, ep, null);
			if (!tables.next()) {
				return false;
			}else{
				return true;
			}
		} catch (SQLException e) {
	    	System.out.println("[" + this.conName + "] Error executing tabel check: " + e.getMessage());
		}
		return true;
	}
  
  public boolean isSTabel(){
		try {
			DatabaseMetaData dbm = con.getMetaData();
			ResultSet tables = dbm.getTables(null, null, seriale, null);
			if (!tables.next()) {
				return false;
			}else{
				return true;
			}
		} catch (SQLException e) {
	    	System.out.println("[" + this.conName + "] Error executing tabel check: " + e.getMessage());
		}
		return true;
	}
  
  public void createS(){
	  String q = "CREATE TABLE "+seriale+"("
			  + "serial varchar(20) NOT NULL,"
			  + "poster varchar(200) NOT NULL"
			  + ");";
	  execute(q);
  }   
  
  public void createF(){
	  String q = "CREATE TABLE "+filme+"("
			  + "id varchar(20) NOT NULL,"
			  + "nume varchar(20) NOT NULL,"
			  + "poster varchar(200) NOT NULL,"
			  + "an int(4) NOT NULL"
			  + ");";
	  execute(q);
  }   

  public void createE(){
  String q = "CREATE TABLE "+ep+"("
		  + "serial varchar(20) NOT NULL,"
		  + "posterlink varchar(200) NOT NULL,"
		  + "numar int(2) NOT NULL,"
		  + "an int(4) NOT NULL,"
		  + "sezon int(2) NOT NULL"
		  + ");";
  execute(q);
}  
  
  public void createP(){
	  String q = "CREATE TABLE "+posts+"("
			  + "id int(10) NOT NULL,"
			  + "nume varchar(20) NOT NULL,"
			  + "image varchar(200) NOT NULL,"
			  + "rating int(3) NOT NULL,"
			  + "titlu varchar(30) NOT NULL,"
			  + "descriere varchar(200) NOT NULL,"
			  + "an int(4) NOT NULL,"
			  + "desc2 varchar(10) NOT NULL"
			  + ");";
	  execute(q);
  }
  
  public void createT(){
	  String q = "CREATE TABLE "+tname+"("
			  + "nume varchar(20) NOT NULL,"
			  + "parola varchar(200) NOT NULL,"
			  + "uuid varchar(100) NOT NULL,"
			  + "lastActivity varchar(50) NOT NULL,"
			  + "lastIP varchar(50) NOT NULL,"
			  + "role int(15) NOT NULL,"
			  + "banned varchar(10) NOT NULL"
			  + ");";
	  execute(q);
  }
  
  public void execute(String query) {
    this.MySQL = new MySQLFunc(this.HOST, this.DB, this.USER, this.PASS);
    this.con = this.MySQL.open();
    try {
      this.st = this.con.createStatement();
      this.st.execute(query);
    } catch (SQLException e) {
    	System.out.println("[" + this.conName + "] Error executing statement: " + e.getMessage());
    }
    this.MySQL.close(this.con);
  }

  public ResultSet query(String query) {
    this.MySQL = new MySQLFunc(this.HOST, this.DB, this.USER, this.PASS);
    this.con = this.MySQL.open();
    ResultSet rs = null;
    try {
      this.st = this.con.createStatement();
      rs = this.st.executeQuery(query);
    } catch (SQLException e) {
    	System.out.println("[" + this.conName + "] Error executing query: " + e.getMessage());
    }
    return rs;
  }
}