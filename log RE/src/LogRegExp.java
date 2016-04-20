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


public class LogRegExp {
    public static void main(String argv[]) {
    	try{
    		   FileInputStream fstream = new FileInputStream("/Users/yihao/PersonalFile/Study Work/Waikato/COMP592/yy264/usage-logs/usage.log");
    		   BufferedReader in = new BufferedReader(new InputStreamReader(fstream));
    		   String strLine;
    		   String phrasedLine = "";
    		   String ActionsAccountLine = "";
    		   String unique_id = null;
    		   String unique_date = null;
    		   final String dir = System.getProperty("user.dir"); //get current working directory path
    		   PrintWriter writer = null;	//for print info into txt file (important info for each ip, actions account)
    		   Gson gson = new Gson(); // for convert object/hashmap to json format
    		   
    		   
    		   /* get all unique action_c and action_s */
    		   Pattern pattern_actions[] = {Pattern.compile("\\&c=\\w+"), 
    				   Pattern.compile("\\&s=\\w+")};
    		   
    		   /* Hashmap for calculate each actions numbers, it is hashmap in hashmap data structure */
    		   HashMap<String, HashMap<String, Integer>> c_s_action_map = new HashMap<String, HashMap<String, Integer>>();
    		   HashMap<String, Integer> c_action_account_map = new HashMap<String, Integer>();
    		   HashMap<String, USER> user_action_map = new HashMap<String, USER>();
    		   
    		   /* count actions */
    		   File account = new File(dir+"/AccountFiles");
    		   account.mkdir(); // create a new directory to store txts
    		   
    		   
    		   
    		   /* get all unique uid and date */
    		   Pattern pattern_ip[] = {Pattern.compile("\\d{4}-\\d{2}-\\d{2}"), 
    				   Pattern.compile("uid=\\d+")};
    		   
    		   /* Regular Expression */
    		   Pattern pattern[] = {Pattern.compile("\\d{4}-\\d{2}-\\d{2}"), 
    				   Pattern.compile("\\d{2}:\\d{2}:\\d{2}"), 
    				   Pattern.compile("uid=\\d+"),
    				   Pattern.compile("\\&c=\\w+"), 
    				   Pattern.compile("\\&s=\\w+"), 
    				   Pattern.compile("s1.\\w+=\\w+")};
    		       		   
    		   /* output txt file object initialize */
    		   File file = new File(dir+"/phrasedFiles");
    		   file.mkdir(); // create a new directory to store txts
    		   
    		   
    		   
    		   /* read log line by line */
    		   while ((strLine = in.readLine()) != null)   {
    			   
    			   /* check actions */
    			   Matcher matcher_actions[] = new Matcher[pattern_actions.length];
    			   for(int i=0;i<pattern_actions.length;i++){
    				   matcher_actions[i] = pattern_actions[i].matcher(strLine);
    			   }
    			   if(matcher_actions[0].find() && matcher_actions[1].find()){ //find if new action_c
    				   /* accounting c_actions accessed account */
    				   if(c_action_account_map.containsKey(matcher_actions[0].group())){
    					   /* if already add c_action, increase the account */
    					   Integer temp = c_action_account_map.get(matcher_actions[0].group());
    					   temp++;
    					   c_action_account_map.put(matcher_actions[0].group(), temp);
    				   }else{
    					   /* add new accessed c_action account */
    					   c_action_account_map.put(matcher_actions[0].group(), 1);
    				   }
    				   /* accounting s_actions for each c_action */
    				   if(c_s_action_map.containsKey(matcher_actions[0].group())){
    					   /* counting s_actions */
    					   if(c_s_action_map.get(matcher_actions[0].group()).containsKey(matcher_actions[1].group())){
    						   Integer temp = c_s_action_map.get(matcher_actions[0].group()).get(matcher_actions[1].group());
							   temp++;
							   c_s_action_map.get(matcher_actions[0].group()).put(matcher_actions[1].group(), temp);
    					   }else{
    						   c_s_action_map.get(matcher_actions[0].group()).put(matcher_actions[1].group(), 1);
    					   }
    				   }else{
    					   /* add new c_actions(which contain a own hashmap) */
    					   c_s_action_map.put(matcher_actions[0].group(), new HashMap<String, Integer>());
    				   }
    			   }
    			   
    			   /* check ip (by date and uid), if changed, create new txt file to write */
    			   Matcher matcher_ip[] = new Matcher[pattern_ip.length];
    			   for(int i=0;i<pattern_ip.length;i++){
    				   matcher_ip[i] = pattern_ip[i].matcher(strLine);
    			   }
    			   if(matcher_ip[0].find() && matcher_ip[1].find()){
    				   if(!matcher_ip[0].group().equals(unique_date) || !matcher_ip[1].group().equals(unique_id)){
    					   unique_date = matcher_ip[0].group();
    					   unique_id = matcher_ip[1].group();
    					   /* create a new txt file for writing */
    					   if(writer!=null) 
    						   writer.close();
    		    		   writer = new PrintWriter(file + "/" + unique_date + " "+ unique_id + " " + "phrasedResult.txt", "UTF-8");
    				   }
    			   }
    			   
    			   /* separate log info into files by ip, fetch important info from each line and write to above created txt file */
    			   Matcher matcher[] = new Matcher[pattern.length];
    			   
				   for(int i=0; i<pattern.length; i++){
					   matcher[i] = pattern[i].matcher(strLine);
					   if(matcher[i].find()){
						   if(i<(pattern.length-1)){
							   phrasedLine += matcher[i].group() + " ";
							   strLine = strLine.replace(matcher[i].group(), ""); // remove match part
						   }
						   
						   if(i==(pattern.length-1)){ //last RegExp repeat executed (s1. property)
							   phrasedLine += matcher[i].group() + " ";
							   strLine = strLine.replace(matcher[i].group(), ""); // remove match part
							   matcher[i] = pattern[i].matcher(strLine);
							   if(matcher[i].find()){
								   i--;
							   }
						   }
					   }
				   }
				   
				   System.out.println (phrasedLine);
				   writer.println(phrasedLine);	// write info into txt
				   phrasedLine = "";
    			   
    		   }
    		   
    		   /* sort c_action_account_map */
    		   Map<String, Integer> sorted_c_action_hashmap = new HashMap<String, Integer>();
    		   sorted_c_action_hashmap = sortByValue(c_action_account_map);

    		   // create json file
    		   if(writer!=null) 
				   writer.close();
    		   writer = new PrintWriter(account + "/" + "c_s_actions" +  " " + "AccountResult.json", "UTF-8");
    		   
			   // write into json file
    		   ActionsAccountLine = gson.toJson(c_s_action_map);
			   //ActionsAccountLine = gson.toJson(sorted_c_action_hashmap);
			   System.out.println (ActionsAccountLine);
			   writer.println(ActionsAccountLine);	// write info into txt
			   ActionsAccountLine = "";
    		   
    		   in.close();
    		   writer.close();
    		   
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




