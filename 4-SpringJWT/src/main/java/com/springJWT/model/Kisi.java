package com.springJWT.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "personeljwt")
public class Kisi {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Size(min = 3, max = 30)
	private String username;

	@NotBlank
	@Size(min=6, max = 120)
	private String password;

	@NotBlank
	@Email
	private String email;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "kisi_roller", joinColumns = @JoinColumn(name = "kisi_id"),//Burada default olarak id'leri alıyor.  
	inverseJoinColumns = @JoinColumn(name = "role_id" ))// ,referencedColumnName ="" belirtirsek name yazdığımız isim oluyor. 
	private Set<KisiRole> roller = new HashSet<>();

	public Kisi() {
		
	}

	 public Kisi(String username, String password, String email) {
	        this.username = username;
	        this.password = password;
	        this.email = email;
	    }





}
