<#include "/include/header.ftl">
<#include "/include/head_card.ftl">
<#if (label_learn?size <= 0) &&(label_no_learn?size <= 0)>
	没有可以学习的技能
<#else>
	<#if (label_learn?size > 0)>
		<#list label_learn as skill>
			<@a href="/p?s_VS/${pid}/${skill.id}/"/>${skill.name}</a>
			<@a href="/p?s_US/${pid}/${skill.skillType}/"/>升级</a><br/>
		</#list>
	</#if>
	<#if (label_no_learn?size > 0)>
		<#list label_no_learn as skill>
			<@a href="/p?s_VS/${pid}/${skill.id}/"/>${skill.name}</a>
			<@a href="/p?s_LS/${pid}/${skill.id}/"/>学习</a><br/>
		</#list>
	</#if>
</#if>
<@goback/>
<@gogame/>
<#include "/include/foot_card.ftl">