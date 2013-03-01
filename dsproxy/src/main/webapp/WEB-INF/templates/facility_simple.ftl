<#include "/include/header.ftl">
<#include "/include/config.ftl">
<#include "/include/chat.ftl">
<#macro direction desc name id index>
	<#if name != "null">
		${desc}
		<#if is_clicks[index]>
		<@a href="/p?mv_W/${pid}/${id}"/>${name}</a>
		<#else>
		${name}
		</#if>
	</#if>
</#macro>

<card title="${game_title}"><p>
<@app/>

<#assign lowPlayerLevel = 2>
<#if onlinegive?exists>
<#if (onlintime?number-(60-minutes?number))<=0>第${onlinegive?number+1}份礼包可
<@a href="/p?mv_W/${pid}/${curr_facility.id}/online/1"/>领取</a>
<#else>继续在线${onlintime?number-(60-minutes?number)}分钟可以领取第${onlinegive?number+1}份礼包</#if>
<br/><#elseif onlinetype?number==1><#if minutes?exists><#if (minutes?number<=0)>获得最高在线奖励<@a href="/p?n_re/${pid}/zxlq/"/>领取</a>
<#else>你还有${minutes}分钟领取礼包</#if></#if><br/></#if>
${location}

<#if player?exists && (player.level < 3)>
<br/>【提示】带<img alt="" src="${img_server}/tan.gif" />的人物为主线任务
</#if>
<#if player?exists && (player.level > lowPlayerLevel)>
<#if is_facility>

<@a href="/p?mv_W/${pid}/${curr_facility.id}"/>刷新</a>
<img alt="" src="${img_server}/tan.gif" /><@a href="/p?m/${pid}/type"/>任务(<#if amount?exists>${amount}<#else>0</#if>)</a>
<br/>
<#if player.streamOpen==1>
<@a href="/p?mv_W/${pid}/${curr_facility.id}/stream/0"/>完整版<br/></a>
<#else>
<@a href="/p?mv_W/${pid}/${curr_facility.id}/stream/1"/>打开省流量<br/></a>
<#if mission?exists>
<#if player.missionopen?exists>
<#if player.missionopen==0>
<@a href="/p?mv_W/${pid}/${curr_facility.id}/mission/1"/>打开任务跟踪：<br/></a>
<#else>
<@a href="/p?mv_W/${pid}/${curr_facility.id}/mission/0"/>关闭任务跟踪：<br/></a>
<#if targetArea?exists>
任务目的地：${targetArea.name}-${targetCity.name}-${targetFacility.name}
<#if targetNpc?exists>-${targetNpc.name}</#if><br/><#if text?exists>${text}</#if>
<#if city_facility?exists >
<@a href="/p?m_F/${pid}/${mission.mission.cityfacilityId}"/>遁地(${shuliang})</a>
</#if><br/>
<#else>
${mission.mission.description}<br/>
</#if>
</#if>
</#if>
</#if>
<#if private_msg?exists>
<@showPrivateMessage private_msg=private_msg/>
</#if>
<#if page_objs?exists>
<@showPublicMessage page_objs=page_objs/>
</#if>
</#if>

<#if players?exists>
你看到玩家:<@a href="p?p_LP/${pid}/0/0"/>${players}...</a><br/>
</#if>
<#else>
<br/>
</#if>
<#if (fight_monster?exists) >
你看到怪:
<#list fight_monster as ls><@a href="/p?f_MB/${pid}/${ls[0].id}/"/>${ls[0].name}</a><#if !ls_has_next><br/><#else>,</#if></#list>
</#if>
<#else>
<br/>
</#if>
<#if npc?exists>
<#list npc as e>
	<#if e[0].name?substring(0, 1) == "？">
   		<img alt="" src="${img_server}/wen.gif" /><@a href="/p?p_T/${pid}/${e[0].id}"/>${e[0].name?substring(1)}</a><br/>
   	<#elseif e[0].name?substring(0, 1) == "！">
   		<img alt="" src="${img_server}/tan.gif" /><@a href="/p?p_T/${pid}/${e[0].id}"/>${e[0].name?substring(1)}</a><br/>
	<#else>
	 <#if player?exists && (player.level > lowPlayerLevel)>
		<@showSpecialNpcImg e[0].id /> <@a href="/p?p_T/${pid}/${e[0].id}/"/>${e[0].name}</a><br/>
	</#if>
	</#if>
</#list>
</#if>

<#if action?exists>
	<#list action as action>
		<#assign action_param_temp = ""/>
		<#if action.param?exists><#assign action_param_temp = action.param/></#if>
		<@a href="/p?${action.command}/${pid}/${action_param_temp}"/>${action.name}</a><br/>
	</#list>
</#if>
<#if player?exists && (player.level >lowPlayerLevel)>
<#if direct[0][0].name != "null" || direct[1][0].name != "null" || direct[2][0].name != "null" || direct[3][0].name != "null">
请选择出口:
<#if direct[0][0].name != "null" || direct[1][0].name != "null"><br/></#if>
<@direction desc="东:" name="${direct[0][0].name}" id="${direct[0][0].id}" index=0/>
<@direction desc="西:" name="${direct[1][0].name}" id="${direct[1][0].id}" index=1/>
<#if direct[2][0].name != "null" || direct[3][0].name != "null"><br/></#if>
<@direction desc="南:" name="${direct[2][0].name}" id="${direct[2][0].id}" index=2/>
<@direction desc="北:" name="${direct[3][0].name}" id="${direct[3][0].id}" index=3/><br/>
</#if>
<#if is_facility>			
【<@a href="/p?mv_LF/${pid}/"/>城内地图</a>】<#if currentCity?exists && currentCity.id != 134>【<@a href="/p?mv_O/${pid}/"/>出城</a>】</#if><br/>
</#if>

<#if player.stateOpen==1>
<@a href="/p?mv_W/${pid}/${curr_facility.id}/state/0"/>打开<br/></a>
<#else>
<@a href="/p?mv_W/${pid}/${curr_facility.id}/state/1"/>收起<br/></a>
${facility_desc}<br/>
<#if win_prize?exists>
	<#list win_prize as ls>
		<@a href="p?f_PU/${pid}/${ls[0].id}/"/>${ls[0].name}</a><br/>
	</#list>
</#if>
<#if is_facility>
<@a href="/p?p_VS/${pid}/"/>状态</a>.
<@a href="/p?i_L/${pid}/1"/>物品</a>.
<@a href="/p?ca_L/${pid}/0"/>聊天</a>
<br/>
<@a href="/p?p_LF/${pid}/0"/>好友</a>.
<@a href="/p?t_M/${pid}/"/>帮会</a>.
<@a href="/p?te_L/${pid}/"/>队伍</a>
<br/>
<@a href="/p?pe_L/${pid}/"/>宠物</a>.
<@a href="/p?ma_I/${pid}/1/"/>商城</a>.
<@a href="${bbs_server}/b?f_l/${pid}/${zoneId}"/>论坛</a><br/>
<br/>
<@qqbar/><br/><br/>
</#if>
</#if>
</#if>
</p></card>
</wml>