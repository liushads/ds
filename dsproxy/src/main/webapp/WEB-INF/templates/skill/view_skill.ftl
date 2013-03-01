<#include "/include/header.ftl">
<#include "/include/head_card.ftl">
<#if label_skill?exists>
	${label_skill.name}：${label_skill.level}级，${label_skill.desception}<br/>
</#if>
<@goback/>
<@gogame/>
<#include "/include/foot_card.ftl">