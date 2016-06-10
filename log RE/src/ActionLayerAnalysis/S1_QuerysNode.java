package ActionLayerAnalysis;

import java.util.HashMap;
import java.util.HashSet;

public class S1_QuerysNode {
	
	public int account;
	//public List<String> next_actions;
	//public List<String> following_querys;
	public HashMap<String, HashSet<String>> next_ActionsQuerys;
	
	public S1_QuerysNode(){
		account = 1;
		//next_actions = new ArrayList();
		//following_querys = new ArrayList();
		next_ActionsQuerys = new HashMap<String, HashSet<String>>();
	}
	
	public void set_s1_query_node(String action, String query){
		
		if(next_ActionsQuerys.containsKey(action)){
			next_ActionsQuerys.get(action).add(query);
		}else{
			next_ActionsQuerys.put(action, new HashSet<String>());
			next_ActionsQuerys.get(action).add(query);
		}
	}
}
