<#include "/include/header.ftl">
<#include "/include/head_card.ftl">
<#if label_prize?exists && (label_prize?size > 0)>
	<#list label_prize as prize>
		<@a href="/p?d_GRD/${pid}/1/de/${prize.id}/"/>劫镖奖励${prize_index + 1}</a> <@a href="/p?d_GRD/${pid}/1/gt/${prize.id}/"/>领取</a><br/>
	</#list>
</#if>
<@goback/>
<@gogame/>
<#include "/include/foot_card.ftl">