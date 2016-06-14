import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.*;
import java.util.*;
import com.google.gson.*;

import ActionLayerAnalysis.TreeProcessor;
import ActionRelation.ActionRelationProcessor;


public class LogRegExp {
    public static void main(String argv[]) {
    	try{
    		   FileInputStream fstream[] = new FileInputStream[7];
    		   BufferedReader in = null;
    		   fstream[6] = new FileInputStream("/Users/yihao/PersonalFile/Study Work/Waikato/COMP592/yy264/usage-logs/usage.log");
    		   fstream[5] = new FileInputStream("/Users/yihao/PersonalFile/Study Work/Waikato/COMP592/yy264/usage-logs/usage.log.1");
    		   fstream[4] = new FileInputStream("/Users/yihao/PersonalFile/Study Work/Waikato/COMP592/yy264/usage-logs/usage.log.2");
    		   fstream[3] = new FileInputStream("/Users/yihao/PersonalFile/Study Work/Waikato/COMP592/yy264/usage-logs/usage.log.3");
    		   fstream[2] = new FileInputStream("/Users/yihao/PersonalFile/Study Work/Waikato/COMP592/yy264/usage-logs/usage.log.4");
    		   fstream[1] = new FileInputStream("/Users/yihao/PersonalFile/Study Work/Waikato/COMP592/yy264/usage-logs/usage.log.5");
    		   fstream[0] = new FileInputStream("/Users/yihao/PersonalFile/Study Work/Waikato/COMP592/yy264/usage-logs/usage.log.6");
    		   
    		   
    		   
    		   String strLine;
    		   String JSONLine = "";
    		   final String dir = System.getProperty("user.dir"); //get current working directory path
    		   PrintWriter writer = null;	//for print info into txt file (important info for each ip, actions account)
    		   Gson gson = new Gson(); // for convert object/hashmap to json format
    		   
    		   //create three object for three different process
    		   LogDiv log_div = new LogDiv(); //for div log by user id
    		   UserTree user_tree = new UserTree(); //for build up complete user tree (include their actions and relevant info)
    		   TreeProcessor action_layer_tree = new TreeProcessor();
    		   ActionRelationProcessor arp = new ActionRelationProcessor();
    		   
    		   //filter object for filtering auto created actions
    		   FilteredActions filter = new FilteredActions();
    		   
    		   //for(int i=0; i<=3; i++){
    			   in = new BufferedReader(new InputStreamReader(new FileInputStream("/Users/yihao/PersonalFile/Study Work/Waikato/COMP592/yy264/usage-logs/usage_test.log")));
    			//}
    			   
	    		   /* read log line by line */
	    		   while ((strLine = in.readLine()) != null){
	    			   //
	    			   if(filter.filter_action(strLine)){
	    				   continue; //skip this loop
	    			   }
	    			   
	    			   //
	    			   //log_div.process(strLine);
	    			   
	    			   //
	    			   action_layer_tree.process(strLine);
	    			   
	    			   //
	    			   //arp.process(strLine);
	    			   
	    			   //
	    			   //user_tree.process(strLine);
	    			   
	    		   }
    		   
    		   
    		   action_layer_tree.write_json();
    		   //arp.write_json();
    		   //user_tree.write_json();
    		   
    		   
    		   
    		} catch (Exception e) {
    		     System.err.println("Error: " + e.getMessage());
    		}

    }
    
    
    
    
    
    
    //sort user/c_action/s_action hashmap
    public void sort_the_whole_data_structure(){
    	
    }
    
    /* HashMap sort function */
    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue( Map<K, V> map ){
	    List<Map.Entry<K, V>> list = new LinkedList<Map.Entry<K, V>>( map.entrySet() );
	    Collections.sort( list, new Comparator<Map.Entry<K, V>>(){
	        public int compare( Map.Entry<K, V> o1, Map.Entry<K, V> o2 ){
	            return (o2.getValue()).compareTo( o1.getValue() ); //why change o1,o2 can reverse?
	        }
	    });
	
	    Map<K, V> result = new LinkedHashMap<K, V>();
	    for (Map.Entry<K, V> entry : list){
	        result.put( entry.getKey(), entry.getValue() );
	    }
	    return result;
	}
}




