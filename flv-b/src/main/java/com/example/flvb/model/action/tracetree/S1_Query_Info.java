package com.example.flvb.model.action.tracetree;


public class S1_Query_Info {
	
	//public HashMap<String, HashMap<String, HashSet<String>>> users_actions_paths;//<user_id, <level_action, querys_of_the_action>>, 另做一个这个类，不在此处实现，输出JSON，供JS用，HashSet也要有顺序若最后一个词与下层action的首词不同，则改变颜色

	public int account;
	public String user_id;
	public String lvl_action;
	//<user_id, level_action>, refer to the user_id&level_action within the above "users_actions_paths"
	
	public S1_Query_Info(){
		account = 1;
	}
	
	public void accountIncrease(){
		account++;
	}
	
	public void setPathRelatedParams(String user, String action){
		user_id = user;
		lvl_action = action;
	}
}
