//import java.text.DateFormat;
//import java.text.SimpleDateFormat;
//import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class S_ACTION {
	
	HashMap<String, Integer> s1_paras_map = new HashMap<String, Integer>();
	public long startTime;
	public long endTime;
	public int stayTime;
	HashMap<String, Integer> pre_c_s_actions_map = new HashMap<String, Integer>();
	//public String last_pre_s_action = null;
	public int accessed_account;
	
	//constructor
	public S_ACTION(){
		startTime = 0;
		endTime = 0;
		stayTime = 0;
		accessed_account = 1;
	}
		
	//put in s1_paras_map hashmap
	public void put_s1_paras_map(String s1_para){
		if(s1_paras_map.containsKey(s1_para)){
			Integer temp = s1_paras_map.get(s1_para);
			temp++;
			s1_paras_map.put(s1_para, temp);
		}else{
			s1_paras_map.put(s1_para, 1);
		}
	}
	
	//put in pre_s_actions_map hashmap
	public void add_pre_c_s_actions(String pre_c_s_action){
		if(pre_c_s_actions_map.containsKey(pre_c_s_action)){
			Integer temp = pre_c_s_actions_map.get(pre_c_s_action);
			temp++;
			pre_c_s_actions_map.put(pre_c_s_action, temp);
		}else{
			pre_c_s_actions_map.put(pre_c_s_action, 1);
		}
	}
	
	//set the startTime of the s_action
	public void set_startTime(String time){
		if(time==null){
			startTime = 0;
		}else{
			String[] tokens = time.split(":");
			int hours = Integer.parseInt(tokens[0]);
			int minutes = Integer.parseInt(tokens[1]);
			int seconds = Integer.parseInt(tokens[2]);
			startTime = 3600 * hours + 60 * minutes + seconds;
		}
	}
	
	//set the endTime of the s_action
	public void set_endTime(String time){
		if(time==null){
			endTime = 0;
		}else{
			String[] tokens = time.split(":");
			int hours = Integer.parseInt(tokens[0]);
			int minutes = Integer.parseInt(tokens[1]);
			int seconds = Integer.parseInt(tokens[2]);
			endTime = 3600 * hours + 60 * minutes + seconds;
		}
	}
	
	//calculate the stayTime of the s_action
	public void calculate_stayTime(){
		if(endTime > startTime){
			stayTime += endTime - startTime;
		}else{
			stayTime += endTime + 43200 - startTime; //43200seconds means 12 hours
		}
	}
	
	//increase accessed account
	public void access_account_increased(){
		accessed_account++;
	}
	
}
