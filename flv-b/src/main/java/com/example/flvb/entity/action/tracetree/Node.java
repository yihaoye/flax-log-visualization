package com.example.flvb.entity.action.tracetree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Node {

	public String name; //lvl_s_action name
    //private Node parent;
    public List<Node> children;
    public HashMap<String, S1_Query_Info> s1_querys; //<user_id + s1.query_name, S1_Query_Info>
    public int access_account;
    public float access_percentage;
    
    public Node(){
    	name = null;
    	children = new ArrayList<Node>();
    	s1_querys = new HashMap<String, S1_Query_Info>();
    	access_account = 1;
    }
    
    public void setNodeName(String lvl_action){
    	name = lvl_action;
    }
    
    public void accessAccountIncrease(){
    	access_account++;
    }
    
    public void setAccessPercentage(float given_percentage){
    	access_percentage = given_percentage;
    }
    
    public void setS1QuerysInfo(String unique_query_name, String user_id, String lvl_action){
    	if(s1_querys.containsKey(unique_query_name)){
    		s1_querys.get(unique_query_name).accountIncrease();
    	}else{
    		s1_querys.put(unique_query_name, new S1_Query_Info());
    		s1_querys.get(unique_query_name).setPathRelatedParams(user_id, lvl_action);
    	}
    }
    
    public void combineNodes(Node new_node){ //these two nodes must have the same "name"
    	
    	//add para access_account
    	this.access_account = this.access_account + new_node.access_account;  
    	
		//combine para s1_querys
    	for(String key : new_node.s1_querys.keySet()){
    		if(this.s1_querys.containsKey(key)){
    			this.s1_querys.get(key).account += new_node.s1_querys.get(key).account;
    		}else{
    			this.s1_querys.put(key, new_node.s1_querys.get(key));
    		}
    	}
    	
    	//add para children (not used in this project yet)
    	if(!new_node.children.isEmpty()){
    		//this.children.addAll(new_node.children); //wrong, have not merge nodes, may have same name twice
    		for(Node temp_node: new_node.children){
    			for(Node original_node: this.children){
    				if(original_node.name.equals(temp_node.name)){
    					this.children.get(this.children.indexOf(original_node)).combineNodes(temp_node);
    					break;
    				}
    				if(this.children.indexOf(original_node) == (this.children.size()-1)){ //if do not find temp_node in this.children
    					this.children.add(temp_node);
    				}
    			}
    		}
    	}
    	
    }
}
