package com.ppsea.ds.command.help;

import java.util.List;

import com.ppsea.ds.command.BaseCmd;
import com.ppsea.ds.command.Command;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.data.model.HelpQa;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.data.model.QuestionType;
import com.ppsea.ds.exception.PpseaException;
import com.ppsea.ds.service.ErrorCode;
import com.ppsea.ds.service.HelpService;

/**
 * 新手帮助command
 * 
 * 
 */
public class HelpCmd extends BaseCmd {

	@Override
	protected CommandResult done(Player player, String[] ps)
			throws PpseaException {
		CommandResult result = new CommandResult(Command.STATUS_SUCC);
		
		// 问题类型列表(/t)
		if (ps != null && ps[0].equals("t")) {
			List<QuestionType> qtypes = HelpService.getQuestionTypes();
			result.setVO("qtypes", qtypes);
		}
		
		// 某种类型的问题列表(/l/type)
		else if (ps != null && ps[0].equals("l")) {
			int type = Integer.parseInt(ps[1]);
			List<HelpQa> qas = HelpService.getOneTypeQa(type);
			QuestionType qtype = HelpService.getQuesitonType(type);
			
			result.setVO("qas", qas);
			result.setVO("qtype", qtype);
		}
		
		// 查看一个问题的答案(/q/qaId)
		else if (ps != null && ps[0].equals("q")) {
			Integer qaId = Integer.parseInt(ps[1]);
			HelpQa qa = HelpService.getHelpQa(qaId);
			Integer type = qa.getType();
			QuestionType qtype = HelpService.getQuesitonType(type);
			result.setVO("qa", qa);
			result.setVO("qtype", qtype);
		}
		
		// 关键字查询(/s/keywords)
		else if (ps != null && ps[0].equals("s")) {
			String keyword = ps[1].trim();
			List<HelpQa> qas = HelpService.searchHelpQa(keyword);
			result.setVO("qas", qas);
		} 
		
		// 错误的书签 
		else {
			setResult(result,STATUS_FAIL,ErrorCode.ERR_SYS,"书签错误");
		}
		
		return result;
	}

}
