package com.ppsea.ds.task;

import java.util.GregorianCalendar;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.log4j.Logger;

import com.ppsea.ds.data.Constants;
import com.ppsea.ds.data.model.Sect;
import com.ppsea.ds.manager.PlayerMG;
import com.ppsea.ds.manager.SectMG;
import com.ppsea.ds.service.GamblerService;

/**
 * 
 * 资源回收定时器
 * 
 * 
 */
public class CleanTask {

	private static Logger logger = Logger.getLogger(CleanTask.class);
	private Timer timer = new Timer();
	
	public CleanTask() {
	}
	
	/**
	 * 计时器启动
	 * 
	 * 3秒回收1次	 * 
	 */
	public void start() {
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.set(GregorianCalendar.HOUR_OF_DAY, 00);
		calendar.set(GregorianCalendar.MINUTE, 00);
		calendar.set(GregorianCalendar.SECOND, 01);
		timer.schedule(	
				new TimerTask() {
					public void run() {
						//扫描清理用户
						try {
							PlayerMG.instance.scan();
							GamblerService.checkAndEndRound(null);
						}
						catch (Exception e) {
							logger.error("clean exception ：",e);
						}
						//检查门派比武
					}
				}
				, calendar.getTime()
				, Constants.SCAN_INTERVAL);

	}		
}
