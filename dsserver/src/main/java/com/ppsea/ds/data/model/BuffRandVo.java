package com.ppsea.ds.data.model;

import java.io.Serializable;

public class BuffRandVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private BuffRand buffRand;
	
	private String lock;

	public BuffRand getBuffRand() {
		return buffRand;
	}

	public void setBuffRand(BuffRand buffRand) {
		this.buffRand = buffRand;
	}

	public String getLock() {
		return lock;
	}

	public void setLock(String lock) {
		this.lock = lock;
	}

}
