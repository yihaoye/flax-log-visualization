package com.yihaoye.usertree;
import java.util.HashMap;
import java.util.Map;

public class USER {
	
	HashMap<String, C_ACTION> c_actions_map = new HashMap<String, C_ACTION>();
	private long startTime;
	private long endTime;
	private long stayTime;
	public String last_pre_c_action = null;
	public String last_pre_s_action = null;
	private int actions_account;
	
	//constructor
	public USER(){
		startTime = 0;
		endTime = 0;
		stayTime = 0;
		actions_account = 1;
	}
	
	
	
	/**************              Refered functions 1 (carry out by order for dealing with c_s_actions)                ******************/
	//set stayTime for last c_s_action
	public void calculate_last_action_stayTime(String time){
		if(last_pre_c_action != null && last_pre_s_action != null){
			//calculate last c_action stayTime
			c_actions_map.get(last_pre_c_action).set_endTime(time);
			c_actions_map.get(last_pre_c_action).calculate_stayTime();
			//clear the c_action startTime and endTime
			c_actions_map.get(last_pre_c_action).set_startTime(null);
			c_actions_map.get(last_pre_c_action).set_endTime(null);
			
			//calculate last s_action stayTime
			c_actions_map.get(last_pre_c_action).s_actions_map.get(last_pre_s_action).set_endTime(time);
			c_actions_map.get(last_pre_c_action).s_actions_map.get(last_pre_s_action).calculate_stayTime();
			//clear the s_action startTime and endTime
			c_actions_map.get(last_pre_c_action).s_actions_map.get(last_pre_s_action).set_startTime(null);
			c_actions_map.get(last_pre_c_action).s_actions_map.get(last_pre_s_action).set_endTime(null);
		}
		
		//calculate user stayTime
		if(last_pre_c_action != null && last_pre_s_action != null){
			if(endTime > startTime){
				stayTime = endTime - startTime;
			}else{
				stayTime = endTime + 43200 - startTime; //43200seconds means 12 hours
			}
		}
	}
	
	//set c_s_actions hashmap, add/increase C_ACTION/SACTION, set pre_action/startTime for both action, set s1.para
	public void set_c_s_actions_map(String current_c_action, String current_s_action, String startTime, String[] s1_paras){
		
		//set c_actions_map (add/increase C_ACTION to c_actions_map, set pre_action/startTime for the map)
		set_c_actions_map(current_c_action, last_pre_c_action, startTime);
		
		//set s_actions_map (add/increase S_ACTION to s_actions_map, set pre_action/startTime for the map)
		C_ACTION temp_c_action = c_actions_map.get(current_c_action);
		temp_c_action.set_s_actions_map(current_s_action, last_pre_c_action, last_pre_s_action, startTime, s1_paras);
		
	}
	
	//set last pre_c_s_action
	public void update_last_action(String current_c_action, String current_s_action){
		last_pre_c_action = current_c_action;
		last_pre_s_action = current_s_action;
	}
	
	
	
	
	/**************              Refered functions 2 (for dealing USER inner variables)                ******************/
	//increase user's actions account
	public void actions_account_increased(){
		actions_account++;
	}
	
	//set the time for the user first behavior
	public void set_startTime(String time){
		String[] tokens = time.split(":");
		int hours = Integer.parseInt(tokens[0]);
		int minutes = Integer.parseInt(tokens[1]);
		int seconds = Integer.parseInt(tokens[2]);
		startTime = 3600 * hours + 60 * minutes + seconds;
	}
	
	//set the time for the user last behavior
	public void set_endTime(String time){
		String[] tokens = time.split(":");
		int hours = Integer.parseInt(tokens[0]);
		int minutes = Integer.parseInt(tokens[1]);
		int seconds = Integer.parseInt(tokens[2]);
		endTime = 3600 * hours + 60 * minutes + seconds;
	}
	
	
	
	
	/**************              Inner functions                 ******************/
	//put in c_actions_map hashmap, set pre_action/startTime for the map
	public void set_c_actions_map(String current_c_action, String last_c_action, String start_Time){
		if(c_actions_map.containsKey(current_c_action)){
			C_ACTION temp_c_action = c_actions_map.get(current_c_action);
			temp_c_action.access_account_increased();
		}else{
			c_actions_map.put(current_c_action, new C_ACTION());
		}
		
		C_ACTION temp_c_action = c_actions_map.get(current_c_action);
		temp_c_action.set_startTime(start_Time);
		temp_c_action.add_pre_c_actions(last_c_action);
	}

}
