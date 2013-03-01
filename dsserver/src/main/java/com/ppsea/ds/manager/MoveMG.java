/*
 * 
 */
package com.ppsea.ds.manager;

import java.util.List;

import com.ppsea.ds.data.model.ChuansongItemConfig;

/**
 * @author Administrator
 * @date 2010-8-6
 */
public class MoveMG {

	public static int[] pointPool = new int[100];
	
	public static List<ChuansongItemConfig> chuansongList = null;
	
	public static MoveMG instance = new MoveMG();
	
	public void init() {
		int index = 0;
		chuansongList = DBManager.queryChuanSongitemConfig();
		for (ChuansongItemConfig conf : chuansongList) {
			try {
				if (conf.getProbability() > 0) {
					for(int i = 0; i < conf.getProbability(); i++) {
						try {
							pointPool[index] = conf.getUseTimes();
							index++;
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
