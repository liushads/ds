<#include "/include/header.ftl">
<#include "/include/player.ftl">
<#include "/include/playeritem.ftl">
<card title="${game_title}"><p>
<#if other?exists>
摆摊的物品列表：<@a href="/p?ex_SPL/${pid}/${other.id}/0"/>宠物</a><br/>
<#if page_objs?size == 0>
没有物品出售<br/>
<#else>
<#list page_objs as pi>
	<@a href="/p?ex_SD/${pid}/${pi.playerId}/${pi.id}"/><@showName pi/><#if pi.item.type!=1>*${pi.exchangeAmount}</#if></a>(${pi.exchangePrice/100}金票)<br/>
	
</#list>
<#if (page>0)><@a href="/p?ex_SP/${pid}/${other.id}/${page-1}/"/>上页.</a></#if>
	<#if (page<total_page-1)><@a href="/p?ex_SP/${pid}/${other.id}/${page+1}/"/>下页.</a></#if>
	<#if (total_page>1)>(${page+1}/${total_page})<br/></#if>
</#if>
<#else>
用户离线或者暂无摆摊物品<br/>
</#if>
<@gogame/>
</p></card>
</wml>