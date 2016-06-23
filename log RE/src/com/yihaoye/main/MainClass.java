package com.yihaoye.main;
import com.yihaoye.actionrelation.*;
import com.yihaoye.actiontracetree.*;
import com.yihaoye.filterandrename.*;
import com.yihaoye.logdiv.LogDiv;
import com.yihaoye.usertree.*;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.*;



public class MainClass {
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
    		   
    		   	   
    		   String str_line = null;
    		   //use str_line_abstract_info array since Java can not pass parameters by reference in single variable, but array can
    		   String[] str_line_abstract_info = new String[6]; //String date, time, uid, current_c_action, current_s_action, s1_query;
    		   String[] s1_paras = null;
    		   
    		   //regular expression object (and replace the original action name with new simple action name)
    		   RegExp reg_exp = new RegExp();
    		   //filter object for filtering auto created actions ,and rename c_action and s_action
    		   FilterAndRename filter = new FilterAndRename();
    		   //create objects for different process
    		   LogDiv log_div = new LogDiv(); //for div log by user id
    		   UserTree user_tree = new UserTree(); //for build up complete user tree (include their actions and relevant info)
    		   ActionTraceTreeProcessor action_trace_tree = new ActionTraceTreeProcessor();
    		   ActionRelationProcessor action_relation_ds = new ActionRelationProcessor();    		   
    		   		   
    		   
    		   //for(int i=0; i<=3; i++){
    			   in = new BufferedReader(new InputStreamReader(new FileInputStream("/Users/yihao/PersonalFile/Study Work/Waikato/COMP592/yy264/usage-logs/usage_test.log")));
    			//}
    			   
    			   
	    		   /* read log line by line */
	    		   while ((str_line = in.readLine()) != null){
	    			   
	    			   //regular expression
	    			   reg_exp.process(str_line, str_line_abstract_info, s1_paras);
	    			   
	    			   //if action is not in white list, dismiss the line
	    			   if(filter.process(str_line_abstract_info)){ //目前只保留&c=collocations内的s_action
	    				   continue; //skip this loop
	    			   }
	    			   	    			   
	    			   //
	    			   //log_div.process(str_line_abstract_info);
	    			   
	    			   //
	    			   action_trace_tree.process(str_line_abstract_info);
	    			   
	    			   //
	    			   //action_relation_ds.process(str_line_abstract_info);
	    			   
	    			   //
	    			   //user_tree.process(str_line_abstract_info);
	    			   
	    		   }
    		   
	    	   action_trace_tree.setEachNodeAccessPercentage();
    		   
    		   action_trace_tree.writeJSON();
    		   //action_relation_ds.writeJSON();
    		   //user_tree.writeJSON();
    		   
    		   
    		   
    		} catch (Exception e) {
    		     System.err.println("Error: " + e.getMessage());
    		}

    }
    
    
    
    
    
    
    //sort user/c_action/s_action hashmap
    public void sortTheWholeDataStructure(){
    	
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




