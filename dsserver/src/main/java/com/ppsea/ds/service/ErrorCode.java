package com.ppsea.ds.service;

import com.ppsea.ds.data.ErrorMsg;

public class ErrorCode {
	public static int SUCC = 0;
	public static int SUCC_GO = 1;
	public static int SUCC_CITY = 2;
	public static int SUCC_END = 3;

	
	//系统错误  (-1) --- (-100)
	public static int ERR_SYS = -1;
	public static int ERR_SYS_STOP = -2;
	public static int ERR_TERMINATED = -3;
	
	public static int ERR_NAME_INVALID = -4;
	public static int ERR_DIRTY_WORD  = -5;
	public static int ERR_PLAYER_IN_USER_LIMIT  = -6;
	public static int ERR_NAME_EXIST = -7;
	
	public static int ERR_SECT_JOINED = -8; //已经加入过门派了
	public static int ERR_INVALID_CITY = -9; //非法目的地
	public static int ERR_HAD_ARRVIED = -10; //已经到达目的地
	public static int ERR_FREQUENCE = -11;
	
	public static int ERR_USER_NO_EXIST = -18; //用户不存在
	public static final int ERR_NOT_ENEMY = -19; //不是仇家
	public static final int ERR_PAY = -20; //支付错误
	public static final int ERR_PAY_SMS = -22; //短信支付错误
	public static int ERR_CMD = -21;
	
	// 道具
	public static int ITEM_ERR_BASE = -400;
	public static int ERR_EX_IN = ITEM_ERR_BASE - 1; 					//道具在交易中,不能进行装备
	public static int ERR_EQUIP_FULL = ITEM_ERR_BASE - 2; 				//没有装备空间，需要先卸载装备
	public static int ERR_PLAYER_LEVEL = ITEM_ERR_BASE - 3; 			//用户等级不够
	public static int ERR_ENDURE = ITEM_ERR_BASE -4; 					//道具耐久为0
	public static int ERR_BIND = ITEM_ERR_BASE - 5; 					//绑定关系错误
	public static int ERR_NO_ITEM = ITEM_ERR_BASE - 6; 					//没有物品
	public static int ERR_ROOM_LIMIT = ITEM_ERR_BASE - 7; 				//物品栏空间不够
	public static int ERR_DROP = ITEM_ERR_BASE - 8; 					//不能丢弃
	public static int ERR_USING = ITEM_ERR_BASE - 9; 					//道具正在使用
	public static int ERR_ITEM_AMOUNT = ITEM_ERR_BASE - 10; 			//物品数量错误
	public static int ERR_HOLE_LIMIT= ITEM_ERR_BASE - 11; 				//打孔数量达到上限
	public static int ERR_LACK_MUST_ITEM = ITEM_ERR_BASE - 12; 			//缺少必需的材料
	public static int ERR_CAN_NOT_EMBED = ITEM_ERR_BASE - 13; 			//装备不能被镶嵌
	public static int ERR_NO_EMBED_HOLE = ITEM_ERR_BASE - 14; 			//没有镶嵌的孔
	public static int ERR_ITEM_FORGE_NO_EXISTS = ITEM_ERR_BASE - 15; 	//锻造的道具不存在
	public static int ERR_NO_MONEY = ITEM_ERR_BASE -16;					//钱不足
	public static int ERR_NOT_ENOUGH_ITEM = ITEM_ERR_BASE - 17; 		//材料数量不足
	public static int ERR_STAR_LIMIT = ITEM_ERR_BASE - 18; 				//升星达到上限
	public static int ERR_NO_PROPERTY = ITEM_ERR_BASE - 19; 			//没有相应的属性
	public static int ERR_IMPROVE_LIMIT = ITEM_ERR_BASE - 20; 			//强化达到上限
	public static int ERR_NO_PROMOTE_PARAM = ITEM_ERR_BASE - 21; 		//没有装备升星参数
	public static int ERR_NO_IMPROVE_PARAM = ITEM_ERR_BASE - 22; 		//没有装备强化参数
	public static int ERR_ITEM_IMPROVE_NO_EXISTS = ITEM_ERR_BASE - 23; 	//强化的道具不存在
	public static int ERR_ITEM_IMPROVE_FAIL = ITEM_ERR_BASE - 24; 		//强化装备失败
	public static int ERR_NOT_SPECIFY_TYPE = ITEM_ERR_BASE - 25; 		//不是指定的类型
	public static int ERR_ITEM_IN_USE = ITEM_ERR_BASE - 26; 			//装备正在使用
	public static int ERR_NO_ITEM_DECOMPOSE = ITEM_ERR_BASE - 27; 		//没有分解图
	public static int ERR_ITEM_DECOMPOSE_FAIL = ITEM_ERR_BASE - 28; 	//分解失败
	public static int ERR_ITEM_SWITH_DUPLICATE = ITEM_ERR_BASE - 29; 	//装备重复切换
	public static int ERR_IS_NOT_ARM = ITEM_ERR_BASE - 30; 				//不是装备类型
	public static int ERR_IS_NOT_USE = ITEM_ERR_BASE - 31; 				//没有使用装备
	public static int ERR_GROUP_USING = ITEM_ERR_BASE - 32;             //有同组道具正在使用中
	public static int ERR_ITEM_SUIT_AMOUT_LESS = ITEM_ERR_BASE - 33;    //组成套装数量不够.
	public static int ERR_ITEM_SUIT_DONE = ITEM_ERR_BASE - 34;          //已组成套装  
	public static int ERR_ITEM_SUIT_UNDEL = ITEM_ERR_BASE - 35;         //不能组套装.
	public static int ERR_ITEM_MAX_NUM = ITEM_ERR_BASE - 36;           	//超过最大数量.
	public static int ERR_NO_GUIDE_ITEM = ITEM_ERR_BASE - 37;           //没有遁地符
	public static int ERR_NOT_GIVE_ITEM = ITEM_ERR_BASE - 38;           //不可赠送
	public static int ERR_NOT_DELETE_ITEM = ITEM_ERR_BASE - 39;         //不可删除
	public static int ERR_CAN_NOT_DROP = ITEM_ERR_BASE - 40;           	//不可丢弃
	public static int ERR_IN_EXCHANGE = ITEM_ERR_BASE - 41;           	//物品正在交易
	public static int ERR_ITEM_EXIST = ITEM_ERR_BASE - 42;           	//物品重复
	public static int ERR_NO_SCROLL = ITEM_ERR_BASE - 43; 				//没有鉴定卷轴


