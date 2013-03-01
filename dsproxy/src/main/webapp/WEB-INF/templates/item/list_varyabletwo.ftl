<#include "/include/header.ftl">
<#include "/include/playeritem.ftl">
<card title="${game_title}"><p>
${location}<br/>
${last_npc.name}:<br/>
<#if items?exists>
	你要用什么宝石改变装备特性呢?<br/>
	<#list items as pi>		
		<@a href="/p?i_VPr/${pid}/${player_item.id}/${pi.itemId}"/>改变</a>
		<@a href="/p?i_I/${pid}/${pi.id}"/><@showName pi/></a><br/>	
	</#list>
<#else>
	抱歉,你没有可以进行改变特性的宝石<br/>
</#if>
<@goback/>
<@gofacility/>
</p></card>
</wml> 