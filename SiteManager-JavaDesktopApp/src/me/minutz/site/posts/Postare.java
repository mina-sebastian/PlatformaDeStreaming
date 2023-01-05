package me.minutz.site.posts;

public class Postare {
	public String image,titlu,descriere,desc2,nume;
	public int id,rating,an;
	
	public Postare(String nume,String image, String titlu, String descriere, String desc2, int id, int rating, int an) {
		this.nume = nume;
		this.image = image;
		this.titlu = titlu;
		this.descriere = descriere;
		this.desc2 = desc2;
		this.id = id;
		this.rating = rating;
		this.an = an;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getTitlu() {
		return titlu;
	}

	public void setTitlu(String titlu) {
		this.titlu = titlu;
	}

	public String getDescriere() {
		return descriere;
	}

	public void setDescriere(String descriere) {
		this.descriere = descriere;
	}

	public String getDesc2() {
		return desc2;
	}

	public void setDesc2(String desc2) {
		this.desc2 = desc2;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public int getAn() {
		return an;
	}

	public void setAn(int an) {
		this.an = an;
	}

	public String getNume() {
		return nume;
	}

	public void setNume(String nume) {
		this.nume = nume;
	}
	
	
	
}
