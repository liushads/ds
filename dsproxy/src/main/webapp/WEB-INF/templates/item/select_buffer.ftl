<#include "/include/header.ftl">
<#include "/include/head_card.ftl">
您当前有以下装备可以洗练<br/>
<#if label_player_items?exists && (label_player_items?size > 0)>
	<#list label_player_items as pi>
		<@a href="/p?i_I/${pid}/${pi.id}/"/>${pi.item.name}</a> <@a href="/p?i_SB/${pid}//0/${pi.id}/se/"/>洗练</a><br/>
	</#list>
<#else>
无<br/>
</#if>
<@gogame/>
<#include "/include/foot_card.ftl">