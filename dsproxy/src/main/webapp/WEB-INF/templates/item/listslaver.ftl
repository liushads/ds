<#-- 可以进行镶嵌的宝石 -->
<#include "/include/header.ftl">
<#include "/include/playeritem.ftl">
<card title="${game_title}"><p>
${location}<br/>
${last_npc.name}:
<#if items?exists>
	你要把什么宝石放在装备上呢?<br/>
	<#list items as pi>		
		<@a href="/p?i_E/${pid}/${player_item.id}/${pi.id}/"/>镶嵌</a>
		<@a href="/p?i_I/${pid}/${pi.id}"/><@showName pi/></a><br/>	
	</#list>
<#else>
	抱歉,你没有可以进行镶嵌的宝石<br/>
</#if>
<@goback/>
<@gofacility/>
</p></card>
</wml> 