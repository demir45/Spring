package model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="kisiler")
public class Kisi {

	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private String ad;
	private String soyad;
	private int yas;
	
public Kisi() {
		
	}

public Kisi(String ad, String soyad, int yas) {
	this.ad = ad;
	this.soyad = soyad;
	this.yas = yas;
}

//public Integer getId() {
//	return id;
//}
//
//public void setId(Integer id) {
//	this.id = id;
//}
//
//public String getAd() {
//	return ad;
//}
//
//public void setAd(String ad) {
//	this.ad = ad;
//}
//
//public String getSoyad() {
//	return soyad;
//}
//
//public void setSoyad(String soyad) {
//	this.soyad = soyad;
//}
//
//public int getYas() {
//	return yas;
//}
//
//public void setYas(int yas) {
//	this.yas = yas;
//}
	
	
}
