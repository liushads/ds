package com.ppsea.ds.util;

import java.util.Properties;

public class WordFilter {
	private static MultiStringReplacer wordFitler;
	private static String newWord = "*";

	public static void init() {
		wordFitler = new MultiStringReplacer();
		
		//加载脏话
		Properties prop = Util.getProjectProperties(Util.DIRTY_WORD_FILE);
		String dirtyWords = prop.getProperty("dirty_word");
		String[] dirtyWordArr = dirtyWords.split("\\|");
		for (int i = 0; i < dirtyWordArr.length; i++) {
			wordFitler.add(dirtyWordArr[i], newWord);
		}
	}

	public static String filterDirtyWord(String s) {
		return wordFitler.replace(s);
	}

	public static boolean checkDirtyWorld(String s) {
		return wordFitler.existWords(s);
	}
}
