package com.yihaoye.actiontracetree;

import java.util.HashMap;
//import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

public class UsersActionsPath {

	//<user_id, <lvl_action, querys_of_the_action>>, 输出JSON，供JS用，LinkedHashSet也要有顺序若最后一个词与下层action的首词不同，则改变颜色
	public HashMap<String, LinkedHashMap<String, LinkedHashSet<String>>> the_path;
	
	public UsersActionsPath(){
		the_path = new HashMap<String, LinkedHashMap<String, LinkedHashSet<String>>>();
	}
	
	//以后有空再检查看看有没漏想的情况和bug
	public void setPath(String user_id, String lvl_action, String s1_query){
		if(the_path.containsKey(user_id)){//the user has not been recorded yet
			if(getLastKey(the_path.get(user_id)).equals(lvl_action)){//if current_s_action == the_path.user_ip.last_s_action
				if(the_path.get(user_id).get(lvl_action).contains(s1_query)){//if the_path.user_ip.last_s_action has record the s1_query before
					the_path.get(user_id).get(lvl_action).remove(s1_query);
				}
				the_path.get(user_id).get(lvl_action).add(s1_query);
			}else{//if current_s_action != the_path.user_ip.last_s_action
				the_path.get(user_id).put(lvl_action, new LinkedHashSet<String>());
				the_path.get(user_id).get(lvl_action).add(s1_query);
			}
		}else{//the user has been recorded
			the_path.put(user_id, new LinkedHashMap<String, LinkedHashSet<String>>());
			if(!lvl_action.equals(null)){
				the_path.get(user_id).put(lvl_action, new LinkedHashSet<String>());
			}
		}
	}
	
	public String getLastKey(LinkedHashMap<String, LinkedHashSet<String>> map) {//used in setPath()
		  String out = null;
		  for (String key : map.keySet()) {
		    out = key;
		  }
		  return out;
	}
	
	
	public boolean containsKey(String key){
		return the_path.containsKey(key);
	}
	
	public LinkedHashMap<String, LinkedHashSet<String>> get(String user_id){
		return the_path.get(user_id);
	}
}
