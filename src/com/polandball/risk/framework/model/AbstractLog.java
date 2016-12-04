package com.polandball.risk.framework.model;

import java.util.LinkedList;

public abstract class AbstractLog {
	
	protected LinkedList<String> logs;
	protected int limit;
	
	public AbstractLog(int limit){
		this.limit = limit;
		logs = new LinkedList<String>();
	}
	
	public void setLimit(int limit){
		this.limit = limit;
	}
	
	public int getLimit(){
		return limit;
	}
	
	public abstract void addLog(String log);
	public abstract void removeLog(int index);
	public abstract String getLastLog();
	public abstract LinkedList<String> getLogs();
}
