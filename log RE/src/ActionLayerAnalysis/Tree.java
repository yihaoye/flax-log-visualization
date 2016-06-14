package ActionLayerAnalysis;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Tree {

	public Node root;

    public Tree() {
        root = new Node();
        root.name = "root";
        root.children = new ArrayList<Node>();
        root.access_percentage = 1;
    }

    
    
    public void add_action_node(Node added_root, Node added_node){
    	
    	//add node to current tree
    	if(added_root.name.equals(added_node.name)){
    		added_root.access_account++;   
    		//add s1_paras account
        	for(String key : added_node.s1_querys.keySet()){
        		if(added_root.s1_querys.containsKey(key)){
        			int access_value = added_root.s1_querys.get(key).account;////////
        			access_value += added_node.s1_querys.get(key).account;//////
        			added_root.s1_querys.get(key).account = access_value;////////
        		}else{
        			added_root.s1_querys.put(key, new S1_QuerysNode());
        		}
        	}
    	}else{
    		boolean isChild = false;
    		for(Node temp_node : added_root.children){
    			if(temp_node.name.equals(added_node.name)){
    				add_action_node(temp_node, added_node);
    				isChild = true;
    			}
    		}
    		if(!isChild){
    			added_root.children.add(added_node);
    		}
    		//System.out.println (added_root.children.get(0).name);
    	}
    	    	
    }
    
    
    //levelOrder traverse
    public Node bfs_traverse(Node node, String action_name){
    	//Node no_node = null;
    	if(action_name == null){
    		System.out.println ("here");
    		return root;
    	}
    	
    	Queue<Node> queue = new LinkedList<Node>(); 
    	//A Queue is an interface, which means you cannot construct a Queue directly. The best option is to construct off a class that already implements the Queue interface, 
    	//like one of the following: AbstractQueue, ArrayBlockingQueue, ConcurrentLinkedQueue, DelayQueue, LinkedBlockingQueue, LinkedList, PriorityBlockingQueue, PriorityQueue, or SynchronousQueue.
    	
    	if(node!=null){
    		queue.add(node);
    	}else{
    		queue.add(root);
    	}
    	System.out.println ("here");
    	while(!queue.isEmpty()){
    		//System.out.println ("here");
    		Node temp = queue.remove();
    		if(temp.name.equals(action_name)){
    			return temp;
    		}
    		for(int i = 0; i < temp.children.size(); i++){
    			queue.add(temp.children.get(i));
    		}
    	}
    	
    	return root;
    }
}



/* Tree example
 public class Tree<T> {
    private Node<T> root;

    public Tree(T rootData) {
        root = new Node<T>();
        root.data = rootData;
        root.children = new ArrayList<Node<T>>();
    }

    public static class Node<T> {
        private T data;
        private Node<T> parent;
        private List<Node<T>> children;
    }
} 
 */
