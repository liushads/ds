<#include "/include/header.ftl">
<card title="${game_title}"><p>
${location}<br/>
${npc.name}：你已经申请进入${mission.name}，召唤你的战友吧，你的队伍必须有${mission.conditionTeamSize}人加入才能进入${mission.name}！<br/>
<#if teams?exists>
我在的队伍：<@a href="/p?in_E/${pid}/${mission.id}"/>刷新</a><br/>
	<#list teams as obj>
		${obj[0].name}<br/>
	</#list>
</#if>
<@gofacility/>
</p></card>
</wml>
