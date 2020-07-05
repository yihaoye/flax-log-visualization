package com.example.flvb.model.calendar.fragment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import com.google.gson.Gson;

public class CalendarFragment {
	
	final String dir = System.getProperty("user.dir"); //get current working directory path
	String calendar_fragment_json = "";
	PrintWriter writer = null;	//for print info into json file 
	Gson gson = new Gson(); // for convert object/tree to json format
	File file = new File(this.dir + "/json");
	
	HashMap<String, HashMap<String, HashMap<String, DateData>>> calendar_data_map = new HashMap<String, HashMap<String, HashMap<String, DateData>>>(); // HashMap<Year, HashMap<Month, HashMap<Date, DateData>>>
	
	
	public void process(String[] str_line_abstract_info) {
		// System.out.println (str_line_abstract_info[0]);
		setJSON(str_line_abstract_info[0], str_line_abstract_info[2], str_line_abstract_info[1], str_line_abstract_info[3], str_line_abstract_info[4], str_line_abstract_info[5]);
	}
	
	public void setJSON(String date, String uid, String time, String current_c_action, String current_s_action, String s1_query) {
		String date_split[] = null;
		date_split = date.split("-");
		
		String the_year = date_split[0];
		String the_month = date_split[1];
		String the_date = date_split[2];
		
		if (this.calendar_data_map.containsKey(the_year)) {
			if (this.calendar_data_map.get(the_year).containsKey(the_month)) {
				if (this.calendar_data_map.get(the_year).get(the_month).containsKey(the_date)) {
					this.calendar_data_map.get(the_year).get(the_month).get(the_date).setDateData();
				} else {
					this.calendar_data_map.get(the_year).get(the_month).put(the_date, new DateData());
					this.calendar_data_map.get(the_year).get(the_month).get(the_date).setDateData();
				}
			} else {
				this.calendar_data_map.get(the_year).put(the_month, new HashMap<String, DateData>());
				this.calendar_data_map.get(the_year).get(the_month).put(the_date, new DateData());
				this.calendar_data_map.get(the_year).get(the_month).get(the_date).setDateData();
			}
		} else {
			this.calendar_data_map.put(the_year, new HashMap<String, HashMap<String, DateData>>());
			this.calendar_data_map.get(the_year).put(the_month, new HashMap<String, DateData>());
			this.calendar_data_map.get(the_year).get(the_month).put(the_date, new DateData());
			this.calendar_data_map.get(the_year).get(the_month).get(the_date).setDateData();
		}
	}
	
	//
	public void writeJSON() throws FileNotFoundException, UnsupportedEncodingException {
		this.file.mkdir(); // create a new directory to store json
		 // check if writer is closed
		 if (this.writer != null) 
			this.writer.close();
		 
		 // action_trace_tree_json write into json file
		 this.writer = new PrintWriter(this.file + "/" + "calendar_fragment_data.json", "UTF-8");
		 this.calendar_fragment_json = this.gson.toJson(this.calendar_data_map);
		 System.out.println("Calendar:" + this.calendar_fragment_json);
		 this.writer.println(this.calendar_fragment_json); // write info into json
		 this.writer.close();
		 
		 // clear the String
		 this.calendar_fragment_json = "";
	}
	
}
