package me.minutz.site.variabili.useri;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import me.minutz.site.Role;

public class User {
	public String nume,parola,uuid,lastActivity,lastIp,lastViz;
	public Role role;
	public boolean banned;
	private int i = 0;
	private Date toBl = null;
	public User(String nume, String parola, String uuid, String lastActivity, String lastIp, Role role,
			boolean banned, String lastViz) {
		this.nume = nume;
		this.parola = parola;
		this.uuid = uuid;
		this.lastActivity = lastActivity;
		this.lastIp = lastIp;
		this.role = role;
		this.banned = banned;
		this.lastViz = lastViz;
	}
	public String getNume() {
		return nume;
	}
	public void setNume(String nume) {
		this.nume = nume;
	}
	public String getParola() {
		return parola;
	}
	public void setParola(String parola) {
		this.parola = parola;
	}
	public String getUUID() {
		return uuid;
	}
	public void setUUID(String uuid) {
		this.uuid = uuid;
	}
	public String getLastActivity() {
		return lastActivity;
	}
	public Date getLastDate() {
		DateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		Date date=null;;
		try {
			date = format.parse(lastActivity);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	public void setLastActivity(String lastActivity) {
		this.lastActivity = lastActivity;
	}
	public String getLastIp() {
		return lastIp;
	}
	public void setLastIp(String lastIp) {
		this.lastIp = lastIp;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public boolean isBanned() {
		return banned;
	}
	public void setBanned(boolean banned) {
		this.banned = banned;
	}
	public String getLastViz() {
		return lastViz;
	}
	public void setLastViz(String lastViz) {
		this.lastViz = lastViz;
	}
	public int getI() {
		return i;
	}
	public void setI(int i) {
		this.i = i;
	}
	public Date getToBl() {
		return toBl;
	}
	public void setToBl(Date toBl) {
		this.toBl = toBl;
	}
	
}
