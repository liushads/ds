package com.ppsea.ds.command.admin;

import java.util.LinkedList;
import java.util.List;

import com.ppsea.ds.command.admin.TerminateCmd;
import com.ppsea.ds.command.admin.AddCopperCmd;
import com.ppsea.ds.command.admin.AddGoldCmd;

public class AdminCmdChain {
	private static List<AdminCmd> chain = new LinkedList<AdminCmd>();	
	public static List<AdminCmd> getChain(){
		return chain;
	}
	
	public static void registerAll(){
		chain.add(new TerminateCmd());
		chain.add(new AddItemCmd());
		chain.add(new ReloadPlayerCmd());
		chain.add(new AddCopperCmd());
		chain.add(new AddGoldCmd());
		chain.add(new AddResellGoldCmd());
	}
}
