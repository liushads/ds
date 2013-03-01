package com.ppsea.ds.data;

/**
 * ClassName:Scriptable 涉及到脚本操作的类应该实现该接口以区分其他类
 *
 * @author   Daniel Hao
 * @version  
 * @since    Ver 1.0
 * @Date	 2008	Dec 11, 2008		2:01:09 PM
 *
 * @see 	 
 */
public interface Scriptable {

	// For npc's dialog and action
	public static final String VARIABLE_PLAYER = "player";
	public static final String VARIABLE_NPC = "npc";
	public static final String VARIABLE_DIALOG = "dialog";
	public static final String VARIABLE_ACTION_IDS = "actions";
	public static final String VARIABLE_MISSION = "mission";
	public static final String VARIABLE_REDIRECT = "redir";
	public static final String VARIABLE_QUESTION_ID = "question";
	public static final String VARIABLE_UPGRADE = "upgrade";
	
	public static final String DEFAULT_DIALOG = "";
	
	public static final String PARAM_ACTION = "action";
	
	// NPC 通用函数
	static final String SCRIPT_FUNCTIONS = 
			"function setFlag(value){" +
			"  if(typeof flag != 'undefined')flag = value;" +
			"}" +
			"function addDialog(value){" +
			"  if(typeof dialog != 'undefined'){" +
			"    if(Object.prototype.toString.apply(value) === '[object Array]'){" +
			"	   var i = parseInt(Math.random() * (value.length));" +
			"	     dialog += value[i];" + 
			"    }else{" +
			"        dialog += value;" +
			"    }" +
			"  }" +
			"}" +
			"function addAction(value){" +
			"  if(typeof actions != 'undefined')actions.add(value);" +
			"}" +
			"function redirect(value){" +
			"  if(typeof redir != 'undefined')redir = value;" +
			"}";
	
	/**
	 * 预编译脚本
	 *     
	 * @return void    
	 * @throws
	 */
	void compile();
}
