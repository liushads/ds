/*
 * 
 */
package com.ppsea.ds.command.player;

import java.util.Date;
import java.util.List;

import com.ppsea.ds.command.BaseCmd;
import com.ppsea.ds.command.Command;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.data.GlobalGenerator;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.data.model.PlayerPasswordSafty;
import com.ppsea.ds.exception.PpseaException;
import com.ppsea.ds.manager.DBManager;
import com.ppsea.ds.service.DBService;

/**
 * @author Administrator
 * @date 2010-10-29
 */
public class PasswordSaftyCmd extends BaseCmd {

	/**
	 * @see com.ppsea.ds.command.BaseCmd#done(com.ppsea.ds.data.model.Player, java.lang.String[])
	 */
	@Override
	protected CommandResult done(Player player, String[] ps)
	        throws PpseaException {
		CommandResult result = new CommandResult(Command.STATUS_SUCC);
		String action = ps[0];
		if ("p".equals(action)) {
			
			result.setStatus("ps");
			result.setVO("action", ps[1]);
		} else if ("s".equals(action)) {
			//开启保护
			String answer = ps[1];
			
			List<PlayerPasswordSafty> lst = DBManager.queryPlayerPasswordSafty(player.getId());
			if (lst != null && lst.size() > 0) {
				result.setVO("modify", "true");
				result.setStatus("modify");
			} else {
				if (null == answer || "".equals(answer)) {
					result.setStatus("info");
					result.setVO("action", "input");
				} else {
					PlayerPasswordSafty pps = createPlayerPasswordSafty(player);
					pps.setAnswer(answer.trim());
					DBService.commitNoCacheUpdate(pps);
					result.setStatus("info");
					result.setVO("action", action);
				}

			}
		} else if ("g".equals(action)) {
			//找回密码
			String answer = ps[1];
			if (player.getPasswd() == null && player.getPasswd() == null) {
				result.setStatus("info");
				result.setVO("action", "wgg");
			} else {
				List<PlayerPasswordSafty> lst = DBManager.queryPlayerPasswordSafty(player.getId());
				if (lst != null && lst.size() > 0) {
					PlayerPasswordSafty pps = lst.get(0);
					if (!pps.getAnswer().equals(answer))  {
						result.setVO("action", "wg");
					} else {
						result.setVO("book", player.getPasswd());
						result.setVO("item", player.getPasswd());
						result.setVO("action", action);
					}
					result.setStatus("info");
				} else {
					/**
					result.setStatus("ps");
					result.setVO("action", "s");
					result.setVO("plus", "s");
					*/
				}
			}
			
			
		} else if ("m".equals(action)) {
			if (ps.length > 1) {
				String old = ps[1];
				String answer = ps[2];
				if (null == answer || "".equals(answer.trim()) || old == null || "".equals(old.trim())) {
					result.setStatus("info");
					result.setVO("action", "input");
				} else {
					List<PlayerPasswordSafty> lst = DBManager.queryPlayerPasswordSafty(player.getId());
					PlayerPasswordSafty pps = lst.get(0);
					if (!pps.getAnswer().equals(old)) {
						result.setStatus("info");
						result.setVO("action", "w");
					} else {
						pps.setAnswer(answer.trim());
						pps.setTimes(pps.getTimes() + 1);
						pps.setLastModifyDate(new Date());
						DBService.commitNoCacheUpdate(pps);
						result.setStatus("info");
						result.setVO("action", action);
					}
				}
				
				
			} else {
				result.setStatus("modify");				
			}
		}
		
		return result;
	}

	private PlayerPasswordSafty createPlayerPasswordSafty(Player player) {
		PlayerPasswordSafty pps = new PlayerPasswordSafty();
		pps.setId(GlobalGenerator.instance.getIdForNewObj(pps));
		pps.setPlayerId(player.getId());
		pps.setCreateDate(new Date());
		pps.setTimes(0);
		return pps;
	}
}
