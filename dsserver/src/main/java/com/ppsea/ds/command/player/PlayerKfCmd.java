/*
 * 
 */
package com.ppsea.ds.command.player;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.ppsea.ds.command.BaseCmd;
import com.ppsea.ds.command.Command;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.data.ErrorMsg;
import com.ppsea.ds.data.GlobalGenerator;
import com.ppsea.ds.data.model.Gm;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.data.model.PlayerKf;
import com.ppsea.ds.exception.PpseaException;
import com.ppsea.ds.manager.PlayerMG;
import com.ppsea.ds.service.DBService;
import com.ppsea.ds.service.ErrorCode;
import com.ppsea.ds.util.Util;

/**
 * @author Administrator
 * @date 2010-8-25
 */
public class PlayerKfCmd extends BaseCmd {
	
	private final int APPLY_MIN_LEVEL = 40;

	/**
	 * @see com.ppsea.ds.command.BaseCmd#done(com.ppsea.ds.data.model.Player, java.lang.String[])
	 */
	@Override
	protected CommandResult done(Player player, String[] ps)
	        throws PpseaException {
		CommandResult result = new CommandResult(Command.STATUS_SUCC);
		if (ps != null && ps.length > 0) {
			String param = ps[0];
			if ("r".equals(param)) {
				result.setStatus("rule");
			} else if ("b".equals(param)) {
				//申请
				ErrorMsg msg = new ErrorMsg(ErrorCode.SUCC,"");
				if (player.getLevel() <= APPLY_MIN_LEVEL) {
					result.setVO(TAG_ERR_MSG, new ErrorMsg(ErrorCode.ERR_PLAYER_LEVEL, "您的级别不够，高于"+APPLY_MIN_LEVEL+"才可以申请成为解答员"));
					result.setStatus(Command.STATUS_FAIL);
					return result;
				}
				
				PlayerKf kf = player.getKf();
				if (kf != null) {
					result.setVO(TAG_ERR_MSG, new ErrorMsg(ErrorCode.ERR_KF_EXIST, "您已经是解答员，不能重复申请"));
					result.setStatus(Command.STATUS_FAIL);
					return result;
				}
				if (ps != null && ps.length > 1) {
					 msg = becomeKf(player);
					if (msg.code != ErrorCode.SUCC) {
						result.setVO(TAG_ERR_MSG, msg);
						result.setStatus(Command.STATUS_FAIL);
					} else {
						result.setStatus("note");
						result.setVO("desc", msg.getText());
					}
				} else {
					result.setStatus("apply");
				}
				
			} else if ("q".equals(param)) {
				//退出
				ErrorMsg msg = new ErrorMsg(ErrorCode.SUCC,"");
				PlayerKf kf = player.getKf();
				if (kf == null) {
					result.setVO(TAG_ERR_MSG, new ErrorMsg(ErrorCode.ERR_KF_EXIST, "您不是解答员，不能请求此操作"));
					result.setStatus(Command.STATUS_FAIL);
					return result;
				}
				if (ps.length > 1) {
					msg = cancelKf(player);
					if (msg.code != ErrorCode.SUCC) {
						result.setVO(TAG_ERR_MSG, msg);
						result.setStatus(Command.STATUS_FAIL);
					} else {
						result.setStatus("note");
						result.setVO("desc", msg.getText());
					}
				} else {
					result.setStatus("quit");
				}
				
			} else if ("l".equals(param)) {
				//排行榜 
				int pageId = 0;
				try {
					pageId = Integer.parseInt(ps[1]);
				} catch (Exception e) {
				}
				List<Player> pList = topKf(null);
				Util.page(pList, pageId, result);
				result.setStatus("top");
			} else if ("a".equals(param)) {
				//疑问解答 
				List<Player> kfList = getKfList(player);
				List<Player> temp = new ArrayList<Player>();
				
				for (Player p : kfList) {
					if (!p.getId().equals(player.getId())) {
						temp.add(p);							
					}
					if (temp.size() >= 10) {
						break;
					}
				}
				result.setVO("kfList", temp);
				result.setStatus("list");
				//投票 
			} else if ("v".equals(param)) {
				int kfId = 0;
				try {
					kfId = Integer.parseInt(ps[1]);
				} catch (Exception e) {
				}
				ErrorMsg msg = voteKf(player, kfId);
				if (msg.code != ErrorCode.SUCC) {
					result.setVO(TAG_ERR_MSG, msg);
					result.setStatus(Command.STATUS_FAIL);
				} else {
					result.setStatus("note");
					result.setVO("desc", msg.getText());
				}
			} else if ("g".equals(param)) {
				Map<Integer, Gm> gmp = PlayerMG.instance.getGMMap();
				List<Player> onlineGmList = new ArrayList<Player>();
				for (Map.Entry<Integer, Gm> entry : gmp.entrySet()) {
					Gm g = entry.getValue();
					if (g != null && g.getIsJail()) {
						Player p = PlayerMG.instance.getOnlinePlayer(g.getPlayerId());
						if (p != null && !p.getId().equals(player.getId())) {
							onlineGmList.add(p);
						}
					}
				}
				result.setVO("kfList", onlineGmList);
				result.setStatus("list");
			}else {
				result.setVO(TAG_ERR_MSG, new ErrorMsg(ErrorCode.ERR_SYS, "参数错误"));
				result.setStatus(Command.STATUS_FAIL);
			}
		}
		return result;
	}

