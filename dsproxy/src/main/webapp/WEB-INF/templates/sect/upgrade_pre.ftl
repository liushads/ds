<#-- 显示已经修炼的技能列表 -->
<#include "/include/header.ftl">
<#include "/include/sect.ftl">
<card title="${game_title}"><p>
<#if player_skill?exists>
	名称:${player_skill.sectSkill.name}<br/>
	描述:${player_skill.sectSkill.description}<br/>
	重数:${player_skill.sectSkill.level}重<br/>
	<#if sect_next_skill?exists>
	<br/>
	下一等级需求:<br/>
	<#if sect_con_skill?exists>
	心法:${sect_con_skill.alias}<br/>
	</#if>
	<@showInstructRequire sect_next_skill/>	
	<#if (player_skill.type > 0) >
	<#if player_skill.type = 1><#-- 绝学 -->
	<#if player_skill.isUse = true >
	<@a href="/p?se_SC/${pid}/${player_skill.id}/u/"/>卸载</a>
	<#else>
	<@a href="/p?se_SC/${pid}/${player_skill.id}/"/>装载</a>
	</#if>
	<#elseif player_skill.type = 2><#-- 招式 -->
	<#if player_skill.isUse = true >
	<@a href="/p?se_UMS/${pid}/${player_skill.id}/"/>卸载</a>
	<#else>
	<@a href="/p?se_MS/${pid}/${player_skill.id}/"/>装载</a>
	</#if></#if><br/></#if>	
	<@a href="/p?se_US/${pid}/${player_skill.id}/"/>修炼</a><br/>
	<@a href="/p?se_US/${pid}/${player_skill.id}/1"/>金票修炼</a><br/>
	<@a href="/p?se_US/${pid}/${player_skill.id}/2"/>金锭修炼</a><br/>
	</#if>
<#else>
没有该技能
</#if>
<@goback/>
<@gogame/>
</p></card>
</wml>