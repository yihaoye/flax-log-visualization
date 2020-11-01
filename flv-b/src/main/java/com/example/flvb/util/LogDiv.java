package com.example.flvb.util;

import java.util.regex.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
// import java.util.*;


public class LogDiv {
	
   	String dir = System.getProperty("user.dir"); // get current working directory path
	String unique_id = null;
	String unique_date = null;
	String user_log_Lines = "";
	PrintWriter writer = null; // for print info into txt file (important info for each ip, actions account)

   	// constructor
 	public LogDiv() {
		this.dir = System.getProperty("user.dir");
		this.unique_id = null;
		this.unique_date = null;
		this.user_log_Lines = "";
		this.writer = null;
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
	FileStore fs = new FileStore();
	File file = new File(this.dir + "/log-div");
   
   	// carry out to div log into txt by user id
   	public void process(String strLine) throws FileNotFoundException, UnsupportedEncodingException {
	   
		this.file.mkdir(); // create a new directory to store txts
		
		/* check ip (by date and uid), if changed, create new txt file to write */
		Matcher matcher_ip[] = new Matcher[this.pattern_ip.length];
		for (int i=0; i<this.pattern_ip.length; i++) {
			matcher_ip[i] = this.pattern_ip[i].matcher(strLine);
		}
		if (matcher_ip[0].find() && matcher_ip[1].find()) {
			if (!matcher_ip[0].group().equals(this.unique_date) || !matcher_ip[1].group().equals(this.unique_id)) {
				this.unique_date = matcher_ip[0].group();
				this.unique_id = matcher_ip[1].group();
				fs.save(String path, String fileName, Optional<Map<String, String>> optionalMetadata, InputStream inputStream);

				/* create a new txt file for writing */
				if (this.writer != null) 
					this.writer.close();
				this.writer = new PrintWriter(this.file + "/" + this.unique_date + " "+ this.unique_id + " " + "log_record.txt", "UTF-8"); // need a throws above
			}
		}
	   
		/* separate log info into files by ip, fetch important info from each line and write to above created txt file */
		Matcher matcher[] = new Matcher[this.pattern.length];
		
		for (int i=0; i<this.pattern.length; i++) {
			matcher[i] = this.pattern[i].matcher(strLine);
			if (matcher[i].find()) {
				if (i < (this.pattern.length-1)) {
					this.user_log_Lines += matcher[i].group() + " ";
					strLine = strLine.replace(matcher[i].group(), ""); // remove match part
				}
				
				if (i == (this.pattern.length-1)) { // last RegExp repeat executed (s1. property)
					this.user_log_Lines += matcher[i].group() + " ";
					strLine = strLine.replace(matcher[i].group(), ""); // remove match part
					matcher[i] = this.pattern[i].matcher(strLine);
					if (matcher[i].find()) {
						i--;
					}
				}
			}
		}
	   
		System.out.println("LogDiv:" + this.user_log_Lines);
		this.writer.println(this.user_log_Lines); // write info into txt
		this.user_log_Lines = "";
	}
   
}
