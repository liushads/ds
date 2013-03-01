<#include "/include/header.ftl">
<card title="${game_title}">
<#if (player.autoFightMonsterFlag==1)>
<onevent type="ontimer">
<#if curr_jx?exists>
<go href="<@encodeUrl "/p?f_M/${pid}/${nextMonsterId}/jx/${curr_jx.id}/", userKey, Md5Key/>"/>
<#else>
<go href="<@encodeUrl "/p?f_M/${pid}/${nextMonsterId}", userKey, Md5Key/>"/>
</#if>
</onevent>
<timer value="20"/>
</#if>
<p>
<#if player.exp!=312115959>
<#if win_upgrade?exists><img alt="" src="${img_server}/arrow.gif" />恭喜你，升到${win_upgrade}级了!<br/></#if>
</#if>
战胜了${killed[0][0].name}!<br/>
<#if curr_facility?exists>
<@a href="/p?mv_W/${pid}/${curr_facility.id}/"/>继续走</a><br/>
</#if>
<#if fightEnemyInfo?exists>
${fightEnemyInfo}
<#if bonusGold?exists>
,取到了赏金${bonusGold}个${rewardItem.name}
</#if>
<br/>
</#if>

<#if waste_items?exists>
以下装备耐久为0已卸下，请尽快修理:<br/>
<#list waste_items as ls>${ls[0].name}<br/></#list>
</#if>

<#if win_prize?exists>
你看到:<br/>
<#list win_prize as ls>
<@a href="p?f_PU/${pid}/${ls[0].id}"/>${ls[0].name}</a><br/>
</#list>
你可以去杂货店购买宝莲灯自动捡起道具<br/>
</#if>

<#if auto_pick?exists>
使用宝莲灯，自动捡起了:<br/>
<#list auto_pick as ls>
${ls[0].name}(${ls[0].id}级)<#if ls_has_next>,</#if>
</#list>
<br/>
</#if>

你体力  (${player.hp}/${player.dyn.maxHp})<br/>
<#if win_exp?exists>经验    +${win_exp}<br/></#if>
<#if win_money?exists>金钱    +${win_money}(铜贝)<br/></#if>
<#--<#if win_ship?exists><#list win_ship as ls>船只    +${ls[0].name}<br/></#list></#if>-->
<#--<#if win_evil?exists>罪恶    +${win_evil}<br/></#if>-->

<#if mission?exists>
<#list mission as ls>
${ls[0].name}<br/>
<#if missions?exists>
<#if curr_facility?exists >
(提示：点继续走，再点城内地图，可以快速抵达交任务的地点)
<#if city_facility?exists  >
<@a href="/p?m_F/${pid}/${missions.mission.cityfacilityId}"/>遁地(${shuliang})</a>
</#if>
<br/>
</#if>
</#if>
</#list>
</#if>

<@a href="/p?p_VS/${pid}/"/>状态</a>
<@a href="/p?i_L/${pid}/1/"/>物品</a>
<br/>
<#--
<#if player.fromCityFacilityId == 0 && player.eventId == 0 >
	<@a href="/p?s_G/${pid}/1/"/>">继续航行</a>
<#elseif is_facility >	
<@gofacility/>
-->
<#if wsj_monsterId?exists>
<#if isFinish?exists && isFinish>
任务完成：小年兽欢欢${monster910002}/10,小年兽喜喜${monster910003}/10,小年兽乐乐${monster910004}/10<br/>
<#else>
任务未完成：小年兽欢欢${monster910002}/10,小年兽喜喜${monster910003}/10,小年兽乐乐${monster910004}/10<br/>
</#if>
</#if>
<#if curr_facility?exists>
<@a href="/p?mv_W/${pid}/${curr_facility.id}/"/>继续走</a><br/>
<#else>
<@goback/>
</#if>
</p>
</card>
</wml>