	// 常用
	public static int COMMON_ERR_BASE = -100;
	public static int ERR_INPUT_NUM = COMMON_ERR_BASE -1;
	public static int ERR_NOT_ENOUGH_MONEY = COMMON_ERR_BASE -2;
	public static int ERR_NOT_ONLINE = COMMON_ERR_BASE -3;	 		//玩家不在线
	public static int ERR_PASSWD = COMMON_ERR_BASE -4;	 			//密码错误
	public static int ERR_NO_PLAYER = COMMON_ERR_BASE -5;	 		//没有玩家
	public static int ERR_NO_PRIVILIGE = COMMON_ERR_BASE -6;	 	//没有权限
	public static int ERR_OVER_MAX_NUM = COMMON_ERR_BASE -7;	 	//超过最大数
	public static int ERR_NOT_IN_CITY = COMMON_ERR_BASE -8;	 	   //玩家不在市内
	
	
	// 帮会
	public static int TONG_ERR_BASE = -200;
	public static int ERR_TONG_CREATE_LEVEL = TONG_ERR_BASE -1; 	// 创建等级不够
	public static int ERR_TONG_CREATE_FAME =  TONG_ERR_BASE -2; 	// 创建威望不够
	public static int ERR_TONG_MEMBER =  TONG_ERR_BASE -3; 			// 已经是帮会成员
	public static int ERR_TONG_NAME_EXISTS =  TONG_ERR_BASE -4; 	// 帮名已被使用
	public static int ERR_TONG_NO_PERMISSION =  TONG_ERR_BASE -5; 	// 没有权限操作
	public static int ERR_TONG_NOT_EXISTS =  TONG_ERR_BASE -6; 		// 帮会不存在
	public static int ERR_TONG_MEMBER_FULL =  TONG_ERR_BASE -7; 	// 人数已经满
	public static int ERR_ITEM_OVER_AMOUNT =  TONG_ERR_BASE -8; 	// 捐献个数超过了物品数
	public static int ERR_INPUT_ITEM_AMOUNT =  TONG_ERR_BASE -9; 	// 物品个数不合法
	public static int ERR_CANNOT_DONATE =  TONG_ERR_BASE -10; 		// 该物品不能捐献
	public static int ERR_TONG_DEPOT_FULL =  TONG_ERR_BASE -11; 	// 仓库已满
	public static int ERR_DONATE =  TONG_ERR_BASE -11; 				// 捐献道具错误
	public static int ERR_ASKFOR =  TONG_ERR_BASE -12; 				// 索要道具错误
	public static int ERR_JOIN =  TONG_ERR_BASE -13; 				// 加入帮会失败
	public static int ERR_NO_TONG_PLAYER = TONG_ERR_BASE -14; 		// 加入帮会失败
	public static int ERR_TONG_DEPOT = TONG_ERR_BASE -15; 			// 帮会道具相关
	public static int ERR_TONG_PLAYER = TONG_ERR_BASE -16; 			// 帮会玩家相关
	public static int ERR_TONG_PLAYER_EXISTS =  TONG_ERR_BASE -17; 	// 玩家已存在
	public static int ERR_TONG_UP =  TONG_ERR_BASE -18; 			// 升级错误
	public static int ERR_TONG_CREATE_FRIEND =  TONG_ERR_BASE -19;	// 创建密友度不够
	public static int ERR_TONG_ALLY =  TONG_ERR_BASE -20;			// 同盟帮会
	
