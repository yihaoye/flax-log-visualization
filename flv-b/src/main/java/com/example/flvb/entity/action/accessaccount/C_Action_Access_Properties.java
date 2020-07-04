package com.example.flvb.entity.action.accessaccount;

import java.util.HashMap;

public class C_Action_Access_Properties {
	
	int accessed_account = 0;
	int stayTime = 0;
	HashMap<String, S_Action_Access_Properties> s_actions_access_account_map = new HashMap<String, S_Action_Access_Properties>();
	
	public C_Action_Access_Properties(){
		
	}
	
	public void addCActionAccessedAccount(String input_accessed_account){
		accessed_account += Integer.parseInt(input_accessed_account);
	}
	
	public void addCActionStayTime(String input_stayTime){
		stayTime += Integer.parseInt(input_stayTime);
	}
	
	public void addSActionAccessedAccount(String s_action_name, String input_accessed_account){
		s_actions_access_account_map.get(s_action_name).addAccessedAccount(input_accessed_account);
	}
	
	public void addSActionStayTime(String s_action_name, String input_stayTime){
		s_actions_access_account_map.get(s_action_name).addStayTime(input_stayTime);
	}
}
