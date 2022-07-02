package com.example.wt2.models;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class User {
		
	@Id
	@Column(length = 20)
	@NotNull
	@Size(min=3, max=20)
	private String username;
	
	
	@Column(length = 20)
	@NotNull
	@Size(min = 6, max = 20)
	private String password;
	/* @JsonIgnore damit permissons und rollen von serialisierung und deserialisierung ausgenommen sind*/
	@JsonIgnore
	@ElementCollection
	private Set<Permission> permission;
	
	@JsonIgnore
	@ElementCollection
	private Set<Role> role;
	
	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	public User(String username) {
		this.username = username;
	}
	
	public User() {
		
	}
	// Setters & Getters
	public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Permission> getPermissions() {
        return permission;
    }

    public void setPermissions(Set<Permission> permissions) {
        this.permission = permissions;
    }

    public Set<Role> getRoles() {
        return role;
    }

    public void setRoles(Set<Role> roles) {
        this.role = roles;
    }
    
    //Methode um Gleicheit zweier Usernames festzustellen
    @Override
    public boolean equals(Object object) {
        if(object instanceof User){
            return this.username.equals(((User) object).getUsername());
        }
        return false;
    }
	
}
