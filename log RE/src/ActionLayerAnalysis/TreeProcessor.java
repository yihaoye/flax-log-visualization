package ActionLayerAnalysis;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.gson.Gson;

public class TreeProcessor {

	 final String dir = System.getProperty("user.dir"); //get current working directory path
	 String action_layer_tree_json = "";
	 PrintWriter writer = null;	//for print info into txt file 
	 Gson gson = new Gson(); // for convert object/tree to json format
	 File file = new File(dir+"/JSONFiles");
	 
	 Tree action_layer_tree = new Tree();
	 HashMap<String, LinkedList<String>> user_action_path = new HashMap<String, LinkedList<String>>();
	 String[] info = new String[6];
	 String node_userID; 
	 String node_time;
	 String node_current_c_action;
	 String node_current_s_action;
	 String node_s1_query;
	 //String[] node_s1_paras;
	
	 
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
		 node_userID = info[0]+" "+info[2]; 
		 node_time = info[1];
		 node_current_c_action = info[3];
		 node_current_s_action = info[4];
		 node_s1_query = info[5];
		 
		 //set tree
		 System.out.println ("Tree:"+node_userID + " " + node_time);
		 set_tree(node_userID, node_time, node_current_c_action, node_current_s_action, node_s1_query);
		 
	 }
	 
	 //
	 public void write_json() throws FileNotFoundException, UnsupportedEncodingException{
		// write into json file
		 if(writer!=null) 
			   writer.close();
		 writer = new PrintWriter(file + "/" + "action_layer_tree.json", "UTF-8");
		 action_layer_tree_json = gson.toJson(action_layer_tree);
		 System.out.println ("UserTree:" + action_layer_tree_json);
		 writer.println(action_layer_tree_json);	// write info into json
		 writer.close();
		 action_layer_tree_json = "";
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
	
	//
	public void set_tree(String userID, String time, String current_c_action, String current_s_action, String s1_query){

		//
		Node new_node = new Node();
		new_node.set_node_name(current_s_action);
		new_node.set_s1_querys(s1_query);
		Node temp_node = null;
		if(user_action_path.containsKey(userID)){
			for(String node_name : user_action_path.get(userID)){//
				System.out.println (node_name);
				temp_node = action_layer_tree.bfs_traverse(temp_node, node_name);
			}
		}else{
			temp_node = action_layer_tree.bfs_traverse(temp_node, null);
		}
		action_layer_tree.add_action_node(temp_node, new_node);
		
		//built user action path
		if(user_action_path.containsKey(userID)){
			if(!user_action_path.get(userID).getLast().equals(current_s_action)){
				user_action_path.get(userID).add(current_s_action);
			}
		}else{
			user_action_path.put(userID, new LinkedList<String>());
			//user_action_path.get(userID).add(current_c_action);
			if(!current_s_action.equals(null)){
				user_action_path.get(userID).add(current_s_action);
			}
		}
		
	}
		
}
