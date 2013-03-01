package com.ppsea.ds.command;
import com.ppsea.ds.exception.PpseaException;

/**
 * ClassName:Command
 *
 * @author   Daniel.Hao
 * @version  
 * @since    Ver 1.1
 * @Date	 2008	Nov 23, 2008		6:56:39 PM
 *
 * @see 	 
 */
public interface Command{
	static final long ONE_MINUTE = 60 * 1000;
	
	//Command short name
	public static final String COMMAND_WALK  = "mv_W";
	public static final String COMMAND_SECT_LIST  = "se_L";
	public static final String COMMAND_TONG_MY  = "t_M";
	public static final String COMMAND_PLAYER_ITEM_LIST  = "t_PIL";
	public static final String COMMAND_DEPOT_ITEM_LIST  = "t_DIL";
	public static final String COMMAND_MANAGE_ASKFOR  = "t_MAs";
	public static final String COMMAND_MANAGE_NOTICE  = "t_MN";
	public static final String COMMAND_MANAGE_ALLY  = "t_MAl";
	public static final String COMMAND_MANAGE_AUDITING  = "t_MAu";
	public static final String COMMAND_MANAGE_PLAYER  = "t_MP";
	public static final String COMMAND_MISSION_FAST  = "m_F";
	public static final String COMMAND_TALK  = "p_T";
	public static final String COMMAND_LEAVE = "mv_L";
	public static final String COMMAND_FIGHT_GROUND = "fc_G";
	public static final String COMMAND_ITEM_GIVE_SHOW = "i_SG";
	public static final String COMMAND_ITEM_GIVE = "i_SGI";
	public static final String COMMAND_ITEM_GIVE_TO = "i_G";
	public static final String COMMAND_WAR_GROUND = "mc_WG";
	public static final String COMMAND_MOVE_GO = "mv_G";
	public static final String COMMAND_INSTANCE_ENTER = "in_E";
	
	//STATUS
	public static final String STATUS_SUCC  = "succ";
	public static final String STATUS_FAIL  = "fail";
	public static final String STATUS_FULL  = "full";
	public static final String STATUS_FATAL = "fatal";
	public static final String STATUS_BM_ERR = "bm_err";
	public static final String STATUS_NO_MONEY = "no_money";
	public static final String STATUS_LOGIN_FAIL = "login_fail";
	public static final String STATUS_PAY_FAIL = "pay_fail";
	public static final String STATUS_TYPE 	= "type";
	public static final String STATUS_INDEX = "index";
	public static final String STATUS_LIST	= "list";
	public static final String STATUS_INFO	= "info";
	public static final String STATUS_CREATE  = "create";
	public static final String STATUS_EDIT  = "edit";
	public static final String STATUS_UP  = "up";
	public static final String STATUS_CONFIRM  = "confirm";
	public static final String STATUS_COACH_MAN  = "stat_coach_man";
	public static final String STATUS_STUDENT_MAN  = "stat_student_man";
	public static final String STATUS_COACH_IN  = "stat_coach_in";
	public static final String STATUS_STUDENT_IN  = "stat_student_in";
	public static final String STATUS_IN_WARAREA  = "in_wararea";
	public static final String STATUS_MATCH_END  = "match_end";
	public static final String STATUS_PLAYER_UPGRADE  = "player_upgrade";
	public static final String STATUS_PLAYER_ONLINE_GIVING  = "online_give";
	
	//TAG
	public static final String TAG_UID  = "uid";
	public static final String TAG_sSID  = "sSid";
	public static final String TAG_SID  = "sid";
	public static final String TAG_PID  = "pid";
	public static final String TAG_ZONES  = "zones";
//	public static final String TAG_USIGN  = "usign";
//	public static final String TAG_SIGN  = "sign";
//	public static final String TAG_ERR_CODE  = "err_code";
	public static final String TAG_ERR_MSG  = "err_msg";
	public static final String TAG_MY_PLAYERS = "my_players";
	public static final String TAG_PLAYER  = "player";
	public static final String TAG_ENEMY  = "enemy";
	public static final String TAG_PLAYER_LEVEL  = "player_level";
	public static final String TAG_PLAYERS = "players";
	public static final String TAG_OTHER = "other";
	public static final String TAG_PASSWD = "passwd";
	public static final String TAG_NPC_ID  = "npc_id";
	
