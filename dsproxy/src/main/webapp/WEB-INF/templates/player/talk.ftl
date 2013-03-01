<#include "/include/header.ftl">
<#include "/mission/mission.ftl">
<card title="${game_title}"><p>
${location}<br/>
<#if npc?exists>${npc.name}：</#if><#if dialog?exists && dialog.dialog?exists && dialog.dialog != "" >${dialog.dialog}</#if>
<#if dialog?exists && dialog.question?exists>
<br/><#if npc?exists>${npc.name}：</#if>
${dialog.question.description}
	<#list dialog.question.answers as ls>
		<br/><@a href="/p?p_A/${pid}/${npc.id}/${dialog.question.id}/${ls_index}"/>${ls}</a>
	</#list>
</#if>
<#if mission?exists>
<br/><@mission_desc mission/>
</#if>
<#assign ac = 0>
<#if dialog?exists && dialog.actions?exists>
	<#list dialog.actions as ls>
		<#assign param_tmep = ""/>
		<#assign label_custom_tmep = ""/>
		<#if ls.param?exists && ls.param != ""><#assign param_tmep = ls.param+"/"/></#if>
		<#if custom?exists><#assign label_custom_tmep = custom/></#if>
		<br/><@a href="/p?${ls.command}/${pid}/${param_tmep}${label_custom_tmep}"/>${ls.name}</a>
	</#list>
</#if>
<#if ac == 0&&(player.level>3)><br/><@gofacility/></#if>
<@goback/>
<@gogame/>
</p></card>
</wml>