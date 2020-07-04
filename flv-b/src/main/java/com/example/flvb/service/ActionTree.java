package com.example.flvb.service;
import java.io.File;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.regex.Pattern;

import com.google.gson.Gson;
import com.example.flvb.dao.usertree.*;

public class ActionTree {

	 final String dir = System.getProperty("user.dir"); //get current working directory path
	 String action_tree_json = "";
	 PrintWriter writer = null;	//for print info into txt file (important info for each ip, actions account)
	 Gson gson = new Gson(); // for convert object/hashmap to json format
	 File file = new File(dir+"/json");
	 
	 HashMap<String, C_ACTION> action_tree_map = new HashMap<String, C_ACTION>();
	 String[] info = new String[2];
	 String time;
	 String current_c_action;
	 String current_s_action;
	 String[] current_s1_paras;
	 
	 /* Regular Expression */
	 Pattern pattern[] = {Pattern.compile("\\d{4}-\\d{2}-\\d{2}"), 
			 Pattern.compile("\\d{2}:\\d{2}:\\d{2}"), 
			 Pattern.compile("uid=\\d+"),
			 Pattern.compile("\\&c=\\w+"), 
			 Pattern.compile("\\&s=\\w+"), 
			 Pattern.compile("s1.\\w+=\\w+")};
	 
}
