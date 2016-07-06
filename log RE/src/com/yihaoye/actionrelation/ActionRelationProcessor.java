package com.yihaoye.actionrelation;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.gson.Gson;


public class ActionRelationProcessor {
	final String dir = System.getProperty("user.dir"); //get current working directory path
	 String action_nodes_json = "";
	 String action_links_json = "";
	 PrintWriter writer = null;	//for print info into txt file 
	 Gson gson = new Gson(); // for convert object/tree to json format
	 File file = new File(dir+"/JSONFiles");
	 
	 ArrayList<ActionNode> nodes = new ArrayList<ActionNode>();
	 HashMap<String, Integer> nodes_map = new HashMap<String, Integer>();
	 ArrayList<ActionLink> links = new ArrayList<ActionLink>();
	 HashMap<String, HashMap<String, Integer>> links_map = new HashMap<String, HashMap<String, Integer>>();
	 String[] info = new String[6];
	 String re_userID; 
	 String re_time;
	 String re_current_c_action;
	 String re_current_s_action;
	 String re_s1_query;

	 String re_pre_s_action = null;
	 
	 /* Regular Expression */
	 Pattern pattern[] = {Pattern.compile("\\d{4}-\\d{2}-\\d{2}"), 
			 Pattern.compile("\\d{2}:\\d{2}:\\d{2}"), 
			 Pattern.compile("uid=\\d+"),
			 Pattern.compile("\\&c=\\w+"), 
			 Pattern.compile("\\&s=\\w+"), 
			 Pattern.compile("s1.query=\\w+")};
	 
	 //
	 public void process(String strLine){
		 file.mkdir(); // create a new directory to store json
		 
		 //
		 abstract_info(strLine);
		 re_userID = info[0]+" "+info[2]; 
		 re_time = info[1];
		 re_current_c_action = info[3];
		 re_current_s_action = info[4];
		 re_s1_query = info[5];
		 
		 //set tree
		 System.out.println ("Relation:"+re_userID + " " + re_time);
		 set_map(re_current_s_action, re_pre_s_action);
		 re_pre_s_action = re_current_s_action;
	 }
	 
	 //
	 public void writeJSON() throws FileNotFoundException, UnsupportedEncodingException{
		 //
		 set_relation();
		 
		 // write into json file
		 if(writer!=null) 
			   writer.close();
		 writer = new PrintWriter(file + "/" + "action_relations.json", "UTF-8");
		 action_nodes_json = gson.toJson(nodes);
		 action_links_json = gson.toJson(links);
		 writer.println("{\"nodes\":" + action_nodes_json + "," + "\"links\":" + action_links_json + "}");	// write info into json
		 writer.close();
		 action_nodes_json = "";
		 action_links_json = "";
	 }
	 
	 //
	public void abstract_info(String strLine){
		Matcher matcher[] = new Matcher[pattern.length];
		for(int i=0; i<pattern.length; i++){
			matcher[i] = pattern[i].matcher(strLine);
			if(matcher[i].find()){
				if(i<=(pattern.length-1)){
					info[i] = matcher[i].group();
					strLine = strLine.replace(matcher[i].group(), ""); // remove match part
				}
			}
		}
	}
	
	public void set_map(String cur_action, String pre_action){
		if(nodes_map.containsKey(cur_action)){
			int temp = nodes_map.get(cur_action);
			temp++;
			nodes_map.put(cur_action, temp);
		}else{
			nodes_map.put(cur_action, 1);
		}
		
		if(pre_action!=null){
			System.out.println("pre_action");
			if(links_map.containsKey(pre_action)){
				if(links_map.get(pre_action).containsKey(cur_action)){
					int temp = links_map.get(pre_action).get(cur_action);
					temp++;
					links_map.get(pre_action).put(cur_action, temp);
				}else{
					links_map.get(pre_action).put(cur_action, 1);
				}
			}else{
				links_map.put(pre_action, new HashMap<String, Integer>());
				links_map.get(pre_action).put(cur_action, 1);
			}
		}
		
		//pre_action = cur_action;
	}
	
	public void set_relation(){
		for(Map.Entry<String, Integer> entry : nodes_map.entrySet()){
		    nodes.add(new ActionNode(entry.getKey(), entry.getValue()));
		}
		
		for(Map.Entry<String, HashMap<String, Integer>> entry1 : links_map.entrySet()){
			HashMap<String, Integer> temp_map = links_map.get(entry1.getKey());
			for(Map.Entry<String, Integer> entry2 : temp_map.entrySet()){
				int source_index = -1; 
				int target_index = -1;
				for(ActionNode temp_node : nodes){
					if(temp_node.name.equals(entry1.getKey())){
						source_index = nodes.indexOf(temp_node);
					}
				}
				for(ActionNode temp_node : nodes){
					if(temp_node.name.equals(entry2.getKey())){
						target_index = nodes.indexOf(temp_node);
					}
				}
				links.add(new ActionLink(source_index, target_index, entry2.getValue()));
			}
		}
	}
	 
}
