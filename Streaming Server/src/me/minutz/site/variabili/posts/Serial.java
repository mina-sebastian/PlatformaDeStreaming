package me.minutz.site.variabili.posts;

import java.util.ArrayList;
import java.util.List;

public class Serial {
	
	public String nume,imagine;
	public List<Episod> episoade;
	public List<Sezon> sezoane = new ArrayList<Sezon>();
	
	public Serial(String nume, List<Episod> episoade, String imagine) {
		this.nume = nume;
		this.episoade = episoade;
		this.imagine = imagine;
		for(Episod e:episoade){
			Sezon sz = getSezonByNr(e.getSezon());
			if(sz != null){
				sz.addEpisod(e);
			}else{
				Sezon s = new Sezon(e.getSezon(),new ArrayList<Episod>());
				sezoane.add(s);
				Sezon rs = getSezonByNr(e.getSezon());
				rs.addEpisod(e);
			}
		}
	}
	
	public Sezon getSezonByNr(int nr){
		for(Sezon s:sezoane){
			if(s.getNumar()==nr){
				return s;
			}
		}
		return null;
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

	public List<Sezon> getSezoane() {
		return sezoane;
	}

	public void addSezon(Sezon s){
		sezoane.add(s);
	}
}
