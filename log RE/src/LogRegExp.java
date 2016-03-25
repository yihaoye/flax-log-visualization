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
    		   String unique_id = null;
    		   String unique_date = null;
    		   final String dir = System.getProperty("user.dir"); //get current working directory path
    		   
    		   
    		   /* get all unique uid and date */
    		   Pattern pattern_ip[] = {Pattern.compile("\\d{4}-\\d{2}-\\d{2}"), 
    				   Pattern.compile("uid=\\d+")};
    		   
    		   
    		   /* Regular Expression */
    		   Pattern pattern[] = {Pattern.compile("\\d{4}-\\d{2}-\\d{2}"), 
    				   Pattern.compile("\\d{2}:\\d{2}:\\d{2}"), 
    				   Pattern.compile("uid=\\d+"),
    				   Pattern.compile("s=\\w+"), 
    				   Pattern.compile("c=\\w+"), 
    				   Pattern.compile("s1.\\w+=\\w+")};
    		       		   
    		   /* Hashmap for calculate each actions numbers */
    		   HashMap<Character, Integer> c_action_map = new HashMap<Character, Integer>();
    		   HashMap<Character, Integer> s_action_map = new HashMap<Character, Integer>();
    		   
    		   /* output txt file object initialize */
    		   PrintWriter writer = null;	//print important info for each ip
    		   File file = new File(dir+"/phrasedFiles");
    		   file.mkdir(); // create a new directory to store txts
    		   PrintWriter actions_account = new PrintWriter(file + "/" + "actionsAccount.txt", "UTF-8");	//print accounting for all actions
    		   
    		   /* read log line by line */
    		   while ((strLine = in.readLine()) != null)   {
    			   
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
						   
						   if(i==(pattern.length-1)){ //last RegExp repeat executed
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
				   writer.println(phrasedLine);
				   phrasedLine = "";
    			   
    			   /*if(matcher.find()){
    				   System.out.println (matcher.group());
    			   }*/
    			   
    		   }
    		   in.close();
    		   //writer.close();
    		} catch (Exception e) {
    		     System.err.println("Error: " + e.getMessage());
    		}

    }
}


