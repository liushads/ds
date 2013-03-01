<#-- 招式装载显示页面 -->
<#include "/include/header.ftl">
<#include "/include/sect.ftl">
<card title="${game_title}"><p>
<#if desc?exists>
${desc}.<br/>
</#if>
<br/>
【可装载】<#if sect_useable_skills?exists>
	<br/>
	<#list sect_useable_skills as skill>
	<@a href="/p?se_MS/${pid}/${skill.id}/"/>装载</a> <@a href="/p?se_SI/${pid}/${skill.sectSkill.id}/${skill.type}/"/>${skill.sectSkill.alias}</a><br/>
	</#list>
<#else>
无可装载招式<br/>
</#if>

【已装载】<#if sect_used_skills?exists>
<br/><#list sect_used_skills as skill>
		<#if skill?exists>
 		<@a href="/p?se_UMS/${pid}/${skill.id}/"/>卸下</a> <@a href="/p?se_SI/${pid}/${skill.sectSkill.id}/${skill.type}/"/>${skill.sectSkill.alias}</a><br/>
</#if></#list><#else>无装载招式<br/>
</#if>
<br/>
<@a href="/p?se_VS/${pid}/"/>返回</a><br/>
<@gofacility/>
</p></card>
</wml>