	public static final String TAG_DESC  = "desc";
	public static final String TAG_TOTAL_PAGE  = "total_page";
	public static final String TAG_PAGE  = "page";
	public static final String TAG_PAGE_OBJS  = "page_objs";
	public static final String TAG_TYPE  = "type";
	public static final String TAG_USERNAME  = "username";
	public static final String TAG_LINKID  = "linkid";
	public static final String TAG_GOLD  = "gold";
	public static final String TAG_BOOK_MARK = "book_mark";
	
	//Friend
	public static final String TAG_ONLINE_FRIEND = "online_friend";
	public static final String TAG_TOTAL_FRIEND = "total_friend";
	public static final String TAG_ONLINE_INTIMATE = "online_intimate";
	public static final String TAG_TOTAL_INTIMATE = "total_intimate";
	public static final String TAG_ALL_BLACKLIST = "all_blacklist";
	public static final String TAG_SHORTCUT = "shortcut";
	public static final String TAG_CURR_JX = "curr_jx";
	public static final String TAG_CURR_AQ = "curr_aq";
	//Sect
	public static final String TAG_SECTS  = "sects";
	public static final String TAG_SECT  = "sect";
	public static final String TAG_SECT_ID  = "sect_id";
	public static final String TAG_SECT_SKILLS  = "sect_skills";
	public static final String TAG_SECT_ADVANCED_SKILLS  = "sect_advanced_skills";
	public static final String TAG_SECT_SKILL  = "sect_skill";
	public static final String TAG_SECT_NEXT_SKILL  = "sect_next_skill";
	public static final String TAG_SECT_CON_SKILL  = "sect_con_skill";
	public static final String TAG_SECT_SKILL_XF  = "sect_skill_xf";
	public static final String TAG_SECT_SKILL_ZS  = "sect_skill_zs";
	public static final String TAG_SECT_SKILL_JX  = "sect_skill_jx";
	public static final String TAG_SECT_USEABLE_SKILLS  = "sect_useable_skills";
	public static final String TAG_SECT_USED_SKILLS  = "sect_used_skills";
	public static final String TAG_PLAYER_SKILL  = "player_skill";
	public static final String TAG_PLAYER_SKILLS = "player_skills";
	public static final String TAG_MATCH_TYPE = "match_type";
	public static final String TAG_SECT_HONOR = "sect_honor";
	
	public static final String TAG_LOCATION  = "location";
	public static final String TAG_FACILITY_DESC  = "facility_desc";
	public static final String TAG_CITY_FACILITY_ID  = "city_facility_id";
	public static final String TAG_FACILITY_ID  = "facility_id";
	public static final String TAG_DIRECT  = "direct";
	public static final String TAG_IS_CLICKS  = "is_clicks";
	public static final String TAG_NPC  = "npc";
	public static final String TAG_LAST_NPC  = "last_npc";//steady
	public static final String TAG_CITIES  = "cities";
	public static final String TAG_CITY  = "city";
	public static final String TAG_AREAS  = "areas";
	public static final String TAG_AREA = "area";
	public static final String TAG_FACILITY_PALYER = "facility_players";
	public static final String TAG_CURR_FACILITY = "curr_facility";
	public static final String TAG_DISTANCE = "distance";
	public static final String TAG_DISTANCE_LEFT = "distance_left";
	public static final String TAG_FLAG = "flag";
	public static final String TAG_MESSAGE_TYPE = "msg_type";
	public static final String TAG_CHAT_STATUS = "chat_status";
	public static final String TAG_RECEIVER = "receiver";
	
	//marry
	public static final String TAG_MARRY_CONG_NUM	= "marry_cong_num";
	public static final String TAG_MARRY_CONG_INFO = "marry_cong_info";
	public static final String TAG_MARRY_CONG_CONT = "marry_cong_cont";
	public static final String TAG_MARRY_ING_LIST = "marry_ing_list";
	public static final String TAG_FRIEND			= "friend";
	public static final String TAG_MARRY_DATE		= "marry_date";
	public static final String TAG_FRIEND_MATE	= "friend_mate";
	public static final String TAG_MATCH_INTIMATE	= "match_intimate";
	
