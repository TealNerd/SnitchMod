 package com.squeenix.snitchmod;

import java.util.ConcurrentModificationException;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.client.event.RenderWorldLastEvent;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class RenderHandler {
	boolean enabled=true;
	int delay = 0;
	int i = 0;
	@SubscribeEvent
	public void eventRenderWorld(RenderWorldLastEvent e){
		
		if(delay<0&&Keyboard.isKeyDown(56)&&Keyboard.isKeyDown(57)&&Keyboard.isKeyDown(29)){
			delay = 15;
			this.enabled=!this.enabled;
		} 
		delay--;
		if(!this.enabled) return;
		i++;
		if(i>1000){
			i = 0;
			System.out.println("Saving Snitch List");
			Config.saveConfig();
		}
		PlayerInteractHandler.timer++;
		try{//Should put this is method yolo
		for(Coordinate c : SnitchField.instance.coordinateList){
			//Render Snitch Range
			if(c.getDistance()>81) continue;
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
	        GL11.glLineWidth(5F);
	        GL11.glDisable(GL11.GL_LIGHTING);
	        GL11.glDisable(GL11.GL_TEXTURE_2D);
	        GL11.glEnable(GL11.GL_BLEND);
	        GL11.glEnable(GL11.GL_LINE_SMOOTH);
	        GL11.glEnable(GL13.GL_MULTISAMPLE);
	        GL11.glAlphaFunc(GL11.GL_GREATER, 0.09F);
	        GL11.glDepthMask(false);
	        
	        GL11.glPushMatrix();
	        double px = -(RenderManager.renderPosX-c.x);
	        double py = -(RenderManager.renderPosY-c.y);
	        double pz = -(RenderManager.renderPosZ-c.z);
            AxisAlignedBB bb = AxisAlignedBB.getBoundingBox(px - 10.55d, py-10.55d, pz - 10.55d, px + 10.55d, py + 10.55d, pz + 10.55d);
            //odd choice because I just ripped this off squeenhowies esp
            int color = getColor(c.hoursToDate());
            if(c.hostile){
            	GL11.glColor4f(1F, 0.0F, 0.0F, 0.25F);	
            }
            else{
            	if(color==0){
            		GL11.glColor4d(0F, 0.56F, 1.0F, 0.25F);	
            	}else if(color==1){
            		GL11.glColor4d(0.11F, 0.91F, 0F, 0.25F);//Green
            	}else if(color==2){
            		GL11.glColor4d(0.88F, 0.83F, 0.0F, 0.25F);//Yellow
            	}else if(color==3){
            		GL11.glColor4d(0.75F, 0F, 0.91F, 0.25F);
            	}
            }
            drawCrossedOutlinedBoundingBox(bb);
            if(c.hostile){
            	GL11.glColor4f(1F, 0.0F, 0.0F, 0.1F);	
            }
            else{
            	if(color==0){
            		GL11.glColor4d(0F, 0.56F, 1.0F, 0.1F);//Blue
            	}else if(color==1){
            		GL11.glColor4d(0.11F, 0.91F, 0F, 0.1F);//Green
            	}else if(color==2){
            		GL11.glColor4d(0.88F, 0.83F, 0.0F, 0.1F);//Yellow
            	}else if(color==3){
            		GL11.glColor4d(0.75F, 0F, 0.91F, 0.1F);//Purple
            	}
            }
            
            drawBoundingBoxQuads(bb);
	        GL11.glPopMatrix();
	        
	        GL11.glDepthMask(true);
	        GL11.glDisable(GL11.GL_LINE_SMOOTH);
	        GL11.glDisable(GL11.GL_BLEND);
	        GL11.glEnable(GL11.GL_TEXTURE_2D);
	        GL11.glEnable(GL11.GL_DEPTH_TEST);
	        GL11.glDisable(GL13.GL_MULTISAMPLE);
	        GL11.glEnable(GL11.GL_LIGHTING);
	        
	        //Render snitch block
	        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
	        GL11.glLineWidth(5F);
	        GL11.glDisable(GL11.GL_LIGHTING);
	        GL11.glDisable(GL11.GL_DEPTH_TEST);
	        GL11.glDisable(GL11.GL_TEXTURE_2D);
	        GL11.glEnable(GL11.GL_BLEND);
	        GL11.glEnable(GL11.GL_LINE_SMOOTH);
	        GL11.glEnable(GL13.GL_MULTISAMPLE);
	        GL11.glAlphaFunc(GL11.GL_GREATER, 0.09F);
	        GL11.glDepthMask(false);
	        
	        GL11.glPushMatrix();
	        px = -(RenderManager.renderPosX-c.x);
	        py = -(RenderManager.renderPosY-c.y);
	        pz = -(RenderManager.renderPosZ-c.z);
            bb = AxisAlignedBB.getBoundingBox(px - 0.5d, py-0.5d, pz - 0.5d, px + 0.5d, py + 0.5d, pz + 0.5d);
            bb.expand(0.05, 0.05, 0.05);
            //odd choice because I just ripped this off my hacked client's esp
            if(c.hostile){
            	GL11.glColor4f(1F, 0.0F, 0.0F, 0.25F);	
            }
            else{
            	if(color==0){
            		GL11.glColor4d(0F, 0.56F, 1.0F, 0.25F);	
            	}else if(color==1){
            		GL11.glColor4d(0.11F, 0.91F, 0F, 0.25F);
            	}else if(color==2){
            		GL11.glColor4d(0.88F, 0.83F, 0.0F, 0.25F);//Yellow
            	}else if(color==3){
            		GL11.glColor4d(0.75F, 0F, 0.91F, 0.25F);
            	}
            }
            drawCrossedOutlinedBoundingBox(bb);
            if(c.hostile){
            	GL11.glColor4f(1F, 0.0F, 0.0F, 0.1F);	
            }
            else{
            	if(color==0){
            		GL11.glColor4d(0F, 0.56F, 1.0F, 0.1F);	
            	}else if(color==1){
            		GL11.glColor4d(0.11F, 0.91F, 0F, 0.1F);
            	}else if(color==2){
            		GL11.glColor4d(0.88F, 0.83F, 0.0F, 0.1F);//Yellow
            	}else if(color==3){
            		GL11.glColor4d(0.75F, 0F, 0.91F, 0.1F);
            	}
            }
            drawBoundingBoxQuads(bb);
	        GL11.glPopMatrix();
	        
	        GL11.glDepthMask(true);
	        GL11.glDisable(GL11.GL_LINE_SMOOTH);
	        GL11.glDisable(GL11.GL_BLEND);
	        GL11.glEnable(GL11.GL_TEXTURE_2D);
	        GL11.glEnable(GL11.GL_DEPTH_TEST);
	        GL11.glDisable(GL13.GL_MULTISAMPLE);
	        GL11.glEnable(GL11.GL_LIGHTING);
		}
		
		for(Coordinate c : SnitchField.instance.tempList){
			//Render Snitch Range
			if(c.getDistance()>81) continue;
	        
	        //Render snitch block
	        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
	        GL11.glLineWidth(5F);
	        GL11.glDisable(GL11.GL_LIGHTING);
	        GL11.glDisable(GL11.GL_DEPTH_TEST);
	        GL11.glDisable(GL11.GL_TEXTURE_2D);
	        GL11.glEnable(GL11.GL_BLEND);
	        GL11.glEnable(GL11.GL_LINE_SMOOTH);
	        GL11.glEnable(GL13.GL_MULTISAMPLE);
	        GL11.glAlphaFunc(GL11.GL_GREATER, 0.09F);
	        GL11.glDepthMask(false);
	        
	        GL11.glPushMatrix();
	        double px = -(RenderManager.renderPosX-c.x);
	        double py = -(RenderManager.renderPosY-c.y);
	        double pz = -(RenderManager.renderPosZ-c.z);
	        AxisAlignedBB bb = AxisAlignedBB.getBoundingBox(px - 0.5d, py-0.5d, pz - 0.5d, px + 0.5d, py + 0.5d, pz + 0.5d);
            bb.expand(0.05, 0.05, 0.05);
            //odd choice because I just ripped this off my hacked client's esp
        	GL11.glColor4f(1F, 1F, 1F, 0.25F);	
            drawCrossedOutlinedBoundingBox(bb);
        	GL11.glColor4f(1F, 1F, 1F, 0.1F);	
            drawBoundingBoxQuads(bb);
	        GL11.glPopMatrix();
	        
	        GL11.glDepthMask(true);
	        GL11.glDisable(GL11.GL_LINE_SMOOTH);
	        GL11.glDisable(GL11.GL_BLEND);
	        GL11.glEnable(GL11.GL_TEXTURE_2D);
	        GL11.glEnable(GL11.GL_DEPTH_TEST);
	        GL11.glDisable(GL13.GL_MULTISAMPLE);
	        GL11.glEnable(GL11.GL_LIGHTING);
		}
		
		
		}catch(ConcurrentModificationException f){
			
		}
	}
	
	public static void drawCrossedOutlinedBoundingBox(AxisAlignedBB bb){
        Tessellator t = Tessellator.instance;
        t.startDrawing(3);
        t.addVertex(bb.minX, bb.minY, bb.minZ);
        t.addVertex(bb.maxX, bb.minY, bb.minZ);
        t.addVertex(bb.maxX, bb.minY, bb.maxZ);
        t.addVertex(bb.minX, bb.minY, bb.maxZ);
        t.addVertex(bb.minX, bb.minY, bb.minZ);
        t.draw();
        t.startDrawing(3);
        t.addVertex(bb.minX, bb.maxY, bb.minZ);
        t.addVertex(bb.maxX, bb.maxY, bb.minZ);
        t.addVertex(bb.maxX, bb.maxY, bb.maxZ);
        t.addVertex(bb.minX, bb.maxY, bb.maxZ);
        t.addVertex(bb.minX, bb.maxY, bb.minZ);
        t.draw();
        t.startDrawing(3);
        t.addVertex(bb.minX, bb.maxY, bb.minZ);
        t.addVertex(bb.minX, bb.minY, bb.minZ);
        t.draw();
        t.startDrawing(3);
        t.addVertex(bb.minX, bb.maxY, bb.maxZ);
        t.addVertex(bb.minX, bb.minY, bb.maxZ);
        t.draw();
        t.startDrawing(3);
        t.addVertex(bb.maxX, bb.maxY, bb.minZ);
        t.addVertex(bb.maxX, bb.minY, bb.minZ);
        t.draw();
        t.startDrawing(3);
        t.addVertex(bb.maxX, bb.maxY, bb.maxZ);
        t.addVertex(bb.maxX, bb.minY, bb.maxZ);
        t.draw();
    }
	public static void drawBoundingBoxQuads(AxisAlignedBB bb){
        Tessellator t = Tessellator.instance;
        t.startDrawingQuads();
        t.addVertex(bb.minX, bb.minY, bb.minZ);
        t.addVertex(bb.minX, bb.maxY, bb.minZ);
        t.addVertex(bb.maxX, bb.minY, bb.minZ);
        t.addVertex(bb.maxX, bb.maxY, bb.minZ);
        t.addVertex(bb.maxX, bb.minY, bb.maxZ);
        t.addVertex(bb.maxX, bb.maxY, bb.maxZ);
        t.addVertex(bb.minX, bb.minY, bb.maxZ);
        t.addVertex(bb.minX, bb.maxY, bb.maxZ);
        t.draw();
        t.startDrawingQuads();
        t.addVertex(bb.maxX, bb.maxY, bb.minZ);
        t.addVertex(bb.maxX, bb.minY, bb.minZ);
        t.addVertex(bb.minX, bb.maxY, bb.minZ);
        t.addVertex(bb.minX, bb.minY, bb.minZ);
        t.addVertex(bb.minX, bb.maxY, bb.maxZ);
        t.addVertex(bb.minX, bb.minY, bb.maxZ);
        t.addVertex(bb.maxX, bb.maxY, bb.maxZ);
        t.addVertex(bb.maxX, bb.minY, bb.maxZ);
        t.draw();
        t.startDrawingQuads();
        t.addVertex(bb.minX, bb.maxY, bb.minZ);
        t.addVertex(bb.maxX, bb.maxY, bb.minZ);
        t.addVertex(bb.maxX, bb.maxY, bb.maxZ);
        t.addVertex(bb.minX, bb.maxY, bb.maxZ);
        t.addVertex(bb.minX, bb.maxY, bb.minZ);
        t.addVertex(bb.minX, bb.maxY, bb.maxZ);
        t.addVertex(bb.maxX, bb.maxY, bb.maxZ);
        t.addVertex(bb.maxX, bb.maxY, bb.minZ);
        t.draw();
        t.startDrawingQuads();
        t.addVertex(bb.minX, bb.minY, bb.minZ);
        t.addVertex(bb.maxX, bb.minY, bb.minZ);
        t.addVertex(bb.maxX, bb.minY, bb.maxZ);
        t.addVertex(bb.minX, bb.minY, bb.maxZ);
        t.addVertex(bb.minX, bb.minY, bb.minZ);
        t.addVertex(bb.minX, bb.minY, bb.maxZ);
        t.addVertex(bb.maxX, bb.minY, bb.maxZ);
        t.addVertex(bb.maxX, bb.minY, bb.minZ);
        t.draw();
        t.startDrawingQuads();
        t.addVertex(bb.minX, bb.minY, bb.minZ);
        t.addVertex(bb.minX, bb.maxY, bb.minZ);
        t.addVertex(bb.minX, bb.minY, bb.maxZ);
        t.addVertex(bb.minX, bb.maxY, bb.maxZ);
        t.addVertex(bb.maxX, bb.minY, bb.maxZ);
        t.addVertex(bb.maxX, bb.maxY, bb.maxZ);
        t.addVertex(bb.maxX, bb.minY, bb.minZ);
        t.addVertex(bb.maxX, bb.maxY, bb.minZ);
        t.draw();
        t.startDrawingQuads();
        t.addVertex(bb.minX, bb.maxY, bb.maxZ);
        t.addVertex(bb.minX, bb.minY, bb.maxZ);
        t.addVertex(bb.minX, bb.maxY, bb.minZ);
        t.addVertex(bb.minX, bb.minY, bb.minZ);
        t.addVertex(bb.maxX, bb.maxY, bb.minZ);
        t.addVertex(bb.maxX, bb.minY, bb.minZ);
        t.addVertex(bb.maxX, bb.maxY, bb.maxZ);
        t.addVertex(bb.maxX, bb.minY, bb.maxZ);
        t.draw();
    }
	
	public int getColor(double hours){
		if(hours<0) return 3;//Purple
		if(hours<24) return 2;//Yellow
		if(hours<168) return 1;//Green
		else return 0;//Blue
	}
}
