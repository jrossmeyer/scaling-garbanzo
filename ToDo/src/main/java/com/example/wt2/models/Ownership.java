package com.example.wt2.models;

import java.util.Set;

public class Ownership {

	private Set<String> names;
	
	public Ownership() {
		
	}
	
	public Set<String> getNames() {
		return names;
	}
	//Setters & Getters
	public void setNames(Set<String> names) {
		this.names = names;
	}

	public Ownership(Set<String> name) {
		this.names = name;
	}
	
}
