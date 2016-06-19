package com.yihaoye.usertree;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.regex.*;

import com.google.gson.Gson;

public class UserTree {
	
	 final String dir = System.getProperty("user.dir"); //get current working directory path
	 String user_tree_json = "";
	 PrintWriter writer = null;	//for print info into txt file (important info for each ip, actions account)
	 Gson gson = new Gson(); // for convert object/hashmap to json format
	 File file = new File(dir+"/JSONFiles");
	
	 HashMap<String, USER> user_tree_map = new HashMap<String, USER>();
	 String[] info = new String[6];
	 String USER_userID; //add "USER" for making variable name unique
	 String USER_time;
	 String USER_current_c_action;
	 String USER_current_s_action;
	 String[] USER_s1_paras;
	
	 
	 /* Regular Expression */
	 Pattern pattern[] = {Pattern.compile("\\d{4}-\\d{2}-\\d{2}"), 
			 Pattern.compile("\\d{2}:\\d{2}:\\d{2}"), 
			 Pattern.compile("uid=\\d+"),
			 Pattern.compile("\\&c=\\w+"), 
			 Pattern.compile("\\&s=\\w+"), 
			 Pattern.compile("s1.\\w+=\\w+")};
	 
	 //
	 public void process(String strLine){
		 file.mkdir(); // create a new directory to store json
		 
		 //
		 abstract_info(strLine);
		 USER_userID = info[0]+" "+info[2]; 
		 USER_time = info[1];
		 USER_current_c_action = info[3];
		 USER_current_s_action = info[4];
		 USER_s1_paras = info[5].split(",");
		 
		 
		 //set user_tree_map
		 set_user_tree_map(USER_userID, USER_time, USER_current_c_action, USER_current_s_action, USER_s1_paras);
		 System.out.println ("UserTree:"+USER_userID);
	 }
	 
	 //
	 public void write_json() throws FileNotFoundException, UnsupportedEncodingException{
		// write into json file
		 if(writer!=null) 
			   writer.close();
		 writer = new PrintWriter(file + "/" + "user_tree.json", "UTF-8");
		 user_tree_json = gson.toJson(user_tree_map);
		 System.out.println ("UserTree:"+user_tree_json);
		 writer.println(user_tree_json);	// write info into json
		 writer.close();
		 user_tree_json = "";
	 }
	 
	//
	public void abstract_info(String strLine){
		Matcher matcher[] = new Matcher[pattern.length];
		for(int i=0; i<pattern.length; i++){
			matcher[i] = pattern[i].matcher(strLine);
			if(matcher[i].find()){
				if(i<(pattern.length-1)){
					info[i] = matcher[i].group();
					strLine = strLine.replace(matcher[i].group(), ""); // remove match part
				}

				if(i==(pattern.length-1)){ //last RegExp repeat executed (s1. property)
					info[i] += matcher[i].group() + ",";
					strLine = strLine.replace(matcher[i].group(), ""); // remove match part
					matcher[i] = pattern[i].matcher(strLine);
					if(matcher[i].find()){
						i--;
					}
				}
			}
		}
	}
	
	
	//
	public void set_user_tree_map(String userID, String time, String current_c_action, String current_s_action, String[] s1_paras){
		if(user_tree_map.containsKey(userID)){
			USER temp_user = user_tree_map.get(userID);
			temp_user.actions_account_increased();
			user_tree_map.get(userID).set_endTime(time);
		 }else{
			 user_tree_map.put(userID, new USER());
			 user_tree_map.get(userID).set_startTime(time);
		 }
		
		USER temp_user = user_tree_map.get(userID);
				
		temp_user.calculate_last_action_stayTime(time);
		temp_user.set_c_s_actions_map(current_c_action, current_s_action, time, s1_paras);
		temp_user.update_last_action(current_c_action, current_s_action);
	 }
}