	public static final String STATUS_DECLARE_LIST 	= "declare_list";
	public static final String STATUS_DECLARE_WAIT 	= "declare_wait";
	public static final String STATUS_DECLARE_PROC 	= "declare_proc";
	public static final String STATUS_MARRY_START 	= "marry_start";
	public static final String STATUS_MARRY_ING 	= "marry_ing";
	public static final String STATUS_MARRY_DONE 	= "marry_done";
	public static final String STATUS_DIVORCE_PROC  = "divorce_proc";
	public static final String STATUS_CONG_CONTENT  = "cong_content";

	//fight & fightcity
	public static final String STATUS_IN_BATTLE 	= "in_battle";
	public static final String STATUS_IN_PKAREA 	= "in_pkarea";
	public static final String STATUS_BATTLE_END 	= "battle_end";
	public static final String STATUS_PKAREA_END 	= "pkarea_end";
	public static final String STATUS_WAR_END 		= "war_end";
	public static final String STATUS_FIGHT_MEND 	= "fight_mend";
	public static final String STATUS_FIGHT_PEND 	= "fight_pend";
	public static final String STATUS_FIGHT_WIN 	= "fight_win";
	public static final String STATUS_FIGHT_LOSE 	= "fight_lose";
	public static final String STATUS_FIGHT_SUCC 	= "fight_succ";
	public static final String STATUS_SET_SHORTCUT 	= "set_shortcut";
	public static final String STATUS_IN_FIGHT   	= "in_fight";
	public static final String STATUS_IN_FIGHT_P   	= "in_fight_p";
	public static final String STATUS_IN_FIGHT_M 	= "in_fight_m";	
	public static final String STATUS_NO_HP 		= "no_hp";
	public static final String STATUS_INSTANCE_FAIL = "instance_fail";
	public static final String STATUS_USE_ITEM 		= "use_item";
	public static final String TAG_RANDOM 		   = "random";
	public static final String TAG_FIGHT_ATK_NUM   = "fight_atk_num";
	public static final String TAG_TONG_ATK		   = "tong_atk";
	public static final String TAG_TONG_DEF        = "tong_def";
	public static final String TAG_FIGHT_DEF_NUM   = "fight_def_num";
	public static final String TAG_FIGHT_ENMY_NUM  = "fight_enmy_num";
	public static final String TAG_FIGHT_SELF_NUM  = "fight_self_num";
	public static final String TAG_FIGHT_LEFT_TIME = "fight_left_time";
	public static final String TAG_BATTLE_WIN_TONG = "battle_win_tong";
	public static final String TAG_BATTLE_LIFE_NUM = "battle_life_num";
	public static final String TAG_BATTLE_DEAD_NUM = "battle_dead_num";
	public static final String TAG_BATTLE_AWAY_NUM = "battle_away_num";
	public static final String TAG_BATTLE_KILL_NUM = "battle_kill_num";
	public static final String TAG_BATTLE_TAX_TIME = "battle_tax_time";
	public static final String TAG_FIGHT_MONSTER = "fight_monster";
	public static final String TAG_ITEM_ADD_HP  = "item_add_hp";
	public static final String TAG_ITEM_ADD_MP  = "item_add_mp";
	public static final String TAG_IS_FACILITY  = "is_facility";
	public static final String TAG_ENEMY_NAME   = "enemy_name";
	public static final String TAG_AUTO_HP		= "auto_hp";
	public static final String TAG_AUTO_ENDURE	= "autoAddEndure";
	public static final String TAG_AUTO_MP		= "auto_mp";
	public static final String TAG_AUTO_STATE	= "auto_state";
	public static final String TAG_PLAYER_STAT  = "player_stat";
	public static final String TAG_MONSTER      = "monster";
	public static final String TAG_MONSTER_NAME = "monster_name";
	public static final String TAG_MONSTER_PLAYER = "monster_player";
	public static final String TAG_MONSTER_BEG  = "monster_beg";
	public static final String TAG_MONSTER_ABORT= "monster_abort";
	public static final String TAG_WASTE_ITEMS	= "waste_items";
	public static final String TAG_ATK_TO 		= "atk_to";
	public static final String TAG_ATK_TO_DESC	= "atk_to_desc";
	public static final String TAG_BE_ATK_DESC	= "be_atk_desc";
	public static final String TAG_ATK_FROM 	= "atk_from";
	public static final String TAG_KILLED 		= "killed";
	public static final String TAG_PET_AUTO_HP	= "pet_auto_hp";
	public static final String TAG_PET_STAT		= "pet_stat";
	public static final String TAG_BE_KILLED 	= "be_killed";
	public static final String TAG_ENEMY_TYPE	= "enemy_type";
	public static final String TAG_WIN_EXP		= "win_exp";
	public static final String TAG_WIN_EVIL     = "win_evil";
	public static final String TAG_WIN_MONEY	= "win_money";
	public static final String TAG_WIN_TALENT	= "win_talent";
	public static final String TAG_WIN_SHIP		= "win_ship";
	public static final String TAG_WIN_PRIZE	= "win_prize";
	public static final String TAG_PET_ID		= "pet_id";
	public static final String TAG_TALENT_ID	= "talent_id";
	public static final String TAG_AUTO_PICK	= "auto_pick";
	public static final String TAG_LOST_ITEMS	= "lost_items";
	public static final String TAG_LOST_SHIPS	= "lost_ships";
	public static final String TAG_LOST_SUPPLY	= "lost_supply";	
	public static final String TAG_LOST_MONEY	= "lost_money";
	public static final String TAG_LOST_MORALE	= "lost_morale";
	public static final String TAG_RECOVER_ID	= "recover_id";
	public static final String TAG_DEC_HP_SUM   = "dec_hp_sum";
	public static final String TAG_DEC_MP_SUM   = "dec_mp_sum";
	public static final String TAG_FIGHT_MSG 	= "fight_msg";
	public static final String TAG_STATUS 		= "status";
	public static final String TAG_ACTION 		= "action";
	public static final String TAG_TONG_ADMIN   = "tong_admin";
	public static final String TAG_CITY_TONG   = "city_tong";
	public static final String TAG_CITY_TAX   = "city_tax";
	public static final String TAG_CITY_GUARD = "city_guard";
	public static final String TAG_FIGHT_ENEMY = "fight_enemy";
	public static final String TAG_LEFT_ENEMY = "left_enemy";
	public static final String TAG_TOTAL_ENEMY = "total_enemy";
	public static final String TAG_QUESTION = "question";
	public static final String TAG_QUESTIONLIST = "questionlist";
	
	
	//steady
	public static final String TAG_RETURN  = "return";
	public static final String TAG_CODE = "code";
	
