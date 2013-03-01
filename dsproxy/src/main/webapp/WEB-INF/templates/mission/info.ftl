<#include "/include/header.ftl">
<#include "mission.ftl">
<card title="${game_title}"><p>
<#if mission.npc?exists>【${mission.city.name}<#if mission.facilityName?exists>${mission.facilityName}</#if>】：${mission.mission.name}<#if mission.mission.monsterLevel?exists && mission.mission.monsterLevel != "" && mission.mission.monsterLevel != "0">(${mission.mission.monsterLevel}级)</#if></#if>
<#if mission.mission.description != ""><br/>任务描述：${mission.mission.description}</#if>
<#if targetCity?exists>
<br/>任务目的地：${targetArea.name}-${targetCity.name}-${targetFacility.name}<#if targetNpc?exists>-${targetNpc.name}</#if>
</#if>
<#if mission.mission.objective?exists && mission.mission.objective != "" ><br/>任务要求：${mission.mission.objective}</#if>
<#if desc?exists && desc != "" && desc?index_of("任务进度") == 0 ><br/>${desc}</#if>
<br/><@mission_desc mission.mission/>	
<#if city_facility?exists >
<br/><@a href="/p?m_F/${pid}/${mission.mission.cityfacilityId}"/>使用遁地(${amount})</a>
</#if>
<br/><@a href="/p?m/${pid}/list/${mission.mission.type}"/>返回 </a>. 
<#--论坛任务说明-->
<@a href="${bbs_server}/b?t_m/${pid}/${zoneId}/4/${mission.mission.id}/"/>攻略</a>.
<#if mission.mission.type == 6 || mission.mission.type == 8>
<@a href="/p?m/${pid}/cancel/${mission.mission.id}/"/>放弃</a>.
</#if>
<br/>
<@gogame/>
</p>
</card>
</wml>
