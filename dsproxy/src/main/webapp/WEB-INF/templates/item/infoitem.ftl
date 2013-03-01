<#include "/include/header.ftl">
<#include "/include/playeritem.ftl">
<card title="${game_title}"><p>
<@showItem item />
<#if (npc?exists && !no_buy?exists)>
	<#if item.type!=1>
		<@a href="/p?i_BN/${pid}/${item.id}"/>购买</a><br/>
	<#else>
		<@a href="/p?i_BI/${pid}/${item.id}/1"/>购买</a><br/>	
	</#if>
</#if>
<@goback/>
<@gofacility/>
</p></card>
</wml>


 