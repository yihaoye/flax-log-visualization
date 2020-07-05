package com.example.flvb.util;

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
	public void process(String str_line, String[] str_line_abstract_info, String[] s1_paras) {
		
		for (int j = 0; j<this.info.length; j++) { // clear info[] each time call process, 否则上次赋值可能残留
			this.info[j] = "";
		}
		
		abstractInfo(str_line);
		str_line_abstract_info[0] = this.info[0]; // date
		str_line_abstract_info[1] = this.info[1]; // time
		str_line_abstract_info[2] = this.info[2]; // uid
		str_line_abstract_info[3] = this.info[3]; // cuurent_c_action
		str_line_abstract_info[4] = this.info[4]; // current_s_action
		str_line_abstract_info[5] = this.info[5]; // s1_query
		s1_paras = this.info[6].split(",");
	}
	 
	 
	//
	public void abstractInfo(String strLine) {
		Matcher matcher[] = new Matcher[this.pattern.length];
		for (int i=0; i<this.pattern.length; i++) {
			matcher[i] = this.pattern[i].matcher(strLine);
			if (matcher[i].find()) {
				if (i < (this.pattern.length-1)) {
					this.info[i] = matcher[i].group();
					strLine = strLine.replace(matcher[i].group(), ""); // remove match part
				}

				if (i == (this.pattern.length-1)) { // last RegExp repeat executed (s1. property)
					this.info[i] += matcher[i].group() + ",";
					strLine = strLine.replace(matcher[i].group(), ""); // remove match part
					matcher[i] = this.pattern[i].matcher(strLine);
					if (matcher[i].find()) {
						i--;
					}
				}
			}
		}
	}

}