	// 门派
	public static int SECT_ERR_BASE = -300;
	public static int ERR_SKILL_NOT_EXISTS = SECT_ERR_BASE -1;		// 技能不存在
	public static int ERR_SKILL_NOT_MATCH = SECT_ERR_BASE -2;		// 技能与门派不符合
	public static int ERR_TRAIN_SKILL_YET = SECT_ERR_BASE -3;		// 已经修炼了技能
	public static int ERR_POINT_NOT_ENOUGH = SECT_ERR_BASE -4;		// 技能点不足
	public static int ERR_ILLEGEAL_RELATION = SECT_ERR_BASE -5;		// 非法技能关系
	public static int ERR_NOT_ENOUGH_POINT = SECT_ERR_BASE -6;		// 技能点不足
	public static int ERR_NOT_CON_XF = SECT_ERR_BASE -7;			// 没有前置心法
	public static int ERR_REACH_MAX_LEVEL = SECT_ERR_BASE -8;		// 达到最大等级
	public static int ERR_IS_NOT_BASE_SKILL = SECT_ERR_BASE -9;		// 不是基础技能
	public static int ERR_MATCH_SIGNUP_TIME = SECT_ERR_BASE -10;	// 非法报名时间
	public static int ERR_MATCH_TYPE = SECT_ERR_BASE -11;			// 只能挑战更高级别的称号
	public static int ERR_SECT = SECT_ERR_BASE -12;					// 门派对应错误
	public static int ERR_NO_REQUIRE_ARM = SECT_ERR_BASE -10;		// 没有相应武器

	
	//战斗
	public static int FIGHT_ERR_BASE = -800;
	public static ErrorMsg ERR_TARGET_LOST = new ErrorMsg(FIGHT_ERR_BASE -1, "对方已经战败或逃跑");
	public static ErrorMsg ERR_NO_ATK_LEVEL = new ErrorMsg(FIGHT_ERR_BASE -2, "你的级别不够，不能参加战斗");
	public static ErrorMsg ERR_NO_DEF_LEVEL = new ErrorMsg(FIGHT_ERR_BASE -3, "对方级别不够，不能参加战斗");
	public static ErrorMsg ERR_TARGET_DEAD = new ErrorMsg(FIGHT_ERR_BASE -4, "对方已经战败");
	public static ErrorMsg ERR_NO_PK_LOCATION = new ErrorMsg(FIGHT_ERR_BASE -5, "该区域不能PK");
	public static ErrorMsg ERR_NO_KILL_TONGUSER = new ErrorMsg(FIGHT_ERR_BASE -6, "同帮会之间不能PK");
	public static ErrorMsg ERR_PICK_ITEM_LOST = new ErrorMsg(FIGHT_ERR_BASE -7, "该道具已被其他玩家捡起");
	public static ErrorMsg ERR_FIGHT_CITY_TIME_LIMIT = new ErrorMsg(FIGHT_ERR_BASE -8, "现在不是攻城时间");
	public static ErrorMsg ERR_NO_TONG_FIGHT = new ErrorMsg(FIGHT_ERR_BASE -9, "还没有帮会宣布攻城");
	public static ErrorMsg ERR_NO_TONG_OWNER = new ErrorMsg(FIGHT_ERR_BASE -10, "不是帮会帮主，不能宣布攻城");
	public static ErrorMsg ERR_NOT_ENOUGH_MEMBER = new ErrorMsg(FIGHT_ERR_BASE -11, "人员不够");
	public static ErrorMsg ERR_NOT_ENOUGH_ONLINE_PLAYER = new ErrorMsg(FIGHT_ERR_BASE -12, "在线玩家不足");
	public static ErrorMsg ERR_FIGHT_CITY_SELF = new ErrorMsg(FIGHT_ERR_BASE -13, "不能攻占自己的城池");
	public static ErrorMsg ERR_FIGHT_CITY_FREQ_LIMIT = new ErrorMsg(FIGHT_ERR_BASE -14, "该城市最近发生过战事，需要休整");
	public static ErrorMsg ERR_NO_FIGHT_ANNOUNCE = new ErrorMsg(FIGHT_ERR_BASE -15, "还没有宣布攻城");
	public static ErrorMsg ERR_JOIN_MULTI_FIGHT_CITY = new ErrorMsg(FIGHT_ERR_BASE -16, "在其他城池已经有战事，不能参加多个战斗");
	public static ErrorMsg ERR_FIGHT_HAS_BEGAN = new ErrorMsg(FIGHT_ERR_BASE -17, "战斗已经开始，不能加入");
	public static ErrorMsg ERR_JOIN_FIGHT_NO_DEF = new ErrorMsg(FIGHT_ERR_BASE -18, "不是守城帮会成员，不能参加");
	public static ErrorMsg ERR_JOIN_FIGHT_NO_ATK = new ErrorMsg(FIGHT_ERR_BASE -19, "不是攻城帮会成员，不能参加");
	public static ErrorMsg ERR_WAR_NOT_VICTOR = new ErrorMsg(FIGHT_ERR_BASE -20, "不是获胜玩家"); //非最终获胜玩家
	public static ErrorMsg ERR_WAR_SIGNUP_TIME = new ErrorMsg(FIGHT_ERR_BASE -21, "报名时间已过"); //过了报名时间
	public static ErrorMsg ERR_WAR_CHAMPINE = new ErrorMsg(FIGHT_ERR_BASE -22, "上周已是冠军，不能报名");
	public static ErrorMsg ERR_WAR_CITY_NUM = new ErrorMsg(FIGHT_ERR_BASE -23, "你的帮会级别过低，或者占领的城市数超过当前级别的限定，不能攻城");
	public static ErrorMsg ERR_WAR_LEVEL = new ErrorMsg(FIGHT_ERR_BASE -24, "当前级别不能占领城市");
	public static ErrorMsg ERR_ANNOUNCED_ALREADY = new ErrorMsg(FIGHT_ERR_BASE -25, "该帮已经宣过城市，同时只能攻击一个城");
	public static ErrorMsg ERR_ATT_TYPE = new ErrorMsg(FIGHT_ERR_BASE -26, "此暗器不能攻击人物对象");
	
