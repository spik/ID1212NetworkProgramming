package server.model;

import java.io.File;
import java.io.Serializable;

public class ExtendedFile implements Serializable{
	
	private String name, owner, permissions, fileNameForDatabase;
	private boolean notification;
	private double size;
	
	public ExtendedFile(byte[] byteArray, String name, String fileNameForDatabase, String owner, String permissions, boolean notification) {
		this.name = name;
		this.fileNameForDatabase = fileNameForDatabase;
		this.size = byteArray.length;
		this.owner = owner;
		this.permissions = permissions;
		this.notification = notification;
	}
	
	public void setOwner(String owner) {
		this.name = owner;
	}
	public void setPermissions(String permissions) {
		this.name = permissions;
	}
	public void setNotification(boolean notification) {
		this.notification = notification;
	}
	
	public void setSize(double size) {
		this.size = size;
	}
	
	public String getName(){
		return name;
	}
	
	public String getFileNameForDatabase(){
		return fileNameForDatabase;
	}
	
	public String getOwner(){
		return owner;
	}
	public String getPermissions(){
		return permissions;
	}
	public boolean getNotification(){
		return notification;
	}
	public double getSize() {
		return size;
	}
	
	public String toString() {
		return "File name: " + name + "\nSize: + " + size + "\nOwner: " + owner + "\nPermissions: " + permissions;
	}
}
