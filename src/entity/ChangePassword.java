/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.io.Serializable;

/**
 *
 * @author Admin
 */
public class ChangePassword implements Serializable {
	String username;
	String password;

	// Constructor không có tham số
	public ChangePassword() {
	}

	public ChangePassword(String username, String password) {
		this.username = username;
		this.password = password;
	}

	// Các phương thức getter và setter
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
}
