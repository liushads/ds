<#include "/include/header.ftl">
<#include "/include/head_card.ftl">
<#if label_player_skill?exists>
	恭喜成功升级技能${label_player_skill.skill.name}！<br/>
</#if>
<@goback/>
<@gogame/>
<#include "/include/foot_card.ftl">