	// 道具
	public static final String TAG_ITEMS  = "items";
	public static final String TAG_ITEM  = "item";
	public static final String TAG_PLAYER_ITEM  = "player_item";	
	public static final String TAG_PLAYER_ITEMS  = "player_items";	
	public static final String TAG_ITEM_PROPERTY  = "item_property";
	public static final String TAG_OBJS  = "objs";
	public static final String TAG_OBJ  = "obj";
	public static final String TAG_COPPER  = "copper";
	public static final String TAG_MATERIAL_REQUIRE  = "material_requirement";
	public static final String TAG_RETURN_TYPE  = "return_type";
	public static final String TAG_NO_BUY  = "no_buy";
	public static final String TAG_NO_OP  = "no_op";
	public static final String TAG_REDIRECT  = "redirect";
	
	public static final String TAG_MISSIONS = "missions";
	public static final String TAG_AMOUNT = "amount";
	public static final String TAG_MISSION_ID = "mission_id";
	public static final String TAG_MISSION = "mission";
	public static final String TAG_CITY_FACILITY = "city_facility";
	
	public static final String TAG_DIALOG = "dialog";
	public static final String TAG_WIN_UPGRADE = "win_upgrade";
	public static final String TAG_CUSTOM = "custom";
	public static final String TAG_ITEM_SUIT_LIST = "itemSuitList";
	public static final String TAG_ITEM_SUIT = "itemSuit";
	
