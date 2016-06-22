package com.yihaoye.actiontracetree;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import com.google.gson.Gson;

public class ActionTraceTreeProcessor {

	 final String dir = System.getProperty("user.dir"); //get current working directory path
	 String action_layer_tree_json = "";
	 String users_actions_path_json = "";
	 PrintWriter writer = null;	//for print info into txt file 
	 Gson gson = new Gson(); // for convert object/tree to json format
	 File file = new File(dir+"/JSONFiles");
	 
	 Tree action_layer_tree = new Tree();
	 UsersActionsPath users_actions_path = new UsersActionsPath();
	 
	 
	 //
	 public void process(String[] str_line_abstract_info){		 
		 //set tree
		 //str_line_abstract_info (String date, time, uid, current_c_action, current_s_action, s1_query;)
		 System.out.println ("Tree:"+ str_line_abstract_info[0] + " " + str_line_abstract_info[1]);
		 setTree(str_line_abstract_info[0] + " " + str_line_abstract_info[2], str_line_abstract_info[1], str_line_abstract_info[3], str_line_abstract_info[4], str_line_abstract_info[5]);		 
	 }
	 
	 //
	 public void writeJSON() throws FileNotFoundException, UnsupportedEncodingException{
		 file.mkdir(); // create a new directory to store json
		 //check if writer is closed
		 if(writer!=null) 
			   writer.close();
		 
		 //action_layer_tree_json write into json file
		 writer = new PrintWriter(file + "/" + "action_layer_tree.json", "UTF-8");
		 action_layer_tree_json = gson.toJson(action_layer_tree.root);
		 System.out.println ("UserTree:" + action_layer_tree_json);
		 writer.println(action_layer_tree_json);	// write info into json
		 writer.close();
		 
		 //users_actions_path_json write into json file
		 writer = new PrintWriter(file + "/" + "users_actions_path.json", "UTF-8");
		 users_actions_path_json = gson.toJson(users_actions_path.the_path);
		 System.out.println ("UserTree:" + users_actions_path_json);
		 writer.println(users_actions_path_json);	// write info into json
		 writer.close();
		 
		 //clear the String
		 action_layer_tree_json = "";
		 users_actions_path_json = "";
	 }
	 
	
	//
	public void setTree(String userID, String time, String current_c_action, String current_s_action, String s1_query){

		//find current user's last corresponding node within action_layer_tree, if not record the user before then return tree root node. (use users_actions_path to find action node layer by layer -- bfsTraverse)
		Node temp_node = null;
		int level_index = 0;
		if(users_actions_path.containsKey(userID)){
			for(String node_name : users_actions_path.get(userID).keySet()){//
				System.out.println (node_name);
				temp_node = action_layer_tree.bfsTraverse(temp_node, node_name);
			}
			level_index = users_actions_path.get(userID).keySet().size();//get recorded last lvl_action's level
		}else{
			temp_node = action_layer_tree.bfsTraverse(temp_node, null);
			//last recorded user's lvl_action's level_index=0, level_index no need to be changed here
		}
		
		//set action=lvl_action for identified within ActionTraceTree //有空再检查！貌似没问题
		if(temp_node.name.equals("lvl"+ level_index +" "+current_s_action)){//if current level+action equals recorded last lvl_action
			current_s_action = "lvl"+ level_index +" "+current_s_action;		
		}else{//if current level+action not equals recorded last lvl_action, increase level, and set current_action = new_level+action
			level_index++;
			current_s_action = "lvl"+ level_index +" "+current_s_action;
		}
		//
				
		//built users_actions_path (for JSON)
		users_actions_path.setPath(userID, current_s_action, s1_query);
		
		//set it as unique_s1_query with userID (used for identified within action_layer_tree from other query which query same s1.query but by different user)
		s1_query = userID + " " + s1_query; 
		
		//create new temp node to store current action data, and then combine into the action_layer_tree's corresponding node
		Node new_node = new Node();
		new_node.setNodeName(current_s_action);
		new_node.setS1QuerysInfo(s1_query, userID, current_s_action);
		action_layer_tree.addActionNode(temp_node, new_node);
	}
		
}