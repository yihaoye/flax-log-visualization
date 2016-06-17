package ActionLayerAnalysis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Node {

	public String name;
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
    
    public void setNodeName(String given_name){
    	name = given_name;
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
}
