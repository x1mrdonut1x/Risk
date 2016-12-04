package com.polandball.risk.main.model;

import java.util.LinkedList;

import com.polandball.risk.framework.model.AbstractLog;

public class Log extends AbstractLog{

	public Log(int limit) {
		super(limit);
	}

	@Override
	public void addLog(String log) {
		logs.add(log);
		
		if (logs.size() > limit){
			logs.removeFirst();
		}
	}

	@Override
	public void removeLog(int index) {
		logs.remove(index);
	}

	@Override
	public String getLastLog() {
		return logs.getLast();
	}

	@Override
	public LinkedList<String> getLogs() {
		return logs;
	}

}
