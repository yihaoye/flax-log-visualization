import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.HashMap;
import java.util.regex.*;

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

    		   
    		   /* get all unique action_c and action_s */
    		   Pattern pattern_actions[] = {Pattern.compile("c=\\w+"), 
    				   Pattern.compile("s=\\w+")};
    		   
    		   /* Hashmap for calculate each actions numbers, it is hashmap in hashmap data structure */
    		   HashMap<String, HashMap<String, Integer>> c_action_map = new HashMap<String, HashMap<String, Integer>>();

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
    				   Pattern.compile("c=\\w+"), 
    				   Pattern.compile("s=\\w+"), 
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
    				   if(c_action_map.containsKey(matcher_actions[0].group())){
    					   if(c_action_map.get(matcher_actions[0].group()).containsKey(matcher_actions[1].group())){
    						   Integer temp = c_action_map.get(matcher_actions[0].group()).get(matcher_actions[1].group());
							   temp++;
							   c_action_map.get(matcher_actions[0].group()).put(matcher_actions[1].group(), temp);
    					   }else{
    						   c_action_map.get(matcher_actions[0].group()).put(matcher_actions[1].group(), 1);
    					   }
    				   }else{
    					   c_action_map.put(matcher_actions[0].group(), new HashMap<String, Integer>());
    				   }
    			   }
    			   
    			   /* check ip (by date and uid), if changed, create new file to write */
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
    			   
    			   /* fetch important info from each line */
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
    			   
    			   /*if(matcher.find()){
    				   System.out.println (matcher.group());
    			   }*/
    			   
    		   }
    		   
    		   
    		   /* create actions account files */
			   for(String c_key : c_action_map.keySet()){
				   if(writer!=null) 
					   writer.close();
	    		   writer = new PrintWriter(account + "/" + c_key +  " " + "AccountResult.txt", "UTF-8");
	    		   for(String s_key : c_action_map.get(c_key).keySet()){
	    			   ActionsAccountLine += s_key + "    " +c_action_map.get(c_key).get(s_key) + " \n";
	    		   }
	    		   System.out.println (ActionsAccountLine);
				   writer.println(ActionsAccountLine);	// write info into txt
				   ActionsAccountLine = "";
			   }
    		   
    		   
    		   in.close();
    		   writer.close();
    		} catch (Exception e) {
    		     System.err.println("Error: " + e.getMessage());
    		}

    }
}


