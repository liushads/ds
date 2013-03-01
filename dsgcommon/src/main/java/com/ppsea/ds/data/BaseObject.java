package com.ppsea.ds.data;

import java.io.Serializable;

/**
 * ClassName:BaseObject, 游戏中所有实体的基类 
 *
 * @author   Daniel.Hao
 * @version  
 * @since    Ver 1.0
 * @Date	 2008	Nov 28, 2008		8:25:28 PM
 *
 * @see 	 
 */
public abstract class BaseObject implements Serializable {

	private static final long serialVersionUID = 1L;
	
//	// For npc's dialog and action
//	public static final String VARIABLE_PLAYER = "player";
//	public static final String VARIABLE_NPC = "npc";
//	public static final String VARIABLE_DIALOG = "dialog";
//	public static final String VARIABLE_ACTION_IDS = "actions";
//	public static final String VARIABLE_MISSION = "mission";
//	public static final String VARIABLE_REDIRECT = "redir";
//	public static final String VARIABLE_QUESTION_ID = "question";
//	public static final String VARIABLE_UPGRADE = "upgrade";
//	
//	public static final String DEFAULT_DIALOG = "";
//	
//	///////////////////////////////////////////////
//	public String toPrint() {
//		String s = "";
//		try {
//			s = getPropertyString(this);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return s;
//	}
//
//	public String getPropertyString(Object entityName) throws Exception {
//		Class c = entityName.getClass();
//		Field field[] = c.getDeclaredFields();
//		StringBuffer sb = new StringBuffer();
//		for (Field f : field) {
//			sb.append(f.getName());
//			sb.append("=");
//			sb.append(invokeMethod(entityName, f.getName(), null));
//			sb.append(",");
//		}
//		return sb.toString();
//	}
//
//	public Object invokeMethod(Object owner, String methodName, Object[] args)
//			throws Exception {
//		Class ownerClass = owner.getClass();
//
//		methodName = methodName.substring(0, 1).toUpperCase()
//				+ methodName.substring(1);
//		Method method = null;
//		try {
//			method = ownerClass.getMethod("get" + methodName);
//		} catch (SecurityException e) {
//		} catch (NoSuchMethodException e) {
//			// TODO Auto-generated catch block
//			// e.printStackTrace();
//		}
//
//		return method.invoke(owner);
//	}

}
