package com.yihaoye.main;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegExp {
	
	String[] info = new String[7];

	/* Regular Expression */
	 Pattern pattern[] = {Pattern.compile("\\d{4}-\\d{2}-\\d{2}"), 
			 Pattern.compile("\\d{2}:\\d{2}:\\d{2}"), 
			 Pattern.compile("uid=\\d+"),
			 Pattern.compile("\\&c=\\w+"), 
			 Pattern.compile("\\&s=\\w+"), 
			 Pattern.compile("s1.query=\\w+"),
			 Pattern.compile("s1.\\w+=\\w+")};

	 
	 //
	 public void process(String str_line, String[] str_line_abstract_info, String[] s1_paras){
		 
		 for(int j = 0; j<info.length; j++){//clear info[] each time call process, 否则上次赋值可能残留
			 info[j] = "";
		 }
		 
		 abstractInfo(str_line);
		 str_line_abstract_info[0] = info[0]; //date
		 str_line_abstract_info[1] = info[1]; //time
		 str_line_abstract_info[2] = info[2]; //uid
		 str_line_abstract_info[3] = info[3]; //cuurent_c_action
		 str_line_abstract_info[4] = info[4]; //current_s_action
		 str_line_abstract_info[5] = info[5]; //s1_query
		 s1_paras = info[6].split(",");
		 
	 }
	 
	 
	 //
	public void abstractInfo(String strLine){
		Matcher matcher[] = new Matcher[pattern.length];
		for(int i=0; i<pattern.length; i++){
			matcher[i] = pattern[i].matcher(strLine);
			if(matcher[i].find()){
				if(i<(pattern.length-1)){
					info[i] = matcher[i].group();
					strLine = strLine.replace(matcher[i].group(), ""); // remove match part
				}

				if(i==(pattern.length-1)){ //last RegExp repeat executed (s1. property)
					info[i] += matcher[i].group() + ",";
					strLine = strLine.replace(matcher[i].group(), ""); // remove match part
					matcher[i] = pattern[i].matcher(strLine);
					if(matcher[i].find()){
						i--;
					}
				}
			}
		}
	}
	
	
	
}
