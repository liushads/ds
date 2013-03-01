<#-- 显示可以传授的技能列表 -->
<#include "/include/header.ftl">
<card title="${game_title}"><p>
师傅可以免费传授徒弟技能<br/>
<#if sect_skills?exists>
您可以传授以下技能给徒弟<#if student?exists>【${student.name}】</#if><br/>
	<#list sect_skills as skill>
		<@a href="/p?co_In/${pid}/${student.playerId}/${skill.id}/${skill.skillType}/"/>传授</a>
		<@a href="/p?se_SI/${pid}/${skill.id}/${skill.skillType}/"/>${skill.alias}</a><br/>
	</#list>	
<#else>
<#if desc?exists>${desc}<#else>没有可以传授的技能</#if><br/>
</#if>
<@a href="/p?co_CM/${pid}/"/>返回</a><br/>
<@gogame />
</p>
</card>
</wml>