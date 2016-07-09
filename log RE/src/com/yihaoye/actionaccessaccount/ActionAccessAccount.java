package com.yihaoye.actionaccessaccount;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;

import java.util.*;


public class ActionAccessAccount { 
	//这个类是基于UserTree的程序输出数据的（输出数据存在MongoDB里，这是因为在计算每个Action的StayTime时可以基于UserTree的结果，减少重复几乎同样的处理代码）
	//该类需要与MongoDB交互以完成。
	
	final String dir = System.getProperty("user.dir"); //get current working directory path
	String actions_access_account_json = "";
	PrintWriter writer = null;	//for print info into txt file 
	Gson gson = new Gson(); // for convert object/tree to json format
	File file = new File(dir+"/JSONFiles");
	
	HashMap<String, HashMap<String, Integer>> c_s_actions_access_account_map = new HashMap<String, HashMap<String, Integer>>();
	
	public void process(String[] str_line_abstract_info) {
    	
		
    }
	
	//
	public void writeJSON() throws FileNotFoundException, UnsupportedEncodingException{
		file.mkdir(); // create a new directory to store json 
		
		// write into json file
		if(writer!=null) 
			  writer.close();
		writer = new PrintWriter(file + "/" + "actions_access_account.json", "UTF-8");
		actions_access_account_json = gson.toJson(c_s_actions_access_account_map);
		writer.println("Actions Account: " + actions_access_account_json);	// write info into json
		writer.close();
		actions_access_account_json = "";
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