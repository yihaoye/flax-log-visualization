package com.yihaoye.logdiv;
import java.util.regex.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
//import java.util.*;


public class LogDiv {
	
   String dir = System.getProperty("user.dir"); //get current working directory path
   String unique_id = null;
   String unique_date = null;
   String user_log_Lines = "";
   PrintWriter writer = null;	//for print info into txt file (important info for each ip, actions account)

   //constructor
 	public LogDiv(){
 		dir = System.getProperty("user.dir");
 		unique_id = null;
 		unique_date = null;
 		user_log_Lines = "";
 		writer = null;
 	}
   
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
   File file = new File(dir+"/LogDivFiles");
   
   
   
   // carry out to div log into txt by user id
   public void process(String strLine) throws FileNotFoundException, UnsupportedEncodingException{ // what the throws means ?
	   
	   file.mkdir(); // create a new directory to store txts
	   
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
    		   writer = new PrintWriter(file + "/" + unique_date + " "+ unique_id + " " + "log_record.txt", "UTF-8"); // need a throws above
		   }
	   }
	   
	   /* separate log info into files by ip, fetch important info from each line and write to above created txt file */
	   Matcher matcher[] = new Matcher[pattern.length];
	   
	   for(int i=0; i<pattern.length; i++){
		   matcher[i] = pattern[i].matcher(strLine);
		   if(matcher[i].find()){
			   if(i<(pattern.length-1)){
				   user_log_Lines += matcher[i].group() + " ";
				   strLine = strLine.replace(matcher[i].group(), ""); // remove match part
			   }
			   
			   if(i==(pattern.length-1)){ //last RegExp repeat executed (s1. property)
				   user_log_Lines += matcher[i].group() + " ";
				   strLine = strLine.replace(matcher[i].group(), ""); // remove match part
				   matcher[i] = pattern[i].matcher(strLine);
				   if(matcher[i].find()){
					   i--;
				   }
			   }
		   }
	   }
	   
	   System.out.println ("LogDiv:"+user_log_Lines);
	   writer.println(user_log_Lines);	// write info into txt
	   user_log_Lines = "";
   }
   
}