	// 帮会
	public static final String TAG_TONG = "tong";
	public static final String TAG_TONGS = "tongs";
	public static final String TAG_TONG_LEVEL = "tong_level";
	public static final String TAG_TONG_DEPOT = "tong_depot";
	public static final String TAG_TONG_PLAYER = "tong_player";
	public static final String TAG_TONG_CITIES = "tong_cities";
	public static final String TAG_TAGET_TONG_PLAYER = "target_tong_player";
	public static final String TAG_TOTAL_MARK = "total_mark";
	public static final String TAG_PLAYER_RANK = "player_rank";
	public static final String TAG_TOTAL_LOGS = "total_logs";
	public static final String TAG_TONG_TITLE = "tong_title";
	public static final String TAG_TONG_NOTICE = "tong_notice";
	
	// 商城
	public static final String TAG_TODAY_SILVER = "today_silver";
	public static final String TAG_GOODS = "goods";
	public static final String TAG_NUM = "num";
	
	// 宠物
	public static final String TAG_PET = "pet";
	public static final String TAG_PET_SAY = "pet_say";
	public static final String TAG_PLAYER_PET = "player_pet";
	public static final String TAG_PET_LIST = "pet_list";
	public static final String TAG_PET_EGG = "pet_egg";
	public static final String TAG_PET_TALENT = "pet_talent";
	public static final String TAG_PET_FOOD = "pet_food";
	
	// 资源库
	public static final String TAG_NOTICES = "notices";
	public static final String TAG_NOTICE = "notice";
	
	//师徒
	public static final String TAG_COACHES = "coaches";
	public static final String TAG_COACH = "coach";
	public static final String TAG_STUDENTS = "students";	
	public static final String TAG_STUDENT = "student";
	public static final String TAG_STUDENT_ID = "student_id";
	public static final String TAG_ACK_STUDENTS = "ack_students";
	public static final String TAG_WAIT_STUDENTS = "wait_students";
	
	//队伍
	public static final String TAG_TEAM = "team";
	public static final String TAG_TEAMS = "teams";
	
	// 排行
	public static final String TAG_RANKS = "ranks";
	//圣诞活动
	public static final String TAG_JING = "jing";
	public static final String TAG_JINQIAN = "jinqian";
	public static final String TAG_ZHUANGBAN = "zhuangban";
	//点球
	public static final String TAG_TIQIU_DIANQIU = "td";
	//错误
	public static final String TAG_ERROR = "error";
	public static final String TAG_NEWID="newid";
	
	//resell
	public static final String TAG_GOODS_VALUE = "goods_value";//货物总价值
	public static final String TAG_RESELL_GOLD = "resell_gold";//倒卖币
	public static final String TAG_REFRESH_LIMIT = "refresh_limit";//每日刷新最大次数限制
	public static final String TAG_REFRESH_REMAIN = "refresh_remain";//刷新剩余次数
	public static final String TAG_EXCHANGE_REMAIN_GOLD = "exchange_remain_gold";//今日剩余兑换游戏币
	public static final String TAG_GOODS_TYPE = "goods_type";
	public static final String TAG_OPT_TYPE = "opt_type";
	public static final String TAG_SELL_PRICE_VO = "sell_price_vo";
	/**
	 * Execute command
	 * 
	 * @param  @param data    
	 * @return void    
	 * @throws
	 */
	 public CommandResult execute(CommandRequest cmdReq);
	
	/**
	 * 执行cmd，同时校验是否要检查书签
	 * 
	 * @param cmdReq
	 * @param isCheckBookmark true 校验，false 不校验
	 * @return
	 * @throws PpseaException
	 */
	public CommandResult execute(CommandRequest cmdReq, boolean needVerify);
	
	/**
	 * Get player
	 * 
	 * @param  @param playerId
	 * @param  @return    
	 * @return Player    
	 * @throws
	 */
	//public Player getPlayer(String playerId);
}
