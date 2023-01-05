package me.minutz.site.variabili.posts;

public class Film {
	private String id,nume,posterlink;
	private int an;
	
	public Film(String id, String nume, String posterlink, int an) {
		this.id = id;
		this.nume = nume;
		this.posterlink = posterlink;
		this.an = an;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNume() {
		return nume;
	}

	public void setNume(String nume) {
		this.nume = nume;
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