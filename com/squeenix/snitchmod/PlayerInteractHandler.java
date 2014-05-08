package com.squeenix.snitchmod;

import java.util.ConcurrentModificationException;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

public class PlayerInteractHandler {
	public static int timer = 0;
	@SubscribeEvent
	public void eventPlayerInteract(PlayerInteractEvent e){
		//Add Friendly
		if(Keyboard.isKeyDown(56)){
			//Add Hostile
			if(e.action!=null && e.action==PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK){
				if(Block.getIdFromBlock(Minecraft.getMinecraft().theWorld.getBlock(e.x, e.y, e.z))==84||Block.getIdFromBlock(Minecraft.getMinecraft().theWorld.getBlock(e.x, e.y, e.z))==25){
					try{
						for(Coordinate c : SnitchField.instance.coordinateList){
							if(c.x==e.x+0.5&&c.y==e.y+0.5&&c.z==e.z+0.5) return;
						}
					}catch(ConcurrentModificationException f){
						
					}
					if(timer>6){
						SnitchField.instance.coordinateList.add(new Coordinate(e.x+0.5, e.y+0.5, e.z+0.5, 9999999, true));
						Config.saveConfig();
					}
					return;
				}
			}
		}
		
		if(e.action!=null && e.action==PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK){
			if(Block.getIdFromBlock(Minecraft.getMinecraft().theWorld.getBlock(e.x, e.y, e.z))==84||Block.getIdFromBlock(Minecraft.getMinecraft().theWorld.getBlock(e.x, e.y, e.z))==25){
			try{
				for(int i = 0; i < SnitchField.instance.coordinateList.size(); i++){
					Coordinate c = SnitchField.instance.coordinateList.get(i);
					if(c.x==e.x+0.5&&c.y==e.y+0.5&&c.z==e.z+0.5){
						if(timer>6){
							timer = 0;
							SnitchField.instance.coordinateList.remove(i);
							Config.saveConfig();
						}
						return;
					}
				}
			}catch(ConcurrentModificationException f){
				
			}
			if(timer>6){
				timer = 0;
				SnitchField.instance.coordinateList.add(new Coordinate(e.x+0.5, e.y+0.5, e.z+0.5, 9999999, false));
				Config.saveConfig();
			}
			return;
			}
		}
	}
}