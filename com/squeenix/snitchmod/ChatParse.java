package com.squeenix.snitchmod;

import java.util.ConcurrentModificationException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.client.event.ClientChatReceivedEvent;

public class ChatParse {
	@SubscribeEvent
	public void onChat(ClientChatReceivedEvent e){
		if(e.message!=null&&(e.message.getFormattedText().contains("world"))){
			for(String s : e.message.getFormattedText().split("world")){
				parseMessage(s, true, false);
			}
		}
		else if(e.message!=null&&(e.message.getFormattedText().contains("snitch at"))){
			parseMessage(e.message.getFormattedText(), false, false);
		}
		else if(e.message!=null&&(e.message.getFormattedText().contains("Used")||e.message.getFormattedText().contains("Block Break")||e.message.getFormattedText().contains("Block Place"))){
			parseMessage(e.message.getFormattedText(), true, true);
		}
	}
	public void parseMessage(String msg, boolean hasTime, boolean temp){
		  Pattern pattern = Pattern.compile("[\\[][\\d|-][\\s|\\d|-]*[\\]]");
	      Matcher matcher = pattern.matcher(msg);
	      if (matcher.find()){
		      String[] s = matcher.group(0).replaceAll("\\[|\\]", "").split(" ");
		      if (s.length == 3){
		    	  int px = Integer.parseInt(s[0]);
		    	  int py = Integer.parseInt(s[1]);
		    	  int pz = Integer.parseInt(s[2]);
		    	  try{
		    		  if(!temp){//lol
		    			  for(int y = 0; y < SnitchField.instance.coordinateList.size(); y++){
								Coordinate c = SnitchField.instance.coordinateList.get(y);
								if(c.x==px+0.5&&c.y==py+0.5&&c.z==pz+0.5){
										SnitchField.instance.coordinateList.remove(y);
								}
							}
		    		  }else{
						for(int y = 0; y < SnitchField.instance.tempList.size(); y++){
							Coordinate c = SnitchField.instance.tempList.get(y);
							if(c.x==px+0.5&&c.y==py+0.5&&c.z==pz+0.5){
									SnitchField.instance.tempList.remove(y);
							}
						}
		    		}
		    	  	double time = 9999999;
		    	  	if(hasTime){
		    	  		Pattern pattern2 = Pattern.compile("\\b\\d{1,3}\\.\\d{1,3}");
		    	  		Matcher matcher2 = pattern2.matcher(msg);
		    	  		if(matcher2.find()){
		    	  			time = Double.parseDouble(matcher2.group(0));
		    	  		}
		    	  	}
		    	  	if(!temp){
		    	  		SnitchField.instance.coordinateList.add(new Coordinate(px+0.5, py+0.5, pz+0.5, time, false));
		    	  	}else{
		    	  		SnitchField.instance.tempList.add(new Coordinate(px+0.5, py+0.5, pz+0.5, time, false));
		    	  	}
		    	  	}catch(Exception f){
						return;
					}
		      	}
		  }
	}
}
