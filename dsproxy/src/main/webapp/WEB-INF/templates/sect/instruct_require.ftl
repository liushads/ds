<#-- 技能传授需求 -->
<#include "/include/header.ftl">
<#include "/include/sect.ftl">
<card title="${game_title}"><p>
<#if curr_facility?exists>
${curr_facility.name}<br/>
</#if>
<#if last_npc?exists>${last_npc.name}:<br/></#if>
<#if desc?exists>${desc}。<br/></#if>
<#if sect_skill?exists>
	名称:${sect_skill.name}<br/>
	描述:${sect_skill.description}<br/>
	<#if sect_con_skill?exists>
	心法:${sect_con_skill.alias}<br/>
	</#if>
	<@showInstructRequire sect_skill/>	
	<@a href="/p?se_TS/${pid}/${sect_id}/${sect_skill.id}/${return}/"/>修炼</a><br/>
	<@a href="/p?se_TS/${pid}/${sect_id}/${sect_skill.id}/${return}/1"/>金票修炼</a><br/>
	<@a href="/p?se_TS/${pid}/${sect_id}/${sect_skill.id}/${return}/2"/>金锭修炼</a><br/>
<#else>
无
</#if>
<@goback/>
<@gogame/>
</p></card>
</wml>