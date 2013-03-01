package com.ppsea.ds.command;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.ppsea.ds.util.Util;

public class CommandSet {
	private static final Logger log = Logger.getLogger(CommandSet.class);
	
	private static CommandSet instance = null;
	
	private Map<String, Command> cmdSet = new HashMap<String,Command >();
	
	private static final String COMMAND_FILE = "command.properties"; 
	
	private CommandSet(){
	}
	
	public static CommandSet getInstance(){
		if(instance == null ){
			instance = new CommandSet();
		}
		return instance;
	}
	
	public void loadCommand(){
		Properties properties = Util.loadProperties(COMMAND_FILE);
		// Load command objects here
		for(Object key : properties.keySet()){
			String clazz = properties.getProperty((String)key);
			if(clazz == null) continue;
			try {
				String[] k = ((String)key).split(",");
				Command cmd = (Command)Class.forName(clazz).getConstructor().newInstance();
				for (String name : k) {
					cmdSet.put(name, cmd);
				}
			} catch (Exception e) {
				log.error("exception", e);
			}
		}	
	}
	
	public Command getCommand(String cmdName){
		return cmdSet.get(cmdName);
	}
	
	public String printCommandSet(){
		return cmdSet.toString();
	}

	/**
	 * @return the cmdSet
	 */
	public Map<String, Command> getCmdSet() {
		return cmdSet;
	}
	
}