	//宠物
	public static int PET_ERR_BASE = -900;
	public static int ERR_PET_NOT_EXISTS = PET_ERR_BASE - 1;				//宠物不存在了
	public static int ERR_PET_ILLEGAL_ARG = PET_ERR_BASE - 2;				//非法参数值
	public static int ERR_PET_LEVEL = PET_ERR_BASE - 3;						//宠物等级不够
	public static int ERR_TALENT_NOT_EXISTS = PET_ERR_BASE - 4;				//天赋不存在
	public static int ERR_TALENT_ILLEGAL_RELATION = PET_ERR_BASE - 5;		//非法关系
	public static int ERR_NOT_PET_ITEM = PET_ERR_BASE - 6;					//不是宠物物品
	public static int ERR_NO_HUNGER = PET_ERR_BASE - 7;						//宠物当前很饱
	public static int ERR_NO_STONE = PET_ERR_BASE - 8;						//没有洗天赋的石头
	public static int ERR_NO_XIDIAN = PET_ERR_BASE - 9;						//没有洗洗点道具
	public static int ERR_NO_TALENT = PET_ERR_BASE - 10;					//天赋错误
	
	//师徒	
	public static int COACH_STUDENT_ERR_BASE = -1000;	
	public static int ERR_STUDENT_TO_COACH = COACH_STUDENT_ERR_BASE - 1;	//徒弟不能成为师傅
	public static int ERR_STUDENT_TO_STUDENT = COACH_STUDENT_ERR_BASE - 2;	//徒弟不能成为徒弟
	public static int ERR_COACH_TO_STUDENT = COACH_STUDENT_ERR_BASE - 3;	//师傅不能成为徒弟
	public static int ERR_COACH_LEVEL_LOW = COACH_STUDENT_ERR_BASE - 4;		//师傅必须比徒弟高10级以上
	public static int ERR_REFUSE_STUDENT = COACH_STUDENT_ERR_BASE - 5;		//已经是徒弟，不能拒绝
	public static int ERR_NOT_COACH = COACH_STUDENT_ERR_BASE - 6;			//对方还不是师傅
	public static int ERR_WAIT_STUDENT_LIMIT = COACH_STUDENT_ERR_BASE - 7;	//超过最大等待徒弟数
	public static int ERR_ACK_STUDENT_LIMIT = COACH_STUDENT_ERR_BASE - 8;	//超过最大等待徒弟数
	public static int ERR_GIVEUP_COACH = COACH_STUDENT_ERR_BASE - 9;		//已经是徒弟，不能撤销申请
	public static int ERR_OFFLINE = COACH_STUDENT_ERR_BASE - 10;			//玩家离线
	public static int ERR_COACH_MARRY = COACH_STUDENT_ERR_BASE - 11;		//夫妻不能拜师
	public static int ERR_NOT_STUDENT = COACH_STUDENT_ERR_BASE - 12;		//不是徒弟
	public static int ERR_THANK_LIMIT = COACH_STUDENT_ERR_BASE - 13;		//感谢师傅频率限制
	public static int ERR_PLAYER_LEVEL_HIGH = COACH_STUDENT_ERR_BASE - 14;	//玩家等级太高
	public static int ERR_NOT_INSTRUCT_SKILL = COACH_STUDENT_ERR_BASE - 15;	//不是传授的技能
	public static int ERR_RELATION_NOT_EXISTS = COACH_STUDENT_ERR_BASE - 16;//关系不存在
	public static int ERR_COACH_TO_COACH = COACH_STUDENT_ERR_BASE - 17;		//师傅不能成为师傅
	
