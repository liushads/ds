<#include "/include/header.ftl">
<#include "/include/playeritem.ftl">
<card title="${game_title}"><p>
<#if items?exists>
	你要用什么宝石打孔装备呢?<br/>
	<#list items as pi>		
		<@a href="/p?i_P/${pid}/${pi.itemId}/${player_item.id}"/>打孔</a>
		<@a href="/p?i_I/${pid}/${pi.id}"/><@showName pi/></a><br/>	
	</#list>
<#else>
	抱歉,你没有可以进行打孔的宝石<br/>
</#if>
<@goback/>
<@gofacility/>
</p></card>
</wml> 