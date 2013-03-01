<#-- 修炼技能 -->
<#include "/include/header.ftl">
<#include "/include/sect.ftl">
<card title="${game_title}"><p>
<#if curr_facility?exists>
${curr_facility.name}<br/>
</#if>
<#assign skillType = '0'>
<#if return?exists><#assign skillType = return></#if>
<#if last_npc?exists>${last_npc.name}:<br/></#if>
<#if sect_skill?exists>
	<#assign skillType = sect_skill.skillType?string>
	修炼成功，你习得技能<@a href="/p?se_SI/${pid}/${sect_skill.id}/${sect_skill.skillType}/"/>${sect_skill.alias}</a><br/>
<@a href="/p?se_LI/${pid}/${sect_id}/${skillType}/"/>继续修炼</a><br/>
<#else><#-- 修炼可能不成功 -->
	<#if desc?exists>
		${desc}<br/>
	<#else>
		修炼失败<br/>
	</#if>
	<@a href="/p?se_LI/${pid}/${sect_id}/${skillType}/"/>返回</a><br/>
</#if>
<@gogame/>
</p></card>
</wml>