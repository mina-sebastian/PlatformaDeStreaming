package me.minutz.site.posts;

public class Episod {
	public String serial,posterlink;
	public int numar,sezon,an;
	
	public Episod(String serial, String posterlink, int numar,int sezon,int an) {
		this.serial = serial;
		this.numar = numar;
		this.sezon = sezon;
		this.posterlink = posterlink;
		this.an = an;
	}
	
	public String getSerial() {
		return serial;
	}
	public void setSerial(String serial) {
		this.serial = serial;
	}
	public int getNumar() {
		return numar;
	}
	public void setNumar(int numar) {
		this.numar = numar;
	}
	public int getSezon() {
		return sezon;
	}
	public void setSezon(int sezon) {
		this.sezon = sezon;
	}
	public String getPosterlink() {
		return posterlink;
	}
	public void setPosterlink(String posterlink) {
		this.posterlink = posterlink;
	}
	public int getAn() {
		return an;
	}
	public void setAn(int an) {
		this.an = an;
	}
	
}
