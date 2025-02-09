package com.securityModel.payload.response;

import java.util.List;

public class JwtResponse {
	private final String jwt;
	private String token;
	private String type = "Bearer";
	private String refreshToken;
	private Long id;
	private String nom;
	private String prenom;
	private String email;
	private List<String> roles;

	public JwtResponse(String jwt, String accessToken, String refreshToken, Long id, String nom, String prenom, List<String> roles) {
        this.jwt = jwt;
        this.token = accessToken;
		this.refreshToken = refreshToken;
		this.id = id;
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.roles = roles;
	}


	public JwtResponse(String jwt, String token, String type, String refreshToken, Long id, String nom, String prenom, String email, List<String> roles) {
        this.jwt = jwt;
        this.token = token;
		this.type = type;
		this.refreshToken = refreshToken;
		this.id = id;
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.roles = roles;
	}

	public JwtResponse(String jwt, String token, Long id, String nom, String prenom, String email, List<String> roles) {
	this.jwt=jwt;
	this.token= token;
	this.id= id;
	this.nom= nom;
	this.prenom= prenom;
	this.email= email;
	this.roles= roles;
	}

	public String getAccessToken() {
		return token;
	}

	public void setAccessToken(String accessToken) {
		this.token = accessToken;
	}

	public String getTokenType() {
		return type;
	}

	public void setTokenType(String tokenType) {
		this.type = tokenType;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public List<String> getRoles() {
		return roles;
	}

  public String getRefreshToken() {
    return refreshToken;
  }

  public void setRefreshToken(String refreshToken) {
    this.refreshToken = refreshToken;
  }
}
