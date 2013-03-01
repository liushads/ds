package com.ppsea.ds.task;


/**
 * 副本运行，检查是否过期或者已被完成或者全部退出
 * 
 * @author daniel
 *
 */
public class InstanceTask {
	public static InstanceTask instance = null;
//	private Timer timer = null;
//	private static final Logger log = Logger.getLogger(InstanceTask.class);
	
	// 副本
//	private Map<Integer, Instance> map = new ConcurrentHashMap<Integer, Instance>();
	
	public static void init() {
		if(instance == null){
			instance = new InstanceTask();
		}
	}
	
//	public void start(){
//		timer = new Timer();
//
//		timer.schedule(new InstanceTimerTask(), 0L, 5 * 60 * 1000L);
//	}
	
//	private class InstanceTimerTask extends TimerTask{
//		
//		/**
//		 * (non-Javadoc)
//		 * @see java.util.TimerTask#run()
//		 */
//		@Override
//		public void run() {
//			long currentTime = new Date().getTime();
//			for (Map.Entry<Integer, Instance> entry : map.entrySet()) {
//				Integer key = entry.getKey();
//				Instance ins = entry.getValue();
//				log.info("check instance leaderId="+ins.leaderId);
//				
//				// 副本超时、无人在线或无队员
//				if(currentTime - ins.startTime > ins.mission.getConditionTimeLimit() * 60 * 1000 ||
//						ins.team.getMembers().size() == 0){
//					
//					log.info("remove instance leaderId="+ins.leaderId);
//					TeamMG.instance.removeTeam(ins.team.getId());
//					map.remove(key);
//					
//					// 移除申请队
//					if(ins.leaderId != null)
//						InstanceSet.removeTeam(ins.mission.getId(), ins.leaderId);
//					
//					// 将还在副本中的player放到广场
//					for(TeamPlayer tp : ins.team.getMembers()){
//						Player player = PlayerMG.instance.getPlayerSimple(tp.getPlayerId());
//						
//						if(player.isInInstacneCity()){
//							if(player.getInstanceEntrance() == 0){
//								City mainCity = MapMG.instance.getArea(player.getCity().getAreaId()).getMainCity();
//								PlayerService.move(player, mainCity.getSpecialFacilityMap().get(Constants.CENTER_ID).getId(), false);
//							}else{
//								PlayerService.move(player, player.getInstanceEntrance(), false);
//							}
//							player.setSpecialCity(false);
//							TeamService.quitTeam(player, true);
//							InstanceSet.removeFromApplyTeam(player);
//						}
//						LoggerHelper.Fuben.logForFuBenNoneComplete(player, ins.mission.getId());
//					}
//				}else if(currentTime - ins.startTime > (ins.mission.getConditionTimeLimit() - 5) * 60 * 1000){
//					for(TeamPlayer tp : ins.team.getMembers()){
//						Player player = PlayerMG.instance.getPlayerSimple(tp.getPlayerId());
//						
//						if(player.isInInstacneCity()){
//							ChatService.sendMessageSystem(player.getId(), "注意，副本任务5分钟内将结束！", false);
//						}
//					}
//				}
//			}
//		}
//	}
	
	/**
	 * 副本实例
	 */
//	private class Instance {
////		// 任务
////		private Mission mission = null;
////		
////		// 开始时间
////		private long startTime = 0;
////		
////		
////		// 申请队的队ID，为创建者PlayerID
////		private Integer leaderId = null;
//	}
}
