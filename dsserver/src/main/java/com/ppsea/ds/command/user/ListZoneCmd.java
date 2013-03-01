package com.ppsea.ds.command.user;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import com.ppsea.ds.GameServer;
import com.ppsea.ds.command.BaseCmd;
import com.ppsea.ds.command.Command;
import com.ppsea.ds.command.CommandRequest;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.data.Constants;
import com.ppsea.ds.data.Zone;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.exception.BookMarkException;
import com.ppsea.ds.exception.PpseaException;
import com.ppsea.ds.service.PlayerService;
import com.ppsea.ds.util.Memcached;
import com.ppsea.ds.util.OnlineUserNum;

public class ListZoneCmd extends BaseCmd{
	private static final Logger log = Logger.getLogger(ListZoneCmd.class);

	@Override
	public CommandResult execute(CommandRequest cmdReq) { 
		CommandResult result = new CommandResult(STATUS_SUCC);
		try{
			PlayerService.verifySign(cmdReq, null, GameServer.SERVER_GLOBL_MD5KEY);
			result.setVO(TAG_UID, cmdReq.getUid());
			result.setVO(TAG_sSID, cmdReq.getSSid());
			result.setVO(TAG_SID, cmdReq.getSid());
			result.setVO(TAG_ZONES, Constants.ZONES);
			
			
			try{
				String info = (String) Memcached.get(cmdReq.getUid());
				log.warn("info = " + info);
				String loginInfo = null;
				if(!(info==null||"".equals(info))){
					String[] iA = info.split("[_]");
					if(iA.length==2){
						long time = Long.parseLong(iA[0]);
						String zoneId = iA[1];
						SimpleDateFormat sdf = new SimpleDateFormat("M月d日 H时m分");
						for (Zone z : Constants.ZONES) {
							if(z.getId().equals(zoneId)){
								loginInfo = "上次登录时间："+sdf.format(new Date(time))+"<br/>上次登录分区：";
								result.getData().put("loginInfo", loginInfo);
								result.getData().put("lastZone", z);
								break;
							}
						}
					}
				}
			}
			catch(Exception e){
				log.error(e.getMessage(),e);
			}			
			
			try {
				/**
				for (Zone z : Constants.ZONES) {
					
					String res = OnlineUserNum.getOnlineUserResult(z);
					if (res != null) {
						String[] values = res.split("/");
						if (values.length == 2) {
							int online = Integer.valueOf(values[0]);
							int cached = Integer.valueOf(values[1]);
							z.setCurrOnlineUserNum(online);
							z.setCurrCachedUserNum(cached);		
						}
					}
					
					Integer online = z.getCurrOnlineUserNum();
					if (z.getSortId() != null && z.getSortId() == 1) {
						z.setNoteInfo("荐");
					} else {
						if (online > 1000) {
							z.setNoteInfo("挤");
						} else {
							z.setNoteInfo("畅");
						}
					}
					
				}
				*/
			} catch (Exception e) {
				log.error(e.getMessage(),e);
			}
			return result;
		}
		catch (BookMarkException e) {
			log.error("pException", e);
			result.setStatus(Command.STATUS_BM_ERR);
			return result;
		}
	}

	@Override
	protected CommandResult done(Player player, String[] ps)
			throws PpseaException {
		return null;
	}
}
