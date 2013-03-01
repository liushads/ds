/**
 * 
 */
package com.ppsea.ds.proxy.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

public class RegexMatch {

	protected class MatchRule {
		private List<String> option = new ArrayList<String>();
		private List<String> values = new ArrayList<String>();
		private Map<String, Pattern> patterns = new HashMap<String, Pattern>();
		/**
		 * 
		 * @param optionNum
		 * @param valuesNum
		 */
		public MatchRule(int optionNum, int valuesNum, String line) {
			parse(optionNum, valuesNum, line);
		}
		/**
		 * 
		 */
		public String toString() {
			StringBuffer sb = new StringBuffer();
			sb.append("[option]");
			for (int i = 0; i < option.size(); ++i) {
				sb.append("(" + option.get(i) + ")");
			}
			sb.append("[values]");
			for (int i = 0; i < values.size(); ++i) {
				sb.append("(" + values.get(i) + ")");
			}
			return sb.toString();
		}
		/**
		 * 
		 * @param line
		 */
		public void parse(int optionNum, int valuesNum, String line) {
			String[] s = line.split(";", optionNum + valuesNum + 1);
			if (s.length < (optionNum + valuesNum)) {
				System.out.println("[ERROR]:rule is notvalid:" + line);
				return;
			}
			for (int i = 0; i < optionNum + valuesNum; ++i) {
				if (i < optionNum) option.add(s[i].trim());
				else values.add(s[i].trim());
			}
		}
		/**
		 * 
		 * @param opt
		 * @param val
		 * @return
		 */
		public boolean match(ArrayList<String> opt, ArrayList<String> val) {
			val.clear();
			if (opt.size() < option.size()) return false;
			for (int i = 0; i < option.size(); ++i) {
				if (matchItem(option.get(i), opt.get(i)) == false) {
					return false;
				}
			}
			for (String s : values) val.add(s);
			return true;
		}
		/**
		 * 
		 * @param r
		 * @param s
		 * @return
		 */
		private boolean matchItem(String r, String s) {
			if (r.equals(s)) return true;
			Pattern p = null;
			if (patterns.containsKey(r)) {
				p = patterns.get(r);
			} else {
				p = Pattern.compile(r);
				patterns.put(r, p);
			}
			return p.matcher(s).matches();
		}
	}
	/**
	 * 
	 */
	public class MatchDomain {
		private String name = "";
		private int optionNum = 0;
		private int valuesNum = 0;
		private ArrayList<MatchRule> rules = new ArrayList<MatchRule>();
		/**
		 * 
		 * @param optionNum
		 * @param valuesNum
		 */
		public MatchDomain(String name, int optionNum, int valuesNum) {
			this.name = name;
			this.optionNum = optionNum;
			this.valuesNum = valuesNum;
		}
		/**
		 * 
		 */
		public String toString() {
			StringBuffer sb = new StringBuffer();
			sb.append("[domain]");
			sb.append("(name:" + name + ")");
			sb.append("(optionNum:" + optionNum + ")");
			sb.append("(valuesNum:" + valuesNum + ")");
			sb.append("\n");
			for (int i = 0; i < rules.size(); ++i) {
				sb.append(rules.get(i).toString() + "\n");
			}
			return sb.toString();
		}
		/**
		 * 
		 * @param line
		 */
		public void put(String line) {
			rules.add(new MatchRule(optionNum, valuesNum, line));
		}
		/**
		 * 
		 * @param opt
		 * @param val
		 * @return
		 */
		public boolean match(ArrayList<String> option, ArrayList<String> values) {
			for (int i = 0; i < rules.size(); ++i) {
				if (rules.get(i).match(option, values)) {
					return true;
				}
			}
			return false;
		}
	}
	/**
	 * 
	 */
	private static String configFile = "";
	private String lastDomain = "";
	private String domainBeg = "[::";
	private HashMap<String, MatchDomain> 
		domains = new HashMap<String, MatchDomain>();
	/**
	 * 
	 */
	public String toString() {
		Set<String> st = domains.keySet();
		StringBuffer sb = new StringBuffer();
		for (String s : st) {
			sb.append("-----------------\n"); 
			sb.append("Domain:" + s + "\n");
			sb.append(domains.get(s).toString());
		}
		sb.append("\n-----------------");
		return sb.toString();
	}
	/**
	 * 
	 * @param config
	 * @throws Exception
	 */
	public void loadFromFile(String config) throws Exception {
		configFile = config;
		FileReader f = new FileReader(config);
		BufferedReader br = new BufferedReader(f);
		String line = null;
		
		while ((line = br.readLine()) != null) {
			line.trim();
			//空白行
			if (line.equals("")) continue;
			//注释
			if (line.startsWith("#")) continue;
			//Domain结束
			if (line.startsWith("[/")) {
				lastDomain = "";
				continue;
			}
			//Domain开始
			if (line.startsWith("[") && !line.startsWith("[/")) {
				int i = line.indexOf(']');
				if (i > domainBeg.length()) {
					String cont = line.substring(1, i);
					String[] csp = cont.split(":", 3);
					lastDomain = csp[0];
					int opn = Integer.valueOf(csp[1]);
					int vln = Integer.valueOf(csp[2]);
					domains.put(lastDomain, new MatchDomain(lastDomain, opn, vln));
				}
				continue;
			}
			//规则内容
			if (!line.equals("") && !lastDomain.equals("")) {
				MatchDomain mr = domains.get(lastDomain);
				if (mr != null) mr.put(line);
			}
		}
	}
	/**
	 * 
	 * @param name
	 * @return
	 */
	public MatchDomain getDomain(String name) {
		return domains.get(name);
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		RegexMatch regex = new RegexMatch();
		try {
			regex.loadFromFile("regex.config");
			RegexMatch.MatchDomain domain = regex.getDomain("Test");
			ArrayList<String> option = new ArrayList<String>();
			ArrayList<String> values = new ArrayList<String>();
			option.add("cmd.login");
			option.add("succ");
			option.add("0");
			boolean ret = domain.match(option, values);
			
			System.out.println(option.toString() + "(match?)" 
					+ values.toString() + "(result:" + ret + ")");
			System.out.println("-------------------------------");
			System.out.println(regex.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
