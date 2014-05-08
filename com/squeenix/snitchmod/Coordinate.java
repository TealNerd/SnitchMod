package com.squeenix.snitchmod;

import java.util.Date;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;

public class Coordinate {
	public double x;
	public double y;
	public double z;
	public double minX;
	public double minY;
	public double minZ;
	public double maxX;
	public double maxY;
	public double maxZ;
	public boolean hostile;
	public Date cullDate;
	
	public Coordinate(double x, double y, double z, double time, boolean hostile){
		this.x = x;
		this.y = y;
		this.z = z;
		this.minX = x-11;
		this.minY = y-11;
		this.minZ = z-11;
		this.maxX = x+11;
		this.maxY = y+11;
		this.maxZ = z+11;
		this.hostile = hostile;
		changeToDate(time);
	}
	
	private void changeToDate(Double d){
		Date oldDate = new Date();
		final long hoursInMillis = 60L * 60L * 1000L;
		cullDate = new Date(oldDate.getTime() +  (long)(d * hoursInMillis)); // Adds d+ hours
	}
	
	public double hoursToDate(){
		Date oldDate = new Date();
		long diff = (cullDate.getTime() - oldDate.getTime())/(60L * 60L * 1000L);
		return diff;
	}
		
	public double getDistance(){
		EntityClientPlayerMP thePlayer = Minecraft.getMinecraft().thePlayer;
		return Math.sqrt((thePlayer.posX-x)*(thePlayer.posX-x) + (thePlayer.posZ-z)*(thePlayer.posZ-z));
	}
	
	public boolean isInBox(){
		EntityClientPlayerMP thePlayer = Minecraft.getMinecraft().thePlayer;
		return Math.abs(x-thePlayer.posX)<11&&Math.abs(y-thePlayer.posY)<11&&Math.abs(z-thePlayer.posZ)<11;
	}
}
