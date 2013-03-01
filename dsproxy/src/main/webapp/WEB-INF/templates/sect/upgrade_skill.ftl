<#-- 修炼技能 -->
<#include "/include/header.ftl">
<#include "/include/sect.ftl">
<card title="${game_title}"><p>
<#if sect_skill?exists>
	修炼成功，你习得技能<@a href="/p?se_SI/${pid}/${sect_skill.id}/${sect_skill.skillType}/"/>${sect_skill.alias}</a><br/>
<#else><#-- 修炼可能不成功 -->
	<#if desc?exists>
		${desc}<br/>
	<#else>
		修炼失败<br/>
	</#if>
</#if>
<@a href="/p?se_VS/${pid}/"/>返回</a><br/>
<@gogame/>
</p></card>
</wml>