package me.minutz.site.posts;

import java.util.List;

public class Serial {
	
	public String nume,imagine;
	public List<Episod> episoade;
	
	public Serial(String nume, List<Episod> episoade, String imagine) {
		this.nume = nume;
		this.episoade = episoade;
		this.imagine = imagine;
	}

	public String getNume() {
		return nume;
	}

	public void setNume(String nume) {
		this.nume = nume;
	}

	public List<Episod> getEpisoade() {
		return episoade;
	}

	public void addEp(Episod e){
		episoade.add(e);
	}

	public String getImagine() {
		return imagine;
	}

	public void setImagine(String imagine) {
		this.imagine = imagine;
	}

}
