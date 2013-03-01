<#include "/include/header.ftl">
<card title="${game_title}"><p>
${location}<br/>
${npc.name}：${mission.description}<br/>
<#if desc?exists>
${desc}<br/>
<#else>
<@a href="/p?in_E/${pid}/${mission.id}"/>申请进入"${mission.name}"</a>
<br/>
<#if teams?exists>
等待进入${mission.name}的队伍：<br/>
	<#list teams as obj>
		${obj[0].name} <@a href="/p?in_J/${pid}/${mission.id}/${obj[0].id}"/>加入</a><br/>
	</#list>
</#if>
</#if>
<@goback />
<@gofacility/>
</p></card>
</wml>
