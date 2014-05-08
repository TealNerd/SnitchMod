package com.squeenix.snitchmod;

import java.util.ArrayList;
import java.util.Iterator;

import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler; // used in 1.6.2
//import cpw.mods.fml.common.Mod.PreInit;    // used in 1.5.2
//import cpw.mods.fml.common.Mod.Init;       // used in 1.5.2
//import cpw.mods.fml.common.Mod.PostInit;   // used in 1.5.2
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid="fieldrenderer", name="SnitchFieldRenderer", version="1.0.0")

public class SnitchField {
	
	public ArrayList<Coordinate> coordinateList;
	public ArrayList<Coordinate> tempList;
	
    // The instance of your mod that Forge uses.
    @Instance(value = "SnitchField")
    public static SnitchField instance;
    
    @SidedProxy(clientSide="com.squeenix.snitchmod.CommonProxy")
    public static CommonProxy proxy;
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
    	SnitchField.instance = this;
    	coordinateList = new ArrayList<Coordinate>();
    	tempList = new ArrayList<Coordinate>();
    	MinecraftForge.EVENT_BUS.register(new PlayerInteractHandler());
    	MinecraftForge.EVENT_BUS.register(new RenderHandler());
    	MinecraftForge.EVENT_BUS.register(new ChatParse());
    }
    
    @EventHandler
    public void load(FMLInitializationEvent event) {
            proxy.registerListeners();
    }
    
    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
    }
    
}