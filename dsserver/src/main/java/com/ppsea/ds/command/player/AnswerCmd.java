package com.ppsea.ds.command.player;

import java.util.Map;

import com.ppsea.ds.command.BaseCmd;
import com.ppsea.ds.command.Command;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.data.Constants;
import com.ppsea.ds.data.ErrorMsg;
import com.ppsea.ds.data.model.CityFacilityNpc;
import com.ppsea.ds.data.model.Npc;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.data.model.Question;
import com.ppsea.ds.exception.PpseaException;
import com.ppsea.ds.manager.MissionMG;
import com.ppsea.ds.manager.NpcMG;
import com.ppsea.ds.service.ErrorCode;

/**
 * ClassName:AnswerCmd 回答问题
 *
 * @author   Daniel Hao
 * @version  
 * @since    Ver 1.0
 *
 * @see 	 
 */
public class AnswerCmd extends BaseCmd {

	/**
	 * ps[0] = npcId
	 * ps[1] = questionId
	 * ps[2] = player answerId
	 * (non-Javadoc)
	 * @see com.ppsea.server.command.BaseCmd#done(com.ppsea.server.core.model.Player, java.lang.String[])
	 */
	@Override
	protected CommandResult done(Player player, String[] ps)
			throws PpseaException {
		
		CityFacilityNpc cfn = NpcMG.instance.getNpcInFacility(player.getCityFacility().getId()).get(Integer.valueOf(ps[0]));
		if(cfn == null){
			CommandResult result = new CommandResult(Command.STATUS_FAIL);
			result.setVO(TAG_ERR_MSG, new ErrorMsg(ErrorCode.ERR_SYS, "网络忙！"));
			return result;
		}
		
		Npc npc = cfn.getNpc();
		
		Question question = MissionMG.instance.getQuestion(Integer.valueOf(ps[1]));
		
		if(player.getInteraction(Constants.PREFIX_QUESTION + question.getId()) >= 1){
			// 后退刷页面
			player.setInteraction(Constants.PREFIX_QUESTION + question.getId(), 2);
		}else if(question.getCorrect().equals(Integer.valueOf(ps[2]))){
			// 回答正确，更新标志位
			player.setInteraction(Constants.PREFIX_QUESTION + question.getId(), 1);
		}else{
			// 回答错误
			player.setInteraction(Constants.PREFIX_QUESTION + question.getId(), 0);
		}
		
		//
		//player.setInteraction(Constants.PREFIX_NPC + npc.getUuid(), question.getId());
		
		return callOtherCmd(COMMAND_TALK, player, String.valueOf(npc.getId())); 
	}

}
