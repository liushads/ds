<#include "/include/header.ftl">
<#include "/include/player.ftl">
<card title="${game_title}"><p>
<#if npc?exists>
	<@location/>
	${npc.name}：
	<#if desc?exists>
	${desc}。
	<#else>
	目前没有可领任务。任务将每周不定时更新，敬请关注。
	</#if>
	<br/>
	<a href="<@link "/p?p_T/${pid}/${npc.id}"/>">返回</a><br/>
<#else>
	<#if desc?exists>
	${desc}。
	<#else>
	目前没有可领任务。任务将每周不定时更新，敬请关注。
	</#if>
	<br/>
	<@goback/>
</#if>
<@gofacility/>
</p>
</card>
</wml>
