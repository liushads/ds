<#include "/include/header.ftl">
<#include "/include/head_card.ftl">
<#if label_dart_vo?exists>
	<#list label_dart_vo as vo>
	${vo.playerName} ${vo.playerLevel}级 ${vo.dartName}
	<#if vo.isRob == 1>
		<@a href="/p?d_RD/${pid}/${vo.playerId}/"/>劫镖</a>
	</#if>
	<br/>
	</#list>
</#if>
<@goback/>
<@gogame/>
<#include "/include/foot_card.ftl">