	private ErrorMsg becomeKf(Player player) {
		ErrorMsg msg = new ErrorMsg(ErrorCode.SUCC,"");
		PlayerKf kf = player.getKf();
		kf = createKf(player);
		player.setKf(kf);
		DBService.commit(kf);
		msg.setText("您已经成功成为了游戏解答员");
		return msg;
	}
	
	private PlayerKf createKf(Player player) {
		PlayerKf kf = new PlayerKf();
		kf.setId(GlobalGenerator.instance.getIdForNewObj(kf));
		kf.setPlayerId(player.getId());
		kf.setPlayerName(player.getName());
		kf.setCreateDate(new Date());
		kf.setHonorValue(0);
		kf.setTotalSeverTimes(0);
		return kf;
	}
	
	private ErrorMsg cancelKf(Player player) {
		ErrorMsg msg = new ErrorMsg(ErrorCode.SUCC,"");
		PlayerKf kf = player.getKf();
		player.setKf(null);
		DBService.commitDelete(kf);
		msg.setText("您已经成功退出了游戏解答");
		return msg;
	}
	
	@SuppressWarnings("unchecked")
    private List<Player> topKf(Player player) {
		
		List<Player> kfList = getKfList(player);
		Collections.sort(kfList, new Comparator() {
            public int compare(Object o1, Object o2) {
				Player p1 = (Player)o1;
				Player p2 = (Player)o2;
				if (p1.getKf().getHonorValue() > p2.getKf().getHonorValue()) {
					return -1;
				} else {
					return 1;					
				}
            }
		});
		List<Player> temp = new ArrayList<Player>();
		if (kfList.size() > 20) {
			//kfList = kfList.subList(0, 20);
			for (Player p : kfList) {
				temp.add(p);
				if (temp.size() >= 20) {
					break;
				}
			}
			return temp;
		} 
		return kfList;
	}
	
	//缓存，1分钟刷新一次
	private static List<Player> kfPlayerList = new ArrayList<Player>();
	private static long lastUpdateTime = 0;
	private static int interval = 5 * 60 * 1000;//five minutes.
	
	private synchronized List<Player> getKfList(Player player) {
		
		long now = System.currentTimeMillis();
		if (kfPlayerList.size() >= 20 && lastUpdateTime != 0 && now - lastUpdateTime < interval) {
			return kfPlayerList;
		}
		kfPlayerList.clear();
		Map<Integer, Player> playerMap = PlayerMG.instance.getPlayerMap();
		//List<Player> kfList = new ArrayList<Player>();
		for (Map.Entry<Integer, Player> entry : playerMap.entrySet()) {
			Player p = entry.getValue();
			
			if (p.isOnline() && p.getKf() != null) {
				kfPlayerList.add(p);
			}
		}
		lastUpdateTime = System.currentTimeMillis();
		return kfPlayerList;
	}
	
	private ErrorMsg voteKf(Player player, int kfId) {
		ErrorMsg msg = new ErrorMsg(ErrorCode.SUCC,"");
		Player pkf = PlayerMG.instance.getOnlinePlayer(kfId);
		if (pkf == null || pkf.isOffline()) {
			msg.setCode(ErrorCode.ERR_NOT_ONLINE);
			msg.setText("对方不在线，不可以被投票");
			return msg;
		}
		PlayerKf kf = pkf.getKf();
		if (kf == null) {
			msg.setCode(ErrorCode.ERR_KF_NOT_EXIST);
			msg.setText("对方不是解答员，不可以被投票 ");
			return msg;
		}
		boolean flag = player.isVoteAble();
		if (flag) {
			kf.setHonorValue(kf.getHonorValue() + 1);
			DBService.commitNoCacheUpdate(kf);
			player.setLastVoteDate(Calendar.getInstance());
			player.setVote_times(player.getVote_times() + 1);
			msg.setText("投票成功，感谢您的参与");
			return msg;
		} else {
			msg.setCode(ErrorCode.ERR_KF_NONE_VOTE);
			msg.setText("您今天的投票次数已经用完，欢迎明天再来");
			return msg;
		}
		
	}
}
