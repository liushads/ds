package com.ppsea.ds.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.ppsea.ds.data.Constants;
import com.ppsea.ds.data.ErrorMsg;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.data.model.Question;
import com.ppsea.ds.manager.DBManager;
import com.ppsea.ds.manager.MissionMG;

public class QuestionService {
		
	public static Question chouti(Player player) {
		Random random=new Random();
		int num=random.nextInt(40);
		Question question= MissionMG.instance.getQuestion(653+num);
		return question;
	}
	public static ErrorMsg dati(Player player) {
		return Constants.SUCC;
	}
}
