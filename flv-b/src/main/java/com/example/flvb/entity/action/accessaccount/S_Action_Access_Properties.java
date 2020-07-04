package com.example.flvb.entity.action.accessaccount;

public class S_Action_Access_Properties {
	
	int accessed_account = 0;
	int stayTime = 0;
	
	public S_Action_Access_Properties(){
		
	}
	
	public void addAccessedAccount(String input_accessed_account){
		accessed_account += Integer.parseInt(input_accessed_account);
	}
	
	public void addStayTime(String input_stayTime){
		stayTime += Integer.parseInt(input_stayTime);
	}
}
