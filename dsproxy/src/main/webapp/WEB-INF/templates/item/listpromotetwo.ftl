<#include "/include/header.ftl">
<#include "/include/playeritem.ftl">
<card title="${game_title}"><p>
${location}<br/>
${last_npc.name}:<br/>
<#if items?exists>
	你要用什么宝石升星装备呢?<br/>
	<#list items as pi>		
		<@a href="/p?i_Pr/${pid}/${pi.itemId}/${player_item.id}"/>升星</a>
		<@a href="/p?i_I/${pid}/${pi.id}"/><@showName pi/></a><br/>	
	</#list>
<#else>
	抱歉,你没有可以进行升星的宝石<br/>
</#if>
<@goback/>
<@gofacility/>
</p></card>
</wml> 