package me.minutz.site.variabili.posts;

import java.util.ArrayList;
import java.util.List;

public class Sezon {
	
	int numar;
	List<Episod> elist = new ArrayList<Episod>();
	
	public Sezon(int numar, List<Episod> elist) {
		super();
		this.numar = numar;
		this.elist = elist;
	}

	public int getNumar() {
		return numar;
	}

	public void setNumar(int numar) {
		this.numar = numar;
	}

	public List<Episod> getEplist() {
		return elist;
	}

	public void addEpisod(Episod e){
		elist.add(e);
	}

}
