import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
    		       		   
    		   
    		   /* output txt file object initialize */
    		   PrintWriter writer = null;
    		   
    		   
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
    		    		   writer = new PrintWriter(unique_date + " "+ unique_id + " " + "phrasedResult.txt", "UTF-8");
    				   }
    			   }
    			   
    			   /* fetch important info from each line */
    			   Matcher matcher[] = new Matcher[pattern.length];
    			   for(int i=0;i<pattern.length;i++){
    				   matcher[i] = pattern[i].matcher(strLine);
    			   }
    			   
    			   
    			   //if(matcher[0].find() && matcher[0].group().equals("2016-01-28") && matcher[2].find()){
    				   //System.out.println (strLine);
    				   for(int i=0;i<pattern.length;i++){
    					   if(matcher[i].find()){
    						   phrasedLine += matcher[i].group() + " ";
    					   }
    				   }
    				   System.out.println (phrasedLine);
    				   writer.println(phrasedLine);
    				   phrasedLine = "";
    			   //}
    			   
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