	//队伍
	public static int TEAM_ERR_BASE = -1100;
	public static int ERR_PLAYER_IN_TEAM = TEAM_ERR_BASE - 1;	// player已经加入了队伍
	public static int ERR_NO_TEAM = TEAM_ERR_BASE - 2;	// player已经加入了队伍
	public static int ERR_TEAM_MEMBER = TEAM_ERR_BASE - 3;	// 队伍人数限制
	public static int ERR_TEAM_LEADER = TEAM_ERR_BASE - 4;	// 需要队长权限
	public static int ERR_TEAM_LINK_SELF = TEAM_ERR_BASE - 5;	// 队伍存在，转到自己的队伍
	public static int ERR_TEAM_FULL = TEAM_ERR_BASE - 6;	// 队伍人数满
	
	//任务
	public static int ERR_MISSION_BASE = -1200;
	public static int ERR_MISSION_NOT_EXIST = ERR_MISSION_BASE - 1;//任务不存在
	public static int ERR_MISSION_COMPLETED = ERR_MISSION_BASE - 2;//任务已经完成
	public static int ERR_MISSION_LOW_LEVEL =  ERR_MISSION_BASE - 3;//玩家级别不够
	public static int ERR_MISSION_NO_MONEY = ERR_MISSION_BASE - 4;//金额不足
	public static int ERR_MISSION_ALREADY_EXIST = ERR_MISSION_BASE - 5;//任务已经存在
	public static int ERR_MISSION_MAX_NUM = ERR_MISSION_BASE - 6;//超过最大任务数;
	public static int ERR_MISSION_INPUT = ERR_MISSION_BASE - 7;//输入错误;
	
