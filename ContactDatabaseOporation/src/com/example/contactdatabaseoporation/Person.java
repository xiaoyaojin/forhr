package com.example.contactdatabaseoporation;

import java.io.Serializable;

public class Person implements Serializable{
	private String name;
	private String tel;
	private String ship;
	
	public Person(String name, String tel, String ship) {
		this.name = name;
		this.tel = tel;
		this.ship = ship;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the tel
	 */
	public String getTel() {
		return tel;
	}

	/**
	 * @return the ship
	 */
	public String getShip() {
		return ship;
	}

	
}
