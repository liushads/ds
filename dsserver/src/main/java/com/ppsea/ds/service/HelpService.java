package com.ppsea.ds.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ppsea.ds.data.model.HelpQa;
import com.ppsea.ds.data.model.QuestionType;
import com.ppsea.ds.manager.DBManager;

/**
 * 新手帮助业务逻辑处理类
 * 
 */
public class HelpService {
	
	// <type, QuestionType>
	private static Map<Integer, QuestionType> qtypes = new HashMap<Integer, QuestionType>();
	
	// <id, QuestionType>
	private static Map<Integer, HelpQa> qas = new HashMap<Integer, HelpQa>();

	/**
	 * 获取所有消息类型
	 * 
	 * @return
	 */
	public static List<QuestionType> getQuestionTypes() {
		if (qtypes.isEmpty()) {
			initQtypes();
		}

		List<QuestionType> result = new ArrayList<QuestionType>();
		result.addAll(qtypes.values());
		Collections.sort(result, new Comparator() {
            public int compare(Object o1, Object o2) {
				QuestionType q1 = (QuestionType)o1;
				QuestionType q2 = (QuestionType)o2;
				return (q1.getId()- q2.getId()) ;
            }
		});
		return result;
	}
	
	public static QuestionType getQuesitonType(Integer type) {
		if (qtypes.isEmpty()) {
			initQtypes();
		}
		
		return qtypes.get(type);
	}
	
	private static synchronized void initQtypes() {
		if (qtypes.isEmpty()) {
			List<QuestionType> list = DBManager.loadQuestionTypes();
			for (QuestionType type : list) {
				qtypes.put(type.getType(), type);
			}
		}
	}
	
	public static List<HelpQa> getOneTypeQa(int type) {
		List<HelpQa> result = new ArrayList<HelpQa>();
		
		if (qas.isEmpty()) {	
			initQas();
		}
		
		Collection<HelpQa> values = qas.values();
		for (HelpQa qa : values) {
			if (qa.getType().intValue() == type) {
				result.add(qa);
			}
		}
		if (result != null) {
			Collections.sort(result, new Comparator() {
                public int compare(Object o1, Object o2) {
					HelpQa h1 = (HelpQa)o1;
					HelpQa h2 = (HelpQa)o2;
					return h1.getId() - h2.getId();
                }
			});
		}
		return result;
	}
	
	public static HelpQa getHelpQa(Integer id) {
		if (qas.isEmpty()) {	
			initQas();
		}
		
		return qas.get(id);
	}
	
	public static List<HelpQa> searchHelpQa(String keywords) {
		//return DBManager.searchHelpQs(keywords);
		
		if (qas.isEmpty()) {	
			initQas();
		}
		
		List<HelpQa> list = new ArrayList<HelpQa>();
		for (HelpQa qa : qas.values()) {
			if (qa.getQuestion().indexOf(keywords) != -1) {
				list.add(qa);
			}
		}
		return list;
	}
	
	private static synchronized void initQas() {
		if (qas.isEmpty()) {
			List<HelpQa> list = DBManager.loadHelpQas();
			for (HelpQa qa : list) {
				qas.put(qa.getId(), qa);
			}
		}	
	}
	
}
