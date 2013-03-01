package com.ppsea.ds.command.user;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;

import com.ppsea.ds.GameServer;
import com.ppsea.ds.command.BaseCmd;
import com.ppsea.ds.command.CommandRequest;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.data.model.Sect;
import com.ppsea.ds.exception.BookMarkException;
import com.ppsea.ds.exception.PpseaException;
import com.ppsea.ds.manager.DBManager;
import com.ppsea.ds.manager.SectMG;
import com.ppsea.ds.service.PlayerService;

public class EnterZoneCmd extends BaseCmd{
	private static final Logger log = Logger.getLogger(EnterZoneCmd.class);

	/**
	 * 进入分区，列出该区所有角色
	 * */
	@SuppressWarnings("unchecked")
	@Override
	public CommandResult execute(CommandRequest cmdReq){
		try{
			CommandResult result = new CommandResult(STATUS_SUCC);
			PlayerService.verifySign(cmdReq, null, GameServer.SERVER_GLOBL_MD5KEY);
			
			String uid = cmdReq.getUid();
			//列出用户所有角色
			List<Player> players = DBManager.queryPlayerByUser(uid);
			List<Player> newPlayers = new ArrayList<Player>(); 
			for(Player player:players){
				newPlayers.add(player);
			}
			
			result.setVO(TAG_UID, uid);
			result.setVO(TAG_MY_PLAYERS, newPlayers);
			if (newPlayers.size()==0) {
				List<Sect> sects = SectMG.instance.getSectList();
				Collections.sort(sects, new Comparator() {
					public int compare(final Object a, final Object b) {
						Sect s1 = (Sect)a;
						Sect s2 = (Sect)b;
						return s1.getId() - s2.getId();
					}
				});
				result.setVO(TAG_SECTS, sects);
				result.setStatus("no_user");
			}
	
			StringBuffer sb =new StringBuffer();
			sb.append(uid).append("|")
				.append(0).append("|")
				.append(0).append("|")
				.append(0).append("|")
				.append(0);
			result.setLogMsg(sb);
			return result;
		}
		catch (BookMarkException e) {
			CommandResult result = new CommandResult(STATUS_BM_ERR);
			return result;
		}
		
		
	}

	@Override
	protected CommandResult done(Player player, String[] ps)
			throws PpseaException {
		return null;
	}


}