	//道具赠送
	public static int ERR_GIVE_BASE = -1300;
	public static int ERR_GEVE_TO_SELF = ERR_GIVE_BASE -1; //自己不能赠送给自己
	public static int ERR_GIVE_NOT_FRIEND = ERR_GIVE_BASE -2; //不是好友
	public static int ERR_GIVE_BLACK = ERR_GIVE_BASE -3; //黑名单
	public static int ERR_GEVE_SEX= ERR_GIVE_BASE -4; //性别不对
	//婚姻
	public static int ERR_MARRY_BASE = -1400;
	public static int ERR_MARRY_NOT_CONDITION = ERR_MARRY_BASE-1;//不满足婚姻条件
	public static int ERR_MARRY_TO_SELF = ERR_MARRY_BASE-2;//不满足婚姻条件
	public static int ERR_MARRY_NOT_ING = ERR_MARRY_BASE-3;//还没举办婚礼
	public static int ERR_MARRY_OVER_MAX_NUM = ERR_MARRY_BASE-4;//超过最大祝贺人数
	public static int ERR_MARRY_TYPE = ERR_MARRY_BASE-5;// 类型错误
	public static int ERR_MARRY_CONG = ERR_MARRY_BASE-6;//祝贺过
	public static int ERR_MARRY_END = ERR_MARRY_BASE-7;//婚礼已结束
	public static int ERR_MARRY_APPLY = ERR_MARRY_BASE-8;//已经申请过，等待处理 
	public static int ERR_MARRY_CONG_FUNNY = ERR_MARRY_BASE-9;//送美金 
	public static int ERR_MARRY_ING = ERR_MARRY_BASE-10;//婚礼进行中 
	public static int ERR_MARRY_DONE = ERR_MARRY_BASE-11;//结过婚 
	
	//送礼
	public static int ERR_PRESENT_BASE = -1500;
	public static int ERR_SAME_PRESENT_TO_SAME_RECEIVER = ERR_PRESENT_BASE - 1; //同样的礼物送同样的人
	
	// 交易
	public static int ERR_EXCHANGE_BASE = -1600;
	public static int ERR_EXCHANGE_LIMIT = ERR_EXCHANGE_BASE-1;
	public static int ERR_EXCHANGE_CANCEL = ERR_EXCHANGE_BASE-2;
	public static int ERR_EXCHANGE_SELF = ERR_EXCHANGE_BASE-3;
	
	// 解答员
	public static int ERR_KF_BASE = -1700;
	public static int ERR_KF_EXIST = ERR_KF_BASE - 1;//已经是解答员 
	public static int ERR_KF_NOT_EXIST = ERR_KF_BASE - 2;//不是解答员
	public static int ERR_KF_NONE_VOTE = ERR_KF_BASE - 3;//超过当日投票次数 
	
	//活动
	public static int ERR_ACTIVE_BASE = -1800;
	public static int ERR_ACTIVE_INVALID = ERR_ACTIVE_BASE - 1; //活动不在有效期
	public static int ERR_ACTIVE_LOW = ERR_ACTIVE_BASE - 2; //没达到活动条件
	public static int ERR_ACTIVE_DONE = ERR_ACTIVE_BASE - 3; //是否已经参加
	public static int ERR_ACTIVE_TYPE = ERR_ACTIVE_BASE - 4; //参数，类型错误
	
	//倒卖
	public static int ERR_RESELL_BASE = -1900;
	public static int ERR_RESELL_NO_RESELL_GOLD = ERR_RESELL_BASE-1;//倒卖币不足
	public static int ERR_RESELL_GOODS_FULL = ERR_RESELL_BASE-2;    //可买货物已满
	public static int ERR_RESELL_FLUSH_MAX_TIMES = ERR_RESELL_BASE-3;//最大刷新次数
	
}


