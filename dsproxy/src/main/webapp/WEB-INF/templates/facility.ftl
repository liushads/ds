<#include "/include/header.ftl">
<#include "/include/chat.ftl">
<#include "/include/playeritem.ftl">
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
<#if label_level?exists>
${label_level}级 <@showPlayerMoney label_adv_gold,label_gold,label_copper/><br/>
vip信息 疲劳值${label_fame}/${label_fame_max} 充值<br/>
</#if>
<#if displayer?exists>
<#if displayer.serverType == 1>
<#if displayer.player.id == pid>
${displayer.player.name}展示了他的${displayer.playerItem.item.name}
<#else>
<@a href="/p?p_VO/${pid}/${displayer.player.id}"/>${displayer.player.name}</a>展示了他的<@a href="/p?i_I/${pid}/${displayer.playerItem.id}/${displayer.playerItem.playerId}/"/><@showName displayer.playerItem/></a>
</#if>
,好牛的装备哦
<br/>
</#if>
<#if displayer.serverType == 2>
</#if>
<#if displayer.serverType == 4>
${displayer.player.name}战胜了${displayer.seller}，好厉害哦~<br/>
</#if>
<#if displayer.serverType == 3>
</#if>
</#if>

<#assign lowPlayerLevel = 2>

${location}
<#if player?exists && (player.level > lowPlayerLevel)>
<#if is_facility>
<#if city_facility?exists  >
<@a href="/p?m_F/${pid}/${mission.mission.cityfacilityId}"/>自动寻路(${shuliang})</a>
</#if><br/>
<#else>
<br/>
</#if>
<#else>
<br/>
</#if>
<#if players?exists>
你看到玩家:<@a href="p?p_LP/${pid}/0/0"/>${players}...</a><br/>
</#if>
<#if (fight_monster?exists) >
你看到怪:
<#list fight_monster as ls><@a href="/p?f_MB/${pid}/${ls[0].id}/"/>${ls[0].name}</a><#if !ls_has_next><br/><#else>,</#if></#list>
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
【<@a href="/p?mv_LF/${pid}/"/>城内地图</a>】<#if currentCity?exists && currentCity.id != 134 && currentCity.id != 115 && currentCity.id != 122 && currentCity.id != 126 && currentCity.id != 135 && currentCity.id != 140&& currentCity.id != 141&& currentCity.id != 142&& currentCity.id != 143>【<@a href="/p?mv_O/${pid}/"/>出城</a>】</#if><br/>
</#if>

${facility_desc}<br/>
<#if win_prize?exists>
	<#list win_prize as ls>
		<@a href="p?f_PU/${pid}/${ls[0].id}/"/>${ls[0].name}</a><br/>
	</#list>
</#if>
<#if is_facility>
<#include "/include/toolbar.ftl"><br/>
</#if>
</#if>
<@ag href="/p?u_LZ/${player.userId}/"/>返回游戏首页</a>
</p></card>
</wml>