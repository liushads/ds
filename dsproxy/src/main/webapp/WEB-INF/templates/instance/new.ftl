<#include "/include/header.ftl">
<card title="${game_title}"><p>
${location}<br/>
<#if desc?exists>
${desc}<br/>
</#if>
${npc.name}：尚未加入任何队伍！<br/>
<#if teams?exists>
加入队伍：<br/>
	<#list teams as obj>
		${obj[0].name} <@a href="/p?in_J/${pid}/${mission.id}/${obj[0].id}"/>加入</a><br/>
	</#list>
</#if>
<@a href="/p?in_C/${pid}/${mission.id}"/>创建新队伍</a>
<br/>
<@goback />
<@gofacility/>
</p></card>
</wml>
