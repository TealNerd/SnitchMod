package com.squeenix.snitchmod;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Date;
import net.minecraft.client.Minecraft;
public class Config {
	public static void saveConfig(){
		File alliedSnitchConfig = new File(Minecraft.getMinecraft().mcDataDir.toString()+"/AlliedSnitches.txt");
		try{
			alliedSnitchConfig.delete();
			alliedSnitchConfig.createNewFile();
			BufferedWriter configWriter = new BufferedWriter(new FileWriter(alliedSnitchConfig));
			for(Coordinate c : SnitchField.instance.coordinateList){
				if(!c.hostile){
					configWriter.write((c.x-0.5) + ":" + (c.y-0.5) + ":" + (c.z-0.5) + ":" + c.cullDate.getTime() + "\r\n");
				}
			}
			configWriter.close();
		}
		catch(Exception e){
			e.printStackTrace();
			System.out.println("Snitch load failed!");
		}
		
		File enemySnitchConfig = new File(Minecraft.getMinecraft().mcDataDir.toString()+"/EnemySnitches.txt");
		try{
			enemySnitchConfig.delete();
			enemySnitchConfig.createNewFile();
			BufferedWriter configWriter = new BufferedWriter(new FileWriter(enemySnitchConfig));
			for(Coordinate c : SnitchField.instance.coordinateList){
				if(c.hostile){
					configWriter.write((c.x-0.5) + ":" + (c.y-0.5) + ":" + (c.z-0.5) + ":" + c.cullDate.getTime() + "\r\n");
				}
			}
			configWriter.close();
		}
		catch(Exception e){
			e.printStackTrace();
			System.out.println("Snitch load failed!");
		}
	}
	
	public static void loadConfig(){
		File alliedSnitchConfig = new File(Minecraft.getMinecraft().mcDataDir.toString()+"/AlliedSnitches.txt");
		try{
			if(!alliedSnitchConfig.exists()){
				Config.saveConfig();
			}
			BufferedReader configReader = new BufferedReader(new FileReader(alliedSnitchConfig));
			String line = configReader.readLine();
			while(line!=null){
				String[] components = line.split(":");
				if(components.length>3){
					SnitchField.instance.coordinateList.add(new Coordinate(
							Double.parseDouble(components[0])+0.5, 
							Double.parseDouble(components[1])+0.5,
							Double.parseDouble(components[2])+0.5,
							hoursToDate(Long.parseLong(components[3])),
							false));
							
				}
				line = configReader.readLine();
			}
			configReader.close();
		}
		catch(Exception e){
			e.printStackTrace();
			System.out.println("Snitch load failed!");
		}
		
		File enemySnitchConfig = new File(Minecraft.getMinecraft().mcDataDir.toString()+"/EnemySnitches.txt");
		try{
			if(!enemySnitchConfig.exists()){
				Config.saveConfig();
			}
			BufferedReader configReader = new BufferedReader(new FileReader(enemySnitchConfig));
			String line = configReader.readLine();
			while(line!=null){
				String[] components = line.split(":");
				if(components.length>3){
					SnitchField.instance.coordinateList.add(new Coordinate(
							Double.parseDouble(components[0])+0.5, 
							Double.parseDouble(components[1])+0.5,
							Double.parseDouble(components[2])+0.5,
							hoursToDate(Long.parseLong(components[3])),
							true));
				}
				line = configReader.readLine();
			}
			configReader.close();
		}
		catch(Exception e){
			e.printStackTrace();
			System.out.println("Snitch load failed!");
		}
	}
	
	public static double hoursToDate(long l){
		Date oldDate = new Date();
		double diff = (double)(l - oldDate.getTime())/(1000*60*60);
		return diff;
	}
}
