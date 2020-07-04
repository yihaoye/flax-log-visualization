package com.example.flvb.entity.action.relation;

public class ActionLink {

	public int source;
	public int target;
	public int relation;
	
	public ActionLink(){
		
	}
	
	public ActionLink(int src, int tar, int rel){
		source = src;
		target = tar;
		relation = rel;
	}
}
