package com.ppsea.ds.service;

import java.util.List;
import java.util.Map;

import com.ppsea.ds.data.ErrorMsg;
import com.ppsea.ds.data.GlobalGenerator;
import com.ppsea.ds.data.ResourceCache;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.data.model.PlayerSkill;
import com.ppsea.ds.data.model.Skill;

public class SkillService {
	
	/**
	 * 学习技能
	 * @param player
	 * @param skill_id
	 * @return
	 */
	public static ErrorMsg learnSkill(Player player,int skill_id){
		//1、是否已经学过该技能
		Skill skill = getSkill(skill_id);
		if (skill == null) {
			return new ErrorMsg(-1, "学习的技能不存在");
		}
		Skill s1 = hasSkill(player, skill);
		if (s1 != null) {
			return new ErrorMsg(-1, "已经学习" + skill.getName() + "过该技能了，还是尝试升级吧！");
		}
		//2、技能等级判断、疲劳值
		ErrorMsg msg = checkPlayer(player, skill);
		if (ErrorCode.SUCC != msg.getCode()) {
			return msg;
		}
		//4、学习
		player.consumEternal(skill.getLimitEternal());
		PlayerSkill ps = createPlayerSkill(player, skill);
		player.getPlayerSkill().put(ps.getSkill().getSkillType(), ps);
		return new ErrorMsg(ErrorCode.SUCC, ps);
	}
	
	private static PlayerSkill createPlayerSkill(Player player,Skill skill){
		PlayerSkill ps = new PlayerSkill();
		ps.setId(GlobalGenerator.instance.getIdForNewObj(ps));
		ps.setPlayerId(player.getId());
		ps.setSkillId(skill.getId());
		ps.setSkill(skill);
		DBService.commit(ps);
		return ps;
	}
	
	/**
	 * 升级技能
	 * @param player
	 * @param skill_type
	 * @return
	 */
	public static ErrorMsg upgredSkill(Player player,int skill_type){
		//1、是否已经学过该技能
		PlayerSkill ps = getPlayerSkill(player, skill_type);
		if (ps == null) {
			return new ErrorMsg(-1, "没有学习过该技能，还是先学习了再来升级吧！");
		}
		//2、是否可以继续学
		Skill nextSkill = getNextLevel(ps.getSkill());
		if (nextSkill == null) {
			return new ErrorMsg(-1, "已经是最高等级了，不用再学了！");
		}
		//3、技能等级判断、疲劳值
		ErrorMsg msg = checkPlayer(player, nextSkill);
		if (msg.getCode() != ErrorCode.SUCC) {
			return msg;
		}
		//4、升级
		ps.setSkill(nextSkill);
		DBService.commit(ps);
		player.getPlayerSkill().put(ps.getSkill().getSkillType(), ps);
		return new ErrorMsg(ErrorCode.SUCC, ps);
	}
	
	/**
	 * 检查玩家等级、疲劳值是否够
	 * @param player
	 * @param skill
	 * @return
	 */
	private static ErrorMsg checkPlayer(Player player ,Skill skill){
		if (player.getEternal() < skill.getLimitEternal()) {
			return new ErrorMsg(-1, "武魂值不足！");
		}
		if (player.getLevel() < skill.getLimitLevel()) {
			return new ErrorMsg(-1, "等级不够");
		}
		return new ErrorMsg(ErrorCode.SUCC, null);
	}
	private static Skill hasSkill(Player player,Skill skill){
		if(skill == null){
			return null;
		}
		PlayerSkill ps = getPlayerSkill(player, skill.getSkillType());
		if (ps == null) {
			return null;
		}
		return ps.getSkill();
	}
	
	/**
	 * 根id获取技能
	 * @param id
	 * @return
	 */
	public static Skill getSkill(int id){
		Map<Integer, Map<Integer, Skill>> skills = ResourceCache.instance.getSkills();
		for (Map<Integer, Skill> sk : skills.values()) {
			if (sk.get(id) != null) {
				return sk.get(id);
			}
		}
		return null;
	}
	
	private static PlayerSkill getPlayerSkill(Player player,int skill_type){
		if (player.getPlayerSkill() != null) {
			for (PlayerSkill ps : player.getPlayerSkill().values()) {
				if (ps.getSkill().getSkillType().intValue() == skill_type) {
					return ps;
				}
			}
		}
		return null;
	}
	
	private static Skill getNextLevel(Skill skill){
		if (skill == null) {
			return null;
		}
		Map<Integer, Skill> maps = ResourceCache.instance.getSkills().get(skill.getSkillType());
		if (maps != null && maps.size() > 0) {
			for (Skill s : maps.values()) {
				if (s.getLevel() == skill.getLevel() + 1) {
					return s;
				}
			}
		}
		return null;
	}
	
	/**
	 * 根据想要得到的加成获取技能增加
	 * @param player
	 * @param skill_type
	 * @param buff_type
	 * @return
	 */
	public static int getPlayerSkillBuf(Player player,int skill_type,int buff_type){
		PlayerSkill ps = getPlayerSkill(player, skill_type);
		if (ps != null) {
			switch (buff_type) {
			case 1://攻击
				ps.getSkill().getAttack();
				break;
			case 2://防御
				ps.getSkill().getDefence();
				break;
			case 3://体力
				ps.getSkill().getHp();
				break;
			case 4://暴击
				ps.getSkill().getCrit();
				break;
			case 5://格挡
				ps.getSkill().getParry();
				break;
			case 6://命中
				ps.getSkill().getHit();
				break;
			case 7://闪避
				ps.getSkill().getSidestep();
				break;
			}
		}
		return 0;
	}
	
	/**
	 * 根据玩家是否学习过技能来获取
	 * @param player
	 * @param learn
	 * @param no_learn
	 */
	public static void getAllSkillType(Player player,List<Skill> learn,List<Skill> no_learn){
		Map<Integer, Map<Integer, Skill>> skills = ResourceCache.instance.getSkills();
		PlayerSkill ps = null;
		for (Map<Integer, Skill> sk : skills.values()) {
			for (Skill s : sk.values()) {
				ps = getPlayerSkill(player, s.getSkillType());
				if (ps != null) {
//					learn.add(ps.getSkill());
					continue;
				}
				if (s.getLevel() == 1 && player.getLevel() >= s.getLimitLevel()) {
					no_learn.add(s);
					continue;
				}
				
			}
		}
		if (player.getPlayerSkill().size() > 0) {
			for (PlayerSkill p : player.getPlayerSkill().values()) {
				learn.add(p.getSkill());
			}
		}
	}
	
}
