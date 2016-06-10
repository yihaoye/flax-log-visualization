package ActionLayerAnalysis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Node {

	public String name;
    //private Node parent;
    public List<Node> children;
    public HashMap<String, S1_QuerysNode> s1_querys; //////query:related next_ActionQuery
    public int access_account;
    public float access_percentage;
    
    public Node(){
    	name = null;
    	children = new ArrayList<Node>();
    	s1_querys = new HashMap<String, S1_QuerysNode>();//////////
    	access_account = 1;
    }
    
    public void set_node_name(String given_name){
    	name = given_name;
    }
    
    public void set_access_percentage(float given_percentage){
    	access_percentage = given_percentage;
    }
    
    public void set_s1_querys(String given_query){//////////
    	s1_querys.put(given_query, new S1_QuerysNode());//////////
    }
}
