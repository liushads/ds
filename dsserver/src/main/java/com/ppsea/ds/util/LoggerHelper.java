/*
 * 
 */
package com.ppsea.ds.util;

import org.apache.log4j.Logger;

import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.data.model.PlayerMission;

/**
 * @author Administrator
 * @date 2010-9-6
 */
public class LoggerHelper {
	

	public static final String GAME_ID = "127";
	
	public static final String ZONE_ID = GlobalConfig.zone.attributeValue("id");
	
	public static final String LINE = "|";
	
	/**
	 * 
	 * @author Administrator
	 * @date 2010-9-6
	 */
	public static class Mission {
		
		private static Logger missionLogger = Logger.getLogger("Mission");
		
		public static void logForAcceptMission(Player player, PlayerMission pm) {
			loggerForMission(player, pm, "accept", 0);
		}
		
		public static void logForCompleteMission(Player player, PlayerMission pm) {
			loggerForMission(player, pm, "complete", pm.getEnd() - pm.getStart());
		}
		
		private static void loggerForMission(Player player, PlayerMission pm, String type, long time) {
			try {
				String content = GAME_ID + LINE;
				content += player.getUserId() + LINE;
				content += ZONE_ID + LINE;
				content += player.getName() + LINE;
				content += (player.getSex()==1?"F":"M") + LINE;
				content += player.getSectId() + LINE;
				content += pm.getMissionId() + LINE;
				content += type + LINE;
				content += time;
				missionLogger.info(content);
			} catch (Exception e) {
			}
		}
	}
	
	/**
	 * 
	 * @author Administrator
	 * @date 2010-9-6
	 */
	public static class PlayerRole {
		
		private static Logger roleLogger = Logger.getLogger("PlayerRole");
		
		public static void logForRoleCreation(Player player) {
			try {
				String content = GAME_ID + LINE;
				content += player.getUserId() + LINE;
				content += ZONE_ID + LINE;
				content += player.getName() + LINE;
				content += (player.getSex()==1?"F":"M") + LINE;
				content += player.getSectId();
				roleLogger.info(content);
			} catch (Exception e) {
				
			}

		}
	}
	
	/**
	 * 
	 * @author Administrator
	 * @date 2010-9-6
	 */
	public static class RelationShip {
		
		private static Logger roleLogger = Logger.getLogger("RelationShip");
		
		private static String SHIP_TYPE_FRIEND = "11";
		
		public static void logForFriendCreation(Player player, int id, int size) {
			logFor(player, "create", SHIP_TYPE_FRIEND, id, size);
		}
		
		public static void logForFriendDismiss(Player player, int id, int size) {
			logFor(player, "dismiss", SHIP_TYPE_FRIEND, id, size);
		}
		
		private static void logFor(Player player, String type, String shipType, int id, int size) {
			try {
				String content = GAME_ID + LINE;
				content += player.getUserId() + LINE;
				content += ZONE_ID + LINE;
				content += player.getName() + LINE;
				content += type + LINE;
				content += "" + LINE;
				content += shipType + LINE;
				content += id + LINE;
				content += size;
				roleLogger.info(content);
			} catch (Exception e) {
				
			}
		}
	}
	
//	public static class PlayerLevelUpgrade {
//		
//		private static Logger roleLogger = Logger.getLogger("PlayerUpgrade");
//		
//		public static void logForPlayerUpgrade(Player player) {
//			try {
//				String content = GAME_ID + LINE;
//				content += player.getUserId() + LINE;
//				content += ZONE_ID + LINE;
//				content += player.getName() + LINE;
//				content += (player.getLevel() - 1) + LINE;
//				content += player.getLevel() + LINE;
//				if (player.getLastUpgradeTime() == null) {
//					content += 0;
//				} else {
//					content += (System.currentTimeMillis()/1000 - player.getLastUpgradeTime().getTime()/1000);					
//				}
//				roleLogger.info(content);
//			} catch (Exception e) {
//				
//			}
//		}
//	}
	
	public static class Exchange {
		
		private static Logger roleLogger = Logger.getLogger("Exchange");
		
		public static void logForBuyItem(String userId, String sellerUid, int itemId, float price, int num) {
			try {
				String content = GAME_ID + LINE;
				content += itemId + LINE;
				content += userId + LINE;
				content += sellerUid + LINE;
				content += price + LINE;
				content += num;
				roleLogger.info(content);
			} catch (Exception e) {
				
			}
		}
	}
	
	public static class RoleStatus {
		
		private static Logger roleLogger = Logger.getLogger("RoleStatus");
		
		public static void logForRoleDead(Player player) {
			try {
				String content = GAME_ID + LINE;
				content += player.getUserId() + LINE;
				content += ZONE_ID + LINE;
				content += player.getName() + LINE;
				content += "2" + LINE;//复活
				content += "1";//死亡
				roleLogger.info(content);
			} catch (Exception e) {
				
			}
		}
	}
	
//	public static class Fuben {
//		private static Logger roleLogger = Logger.getLogger("Fuben");
//		
//		public static void logForFuBenComplete(Player player, int fuBenId) {
//			logForFuBen(player, "muilt", fuBenId, "Y");
//		}
//		
//		public static void logForFuBenNoneComplete(Player player, int fuBenId) {
//			logForFuBen(player, "muilt", fuBenId, "N");
//		}
//		
//		private static void logForFuBen(Player player,String type, int fuBenId, String flag) {
//			try {
//				String content = GAME_ID + LINE;
//				content += type + LINE;
//				content += player.getTeamId() + LINE;
//				content += ZONE_ID + LINE;
//				content += player.getName() + LINE;
//				content += fuBenId + LINE;
//				content += flag;
//				roleLogger.info(content);
//			} catch (Exception e) {
//				
//			}
//		}
//	}
}
